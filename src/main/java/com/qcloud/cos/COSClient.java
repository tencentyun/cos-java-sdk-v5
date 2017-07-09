package com.qcloud.cos;

import static com.qcloud.cos.internal.LengthCheckInputStream.EXCLUDE_SKIPPED_BYTES;
import static com.qcloud.cos.internal.LengthCheckInputStream.INCLUDE_SKIPPED_BYTES;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.Throwables;
import com.qcloud.cos.http.CosHttpClient;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.DefaultCosHttpClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpResponseHandler;
import com.qcloud.cos.http.Mimetypes;
import com.qcloud.cos.internal.BucketNameUtils;
import com.qcloud.cos.internal.COSObjectResponseHandler;
import com.qcloud.cos.internal.COSVersionHeaderHandler;
import com.qcloud.cos.internal.COSXmlResponseHandler;
import com.qcloud.cos.internal.Constants;
import com.qcloud.cos.internal.CosMetadataResponseHandler;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.CosServiceResponse;
import com.qcloud.cos.internal.InputSubstream;
import com.qcloud.cos.internal.LengthCheckInputStream;
import com.qcloud.cos.internal.MD5DigestCalculatingInputStream;
import com.qcloud.cos.internal.ObjectExpirationHeaderHandler;
import com.qcloud.cos.internal.ReleasableInputStream;
import com.qcloud.cos.internal.RequestXmlFactory;
import com.qcloud.cos.internal.ResettableInputStream;
import com.qcloud.cos.internal.ResponseHeaderHandlerChain;
import com.qcloud.cos.internal.ServerSideEncryptionHeaderHandler;
import com.qcloud.cos.internal.ServiceClientHolderInputStream;
import com.qcloud.cos.internal.SkipMd5CheckStrategy;
import com.qcloud.cos.internal.Unmarshaller;
import com.qcloud.cos.internal.Unmarshallers;
import com.qcloud.cos.internal.VoidCosResponseHandler;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CompleteMultipartUploadHandler;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CopyObjectResultHandler;
import com.qcloud.cos.model.AbortMultipartUploadRequest;
import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.CompleteMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadResult;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.CosDataSource;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.DeleteBucketRequest;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.Grant;
import com.qcloud.cos.model.Grantee;
import com.qcloud.cos.model.InitiateMultipartUploadRequest;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.ListMultipartUploadsRequest;
import com.qcloud.cos.model.ListNextBatchOfObjectsRequest;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ListPartsRequest;
import com.qcloud.cos.model.MultipartUploadListing;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PartListing;
import com.qcloud.cos.model.Permission;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.UploadPartRequest;
import com.qcloud.cos.model.UploadPartResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Base64;
import com.qcloud.cos.utils.DateUtils;
import com.qcloud.cos.utils.Md5Utils;
import com.qcloud.cos.utils.ServiceUtils;
import com.qcloud.cos.utils.StringUtils;
import com.qcloud.cos.utils.UrlEncoderUtils;

public class COSClient implements COS {

    private static final Logger log = LoggerFactory.getLogger(COSClient.class);

    private final SkipMd5CheckStrategy skipMd5CheckStrategy = SkipMd5CheckStrategy.INSTANCE;
    private final VoidCosResponseHandler voidCosResponseHandler = new VoidCosResponseHandler();

    private COSCredentials cred;

    private ClientConfig clientConfig;

    private CosHttpClient cosHttpClient;

    public COSClient(COSCredentials cred, ClientConfig clientConfig) {
        super();
        this.cred = cred;
        this.clientConfig = clientConfig;
        this.cosHttpClient = new DefaultCosHttpClient(clientConfig);
    }

    public void shutdown() {
        this.cosHttpClient.shutdown();
    }

    /**
     * <p>
     * Asserts that the specified parameter value is not <code>null</code> and if it is, throws an
     * <code>IllegalArgumentException</code> with the specified error message.
     * </p>
     *
     * @param parameterValue The parameter value being checked.
     * @param errorMessage The error message to include in the IllegalArgumentException if the
     *        specified parameter is null.
     */
    private void rejectNull(Object parameterValue, String errorMessage) {
        if (parameterValue == null)
            throw new IllegalArgumentException(errorMessage);
    }

    protected <X extends CosServiceRequest> CosHttpRequest<X> createRequest(String bucketName,
            String key, X originalRequest, HttpMethodName httpMethod) {
        CosHttpRequest<X> httpRequest = new CosHttpRequest<X>(originalRequest);
        httpRequest.setHttpMethod(httpMethod);
        httpRequest.addHeader(Headers.USER_AGENT, clientConfig.getUserAgent());
        buildUrlAndHost(httpRequest, bucketName, key);
        return httpRequest;
    }

    private void addAclHeaders(CosHttpRequest<? extends CosServiceRequest> request,
            AccessControlList acl) {
        List<Grant> grants = acl.getGrantsAsList();
        Map<Permission, Collection<Grantee>> grantsByPermission =
                new HashMap<Permission, Collection<Grantee>>();
        for (Grant grant : grants) {
            if (!grantsByPermission.containsKey(grant.getPermission())) {
                grantsByPermission.put(grant.getPermission(), new LinkedList<Grantee>());
            }
            grantsByPermission.get(grant.getPermission()).add(grant.getGrantee());
        }
        for (Permission permission : Permission.values()) {
            if (grantsByPermission.containsKey(permission)) {
                Collection<Grantee> grantees = grantsByPermission.get(permission);
                boolean seenOne = false;
                StringBuilder granteeString = new StringBuilder();
                for (Grantee grantee : grantees) {
                    if (!seenOne)
                        seenOne = true;
                    else
                        granteeString.append(", ");
                    granteeString.append(grantee.getTypeIdentifier()).append("=").append("\"")
                            .append(grantee.getIdentifier()).append("\"");
                }
                request.addHeader(permission.getHeaderName(), granteeString.toString());
            }
        }
    }

