/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.

 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos;

import static com.qcloud.cos.internal.LengthCheckInputStream.EXCLUDE_SKIPPED_BYTES;
import static com.qcloud.cos.internal.LengthCheckInputStream.INCLUDE_SKIPPED_BYTES;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSCredentialsProvider;
import com.qcloud.cos.auth.COSSessionCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.auth.COSStaticCredentialsProvider;
import com.qcloud.cos.endpoint.CIPicRegionEndpointBuilder;
import com.qcloud.cos.endpoint.CIRegionEndpointBuilder;
import com.qcloud.cos.endpoint.EndpointBuilder;
import com.qcloud.cos.endpoint.RegionEndpointBuilder;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.CosServiceException.ErrorType;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.exception.Throwables;
import com.qcloud.cos.http.CosHttpClient;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.DefaultCosHttpClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.http.HttpResponseHandler;
import com.qcloud.cos.internal.*;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CompleteMultipartUploadHandler;
import com.qcloud.cos.internal.XmlResponsesSaxParser.CopyObjectResultHandler;
import com.qcloud.cos.model.*;
import com.qcloud.cos.model.IntelligentTiering.BucketIntelligentTieringConfiguration;
import com.qcloud.cos.model.bucketcertificate.BucketDomainCertificateRequest;
import com.qcloud.cos.model.bucketcertificate.BucketGetDomainCertificate;
import com.qcloud.cos.model.bucketcertificate.BucketPutDomainCertificate;
import com.qcloud.cos.model.bucketcertificate.SetBucketDomainCertificateRequest;
import com.qcloud.cos.model.bucketcertificate.BucketDomainCertificateParameters;
import com.qcloud.cos.model.ciModel.ai.*;
import com.qcloud.cos.model.ciModel.auditing.*;
import com.qcloud.cos.model.ciModel.bucket.DocBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketResponse;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketResponse;
import com.qcloud.cos.model.ciModel.common.CImageProcessRequest;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.hls.*;
import com.qcloud.cos.model.ciModel.image.AIImageColoringRequest;
import com.qcloud.cos.model.ciModel.image.AutoTranslationBlockRequest;
import com.qcloud.cos.model.ciModel.image.AutoTranslationBlockResponse;
import com.qcloud.cos.model.ciModel.image.DetectFaceRequest;
import com.qcloud.cos.model.ciModel.image.DetectFaceResponse;
import com.qcloud.cos.model.ciModel.image.GenerateQrcodeRequest;
import com.qcloud.cos.model.ciModel.image.ImageInspectRequest;
import com.qcloud.cos.model.ciModel.image.ImageInspectResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Request;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Response;
import com.qcloud.cos.model.ciModel.image.ImageSearchRequest;
import com.qcloud.cos.model.ciModel.image.ImageSearchResponse;
import com.qcloud.cos.model.ciModel.image.ImageStyleRequest;
import com.qcloud.cos.model.ciModel.image.ImageStyleResponse;
import com.qcloud.cos.model.ciModel.image.OpenImageSearchRequest;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.v2.*;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.metaInsight.*;
import com.qcloud.cos.model.ciModel.persistence.AIGameRecResponse;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.AIRecRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectCarResponse;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.DocQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaQueueResponse;
import com.qcloud.cos.model.ciModel.snapshot.CosSnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Request;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Response;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import com.qcloud.cos.model.ciModel.template.MediaListTemplateResponse;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaTemplateResponse;
import com.qcloud.cos.model.ciModel.utils.CICheckUtils;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionsResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListRequest;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
import com.qcloud.cos.model.ciModel.xml.CIAuditingXmlFactory;
import com.qcloud.cos.model.ciModel.xml.CIAuditingXmlFactoryV2;
import com.qcloud.cos.model.ciModel.xml.CIMediaXmlFactory;
import com.qcloud.cos.model.ciModel.xml.CImageXmlFactory;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskRequest;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskResult;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskResultHandler;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskRequest;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskResult;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskResultHandler;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskSerializer;
import com.qcloud.cos.model.inventory.InventoryConfiguration;
import com.qcloud.cos.model.inventory.PostBucketInventoryConfigurationResult;
import com.qcloud.cos.model.transform.ObjectTaggingXmlFactory;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.retry.RetryUtils;
import com.qcloud.cos.utils.*;