    private UploadPartResult doUploadPart(final String bucketName, final String key,
            final String uploadId, final int partNumber, final long partSize,
            CosHttpRequest<UploadPartRequest> request, InputStream inputStream,
            MD5DigestCalculatingInputStream md5DigestStream) {
        try {
            request.setContent(inputStream);
            ObjectMetadata metadata = invoke(request, new CosMetadataResponseHandler());
            final String etag = metadata.getETag();

            /*
             * if (md5DigestStream != null &&
             * !skipMd5CheckStrategy.skipClientSideValidationPerUploadPartResponse(metadata)) {
             * byte[] clientSideHash = md5DigestStream.getMd5Digest(); byte[] serverSideHash =
             * BinaryUtils.fromHex(etag);
             * 
             * if (!Arrays.equals(clientSideHash, serverSideHash)) { final String info =
             * "bucketName: " + bucketName + ", key: " + key + ", uploadId: " + uploadId +
             * ", partNumber: " + partNumber + ", partSize: " + partSize; throw new
             * CosClientException( "Unable to verify integrity of data upload.  " +
             * "Client calculated content hash (contentMD5: " +
             * Base16.encodeAsString(clientSideHash) + " in hex) didn't match hash (etag: " + etag +
             * " in hex) calculated by Qcloud COS.  " +
             * "You may need to delete the data stored in Qcloud COS. " + "(" + info + ")"); } }
             */
            UploadPartResult result = new UploadPartResult();
            result.setETag(etag);
            result.setPartNumber(partNumber);

            return result;
        } catch (Throwable t) {
            throw Throwables.failure(t);
        }
    }