import com.qcloud.cos.utils.Base64;
import org.apache.commons.codec.DecoderException;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class COSClient implements COS {

    private static final Logger log = LoggerFactory.getLogger(COSClient.class);

    private final SkipMd5CheckStrategy skipMd5CheckStrategy = SkipMd5CheckStrategy.INSTANCE;
    private final VoidCosResponseHandler voidCosResponseHandler = new VoidCosResponseHandler();

    private volatile COSCredentialsProvider credProvider;

    protected ClientConfig clientConfig;

    private CosHttpClient cosHttpClient;

    private static ConcurrentHashMap<String, Long> preflightBuckets = new ConcurrentHashMap<>();

    public COSClient(COSCredentials cred, ClientConfig clientConfig) {
        this(new COSStaticCredentialsProvider(cred), clientConfig);
    }

    public COSClient(COSCredentialsProvider credProvider, ClientConfig clientConfig) {
        super();
        this.credProvider = credProvider;
        this.clientConfig = clientConfig;
        this.cosHttpClient = new DefaultCosHttpClient(clientConfig);
    }

    public void shutdown() {
        this.cosHttpClient.shutdown();
    }

    public void setCOSCredentials(COSCredentials cred) {
        rejectNull(cred, "cred must not be null");
        this.credProvider = new COSStaticCredentialsProvider(cred);
    }

    public void setCOSCredentialsProvider(COSCredentialsProvider credProvider) {
        rejectNull(credProvider, "credProvider must not be null");
        this.credProvider = credProvider;
    }


    @Override
    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    private COSCredentials fetchCredential() throws CosClientException {
        if (credProvider == null) {
            throw new CosClientException(
                    "credentials Provider is null, you must set legal Credentials info when init cosClient.");
        }

        COSCredentials cred = credProvider.getCredentials();
        if (cred == null) {
            throw new CosClientException(
                    "credentials from Provider is null. please check your credentials provider");
        }
        return cred;
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

    private void rejectEmpty(String parameterValue, String errorMessage) {
        if (parameterValue.isEmpty())
            throw new IllegalArgumentException(errorMessage);
    }

    private void rejectEmpty(Map parameterValue, String errorMessage) {
        if (parameterValue.isEmpty())
            throw new IllegalArgumentException(errorMessage);
    }

    private void rejectStartWith(String value, String startStr, String errorMessage) {
        if (value != null && !value.isEmpty() && startStr != null) {
            if (!value.startsWith(startStr))
                throw new IllegalArgumentException(errorMessage);
        }
    }

    protected <X extends CosServiceRequest> CosHttpRequest<X> createRequest(String bucketName,
            String key, X originalRequest, HttpMethodName httpMethod) {
        CosHttpRequest<X> httpRequest = new CosHttpRequest<X>(originalRequest);
        httpRequest.setHttpMethod(httpMethod);
        httpRequest.addHeader(Headers.USER_AGENT, clientConfig.getUserAgent());
        if (originalRequest.getCustomRequestHeaders() != null && originalRequest.getCustomRequestHeaders().containsKey("Pic-Operations")) {
            httpRequest.addHeader("Pic-Operations", originalRequest.getCustomRequestHeaders().get("Pic-Operations"));
        }
        if (originalRequest instanceof ListBucketsRequest) {
            buildUrlAndHost(httpRequest, bucketName, key, true);
        } else {
            rejectNull(clientConfig.getRegion(),
                    "region is missing, you must set region when init clientConfig for the api.");
            buildUrlAndHost(httpRequest, bucketName, key, false);
        }
        httpRequest.setProgressListener(originalRequest.getGeneralProgressListener());
        httpRequest.setBucketName(bucketName);
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
        EndpointBuilder srcEndpointBuilder = null;
        // 如果用户没有设置源region, 则默认和目的region一致
        if (sourceRegion == null) {
            srcEndpointBuilder = this.clientConfig.getEndpointBuilder();
        } else {
            srcEndpointBuilder = new RegionEndpointBuilder(sourceRegion);
        }
        if (copyObjectRequest.getSourceEndpointBuilder() != null) {
            srcEndpointBuilder = copyObjectRequest.getSourceEndpointBuilder();
        }

        String sourceKey = formatKey(copyObjectRequest.getSourceKey());
        String sourceBucket =
                formatBucket(copyObjectRequest.getSourceBucketName(),
                        (copyObjectRequest.getSourceAppid() != null)
                                ? copyObjectRequest.getSourceAppid()
                                : fetchCredential().getCOSAppId());
        String copySourceHeader =
                String.format("%s%s", srcEndpointBuilder.buildGeneralApiEndpoint(sourceBucket),
                        UrlEncoderUtils.encodeEscapeDelimiter(sourceKey));
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

        if(copyObjectRequest.getMetadataDirective() != null) {
            request.addHeader(Headers.METADATA_DIRECTIVE, copyObjectRequest.getMetadataDirective());
        } else if (newObjectMetadata != null) {
            request.addHeader(Headers.METADATA_DIRECTIVE, "REPLACE");
        }

        if(newObjectMetadata != null) {
            populateRequestMetadata(request, newObjectMetadata);
        }

        // Populate the SSE-C parameters for the source and destination object
        populateSSE_C(request, copyObjectRequest.getDestinationSSECustomerKey());
        populateSourceSSE_C(request, copyObjectRequest.getSourceSSECustomerKey());
        populateSSE_KMS(request, copyObjectRequest.getSSECOSKeyManagementParams());
    }

    private void populateRequestWithCopyPartParameters(
            CosHttpRequest<? extends CosServiceRequest> request, CopyPartRequest copyPartRequest) {
        Region sourceRegion = copyPartRequest.getSourceBucketRegion();
        EndpointBuilder srcEndpointBuilder = null;
        // 如果用户没有设置源region, 则默认和目的region一致
        if (sourceRegion == null) {
            srcEndpointBuilder = this.clientConfig.getEndpointBuilder();
        } else {
            srcEndpointBuilder = new RegionEndpointBuilder(sourceRegion);
        }
        if (copyPartRequest.getSourceEndpointBuilder() != null) {
            srcEndpointBuilder = copyPartRequest.getSourceEndpointBuilder();
        }
        String sourceKey = formatKey(copyPartRequest.getSourceKey());

        String sourceBucket =
                formatBucket(copyPartRequest.getSourceBucketName(),
                        (copyPartRequest.getSourceAppid() != null)
                                ? copyPartRequest.getSourceAppid()
                                : fetchCredential().getCOSAppId());
        String copySourceHeader =
                String.format("%s%s", srcEndpointBuilder.buildGeneralApiEndpoint(sourceBucket),
                        UrlEncoderUtils.encodeEscapeDelimiter(sourceKey));
        if (copyPartRequest.getSourceVersionId() != null) {
            copySourceHeader += "?versionId=" + copyPartRequest.getSourceVersionId();
        }
        request.addHeader("x-cos-copy-source", copySourceHeader);

        addDateHeader(request, Headers.COPY_SOURCE_IF_MODIFIED_SINCE,
                copyPartRequest.getModifiedSinceConstraint());
        addDateHeader(request, Headers.COPY_SOURCE_IF_UNMODIFIED_SINCE,
                copyPartRequest.getUnmodifiedSinceConstraint());

        addStringListHeader(request, Headers.COPY_SOURCE_IF_MATCH,
                copyPartRequest.getMatchingETagConstraints());
        addStringListHeader(request, Headers.COPY_SOURCE_IF_NO_MATCH,
                copyPartRequest.getNonmatchingETagConstraints());

        if (copyPartRequest.getFirstByte() != null && copyPartRequest.getLastByte() != null) {
            String range =
                    "bytes=" + copyPartRequest.getFirstByte() + "-" + copyPartRequest.getLastByte();
            request.addHeader(Headers.COPY_PART_RANGE, range);
        }

        // Populate the SSE-C parameters for the source and destination object
        populateSSE_C(request, copyPartRequest.getDestinationSSECustomerKey());
        populateSourceSSE_C(request, copyPartRequest.getSourceSSECustomerKey());

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

    // 格式化一些路径, 去掉开始时的分隔符/, 比如list prefix.
    // 因为COS V4的prefix是以/开始的,这里SDK需要坐下兼容
    private String leftStripPathDelimiter(String path) {
        if (path == null) {
            return path;
        }
        while (path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

    // 格式化bucket, 是bucket返回带appid
    private String formatBucket(String bucketName, String appid) throws CosClientException {
        BucketNameUtils.validateBucketName(bucketName);
        if (appid == null) {
            if (!bucketName.trim().isEmpty()) {
                return bucketName;
            } else {
                throw new CosClientException(
                        "please make sure bucket name must contain legal appid when appid is missing. example: music-1251122334");
            }
        }

        String appidSuffix = "-" + appid;
        if (bucketName.endsWith(appidSuffix)) {
            return bucketName;
        } else {
            return bucketName + appidSuffix;
        }
    }

    private <X extends CosServiceRequest> void buildUrlAndHost(CosHttpRequest<X> request,
                                                               String bucket, String key, boolean isServiceRequest) throws CosClientException {
        boolean isCIRequest = request.getOriginalRequest() instanceof CIServiceRequest;
        key = formatKey(key);
        request.setResourcePath(key);
        String endpoint = "";
        String endpointAddr = "";
        if (isServiceRequest) {
            endpoint = clientConfig.getEndpointBuilder().buildGetServiceApiEndpoint();
            endpointAddr =
                    clientConfig.getEndpointResolver().resolveGetServiceApiEndpoint(endpoint);
        } else {
            bucket = formatBucket(bucket, fetchCredential().getCOSAppId());
            if (request.getOriginalRequest() instanceof CIPicServiceRequest) {
                endpoint = new CIPicRegionEndpointBuilder(clientConfig.getRegion()).buildGeneralApiEndpoint(bucket);
            } else if (isCIRequest) {
                endpoint = new CIRegionEndpointBuilder(clientConfig.getRegion()).buildGeneralApiEndpoint(bucket);
            } else {
                endpoint = clientConfig.getEndpointBuilder().buildGeneralApiEndpoint(bucket);
            }
            endpointAddr = clientConfig.getEndpointResolver().resolveGeneralApiEndpoint(endpoint);
        }

        if (endpoint == null) {
            throw new CosClientException("endpoint is null, please check your endpoint builder");
        }
        if (endpointAddr == null) {
            throw new CosClientException(
                    "endpointAddr is null, please check your endpoint resolver");
        }

        if (clientConfig.getIsDistinguishHost()) {
            String host = String.format("%s.%s.myqcloud.com", bucket, Region.formatRegion(clientConfig.getRegion()));
            request.addHeader(Headers.HOST,host);
        }else{
            request.addHeader(Headers.HOST, endpoint);
        }

        if (isCIRequest && !clientConfig.getCiSpecialRequest()) {
            //万象请求只支持https
            request.setProtocol(HttpProtocol.https);
        } else {
            request.setProtocol(clientConfig.getHttpProtocol());
        }
        String fixedEndpointAddr = request.getOriginalRequest().getFixedEndpointAddr();
        if (fixedEndpointAddr != null) {
            request.setEndpoint(fixedEndpointAddr);
        } else {
            request.setEndpoint(endpointAddr);
        }

        request.setResourcePath(key);
    }

    private <X, Y extends CosServiceRequest> X invoke(CosHttpRequest<Y> request,
            Unmarshaller<X, InputStream> unmarshaller)
            throws CosClientException, CosServiceException {
        return invoke(request, new COSXmlResponseHandler<X>(unmarshaller));
    }

    private <X, Y extends CosServiceRequest> X invoke(CosHttpRequest<Y> request,
            HttpResponseHandler<CosServiceResponse<X>> responseHandler)
            throws CosClientException, CosServiceException {

        COSSigner cosSigner = clientConfig.getCosSigner();
        COSCredentials cosCredentials;
        CosServiceRequest cosServiceRequest = request.getOriginalRequest();
        if(cosServiceRequest != null && cosServiceRequest.getCosCredentials() != null) {
            cosCredentials = cosServiceRequest.getCosCredentials();
            request.setCosCredentials(cosCredentials);
        } else {
            cosCredentials = fetchCredential();
            request.setCosCredentials(cosCredentials);
        }
        Date expiredTime = new Date(System.currentTimeMillis() + clientConfig.getSignExpired() * 1000);
        boolean isCIWorkflowRequest = cosServiceRequest instanceof  CIWorkflowServiceRequest;
        cosSigner.setCIWorkflowRequest(isCIWorkflowRequest);
        cosSigner.sign(request, cosCredentials, expiredTime);
        return this.cosHttpClient.exeute(request, responseHandler);
    }

    private static PutObjectResult createPutObjectResult(ObjectMetadata metadata) {
        final PutObjectResult result = new PutObjectResult();
        result.setRequestId((String) metadata.getRawMetadataValue(Headers.REQUEST_ID));
        result.setDateStr((String) metadata.getRawMetadataValue(Headers.DATE));
        result.setVersionId(metadata.getVersionId());
        result.setETag(metadata.getETag());
        result.setExpirationTime(metadata.getExpirationTime());
        result.setSSEAlgorithm(metadata.getSSEAlgorithm());
        result.setSSECustomerAlgorithm(metadata.getSSECustomerAlgorithm());
        result.setSSECustomerKeyMd5(metadata.getSSECustomerKeyMd5());
        result.setCrc64Ecma(metadata.getCrc64Ecma());
        result.setCrc32c(metadata.getCrc32c());
        result.setMetadata(metadata);
        result.setCiUploadResult(metadata.getCiUploadResult());
        return result;
    }

    private static AppendObjectResult createAppendObjectResult(ObjectMetadata metadata) {
        final AppendObjectResult result = new AppendObjectResult();
        result.setNextAppendPosition(Long.valueOf(
                (String)metadata.getRawMetadataValue(Headers.APPEND_OBJECT_NEXT_POSISTION)));
        result.setMetadata(metadata);
        return result;
    }


    /**
     * Adds the specified parameter to the specified request, if the parameter value is not null.
     *
     * @param request The request to add the parameter to.
     * @param paramName The parameter name.
     * @param paramValue The parameter value.
     */
    private static void addParameterIfNotNull(CosHttpRequest<?> request, String paramName,
                                              String paramValue) {
        if (paramValue != null) {
            request.addParameter(paramName, paramValue);
        }
    }

    private static void addParameterIfNotNull(CosHttpRequest<?> request, String paramName,
                                              Integer value) {
        if (value != null) {
            request.addParameter(paramName, String.valueOf(value));
        }
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

    private boolean shouldRetryCompleteMultipartUpload(CosServiceRequest originalRequest,
            CosClientException exception, int retriesAttempted) {
        return false;
    }

    /**
     * <p>
     * Adds response headers parameters to the request given, if non-null.
     * </p>
     *
     * @param request The request to add the response header parameters to.
     * @param responseHeaders The full set of response headers to add, or null for none.
     */
    private static void addResponseHeaderParameters(CosHttpRequest<?> request,
            ResponseHeaderOverrides responseHeaders) {
        if (responseHeaders != null) {
            if (responseHeaders.getCacheControl() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CACHE_CONTROL,
                        responseHeaders.getCacheControl());
            }
            if (responseHeaders.getContentDisposition() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_DISPOSITION,
                        responseHeaders.getContentDisposition());
            }
            if (responseHeaders.getContentEncoding() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_ENCODING,
                        responseHeaders.getContentEncoding());
            }
            if (responseHeaders.getContentLanguage() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_LANGUAGE,
                        responseHeaders.getContentLanguage());
            }
            if (responseHeaders.getContentType() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_CONTENT_TYPE,
                        responseHeaders.getContentType());
            }
            if (responseHeaders.getExpires() != null) {
                request.addParameter(ResponseHeaderOverrides.RESPONSE_HEADER_EXPIRES,
                        responseHeaders.getExpires());
            }
        }
    }

    /**
     * <p>
     * Populates the specified request with the numerous attributes available in
     * <code>SSEWithCustomerKeyRequest</code>.
     * </p>
     *
     * @param request The request to populate with headers to represent all the options expressed in
     *        the <code>ServerSideEncryptionWithCustomerKeyRequest</code> object.
     * @param sseKey The request object for an COS operation that allows server-side encryption
     *        using customer-provided keys.
     */
    private static void populateSSE_C(CosHttpRequest<?> request, SSECustomerKey sseKey) {
        if (sseKey == null)
            return;

        addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM,
                sseKey.getAlgorithm());
        addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY, sseKey.getKey());
        addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5,
                sseKey.getMd5());
        // Calculate the MD5 hash of the encryption key and fill it in the
        // header, if the user didn't specify it in the metadata
        if (sseKey.getKey() != null && sseKey.getMd5() == null) {
            String encryptionKey_b64 = sseKey.getKey();
            byte[] encryptionKey = Base64.decode(encryptionKey_b64);
            request.addHeader(Headers.SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5,
                    Md5Utils.md5AsBase64(encryptionKey));
        }
    }

    private static void populateTrafficLimit(CosHttpRequest<?> request, int trafficLimit) {
        if(trafficLimit > 0) {
            request.addHeader(Headers.COS_TRAFFIC_LIMIT, String.valueOf(trafficLimit));
        }
    }

    private static void populateSourceSSE_C(CosHttpRequest<?> request, SSECustomerKey sseKey) {
        if (sseKey == null)
            return;

        // Populate the SSE-C parameters for the source object
        addHeaderIfNotNull(request, Headers.COPY_SOURCE_SERVER_SIDE_ENCRYPTION_CUSTOMER_ALGORITHM,
                sseKey.getAlgorithm());
        addHeaderIfNotNull(request, Headers.COPY_SOURCE_SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY,
                sseKey.getKey());
        addHeaderIfNotNull(request, Headers.COPY_SOURCE_SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5,
                sseKey.getMd5());
        // Calculate the MD5 hash of the encryption key and fill it in the
        // header, if the user didn't specify it in the metadata
        if (sseKey.getKey() != null && sseKey.getMd5() == null) {
            String encryptionKey_b64 = sseKey.getKey();
            byte[] encryptionKey = Base64.decode(encryptionKey_b64);
            request.addHeader(Headers.COPY_SOURCE_SERVER_SIDE_ENCRYPTION_CUSTOMER_KEY_MD5,
                    Md5Utils.md5AsBase64(encryptionKey));
        }
    }

    private static void populateSSE_KMS(CosHttpRequest<?> request,
            SSECOSKeyManagementParams sseParams) {
        if (sseParams != null) {
            addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION, sseParams.getEncryption());
            addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION_COS_KMS_KEY_ID,
                    sseParams.getCOSKmsKeyId());
            addHeaderIfNotNull(request, Headers.SERVER_SIDE_ENCRYPTION_CONTEXT,
                    sseParams.getEncryptionContext());
        }
    }

    @Override
    public PutObjectResult putObject(PutObjectRequest putObjectRequest)
            throws CosClientException, CosServiceException {
        ObjectMetadata returnedMetadata = uploadObjectInternal(UploadMode.PUT_OBJECT, putObjectRequest);
        PutObjectResult result = createPutObjectResult(returnedMetadata);
        result.setContentMd5(returnedMetadata.getETag());
        return result;
    }

    @Override
    public AppendObjectResult appendObject(AppendObjectRequest appendObjectRequest)
            throws CosServiceException, CosClientException {
        rejectNull(appendObjectRequest, "The append object request must be specified");
        rejectNull(appendObjectRequest.getPosition(), "The position parameter must be specified");
        ObjectMetadata returnedMetadata = uploadObjectInternal(UploadMode.APPEND_OBJECT, appendObjectRequest);
        return createAppendObjectResult(returnedMetadata);
    }

    @Override
    public void rename(RenameRequest renameRequest)
            throws CosServiceException, CosClientException {
        rejectNull(renameRequest, "The request must not be null");
        rejectNull(renameRequest.getBucketName(), "The bucket name parameter must be specified when rename");
        rejectNull(renameRequest.getSrcObject(), "The src object parameter must be specified when rename");
        rejectNull(renameRequest.getDstObject(), "The dst object parameter must be specified when rename");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when rename");
        rejectEmpty(renameRequest.getSrcObject(), "The length of the src key must be greater than 0");
        rejectEmpty(renameRequest.getDstObject(), "The length of the dst key must be greater than 0");

        String srcInodeId = "";
        if (clientConfig.isRenameFaultTolerant()) {
            srcInodeId = getObjectInodeId(renameRequest.getBucketName(), renameRequest.getSrcObject(), renameRequest.getFixedEndpointAddr(),
                    renameRequest.getCustomRequestHeaders());
        }

        CosHttpRequest<RenameRequest> request = createRequest(renameRequest.getBucketName(),
                renameRequest.getDstObject(), renameRequest, HttpMethodName.PUT);
        request.addParameter("rename", null);
        request.addHeader("x-cos-rename-source", UrlEncoderUtils.encodeEscapeDelimiter(renameRequest.getSrcObject()));
        try {
            invoke(request, voidCosResponseHandler);
        } catch (CosServiceException cse) {
            if (clientConfig.isRenameFaultTolerant() && cse.getStatusCode() == 404 && !srcInodeId.isEmpty()) {
                if (!checkResultForRenameObject(srcInodeId, renameRequest)) {
                    throw cse;
                }
            } else {
                throw cse;
            }
        }
    }

    private boolean checkResultForRenameObject(String srcInodeId, RenameRequest renameRequest) {
        /**
         * try to head src object and dst object, and check their inode ids when catch exception
         * rename request is successful only if src object does not exist and the inode ids of the source object and the target object are the same
         * */
        try {
            getObjectInodeId(renameRequest.getBucketName(), renameRequest.getSrcObject(), renameRequest.getFixedEndpointAddr(), renameRequest.getCustomRequestHeaders());
        } catch (CosServiceException cse) {
            if (cse.getStatusCode() != 404) {
                log.info("catch CosServiceException and status code is not 404 when getting the inode id of the source object, exp: ", cse);
                return false;
            }
        } catch (CosClientException cce) {
            log.info("catch CosClientException when getting the inode id of the source object, exp: ", cce);
            return false;
        }

        String dstInodeId;
        try {
            dstInodeId = getObjectInodeId(renameRequest.getBucketName(), renameRequest.getDstObject(), renameRequest.getFixedEndpointAddr(), renameRequest.getCustomRequestHeaders());
        } catch (Exception e) {
            log.info("catch exception when getting the inode id of the target object, exp: ", e);
            return false;
        }
        return Objects.equals(dstInodeId, srcInodeId);
    }

    private String getObjectInodeId(String bucketName, String key, String fixedEndpointAddr, Map<String, String> customHeaders)
            throws CosServiceException, CosClientException {
        GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest(bucketName, key);
        if (fixedEndpointAddr != null && !fixedEndpointAddr.isEmpty()) {
            getObjectMetadataRequest.setFixedEndpointAddr(fixedEndpointAddr);
        }

        if (customHeaders != null) {
            for (Map.Entry<String, String> e : customHeaders.entrySet()) {
                getObjectMetadataRequest.putCustomRequestHeader(e.getKey(), e.getValue());
            }
        }

        ObjectMetadata objectMetadata = getObjectMetadata(getObjectMetadataRequest);
        return objectMetadata.getInodeId() == null ? "" : objectMetadata.getInodeId();
    }

    protected <UploadObjectRequest extends PutObjectRequest>
        ObjectMetadata uploadObjectInternal(UploadMode uploadMode, UploadObjectRequest uploadObjectRequest)
            throws CosClientException, CosServiceException {
        rejectNull(uploadObjectRequest,
                "The PutObjectRequest parameter must be specified when uploading an object");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when uploading an object");

        final File file = uploadObjectRequest.getFile();
        final InputStream isOrig = uploadObjectRequest.getInputStream();
        final String bucketName = uploadObjectRequest.getBucketName();
        final String key = uploadObjectRequest.getKey();
        ObjectMetadata metadata = uploadObjectRequest.getMetadata();
        InputStream input = isOrig;
        if (metadata == null)
            metadata = new ObjectMetadata();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when uploading an object");
        rejectNull(key, "The key parameter must be specified when uploading an object");

        try {
            preflightObj(uploadObjectRequest);
        } catch (CosServiceException cse) {
            String msg = String.format("fail to do the preflight request due to the service exception[statusCode:%s, requestId:%s], will not do the upload obj request", cse.getStatusCode(), cse.getRequestId());
            log.warn(msg);
            throw cse;
        } catch (CosClientException cce) {
            log.warn("fail to do the preflight request due to the client exception, will not do the upload obj request", cce);
            throw cce;
        }

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
            final long maxAllowdSingleFileSize = 5 * 1024L * 1024L * 1024L;
            if (file.length() > maxAllowdSingleFileSize) {
                throw new CosClientException(
                        "max size 5GB is allowed by putObject Method, your filesize is "
                                + file.length()
                                + ", please use transferManager to upload big file!");
            }
            final boolean calculateMD5 = metadata.getContentMD5() == null;

            if (calculateMD5 && !skipMd5CheckStrategy.skipServerSideValidation(uploadObjectRequest)) {
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
            CosHttpRequest<UploadObjectRequest> request = null;
            if(uploadMode.equals(UploadMode.PUT_OBJECT)) {
                request = createRequest(bucketName, key, uploadObjectRequest, HttpMethodName.PUT);
            } else if(uploadMode.equals(UploadMode.APPEND_OBJECT)){
                request = createRequest(bucketName, key, uploadObjectRequest, HttpMethodName.POST);
                AppendObjectRequest appendObjectRequest = (AppendObjectRequest)uploadObjectRequest;
                String positionStr = String.valueOf(appendObjectRequest.getPosition());
                request.addParameter("append", null);
                request.addParameter("position", positionStr);
            }
            if (uploadObjectRequest.getAccessControlList() != null) {
                addAclHeaders(request, uploadObjectRequest.getAccessControlList());
            } else if (uploadObjectRequest.getCannedAcl() != null) {
                request.addHeader(Headers.COS_CANNED_ACL,
                        uploadObjectRequest.getCannedAcl().toString());
            }

            if (uploadObjectRequest.getStorageClass() != null) {
                request.addHeader(Headers.STORAGE_CLASS, uploadObjectRequest.getStorageClass());
            }

            if (uploadObjectRequest.getRedirectLocation() != null) {
                request.addHeader(Headers.REDIRECT_LOCATION,
                        uploadObjectRequest.getRedirectLocation());
                if (input == null) {
                    input = new ByteArrayInputStream(new byte[0]);
                }
            }

            // Populate the SSE-C parameters to the request header
            populateSSE_C(request, uploadObjectRequest.getSSECustomerKey());

            // Populate the SSE KMS parameters to the request header
            populateSSE_KMS(request, uploadObjectRequest.getSSECOSKeyManagementParams());

            // Populate the traffic limit parameter to the request header
            populateTrafficLimit(request, uploadObjectRequest.getTrafficLimit());
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
                final long maxAllowdSingleFileSize = 5 * 1024L * 1024L * 1024L;
                if (expectedLength > maxAllowdSingleFileSize) {
                    throw new CosClientException(
                            "max size 5GB is allowed by putObject Method, your filesize is "
                                    + expectedLength
                                    + ", please use transferManager to upload big file!");
                }
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
                    && !skipMd5CheckStrategy.skipClientSideValidationPerRequest(uploadObjectRequest)) {
                /*
                 * If the user hasn't set the content MD5, then we don't want to buffer the whole
                 * stream in memory just to calculate it. Instead, we can calculate it on the fly
                 * and validate it with the returned ETag from the object upload.
                 */
                input = md5DigestStream = new MD5DigestCalculatingInputStream(input);
            }

            populateRequestMetadata(request, metadata);
            request.setContent(input);
            try {
                if(uploadObjectRequest.getPicOperations() != null || (uploadObjectRequest.getCustomRequestHeaders() != null && uploadObjectRequest.getCustomRequestHeaders().containsKey(Headers.PIC_OPERATIONS)) ) {
                    if (uploadObjectRequest.getCustomRequestHeaders() == null || !uploadObjectRequest.getCustomRequestHeaders().containsKey(Headers.PIC_OPERATIONS)){
                        request.addHeader(Headers.PIC_OPERATIONS, Jackson.toJsonString(uploadObjectRequest.getPicOperations()));
                    }
                    returnedMetadata = invoke(request, new ResponseHeaderHandlerChain<ObjectMetadata>(
                            new Unmarshallers.ImagePersistenceUnmarshaller(), new CosMetadataResponseHandler()));
                } else {
                    returnedMetadata = invoke(request, new CosMetadataResponseHandler());
                }
            } catch (Throwable t) {
                throw Throwables.failure(t);
            }
        } finally {
            CosDataSource.Utils.cleanupDataSource(uploadObjectRequest, file, isOrig, input, log);
        }

        if (returnedMetadata.isNeedPreflight()) {
            Long currentTime = System.currentTimeMillis();
            if ((preflightBuckets.get(bucketName) == null) || ((currentTime - preflightBuckets.get(bucketName)) > clientConfig.getPreflightStatusUpdateInterval())) {
                String reqMsg = String.format("will update preflight status, bucket[%s]", bucketName);
                log.info(reqMsg);
                preflightBuckets.put(bucketName, currentTime);
            }
        } else {
            Long currentTime = System.currentTimeMillis();
            if ((preflightBuckets.get(bucketName) != null) && ((currentTime - preflightBuckets.get(bucketName)) > clientConfig.getPreflightStatusUpdateInterval())) {
                String reqMsg = String.format("will remove bucket[%s] from preflight lists", bucketName);
                log.info(reqMsg);
                preflightBuckets.remove(bucketName);
            }
        }

        String contentMd5 = metadata.getContentMD5();
        if (md5DigestStream != null) {
            contentMd5 = Base64.encodeAsString(md5DigestStream.getMd5Digest());
        }

        final String etag = returnedMetadata.getETag();
        if (contentMd5 != null && uploadMode.equals(UploadMode.PUT_OBJECT)
                && !skipMd5CheckStrategy.skipClientSideValidationPerPutResponse(returnedMetadata)
                && !CICheckUtils.isCoverImageRequest(uploadObjectRequest)) {
            byte[] clientSideHash = BinaryUtils.fromBase64(contentMd5);
            byte[] serverSideHash = null;
            try {
                serverSideHash = BinaryUtils.fromHex(etag);
            } catch (DecoderException e) {
                throw new CosClientException("Unable to verify integrity of data upload.  "
                        + "Client calculated content hash (contentMD5: " + contentMd5
                        + " in base 64) didn't match hash (etag: " + etag
                        + " in hex) calculated by COS .  "
                        + "You may need to delete the data stored in COS . (metadata.contentMD5: "
                        + metadata.getContentMD5() + ", bucketName: " + bucketName + ", key: " + key
                        + ")");
            }

            if (!Arrays.equals(clientSideHash, serverSideHash)) {
                throw new CosClientException("Unable to verify integrity of data upload.  "
                        + "Client calculated content hash (contentMD5: " + contentMd5
                        + " in base 64) didn't match hash (etag: " + etag
                        + " in hex) calculated by COS .  "
                        + "You may need to delete the data stored in COS . (metadata.contentMD5: "
                        + metadata.getContentMD5() + ", bucketName: " + bucketName + ", key: " + key
                        + ")");
            }
        }
        return returnedMetadata;
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
    public PutObjectResult putObject(String bucketName, String key, String content)
            throws CosClientException, CosServiceException {
        rejectNull(bucketName,
                "The bucket name parameter must be specified when uploading an object");
        rejectNull(key, "The key parameter must be specified when uploading an object");
        rejectNull(content,
                "The content with utf-8 encoding must be specified when uploading an object");

        byte[] contentByteArray = content.getBytes(StringUtils.UTF8);
        String contentMd5 = Md5Utils.md5AsBase64(contentByteArray);

        InputStream contentInput = new ByteArrayInputStream(contentByteArray);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/plain");
        metadata.setContentLength(contentByteArray.length);
        metadata.setContentMD5(contentMd5);

        return putObject(new PutObjectRequest(bucketName, key, contentInput, metadata));
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
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when requesting an object");

        if (clientConfig.isCheckRequestPath()) {
            if (StringUtils.isRequestPathInvalid(getObjectRequest.getKey())) {
                throw new IllegalArgumentException("The key you specified is invalid");
            }
        }

        CosHttpRequest<GetObjectRequest> request = createRequest(getObjectRequest.getBucketName(),
                getObjectRequest.getKey(), getObjectRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "versionId", getObjectRequest.getVersionId());

        // Range
        long[] range = getObjectRequest.getRange();
        if (range != null) {
            if (range[0] == -1) {
                request.addHeader(Headers.RANGE,
                        "bytes=" + "-" + Long.toString(range[1]));
            } else if (range[1] == -1) {
                request.addHeader(Headers.RANGE,
                        "bytes=" + Long.toString(range[0]) + "-");
            } else {
                request.addHeader(Headers.RANGE,
                        "bytes=" + Long.toString(range[0]) + "-" + Long.toString(range[1]));
            }
        }
        addResponseHeaderParameters(request, getObjectRequest.getResponseHeaders());

        addDateHeader(request, Headers.GET_OBJECT_IF_MODIFIED_SINCE,
                getObjectRequest.getModifiedSinceConstraint());
        addDateHeader(request, Headers.GET_OBJECT_IF_UNMODIFIED_SINCE,
                getObjectRequest.getUnmodifiedSinceConstraint());
        addStringListHeader(request, Headers.GET_OBJECT_IF_MATCH,
                getObjectRequest.getMatchingETagConstraints());
        addStringListHeader(request, Headers.GET_OBJECT_IF_NONE_MATCH,
                getObjectRequest.getNonmatchingETagConstraints());

        // Populate the SSE-C parameters to the request header
        populateSSE_C(request, getObjectRequest.getSSECustomerKey());

        // Populate the traffic limit parameter to the request header
        populateTrafficLimit(request, getObjectRequest.getTrafficLimit());
        try {
            COSObject cosObject = invoke(request, new COSObjectResponseHandler());
            cosObject.setBucketName(getObjectRequest.getBucketName());
            cosObject.setKey(getObjectRequest.getKey());
            InputStream is = cosObject.getObjectContent();
            HttpRequestBase httpRequest = cosObject.getObjectContent().getHttpRequest();

            is = new ServiceClientHolderInputStream(is, this);

            // The Etag header contains a server-side MD5 of the object. If
            // we're downloading the whole object, by default we wrap the
            // stream in a validator that calculates an MD5 of the downloaded
            // bytes and complains if what we received doesn't match the Etag.
            if (!skipMd5CheckStrategy.skipClientSideValidation(getObjectRequest,
                    cosObject.getObjectMetadata())) {
                try {
                    byte[] serverSideHash =
                            BinaryUtils.fromHex(cosObject.getObjectMetadata().getETag());
                    // No content length check is performed when the
                    // MD5 check is enabled, since a correct MD5 check would
                    // imply a correct content length.
                    MessageDigest digest = MessageDigest.getInstance("MD5");
                    is = new DigestValidationInputStream(is, digest, serverSideHash);
                } catch (NoSuchAlgorithmException e) {
                    log.warn("No MD5 digest algorithm available.  Unable to calculate "
                            + "checksum and verify data integrity.", e);
                } catch (DecoderException e) {
                    log.warn("BinaryUtils.fromHex error. Unable to calculate "
                            + "checksum and verify data integrity. etag:"
                            + cosObject.getObjectMetadata().getETag(), e);
                }
            } else {
                // Ensures the data received from COS has the same length as the
                // expected content-length
                is = new LengthCheckInputStream(is,
                        cosObject.getObjectMetadata().getContentLength(), // expected length
                        INCLUDE_SKIPPED_BYTES); // bytes received from cos are all included even if
                                                // skipped
            }
            cosObject.setObjectContent(new COSObjectInputStream(is, httpRequest));
            return cosObject;
        } catch (CosServiceException cse) {
            /*
             * If the request failed because one of the specified constraints was not met (ex:
             * matching ETag, modified since date, etc.), then return null, so that users don't have
             * to wrap their code in try/catch blocks and check for this status code if they want to
             * use constraints.
             */
            if ((cse.getStatusCode() == 412 && !clientConfig.isThrow412Directly()) || (cse.getStatusCode() == 304 && !clientConfig.isThrow304Directly())) {
                return null;
            }
            throw cse;
        }
    }

    @Override
    public ObjectMetadata getObject(final GetObjectRequest getObjectRequest, File destinationFile)
            throws CosClientException, CosServiceException {
        rejectNull(getObjectRequest,
                "The GetObjectRequest parameter must be specified when requesting an object");
        rejectNull(destinationFile,
                "The destination file parameter must be specified when downloading an object directly to a file");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when downloading an object directly to a file");

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
    public PutSymlinkResult putSymlink(PutSymlinkRequest putSymlinkRequest) {
        rejectNull(putSymlinkRequest, "The request must not be null.");
        rejectNull(putSymlinkRequest.getBucketName(),
                "The bucket name parameter must be specified when create symlink.");
        rejectNull(putSymlinkRequest.getSymlink(), "The symlink name must be specified when create symlink");
        rejectNull(putSymlinkRequest.getTarget(), "The target object must be specified when create symlink");

        CosHttpRequest<CosServiceRequest> request = createRequest(putSymlinkRequest.getBucketName(),
                putSymlinkRequest.getSymlink(), putSymlinkRequest, HttpMethodName.PUT);
        request.addParameter("symlink", null);

        request.addHeader(Headers.SYMLINK_TARGET, putSymlinkRequest.getTarget());

        return invoke(request, new PutSymlinkResultHandler());
    }

    @Override
    public GetSymlinkResult getSymlink(GetSymlinkRequest getSymlinkRequest) {
        rejectNull(getSymlinkRequest, "The request must not be null.");
        rejectNull(getSymlinkRequest.getBucketName(),
                "The bucket name parameter must be specified when getting symlink.");
        rejectNull(getSymlinkRequest.getSymlink(), "The requested symbolic link must be specified.");

        CosHttpRequest<CosServiceRequest> request = createRequest(getSymlinkRequest.getBucketName(),
                getSymlinkRequest.getSymlink(), getSymlinkRequest, HttpMethodName.GET);
        request.addParameter("symlink", null);
        addParameterIfNotNull(request,"versionId", getSymlinkRequest.getVersionId());

        return invoke(request, new GetSymlinkResultHandler());
    }


    @Override
    public boolean doesObjectExist(String bucketName, String objectName)
            throws CosClientException, CosServiceException {
        try {
            getObjectMetadata(bucketName, objectName);
            return true;
        } catch (CosServiceException cse) {
            if (cse.getStatusCode() == 404) {
                return false;
            }
            throw cse;
        }
    }

    @Override
    public ObjectMetadata getObjectMetadata(String bucketName, String key)
            throws CosClientException, CosServiceException {
        return getObjectMetadata(new GetObjectMetadataRequest(bucketName, key));
    }

    @Override
    public ObjectMetadata getObjectMetadata(GetObjectMetadataRequest getObjectMetadataRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getObjectMetadataRequest,
                "The GetObjectMetadataRequest parameter must be specified when requesting an object's metadata");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when requesting an object's metadata");

        String bucketName = getObjectMetadataRequest.getBucketName();
        String key = getObjectMetadataRequest.getKey();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when requesting an object's metadata");
        rejectNull(key, "The key parameter must be specified when requesting an object's metadata");

        CosHttpRequest<GetObjectMetadataRequest> request =
                createRequest(bucketName, key, getObjectMetadataRequest, HttpMethodName.HEAD);
        addParameterIfNotNull(request, "versionId", getObjectMetadataRequest.getVersionId());
        // Populate the SSE-C parameters to the request header
        populateSSE_C(request, getObjectMetadataRequest.getSSECustomerKey());
        return invoke(request, new CosMetadataResponseHandler());
    }

    @Override
    public void deleteObject(String bucketName, String key)
            throws CosClientException, CosServiceException {
        deleteObject(new DeleteObjectRequest(bucketName, key));
    }

    @Override
    public void deleteObject(DeleteObjectRequest deleteObjectRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteObjectRequest,
                "The delete object request must be specified when deleting an object");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when deleting an object");

        rejectNull(deleteObjectRequest.getBucketName(),
                "The bucket name must be specified when deleting an object");
        rejectNull(deleteObjectRequest.getKey(),
                "The key must be specified when deleting an object");

        rejectEmpty(deleteObjectRequest.getKey(),
                                "The length of the key must be greater than 0");

        if (Objects.equals(deleteObjectRequest.getKey(), "/")) {
            throw new IllegalArgumentException("The specified key should not be /");
        }

        CosHttpRequest<DeleteObjectRequest> request =
                createRequest(deleteObjectRequest.getBucketName(), deleteObjectRequest.getKey(),
                        deleteObjectRequest, HttpMethodName.DELETE);
        if (deleteObjectRequest.isRecursive()) {
            request.addParameter("recursive", null);
        }

        if (deleteObjectRequest.getVersionId() != null && !deleteObjectRequest.getVersionId().isEmpty()) {
            request.addParameter("versionId", deleteObjectRequest.getVersionId());
        }

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public DeleteObjectsResult deleteObjects(DeleteObjectsRequest deleteObjectsRequest)
            throws MultiObjectDeleteException, CosClientException, CosServiceException {
        rejectNull(deleteObjectsRequest,
                "The DeleteObjectsRequest parameter must be specified when deleting objects");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when deleting objects");

        CosHttpRequest<DeleteObjectsRequest> request =
                createRequest(deleteObjectsRequest.getBucketName(), null, deleteObjectsRequest,
                        HttpMethodName.POST);
        request.addParameter("delete", null);

        byte[] content =
                new MultiObjectDeleteXmlFactory().convertToXmlByteArray(deleteObjectsRequest);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", "application/xml");
        request.setContent(new ByteArrayInputStream(content));
        try {
            byte[] md5 = Md5Utils.computeMD5Hash(content);
            String md5Base64 = BinaryUtils.toBase64(md5);
            request.addHeader("Content-MD5", md5Base64);
        } catch (Exception e) {
            throw new CosClientException("Couldn't compute md5 sum", e);
        }

        @SuppressWarnings("unchecked")
        ResponseHeaderHandlerChain<DeleteObjectsResponse> responseHandler =
                new ResponseHeaderHandlerChain<DeleteObjectsResponse>(
                        new Unmarshallers.DeleteObjectsResultUnmarshaller());

        DeleteObjectsResponse response = invoke(request, responseHandler);

        /*
         * If the result was only partially successful, throw an exception
         */
        if (!response.getErrors().isEmpty()) {
            Map<String, String> headers = responseHandler.getResponseHeaders();

            MultiObjectDeleteException ex = new MultiObjectDeleteException(response.getErrors(),
                    response.getDeletedObjects());

            ex.setStatusCode(200);
            ex.setRequestId(headers.get(Headers.REQUEST_ID));

            throw ex;
        }
        DeleteObjectsResult result = new DeleteObjectsResult(response.getDeletedObjects());

        return result;
    }

    @Override
    public void deleteVersion(String bucketName, String key, String versionId)
            throws CosClientException, CosServiceException {
        deleteVersion(new DeleteVersionRequest(bucketName, key, versionId));
    }

    @Override
    public void deleteVersion(DeleteVersionRequest deleteVersionRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteVersionRequest,
                "The DeleteVersionRequest parameter must be specified when deleting a version");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when deleting a version");


        String bucketName = deleteVersionRequest.getBucketName();
        String key = deleteVersionRequest.getKey();
        String versionId = deleteVersionRequest.getVersionId();

        rejectNull(bucketName, "The bucket name must be specified when deleting a version");
        rejectNull(key, "The key must be specified when deleting a version");
        rejectNull(versionId, "The version ID must be specified when deleting a version");

        CosHttpRequest<DeleteVersionRequest> request =
                createRequest(bucketName, key, deleteVersionRequest, HttpMethodName.DELETE);
        request.addParameter("versionId", versionId);

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public Bucket createBucket(String bucketName) throws CosClientException, CosServiceException {
        return createBucket(new CreateBucketRequest(bucketName));
    }

    @Override
    public Bucket createBucket(CreateBucketRequest createBucketRequest)
            throws CosClientException, CosServiceException {
        rejectNull(createBucketRequest,
                "The CreateBucketRequest parameter must be specified when creating a bucket");

        String bucketName = createBucketRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when creating a bucket");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when creating a bucket");

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

    public Bucket createMAZBucket(CreateBucketRequest createBucketRequest)
            throws CosClientException, CosServiceException {
        rejectNull(createBucketRequest,
                "The CreateBucketRequest parameter must be specified when creating a bucket");

        String bucketName = createBucketRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when creating a bucket");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when creating a bucket");

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

        ObjectMetadata metadata = new ObjectMetadata();

        String MAZStr = "<CreateBucketConfiguration>\n" +
                "    <BucketAZConfig>MAZ</BucketAZConfig>\n" +
                "</CreateBucketConfiguration>";

        byte[] contentByteArray = MAZStr.getBytes(StringUtils.UTF8);
        String contentMd5 = Md5Utils.md5AsBase64(contentByteArray);

        InputStream contentInput = new ByteArrayInputStream(contentByteArray);

        metadata.setContentType("application/xml");
        metadata.setContentLength(contentByteArray.length);
        metadata.setContentMD5(contentMd5);

        MD5DigestCalculatingInputStream md5DigestStream = null;
        md5DigestStream = new MD5DigestCalculatingInputStream(contentInput);

        populateRequestMetadata(request, metadata);
        request.setContent(md5DigestStream);

        try {
            invoke(request, voidCosResponseHandler);
        } catch (Throwable t) {
            throw Throwables.failure(t);
        } finally {
            try {
                contentInput.close();
                md5DigestStream.close();
            } catch (IOException e) {
                throw new CosClientException(e.getMessage(), e);
            }
        }

        return new Bucket(bucketName);
    }

    @Override
    public void deleteBucket(String bucketName) throws CosClientException, CosServiceException {
        deleteBucket(new DeleteBucketRequest(bucketName));
    }

    @Override
    public void deleteBucket(DeleteBucketRequest deleteBucketRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteBucketRequest,
                "The DeleteBucketRequest parameter must be specified when deleting a bucket");

        String bucketName = deleteBucketRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when deleting a bucket");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when deleting a bucket");

        CosHttpRequest<DeleteBucketRequest> request =
                createRequest(bucketName, "/", deleteBucketRequest, HttpMethodName.DELETE);
        invoke(request, voidCosResponseHandler);
    }

    @Override
    public boolean doesBucketExist(String bucketName)
            throws CosClientException, CosServiceException {
        try {
            headBucket(new HeadBucketRequest(bucketName));
            return true;
        } catch (CosServiceException cse) {
            if (cse.getStatusCode() == Constants.NO_SUCH_BUCKET_STATUS_CODE) {
                return false;
            }
            throw cse;
        }
    }

    @Override
    public HeadBucketResult headBucket(HeadBucketRequest headBucketRequest)
            throws CosClientException, CosServiceException {
        rejectNull(headBucketRequest,
                "The HeadBucketRequest parameter must be specified when head a bucket");
        String bucketName = headBucketRequest.getBucketName();

        rejectNull(bucketName, "The bucketName parameter must be specified.");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when querying a bucket");

        CosHttpRequest<HeadBucketRequest> request =
                createRequest(bucketName, null, headBucketRequest, HttpMethodName.HEAD);

        return invoke(request, new HeadBucketResultHandler());
    }

    @Override
    public List<Bucket> listBuckets() throws CosClientException, CosServiceException {
        return listBuckets(new ListBucketsRequest());
    }

    @Override
    public List<Bucket> listBuckets(ListBucketsRequest listBucketsRequest)
            throws CosClientException, CosServiceException {
        ListBucketsResult result = getService(listBucketsRequest);
        return result.getBuckets();
    }

    public ListBucketsResult getService(ListBucketsRequest listBucketsRequest)
            throws CosClientException, CosServiceException {
        rejectNull(listBucketsRequest,
                "The request object parameter listBucketsRequest must be specified.");
        CosHttpRequest<ListBucketsRequest> request =
                createRequest(null, null, listBucketsRequest, HttpMethodName.GET);
        if (!listBucketsRequest.getMarker().isEmpty()) {
            request.addParameter("marker", listBucketsRequest.getMarker());
        }

        if (listBucketsRequest.getMaxKeys() != null && listBucketsRequest.getMaxKeys() > 0) {
            request.addParameter("max-keys", listBucketsRequest.getMaxKeys().toString());
        }

        return invoke(request, new Unmarshallers.GetServiceUnmarshaller());
    }

    @Override
    public String getBucketLocation(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketLocation(new GetBucketLocationRequest(bucketName));
    }

    @Override
    public String getBucketLocation(GetBucketLocationRequest getBucketLocationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketLocationRequest,
                "The request parameter must be specified when requesting a bucket's location");
        String bucketName = getBucketLocationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when requesting a bucket's location");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when requesting a bucket's location");

        CosHttpRequest<GetBucketLocationRequest> request =
                createRequest(bucketName, null, getBucketLocationRequest, HttpMethodName.GET);
        request.addParameter("location", null);

        return invoke(request, new Unmarshallers.BucketLocationUnmarshaller());
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
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when initiating a multipart upload");

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

        // Populate the SSE-C parameters to the request header
        populateSSE_C(request, initiateMultipartUploadRequest.getSSECustomerKey());

        // Populate the SSE KMS parameters to the request header
        populateSSE_KMS(request, initiateMultipartUploadRequest.getSSECOSKeyManagementParams());

        // init upload body length is zero
        request.addHeader(Headers.CONTENT_LENGTH, String.valueOf(0));

        @SuppressWarnings("unchecked")
        ResponseHeaderHandlerChain<InitiateMultipartUploadResult> responseHandler =
                new ResponseHeaderHandlerChain<InitiateMultipartUploadResult>(
                        // xml payload unmarshaller
                        new Unmarshallers.InitiateMultipartUploadResultUnmarshaller(),
                        // header handlers
                        new ServerSideEncryptionHeaderHandler<InitiateMultipartUploadResult>(),
                        new VIDResultHandler<InitiateMultipartUploadResult>());
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
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when uploading a part");

        try {
            preflightObj(uploadPartRequest);
        } catch (CosServiceException cse) {
            String msg = String.format("fail to do the preflight request due to the service exception[statusCode:%s, requestId:%s], will not do the upload part request", cse.getStatusCode(), cse.getRequestId());
            log.warn(msg);
            throw cse;
        } catch (CosClientException cce) {
            log.warn("fail to do the preflight request due to the client exception, will not do the upload part request", cce);
            throw cce;
        }

        CosHttpRequest<UploadPartRequest> request =
                createRequest(bucketName, key, uploadPartRequest, HttpMethodName.PUT);
        request.addParameter("uploadId", uploadId);
        request.addParameter("partNumber", Integer.toString(partNumber));

        final ObjectMetadata objectMetadata = uploadPartRequest.getObjectMetadata();
        if (objectMetadata != null)
            populateRequestMetadata(request, objectMetadata);

        addHeaderIfNotNull(request, Headers.CONTENT_MD5, uploadPartRequest.getMd5Digest());
        request.addHeader(Headers.CONTENT_LENGTH, Long.toString(partSize));

        // Populate the SSE-C parameters to the request header
        populateSSE_C(request, uploadPartRequest.getSSECustomerKey());

        // Populate the traffic limit parameter to the request header
        populateTrafficLimit(request, uploadPartRequest.getTrafficLimit());
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

    private UploadPartResult doUploadPart(final String bucketName, final String key,
            final String uploadId, final int partNumber, final long partSize,
            CosHttpRequest<UploadPartRequest> request, InputStream inputStream,
            MD5DigestCalculatingInputStream md5DigestStream) {
        try {
            request.setContent(inputStream);
            ObjectMetadata metadata = invoke(request, new CosMetadataResponseHandler());
            final String etag = metadata.getETag();

            if (metadata.isNeedPreflight()) {
                Long currentTime = System.currentTimeMillis();
                if ((preflightBuckets.get(bucketName) == null) || ((currentTime - preflightBuckets.get(bucketName)) > clientConfig.getPreflightStatusUpdateInterval())) {
                    String reqMsg = String.format("will update preflight status, bucket[%s]", bucketName);
                    log.info(reqMsg);
                    preflightBuckets.put(bucketName, currentTime);
                }
            } else {
                Long currentTime = System.currentTimeMillis();
                if ((preflightBuckets.get(bucketName) != null) && ((currentTime - preflightBuckets.get(bucketName)) > clientConfig.getPreflightStatusUpdateInterval())) {
                    String reqMsg = String.format("will remove bucket[%s] from preflight lists", bucketName);
                    log.info(reqMsg);
                    preflightBuckets.remove(bucketName);
                }
            }

            if (md5DigestStream != null && !skipMd5CheckStrategy
                    .skipClientSideValidationPerUploadPartResponse(metadata)) {
                byte[] clientSideHash = md5DigestStream.getMd5Digest();
                byte[] serverSideHash = BinaryUtils.fromHex(etag);

                if (!Arrays.equals(clientSideHash, serverSideHash)) {
                    final String info = "bucketName: " + bucketName + ", key: " + key
                            + ", uploadId: " + uploadId + ", partNumber: " + partNumber
                            + ", partSize: " + partSize;
                    throw new CosClientException("Unable to verify integrity of data upload.  "
                            + "Client calculated content hash (contentMD5: "
                            + BinaryUtils.toHex(clientSideHash)
                            + " in hex) didn't match hash (etag: " + etag
                            + " in hex) calculated by Qcloud COS.  "
                            + "You may need to delete the data stored in Qcloud COS. " + "(" + info
                            + ")");
                }
            }

            UploadPartResult result = new UploadPartResult();
            result.setETag(etag);
            result.setPartNumber(partNumber);
            result.setSSEAlgorithm(metadata.getSSEAlgorithm());
            result.setSSECustomerAlgorithm(metadata.getSSECustomerAlgorithm());
            result.setSSECustomerKeyMd5(metadata.getSSECustomerKeyMd5());
            result.setCrc64Ecma(metadata.getCrc64Ecma());
            result.setRequestId(metadata.getRequestId());

            return result;
        } catch (Throwable t) {
            throw Throwables.failure(t);
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
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when listing parts");


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
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when aborting a multipart uploads");

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
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when completing a multipart uploads");


        int retries = 0;
        CompleteMultipartUploadHandler handler;
        do {
            CosHttpRequest<CompleteMultipartUploadRequest> request = createRequest(bucketName, key,
                    completeMultipartUploadRequest, HttpMethodName.POST);
            request.addParameter("uploadId", uploadId);

            byte[] xml = RequestXmlFactory
                    .convertToXmlByteArray(completeMultipartUploadRequest.getPartETags());

            request.addHeader("Content-Type", "application/xml");
            request.addHeader("Content-Length", String.valueOf(xml.length));
            ObjectMetadata objectMetadata = completeMultipartUploadRequest.getObjectMetadata();
            if(objectMetadata != null) {
                populateRequestMetadata(request, objectMetadata);
            }
            request.setContent(new ByteArrayInputStream(xml));
            if(completeMultipartUploadRequest.getPicOperations() != null) {
                request.addHeader(Headers.PIC_OPERATIONS, Jackson.toJsonString(
                        completeMultipartUploadRequest.getPicOperations()));
            }
            @SuppressWarnings("unchecked")
            ResponseHeaderHandlerChain<CompleteMultipartUploadHandler> responseHandler =
                    new ResponseHeaderHandlerChain<CompleteMultipartUploadHandler>(
                            // xml payload unmarshaller
                            new Unmarshallers.CompleteMultipartUploadResultUnmarshaller(),
                            // header handlers
                            new ServerSideEncryptionHeaderHandler<CompleteMultipartUploadHandler>(),
                            new ObjectExpirationHeaderHandler<CompleteMultipartUploadHandler>(),
                            new VIDResultHandler<CompleteMultipartUploadHandler>());
            handler = invoke(request, responseHandler);
            if (handler.getCompleteMultipartUploadResult() != null) {
                Map<String, String> responseHeaders = responseHandler.getResponseHeaders();
                String versionId = responseHeaders.get(Headers.COS_VERSION_ID);
                String crc64Ecma = responseHeaders.get(Headers.COS_HASH_CRC64_ECMA);
                String crc32c = responseHeaders.get(Headers.COS_HASH_CRC32_C);
                handler.getCompleteMultipartUploadResult().setVersionId(versionId);
                handler.getCompleteMultipartUploadResult().setCrc64Ecma(crc64Ecma);
                if (crc32c != null) {
                    handler.getCompleteMultipartUploadResult().setCrc32c(crc32c);
                }
                // if ci request, set ciUploadResult to CompleteMultipartUploadResult
                if(completeMultipartUploadRequest.getPicOperations() != null) {
                    handler.getCompleteMultipartUploadResult().setCiUploadResult(handler.getCiUploadResult());
                }
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

        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when listing multipart uploads");

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
        rejectNull(listObjectsRequest,
                "The ListObjectsRequest parameter must be specified when listing objects in a bucket");
        rejectNull(listObjectsRequest.getBucketName(),
                "The bucket name parameter must be specified when listing objects in a bucket");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when listing objects in a bucket");

        final boolean shouldSDKDecodeResponse = listObjectsRequest.getEncodingType() == null;

        CosHttpRequest<ListObjectsRequest> request = createRequest(
                listObjectsRequest.getBucketName(), "/", listObjectsRequest, HttpMethodName.GET);

        // 兼容prefix以/开始, 以为COS V4的prefix等可以以斜杠开始
        addParameterIfNotNull(request, "prefix",
                leftStripPathDelimiter(listObjectsRequest.getPrefix()));
        addParameterIfNotNull(request, "marker", listObjectsRequest.getMarker());
        addParameterIfNotNull(request, "delimiter", listObjectsRequest.getDelimiter());
        request.addParameter("encoding-type", shouldSDKDecodeResponse ? Constants.URL_ENCODING
                : listObjectsRequest.getEncodingType());
        if (listObjectsRequest.getMaxKeys() != null
                && listObjectsRequest.getMaxKeys().intValue() >= 0)
            request.addParameter("max-keys", listObjectsRequest.getMaxKeys().toString());
        COSXmlResponseHandler<ObjectListing> handler =
                new COSXmlResponseHandler(new Unmarshallers.ListObjectsUnmarshaller(shouldSDKDecodeResponse));
        return invoke(request, handler);
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
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when listing the next batch of objects  in a bucket");

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
    public VersionListing listVersions(String bucketName, String prefix)
            throws CosClientException, CosServiceException {
        return listVersions(new ListVersionsRequest(bucketName, prefix, null, null, null, null));
    }

    @Override
    public VersionListing listVersions(String bucketName, String prefix, String keyMarker,
            String versionIdMarker, String delimiter, Integer maxResults)
            throws CosClientException, CosServiceException {
        ListVersionsRequest request = new ListVersionsRequest().withBucketName(bucketName)
                .withPrefix(prefix).withDelimiter(delimiter).withKeyMarker(keyMarker)
                .withVersionIdMarker(versionIdMarker).withMaxResults(maxResults);
        return listVersions(request);
    }

    @Override
    public VersionListing listVersions(ListVersionsRequest listVersionsRequest)
            throws CosClientException, CosServiceException {
        rejectNull(listVersionsRequest,
                "The ListVersionsRequest parameter must be specified when listing versions in a bucket");
        rejectNull(listVersionsRequest.getBucketName(),
                "The bucket name parameter must be specified when listing versions in a bucket");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when listing versions in a bucket");

        final boolean shouldSDKDecodeResponse = listVersionsRequest.getEncodingType() == null;

        CosHttpRequest<ListVersionsRequest> request = createRequest(
                listVersionsRequest.getBucketName(), null, listVersionsRequest, HttpMethodName.GET);
        request.addParameter("versions", null);

        addParameterIfNotNull(request, "prefix", listVersionsRequest.getPrefix());
        addParameterIfNotNull(request, "key-marker", listVersionsRequest.getKeyMarker());
        addParameterIfNotNull(request, "version-id-marker",
                listVersionsRequest.getVersionIdMarker());
        addParameterIfNotNull(request, "delimiter", listVersionsRequest.getDelimiter());
        request.addParameter("encoding-type", shouldSDKDecodeResponse ? Constants.URL_ENCODING
                : listVersionsRequest.getEncodingType());

        if (listVersionsRequest.getMaxResults() != null && listVersionsRequest.getMaxResults() >= 0)
            request.addParameter("max-keys", listVersionsRequest.getMaxResults().toString());

        return invoke(request, new Unmarshallers.VersionListUnmarshaller(shouldSDKDecodeResponse));
    }

    @Override
    public VersionListing listNextBatchOfVersions(VersionListing previousVersionListing)
            throws CosClientException, CosServiceException {
        return listNextBatchOfVersions(new ListNextBatchOfVersionsRequest(previousVersionListing));
    }

    @Override
    public VersionListing listNextBatchOfVersions(
            ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest)
            throws CosClientException, CosServiceException {
        rejectNull(listNextBatchOfVersionsRequest,
                "The request object parameter must be specified when listing the next batch of versions in a bucket");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when listing the next batch of versions in a bucket");

        VersionListing previousVersionListing =
                listNextBatchOfVersionsRequest.getPreviousVersionListing();

        if (!previousVersionListing.isTruncated()) {
            VersionListing emptyListing = new VersionListing();
            emptyListing.setBucketName(previousVersionListing.getBucketName());
            emptyListing.setDelimiter(previousVersionListing.getDelimiter());
            emptyListing.setKeyMarker(previousVersionListing.getNextKeyMarker());
            emptyListing.setVersionIdMarker(previousVersionListing.getNextVersionIdMarker());
            emptyListing.setMaxKeys(previousVersionListing.getMaxKeys());
            emptyListing.setPrefix(previousVersionListing.getPrefix());
            emptyListing.setEncodingType(previousVersionListing.getEncodingType());
            emptyListing.setTruncated(false);

            return emptyListing;
        }

        return listVersions(listNextBatchOfVersionsRequest.toListVersionsRequest());
    }

    @Override
    public CopyObjectResult copyObject(String sourceBucketName, String sourceKey,
            String destinationBucketName, String destinationKey)
            throws CosClientException, CosServiceException {
        return copyObject(new CopyObjectRequest(sourceBucketName, sourceKey, destinationBucketName,
                destinationKey));
    }

    @Override
    public CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest)
            throws CosClientException, CosServiceException {
        rejectNull(copyObjectRequest,
                "The CopyObjectRequest parameter must be specified when copying an object");
        rejectNull(copyObjectRequest.getSourceBucketName(),
                "The source bucket name must be specified when copying an object");
        rejectNull(copyObjectRequest.getSourceKey(),
                "The source object key must be specified when copying an object");
        rejectNull(copyObjectRequest.getDestinationBucketName(),
                "The destination bucket name must be specified when copying an object");
        rejectNull(copyObjectRequest.getDestinationKey(),
                "The destination object key must be specified when copying an object");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when copying an object");

        String destinationKey = copyObjectRequest.getDestinationKey();
        String destinationBucketName = copyObjectRequest.getDestinationBucketName();

        CosHttpRequest<CopyObjectRequest> request = createRequest(destinationBucketName,
                destinationKey, copyObjectRequest, HttpMethodName.PUT);

        populateRequestWithCopyObjectParameters(request, copyObjectRequest);

        /*
         * We can't send a non-zero length Content-Length header if the user specified it, otherwise
         * it messes up the HTTP connection when the remote server thinks there's more data to pull.
         */
        int retryIndex = 0;
        while (true) {
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
                                new ObjectExpirationHeaderHandler<CopyObjectResultHandler>(),
                                new VIDResultHandler<CopyObjectResultHandler>());
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

                String errorMsg = String.format("failed to execute http request, due to service exception: %s"
                                + " httpRequest: %s, retryIdx:%d, maxErrorRetry:%d", cse.getMessage(), request,
                        retryIndex, clientConfig.getMaxErrorRetryForCopyRequest());
                log.debug(errorMsg);
                if (retryIndex < clientConfig.getMaxErrorRetryForCopyRequest() && RetryUtils.shouldRetryCopyRequest(cse)) {
                    retryIndex++;
                    continue;
                }

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
            copyObjectResult.setDateStr(copyObjectResultHandler.getDateStr());
            copyObjectResult.setCrc64Ecma(copyObjectResultHandler.getCrc64Ecma());
            copyObjectResult.setRequestId(copyObjectResultHandler.getRequestId());

            return copyObjectResult;
        }
    }

    @Override
    public CopyPartResult copyPart(CopyPartRequest copyPartRequest)
            throws CosClientException, CosServiceException {
        rejectNull(copyPartRequest,
                "The CopyPartRequest parameter must be specified when copying a part");
        rejectNull(copyPartRequest.getSourceBucketName(),
                "The source bucket name must be specified when copying a part");
        rejectNull(copyPartRequest.getSourceKey(),
                "The source object key must be specified when copying a part");
        rejectNull(copyPartRequest.getDestinationBucketName(),
                "The destination bucket name must be specified when copying a part");
        rejectNull(copyPartRequest.getUploadId(),
                "The upload id must be specified when copying a part");
        rejectNull(copyPartRequest.getDestinationKey(),
                "The destination object key must be specified when copying a part");
        rejectNull(copyPartRequest.getPartNumber(),
                "The part number must be specified when copying a part");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when copying a part");

        String destinationKey = copyPartRequest.getDestinationKey();
        String destinationBucketName = copyPartRequest.getDestinationBucketName();

        CosHttpRequest<CopyPartRequest> request = createRequest(destinationBucketName,
                destinationKey, copyPartRequest, HttpMethodName.PUT);

        populateRequestWithCopyPartParameters(request, copyPartRequest);

        request.addParameter("uploadId", copyPartRequest.getUploadId());
        request.addParameter("partNumber", Integer.toString(copyPartRequest.getPartNumber()));

        /*
         * We can't send a non-zero length Content-Length header if the user
         * specified it, otherwise it messes up the HTTP connection when the
         * remote server thinks there's more data to pull.
         */
        int retryIndex = 0;
        while (true) {
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
                                new VIDResultHandler<CopyObjectResultHandler>());
                copyObjectResultHandler = invoke(request, handler);
            } catch (CosServiceException cse) {
                /*
                 * If the request failed because one of the specified constraints
                 * was not met (ex: matching ETag, modified since date, etc.), then
                 * return null, so that users don't have to wrap their code in
                 * try/catch blocks and check for this status code if they want to
                 * use constraints.
                 */
                if (cse.getStatusCode() == Constants.FAILED_PRECONDITION_STATUS_CODE) {
                    return null;
                }

                throw cse;
            }

            /*
             * CopyPart has two failure modes: 1 - An HTTP error code is returned
             * and the error is processed like any other error response. 2 - An HTTP
             * 200 OK code is returned, but the response content contains an XML
             * error response.
             *
             * This makes it very difficult for the client runtime to cleanly detect
             * this case and handle it like any other error response. We could
             * extend the runtime to have a more flexible/customizable definition of
             * success/error (per request), but it's probably overkill for this one
             * special case.
             */
            if (copyObjectResultHandler.getErrorCode() != null) {
                String errorCode = copyObjectResultHandler.getErrorCode();
                String errorMessage = copyObjectResultHandler.getErrorMessage();
                String requestId = copyObjectResultHandler.getErrorRequestId();

                CosServiceException cse = new CosServiceException(errorMessage);
                cse.setErrorCode(errorCode);
                cse.setErrorType(ErrorType.Service);
                cse.setRequestId(requestId);
                cse.setStatusCode(200);

                String errorMsg = String.format("failed to execute http request, due to service exception: %s"
                                + " httpRequest: %s, retryIdx:%d, maxErrorRetry:%d", cse.getMessage(), request,
                        retryIndex, clientConfig.getMaxErrorRetryForCopyRequest());
                log.debug(errorMsg);
                if (retryIndex < clientConfig.getMaxErrorRetryForCopyRequest() && RetryUtils.shouldRetryCopyRequest(cse)) {
                    retryIndex++;
                    continue;
                }

                throw cse;
            }

            CopyPartResult copyPartResult = new CopyPartResult();
            copyPartResult.setETag(copyObjectResultHandler.getETag());
            copyPartResult.setPartNumber(copyPartRequest.getPartNumber());
            copyPartResult.setLastModifiedDate(copyObjectResultHandler.getLastModified());
            copyPartResult.setVersionId(copyObjectResultHandler.getVersionId());
            copyPartResult.setSSEAlgorithm(copyObjectResultHandler.getSSEAlgorithm());
            copyPartResult.setSSECustomerAlgorithm(copyObjectResultHandler.getSSECustomerAlgorithm());
            copyPartResult.setSSECustomerKeyMd5(copyObjectResultHandler.getSSECustomerKeyMd5());
            copyPartResult.setCrc64Ecma(copyObjectResultHandler.getCrc64Ecma());
            copyPartResult.setRequestId(copyObjectResultHandler.getRequestId());

            return copyPartResult;
        }
    }

    @Override
    public void setBucketLifecycleConfiguration(String bucketName,
            BucketLifecycleConfiguration bucketLifecycleConfiguration)
            throws CosClientException, CosServiceException {
        setBucketLifecycleConfiguration(new SetBucketLifecycleConfigurationRequest(bucketName,
                bucketLifecycleConfiguration));
    }

    @Override
    public void setBucketLifecycleConfiguration(
            SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketLifecycleConfigurationRequest,
                "The set bucket lifecycle configuration request object must be specified.");


        String bucketName = setBucketLifecycleConfigurationRequest.getBucketName();
        BucketLifecycleConfiguration bucketLifecycleConfiguration =
                setBucketLifecycleConfigurationRequest.getLifecycleConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting bucket lifecycle configuration.");
        rejectNull(bucketLifecycleConfiguration,
                "The lifecycle configuration parameter must be specified when setting bucket lifecycle configuration.");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when setting bucket lifecycle configuration");

        CosHttpRequest<SetBucketLifecycleConfigurationRequest> request = createRequest(bucketName,
                null, setBucketLifecycleConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("lifecycle", null);

        byte[] content = new BucketConfigurationXmlFactory()
                .convertToXmlByteArray(bucketLifecycleConfiguration);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", "application/xml");
        request.setContent(new ByteArrayInputStream(content));
        try {
            byte[] md5 = Md5Utils.computeMD5Hash(content);
            String md5Base64 = BinaryUtils.toBase64(md5);
            request.addHeader("Content-MD5", md5Base64);
        } catch (Exception e) {
            throw new CosClientException("Couldn't compute md5 sum", e);
        }

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketLifecycleConfiguration getBucketLifecycleConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketLifecycleConfiguration(
                new GetBucketLifecycleConfigurationRequest(bucketName));
    }

    @Override
    public BucketLifecycleConfiguration getBucketLifecycleConfiguration(
            GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest) {
        rejectNull(getBucketLifecycleConfigurationRequest,
                "The request object pamameter getBucketLifecycleConfigurationRequest must be specified.");
        String bucketName = getBucketLifecycleConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name must be specifed when retrieving the bucket lifecycle configuration.");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when retrieving lifecycle configuration");

        CosHttpRequest<GetBucketLifecycleConfigurationRequest> request = createRequest(bucketName,
                null, getBucketLifecycleConfigurationRequest, HttpMethodName.GET);
        request.addParameter("lifecycle", null);

        try {
            return invoke(request, new Unmarshallers.BucketLifecycleConfigurationUnmarshaller());
        } catch (CosServiceException cse) {
            switch (cse.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw cse;
            }
        }
    }

    @Override
    public void deleteBucketLifecycleConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        deleteBucketLifecycleConfiguration(
                new DeleteBucketLifecycleConfigurationRequest(bucketName));
    }

    @Override
    public void deleteBucketLifecycleConfiguration(
            DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteBucketLifecycleConfigurationRequest,
                "The delete bucket lifecycle configuration request object must be specified.");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when deleting lifecycle configuration");

        String bucketName = deleteBucketLifecycleConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when deleting bucket lifecycle configuration.");

        CosHttpRequest<DeleteBucketLifecycleConfigurationRequest> request = createRequest(
                bucketName, null, deleteBucketLifecycleConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("lifecycle", null);

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void setBucketVersioningConfiguration(
            SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketVersioningConfigurationRequest,
                "The SetBucketVersioningConfigurationRequest object must be specified when setting versioning configuration");

        String bucketName = setBucketVersioningConfigurationRequest.getBucketName();
        BucketVersioningConfiguration versioningConfiguration =
                setBucketVersioningConfigurationRequest.getVersioningConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting versioning configuration");
        rejectNull(versioningConfiguration,
                "The bucket versioning parameter must be specified when setting versioning configuration");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when setting versioning configuration");

        CosHttpRequest<SetBucketVersioningConfigurationRequest> request = createRequest(bucketName,
                null, setBucketVersioningConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("versioning", null);


        byte[] bytes =
                new BucketConfigurationXmlFactory().convertToXmlByteArray(versioningConfiguration);
        request.setContent(new ByteArrayInputStream(bytes));

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketVersioningConfiguration getBucketVersioningConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketVersioningConfiguration(
                new GetBucketVersioningConfigurationRequest(bucketName));
    }

    @Override
    public BucketVersioningConfiguration getBucketVersioningConfiguration(
            GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketVersioningConfigurationRequest,
                "The request object parameter getBucketVersioningConfigurationRequest must be specified.");
        String bucketName = getBucketVersioningConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when querying versioning configuration");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when querying versioning configuration");

        CosHttpRequest<GetBucketVersioningConfigurationRequest> request = createRequest(bucketName,
                null, getBucketVersioningConfigurationRequest, HttpMethodName.GET);
        request.addParameter("versioning", null);

        return invoke(request, new Unmarshallers.BucketVersioningConfigurationUnmarshaller());
    }

    @Override
    public void setObjectAcl(String bucketName, String key, AccessControlList acl)
            throws CosClientException, CosServiceException {
        setObjectAcl(new SetObjectAclRequest(bucketName, key, acl));
    }

    @Override
    public void setObjectAcl(String bucketName, String key, CannedAccessControlList acl)
            throws CosClientException, CosServiceException {
        setObjectAcl(new SetObjectAclRequest(bucketName, key, acl));
    }

    @Override
    public void setObjectAcl(SetObjectAclRequest setObjectAclRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setObjectAclRequest, "The request must not be null.");
        rejectNull(setObjectAclRequest.getBucketName(),
                "The bucket name parameter must be specified when setting an object's ACL");
        rejectNull(setObjectAclRequest.getKey(),
                "The key parameter must be specified when setting an object's ACL");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when setting an object acl");

        if (setObjectAclRequest.getAcl() != null && setObjectAclRequest.getCannedAcl() != null) {
            throw new IllegalArgumentException(
                    "Only one of the ACL and CannedACL parameters can be specified, not both.");
        }

        if (setObjectAclRequest.getAcl() != null) {
            setAcl(setObjectAclRequest.getBucketName(), setObjectAclRequest.getKey(), null,
                    setObjectAclRequest.getAcl(), setObjectAclRequest);

        } else if (setObjectAclRequest.getCannedAcl() != null) {
            setAcl(setObjectAclRequest.getBucketName(), setObjectAclRequest.getKey(),
                    setObjectAclRequest.getVersionId(), setObjectAclRequest.getCannedAcl(),
                    setObjectAclRequest);

        } else {
            throw new IllegalArgumentException(
                    "At least one of the ACL and CannedACL parameters should be specified");
        }
    }

    @Override
    public AccessControlList getObjectAcl(String bucketName, String key)
            throws CosClientException, CosServiceException {
        return getObjectAcl(new GetObjectAclRequest(bucketName, key));
    }

    @Override
    public AccessControlList getObjectAcl(GetObjectAclRequest getObjectAclRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getObjectAclRequest,
                "The request parameter must be specified when requesting an object's ACL");
        rejectNull(getObjectAclRequest.getBucketName(),
                "The bucket name parameter must be specified when requesting an object's ACL");
        rejectNull(getObjectAclRequest.getKey(),
                "The key parameter must be specified when requesting an object's ACL");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when requesting an object acl");

        return getAcl(getObjectAclRequest.getBucketName(), getObjectAclRequest.getKey(),
                getObjectAclRequest.getVersionId(), getObjectAclRequest);
    }

    @Override
    public void setBucketAcl(String bucketName, AccessControlList acl)
            throws CosClientException, CosServiceException {
        setBucketAcl(new SetBucketAclRequest(bucketName, acl));
    }

    @Override
    public void setBucketAcl(String bucketName, CannedAccessControlList acl)
            throws CosClientException, CosServiceException {
        setBucketAcl(new SetBucketAclRequest(bucketName, acl));
    }

    @Override
    public void setBucketAcl(SetBucketAclRequest setBucketAclRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketAclRequest,
                "The SetBucketAclRequest parameter must be specified when setting a bucket's ACL");
        String bucketName = setBucketAclRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting a bucket's ACL");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when setting a bucket acl");

        AccessControlList acl = setBucketAclRequest.getAcl();
        CannedAccessControlList cannedAcl = setBucketAclRequest.getCannedAcl();

        if (acl == null && cannedAcl == null) {
            throw new IllegalArgumentException(
                    "The ACL parameter must be specified when setting a bucket's ACL");
        }
        if (acl != null && cannedAcl != null) {
            throw new IllegalArgumentException(
                    "Only one of the acl and cannedAcl parameter can be specified, not both.");
        }

        if (acl != null) {
            setAcl(bucketName, null, null, acl, setBucketAclRequest);
        } else {
            setAcl(bucketName, null, null, cannedAcl, setBucketAclRequest);
        }
    }

    @Override
    public AccessControlList getBucketAcl(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketAcl(new GetBucketAclRequest(bucketName));
    }

    @Override
    public AccessControlList getBucketAcl(GetBucketAclRequest getBucketAclRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketAclRequest,
                "The bucket name parameter must be specified when requesting a bucket's ACL");
        String bucketName = getBucketAclRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when requesting a bucket's ACL");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when requesting a bucket acl");

        return getAcl(bucketName, null, null, getBucketAclRequest);
    }

    /**
     * <p>
     * Gets the AccessControlList for the specified resource. (bucket if only the bucketName
     * parameter is specified, otherwise the object with the specified key in the bucket).
     * </p>
     *
     * @param bucketName The name of the bucket whose ACL should be returned if the key parameter is
     *        not specified, otherwise the bucket containing the specified key.
     * @param key The object key whose ACL should be retrieve. If not specified, the bucket's ACL is
     *        returned.
     * @param versionId The version ID of the object version whose ACL is being retrieved.
     * @param originalRequest The original, user facing request object.
     *
     * @return The ACL for the specified resource.
     */
    private AccessControlList getAcl(String bucketName, String key, String versionId,
            CosServiceRequest originalRequest) {
        if (originalRequest == null)
            originalRequest = new GenericBucketRequest(bucketName);

        CosHttpRequest<CosServiceRequest> request =
                createRequest(bucketName, key, originalRequest, HttpMethodName.GET);
        request.addParameter("acl", null);
        if (versionId != null) {
            request.addParameter("versionId", versionId);
        }

        @SuppressWarnings("unchecked")
        ResponseHeaderHandlerChain<AccessControlList> responseHandler =
                new ResponseHeaderHandlerChain<AccessControlList>(
                        new Unmarshallers.AccessControlListUnmarshaller(), new COSDefaultAclHeaderHandler());

        return invoke(request, responseHandler);
    }


    /**
     * Sets the ACL for the specified resource in COS. If only bucketName is specified, the ACL will
     * be applied to the bucket, otherwise if bucketName and key are specified, the ACL will be
     * applied to the object.
     *
     * @param bucketName The name of the bucket containing the specified key, or if no key is
     *        listed, the bucket whose ACL will be set.
     * @param key The optional object key within the specified bucket whose ACL will be set. If not
     *        specified, the bucket ACL will be set.
     * @param versionId The version ID of the object version whose ACL is being set.
     * @param acl The ACL to apply to the resource.
     * @param originalRequest The original, user facing request object.
     */
    private void setAcl(String bucketName, String key, String versionId, AccessControlList acl,
            CosServiceRequest originalRequest) {
        if (originalRequest == null)
            originalRequest = new GenericBucketRequest(bucketName);

        CosHttpRequest<CosServiceRequest> request =
                createRequest(bucketName, key, originalRequest, HttpMethodName.PUT);
        request.addParameter("acl", null);
        if (versionId != null)
            request.addParameter("versionId", versionId);

        byte[] aclAsXml = new AclXmlFactory().convertToXmlByteArray(acl);
        request.addHeader("Content-Type", "application/xml");
        request.addHeader("Content-Length", String.valueOf(aclAsXml.length));
        request.setContent(new ByteArrayInputStream(aclAsXml));

        invoke(request, voidCosResponseHandler);
    }

    private void setAcl(String bucketName, String key, String versionId,
            CannedAccessControlList cannedAcl, CosServiceRequest originalRequest)
            throws CosClientException, CosServiceException {
        if (originalRequest == null)
            originalRequest = new GenericBucketRequest(bucketName);

        CosHttpRequest<CosServiceRequest> request =
                createRequest(bucketName, key, originalRequest, HttpMethodName.PUT);
        request.addParameter("acl", null);
        request.addHeader(Headers.COS_CANNED_ACL, cannedAcl.toString());
        if (versionId != null)
            request.addParameter("versionId", versionId);

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketCrossOriginConfiguration(
                new GetBucketCrossOriginConfigurationRequest(bucketName));
    }

    @Override
    public BucketCrossOriginConfiguration getBucketCrossOriginConfiguration(
            GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketCrossOriginConfigurationRequest,
                "The request object parameter getBucketCrossOriginConfigurationRequest must be specified.");
        String bucketName = getBucketCrossOriginConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name must be specified when retrieving the bucket cross origin configuration.");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when retrieving the bucket cross origin configuration");

        CosHttpRequest<GetBucketCrossOriginConfigurationRequest> request = createRequest(bucketName,
                null, getBucketCrossOriginConfigurationRequest, HttpMethodName.GET);
        request.addParameter("cors", null);

        try {
            return invoke(request, new Unmarshallers.BucketCrossOriginConfigurationUnmarshaller());
        } catch (CosServiceException cse) {
            switch (cse.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw cse;
            }
        }
    }

    @Override
    public void setBucketCrossOriginConfiguration(String bucketName,
            BucketCrossOriginConfiguration bucketCrossOriginConfiguration)
            throws CosClientException, CosServiceException {
        setBucketCrossOriginConfiguration(new SetBucketCrossOriginConfigurationRequest(bucketName,
                bucketCrossOriginConfiguration));;
    }

    @Override
    public void setBucketCrossOriginConfiguration(
            SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketCrossOriginConfigurationRequest,
                "The set bucket cross origin configuration request object must be specified.");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when setting bucket cross origin configuration");

        String bucketName = setBucketCrossOriginConfigurationRequest.getBucketName();
        BucketCrossOriginConfiguration bucketCrossOriginConfiguration =
                setBucketCrossOriginConfigurationRequest.getCrossOriginConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting bucket cross origin configuration.");
        rejectNull(bucketCrossOriginConfiguration,
                "The cross origin configuration parameter must be specified when setting bucket cross origin configuration.");

        CosHttpRequest<SetBucketCrossOriginConfigurationRequest> request = createRequest(bucketName,
                null, setBucketCrossOriginConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("cors", null);

        byte[] content = new BucketConfigurationXmlFactory()
                .convertToXmlByteArray(bucketCrossOriginConfiguration);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", "application/xml");
        request.setContent(new ByteArrayInputStream(content));
        try {
            byte[] md5 = Md5Utils.computeMD5Hash(content);
            String md5Base64 = BinaryUtils.toBase64(md5);
            request.addHeader("Content-MD5", md5Base64);
        } catch (Exception e) {
            throw new CosClientException("Couldn't compute md5 sum", e);
        }

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void deleteBucketCrossOriginConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        deleteBucketCrossOriginConfiguration(
                new DeleteBucketCrossOriginConfigurationRequest(bucketName));
    }

    @Override
    public void deleteBucketCrossOriginConfiguration(
            DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteBucketCrossOriginConfigurationRequest,
                "The delete bucket cross origin configuration request object must be specified.");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when deleting bucket cross origin configuration");

        String bucketName = deleteBucketCrossOriginConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when deleting bucket cross origin configuration.");

        CosHttpRequest<DeleteBucketCrossOriginConfigurationRequest> request =
                createRequest(bucketName, null, deleteBucketCrossOriginConfigurationRequest,
                        HttpMethodName.DELETE);
        request.addParameter("cors", null);
        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void setBucketReplicationConfiguration(String bucketName,
            BucketReplicationConfiguration configuration)
            throws CosClientException, CosServiceException {
        setBucketReplicationConfiguration(
                new SetBucketReplicationConfigurationRequest(bucketName, configuration));
    }

    @Override
    public void setBucketReplicationConfiguration(
            SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketReplicationConfigurationRequest,
                "The set bucket replication configuration request object must be specified.");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when setting bucket replication configuration");

        final String bucketName = setBucketReplicationConfigurationRequest.getBucketName();

        final BucketReplicationConfiguration bucketReplicationConfiguration =
                setBucketReplicationConfigurationRequest.getReplicationConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting replication configuration.");
        rejectNull(bucketReplicationConfiguration,
                "The replication configuration parameter must be specified when setting replication configuration.");

        CosHttpRequest<SetBucketReplicationConfigurationRequest> request = createRequest(bucketName,
                null, setBucketReplicationConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("replication", null);

        final byte[] bytes = new BucketConfigurationXmlFactory()
                .convertToXmlByteArray(bucketReplicationConfiguration);

        request.addHeader("Content-Length", String.valueOf(bytes.length));
        request.addHeader("Content-Type", "application/xml");
        request.setContent(new ByteArrayInputStream(bytes));


        try {
            request.addHeader("Content-MD5", BinaryUtils.toBase64(Md5Utils.computeMD5Hash(bytes)));
        } catch (Exception e) {
            throw new CosClientException(
                    "Not able to compute MD5 of the replication rule configuration. Exception Message : "
                            + e.getMessage(),
                    e);
        }
        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketReplicationConfiguration getBucketReplicationConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketReplicationConfiguration(
                new GetBucketReplicationConfigurationRequest(bucketName));
    }

    @Override
    public BucketReplicationConfiguration getBucketReplicationConfiguration(
            GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketReplicationConfigurationRequest,
                "The bucket request parameter must be specified when retrieving replication configuration");
        String bucketName = getBucketReplicationConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket request must specify a bucket name when retrieving replication configuration");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when retrieving replication configuration");

        CosHttpRequest<GetBucketReplicationConfigurationRequest> request = createRequest(bucketName,
                null, getBucketReplicationConfigurationRequest, HttpMethodName.GET);
        request.addParameter("replication", null);

        return invoke(request, new Unmarshallers.BucketReplicationConfigurationUnmarshaller());
    }

    @Override
    public void deleteBucketReplicationConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        deleteBucketReplicationConfiguration(
                new DeleteBucketReplicationConfigurationRequest(bucketName));
    }

    @Override
    public void deleteBucketReplicationConfiguration(
            DeleteBucketReplicationConfigurationRequest deleteBucketReplicationConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteBucketReplicationConfigurationRequest,
                "The DeleteBucketReplicationConfigurationRequest parameter must be specified when deleting replication configuration");
        final String bucketName = deleteBucketReplicationConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when deleting replication configuration");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when deleting replication configuration");

        CosHttpRequest<DeleteBucketReplicationConfigurationRequest> request =
                createRequest(bucketName, null, deleteBucketReplicationConfigurationRequest,
                        HttpMethodName.DELETE);
        request.addParameter("replication", null);

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration) throws CosClientException {
        return generatePresignedUrl(bucketName, key, expiration, HttpMethodName.GET);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration, HttpMethodName method) throws CosClientException {
        return generatePresignedUrl(bucketName, key, expiration, method, new HashMap<String, String>(), new HashMap<String, String>(), false, true);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration,
            HttpMethodName method, Map<String, String> headers, Map<String, String> params) throws CosClientException {
        return generatePresignedUrl(bucketName, key, expiration, method, headers, params, false, true);
    }

    @Override
    public URL generatePresignedUrl(String bucketName, String key, Date expiration,
            HttpMethodName method, Map<String, String> headers, Map<String, String> params, Boolean signPrefixMode,
            Boolean signHost) throws CosClientException {

        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucketName, key, method);
        request.setExpiration(expiration);

        for (Entry<String, String> entry : params.entrySet()) {
            request.addRequestParameter(entry.getKey(), entry.getValue());
        }

        if (signHost) {
            request.putCustomRequestHeader(Headers.HOST, this.clientConfig.getEndpointBuilder().buildGeneralApiEndpoint(bucketName));
        }

        for (Entry<String, String> entry : headers.entrySet()) {
            request.putCustomRequestHeader(entry.getKey(), entry.getValue());
        }

        request.setSignPrefixMode(signPrefixMode);

        return generatePresignedUrl(request, signHost);
    }

    @Override
    public URL generatePresignedUrl(GeneratePresignedUrlRequest req) throws CosClientException {
        return generatePresignedUrl(req, true);
    }

    @Override
    public URL generatePresignedUrl(GeneratePresignedUrlRequest req, Boolean signHost) throws CosClientException {
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when generating a pre-signed URL");
        rejectNull(req, "The request parameter must be specified when generating a pre-signed URL");
        req.rejectIllegalArguments();

        final String bucketName = req.getBucketName();
        final String key = req.getKey();
        rejectEmpty(key, "The filepath must be specified when generating a pre-signed URL");

        if (req.getExpiration() == null) {
            req.setExpiration(new Date(
                    System.currentTimeMillis() + this.clientConfig.getSignExpired() * 1000));
        }

        HttpMethodName httpMethod = req.getMethod();

        CosHttpRequest<GeneratePresignedUrlRequest> request =
                createRequest(bucketName, key, req, httpMethod);

        addParameterIfNotNull(request, "versionId", req.getVersionId());

        for (Entry<String, String> entry : req.getRequestParameters().entrySet()) {
            request.addParameter(entry.getKey(), entry.getValue());
        }

        addHeaderIfNotNull(request, Headers.CONTENT_TYPE, req.getContentType());
        addHeaderIfNotNull(request, Headers.CONTENT_MD5, req.getContentMd5());

        // Custom headers that open up the possibility of supporting unexpected
        // cases.
        Map<String, String> customHeaders = req.getCustomRequestHeaders();
        if (customHeaders != null) {
            for (Map.Entry<String, String> e : customHeaders.entrySet()) {
                request.addHeader(e.getKey(), e.getValue());
            }
        }

        if (!signHost) {
            Map<String, String> headers = request.getHeaders();
            if (headers.containsKey(Headers.HOST)) {
                headers.remove(Headers.HOST);
            }
        }

        addResponseHeaderParameters(request, req.getResponseHeaders());

        COSSigner cosSigner = new COSSigner();
        COSCredentials cred = fetchCredential();
        String authStr =
                cosSigner.buildAuthorizationStr(request.getHttpMethod(), request.getResourcePath(),
                        request.getHeaders(), request.getParameters(), cred, req.getExpiration(), signHost);
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(clientConfig.getHttpProtocol().toString()).append("://");
        strBuilder.append(clientConfig.getEndpointBuilder()
                .buildGeneralApiEndpoint(formatBucket(bucketName, cred.getCOSAppId())));
        strBuilder.append(UrlEncoderUtils.encodeUrlPath(formatKey(key)));

        boolean hasAppendFirstParameter = false;
        if (authStr != null) {
            if(req.isSignPrefixMode()) {
                strBuilder.append("?sign=").append(UrlEncoderUtils.encode(authStr));
            } else {

                // urlencode auth string key & value
                String[] authParts = authStr.split("&");
                String[] encodeAuthParts = new String[authParts.length];

                for (int i = 0; i < authParts.length; i++) {
                    String[] kv = authParts[i].split("=", 2);
                    if (kv.length == 2) {
                        encodeAuthParts[i] = StringUtils.join("=", UrlEncoderUtils.encode(kv[0]), UrlEncoderUtils.encode(kv[1]));
                    } else if (kv.length == 1) {
                        encodeAuthParts[i] = StringUtils.join("=", UrlEncoderUtils.encode(kv[0]));
                    }
                }

                authStr = StringUtils.join("&", encodeAuthParts);

                strBuilder.append("?").append(authStr);
            }
            if (cred instanceof COSSessionCredentials) {
                strBuilder.append("&").append(Headers.SECURITY_TOKEN).append("=").append(
                        UrlEncoderUtils.encode(((COSSessionCredentials) cred).getSessionToken()));
            }
            hasAppendFirstParameter = true;
        }

        for (Entry<String, String> entry : request.getParameters().entrySet()) {
            String paramKey = entry.getKey();
            String paramValue = entry.getValue();

            if (!hasAppendFirstParameter) {
                strBuilder.append("?");
                hasAppendFirstParameter = true;
            } else {
                strBuilder.append("&");
            }
            strBuilder.append(UrlEncoderUtils.encode(paramKey));
            if (paramValue != null) {
                strBuilder.append("=").append(UrlEncoderUtils.encode(paramValue));
            }
        }

        try {
            return new URL(strBuilder.toString());
        } catch (MalformedURLException e) {
            throw new CosClientException(e.toString());
        }
    }

    @Override
    public void restoreObject(String bucketName, String key, int expirationInDays)
            throws CosClientException, CosServiceException {
        restoreObject(new RestoreObjectRequest(bucketName, key, expirationInDays));
    }

    @Override
    public void restoreObject(RestoreObjectRequest restoreObjectRequest)
            throws CosClientException, CosServiceException {
        rejectNull(restoreObjectRequest,
                "The RestoreObjectRequest parameter must be specified when restore a object.");
        String bucketName = restoreObjectRequest.getBucketName();
        String key = restoreObjectRequest.getKey();
        String versionId = restoreObjectRequest.getVersionId();
        int expirationIndays = restoreObjectRequest.getExpirationInDays();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when copying a cas object");
        rejectNull(key, "The key parameter must be specified when copying a cas object");
        if (expirationIndays == -1) {
            throw new IllegalArgumentException(
                    "The expiration in days parameter must be specified when copying a cas object");
        }

        CosHttpRequest<RestoreObjectRequest> request =
                createRequest(bucketName, key, restoreObjectRequest, HttpMethodName.POST);
        request.addParameter("restore", null);
        addParameterIfNotNull(request, "versionId", versionId);


        byte[] content = RequestXmlFactory.convertToXmlByteArray(restoreObjectRequest);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", "application/xml");
        request.setContent(new ByteArrayInputStream(content));
        try {
            byte[] md5 = Md5Utils.computeMD5Hash(content);
            String md5Base64 = BinaryUtils.toBase64(md5);
            request.addHeader("Content-MD5", md5Base64);
        } catch (Exception e) {
            throw new CosClientException("Couldn't compute md5 sum", e);
        }

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void updateObjectMetaData(String bucketName, String key, ObjectMetadata objectMetadata)
            throws CosClientException, CosServiceException {
        CopyObjectRequest copyObjectRequest =
                new CopyObjectRequest(bucketName, key, bucketName, key);
        copyObjectRequest.setNewObjectMetadata(objectMetadata);
        copyObject(copyObjectRequest);
    }

    @Override
    public void setBucketPolicy(String bucketName, String policyText)
            throws CosClientException, CosServiceException {
        setBucketPolicy(new SetBucketPolicyRequest(bucketName, policyText));
    }

    @Override
    public BucketLoggingConfiguration getBucketLoggingConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketLoggingConfiguration(new GetBucketLoggingConfigurationRequest(bucketName));
    }

    @Override
    public BucketLoggingConfiguration getBucketLoggingConfiguration(GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketLoggingConfigurationRequest, "The request object parameter getBucketLoggingConfigurationRequest must be specifed.");
        String bucketName = getBucketLoggingConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when requesting a bucket's logging status");

        CosHttpRequest<GetBucketLoggingConfigurationRequest> request = createRequest(bucketName, null, getBucketLoggingConfigurationRequest, HttpMethodName.GET);
        request.addParameter("logging", null);

        return invoke(request, new Unmarshallers.BucketLoggingConfigurationnmarshaller());
    }

    @Override
    public void setBucketLoggingConfiguration(SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketLoggingConfigurationRequest,
                "The set bucket logging configuration request object must be specified when enabling server access logging");

        String bucketName = setBucketLoggingConfigurationRequest.getBucketName();
        BucketLoggingConfiguration loggingConfiguration = setBucketLoggingConfigurationRequest.getLoggingConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when enabling server access logging");
        rejectNull(loggingConfiguration,
                "The logging configuration parameter must be specified when enabling server access logging");

        CosHttpRequest<SetBucketLoggingConfigurationRequest> request = createRequest(bucketName, null, setBucketLoggingConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("logging", null);

        byte[] bytes = new BucketConfigurationXmlFactory().convertToXmlByteArray(loggingConfiguration);
        request.setContent(new ByteArrayInputStream(bytes));

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void setBucketPolicy(SetBucketPolicyRequest setBucketPolicyRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketPolicyRequest,
                "The request object must be specified when setting a bucket policy");

        String bucketName = setBucketPolicyRequest.getBucketName();
        String policyText = setBucketPolicyRequest.getPolicyText();

        rejectNull(bucketName, "The bucket name must be specified when setting a bucket policy");
        rejectNull(policyText, "The policy text must be specified when setting a bucket policy");

        CosHttpRequest<SetBucketPolicyRequest> request =
                createRequest(bucketName, null, setBucketPolicyRequest, HttpMethodName.PUT);
        request.addParameter("policy", null);
        request.setContent(new ByteArrayInputStream(policyText.getBytes(StringUtils.UTF8)));
        request.addHeader(Headers.CONTENT_LENGTH, String.valueOf(policyText.length()));

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketPolicy getBucketPolicy(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketPolicy(new GetBucketPolicyRequest(bucketName));
    }

    @Override
    public BucketPolicy getBucketPolicy(GetBucketPolicyRequest getBucketPolicyRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketPolicyRequest,
                "The request object must be specified when getting a bucket policy");

        String bucketName = getBucketPolicyRequest.getBucketName();
        rejectNull(bucketName, "The bucket name must be specified when getting a bucket policy");

        CosHttpRequest<GetBucketPolicyRequest> request =
                createRequest(bucketName, null, getBucketPolicyRequest, HttpMethodName.GET);
        request.addParameter("policy", null);

        BucketPolicy result = new BucketPolicy();
        try {
            String policyText = invoke(request, new COSStringResponseHandler());
            result.setPolicyText(policyText);
            return result;
        } catch (CosServiceException cse) {
            if (cse.getErrorCode().equals("NoSuchBucketPolicy"))
                return result;
            throw cse;
        }
    }


    @Override
    public void deleteBucketPolicy(String bucketName)
            throws CosClientException, CosServiceException {
        deleteBucketPolicy(new DeleteBucketPolicyRequest(bucketName));
    }

    @Override
    public void deleteBucketPolicy(DeleteBucketPolicyRequest deleteBucketPolicyRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteBucketPolicyRequest,
                "The request object must be specified when deleting a bucket policy");

        String bucketName = deleteBucketPolicyRequest.getBucketName();
        rejectNull(bucketName, "The bucket name must be specified when deleting a bucket policy");

        CosHttpRequest<DeleteBucketPolicyRequest> request =
                createRequest(bucketName, null, deleteBucketPolicyRequest, HttpMethodName.DELETE);
        request.addParameter("policy", null);

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketWebsiteConfiguration(new GetBucketWebsiteConfigurationRequest(bucketName));
    }

    @Override
    public BucketWebsiteConfiguration getBucketWebsiteConfiguration(GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketWebsiteConfigurationRequest, "The request object parameter getBucketWebsiteConfigurationRequest must be specified.");
        String bucketName = getBucketWebsiteConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when requesting a bucket's website configuration");

        CosHttpRequest<GetBucketWebsiteConfigurationRequest> request = createRequest(bucketName, null, getBucketWebsiteConfigurationRequest, HttpMethodName.GET);
        request.addParameter("website", null);
        request.addHeader("Content-Type", "application/xml");

        try {
            return invoke(request, new Unmarshallers.BucketWebsiteConfigurationUnmarshaller());
        } catch (CosServiceException ase) {
            if (ase.getStatusCode() == 404) return null;
            throw ase;
        }
    }

    @Override
    public void setBucketWebsiteConfiguration(String bucketName, BucketWebsiteConfiguration configuration)
            throws CosClientException, CosServiceException {
        setBucketWebsiteConfiguration(new SetBucketWebsiteConfigurationRequest(bucketName, configuration));
    }

    @Override
    public void setBucketWebsiteConfiguration(SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest)
            throws CosClientException, CosServiceException {
        String bucketName = setBucketWebsiteConfigurationRequest.getBucketName();
        BucketWebsiteConfiguration configuration = setBucketWebsiteConfigurationRequest.getConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting a bucket's website configuration");
        rejectNull(configuration,
                "The bucket website configuration parameter must be specified when setting a bucket's website configuration");
        if (configuration.getRedirectAllRequestsTo() == null) {
            rejectNull(configuration.getIndexDocumentSuffix(),
                    "The bucket website configuration parameter must specify the index document suffix when setting a bucket's website configuration");
        }

        CosHttpRequest<SetBucketWebsiteConfigurationRequest> request = createRequest(bucketName, null, setBucketWebsiteConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("website", null);
        request.addHeader("Content-Type", "application/xml");

        byte[] bytes = new BucketConfigurationXmlFactory().convertToXmlByteArray(configuration);
        request.setContent(new ByteArrayInputStream(bytes));

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void deleteBucketWebsiteConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        deleteBucketWebsiteConfiguration(new DeleteBucketWebsiteConfigurationRequest(bucketName));
    }

    @Override
    public void deleteBucketWebsiteConfiguration(DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest)
            throws CosClientException, CosServiceException {
        String bucketName = deleteBucketWebsiteConfigurationRequest.getBucketName();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when deleting a bucket's website configuration");

        CosHttpRequest<DeleteBucketWebsiteConfigurationRequest> request = createRequest(bucketName, null, deleteBucketWebsiteConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("website", null);
        request.addHeader("Content-Type", "application/xml");

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void deleteBucketDomainConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        deleteBucketDomainConfiguration(new DeleteBucketDomainConfigurationRequest(bucketName));
    }

    @Override
    public void deleteBucketDomainConfiguration(DeleteBucketDomainConfigurationRequest deleteBucketDomainConfigurationRequest) {
        String bucketName = deleteBucketDomainConfigurationRequest.getBucketName();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when deleting a bucket's domain configuration");

        CosHttpRequest<DeleteBucketDomainConfigurationRequest> request = createRequest(bucketName, null, deleteBucketDomainConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("domain", null);

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void setBucketDomainConfiguration(String bucketName, BucketDomainConfiguration configuration)
            throws CosClientException, CosServiceException {
        setBucketDomainConfiguration(new SetBucketDomainConfigurationRequest(bucketName, configuration));
    }

    @Override
    public void setBucketDomainConfiguration(SetBucketDomainConfigurationRequest setBucketDomainConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketDomainConfigurationRequest,
                "The request object parameter setBucketDomainConfigurationRequest must be specified.");
        String bucketName = setBucketDomainConfigurationRequest.getBucketName();
        BucketDomainConfiguration configuration = setBucketDomainConfigurationRequest.getConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting a bucket's domain configuration");
        rejectNull(configuration,
                "The bucket domain configuration parameter must be specified when setting a bucket's domain configuration");
        rejectNull(configuration.getDomainRules(),
                "The bucket domain rules must specify the index document suffix when setting a bucket's domain configuration");

        CosHttpRequest<SetBucketDomainConfigurationRequest> request = createRequest(bucketName,
                null, setBucketDomainConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("domain", null);
        request.addHeader("Content-Type", "application/xml");

        byte[] bytes = new BucketConfigurationXmlFactory().convertToXmlByteArray(configuration);
        request.setContent(new ByteArrayInputStream(bytes));

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketDomainConfiguration getBucketDomainConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketDomainConfiguration(new GetBucketDomainConfigurationRequest(bucketName));
    }

    @Override
    public BucketDomainConfiguration getBucketDomainConfiguration(GetBucketDomainConfigurationRequest getBucketDomainConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketDomainConfigurationRequest,
                "The request object parameter getBucketDomainConfigurationRequest must be specified.");
        String bucketName = getBucketDomainConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name must be specified when retrieving the bucket domain configuration.");

        CosHttpRequest<GetBucketDomainConfigurationRequest> request = createRequest(bucketName,
                null, getBucketDomainConfigurationRequest, HttpMethodName.GET);
        request.addParameter("domain", null);

        try {
            return invoke(request, new Unmarshallers.BucketDomainConfigurationUnmarshaller());
        } catch (CosServiceException cse) {
            switch (cse.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw cse;
            }
        }
    }

    @Override
    public void setBucketDomainCertificate(String bucketName, BucketPutDomainCertificate domainCertificate)
            throws CosClientException, CosServiceException{
        setBucketDomainCertificate(new SetBucketDomainCertificateRequest(bucketName,domainCertificate));
    }

    @Override
    public void setBucketDomainCertificate(SetBucketDomainCertificateRequest setBucketDomainCertificateRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketDomainCertificateRequest,
                "The request object parameter setBucketDomainCertificateRequest must be specified.");
        String bucketName = setBucketDomainCertificateRequest.getBucketName();
        BucketPutDomainCertificate domainCertificate = setBucketDomainCertificateRequest.getBucketPutDomainCertificate();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting a bucket's domain certificate");
        rejectNull(domainCertificate,
                "The bucket domain certificate parameter must be specified when setting a bucket's domain certificate");
        rejectNull(domainCertificate.getBucketDomainCertificateInfo(),
                "The bucket domain certificate parameter must be specified when setting a bucket's domain certificate");
        rejectNull(domainCertificate.getDomainList(),
                "The bucket domain lists must specify the index document suffix when setting a bucket's domain certificate");

        CosHttpRequest<SetBucketDomainCertificateRequest> request = createRequest(bucketName,
                null, setBucketDomainCertificateRequest, HttpMethodName.PUT);
        request.addParameter(BucketDomainCertificateParameters.Parameter_Domain_Certificate, null);
        request.addHeader("Content-Type", "application/xml");

        byte[] bytes = new BucketConfigurationXmlFactory().convertToXmlByteArray(domainCertificate);
        request.setContent(new ByteArrayInputStream(bytes));

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketGetDomainCertificate getBucketDomainCertificate(String bucketName, String domainName)
            throws CosClientException, CosServiceException {
        BucketDomainCertificateRequest getBucketDomainCertificateRequest = new BucketDomainCertificateRequest(bucketName);
        getBucketDomainCertificateRequest.setDomainName(domainName);
        return getBucketDomainCertificate(getBucketDomainCertificateRequest);
    }

    @Override
    public BucketGetDomainCertificate getBucketDomainCertificate(BucketDomainCertificateRequest getBucketDomainCertificateRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketDomainCertificateRequest,
                "The request object parameter getBucketDomainCertificateRequest must be specified.");
        String bucketName = getBucketDomainCertificateRequest.getBucketName();
        String domainName = getBucketDomainCertificateRequest.getDomainName();
        rejectNull(bucketName,
                "The bucket name must be specified when retrieving the bucket domain Certificate.");

        rejectNull(domainName,
                "The domain name must be specified when retrieving the bucket domain Certificate.");

        CosHttpRequest<BucketDomainCertificateRequest> request = createRequest(bucketName,
                null, getBucketDomainCertificateRequest, HttpMethodName.GET);
        request.addParameter(BucketDomainCertificateParameters.Parameter_Domain_Certificate, null);
        request.addParameter(BucketDomainCertificateParameters.Parameter_Domain_Name,domainName);


        try {
            return invoke(request, new Unmarshallers.BucketDomainCertificateUnmarshaller());
        } catch (CosServiceException cse) {
            switch (cse.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw cse;
            }
        }
    }

    @Override
    public void deleteBucketDomainCertificate(String bucketName,String domainName)
            throws CosClientException, CosServiceException{
        BucketDomainCertificateRequest deleteBucketDomainCertificateRequest = new BucketDomainCertificateRequest(bucketName);
        deleteBucketDomainCertificateRequest.setDomainName(domainName);
        deleteBucketDomainCertificate(deleteBucketDomainCertificateRequest);
    }

    @Override
    public void deleteBucketDomainCertificate(BucketDomainCertificateRequest deleteBucketDomainCertificateRequest)
            throws CosClientException, CosServiceException{
        rejectNull(deleteBucketDomainCertificateRequest,
                "The request object parameter deleteBucketDomainCertificateRequest must be specified.");
        String bucketName = deleteBucketDomainCertificateRequest.getBucketName();
        String domainName = deleteBucketDomainCertificateRequest.getDomainName();
        rejectNull(bucketName,
                "The bucket name must be specified when removing the bucket domain Certificate.");

        rejectNull(domainName,
                "The domain name must be specified when removing the bucket domain Certificate.");
        CosHttpRequest<BucketDomainCertificateRequest> request = createRequest(bucketName,
                null, deleteBucketDomainCertificateRequest, HttpMethodName.DELETE);
        request.addParameter(BucketDomainCertificateParameters.Parameter_Domain_Certificate, null);
        request.addParameter(BucketDomainCertificateParameters.Parameter_Domain_Name,domainName);
        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void setBucketRefererConfiguration(String bucketName, BucketRefererConfiguration configuration)
            throws CosClientException, CosServiceException {
        setBucketRefererConfiguration(new SetBucketRefererConfigurationRequest(bucketName, configuration));
    }

    @Override
    public void setBucketRefererConfiguration(SetBucketRefererConfigurationRequest setBucketRefererConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketRefererConfigurationRequest,
                "The request object parameter setBucketRefererConfigurationRequest must be specified.");
        String bucketName = setBucketRefererConfigurationRequest.getBucketName();
        BucketRefererConfiguration configuration = setBucketRefererConfigurationRequest.getConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting a bucket's referer configuration");
        rejectNull(configuration,
                "The bucket domain configuration parameter must be specified when setting a bucket's referer configuration");

        CosHttpRequest<SetBucketRefererConfigurationRequest> request = createRequest(bucketName,
                null, setBucketRefererConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("referer", null);
        request.addHeader("Content-Type", "application/xml");

        byte[] bytes = new BucketConfigurationXmlFactory().convertToXmlByteArray(configuration);
        request.setContent(new ByteArrayInputStream(bytes));

        try {
            byte[] md5 = Md5Utils.computeMD5Hash(bytes);
            String md5Base64 = BinaryUtils.toBase64(md5);
            request.addHeader("Content-MD5", md5Base64);
        } catch ( Exception e ) {
            throw new CosClientException("Couldn't compute md5 sum", e);
        }

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public BucketRefererConfiguration getBucketRefererConfiguration(String bucketName)
            throws CosClientException, CosServiceException {
        return getBucketRefererConfiguration(new GetBucketRefererConfigurationRequest(bucketName));
    }

    @Override
    public BucketRefererConfiguration getBucketRefererConfiguration(GetBucketRefererConfigurationRequest getBucketRefererConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketRefererConfigurationRequest,
                "The request object parameter getBucketRefererConfigurationRequest must be specified.");
        String bucketName = getBucketRefererConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name must be specified when retrieving the bucket domain configuration.");

        CosHttpRequest<GetBucketRefererConfigurationRequest> request = createRequest(bucketName,
                null, getBucketRefererConfigurationRequest, HttpMethodName.GET);
        request.addParameter("referer", null);

        try {
            return invoke(request, new Unmarshallers.BucketRefererConfigurationUnmarshaller());
        } catch (CosServiceException cse) {
            switch (cse.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw cse;
            }
        }
    }

    @Override
    public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(
            String bucketName, String id) throws CosClientException, CosServiceException {
        return deleteBucketInventoryConfiguration(
                new DeleteBucketInventoryConfigurationRequest(bucketName, id));
    }

    @Override
    public DeleteBucketInventoryConfigurationResult deleteBucketInventoryConfiguration(
            DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(deleteBucketInventoryConfigurationRequest, "The request cannot be null");
        rejectNull(deleteBucketInventoryConfigurationRequest.getBucketName(), "The bucketName cannot be null");
        rejectNull(deleteBucketInventoryConfigurationRequest.getId(), "The id cannot be null");
        String bucketName = deleteBucketInventoryConfigurationRequest.getBucketName();
        String id = deleteBucketInventoryConfigurationRequest.getId();

        CosHttpRequest<DeleteBucketInventoryConfigurationRequest> request = createRequest(bucketName, null, deleteBucketInventoryConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("inventory", null);
        request.addParameter("id", id);

        return invoke(request, new Unmarshallers.DeleteBucketInventoryConfigurationUnmarshaller());
    }

    @Override
    public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(
            String bucketName, String id) throws CosClientException, CosServiceException {
        return getBucketInventoryConfiguration(
                new GetBucketInventoryConfigurationRequest(bucketName, id));
    }

    @Override
    public GetBucketInventoryConfigurationResult getBucketInventoryConfiguration(
            GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(getBucketInventoryConfigurationRequest, "The request cannot be null");
        rejectNull(getBucketInventoryConfigurationRequest.getBucketName(), "The bucketName cannot be null");
        rejectNull(getBucketInventoryConfigurationRequest.getId(), "The id cannot be null");
        String bucketName = getBucketInventoryConfigurationRequest.getBucketName();
        String id = getBucketInventoryConfigurationRequest.getId();

        CosHttpRequest<GetBucketInventoryConfigurationRequest> request = createRequest(bucketName, null, getBucketInventoryConfigurationRequest, HttpMethodName.GET);
        request.addParameter("inventory", null);
        request.addParameter("id", id);

        return invoke(request, new Unmarshallers.GetBucketInventoryConfigurationUnmarshaller());
    }

    @Override
    public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(
            String bucketName, InventoryConfiguration inventoryConfiguration)
            throws CosClientException, CosServiceException {
        return setBucketInventoryConfiguration(
                new SetBucketInventoryConfigurationRequest(bucketName, inventoryConfiguration));
    }

    @Override
    public SetBucketInventoryConfigurationResult setBucketInventoryConfiguration(
            SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketInventoryConfigurationRequest, "The request cannot be null");
        rejectNull(setBucketInventoryConfigurationRequest.getBucketName(), "The bucketName cannot be null");
        rejectNull(setBucketInventoryConfigurationRequest.getInventoryConfiguration(), "The inventoryConfiguration cannot be null");
        rejectNull(setBucketInventoryConfigurationRequest.getInventoryConfiguration().getId(), "The inventoryConfiguration.id cannot be null");
        final String bucketName = setBucketInventoryConfigurationRequest.getBucketName();
        final InventoryConfiguration inventoryConfiguration = setBucketInventoryConfigurationRequest.getInventoryConfiguration();
        final String id = inventoryConfiguration.getId();

        CosHttpRequest<SetBucketInventoryConfigurationRequest> request = createRequest(bucketName, null, setBucketInventoryConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("inventory", null);
        request.addParameter("id", id);

        if (!setBucketInventoryConfigurationRequest.IsUseInventoryText()) {
            final byte[] bytes = new BucketConfigurationXmlFactory().convertToXmlByteArray(inventoryConfiguration, false);
            request.addHeader("Content-Length", String.valueOf(bytes.length));
            request.addHeader("Content-Type", "application/xml");
            request.setContent(new ByteArrayInputStream(bytes));
        } else {
            final String contentStr = setBucketInventoryConfigurationRequest.getInventoryText();
            if (contentStr == null || contentStr.length() <= 0) {
                throw new IllegalArgumentException("The inventory text should be specified");
            }
            request.addHeader("Content-Length", String.valueOf(contentStr.length()));
            request.addHeader("Content-Type", "application/xml");
            request.setContent(new ByteArrayInputStream(contentStr.getBytes(StringUtils.UTF8)));
        }

        return invoke(request, new Unmarshallers.SetBucketInventoryConfigurationUnmarshaller());
    }

    @Override
    public ListBucketInventoryConfigurationsResult listBucketInventoryConfigurations(ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest)
            throws CosClientException, CosServiceException {
        rejectNull(listBucketInventoryConfigurationsRequest, "The request cannot be null");
        rejectNull(listBucketInventoryConfigurationsRequest.getBucketName(), "The bucketName cannot be null");
        final String bucketName = listBucketInventoryConfigurationsRequest.getBucketName();

        CosHttpRequest<ListBucketInventoryConfigurationsRequest> request = createRequest(bucketName, null, listBucketInventoryConfigurationsRequest, HttpMethodName.GET);
        request.addParameter("inventory", null);
        addParameterIfNotNull(request, "continuation-token", listBucketInventoryConfigurationsRequest.getContinuationToken());

        return invoke(request, new Unmarshallers.ListBucketInventoryConfigurationsUnmarshaller());
    }

    public PostBucketInventoryConfigurationResult postBucketInventoryConfiguration(
            SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest)
            throws CosClientException, CosServiceException {
        rejectNull(setBucketInventoryConfigurationRequest, "The request cannot be null");
        rejectNull(setBucketInventoryConfigurationRequest.getBucketName(), "The bucketName cannot be null");
        rejectNull(setBucketInventoryConfigurationRequest.getInventoryConfiguration(), "The inventoryConfiguration cannot be null");
        rejectNull(setBucketInventoryConfigurationRequest.getInventoryConfiguration().getId(), "The inventoryConfiguration.id cannot be null");
        final String bucketName = setBucketInventoryConfigurationRequest.getBucketName();
        final InventoryConfiguration inventoryConfiguration = setBucketInventoryConfigurationRequest.getInventoryConfiguration();
        final String id = inventoryConfiguration.getId();

        CosHttpRequest<SetBucketInventoryConfigurationRequest> request = createRequest(bucketName, null, setBucketInventoryConfigurationRequest, HttpMethodName.POST);
        request.addParameter("inventory", null);
        request.addParameter("id", id);

        if (!setBucketInventoryConfigurationRequest.IsUseInventoryText()) {
            final byte[] bytes = new BucketConfigurationXmlFactory().convertToXmlByteArray(inventoryConfiguration, true);
            request.addHeader("Content-Length", String.valueOf(bytes.length));
            request.addHeader("Content-Type", "application/xml");
            request.setContent(new ByteArrayInputStream(bytes));
        } else {
            final String contentStr = setBucketInventoryConfigurationRequest.getInventoryText();
            if (contentStr == null || contentStr.length() <= 0) {
                throw new IllegalArgumentException("The inventory text should be specified");
            }
            request.addHeader("Content-Length", String.valueOf(contentStr.length()));
            request.addHeader("Content-Type", "application/xml");
            request.setContent(new ByteArrayInputStream(contentStr.getBytes(StringUtils.UTF8)));
        }

        return invoke(request, new Unmarshallers.PostBucketInventoryConfigurationUnmarshaller());
    }

    @Override
    public BucketTaggingConfiguration getBucketTaggingConfiguration(String bucketName) {
        return getBucketTaggingConfiguration(new GetBucketTaggingConfigurationRequest(bucketName));
    }

    @Override
    public BucketTaggingConfiguration getBucketTaggingConfiguration(GetBucketTaggingConfigurationRequest getBucketTaggingConfigurationRequest) {
        rejectNull(getBucketTaggingConfigurationRequest, "The request object parameter getBucketTaggingConfigurationRequest must be specifed.");
        String bucketName = getBucketTaggingConfigurationRequest.getBucketName();
        rejectNull(bucketName, "The bucket name must be specified when retrieving the bucket tagging configuration.");

        CosHttpRequest<GetBucketTaggingConfigurationRequest> request = createRequest(bucketName, null, getBucketTaggingConfigurationRequest, HttpMethodName.GET);
        request.addParameter("tagging", null);

        try {
            return invoke(request, new Unmarshallers.BucketTaggingConfigurationUnmarshaller());
        } catch (CosServiceException ase) {
            switch (ase.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw ase;
            }
        }
    }

    @Override
    public void setBucketTaggingConfiguration(String bucketName, BucketTaggingConfiguration bucketTaggingConfiguration) {
        setBucketTaggingConfiguration(new SetBucketTaggingConfigurationRequest(bucketName, bucketTaggingConfiguration));
    }

    @Override
    public void setBucketTaggingConfiguration(
            SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest) {
        rejectNull(setBucketTaggingConfigurationRequest,
                "The set bucket tagging configuration request object must be specified.");

        String bucketName = setBucketTaggingConfigurationRequest.getBucketName();
        BucketTaggingConfiguration bucketTaggingConfiguration = setBucketTaggingConfigurationRequest.getTaggingConfiguration();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when setting bucket tagging configuration.");
        rejectNull(bucketTaggingConfiguration,
                "The tagging configuration parameter must be specified when setting bucket tagging configuration.");

        CosHttpRequest<SetBucketTaggingConfigurationRequest> request = createRequest(bucketName, null, setBucketTaggingConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("tagging", null);

        byte[] content = new BucketConfigurationXmlFactory().convertToXmlByteArray(bucketTaggingConfiguration);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", "application/xml");
        request.setContent(new ByteArrayInputStream(content));
        try {
            byte[] md5 = Md5Utils.computeMD5Hash(content);
            String md5Base64 = BinaryUtils.toBase64(md5);
            request.addHeader("Content-MD5", md5Base64);
        } catch ( Exception e ) {
            throw new CosClientException("Couldn't compute md5 sum", e);
        }

        invoke(request, voidCosResponseHandler);
    }

    @Override
    public void deleteBucketTaggingConfiguration(String bucketName) {
        deleteBucketTaggingConfiguration(new DeleteBucketTaggingConfigurationRequest(bucketName));
    }

    @Override
    public void deleteBucketTaggingConfiguration(
            DeleteBucketTaggingConfigurationRequest deleteBucketTaggingConfigurationRequest) {
        rejectNull(deleteBucketTaggingConfigurationRequest,
                "The delete bucket tagging configuration request object must be specified.");

        String bucketName = deleteBucketTaggingConfigurationRequest.getBucketName();
        rejectNull(bucketName,
                "The bucket name parameter must be specified when deleting bucket tagging configuration.");

        CosHttpRequest<DeleteBucketTaggingConfigurationRequest> request = createRequest(bucketName, null, deleteBucketTaggingConfigurationRequest, HttpMethodName.DELETE);
        request.addParameter("tagging", null);

        invoke(request, voidCosResponseHandler);
    }

    private void setContent(CosHttpRequest<?> request, byte[] content, String contentType, boolean setMd5) {
        request.setContent(new ByteArrayInputStream(content));
        request.addHeader("Content-Length", Integer.toString(content.length));
        request.addHeader("Content-Type", contentType);
        if (setMd5) {
            try {
                byte[] md5 = Md5Utils.computeMD5Hash(content);
                String md5Base64 = BinaryUtils.toBase64(md5);
                request.addHeader("Content-MD5", md5Base64);
            } catch (Exception e) {
                throw new CosClientException("Couldn't compute md5 sum", e);
            }
        }
    }

    @Override
    public SelectObjectContentResult selectObjectContent(SelectObjectContentRequest selectRequest) throws CosClientException, CosServiceException {
        rejectNull(selectRequest, "The request parameter must be specified");

        rejectNull(selectRequest.getBucketName(), "The bucket name parameter must be specified when selecting object content.");
        rejectNull(selectRequest.getKey(), "The key parameter must be specified when selecting object content.");

        CosHttpRequest<SelectObjectContentRequest> request = createRequest(selectRequest.getBucketName(), selectRequest.getKey(), selectRequest, HttpMethodName.POST);
        request.addParameter("select", null);
        request.addParameter("select-type", "2");

        populateSSE_C(request, selectRequest.getSSECustomerKey());

        setContent(request, RequestXmlFactory.convertToXmlByteArray(selectRequest), ContentType.APPLICATION_XML.toString(), true);

        COSObject result = invoke(request, new COSObjectResponseHandler());

        // Hold a reference to this client while the InputStream is still
        // around - otherwise a finalizer in the HttpClient may reset the
        // underlying TCP connection out from under us.
        SdkFilterInputStream resultStream = new ServiceClientHolderInputStream(result.getObjectContent(), this);

        return new SelectObjectContentResult().withPayload(new SelectObjectContentEventStream(resultStream));
    }

    @Override
    public GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest getObjectTaggingRequest) {
        rejectNull(getObjectTaggingRequest,
                "The request parameter must be specified when getting the object tags");
        rejectNull(getObjectTaggingRequest.getBucketName(),
                "The request bucketName must be specified when getting the object tags");
        rejectNull(getObjectTaggingRequest.getKey(),
                "The request key must be specified when getting the object tags");

        CosHttpRequest<GetObjectTaggingRequest> request = createRequest(getObjectTaggingRequest.getBucketName(),
                getObjectTaggingRequest.getKey(), getObjectTaggingRequest, HttpMethodName.GET);
        request.addParameter("tagging", null);
        addParameterIfNotNull(request, "versionId", getObjectTaggingRequest.getVersionId());

        ResponseHeaderHandlerChain<GetObjectTaggingResult> handlerChain = new ResponseHeaderHandlerChain<GetObjectTaggingResult>(
                new Unmarshallers.GetObjectTaggingResponseUnmarshaller(),
                new GetObjectTaggingResponseHeaderHandler()
        );

        return invoke(request, handlerChain);
    }

    @Override
    public SetObjectTaggingResult setObjectTagging(SetObjectTaggingRequest setObjectTaggingRequest) {
        rejectNull(setObjectTaggingRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(setObjectTaggingRequest.getBucketName(),
                "The request bucketName must be specified setting the object tags");
        rejectNull(setObjectTaggingRequest.getKey(),
                "The request key must be specified setting the object tags");
        rejectNull(setObjectTaggingRequest.getTagging(),
                "The request tagging must be specified setting the object tags");

        CosHttpRequest<SetObjectTaggingRequest> request = createRequest(setObjectTaggingRequest.getBucketName(),
                setObjectTaggingRequest.getKey(), setObjectTaggingRequest, HttpMethodName.PUT);
        request.addParameter("tagging", null);
        addParameterIfNotNull(request, "versionId", setObjectTaggingRequest.getVersionId());
        byte[] content = new ObjectTaggingXmlFactory().convertToXmlByteArray(setObjectTaggingRequest.getTagging());
        setContent(request, content, "application/xml", true);

        ResponseHeaderHandlerChain<SetObjectTaggingResult> handlerChain = new ResponseHeaderHandlerChain<SetObjectTaggingResult>(
                new Unmarshallers.SetObjectTaggingResponseUnmarshaller(),
                new SetObjectTaggingResponseHeaderHandler()
        );

        return invoke(request, handlerChain);
    }

    @Override
    public DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest deleteObjectTaggingRequest) {
        rejectNull(deleteObjectTaggingRequest, "The request parameter must be specified when delete the object tags");
        rejectNull(deleteObjectTaggingRequest.getBucketName(),
                "The request bucketName must be specified setting the object tags");
        rejectNull(deleteObjectTaggingRequest.getKey(),
                "The request key must be specified setting the object tags");

        CosHttpRequest<DeleteObjectTaggingRequest> request = createRequest(deleteObjectTaggingRequest.getBucketName(),
                deleteObjectTaggingRequest.getKey(), deleteObjectTaggingRequest, HttpMethodName.DELETE);
        request.addParameter("tagging", null);
        addParameterIfNotNull(request, "versionId", deleteObjectTaggingRequest.getVersionId());

        ResponseHeaderHandlerChain<DeleteObjectTaggingResult> handlerChain = new ResponseHeaderHandlerChain<DeleteObjectTaggingResult>(
                new Unmarshallers.DeleteObjectTaggingResponseUnmarshaller(),
                new DeleteObjectTaggingHeaderHandler()
        );

        return invoke(request, handlerChain);
    }

    @Override
    public BucketIntelligentTierConfiguration getBucketIntelligentTierConfiguration(GetBucketIntelligentTierConfigurationRequest getBucketIntelligentTierConfigurationRequest) {
        rejectNull(getBucketIntelligentTierConfigurationRequest, "The request cannot be null");
        rejectNull(getBucketIntelligentTierConfigurationRequest.getBucketName(), "The bucketName cannot be null");
        final String bucketName = getBucketIntelligentTierConfigurationRequest.getBucketName();

        CosHttpRequest<GetBucketIntelligentTierConfigurationRequest> request = createRequest(bucketName, null, getBucketIntelligentTierConfigurationRequest, HttpMethodName.GET);
        request.addParameter("intelligenttiering", null);
        return invoke(request, new Unmarshallers.GetBucketIntelligentTierConfigurationsUnmarshaller());
    }

    @Override
    public BucketIntelligentTierConfiguration getBucketIntelligentTierConfiguration(String bucketName) {
        return getBucketIntelligentTierConfiguration(new GetBucketIntelligentTierConfigurationRequest(bucketName));
    }

    @Override
    public void setBucketIntelligentTieringConfiguration(SetBucketIntelligentTierConfigurationRequest setBucketIntelligentTierConfigurationRequest) {
        rejectNull(setBucketIntelligentTierConfigurationRequest, "The request cannot be null");
        rejectNull(setBucketIntelligentTierConfigurationRequest.getBucketName(), "The bucketName cannot be null");
        BucketIntelligentTierConfiguration bucketIntelligentTierConfiguration = setBucketIntelligentTierConfigurationRequest.getBucketIntelligentTierConfiguration();
        rejectNull(bucketIntelligentTierConfiguration, "Bucket intelligent tier configuration cannot be null");
        final String bucketName = setBucketIntelligentTierConfigurationRequest.getBucketName();

        CosHttpRequest<SetBucketIntelligentTierConfigurationRequest> request = createRequest(bucketName, null, setBucketIntelligentTierConfigurationRequest, HttpMethodName.PUT);
        request.addParameter("intelligenttiering", null);

        byte[] content = new BucketConfigurationXmlFactory().convertToXmlByteArray(bucketIntelligentTierConfiguration);
        request.addHeader("Content-Length", String.valueOf(content.length));
        request.addHeader("Content-Type", "application/xml");
        request.setContent(new ByteArrayInputStream(content));
        invoke(request, voidCosResponseHandler);
    }

    public List<BucketIntelligentTieringConfiguration> listBucketIntelligentTieringConfiguration(String bucketName) throws CosServiceException, CosClientException {
        rejectEmpty(bucketName,
                "The bucket name parameter must be specified when listing bucket IntelligentTieringConfiguration");
        CosHttpRequest<CosServiceRequest> request = createRequest(bucketName, null, new CosServiceRequest(), HttpMethodName.GET);
        request.addParameter("intelligent-tiering", null);

        try {
            return invoke(request, new Unmarshallers.ListBucketTieringConfigurationUnmarshaller());
        } catch (CosServiceException cse) {
            switch (cse.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw cse;
            }
        }
    }

    @Override
    public MediaJobResponse createMediaJobs(MediaJobsRequest req)  {
        this.rejectStartWith(req.getCallBack(),"http","The CallBack parameter mush start with http or https");
        CosHttpRequest<MediaJobsRequest> request = createRequest(req.getBucketName(), "/jobs", req, HttpMethodName.POST);
        this.setContent(request, CIMediaXmlFactory.convertToXmlByteArray(req), "application/xml", false);
        return invoke(request, new Unmarshallers.JobCreatUnmarshaller());
    }

    @Override
    public MediaJobResponseV2 createMediaJobsV2(MediaJobsRequestV2 req)  {
        CosHttpRequest<MediaJobsRequestV2> request = createRequest(req.getBucketName(), "/jobs", req, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(req), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<MediaJobResponseV2>(MediaJobResponseV2.class));
    }

    @Override
    public Boolean cancelMediaJob(MediaJobsRequest req) {
        this.checkCIRequestCommon(req);
        rejectNull(req.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<MediaJobsRequest> request = createRequest(req.getBucketName(), "/jobs/" + req.getJobId(), req, HttpMethodName.PUT);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public MediaJobResponse describeMediaJob(MediaJobsRequest req) {
        this.checkCIRequestCommon(req);
        rejectNull(req.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<MediaJobsRequest> request = createRequest(req.getBucketName(), "/jobs/" + req.getJobId(), req, HttpMethodName.GET);
        return invoke(request, new Unmarshallers.JobUnmarshaller());
    }

    @Override
    public MediaJobResponseV2 describeMediaJobV2(MediaJobsRequest req) {
        this.checkCIRequestCommon(req);
        rejectNull(req.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<MediaJobsRequest> request = createRequest(req.getBucketName(), "/jobs/" + req.getJobId(), req, HttpMethodName.GET);

        return invoke(request, new Unmarshallers.CICommonUnmarshaller<MediaJobResponseV2>(MediaJobResponseV2.class));
    }

    @Override
    public MediaListJobResponse describeMediaJobs(MediaJobsRequest req) {
        this.checkCIRequestCommon(req);
        CosHttpRequest<MediaJobsRequest> request = createRequest(req.getBucketName(), "/jobs", req, HttpMethodName.GET);
        addParameterIfNotNull(request, "queueId", req.getQueueId());
        addParameterIfNotNull(request, "tag", req.getTag());
        addParameterIfNotNull(request, "orderByTime", req.getOrderByTime());
        addParameterIfNotNull(request, "nextToken", req.getNextToken());
        addParameterIfNotNull(request, "size", req.getSize().toString());
        addParameterIfNotNull(request, "states", req.getStates());
        addParameterIfNotNull(request, "startCreationTime", req.getStartCreationTime());
        addParameterIfNotNull(request, "endCreationTime", req.getEndCreationTime());
        MediaListJobResponse response = invoke(request, new Unmarshallers.ListJobUnmarshaller());
        this.checkMediaListJobResponse(response);
        return response;
    }

    @Override
    public MediaListJobResponse describeMediaJobsV2(MediaJobsRequest req) {
        this.checkCIRequestCommon(req);
        CosHttpRequest<MediaJobsRequest> request = createRequest(req.getBucketName(), "/jobs", req, HttpMethodName.GET);
        addParameterIfNotNull(request, "queueId", req.getQueueId());
        addParameterIfNotNull(request, "tag", req.getTag());
        addParameterIfNotNull(request, "orderByTime", req.getOrderByTime());
        addParameterIfNotNull(request, "nextToken", req.getNextToken());
        addParameterIfNotNull(request, "size", req.getSize().toString());
        addParameterIfNotNull(request, "states", req.getStates());
        addParameterIfNotNull(request, "startCreationTime", req.getStartCreationTime());
        addParameterIfNotNull(request, "endCreationTime", req.getEndCreationTime());
        MediaListJobResponse response = invoke(request, new Unmarshallers.CICommonUnmarshaller<MediaListJobResponse>(MediaListJobResponse.class));
        this.checkMediaListJobResponse(response);
        return response;
    }

    @Override
    public MediaListQueueResponse describeMediaQueues(MediaQueueRequest req) {
        this.checkCIRequestCommon(req);
        CosHttpRequest<MediaQueueRequest> request = createRequest(req.getBucketName(), "/queue", req, HttpMethodName.GET);
        addParameterIfNotNull(request, "queueIds", req.getQueueId());
        addParameterIfNotNull(request, "state", req.getState());
        addParameterIfNotNull(request, "pageNumber", req.getPageNumber());
        addParameterIfNotNull(request, "category", req.getCategory());
        addParameterIfNotNull(request, "pageSize", req.getPageSize());
        return invoke(request, new Unmarshallers.ListQueueUnmarshaller());
    }

    @Override
    public MediaQueueResponse updateMediaQueue(MediaQueueRequest mediaQueueRequest) {
        CosHttpRequest<MediaQueueRequest> request = createRequest(mediaQueueRequest.getBucketName(), "/queue/" + mediaQueueRequest.getQueueId(), mediaQueueRequest, HttpMethodName.PUT);
        this.setContent(request, RequestXmlFactory.convertToXmlByteArray(mediaQueueRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.QueueUnmarshaller());
    }

    @Override
    public MediaBucketResponse describeMediaBuckets(MediaBucketRequest mediaBucketRequest) {
        this.checkCIRequestCommon(mediaBucketRequest);
        CosHttpRequest<MediaBucketRequest> request = createRequest(mediaBucketRequest.getBucketName(), "/mediabucket", mediaBucketRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "regions", mediaBucketRequest.getRegions());
        addParameterIfNotNull(request, "bucketNames", mediaBucketRequest.getBucketNames());
        addParameterIfNotNull(request, "bucketName", mediaBucketRequest.getBucketName());
        addParameterIfNotNull(request, "pageNumber", mediaBucketRequest.getPageNumber());
        addParameterIfNotNull(request, "pageSize", mediaBucketRequest.getPageSize());
        return invoke(request, new Unmarshallers.ListBucketUnmarshaller());
    }

    @Override
    public MediaTemplateResponse createMediaTemplate(MediaTemplateRequest templateRequest) {
        rejectNull(templateRequest,
                "The request parameter must be specified setting the object tags");
        CosHttpRequest<MediaTemplateRequest> request = this.createRequest(templateRequest.getBucketName(), "/template", templateRequest, HttpMethodName.POST);
        this.setContent(request, CIMediaXmlFactory.convertToXmlByteArray(templateRequest), "application/xml", false);
        return this.invoke(request, new Unmarshallers.TemplateUnmarshaller());
    }

    @Override
    public Boolean deleteMediaTemplate(MediaTemplateRequest request) {
        this.checkCIRequestCommon(request);
        rejectNull(request.getTemplateId(),
                "The templateId parameter must be specified setting the object tags");
        CosHttpRequest<MediaTemplateRequest> httpRequest = this.createRequest(request.getBucketName(), "/template/" + request.getTemplateId(), request, HttpMethodName.DELETE);
        this.invoke(httpRequest, voidCosResponseHandler);
        return true;
    }

    @Override
    public MediaListTemplateResponse describeMediaTemplates(MediaTemplateRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<MediaTemplateRequest> httpRequest = this.createRequest(request.getBucketName(), "/template", request, HttpMethodName.GET);
        addParameterIfNotNull(httpRequest, "tag", request.getTag());
        addParameterIfNotNull(httpRequest, "category", request.getCategory());
        addParameterIfNotNull(httpRequest, "ids", request.getIds());
        addParameterIfNotNull(httpRequest, "name", request.getName());
        addParameterIfNotNull(httpRequest, "pageNumber", request.getPageNumber());
        addParameterIfNotNull(httpRequest, "pageSize", request.getPageSize());
        return this.invoke(httpRequest, new Unmarshallers.ListTemplateUnmarshaller());
    }

    @Override
    public Boolean updateMediaTemplate(MediaTemplateRequest request) {
        this.checkCIRequestCommon(request);
        rejectNull(request.getTag(),
                "The tag parameter must be specified setting the object tags");
        rejectNull(request.getName(),
                "The name parameter must be specified setting the object tags");
        CosHttpRequest<MediaTemplateRequest> httpRequest = this.createRequest(request.getBucketName(), "/template/" + request.getTemplateId(), request, HttpMethodName.PUT);
        this.setContent(httpRequest, RequestXmlFactory.convertToXmlByteArray(request), "application/xml", false);
        this.invoke(httpRequest, voidCosResponseHandler);
        return true;
    }

    @Override
    public SnapshotResponse generateSnapshot(SnapshotRequest request) {
        this.checkCIRequestCommon(request);
        rejectNull(request.getTime(),
                "The time parameter must be specified setting the object tags");
        rejectNull(request.getInput().getObject(),
                "The input.object parameter must be specified setting the object tags");
        CosHttpRequest<SnapshotRequest> httpRequest = this.createRequest(request.getBucketName(), "/snapshot", request, HttpMethodName.POST);
        this.setContent(httpRequest, RequestXmlFactory.convertToXmlByteArray(request), "application/xml", false);
        return this.invoke(httpRequest, new Unmarshallers.SnapshotUnmarshaller());
    }

    @Override
    public MediaInfoResponse generateMediainfo(MediaInfoRequest request) {
        this.checkCIRequestCommon(request);
        rejectNull(request.getInput().getObject(),
                "The input.object parameter must be specified setting the object tags");
        CosHttpRequest<MediaInfoRequest> httpRequest = this.createRequest(request.getBucketName(), "/mediainfo", request, HttpMethodName.POST);
        this.setContent(httpRequest, CIAuditingXmlFactoryV2.convertToXmlByteArray(request), "application/xml", false);
        return this.invoke(httpRequest, new Unmarshallers.CICommonUnmarshaller<MediaInfoResponse>(MediaInfoResponse.class));
    }

    @Override
    public Boolean deleteWorkflow(MediaWorkflowListRequest request) {
        this.checkCIRequestCommon(request);
        rejectNull(request.getWorkflowId(), "The request parameter must be specified when delete the object tags");
        CosHttpRequest<MediaWorkflowListRequest> httpRequest = this.createRequest(request.getBucketName(), "/workflow/" + request.getWorkflowId(), request, HttpMethodName.DELETE);
        this.invoke(httpRequest, voidCosResponseHandler);
        return true;
    }

    @Override
    public MediaWorkflowListResponse describeWorkflow(MediaWorkflowListRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<MediaWorkflowListRequest> httpRequest = this.createRequest(request.getBucketName(), "/workflow", request, HttpMethodName.GET);
        addParameterIfNotNull(httpRequest, "ids", request.getIds());
        addParameterIfNotNull(httpRequest, "name", request.getName());
        addParameterIfNotNull(httpRequest, "pageNumber", request.getPageNumber());
        addParameterIfNotNull(httpRequest, "pageSize", request.getPageSize());
        return this.invoke(httpRequest, new Unmarshallers.WorkflowListUnmarshaller());
    }

    @Override
    public MediaWorkflowExecutionResponse describeWorkflowExecution(MediaWorkflowListRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<MediaWorkflowListRequest> httpRequest = this.createRequest(request.getBucketName(), "/workflowexecution/" + request.getRunId(), request, HttpMethodName.GET);
        return this.invoke(httpRequest, new Unmarshallers.CICommonUnmarshaller<MediaWorkflowExecutionResponse>(MediaWorkflowExecutionResponse.class));
    }

    @Override
    public MediaWorkflowExecutionsResponse describeWorkflowExecutions(MediaWorkflowListRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<MediaWorkflowListRequest> httpRequest = this.createRequest(request.getBucketName(), "/workflowexecution", request, HttpMethodName.GET);
        addParameterIfNotNull(httpRequest, "workflowId", request.getWorkflowId());
        addParameterIfNotNull(httpRequest, "name", request.getName());
        addParameterIfNotNull(httpRequest, "orderByTime", request.getOrderByTime());
        addParameterIfNotNull(httpRequest, "size", request.getSize());
        addParameterIfNotNull(httpRequest, "states", request.getStates());
        addParameterIfNotNull(httpRequest, "startCreationTime", request.getStartCreationTime());
        addParameterIfNotNull(httpRequest, "endCreationTime", request.getEndCreationTime());
        addParameterIfNotNull(httpRequest, "nextToken", request.getNextToken());
        return this.invoke(httpRequest, new Unmarshallers.WorkflowExecutionsUnmarshaller());
    }

    @Override
    public DocJobResponse createDocProcessJobs(DocJobRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<DocJobRequest> httpRequest = this.createRequest(request.getBucketName(), "/doc_jobs", request, HttpMethodName.POST);
        this.setContent(httpRequest, RequestXmlFactory.convertToXmlByteArray(request), "application/xml", false);
        return this.invoke(httpRequest, new Unmarshallers.DocProcessJobUnmarshaller());
    }

    @Override
    public DocJobResponse describeDocProcessJob(DocJobRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<DocJobRequest> httpRequest = this.createRequest(request.getBucketName(), "/doc_jobs/" + request.getJobId(), request, HttpMethodName.GET);
        return this.invoke(httpRequest, new Unmarshallers.DescribeDocJobUnmarshaller());
    }

    @Override
    public DocJobListResponse describeDocProcessJobs(DocJobListRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<DocJobListRequest> httpRequest = this.createRequest(request.getBucketName(), "/doc_jobs", request, HttpMethodName.GET);
        addParameterIfNotNull(httpRequest, "queueId", request.getQueueId());
        addParameterIfNotNull(httpRequest, "tag", request.getTag());
        addParameterIfNotNull(httpRequest, "orderByTime", request.getOrderByTime());
        addParameterIfNotNull(httpRequest, "nextToken", request.getNextToken());
        addParameterIfNotNull(httpRequest, "size", request.getSize().toString());
        addParameterIfNotNull(httpRequest, "states", request.getStates());
        addParameterIfNotNull(httpRequest, "startCreationTime", request.getStartCreationTime());
        addParameterIfNotNull(httpRequest, "endCreationTime", request.getEndCreationTime());
        return this.invoke(httpRequest, new Unmarshallers.DescribeDocJobsUnmarshaller());
    }

    @Override
    public DocListQueueResponse describeDocProcessQueues(DocQueueRequest docRequest) {
        this.checkCIRequestCommon(docRequest);
        CosHttpRequest<DocQueueRequest> request = createRequest(docRequest.getBucketName(), "/docqueue", docRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "queueIds", docRequest.getQueueId());
        addParameterIfNotNull(request, "state", docRequest.getState());
        addParameterIfNotNull(request, "pageNumber", docRequest.getPageNumber());
        addParameterIfNotNull(request, "pageSize", docRequest.getPageSize());
        return invoke(request, new Unmarshallers.DocListQueueUnmarshaller());
    }

    @Override
    public boolean updateDocProcessQueue(DocQueueRequest docRequest) {
        this.checkCIRequestCommon(docRequest);
        CosHttpRequest<DocQueueRequest> request = createRequest(docRequest.getBucketName(), "/docqueue/" + docRequest.getQueueId(), docRequest, HttpMethodName.PUT);
        this.setContent(request, RequestXmlFactory.convertToXmlByteArray(docRequest), "application/xml", false);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public DocBucketResponse describeDocProcessBuckets(DocBucketRequest docRequest) {
        this.checkCIRequestCommon(docRequest);
        CosHttpRequest<DocBucketRequest> request = createRequest(docRequest.getBucketName(), "/docbucket", docRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "regions", docRequest.getRegions());
        addParameterIfNotNull(request, "bucketNames", docRequest.getBucketNames());
        addParameterIfNotNull(request, "pageNumber", docRequest.getPageNumber());
        addParameterIfNotNull(request, "pageSize", docRequest.getPageSize());
        return invoke(request, new Unmarshallers.DocListBucketUnmarshaller());
    }

    @Override
    public CIUploadResult processImage(ImageProcessRequest imageProcessRequest) {
        rejectNull(imageProcessRequest,
                "The ImageProcessRequest parameter must be specified when requesting an object's metadata");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when requesting an object's metadata");

        String bucketName = imageProcessRequest.getBucketName();
        String key = imageProcessRequest.getKey();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when requesting an object's metadata");
        rejectNull(key, "The key parameter must be specified when requesting an object's metadata");

        CosHttpRequest<ImageProcessRequest> request =
                createRequest(bucketName, key, imageProcessRequest, HttpMethodName.POST);
        request.addParameter("image_process", null);
        request.addHeader(Headers.PIC_OPERATIONS, Jackson.toJsonString(imageProcessRequest.getPicOperations()));
        Map<String, String> customRequestHeader = imageProcessRequest.getCustomRequestHeader();
        if (customRequestHeader != null) {
            for (String headerKey : customRequestHeader.keySet()) {
                request.addHeader(headerKey, customRequestHeader.get(headerKey));
            }
        }

        ObjectMetadata returnedMetadata = invoke(request, new ResponseHeaderHandlerChain<>(
                new Unmarshallers.ImagePersistenceUnmarshaller(), new CosMetadataResponseHandler()));
        if (returnedMetadata.getRequestId() != null) {
            returnedMetadata.getCiUploadResult().setRequestId(returnedMetadata.getRequestId());
        }
        return returnedMetadata.getCiUploadResult();
    }

    @Override
    public ImageAuditingResponse imageAuditing(ImageAuditingRequest imageAuditingRequest) {
        rejectNull(imageAuditingRequest,
                "The imageAuditingRequest parameter must be specified setting the object tags");
        rejectNull(imageAuditingRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageAuditingRequest> request = createRequest(imageAuditingRequest.getBucketName(), imageAuditingRequest.getObjectKey(), imageAuditingRequest, HttpMethodName.GET);
        request.addParameter("ci-process", "sensitive-content-recognition");
        String detectType = imageAuditingRequest.getDetectType();
        if (!"all".equalsIgnoreCase(detectType)) {
            addParameterIfNotNull(request, "detect-type", detectType);
        }
        addParameterIfNotNull(request, "interval", Integer.toString(imageAuditingRequest.getInterval()));
        addParameterIfNotNull(request, "max-frames", Integer.toString(imageAuditingRequest.getMaxFrames()));
        addParameterIfNotNull(request, "biz-type", imageAuditingRequest.getBizType());
        addParameterIfNotNull(request, "detect-url", imageAuditingRequest.getDetectUrl());
        addParameterIfNotNull(request, "large-image-detect", imageAuditingRequest.getLargeImageDetect());
        addParameterIfNotNull(request, "dataid", imageAuditingRequest.getDataId());
        addParameterIfNotNull(request, "async", imageAuditingRequest.getAsync());
        addParameterIfNotNull(request, "callback", imageAuditingRequest.getCallback());
        return invoke(request, new Unmarshallers.ImageAuditingUnmarshaller());
    }

    @Override
    public CreateAuditingPictureJobResponse imageAuditingV2(ImageAuditingRequest customRequest) {
        CosHttpRequest<ImageAuditingRequest> request = createRequest(customRequest.getBucketName(), "/" + customRequest.getObjectKey(), customRequest , HttpMethodName.GET);
        request.addParameter("ci-process", "sensitive-content-recognition");
        addParameterIfNotNull(request, "biz-type", customRequest.getBizType());
        addParameterIfNotNull(request, "detect-url", customRequest.getDetectUrl());
        addParameterIfNotNull(request, "interval", customRequest.getInterval());
        addParameterIfNotNull(request, "max-frames", customRequest.getMaxFrames());
        addParameterIfNotNull(request, "large-image-detect", customRequest.getLargeImageDetect());
        addParameterIfNotNull(request, "dataid", customRequest.getDataId());
        addParameterIfNotNull(request, "async", customRequest.getAsync());
        addParameterIfNotNull(request, "callback", customRequest.getCallback());

        return invoke(request, new Unmarshallers.CICommonUnmarshaller<CreateAuditingPictureJobResponse>(CreateAuditingPictureJobResponse.class));
    }



    @Override
    public VideoAuditingResponse createVideoAuditingJob(VideoAuditingRequest videoAuditingRequest) {
        this.checkCIRequestCommon(videoAuditingRequest);
        this.rejectStartWith(videoAuditingRequest.getConf().getCallback(), "http", "The Conf.CallBack parameter mush start with http or https");
        CosHttpRequest<VideoAuditingRequest> request = createRequest(videoAuditingRequest.getBucketName(), "/video/auditing", videoAuditingRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactory.convertToXmlByteArray(videoAuditingRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.VideoAuditingUnmarshaller());
    }

    @Override
    public VideoAuditingResponse describeAuditingJob(VideoAuditingRequest videoAuditingRequest) {
        this.checkCIRequestCommon(videoAuditingRequest);
        rejectNull(videoAuditingRequest.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<VideoAuditingRequest> request = createRequest(videoAuditingRequest.getBucketName(), "/video/auditing/" + videoAuditingRequest.getJobId(), videoAuditingRequest, HttpMethodName.GET);
        return invoke(request, new Unmarshallers.VideoAuditingJobUnmarshaller());
    }

    @Override
    public AudioAuditingResponse createAudioAuditingJobs(AudioAuditingRequest audioAuditingRequest) {
        this.checkCIRequestCommon(audioAuditingRequest);
        this.rejectStartWith(audioAuditingRequest.getConf().getCallback(), "http", "The Conf.CallBack parameter mush start with http or https");
        CosHttpRequest<AudioAuditingRequest> request = createRequest(audioAuditingRequest.getBucketName(), "/audio/auditing", audioAuditingRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactory.convertToXmlByteArray(audioAuditingRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.AudioAuditingUnmarshaller());
    }

    @Override
    public AudioAuditingResponse describeAudioAuditingJob(AudioAuditingRequest audioAuditingRequest) {
        this.checkCIRequestCommon(audioAuditingRequest);
        rejectNull(audioAuditingRequest.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<AudioAuditingRequest> request = createRequest(audioAuditingRequest.getBucketName(), "/audio/auditing/" + audioAuditingRequest.getJobId(), audioAuditingRequest, HttpMethodName.GET);
        return invoke(request, new Unmarshallers.AudioAuditingJobUnmarshaller());
    }

    @Override
    public ImageLabelResponse getImageLabel(ImageLabelRequest imageLabelRequest) {
        rejectNull(imageLabelRequest,
                "The imageLabelRequest parameter must be specified setting the object tags");
        rejectNull(imageLabelRequest.getBucketName(),
                "The imageLabelRequest.bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageLabelRequest> request = createRequest(imageLabelRequest.getBucketName(), imageLabelRequest.getObjectKey(), imageLabelRequest, HttpMethodName.GET);
        request.addParameter("ci-process", "detect-label");
        addParameterIfNotNull(request,"detect-url", imageLabelRequest.getDetectUrl());
        addParameterIfNotNull(request,"scenes", imageLabelRequest.getScenes());
        return invoke(request, new Unmarshallers.ImageLabelUnmarshaller());
    }

    @Override
    public ImageLabelV2Response getImageLabelV2(ImageLabelV2Request imageLabelV2Request) {
        rejectNull(imageLabelV2Request,
                "The imageAuditingRequest parameter must be specified setting the object tags");
        rejectNull(imageLabelV2Request.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageLabelV2Request> request = createRequest(imageLabelV2Request.getBucketName(), imageLabelV2Request.getObjectKey(), imageLabelV2Request, HttpMethodName.GET);
        request.addParameter("ci-process", "content-analysis");
        addParameterIfNotNull(request, "scenes", imageLabelV2Request.getScenes());
        System.out.println(request.getEndpoint());
        return invoke(request, new Unmarshallers.ImageLabelV2Unmarshaller());
    }

    @Override
    public TextAuditingResponse createAuditingTextJobs(TextAuditingRequest textAuditingRequest) {
        this.checkCIRequestCommon(textAuditingRequest);
        this.rejectStartWith(textAuditingRequest.getConf().getCallback(), "http", "The Conf.CallBack parameter mush start with http or https");
        CosHttpRequest<TextAuditingRequest> request = createRequest(textAuditingRequest.getBucketName(), "/text/auditing", textAuditingRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactory.convertToXmlByteArray(textAuditingRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<TextAuditingResponse>(TextAuditingResponse.class));
    }

    @Override
    public TextAuditingResponse describeAuditingTextJob(TextAuditingRequest textAuditingRequest) {
        this.checkCIRequestCommon(textAuditingRequest);
        rejectNull(textAuditingRequest.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<TextAuditingRequest> request = createRequest(textAuditingRequest.getBucketName(), "/text/auditing/" + textAuditingRequest.getJobId(), textAuditingRequest, HttpMethodName.GET);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<TextAuditingResponse>(TextAuditingResponse.class));
    }

    @Override
    public DocumentAuditingResponse createAuditingDocumentJobs(DocumentAuditingRequest documentAuditingRequest) {
        this.checkCIRequestCommon(documentAuditingRequest);
        this.rejectStartWith(documentAuditingRequest.getConf().getCallback(), "http", "The Conf.CallBack parameter mush start with http or https");
        CosHttpRequest<DocumentAuditingRequest> request = createRequest(documentAuditingRequest.getBucketName(), "/document/auditing", documentAuditingRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactory.convertToXmlByteArray(documentAuditingRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.DocumentAuditingJobUnmarshaller());
    }

    @Override
    public DocumentAuditingResponse describeAuditingDocumentJob(DocumentAuditingRequest documentAuditingRequest) {
        this.checkCIRequestCommon(documentAuditingRequest);
        rejectNull(documentAuditingRequest.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<DocumentAuditingRequest> request = createRequest(documentAuditingRequest.getBucketName(), "/document/auditing/" + documentAuditingRequest.getJobId(), documentAuditingRequest, HttpMethodName.GET);
        return invoke(request, new Unmarshallers.DocumentAuditingDescribeJobUnmarshaller());
    }

    @Override
    public BatchImageAuditingResponse batchImageAuditing(BatchImageAuditingRequest batchImageAuditingRequest) {
        this.checkCIRequestCommon(batchImageAuditingRequest);
        this.rejectStartWith(batchImageAuditingRequest.getConf().getCallback(), "http", "The Conf.CallBack parameter mush start with http or https");
        CosHttpRequest<BatchImageAuditingRequest> request = createRequest(batchImageAuditingRequest.getBucketName(), "/image/auditing", batchImageAuditingRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactory.convertToXmlByteArray(batchImageAuditingRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.BatchImageAuditingJobUnmarshaller());
    }

    @Override
    public Boolean createDocProcessBucket(DocBucketRequest docBucketRequest) {
        this.checkCIRequestCommon(docBucketRequest);
        CosHttpRequest<DocBucketRequest> request = createRequest(docBucketRequest.getBucketName(), "/docbucket", docBucketRequest, HttpMethodName.POST);
        this.invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public Boolean createMediaProcessBucket(MediaBucketRequest mediaBucketRequest) {
        this.checkCIRequestCommon(mediaBucketRequest);
        CosHttpRequest<MediaBucketRequest> request = createRequest(mediaBucketRequest.getBucketName(), "/mediabucket", mediaBucketRequest, HttpMethodName.POST);
        this.invoke(request, voidCosResponseHandler);
        return true;
    }

    public String GenerateDocPreviewUrl(DocHtmlRequest docJobRequest) throws URISyntaxException {
        return generateDocPreviewUrl(docJobRequest);
    }

    @Override
    public String generateDocPreviewUrl(DocHtmlRequest docJobRequest) throws URISyntaxException {
        rejectNull(docJobRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(docJobRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        rejectNull(docJobRequest.getObjectKey(),
                "The objectKey parameter must be specified setting the object tags");
        CosHttpRequest<DocHtmlRequest> request = createRequest(docJobRequest.getBucketName(), docJobRequest.getObjectKey(), docJobRequest, HttpMethodName.GET);
        return buildDocPreview(request);
    }

    @Override
    public WebpageAuditingResponse createWebpageAuditingJob(WebpageAuditingRequest webpageAuditingRequest) {
        this.checkCIRequestCommon(webpageAuditingRequest);
        this.rejectStartWith(webpageAuditingRequest.getInput().getUrl(), "http", "The Conf.CallBack parameter mush start with http or https");
        CosHttpRequest<WebpageAuditingRequest> request = createRequest(webpageAuditingRequest.getBucketName(), "/webpage/auditing", webpageAuditingRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactory.convertToXmlByteArray(webpageAuditingRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.WebpageAuditingJobUnmarshaller());
    }

    @Override
    public WebpageAuditingResponse describeWebpageAuditingJob(WebpageAuditingRequest webpageAuditingRequest) {
        this.checkCIRequestCommon(webpageAuditingRequest);
        rejectNull(webpageAuditingRequest.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<WebpageAuditingRequest> request = createRequest(webpageAuditingRequest.getBucketName(), "/webpage/auditing/" + webpageAuditingRequest.getJobId(), webpageAuditingRequest, HttpMethodName.GET);
        return invoke(request, new Unmarshallers.WebpageAuditingDescribeJobUnmarshaller());
    }

    @Override
    public String reportBadCase(ReportBadCaseRequest reportBadCaseRequest) {
        this.checkCIRequestCommon(reportBadCaseRequest);
        CosHttpRequest<ReportBadCaseRequest> request = createRequest(reportBadCaseRequest.getBucketName(), "/report/badcase", reportBadCaseRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactory.convertToXmlByteArray(reportBadCaseRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.ReportBadCaseUnmarshaller());
    }

    private String buildDocPreview(CosHttpRequest<DocHtmlRequest> request) throws URISyntaxException {
        Date expiredTime = new Date(System.currentTimeMillis() + clientConfig.getSignExpired() * 1000);
        HashMap<String, String> params = new HashMap<>();
        params.put("ci-process", "doc-preview");
        DocHtmlRequest originalRequest = request.getOriginalRequest();
        putIfNotNull(params, "dstType", originalRequest.getDstType().toString());
        putIfNotNull(params, "srcType", originalRequest.getSrcType());
        putIfNotNull(params, "page", originalRequest.getPage());
        putIfNotNull(params, "ImageParams", originalRequest.getImageParams());
        putIfNotNull(params, "sheet", originalRequest.getSheet());
        putIfNotNull(params, "password", originalRequest.getPassword());
        putIfNotNull(params, "comment", originalRequest.getComment());
        putIfNotNull(params, "excelPaperDirection", originalRequest.getExcelPaperDirection());
        putIfNotNull(params, "excelPaperSize", originalRequest.getExcelPaperSize());
        putIfNotNull(params, "quality", originalRequest.getQuality());
        putIfNotNull(params, "scale", originalRequest.getScale());
        putIfNotNull(params, "imageDpi", originalRequest.getImageDpi());
        URL url = generatePresignedUrl(request.getBucketName(), request.getResourcePath(), expiredTime, HttpMethodName.GET, new HashMap<String, String>(), params, false, false);
        return url.toString();
    }

    private void putIfNotNull(HashMap<String, String> map, String key, String value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    private void checkCIRequestCommon(CIServiceRequest request) {
        rejectNull(request,
                "The request parameter must be specified setting the object tags");
        rejectNull(request.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
    }

    private void checkMediaListJobResponse(MediaListJobResponse response) {
        List<MediaJobObject> jobsDetailList = response.getJobsDetailList();
        if (jobsDetailList.size() == 1) {
            MediaJobObject mediaJobObject = jobsDetailList.get(0);
            if (mediaJobObject.getQueueId() == null && mediaJobObject.getJobId() == null && mediaJobObject.getCode() == null) {
                jobsDetailList.clear();
            }
        }
    }

    public URL getObjectUrl(String bucketName, String key) {
        return getObjectUrl(new GetObjectRequest(bucketName, key));
    }

    public URL getObjectUrl(String bucketName, String key, String versionId) {
        return getObjectUrl(new GetObjectRequest(bucketName, key, versionId));
    }

    public URL getObjectUrl(GetObjectRequest getObjectRequest) {
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when generating a pre-signed URL");
        rejectNull(getObjectRequest, "The request parameter must be specified when generating a pre-signed URL");

        final String bucketName = getObjectRequest.getBucketName();
        final String key = getObjectRequest.getKey();

        CosHttpRequest<GetObjectRequest> request =
                createRequest(bucketName, key, getObjectRequest, HttpMethodName.GET);

        addParameterIfNotNull(request, "versionId", getObjectRequest.getVersionId());

        COSCredentials cred = fetchCredential();
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(clientConfig.getHttpProtocol().toString()).append("://");
        strBuilder.append(clientConfig.getEndpointBuilder()
                .buildGeneralApiEndpoint(formatBucket(bucketName, cred.getCOSAppId())));
        strBuilder.append(UrlEncoderUtils.encodeUrlPath(formatKey(key)));

        boolean hasAppendFirstParameter = false;
        for (Entry<String, String> entry : request.getParameters().entrySet()) {
            String paramKey = entry.getKey();
            String paramValue = entry.getValue();

            if (!hasAppendFirstParameter) {
                strBuilder.append("?");
                hasAppendFirstParameter = true;
            } else {
                strBuilder.append("&");
            }
            strBuilder.append(UrlEncoderUtils.encode(paramKey));
            if (paramValue != null) {
                strBuilder.append("=").append(UrlEncoderUtils.encode(paramValue));
            }
        }

        try {
            return new URL(strBuilder.toString());
        } catch (MalformedURLException e) {
            throw new CosClientException(e.toString());
        }
    }

    @Deprecated
    public PutAsyncFetchTaskResult putAsyncFetchTask(PutAsyncFetchTaskRequest putAsyncFetchTaskRequest) {
        CosHttpRequest<PutAsyncFetchTaskRequest> request = createRequest(putAsyncFetchTaskRequest.getBucketName(),
                String.format("/%s/", putAsyncFetchTaskRequest.getBucketName()), putAsyncFetchTaskRequest, HttpMethodName.POST);
        PutAsyncFetchTaskSerializer serializer = new PutAsyncFetchTaskSerializer(PutAsyncFetchTaskRequest.class);
        SimpleModule module =
            new SimpleModule("PutAsyncFetchTaskSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(PutAsyncFetchTaskRequest.class, serializer);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);

        String body = null;
        try {
            body = objectMapper.writeValueAsString(putAsyncFetchTaskRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setContent(request, body.getBytes(), "application/json", false);
        return invoke(request, new PutAsyncFetchTaskResultHandler());
    }

    @Deprecated
    public GetAsyncFetchTaskResult getAsyncFetchTask(GetAsyncFetchTaskRequest getAsyncFetchTaskRequest) {
        CosHttpRequest<GetAsyncFetchTaskRequest> request = createRequest(getAsyncFetchTaskRequest.getBucketName(),
                String.format("/%s/%s", getAsyncFetchTaskRequest.getBucketName(), getAsyncFetchTaskRequest.getTaskId()),
                getAsyncFetchTaskRequest, HttpMethodName.GET);
        return invoke(request, new GetAsyncFetchTaskResultHandler());
    }

    @Override
    public ImageAuditingResponse describeAuditingImageJob(DescribeImageAuditingRequest imageAuditingRequest) {
        rejectNull(imageAuditingRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        rejectNull(imageAuditingRequest.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<DescribeImageAuditingRequest> request = createRequest(imageAuditingRequest.getBucketName(), "/image/auditing/" + imageAuditingRequest.getJobId(), imageAuditingRequest, HttpMethodName.GET);
        return invoke(request, new Unmarshallers.ImageAuditingDescribeJobUnmarshaller());
    }

    @Override
    public PrivateM3U8Response getPrivateM3U8(PrivateM3U8Request privateM3U8Request) {
        rejectNull(privateM3U8Request.getExpires(),
                "The expires parameter must be specified setting the object tags , must satisfy the interval [3600, 43200]");
        rejectNull(privateM3U8Request.getBucketName(),
                "The privateM3U8Request.bucketName parameter must be specified setting the object tags");
        CosHttpRequest<PrivateM3U8Request> request = createRequest(privateM3U8Request.getBucketName(), privateM3U8Request.getObject(), privateM3U8Request, HttpMethodName.GET);
        request.addParameter("ci-process", "pm3u8");
        request.addParameter("expires", privateM3U8Request.getExpires());
        return invoke(request, new Unmarshallers.PrivateM3U8Unmarshaller());
    }

    @Override
    public DetectCarResponse detectCar(AIRecRequest AIRecRequest) {
        rejectNull(AIRecRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AIRecRequest> request = createRequest(AIRecRequest.getBucketName(), AIRecRequest.getObjectKey(), AIRecRequest, HttpMethodName.GET);
        request.addParameter("ci-process", "DetectCar");
        addParameterIfNotNull(request, "detect-url", AIRecRequest.getDetectUrl());
        return invoke(request, new Unmarshallers.DetectCarUnmarshaller());
    }

    @Override
    public boolean openImageSearch(OpenImageSearchRequest imageSearchRequest) {
        rejectNull(imageSearchRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(imageSearchRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<OpenImageSearchRequest> request = createRequest(imageSearchRequest.getBucketName(), "/ImageSearchBucket", imageSearchRequest, HttpMethodName.POST);
        this.setContent(request, CImageXmlFactory.convertToXmlByteArray(imageSearchRequest), "application/xml", false);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public boolean addGalleryImages(ImageSearchRequest imageSearchRequest) {
        rejectNull(imageSearchRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(imageSearchRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageSearchRequest> request = createRequest(imageSearchRequest.getBucketName(),  imageSearchRequest.getObjectKey(), imageSearchRequest, HttpMethodName.POST);
        request.addParameter("ci-process", "ImageSearch");
        request.addParameter("action", "AddImage");
        this.setContent(request, CImageXmlFactory.convertToXmlByteArray(imageSearchRequest), "application/xml", false);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public boolean deleteGalleryImages(ImageSearchRequest imageSearchRequest) {
        rejectNull(imageSearchRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(imageSearchRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageSearchRequest> request = createRequest(imageSearchRequest.getBucketName(),  imageSearchRequest.getObjectKey(), imageSearchRequest, HttpMethodName.POST);
        request.addParameter("ci-process", "ImageSearch");
        request.addParameter("action", "DeleteImage");
        this.setContent(request, CImageXmlFactory.convertToXmlByteArray(imageSearchRequest), "application/xml", false);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public ImageSearchResponse searchGalleryImages(ImageSearchRequest imageSearchRequest) {
        rejectNull(imageSearchRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(imageSearchRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageSearchRequest> request = createRequest(imageSearchRequest.getBucketName(), imageSearchRequest.getObjectKey(), imageSearchRequest, HttpMethodName.GET);
        request.addParameter("ci-process", "ImageSearch");
        request.addParameter("action", "SearchImage");
        addParameterIfNotNull(request, "MatchThreshold", imageSearchRequest.getMatchThreshold());
        addParameterIfNotNull(request, "Offset", imageSearchRequest.getOffset());
        addParameterIfNotNull(request, "Limit", imageSearchRequest.getLimit());
        addParameterIfNotNull(request, "Filter", imageSearchRequest.getFilter());
        return invoke(request, new Unmarshallers.SearchImagesUnmarshaller());
    }

    @Override
    public MediaWorkflowListResponse triggerWorkflowList(MediaWorkflowListRequest mediaWorkflowListRequest) {
        rejectNull(mediaWorkflowListRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(mediaWorkflowListRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<MediaWorkflowListRequest> request = createRequest(mediaWorkflowListRequest.getBucketName(), "triggerworkflow", mediaWorkflowListRequest, HttpMethodName.POST);
        addParameterIfNotNull(request, "workflowId", mediaWorkflowListRequest.getWorkflowId());
        addParameterIfNotNull(request, "object", mediaWorkflowListRequest.getObject());
        addParameterIfNotNull(request, "name", mediaWorkflowListRequest.getName());
        if (mediaWorkflowListRequest.getAttachParam() != null) {
            request.addParameter("attachParam", null);
            this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(mediaWorkflowListRequest), "application/xml", false);
        }
        return invoke(request, new Unmarshallers.triggerWorkflowListUnmarshaller());
    }

    @Override
    public InputStream getSnapshot(CosSnapshotRequest snapshotRequest) {
        rejectNull(snapshotRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(snapshotRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        rejectNull(snapshotRequest.getObjectKey(),
                "The objectKey parameter must be specified setting the object tags");
        CosHttpRequest<CosSnapshotRequest> request = this.createRequest(snapshotRequest.getBucketName(), snapshotRequest.getObjectKey(), snapshotRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "ci-process", "snapshot");
        addParameterIfNotNull(request, "time", snapshotRequest.getTime());
        addParameterIfNotNull(request, "width", snapshotRequest.getWidth());
        addParameterIfNotNull(request, "height", snapshotRequest.getHeight());
        addParameterIfNotNull(request, "format", snapshotRequest.getFormat());
        addParameterIfNotNull(request, "rotate", snapshotRequest.getRotate());
        addParameterIfNotNull(request, "mode", snapshotRequest.getMode());
        return this.invoke(request, new CIGetSnapshotResponseHandler());
    }

    @Override
    public String generateQrcode(GenerateQrcodeRequest generateQrcodeRequest) {
        rejectNull(generateQrcodeRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(generateQrcodeRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<GenerateQrcodeRequest> request = this.createRequest(generateQrcodeRequest.getBucketName(), "/", generateQrcodeRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "ci-process", "qrcode-generate");
        addParameterIfNotNull(request, "qrcode-content", generateQrcodeRequest.getQrcodeContent());
        addParameterIfNotNull(request, "mode", generateQrcodeRequest.getMode());
        addParameterIfNotNull(request, "width", generateQrcodeRequest.getWidth());
        return this.invoke(request, new Unmarshallers.GenerateQrcodeUnmarshaller());
    }

    @Override
    public Boolean addImageStyle(ImageStyleRequest imageStyleRequest) {
        rejectNull(imageStyleRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(imageStyleRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageStyleRequest> request = createRequest(imageStyleRequest.getBucketName(), "/", imageStyleRequest, HttpMethodName.PUT);
        request.addParameter("style", "");
        this.setContent(request, CImageXmlFactory.addStyleConvertToXmlByteArray(imageStyleRequest), "application/xml", false);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public ImageStyleResponse getImageStyle(ImageStyleRequest imageStyleRequest) {
        rejectNull(imageStyleRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(imageStyleRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageStyleRequest> request = createRequest(imageStyleRequest.getBucketName(), "/", imageStyleRequest, HttpMethodName.GET);
        request.addParameter("style", "");
        this.setContent(request, CImageXmlFactory.getStyleConvertToXmlByteArray(imageStyleRequest), "application/xml", false);
        return invoke(request,  new Unmarshallers.getImageStyleUnmarshaller());
    }

    @Override
    public Boolean deleteImageStyle(ImageStyleRequest imageStyleRequest) {
        rejectNull(imageStyleRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(imageStyleRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<ImageStyleRequest> request = createRequest(imageStyleRequest.getBucketName(), "/", imageStyleRequest, HttpMethodName.DELETE);
        request.addParameter("style", "");
        this.setContent(request, CImageXmlFactory.deleteStyleConvertToXmlByteArray(imageStyleRequest), "application/xml", false);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public String getObjectDecompressionStatus(String bucketName, String objectKey) {
        rejectNull(bucketName,
            "The bucketName parameter must be specified getting object decompression status");
        rejectNull(objectKey,
            "The objectKey parameter must be specified getting object decompression status");
        CosHttpRequest<CosServiceRequest> request =
            createRequest(bucketName, objectKey, new CosServiceRequest(), HttpMethodName.GET);
        request.addParameter("decompression", null);
        request.addParameter("jobId", objectKey);
        return invoke(request, new COSStringResponseHandler());
    }

  public DecompressionResult postObjectDecompression(DecompressionRequest decompressionRequest) {
      rejectNull(decompressionRequest.getSourceBucketName(),
              "The sourceBucketName parameter must be specified post object decompression");
      rejectNull(decompressionRequest,
              "The decompressionRequest parameter must be specified post object decompression");
      rejectNull(decompressionRequest.getTargetBucketName(),
              "The decompressionRequest parameter must be specified post object decompression");
      rejectNull(decompressionRequest.getResourcesPrefix(),
              "The decompressionRequest parameter must be specified post object decompression");
      if (decompressionRequest.getPrefixReplaced()) {
          rejectNull(decompressionRequest.getTargetKeyPrefix(),
                  "The targetKeyPrefix parameter must be specified post object decompression "
                          + "when prefixReplaced is true");
      }
      CosHttpRequest<DecompressionRequest> request =
              createRequest(decompressionRequest.getSourceBucketName(),
                      decompressionRequest.getObjectKey(), decompressionRequest, HttpMethodName.POST);
      request.addParameter("decompression", null);
      byte[] content = DecompressionRequest.convertToByteArray(decompressionRequest);
      request.addHeader("Content-Length", String.valueOf(content.length));
      request.addHeader("Content-Type", "application/xml");
      request.setContent(new ByteArrayInputStream(content));
      return invoke(request, new COSXmlResponseHandler<>(new Unmarshallers.DecompressionResultUnmarshaller()));
  }

    @Override
    public DecompressionResult getObjectDecompressionStatus(String bucketName, String objectKey, String jobId) {
        rejectNull(bucketName, "The bucketName parameter must be specified getting object decompression status");
        rejectNull(objectKey, "The objectKey parameter must be specified getting object decompression status");
        CosHttpRequest<CosServiceRequest> request = createRequest(bucketName, objectKey, new CosServiceRequest(), HttpMethodName.GET);
        if (jobId != null) {
            request.addParameter("jobId", jobId);
        }
        request.addParameter("decompression", null);
        return invoke(request, new COSXmlResponseHandler<>(new Unmarshallers.DecompressionResultUnmarshaller()));
    }

    @Override
    public ListJobsResult listObjectDecompressionJobs(String bucketName, String jobStatus,
                                                      String sortBy, String maxResults, String nextToken) {
        rejectNull(bucketName,"The bucketName parameter must be specified list object decompression status");
        CosHttpRequest<CosServiceRequest> request =
                createRequest(bucketName, "/", new CosServiceRequest(), HttpMethodName.GET);
        request.addParameter("decompression", null);
        if (jobStatus != null) {
            request.addParameter("jobStatus", jobStatus);
        }
        if (sortBy != null) {
            request.addParameter("sortBy", sortBy);
        }
        if (maxResults != null) {
            request.addParameter("maxResults", maxResults);
        }
        if (nextToken != null) {
            request.addParameter("nextToken", nextToken);
        }
        return invoke(request, new COSXmlResponseHandler<>(new Unmarshallers.ListJobsResultUnmarshaller()));
    }

    @Override
    public MediaJobResponse createPicProcessJob(MediaJobsRequest req) {
        this.checkCIRequestCommon(req);
        this.rejectStartWith(req.getCallBack(),"http","The CallBack parameter mush start with http or https");
        CosHttpRequest<MediaJobsRequest> request = createRequest(req.getBucketName(), "/pic_jobs", req, HttpMethodName.POST);
        this.setContent(request, CIMediaXmlFactory.convertToXmlByteArray(req), "application/xml", false);
        return invoke(request, new Unmarshallers.JobCreatUnmarshaller());
    }

    @Override
    public MediaListQueueResponse describePicProcessQueues(MediaQueueRequest req) {
        this.checkCIRequestCommon(req);
        CosHttpRequest<MediaQueueRequest> request = createRequest(req.getBucketName(), "/picqueue", req, HttpMethodName.GET);
        addParameterIfNotNull(request, "queueIds", req.getQueueId());
        addParameterIfNotNull(request, "state", req.getState());
        addParameterIfNotNull(request, "pageNumber", req.getPageNumber());
        addParameterIfNotNull(request, "pageSize", req.getPageSize());
        return invoke(request, new Unmarshallers.ListQueueUnmarshaller());
    }

    @Override
    public boolean processImage2(CImageProcessRequest imageProcessRequest) {
        rejectNull(imageProcessRequest,
                "The ImageProcessRequest parameter must be specified when requesting an object's metadata");
        rejectNull(clientConfig.getRegion(),
                "region is null, region in clientConfig must be specified when requesting an object's metadata");

        String bucketName = imageProcessRequest.getBucketName();
        String key = imageProcessRequest.getKey();

        rejectNull(bucketName,
                "The bucket name parameter must be specified when requesting an object's metadata");
        rejectNull(key, "The key parameter must be specified when requesting an object's metadata");

        CosHttpRequest<CImageProcessRequest> request =
                createRequest(bucketName, key, imageProcessRequest, HttpMethodName.POST);
        if (imageProcessRequest.getPicOperations() != null) {
            request.addHeader(Headers.PIC_OPERATIONS, Jackson.toJsonString(imageProcessRequest.getPicOperations()));
        }
        Map<String, String> queryParams = imageProcessRequest.getQueryParams();
        if (queryParams != null && !queryParams.isEmpty()) {
            for (String qName : queryParams.keySet()) {
                request.addParameter(qName, queryParams.get(qName));
            }
        }
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public FileProcessJobResponse createFileProcessJob(FileProcessRequest req) {
        this.rejectStartWith(req.getCallBack(),"http","The CallBack parameter mush start with http or https");
        CosHttpRequest<FileProcessRequest> request = createRequest(req.getBucketName(), "/file_jobs", req, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(req), "application/xml", false);
        return invoke(request, new Unmarshallers.FileProcessUnmarshaller());
    }

    @Override
    public FileProcessJobResponse describeFileProcessJob(FileProcessRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<FileProcessRequest> httpRequest = this.createRequest(request.getBucketName(), "/file_jobs/" + request.getJobId(), request, HttpMethodName.GET);
        return this.invoke(httpRequest, new Unmarshallers.CICommonUnmarshaller<FileProcessJobResponse>(FileProcessJobResponse.class));
    }

    @Override
    public BatchJobResponse createInventoryTriggerJob(BatchJobRequest req) {
        CosHttpRequest<BatchJobRequest> request = createRequest(req.getBucketName(), "/inventorytriggerjob", req, HttpMethodName.POST);
        this.setContent(request, CIMediaXmlFactory.convertToXmlByteArray(req), "application/xml", false);
        return invoke(request, new Unmarshallers.BatchJobUnmarshaller());
    }

    @Override
    public BatchJobResponse describeInventoryTriggerJob(BatchJobRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<BatchJobRequest> httpRequest = this.createRequest(request.getBucketName(), "/inventorytriggerjob/" + request.getJobId(), request, HttpMethodName.GET);
        return this.invoke(httpRequest, new Unmarshallers.BatchJobUnmarshaller());
    }

    @Override
    public AutoTranslationBlockResponse autoTranslationBlock(AutoTranslationBlockRequest translationBlockRequest) {
        rejectNull(translationBlockRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(translationBlockRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AutoTranslationBlockRequest> request = createRequest(translationBlockRequest.getBucketName(), "/", translationBlockRequest, HttpMethodName.GET);
        request.addParameter("ci-process","AutoTranslationBlock");
        addParameterIfNotNull(request,"InputText",translationBlockRequest.getInputText());
        addParameterIfNotNull(request,"SourceLang",translationBlockRequest.getSourceLang());
        addParameterIfNotNull(request,"TargetLang",translationBlockRequest.getTargetLang());
        addParameterIfNotNull(request,"TextDomain",translationBlockRequest.getTextDomain());
        addParameterIfNotNull(request,"TextStyle",translationBlockRequest.getTextStyle());
        return this.invoke(request, new Unmarshallers.AutoTranslationBlockUnmarshaller());
    }

    @Override
    public DetectFaceResponse detectFace(DetectFaceRequest detectFaceRequest) {
        rejectNull(detectFaceRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(detectFaceRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<DetectFaceRequest> request = createRequest(detectFaceRequest.getBucketName(), detectFaceRequest.getObjectKey(), detectFaceRequest, HttpMethodName.GET);
        request.addParameter("ci-process","DetectFace");
        addParameterIfNotNull(request,"max-face-num",detectFaceRequest.getMaxFaceNum());
        addParameterIfNotNull(request,"detect-url",detectFaceRequest.getDetectUrl());
        return this.invoke(request, new Unmarshallers.DetectFaceUnmarshaller());
    }

    @Override
    public AIGameRecResponse aiGameRec(AIRecRequest aiRecRequest) {
        rejectNull(aiRecRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(aiRecRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AIRecRequest> request = createRequest(aiRecRequest.getBucketName(), aiRecRequest.getObjectKey(), aiRecRequest, HttpMethodName.GET);
        request.addParameter("ci-process","AIGameRec");
        addParameterIfNotNull(request,"detect-url",aiRecRequest.getDetectUrl());
        return this.invoke(request, new Unmarshallers.AIGameRecUnmarshaller());
    }

    @Override
    public Boolean cancelLiveAuditing(VideoAuditingRequest videoAuditingRequest) {
        rejectNull(videoAuditingRequest,
                "The request parameter must be specified setting the object tags");
        rejectNull(videoAuditingRequest.getBucketName(),
                "The bucketName parameter must be specified setting the object tags");
        rejectNull(videoAuditingRequest.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<VideoAuditingRequest> request = createRequest(videoAuditingRequest.getBucketName(), "/video/cancel_auditing/" + videoAuditingRequest.getJobId(), videoAuditingRequest, HttpMethodName.POST);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public AuditingStrategyResponse addAuditingStrategy(AuditingStrategyRequest auditingStrategyRequest) {
        rejectNull(auditingStrategyRequest, "The request parameter must be specified setting the object tags");
        rejectNull(auditingStrategyRequest.getBucketName(), "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AuditingStrategyRequest> request = createRequest(auditingStrategyRequest.getBucketName(), "/audit/strategy", auditingStrategyRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(auditingStrategyRequest), "application/xml", false);
        addParameterIfNotNull(request, "service", auditingStrategyRequest.getService());
        return invoke(request, new Unmarshallers.AuditingStrategyUnmarshaller());
    }

    @Override
    public AuditingStrategyResponse updateAuditingStrategy(AuditingStrategyRequest auditingStrategyRequest) {
        rejectNull(auditingStrategyRequest, "The request parameter must be specified setting the object tags");
        rejectNull(auditingStrategyRequest.getBucketName(), "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AuditingStrategyRequest> request = createRequest(auditingStrategyRequest.getBucketName(), "/audit/strategy/"+auditingStrategyRequest.getBizType(), auditingStrategyRequest, HttpMethodName.PUT);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(auditingStrategyRequest), "application/xml", false);
        addParameterIfNotNull(request, "service", auditingStrategyRequest.getService());
        return invoke(request, new Unmarshallers.AuditingStrategyUnmarshaller());
    }

    @Override
    public AuditingStrategyResponse describeAuditingStrategy(AuditingStrategyRequest auditingStrategyRequest) {
        rejectNull(auditingStrategyRequest, "The request parameter must be specified setting the object tags");
        rejectNull(auditingStrategyRequest.getBucketName(), "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AuditingStrategyRequest> request = createRequest(auditingStrategyRequest.getBucketName(), "/audit/strategy/"+auditingStrategyRequest.getBizType(), auditingStrategyRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "service", auditingStrategyRequest.getService());
        return invoke(request, new Unmarshallers.AuditingStrategyUnmarshaller());
    }

    @Override
    public AuditingStrategyListResponse describeAuditingStrategyList(AuditingStrategyRequest auditingStrategyRequest) {
        rejectNull(auditingStrategyRequest, "The request parameter must be specified setting the object tags");
        rejectNull(auditingStrategyRequest.getBucketName(), "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AuditingStrategyRequest> request = createRequest(auditingStrategyRequest.getBucketName(), "/audit/strategy", auditingStrategyRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "service", auditingStrategyRequest.getService());
        addParameterIfNotNull(request, "offset", auditingStrategyRequest.getOffset());
        addParameterIfNotNull(request, "limit", auditingStrategyRequest.getLimit());
        return invoke(request, new Unmarshallers.AuditingStrategyListUnmarshaller());
    }

    @Override
    public AuditingTextLibResponse addAuditingTextLib(AuditingTextLibRequest libRequest) {
        rejectNull(libRequest, "The request parameter must be specified setting the object tags");
        rejectNull(libRequest.getBucketName(), "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AuditingTextLibRequest> request = createRequest(libRequest.getBucketName(), "/audit/textlib", libRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(libRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.AuditingTextLibUnmarshaller());
    }

    @Override
    public AuditingTextLibResponse describeAuditingTextLib(AuditingTextLibRequest libRequest) {
        rejectNull(libRequest, "The request parameter must be specified setting the object tags");
        rejectNull(libRequest.getBucketName(), "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AuditingTextLibRequest> request = createRequest(libRequest.getBucketName(), "/audit/textlib", libRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "libid", libRequest.getLibid());
        addParameterIfNotNull(request, "offset", libRequest.getOffset());
        addParameterIfNotNull(request, "limit", libRequest.getLimit());
        return invoke(request, new Unmarshallers.AuditingTextLibUnmarshaller());
    }

    @Override
    public AuditingTextLibResponse updateAuditingTextLib(AuditingTextLibRequest libRequest) {
        rejectNull(libRequest, "The request parameter must be specified setting the object tags");
        rejectNull(libRequest.getBucketName(), "The bucketName parameter must be specified setting the object tags");
        CosHttpRequest<AuditingTextLibRequest> request = createRequest(libRequest.getBucketName(), "/audit/textlib/" + libRequest.getLibid(), libRequest, HttpMethodName.PUT);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(libRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.AuditingTextLibUnmarshaller());
    }

    @Override
    public AuditingTextLibResponse deleteAuditingTextLib(AuditingTextLibRequest libRequest) {
        rejectNull(libRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<AuditingTextLibRequest> request = createRequest(libRequest.getBucketName(), "/audit/textlib/" + libRequest.getLibid(), libRequest, HttpMethodName.DELETE);
        return invoke(request, new Unmarshallers.AuditingTextLibUnmarshaller());
    }

    @Override
    public AuditingKeywordResponse addAuditingLibKeyWord(AuditingKeywordRequest keywordRequest) {
        rejectNull(keywordRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<AuditingKeywordRequest> request = createRequest(keywordRequest.getBucketName(), "/audit/textlib/" + keywordRequest.getLibId() + "/keyword", keywordRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(keywordRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<AuditingKeywordResponse>(AuditingKeywordResponse.class));
    }

    @Override
    public AuditingKeywordResponse describeAuditingKeyWordList(AuditingKeywordRequest keywordRequest) {
        rejectNull(keywordRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<AuditingKeywordRequest> request = createRequest(keywordRequest.getBucketName(), "/audit/textlib/" + keywordRequest.getLibId() + "/keyword", keywordRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "content", keywordRequest.getContent());
        addParameterIfNotNull(request, "label", keywordRequest.getLabel());
        addParameterIfNotNull(request, "offset", keywordRequest.getOffset());
        addParameterIfNotNull(request, "limit", keywordRequest.getLimit());
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<AuditingKeywordResponse>(AuditingKeywordResponse.class));

    }

    @Override
    public AuditingKeywordResponse deleteAuditingKeyWord(AuditingKeywordRequest keywordRequest) {
        rejectNull(keywordRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<AuditingKeywordRequest> request = createRequest(keywordRequest.getBucketName(), "/audit/textlib/" + keywordRequest.getLibId() +"/deletekeyword", keywordRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(keywordRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<AuditingKeywordResponse>(AuditingKeywordResponse.class));
    }

    @Override
    public ImageInspectResponse getImageInspect(ImageInspectRequest inspectRequest) {
        rejectNull(inspectRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<ImageInspectRequest> request = createRequest(inspectRequest.getBucketName(), inspectRequest.getObjectKey(), inspectRequest, HttpMethodName.GET);
        request.addParameter( "ci-process", "ImageInspect");
        return invoke(request, new Unmarshallers.CIJsonUnmarshaller<ImageInspectResponse>(ImageInspectResponse.class));
    }

    @Override
    public MediaJobResponseV2 describeMediaJobV2(MediaJobsRequestV2 req) {
        rejectNull(req.getJobId(),
                "The jobId parameter must be specified setting the object tags");
        CosHttpRequest<MediaJobsRequestV2> request = createRequest(req.getBucketName(), "/jobs/" + req.getJobId(), req, HttpMethodName.GET);
        addParameterIfNotNull(request, "queueId", req.getQueueId());
        addParameterIfNotNull(request, "tag", req.getTag());
        addParameterIfNotNull(request, "orderByTime", req.getOrderByTime());
        addParameterIfNotNull(request, "nextToken", req.getNextToken());
        addParameterIfNotNull(request, "size", req.getSize().toString());
        addParameterIfNotNull(request, "states", req.getStates());
        addParameterIfNotNull(request, "startCreationTime", req.getStartCreationTime());
        addParameterIfNotNull(request, "endCreationTime", req.getEndCreationTime());
        return invoke(request,new Unmarshallers.CICommonUnmarshaller<MediaJobResponseV2>(MediaJobResponseV2.class));
    }

    @Override
    public InputStream aIImageColoring(AIImageColoringRequest aIImageColoringRequest) {
        CosHttpRequest<AIImageColoringRequest> request = createRequest(aIImageColoringRequest.getBucket(), "/" + aIImageColoringRequest.getObjectKey(), aIImageColoringRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "ci-process", aIImageColoringRequest.getCiProcess());
        addParameterIfNotNull(request, "detect-url", aIImageColoringRequest.getDetectUrl());
        return invoke(request, new CIGetSnapshotResponseHandler());
    }

    @Override
    public PostSpeechRecognitionResponse postSpeechRecognition(PostSpeechRecognitionRequest postSpeechRecognitionRequest) {
        rejectNull(postSpeechRecognitionRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<PostSpeechRecognitionRequest> request = createRequest(postSpeechRecognitionRequest.getBucketName(), "/jobs", postSpeechRecognitionRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(postSpeechRecognitionRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<PostSpeechRecognitionResponse>(PostSpeechRecognitionResponse.class));
    }

    @Override
    public boolean faceSearchBucket(FaceSearchBucketRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<FaceSearchBucketRequest> request = createRequest(customRequest.getBucketName(), "/FaceSearchBucket", customRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(customRequest), "application/xml", false);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public CreatePersonResponse createPerson(CreatePersonRequest createPersonRequest) {
        rejectNull(createPersonRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<CreatePersonRequest> request = createRequest(createPersonRequest.getBucketName(), "/" + createPersonRequest.getObjectKey(), createPersonRequest, HttpMethodName.POST);
        request.addParameter("ci-process","FaceSearch");
        request.addParameter("action", "CreatePerson");
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(createPersonRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<CreatePersonResponse>(CreatePersonResponse.class));
    }

    @Override
    public AddPersonFaceResponse addPersonFace(AddPersonFaceRequest addPersonFaceRequest) {
        rejectNull(addPersonFaceRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<AddPersonFaceRequest> request = createRequest(addPersonFaceRequest.getBucketName(), "/" + addPersonFaceRequest.getObjectKey(), addPersonFaceRequest, HttpMethodName.POST);
        request.addParameter("ci-process","FaceSearch");
        request.addParameter("action", "AddPersonFace");
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(addPersonFaceRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<AddPersonFaceResponse>(AddPersonFaceResponse.class));
    }

    @Override
    public SearchPersonFaceResponse searchPersonFace(SearchPersonFaceRequest customRequest) {
        CosHttpRequest<SearchPersonFaceRequest> request = createRequest(customRequest.getBucketName(), "/" + customRequest.getObjectKey(), customRequest, HttpMethodName.GET);
        request.addParameter("ci-process","FaceSearch");
        request.addParameter("action","SearchPersonFace");
        addParameterIfNotNull(request, "ObjectKey", customRequest.getObjectKey());
        addParameterIfNotNull(request, "FaceMatchThreshold", customRequest.getFaceMatchThreshold());
        addParameterIfNotNull(request, "NeedPersonInfo", customRequest.getNeedPersonInfo());
        addParameterIfNotNull(request, "QualityControl", customRequest.getQualityControl());
        addParameterIfNotNull(request, "NeedRotateDetection", customRequest.getNeedRotateDetection());
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<SearchPersonFaceResponse>(SearchPersonFaceResponse.class));
    }

    @Override
    public boolean deletePersonFace(DeletePersonFaceRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<DeletePersonFaceRequest> request = createRequest(customRequest.getBucketName(), "/" + customRequest.getObjectKey(), customRequest, HttpMethodName.POST);
        request.addParameter("ci-process","FaceSearch");
        request.addParameter("action","DeletePersonFace");
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(customRequest), "application/xml", false);
        invoke(request, voidCosResponseHandler);
        return true;
    }

    @Override
    public DNADbFilesResponse describeMediaDnaDbFiles(DNADbFilesRequest dnaDbFilesRequest) {
        rejectNull(dnaDbFilesRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<DNADbFilesRequest> request = createRequest(dnaDbFilesRequest.getBucketName(), "/dnadb_files", dnaDbFilesRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "object", dnaDbFilesRequest.getObject());
        addParameterIfNotNull(request, "dnaDbId", dnaDbFilesRequest.getDnaDbId());
        addParameterIfNotNull(request, "pageNumber", dnaDbFilesRequest.getPageNumber());
        addParameterIfNotNull(request, "pageSize", dnaDbFilesRequest.getPageSize());
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<DNADbFilesResponse>(DNADbFilesResponse.class));
    }

    @Override
    public DNADbConfigsResponse describeMediaDnaDbs(DNADbConfigsRequest dnaDbConfigsRequest) {
        rejectNull(dnaDbConfigsRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<DNADbConfigsRequest> request = createRequest(dnaDbConfigsRequest.getBucketName(), "/dnadb", dnaDbConfigsRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "ids", dnaDbConfigsRequest.getIds());
        addParameterIfNotNull(request, "pageNumber", dnaDbConfigsRequest.getPageNumber());
        addParameterIfNotNull(request, "pageSize", dnaDbConfigsRequest.getPageSize());
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<DNADbConfigsResponse>(DNADbConfigsResponse.class));
    }

    @Override
    public ZipPreviewResponse zipPreview(ZipPreviewRequest zipPreviewRequest) {
        rejectNull(zipPreviewRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<ZipPreviewRequest> request = createRequest(zipPreviewRequest.getBucketName(), zipPreviewRequest.getObjectKey(), zipPreviewRequest, HttpMethodName.GET);
        request.addParameter("ci-process","zippreview");
        addParameterIfNotNull(request, "uncompress-key", zipPreviewRequest.getUncompressKey());
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<ZipPreviewResponse>(ZipPreviewResponse.class));
    }

//    @Override
    public GoodsMattingResponse goodsMatting(GoodsMattingRequest customRequest) {
        CosHttpRequest<GoodsMattingRequest> request = createRequest(customRequest.getBucketName(), "/" + customRequest.getObjectKey(), customRequest, HttpMethodName.GET);
        request.addParameter("ci-process","GoodsMatting");
        addParameterIfNotNull(request, "detect-url", customRequest.getDetectUrl());
        invoke(request, new CIGetResponseHandler());
        return null;
    }

    @Override
    public CreateHLSPlayKeyResponse createHLSPlayKey(CreateHLSPlayKeyRequest customRequest) {

        CosHttpRequest<CreateHLSPlayKeyRequest> request = createRequest(customRequest.getBucketName(), "/playKey", customRequest , HttpMethodName.POST);

        return invoke(request, new Unmarshallers.CICommonUnmarshaller<CreateHLSPlayKeyResponse>(CreateHLSPlayKeyResponse.class));
    }

    @Override
    public GetHLSPlayKeyResponse getHLSPlayKey(GetHLSPlayKeyRequest customRequest) {

        CosHttpRequest<GetHLSPlayKeyRequest> request = createRequest(customRequest.getBucketName(), "/playKey", customRequest , HttpMethodName.GET);


        return invoke(request, new Unmarshallers.CICommonUnmarshaller<GetHLSPlayKeyResponse>(GetHLSPlayKeyResponse.class));
    }

    @Override
    public UpdataHLSPlayKeyResponse updataHLSPlayKey(UpdataHLSPlayKeyRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<UpdataHLSPlayKeyRequest> request = createRequest(customRequest.getBucketName(), "/playKey", customRequest , HttpMethodName.PUT);
        addParameterIfNotNull(request, "masterPlayKey", customRequest.getMasterPlayKey());

        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(request), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<UpdataHLSPlayKeyResponse>(UpdataHLSPlayKeyResponse.class));
    }

    @Override
    public MediaListTemplateResponse describeMediaTemplatesV2(MediaTemplateRequest request) {
        this.checkCIRequestCommon(request);
        CosHttpRequest<MediaTemplateRequest> httpRequest = this.createRequest(request.getBucketName(), "/template", request, HttpMethodName.GET);
        addParameterIfNotNull(httpRequest, "tag", request.getTag());
        addParameterIfNotNull(httpRequest, "category", request.getCategory());
        addParameterIfNotNull(httpRequest, "ids", request.getIds());
        addParameterIfNotNull(httpRequest, "name", request.getName());
        addParameterIfNotNull(httpRequest, "pageNumber", request.getPageNumber());
        addParameterIfNotNull(httpRequest, "pageSize", request.getPageSize());
        return this.invoke(httpRequest, new Unmarshallers.CICommonUnmarshaller<MediaListTemplateResponse>(MediaListTemplateResponse.class));
    }

    @Override
    public InputStream getPlayList(GetPlayListRequest getPlayListRequest) {
        CosHttpRequest<GetPlayListRequest> request = createRequest(getPlayListRequest.getBucketName(), "/getplaylist", getPlayListRequest, HttpMethodName.GET);
        addParameterIfNotNull(request, "object", getPlayListRequest.getObject());
        addParameterIfNotNull(request, "expires", getPlayListRequest.getExpires());

        return invoke(request, new CIGetSnapshotResponseHandler());
    }

    @Override
    public RecognizeLogoResponse recognizeLogo(RecognizeLogoRequest customRequest) {

        CosHttpRequest<RecognizeLogoRequest> request = createRequest(customRequest.getBucketName(), "/", customRequest , HttpMethodName.GET);
        addParameterIfNotNull(request, "ci-process", customRequest.getCiProcess());
        addParameterIfNotNull(request, "detect-url", customRequest.getDetectUrl());

        return invoke(request, new Unmarshallers.CICommonUnmarshaller<RecognizeLogoResponse>(RecognizeLogoResponse.class));
    }

    @Override
    public CreateDatasetResponse createDataset(CreateDatasetRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");
        CosHttpRequest<CreateDatasetRequest> request = createRequest(customRequest.getAppId(), "/dataset", customRequest , HttpMethodName.POST);
        request.addHeader("Accept", "application/json");
        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<CreateDatasetResponse>(CreateDatasetResponse.class));
    }

    @Override
    public CreateDatasetBindingResponse createDatasetBinding(CreateDatasetBindingRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<CreateDatasetBindingRequest> request = createRequest(customRequest.getAppId(), "/datasetbinding", customRequest , HttpMethodName.POST);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<CreateDatasetBindingResponse>(CreateDatasetBindingResponse.class));
    }

    @Override
    public CreateFileMetaIndexResponse createFileMetaIndex(CreateFileMetaIndexRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<CreateFileMetaIndexRequest> request = createRequest(customRequest.getAppId(), "/filemeta", customRequest , HttpMethodName.POST);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<CreateFileMetaIndexResponse>(CreateFileMetaIndexResponse.class));
    }

    @Override
    public DatasetFaceSearchResponse datasetFaceSearch(DatasetFaceSearchRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<DatasetFaceSearchRequest> request = createRequest(customRequest.getAppId(), "/datasetquery/facesearch", customRequest , HttpMethodName.POST);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DatasetFaceSearchResponse>(DatasetFaceSearchResponse.class));
    }

    @Override
    public DatasetSimpleQueryResponse datasetSimpleQuery(DatasetSimpleQueryRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<DatasetSimpleQueryRequest> request = createRequest(customRequest.getAppId(), "/datasetquery/simple", customRequest , HttpMethodName.POST);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DatasetSimpleQueryResponse>(DatasetSimpleQueryResponse.class));
    }

    @Override
    public DeleteDatasetResponse deleteDataset(DeleteDatasetRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<DeleteDatasetRequest> request = createRequest(customRequest.getAppId(), "/dataset", customRequest , HttpMethodName.DELETE);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DeleteDatasetResponse>(DeleteDatasetResponse.class));
    }

    @Override
    public DeleteDatasetBindingResponse deleteDatasetBinding(DeleteDatasetBindingRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<DeleteDatasetBindingRequest> request = createRequest(customRequest.getAppId(), "/datasetbinding", customRequest , HttpMethodName.DELETE);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DeleteDatasetBindingResponse>(DeleteDatasetBindingResponse.class));
    }

    @Override
    public DeleteFileMetaIndexResponse deleteFileMetaIndex(DeleteFileMetaIndexRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<DeleteFileMetaIndexRequest> request = createRequest(customRequest.getAppId(), "/filemeta", customRequest , HttpMethodName.DELETE);
        request.addHeader("Accept", "application/json");
        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DeleteFileMetaIndexResponse>(DeleteFileMetaIndexResponse.class));
    }

    @Override
    public DescribeDatasetResponse describeDataset(DescribeDatasetRequest customRequest) {

        CosHttpRequest<DescribeDatasetRequest> request = createRequest(customRequest.getAppId(), "/dataset", customRequest , HttpMethodName.GET);
        addParameterIfNotNull(request, "datasetname", customRequest.getDatasetname());
        addParameterIfNotNull(request, "statistics", String.valueOf(customRequest.getStatistics()));
        request.addHeader("Accept", "application/json");

        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DescribeDatasetResponse>(DescribeDatasetResponse.class));
    }

    @Override
    public DescribeDatasetBindingResponse describeDatasetBinding(DescribeDatasetBindingRequest customRequest) {

        CosHttpRequest<DescribeDatasetBindingRequest> request = createRequest(customRequest.getAppId(), "/datasetbinding", customRequest , HttpMethodName.GET);
        addParameterIfNotNull(request, "datasetname", customRequest.getDatasetname());
        addParameterIfNotNull(request, "uri", customRequest.getUri());
        request.addHeader("Accept", "application/json");


        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DescribeDatasetBindingResponse>(DescribeDatasetBindingResponse.class));
    }

    @Override
    public DescribeDatasetBindingsResponse describeDatasetBindings(DescribeDatasetBindingsRequest customRequest) {

        CosHttpRequest<DescribeDatasetBindingsRequest> request = createRequest(customRequest.getAppId(), "/datasetbindings", customRequest , HttpMethodName.GET);
        addParameterIfNotNull(request, "datasetname", customRequest.getDatasetname());
        addParameterIfNotNull(request, "maxresults", customRequest.getMaxresults());
        addParameterIfNotNull(request, "nexttoken", customRequest.getNexttoken());
        request.addHeader("Accept", "application/json");
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DescribeDatasetBindingsResponse>(DescribeDatasetBindingsResponse.class));
    }

    @Override
    public DescribeDatasetsResponse describeDatasets(DescribeDatasetsRequest customRequest) {

        CosHttpRequest<DescribeDatasetsRequest> request = createRequest(customRequest.getAppId(), "/datasets", customRequest , HttpMethodName.GET);
        addParameterIfNotNull(request, "maxresults", customRequest.getMaxresults());
        addParameterIfNotNull(request, "nexttoken", customRequest.getNexttoken());
        addParameterIfNotNull(request, "prefix", customRequest.getPrefix());
        request.addHeader("Accept", "application/json");


        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DescribeDatasetsResponse>(DescribeDatasetsResponse.class));
    }

    @Override
    public DescribeFileMetaIndexResponse describeFileMetaIndex(DescribeFileMetaIndexRequest customRequest) {

        CosHttpRequest<DescribeFileMetaIndexRequest> request = createRequest(customRequest.getAppId(), "/filemeta", customRequest , HttpMethodName.GET);
        addParameterIfNotNull(request, "datasetname", customRequest.getDatasetname());
        addParameterIfNotNull(request, "uri", customRequest.getUri());
        request.addHeader("Accept", "application/json");
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<DescribeFileMetaIndexResponse>(DescribeFileMetaIndexResponse.class));
    }

    @Override
    public SearchImageResponse searchImage(SearchImageRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<SearchImageRequest> request = createRequest(customRequest.getAppId(), "/datasetquery/imagesearch", customRequest , HttpMethodName.POST);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<SearchImageResponse>(SearchImageResponse.class));
    }

    @Override
    public UpdateDatasetResponse updateDataset(UpdateDatasetRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<UpdateDatasetRequest> request = createRequest(customRequest.getAppId(), "/dataset", customRequest , HttpMethodName.PUT);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<UpdateDatasetResponse>(UpdateDatasetResponse.class));
    }

    @Override
    public UpdateFileMetaIndexResponse updateFileMetaIndex(UpdateFileMetaIndexRequest customRequest) {
        rejectNull(customRequest, "The request parameter must be specified setting the object tags");

        CosHttpRequest<UpdateFileMetaIndexRequest> request = createRequest(customRequest.getAppId(), "/filemeta", customRequest , HttpMethodName.PUT);
        request.addHeader("Accept", "application/json");

        this.setContent(request, CIJackson.toJsonBytes(customRequest), "application/json", false);
        return invoke(request, new Unmarshallers.CICommonJsonUnmarshaller<UpdateFileMetaIndexResponse>(UpdateFileMetaIndexResponse.class));
    }

    @Override
    public MediaTemplateResponseV2 createMediaTemplateV2(MediaTemplateRequestV2 templateRequest) {
        rejectNull(templateRequest,
                "The request parameter must be specified setting the object tags");
        CosHttpRequest<MediaTemplateRequestV2> request = this.createRequest(templateRequest.getBucketName(), "/template", templateRequest, HttpMethodName.POST);
        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(templateRequest), "application/xml", false);
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<MediaTemplateResponseV2>(MediaTemplateResponseV2.class));
    }

    @Override
    public ImageOCRResponse imageOCR(ImageOCRRequest ocrRequest) {
        CosHttpRequest<ImageOCRRequest> request = createRequest(ocrRequest.getBucketName(), ocrRequest.getObjectKey(), ocrRequest, HttpMethodName.GET);
        request.addParameter("ci-process","OCR");
        addParameterIfNotNull(request, "detect-url", ocrRequest.getDetectUrl());
        addParameterIfNotNull(request, "type", ocrRequest.getType());
        addParameterIfNotNull(request, "language-type", ocrRequest.getLanguageType());
        addParameterIfNotNull(request, "ispdf", String.valueOf(ocrRequest.getPdf()));
        addParameterIfNotNull(request, "pdf-pagenumber", ocrRequest.getPdfPageNumber());
        addParameterIfNotNull(request, "isword", String.valueOf(ocrRequest.getWord()));
        addParameterIfNotNull(request, "enable-word-polygon", String.valueOf(ocrRequest.getEnableWordPolygon()));
        return invoke(request, new Unmarshallers.CICommonUnmarshaller<ImageOCRResponse>(ImageOCRResponse.class));
    }

    public String generateCosDomainPrivateM3U8Url(PrivateM3U8Request privateM3U8Request) {
        CosHttpRequest<PrivateM3U8Request> request = createRequest(privateM3U8Request.getBucketName(), privateM3U8Request.getObject(), privateM3U8Request, HttpMethodName.GET);
        request.addParameter("ci-process","pm3u8");
        addParameterIfNotNull(request,"expires",privateM3U8Request.getExpires());
        addParameterIfNotNull(request,"tokenType",privateM3U8Request.getTokenType());
        addParameterIfNotNull(request,"token",privateM3U8Request.getToken());
        return buildPrivateM3U8Url(request);
    }

    private String buildPrivateM3U8Url(CosHttpRequest<PrivateM3U8Request> request)  {
        Date expiredTime = new Date(System.currentTimeMillis() + clientConfig.getSignExpired() * 1000);
        HashMap<String, String> params = new HashMap<>();
        params.put("ci-process", "pm3u8");
        PrivateM3U8Request originalRequest = request.getOriginalRequest();
        putIfNotNull(params, "expires", originalRequest.getExpires());
        putIfNotNull(params, "tokenType", originalRequest.getTokenType());
        putIfNotNull(params, "token", originalRequest.getToken());
        URL url = generatePresignedUrl(request.getBucketName(), request.getResourcePath(), expiredTime, HttpMethodName.GET, new HashMap<String, String>(), params, false, false);
        return url.toString();
    }

    public void putBucketEncryptionConfiguration(String bucketName, BucketEncryptionConfiguration bucketEncryptionConfiguration)
            throws CosClientException, CosServiceException {
        rejectEmpty(bucketName,
                "The bucket name parameter must be specified when putting bucket encryption");

        CosHttpRequest<CosServiceRequest> request = createRequest(bucketName, null, new CosServiceRequest(), HttpMethodName.PUT);
        request.addParameter("encryption", null);

        rejectEmpty(bucketEncryptionConfiguration.getSseAlgorithm(),
                "The SseAlgorithm parameter must be specified when putting bucket encryption");

        byte[] bytes = new BucketConfigurationXmlFactory().convertToXmlByteArray(bucketEncryptionConfiguration);
        request.setContent(new ByteArrayInputStream(bytes));
        request.addHeader(Headers.CONTENT_LENGTH, String.valueOf(bytes.length));

        invoke(request, voidCosResponseHandler);
    }

    public BucketEncryptionConfiguration getBucketEncryptionConfiguration(String bucketName) throws CosClientException, CosServiceException {
        rejectEmpty(bucketName,
                "The bucket name parameter must be specified when getting bucket encryption");

        CosHttpRequest<CosServiceRequest> request = createRequest(bucketName, null, new CosServiceRequest(), HttpMethodName.GET);
        request.addParameter("encryption", null);

        return invoke(request, new Unmarshallers.BucketEncryptionConfigurationUnmarshaller());
    }

    public void deleteBucketEncryptionConfiguration(String bucketName) throws CosClientException, CosServiceException {
        rejectEmpty(bucketName,
                "The bucket name parameter must be specified when deleting bucket encryption");

        CosHttpRequest<CosServiceRequest> request = createRequest(bucketName, null, new CosServiceRequest(), HttpMethodName.DELETE);
        request.addParameter("encryption", null);

        invoke(request, voidCosResponseHandler);
    }

    public BucketObjectLockConfiguration getBucketObjectLockConfiguration(String bucketName) throws CosClientException, CosServiceException {
        rejectEmpty(bucketName,
                "The bucket name parameter must be specified when getting bucket object lock configuration");

        CosHttpRequest<CosServiceRequest> request = createRequest(bucketName, null, new CosServiceRequest(), HttpMethodName.GET);
        request.addParameter("object-lock", null);

        try {
            return invoke(request, new Unmarshallers.BucketObjectLockConfigurationUnmarshaller());
        } catch (CosServiceException cse) {
            switch (cse.getStatusCode()) {
                case 404:
                    return null;
                default:
                    throw cse;
            }
        }
    }

    public BucketGetMetadataResult getBucketMetadata(String bucketName) throws CosClientException, CosServiceException {
        rejectEmpty(bucketName,
                "The bucket name parameter must be specified when getting bucket metadata");
        BucketGetMetadataResult result = new BucketGetMetadataResult();
        HeadBucketResult headBucketResult = headBucket(new HeadBucketRequest(bucketName));
        result.bucketName = bucketName;
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(clientConfig.getHttpProtocol().toString()).append("://");
        strBuilder.append(clientConfig.getEndpointBuilder()
                .buildGeneralApiEndpoint(formatBucket(bucketName, null)));
        result.bucketUrl = strBuilder.toString();
        result.isMaz = headBucketResult.isMazBucket();
        result.isOFS = headBucketResult.isMergeBucket();
        result.location = headBucketResult.getBucketRegion();

        try {
            BucketEncryptionConfiguration encryptionConfiguration = getBucketEncryptionConfiguration(bucketName);
            result.encryptionConfiguration = encryptionConfiguration;
        } catch (CosServiceException cse) {
            if (cse.getStatusCode() != 404) {
                throw cse;
            }
        }

        result.accessControlList = getBucketAcl(bucketName);
        result.websiteConfiguration = getBucketWebsiteConfiguration(bucketName);
        result.loggingConfiguration = getBucketLoggingConfiguration(bucketName);
        result.crossOriginConfiguration = getBucketCrossOriginConfiguration(bucketName);
        result.versioningConfiguration = getBucketVersioningConfiguration(bucketName);
        result.lifecycleConfiguration = getBucketLifecycleConfiguration(bucketName);

        try {
            result.replicationConfiguration = getBucketReplicationConfiguration(bucketName);
        } catch (CosServiceException cse) {
            if (cse.getStatusCode() != 404) {
                throw cse;
            }
        }

        result.taggingConfiguration = getBucketTaggingConfiguration(bucketName);
        result.tieringConfigurations = listBucketIntelligentTieringConfiguration(bucketName);
        result.bucketObjectLockConfiguration = getBucketObjectLockConfiguration(bucketName);

        return result;
    }

    private void preflightObj(PutObjectRequest putObjectRequest) throws CosClientException, CosServiceException {
        String bucketName = putObjectRequest.getBucketName();
        String key = putObjectRequest.getKey();
        rejectEmpty(bucketName,
                "The bucket name parameter must be specified when doing preflight request");
        rejectEmpty(key,
                "The key parameter must be specified when doing preflight request");
        if (clientConfig.isCheckPreflightStatus() && preflightBuckets.containsKey(bucketName)) {
            String reqMsg = String.format("will do preflight request for put object[%s] to the bucket[%s]", key, bucketName);
            log.debug(reqMsg);
            putObjectRequest.setHasDonePreflight(true);
            CosServiceRequest serviceRequest = new CosServiceRequest();
            Map<String, String> customHeaders = putObjectRequest.getCustomRequestHeaders();
            if (customHeaders != null) {
                for (Map.Entry<String, String> e : customHeaders.entrySet()) {
                    serviceRequest.putCustomRequestHeader(e.getKey(), e.getValue());
                }
            }
            CosHttpRequest<CosServiceRequest> request = createRequest(bucketName, key, serviceRequest, HttpMethodName.HEAD);
            if (putObjectRequest.getFixedEndpointAddr() != null) {
                request.setEndpoint(putObjectRequest.getFixedEndpointAddr());
            }
            request.addParameter("preflight", null);
            ObjectMetadata metadata = putObjectRequest.getMetadata();
            if (metadata != null) {
                populateRequestMetadata(request, metadata);
            }
            request.addHeader("x-cos-next-action", "PutObject");
            invoke(request, voidCosResponseHandler);
        }
    }

    private void preflightObj(UploadPartRequest uploadPartRequest) throws CosClientException, CosServiceException {
        String bucketName = uploadPartRequest.getBucketName();
        String key = uploadPartRequest.getKey();
        rejectEmpty(bucketName,
                "The bucket name parameter must be specified when doing preflight request");
        rejectEmpty(key,
                "The key parameter must be specified when doing preflight request");
        if (clientConfig.isCheckPreflightStatus() && preflightBuckets.containsKey(bucketName)) {
            String reqMsg = String.format("will do preflight request for upload part object[%s] to the bucket[%s]", key, bucketName);
            log.debug(reqMsg);
            uploadPartRequest.setHasDonePreflight(true);
            CosServiceRequest serviceRequest = new CosServiceRequest();
            Map<String, String> customHeaders = uploadPartRequest.getCustomRequestHeaders();
            if (customHeaders != null) {
                for (Map.Entry<String, String> e : customHeaders.entrySet()) {
                    serviceRequest.putCustomRequestHeader(e.getKey(), e.getValue());
                }
            }
            CosHttpRequest<CosServiceRequest> request = createRequest(bucketName, key, serviceRequest, HttpMethodName.HEAD);
            if (uploadPartRequest.getFixedEndpointAddr() != null) {
                request.setEndpoint(uploadPartRequest.getFixedEndpointAddr());
            }
            request.addParameter("preflight", null);
            ObjectMetadata metadata = uploadPartRequest.getObjectMetadata();
            if (metadata != null) {
                populateRequestMetadata(request, metadata);
            }
            request.addHeader("x-cos-next-action", "UploadPart");
            invoke(request, voidCosResponseHandler);
        }
    }

    @Override
    public TranslationResponse createTranslationJob(TranslationRequest req) {
        CosHttpRequest<TranslationRequest> request = createRequest(req.getBucketName(), "/jobs", req, HttpMethodName.POST);

        this.setContent(request, CIAuditingXmlFactoryV2.convertToXmlByteArray(req), "application/xml", false);

        return invoke(request, new Unmarshallers.CICommonUnmarshaller<TranslationResponse>(TranslationResponse.class));
    }

    @Override
    public ImageQualityResponse AccessImageQulity(ImageQualityRequest req) {
        CosHttpRequest<ImageQualityRequest> request = createRequest(req.getBucketName(), req.getObjectKey(), req, HttpMethodName.GET);

        addParameterIfNotNull(request, "ci-process", "AssessQuality");

        request.addHeader("Accept", "application/xml");

        return invoke(request, new Unmarshallers.CICommonUnmarshaller<ImageQualityResponse>(ImageQualityResponse.class));
    }

    @Override
    public AIGCMetadataResponse getImageAIGCMetadata(String bucketName, String key) {
        rejectNull(bucketName, "The bucket name parameter must be specified when getting AIGC metadata");
        rejectNull(key, "The key parameter must be specified when getting AIGC metadata");

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        CosHttpRequest<GetObjectRequest> request = createRequest(bucketName, key, getObjectRequest, HttpMethodName.GET);
        request.addParameter("ci-process", "ImageAIGCMetadata");

        return invoke(request, new Unmarshallers.AigcMetadataUnmarshaller());
    }

    @Override
    public AIGCMetadataResponse getMediaAIGCMetadata(String bucketName, String key) {
        rejectNull(bucketName, "The bucket name parameter must be specified when getting AIGC metadata");
        rejectNull(key, "The key parameter must be specified when getting AIGC metadata");

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        CosHttpRequest<GetObjectRequest> request = createRequest(bucketName, key, getObjectRequest, HttpMethodName.GET);
        request.addParameter("ci-process", "MediaAIGCMetadata");

        return invoke(request, new Unmarshallers.AigcMetadataUnmarshaller());
    }
}