    /**
     * <p>
     * Populates the specified request object with the appropriate headers from the
     * {@link ObjectMetadata} object.
     * </p>
     *
     * @param request The request to populate with headers.
     * @param metadata The metadata containing the header information to include in the request.
     */
    protected static void populateRequestMetadata(CosHttpRequest<?> request,
            ObjectMetadata metadata) {
        Map<String, Object> rawMetadata = metadata.getRawMetadata();
        if (rawMetadata != null) {
            for (Entry<String, Object> entry : rawMetadata.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        Date httpExpiresDate = metadata.getHttpExpiresDate();
        if (httpExpiresDate != null) {
            request.addHeader(Headers.EXPIRES, DateUtils.formatRFC822Date(httpExpiresDate));
        }

        Map<String, String> userMetadata = metadata.getUserMetadata();
        if (userMetadata != null) {
            for (Entry<String, String> entry : userMetadata.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null)
                    key = key.trim();
                if (value != null)
                    value = value.trim();
                request.addHeader(Headers.COS_USER_METADATA_PREFIX + key, value);
            }
        }
    }

    private void populateRequestWithCopyObjectParameters(
            CosHttpRequest<? extends CosServiceRequest> request,
            CopyObjectRequest copyObjectRequest) {
        Region sourceRegion = copyObjectRequest.getSourceBucketRegion();
        // 如果用户没有设置源region, 则默认和目的region一致
        if (sourceRegion == null) {
            sourceRegion = this.clientConfig.getRegion();
        }
        String sourceKey = copyObjectRequest.getSourceKey();
        if (!sourceKey.startsWith("/")) {
            sourceKey = "/" + sourceKey;
        }
        String copySourceHeader = String.format("%s-%s.%s.myqcloud.com%s",
                copyObjectRequest.getSourceBucketName(), this.cred.getCOSAppId(),
                sourceRegion.getRegionName(), UrlEncoderUtils.encodeEscapeDelimiter(sourceKey));
        if (copyObjectRequest.getSourceVersionId() != null) {
            copySourceHeader += "?versionId=" + copyObjectRequest.getSourceVersionId();
        }
        request.addHeader("x-cos-copy-source", copySourceHeader);

        addDateHeader(request, Headers.COPY_SOURCE_IF_MODIFIED_SINCE,
                copyObjectRequest.getModifiedSinceConstraint());
        addDateHeader(request, Headers.COPY_SOURCE_IF_UNMODIFIED_SINCE,
                copyObjectRequest.getUnmodifiedSinceConstraint());

        addStringListHeader(request, Headers.COPY_SOURCE_IF_MATCH,
                copyObjectRequest.getMatchingETagConstraints());
        addStringListHeader(request, Headers.COPY_SOURCE_IF_NO_MATCH,
                copyObjectRequest.getNonmatchingETagConstraints());

        if (copyObjectRequest.getAccessControlList() != null) {
            addAclHeaders(request, copyObjectRequest.getAccessControlList());
        } else if (copyObjectRequest.getCannedAccessControlList() != null) {
            request.addHeader(Headers.COS_CANNED_ACL,
                    copyObjectRequest.getCannedAccessControlList().toString());
        }

        if (copyObjectRequest.getStorageClass() != null) {
            request.addHeader(Headers.STORAGE_CLASS, copyObjectRequest.getStorageClass());
        }

        if (copyObjectRequest.getRedirectLocation() != null) {
            request.addHeader(Headers.REDIRECT_LOCATION, copyObjectRequest.getRedirectLocation());
        }

        ObjectMetadata newObjectMetadata = copyObjectRequest.getNewObjectMetadata();
        if (newObjectMetadata != null) {
            request.addHeader(Headers.METADATA_DIRECTIVE, "REPLACE");
            populateRequestMetadata(request, newObjectMetadata);
        }

        // Populate the SSE-C parameters for the destination object
        // populateSourceSSE_C(request, copyObjectRequest.getSourceSSECustomerKey());
        // populateSSE_C(request, copyObjectRequest.getDestinationSSECustomerKey());
    }

    private String formatKey(String key) {
        if (key == null) {
            return "/";
        }
        if (!key.startsWith("/")) {
            key = "/" + key;
        }
        return key;
    }

    private <X extends CosServiceRequest> void buildUrlAndHost(CosHttpRequest<X> request,
            String bucket, String key) throws CosClientException {
        key = formatKey(key);
        request.setResourcePath(key);

        String host = String.format("%s-%s.%s.myqcloud.com", bucket, cred.getCOSAppId(),
                clientConfig.getRegion().getRegionName());
        if (this.clientConfig.getEndPointSuffix() != null) {
            String endPointSuffix = clientConfig.getEndPointSuffix();
            if (endPointSuffix.startsWith(".")) {
                host = String.format("%s-%s%s", bucket, cred.getCOSAppId(), endPointSuffix);
            } else {
                host = String.format("%s-%s.%s", bucket, cred.getCOSAppId(), endPointSuffix);
            }
        }
        request.addHeader(Headers.HOST, host);
        URI endpoint;
        try {
            endpoint = new URI(clientConfig.getHttpProtocol().toString(), host, key, null);
        } catch (URISyntaxException e) {
            throw new CosClientException("format cos uri error, exception: {}", e);
        }
        request.setEndpoint(endpoint);
    }

    private <X, Y extends CosServiceRequest> X invoke(CosHttpRequest<Y> request,
            Unmarshaller<X, InputStream> unmarshaller)
                    throws CosClientException, CosServiceException {
        return invoke(request, new COSXmlResponseHandler<X>(unmarshaller));

    }

    private <X, Y extends CosServiceRequest> X invoke(CosHttpRequest<Y> request,
            HttpResponseHandler<CosServiceResponse<X>> responseHandler)
                    throws CosClientException, CosServiceException {

        COSSigner cosSigner = new COSSigner();
        cosSigner.sign(request, cred);
        return this.cosHttpClient.exeute(request, responseHandler);

    }

    private static PutObjectResult createPutObjectResult(ObjectMetadata metadata) {
        final PutObjectResult result = new PutObjectResult();
        result.setVersionId(metadata.getVersionId());
        result.setETag(metadata.getETag());
        result.setExpirationTime(metadata.getExpirationTime());
        result.setMetadata(metadata);
        return result;
    }

    /**
     * Adds the specified header to the specified request, if the header value is not null.
     *
     * @param request The request to add the header to.
     * @param header The header name.
     * @param value The header value.
     */
    private static void addHeaderIfNotNull(CosHttpRequest<?> request, String header, String value) {
        if (value != null) {
            request.addHeader(header, value);
        }
    }

    /**
     * <p>
     * Adds the specified date header in RFC 822 date format to the specified request. This method
     * will not add a date header if the specified date value is <code>null</code>.
     * </p>
     *
     * @param request The request to add the header to.
     * @param header The header name.
     * @param value The header value.
     */
    private static void addDateHeader(CosHttpRequest<?> request, String header, Date value) {
        if (value != null) {
            request.addHeader(header, DateUtils.formatRFC822Date(value));
        }
    }

    /**
     * <p>
     * Adds the specified string list header, joined together separated with commas, to the
     * specified request. This method will not add a string list header if the specified values are
     * <code>null</code> or empty.
     * </p>
     *
     * @param request The request to add the header to.
     * @param header The header name.
     * @param values The list of strings to join together for the header value.
     */
    private static void addStringListHeader(CosHttpRequest<?> request, String header,
            List<String> values) {
        if (values != null && !values.isEmpty()) {
            request.addHeader(header, StringUtils.join(values));
        }
    }

    private void setZeroContentLength(CosHttpRequest<?> req) {
        req.addHeader(Headers.CONTENT_LENGTH, String.valueOf(0));
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest putObjectRequest)
            throws CosClientException, CosServiceException {
        rejectNull(putObjectRequest,
                "The PutObjectRequest parameter must be specified when uploading an object");
        final File file = putObjectRequest.getFile();
        final InputStream isOrig = putObjectRequest.getInputStream();
        final String bucketName = putObjectRequest.getBucketName();
        final String key = putObjectRequest.getKey();
        ObjectMetadata metadata = putObjectRequest.getMetadata();
        InputStream input = isOrig;
        if (metadata == null)
            metadata = new ObjectMetadata();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when uploading an object");
        rejectNull(key, "The key parameter must be specified when uploading an object");
        // If a file is specified for upload, we need to pull some additional
        // information from it to auto-configure a few options
        if (file == null) {
            // When input is a FileInputStream, this wrapping enables
            // unlimited mark-and-reset
            if (input != null)
                input = ReleasableInputStream.wrap(input);
        } else {
            // Always set the content length, even if it's already set
            metadata.setContentLength(file.length());
            final boolean calculateMD5 = metadata.getContentMD5() == null;
            // Only set the content type if it hasn't already been set
            if (metadata.getContentType() == null) {
                metadata.setContentType(Mimetypes.getInstance().getMimetype(file));
            }

            if (calculateMD5 && !skipMd5CheckStrategy.skipServerSideValidation(putObjectRequest)) {
                try {
                    String contentMd5_b64 = Md5Utils.md5AsBase64(file);
                    metadata.setContentMD5(contentMd5_b64);
                } catch (Exception e) {
                    throw new CosClientException("Unable to calculate MD5 hash: " + e.getMessage(),
                            e);
                }
            }
            input = ResettableInputStream.newResettableInputStream(file,
                    "Unable to find file to upload");
        }

        final ObjectMetadata returnedMetadata;
        MD5DigestCalculatingInputStream md5DigestStream = null;
        try {
            CosHttpRequest<PutObjectRequest> request =
                    createRequest(bucketName, key, putObjectRequest, HttpMethodName.PUT);
            if (putObjectRequest.getAccessControlList() != null) {
                addAclHeaders(request, putObjectRequest.getAccessControlList());
            } else if (putObjectRequest.getCannedAcl() != null) {
                request.addHeader(Headers.COS_CANNED_ACL,
                        putObjectRequest.getCannedAcl().toString());
            }

            if (putObjectRequest.getStorageClass() != null) {
                request.addHeader(Headers.STORAGE_CLASS, putObjectRequest.getStorageClass());
            }

            if (putObjectRequest.getRedirectLocation() != null) {
                request.addHeader(Headers.REDIRECT_LOCATION,
                        putObjectRequest.getRedirectLocation());
                if (input == null) {
                    input = new ByteArrayInputStream(new byte[0]);
                }
            }

            // Use internal interface to differentiate 0 from unset.
            final Long contentLength = (Long) metadata.getRawMetadataValue(Headers.CONTENT_LENGTH);
            if (contentLength == null) {
                /*
                 * There's nothing we can do except for let the HTTP client buffer the input stream
                 * contents if the caller doesn't tell us how much data to expect in a stream since
                 * we have to explicitly tell how much we're sending before we start sending any of
                 * it.
                 */
                log.warn("No content length specified for stream data.  "
                        + "Stream contents will be buffered in memory and could result in "
                        + "out of memory errors.");
            } else {
                final long expectedLength = contentLength.longValue();
                if (expectedLength >= 0) {
                    // Performs length check on the underlying data stream.
                    // For COS encryption client, the underlying data stream here
                    // refers to the cipher-text data stream (ie not the underlying
                    // plain-text data stream which in turn may have been wrapped
                    // with it's own length check input stream.)
                    LengthCheckInputStream lcis = new LengthCheckInputStream(input, expectedLength, // expected
                                                                                                    // data
                                                                                                    // length
                                                                                                    // to
                                                                                                    // be
                                                                                                    // uploaded
                            EXCLUDE_SKIPPED_BYTES);
                    input = lcis;
                }
            }

            if (metadata.getContentMD5() == null
                    && !skipMd5CheckStrategy.skipClientSideValidationPerRequest(putObjectRequest)) {
                /*
                 * If the user hasn't set the content MD5, then we don't want to buffer the whole
                 * stream in memory just to calculate it. Instead, we can calculate it on the fly
                 * and validate it with the returned ETag from the object upload.
                 */
                input = md5DigestStream = new MD5DigestCalculatingInputStream(input);
            }

            if (metadata.getContentType() == null) {
                /*
                 * Default to the "application/octet-stream" if the user hasn't specified a content
                 * type.
                 */
                metadata.setContentType(Mimetypes.MIMETYPE_OCTET_STREAM);
            }
            populateRequestMetadata(request, metadata);
            request.setContent(input);
            try {
                returnedMetadata = invoke(request, new CosMetadataResponseHandler());
            } catch (Throwable t) {
                throw Throwables.failure(t);
            }
        } finally {
            CosDataSource.Utils.cleanupDataSource(putObjectRequest, file, isOrig, input, log);
        }

        String contentMd5 = metadata.getContentMD5();
        if (md5DigestStream != null) {
            contentMd5 = Base64.encodeAsString(md5DigestStream.getMd5Digest());
        }

        /*
         * final String etag = returnedMetadata.getETag(); if (contentMd5 != null &&
         * !skipMd5CheckStrategy.skipClientSideValidationPerPutResponse(returnedMetadata)) { byte[]
         * clientSideHash = BinaryUtils.fromBase64(contentMd5); byte[] serverSideHash =
         * BinaryUtils.fromHex(etag);
         * 
         * // TODO(chengwu) 目前 COS Server端发挥的etag是 sha1 // 客户端算出来的是content-MD5, 以后这里需要做校验 if
         * (!Arrays.equals(clientSideHash, serverSideHash)) { throw new CosClientException(
         * "Unable to verify integrity of data upload.  " +
         * "Client calculated content hash (contentMD5: " + contentMd5 +
         * " in base 64) didn't match hash (etag: " + etag + " in hex) calculated by COS .  " +
         * "You may need to delete the data stored in COS . (metadata.contentMD5: " +
         * metadata.getContentMD5() + ", md5DigestStream: " + md5DigestStream + ", bucketName: " +
         * bucketName + ", key: " + key + ")"); } }
         */
        PutObjectResult result = createPutObjectResult(returnedMetadata);
        result.setContentMd5(contentMd5);
        return result;
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, File file)
            throws CosClientException, CosServiceException {
        return putObject(
                new PutObjectRequest(bucketName, key, file).withMetadata(new ObjectMetadata()));
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input,
            ObjectMetadata metadata) throws CosClientException, CosServiceException {
        return putObject(new PutObjectRequest(bucketName, key, input, metadata));
    }

    @Override
    public COSObject getObject(String bucketName, String key)
            throws CosClientException, CosServiceException {
        return getObject(new GetObjectRequest(bucketName, key));
    }

    @Override
    public COSObject getObject(GetObjectRequest getObjectRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getObjectRequest,
                "The GetObjectRequest parameter must be specified when requesting an object");
        rejectNull(getObjectRequest.getBucketName(),
                "The bucket name parameter must be specified when requesting an object");
        rejectNull(getObjectRequest.getKey(),
                "The key parameter must be specified when requesting an object");

        CosHttpRequest<GetObjectRequest> request = createRequest(getObjectRequest.getBucketName(),
                getObjectRequest.getKey(), getObjectRequest, HttpMethodName.GET);
        if (getObjectRequest.getVersionId() != null) {
            request.addParameter("versionId", getObjectRequest.getVersionId());
        }

        // Range
        long[] range = getObjectRequest.getRange();
        if (range != null) {
            request.addHeader(Headers.RANGE,
                    "bytes=" + Long.toString(range[0]) + "-" + Long.toString(range[1]));
        }

        addDateHeader(request, Headers.GET_OBJECT_IF_MODIFIED_SINCE,
                getObjectRequest.getModifiedSinceConstraint());
        addDateHeader(request, Headers.GET_OBJECT_IF_UNMODIFIED_SINCE,
                getObjectRequest.getUnmodifiedSinceConstraint());
        addStringListHeader(request, Headers.GET_OBJECT_IF_MATCH,
                getObjectRequest.getMatchingETagConstraints());
        addStringListHeader(request, Headers.GET_OBJECT_IF_NONE_MATCH,
                getObjectRequest.getNonmatchingETagConstraints());

        try {
            COSObject cosObject = invoke(request, new COSObjectResponseHandler());
            cosObject.setBucketName(getObjectRequest.getBucketName());
            cosObject.setKey(getObjectRequest.getKey());
            InputStream is = cosObject.getObjectContent();
            HttpRequestBase httpRequest = cosObject.getObjectContent().getHttpRequest();

            is = new ServiceClientHolderInputStream(is, this);
            // Ensures the data received from COS has the same length as the
            // expected content-length
            is = new LengthCheckInputStream(is, cosObject.getObjectMetadata().getContentLength(), // expected
                                                                                                  // length
                    INCLUDE_SKIPPED_BYTES); // bytes received from COS are all included even if
                                            // skipped
            cosObject.setObjectContent(new COSObjectInputStream(is, httpRequest));
            return cosObject;
        } catch (CosServiceException cse) {
            /*
             * If the request failed because one of the specified constraints was not met (ex:
             * matching ETag, modified since date, etc.), then return null, so that users don't have
             * to wrap their code in try/catch blocks and check for this status code if they want to
             * use constraints.
             */
            if (cse.getStatusCode() == 412 || cse.getStatusCode() == 304) {
                return null;
            }
            throw cse;
        }
    }

    @Override
    public ObjectMetadata getObject(final GetObjectRequest getObjectRequest, File destinationFile)
            throws CosClientException, CosServiceException {
        rejectNull(destinationFile,
                "The destination file parameter must be specified when downloading an object directly to a file");
        COSObject cosObject = ServiceUtils.retryableDownloadCOSObjectToFile(destinationFile,
                new ServiceUtils.RetryableCOSDownloadTask() {

                    @Override
                    public boolean needIntegrityCheck() {
                        return !skipMd5CheckStrategy
                                .skipClientSideValidationPerRequest(getObjectRequest);
                    }

                    @Override
                    public COSObject getCOSObjectStream() {
                        return getObject(getObjectRequest);
                    }
                }, ServiceUtils.OVERWRITE_MODE);

        if (cosObject == null)
            return null;

        return cosObject.getObjectMetadata();
    }

    @Override
    public ObjectMetadata getobjectMetadata(String bucketName, String key)
            throws CosClientException, CosServiceException {
        return getObjectMetadata(new GetObjectMetadataRequest(bucketName, key));
    }

    @Override
    public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getObjectMetadataRequest,
                "The GetObjectMetadataRequest parameter must be specified when requesting an object's metadata");

        String bucketName = getObjectMetadataRequest.getBucketName();
        String key = getObjectMetadataRequest.getKey();
        String versionId = getObjectMetadataRequest.getVersionId();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when requesting an object's metadata");
        rejectNull(key, "The key parameter must be specified when requesting an object's metadata");

        CosHttpRequest<GetObjectMetadataRequest> request =
                createRequest(bucketName, key, getObjectMetadataRequest, HttpMethodName.HEAD);
        if (versionId != null)
            request.addParameter("versionId", versionId);
        return invoke(request, new CosMetadataResponseHandler());
    }

    @Override
    public void deleteObject(DeleteObjectRequest deleteObjectRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteObjectRequest,
                "The delete object request must be specified when deleting an object");

        rejectNull(deleteObjectRequest.getBucketName(),
                "The bucket name must be specified when deleting an object");
        rejectNull(deleteObjectRequest.getKey(),
                "The key must be specified when deleting an object");

        CosHttpRequest<DeleteObjectRequest> request =
                createRequest(deleteObjectRequest.getBucketName(), deleteObjectRequest.getKey(),
                        deleteObjectRequest, HttpMethodName.DELETE);
        invoke(request, voidCosResponseHandler);
    }

    @Override
    public Bucket createBucket(CreateBucketRequest createBucketRequest)
            throws CosClientException, CosServiceException {
        rejectNull(createBucketRequest,
                "The CreateBucketRequest parameter must be specified when creating a bucket");

        String bucketName = createBucketRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when creating a bucket");

        if (bucketName != null)
            bucketName = bucketName.trim();
        BucketNameUtils.validateBucketName(bucketName);

        CosHttpRequest<CreateBucketRequest> request =
                createRequest(bucketName, "/", createBucketRequest, HttpMethodName.PUT);

        if (createBucketRequest.getAccessControlList() != null) {
            addAclHeaders(request, createBucketRequest.getAccessControlList());
        } else if (createBucketRequest.getCannedAcl() != null) {
            request.addHeader(Headers.COS_CANNED_ACL,
                    createBucketRequest.getCannedAcl().toString());
        }

        invoke(request, voidCosResponseHandler);

        return new Bucket(bucketName);
    }

    @Override
    public void deleteBucket(DeleteBucketRequest deleteBucketRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteBucketRequest,
                "The DeleteBucketRequest parameter must be specified when deleting a bucket");

        String bucketName = deleteBucketRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when deleting a bucket");

        CosHttpRequest<DeleteBucketRequest> request =
                createRequest(bucketName, "/", deleteBucketRequest, HttpMethodName.DELETE);
        invoke(request, voidCosResponseHandler);
    }

    private boolean shouldRetryCompleteMultipartUpload(CosServiceRequest originalRequest,
            CosClientException exception, int retriesAttempted) {
        return false;
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(
            InitiateMultipartUploadRequest initiateMultipartUploadRequest)
                    throws CosClientException, CosServiceException {
        rejectNull(initiateMultipartUploadRequest,
                "The request parameter must be specified when initiating a multipart upload");
        rejectNull(initiateMultipartUploadRequest.getBucketName(),
                "The bucket name parameter must be specified when initiating a multipart upload");
        rejectNull(initiateMultipartUploadRequest.getKey(),
                "The key parameter must be specified when initiating a multipart upload");

        CosHttpRequest<InitiateMultipartUploadRequest> request =
                createRequest(initiateMultipartUploadRequest.getBucketName(),
                        initiateMultipartUploadRequest.getKey(), initiateMultipartUploadRequest,
                        HttpMethodName.POST);
        request.addParameter("uploads", null);

        if (initiateMultipartUploadRequest.getStorageClass() != null)
            request.addHeader(Headers.STORAGE_CLASS,
                    initiateMultipartUploadRequest.getStorageClass().toString());

        if (initiateMultipartUploadRequest.getRedirectLocation() != null) {
            request.addHeader(Headers.REDIRECT_LOCATION,
                    initiateMultipartUploadRequest.getRedirectLocation());
        }

        if (initiateMultipartUploadRequest.getAccessControlList() != null) {
            addAclHeaders(request, initiateMultipartUploadRequest.getAccessControlList());
        } else if (initiateMultipartUploadRequest.getCannedACL() != null) {
            request.addHeader(Headers.COS_CANNED_ACL,
                    initiateMultipartUploadRequest.getCannedACL().toString());
        }

        if (initiateMultipartUploadRequest.objectMetadata != null) {
            populateRequestMetadata(request, initiateMultipartUploadRequest.objectMetadata);
        }

        @SuppressWarnings("unchecked")
        ResponseHeaderHandlerChain<InitiateMultipartUploadResult> responseHandler =
                new ResponseHeaderHandlerChain<InitiateMultipartUploadResult>(
                        // xml payload unmarshaller
                        new Unmarshallers.InitiateMultipartUploadResultUnmarshaller(),
                        // header handlers
                        new ServerSideEncryptionHeaderHandler<InitiateMultipartUploadResult>());
        return invoke(request, responseHandler);
    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest)
            throws CosClientException, CosServiceException {
        rejectNull(uploadPartRequest,
                "The request parameter must be specified when uploading a part");
        final File fileOrig = uploadPartRequest.getFile();
        final InputStream isOrig = uploadPartRequest.getInputStream();
        final String bucketName = uploadPartRequest.getBucketName();
        final String key = uploadPartRequest.getKey();
        final String uploadId = uploadPartRequest.getUploadId();
        final int partNumber = uploadPartRequest.getPartNumber();
        final long partSize = uploadPartRequest.getPartSize();
        rejectNull(bucketName, "The bucket name parameter must be specified when uploading a part");
        rejectNull(key, "The key parameter must be specified when uploading a part");
        rejectNull(uploadId, "The upload ID parameter must be specified when uploading a part");
        rejectNull(partNumber, "The part number parameter must be specified when uploading a part");
        rejectNull(partSize, "The part size parameter must be specified when uploading a part");
        CosHttpRequest<UploadPartRequest> request =
                createRequest(bucketName, key, uploadPartRequest, HttpMethodName.PUT);
        request.addParameter("uploadId", uploadId);
        request.addParameter("partNumber", Integer.toString(partNumber));

        final ObjectMetadata objectMetadata = uploadPartRequest.getObjectMetadata();
        if (objectMetadata != null)
            populateRequestMetadata(request, objectMetadata);

        addHeaderIfNotNull(request, Headers.CONTENT_MD5, uploadPartRequest.getMd5Digest());
        request.addHeader(Headers.CONTENT_LENGTH, Long.toString(partSize));

        InputStream isCurr = isOrig;
        try {
            if (fileOrig == null) {
                if (isOrig == null) {
                    throw new IllegalArgumentException(
                            "A File or InputStream must be specified when uploading part");
                } else {
                    // When isCurr is a FileInputStream, this wrapping enables
                    // unlimited mark-and-reset
                    isCurr = ReleasableInputStream.wrap(isCurr);
                }
            } else {
                try {
                    isCurr = new ResettableInputStream(fileOrig);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Failed to open file " + fileOrig, e);
                }
            }
            isCurr = new InputSubstream(isCurr, uploadPartRequest.getFileOffset(), partSize,
                    uploadPartRequest.isLastPart());
            MD5DigestCalculatingInputStream md5DigestStream = null;
            if (uploadPartRequest.getMd5Digest() == null && !skipMd5CheckStrategy
                    .skipClientSideValidationPerRequest(uploadPartRequest)) {
                /*
                 * If the user hasn't set the content MD5, then we don't want to buffer the whole
                 * stream in memory just to calculate it. Instead, we can calculate it on the fly
                 * and validate it with the returned ETag from the object upload.
                 */
                isCurr = md5DigestStream = new MD5DigestCalculatingInputStream(isCurr);
            }
            return doUploadPart(bucketName, key, uploadId, partNumber, partSize, request, isCurr,
                    md5DigestStream);
        } finally {
            CosDataSource.Utils.cleanupDataSource(uploadPartRequest, fileOrig, isOrig, isCurr, log);
        }
    }

    @Override
    public PartListing listParts(ListPartsRequest listPartsRequest)
            throws CosClientException, CosServiceException {
        rejectNull(listPartsRequest, "The request parameter must be specified when listing parts");

        rejectNull(listPartsRequest.getBucketName(),
                "The bucket name parameter must be specified when listing parts");
        rejectNull(listPartsRequest.getKey(),
                "The key parameter must be specified when listing parts");
        rejectNull(listPartsRequest.getUploadId(),
                "The upload ID parameter must be specified when listing parts");

        CosHttpRequest<ListPartsRequest> request = createRequest(listPartsRequest.getBucketName(),
                listPartsRequest.getKey(), listPartsRequest, HttpMethodName.GET);
        request.addParameter("uploadId", listPartsRequest.getUploadId());

        if (listPartsRequest.getMaxParts() != null)
            request.addParameter("max-parts", listPartsRequest.getMaxParts().toString());
        if (listPartsRequest.getPartNumberMarker() != null)
            request.addParameter("part-number-marker",
                    listPartsRequest.getPartNumberMarker().toString());
        if (listPartsRequest.getEncodingType() != null)
            request.addParameter("encoding-type", listPartsRequest.getEncodingType());

        return invoke(request, new Unmarshallers.ListPartsResultUnmarshaller());
    }

    @Override
    public void abortMultipartUpload(AbortMultipartUploadRequest abortMultipartUploadRequest)
            throws CosClientException, CosServiceException {
        rejectNull(abortMultipartUploadRequest,
                "The request parameter must be specified when aborting a multipart upload");
        rejectNull(abortMultipartUploadRequest.getBucketName(),
                "The bucket name parameter must be specified when aborting a multipart upload");
        rejectNull(abortMultipartUploadRequest.getKey(),
                "The key parameter must be specified when aborting a multipart upload");
        rejectNull(abortMultipartUploadRequest.getUploadId(),
                "The upload ID parameter must be specified when aborting a multipart upload");

        String bucketName = abortMultipartUploadRequest.getBucketName();
        String key = abortMultipartUploadRequest.getKey();

        CosHttpRequest<AbortMultipartUploadRequest> request =
                createRequest(bucketName, key, abortMultipartUploadRequest, HttpMethodName.DELETE);
        request.addParameter("uploadId", abortMultipartUploadRequest.getUploadId());

        invoke(request, voidCosResponseHandler);

    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(
            CompleteMultipartUploadRequest completeMultipartUploadRequest)
                    throws CosClientException, CosServiceException {
        rejectNull(completeMultipartUploadRequest,
                "The request parameter must be specified when completing a multipart upload");

        String bucketName = completeMultipartUploadRequest.getBucketName();
        String key = completeMultipartUploadRequest.getKey();
        String uploadId = completeMultipartUploadRequest.getUploadId();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when completing a multipart upload");
        rejectNull(key, "The key parameter must be specified when completing a multipart upload");
        rejectNull(uploadId,
                "The upload ID parameter must be specified when completing a multipart upload");
        rejectNull(completeMultipartUploadRequest.getPartETags(),
                "The part ETags parameter must be specified when completing a multipart upload");

        int retries = 0;
        CompleteMultipartUploadHandler handler;
        do {
            CosHttpRequest<CompleteMultipartUploadRequest> request = createRequest(bucketName, key,
                    completeMultipartUploadRequest, HttpMethodName.POST);
            request.addParameter("uploadId", uploadId);

            byte[] xml = RequestXmlFactory
                    .convertToXmlByteArray(completeMultipartUploadRequest.getPartETags());

            request.addHeader("Content-Type", "text/plain");
            request.addHeader("Content-Length", String.valueOf(xml.length));

            request.setContent(new ByteArrayInputStream(xml));

            @SuppressWarnings("unchecked")
            ResponseHeaderHandlerChain<CompleteMultipartUploadHandler> responseHandler =
                    new ResponseHeaderHandlerChain<CompleteMultipartUploadHandler>(
                            // xml payload unmarshaller
                            new Unmarshallers.CompleteMultipartUploadResultUnmarshaller(),
                            // header handlers
                            new ServerSideEncryptionHeaderHandler<CompleteMultipartUploadHandler>(),
                            new ObjectExpirationHeaderHandler<CompleteMultipartUploadHandler>());
            handler = invoke(request, responseHandler);
            if (handler.getCompleteMultipartUploadResult() != null) {
                String versionId = responseHandler.getResponseHeaders().get(Headers.COS_VERSION_ID);
                handler.getCompleteMultipartUploadResult().setVersionId(versionId);
                return handler.getCompleteMultipartUploadResult();
            }
        } while (shouldRetryCompleteMultipartUpload(completeMultipartUploadRequest,
                handler.getCOSException(), retries++));

        throw handler.getCOSException();
    }

    @Override
    public MultipartUploadListing listMultipartUploads(
            ListMultipartUploadsRequest listMultipartUploadsRequest)
                    throws CosClientException, CosServiceException {
        rejectNull(listMultipartUploadsRequest,
                "The request parameter must be specified when listing multipart uploads");

        rejectNull(listMultipartUploadsRequest.getBucketName(),
                "The bucket name parameter must be specified when listing multipart uploads");

        CosHttpRequest<ListMultipartUploadsRequest> request =
                createRequest(listMultipartUploadsRequest.getBucketName(), null,
                        listMultipartUploadsRequest, HttpMethodName.GET);
        request.addParameter("uploads", null);

        if (listMultipartUploadsRequest.getKeyMarker() != null)
            request.addParameter("key-marker", listMultipartUploadsRequest.getKeyMarker());
        if (listMultipartUploadsRequest.getMaxUploads() != null)
            request.addParameter("max-uploads",
                    listMultipartUploadsRequest.getMaxUploads().toString());
        if (listMultipartUploadsRequest.getUploadIdMarker() != null)
            request.addParameter("upload-id-marker",
                    listMultipartUploadsRequest.getUploadIdMarker());
        if (listMultipartUploadsRequest.getDelimiter() != null)
            request.addParameter("delimiter", listMultipartUploadsRequest.getDelimiter());
        if (listMultipartUploadsRequest.getPrefix() != null)
            request.addParameter("prefix", listMultipartUploadsRequest.getPrefix());
        if (listMultipartUploadsRequest.getEncodingType() != null)
            request.addParameter("encoding-type", listMultipartUploadsRequest.getEncodingType());

        return invoke(request, new Unmarshallers.ListMultipartUploadsResultUnmarshaller());
    }

    @Override
    public ObjectListing listObjects(String bucketName)
            throws CosClientException, CosServiceException {
        return listObjects(new ListObjectsRequest(bucketName, null, null, null, null));
    }

    @Override
    public ObjectListing listObjects(String bucketName, String prefix)
            throws CosClientException, CosServiceException {
        return listObjects(new ListObjectsRequest(bucketName, prefix, null, null, null));
    }

    @Override
    public ObjectListing listObjects(ListObjectsRequest listObjectsRequest)
            throws CosClientException, CosServiceException {
        rejectNull(listObjectsRequest.getBucketName(),
                "The bucket name parameter must be specified when listing objects in a bucket");

        CosHttpRequest<ListObjectsRequest> request = createRequest(
                listObjectsRequest.getBucketName(), "/", listObjectsRequest, HttpMethodName.GET);
        if (listObjectsRequest.getPrefix() != null)
            request.addParameter("prefix", listObjectsRequest.getPrefix());
        if (listObjectsRequest.getMarker() != null)
            request.addParameter("marker", listObjectsRequest.getMarker());
        if (listObjectsRequest.getDelimiter() != null)
            request.addParameter("delimiter", listObjectsRequest.getDelimiter());
        if (listObjectsRequest.getMaxKeys() != null
                && listObjectsRequest.getMaxKeys().intValue() >= 0)
            request.addParameter("max-keys", listObjectsRequest.getMaxKeys().toString());
        if (listObjectsRequest.getEncodingType() != null)
            request.addParameter("encoding-type", listObjectsRequest.getEncodingType());

        return invoke(request, new Unmarshallers.ListObjectsUnmarshaller());
    }

    @Override
    public ObjectListing listNextBatchOfObjects(ObjectListing previousObjectListing)
            throws CosClientException, CosServiceException {
        return listNextBatchOfObjects(new ListNextBatchOfObjectsRequest(previousObjectListing));
    }

    @Override
    public ObjectListing listNextBatchOfObjects(
            ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest)
                    throws CosClientException, CosServiceException {
        rejectNull(listNextBatchOfObjectsRequest,
                "The request object parameter must be specified when listing the next batch of objects in a bucket");
        ObjectListing previousObjectListing =
                listNextBatchOfObjectsRequest.getPreviousObjectListing();

        if (!previousObjectListing.isTruncated()) {
            ObjectListing emptyListing = new ObjectListing();
            emptyListing.setBucketName(previousObjectListing.getBucketName());
            emptyListing.setDelimiter(previousObjectListing.getDelimiter());
            emptyListing.setMarker(previousObjectListing.getNextMarker());
            emptyListing.setMaxKeys(previousObjectListing.getMaxKeys());
            emptyListing.setPrefix(previousObjectListing.getPrefix());
            emptyListing.setEncodingType(previousObjectListing.getEncodingType());
            emptyListing.setTruncated(false);

            return emptyListing;
        }
        return listObjects(listNextBatchOfObjectsRequest.toListObjectsRequest());
    }

    @Override
    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest)
            throws CosClientException, CosServiceException {
        rejectNull(copyObjectRequest.getSourceBucketName(),
                "The source bucket name must be specified when copying an object");
        rejectNull(copyObjectRequest.getSourceKey(),
                "The source object key must be specified when copying an object");
        rejectNull(copyObjectRequest.getDestinationBucketName(),
                "The destination bucket name must be specified when copying an object");
        rejectNull(copyObjectRequest.getDestinationKey(),
                "The destination object key must be specified when copying an object");

        String destinationKey = copyObjectRequest.getDestinationKey();
        String destinationBucketName = copyObjectRequest.getDestinationBucketName();

        CosHttpRequest<CopyObjectRequest> request = createRequest(destinationBucketName,
                destinationKey, copyObjectRequest, HttpMethodName.PUT);

        populateRequestWithCopyObjectParameters(request, copyObjectRequest);

        /*
         * We can't send a non-zero length Content-Length header if the user specified it, otherwise
         * it messes up the HTTP connection when the remote server thinks there's more data to pull.
         */
        setZeroContentLength(request);
        CopyObjectResultHandler copyObjectResultHandler = null;
        try {
            @SuppressWarnings("unchecked")
            ResponseHeaderHandlerChain<CopyObjectResultHandler> handler =
                    new ResponseHeaderHandlerChain<CopyObjectResultHandler>(
                            // xml payload unmarshaller
                            new Unmarshallers.CopyObjectUnmarshaller(),
                            // header handlers
                            new ServerSideEncryptionHeaderHandler<CopyObjectResultHandler>(),
                            new COSVersionHeaderHandler(),
                            new ObjectExpirationHeaderHandler<CopyObjectResultHandler>());
            copyObjectResultHandler = invoke(request, handler);
        } catch (CosServiceException cse) {
            /*
             * If the request failed because one of the specified constraints was not met (ex:
             * matching ETag, modified since date, etc.), then return null, so that users don't have
             * to wrap their code in try/catch blocks and check for this status code if they want to
             * use constraints.
             */
            if (cse.getStatusCode() == Constants.FAILED_PRECONDITION_STATUS_CODE) {
                return null;
            }

            throw cse;
        }

        /*
         * CopyObject has two failure modes: 1 - An HTTP error code is returned and the error is
         * processed like any other error response. 2 - An HTTP 200 OK code is returned, but the
         * response content contains an XML error response.
         *
         * This makes it very difficult for the client runtime to cleanly detect this case and
         * handle it like any other error response. We could extend the runtime to have a more
         * flexible/customizable definition of success/error (per request), but it's probably
         * overkill for this one special case.
         */
        if (copyObjectResultHandler.getErrorCode() != null) {
            String errorCode = copyObjectResultHandler.getErrorCode();
            String errorMessage = copyObjectResultHandler.getErrorMessage();
            String requestId = copyObjectResultHandler.getErrorRequestId();

            CosServiceException cse = new CosServiceException(errorMessage);
            cse.setErrorCode(errorCode);
            cse.setRequestId(requestId);
            cse.setStatusCode(200);

            throw cse;
        }

        CopyObjectResult copyObjectResult = new CopyObjectResult();
        copyObjectResult.setETag(copyObjectResultHandler.getETag());
        copyObjectResult.setLastModifiedDate(copyObjectResultHandler.getLastModified());
        copyObjectResult.setVersionId(copyObjectResultHandler.getVersionId());
        copyObjectResult.setSSEAlgorithm(copyObjectResultHandler.getSSEAlgorithm());
        copyObjectResult.setSSECustomerAlgorithm(copyObjectResultHandler.getSSECustomerAlgorithm());
        copyObjectResult.setSSECustomerKeyMd5(copyObjectResultHandler.getSSECustomerKeyMd5());
        copyObjectResult.setExpirationTime(copyObjectResultHandler.getExpirationTime());
        copyObjectResult.setExpirationTimeRuleId(copyObjectResultHandler.getExpirationTimeRuleId());

        return copyObjectResult;
    }

}
