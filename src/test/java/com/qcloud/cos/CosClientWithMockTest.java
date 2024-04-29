package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.endpoint.UserSpecifiedEndpointBuilder;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.DefaultCosHttpClient;
import com.qcloud.cos.internal.Constants;
import com.qcloud.cos.internal.DeleteObjectsResponse;
import com.qcloud.cos.internal.XmlResponsesSaxParser;
import com.qcloud.cos.model.*;
import com.qcloud.cos.model.bucketcertificate.BucketGetDomainCertificate;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import com.qcloud.cos.model.ciModel.image.AutoTranslationBlockRequest;
import com.qcloud.cos.model.ciModel.image.AutoTranslationBlockResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListRequest;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.ImageAuditingImpl;
import com.qcloud.cos.transfer.MultipleImageAuditingImpl;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.utils.BinaryUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.qcloud.cos.internal.SkipMd5CheckStrategy.DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest({COSClient.class, ClientConfig.class, BinaryUtils.class, XmlResponsesSaxParser.CopyObjectResultHandler.class})
@PowerMockIgnore({"javax.net.ssl.*"})
public class CosClientWithMockTest {
    private String appid_ = System.getenv("appid");
    private String secretId_ = System.getenv("secretId");
    private String secretKey_ = System.getenv("secretKey");
    private String region_ = System.getenv("region");
    private String bucket_ = System.getenv("bucket") + (int) (Math.random() * 1000) + "-" + appid_;

    @Test
    public void testDelObjsWithError() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucket_);
        // 设置要删除的key列表, 最多一次删除1000个
        ArrayList<DeleteObjectsRequest.KeyVersion> keyList = new ArrayList<>();
        // 传入要删除的文件名
        keyList.add(new DeleteObjectsRequest.KeyVersion("test1"));
        keyList.add(new DeleteObjectsRequest.KeyVersion("test2"));
        deleteObjectsRequest.setKeys(keyList);

        CosHttpRequest<DeleteObjectsRequest> request = new CosHttpRequest<DeleteObjectsRequest>(deleteObjectsRequest);

        DeleteObjectsResponse response = new DeleteObjectsResponse();

        List<MultiObjectDeleteException.DeleteError> errorList = new ArrayList<>();
        MultiObjectDeleteException.DeleteError error = new MultiObjectDeleteException.DeleteError();
        error.setKey("test1");
        errorList.add(error);

        response.setErrors(errorList);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(response);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient client = new COSClient(cred, clientConfig);

        try {
            client.deleteObjects(deleteObjectsRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        PowerMockito.mockStatic(BinaryUtils.class);
        PowerMockito.when(BinaryUtils.toBase64(any())).thenAnswer((m)->{
            CosClientException cce = new CosClientException("Couldn't compute md5 sum");
            throw cce;
        });

        try {
            client.deleteObjects(deleteObjectsRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testUploadError() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setETag("b6d81b360a5672d80c27430f39153e2c");
        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(metadata);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient client = new COSClient(cred, clientConfig);

        int inputStreamLength = 1024 * 1024;
        byte[] data = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);
        String key = "testMD5CheckException";
        ObjectMetadata objectMetadata = new ObjectMetadata();

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket_, key, inputStream, objectMetadata);

        try {
            client.putObject(putObjectRequest);
        } catch (CosClientException cce) {
            if (!(cce.getMessage().startsWith("Unable to verify integrity of data upload"))) {
                fail(cce.getMessage());
            }
        } finally {
            inputStream.close();
            client.shutdown();
        }
    }

    @Test
    public void testPutWithDecoderException() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setETag("b6d81b360a5672d80c27430f39153e2c");
        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(metadata);

        PowerMockito.mockStatic(BinaryUtils.class);
        PowerMockito.when(BinaryUtils.fromHex(anyString())).thenAnswer((m)->{
            DecoderException de = new DecoderException();
            throw de;
        });

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient client = new COSClient(cred, clientConfig);

        byte[] data = new byte[1024 * 1024];
        InputStream inputStream2 = new ByteArrayInputStream(data);
        String key = "testDecoderException";
        ObjectMetadata objectMetadata = new ObjectMetadata();

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket_, key, inputStream2, objectMetadata);

        try {
            client.putObject(putObjectRequest);
        } catch (CosClientException cce) {
            if (!(cce.getMessage().startsWith("Unable to verify integrity of data upload"))) {
                fail(cce.getMessage());
            }
        } finally {
            client.shutdown();
        }
    }

    @Test
    public void testGetWithError() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        COSObject cosObject = new COSObject();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setETag("b6d81b360a5672d80c27430f39153e2c");
        cosObject.setObjectMetadata(metadata);

        int inputStreamLength = 1024 * 1024;
        byte[] data = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);
        COSObjectInputStream cosObjectInputStream = new COSObjectInputStream(inputStream, new HttpGet());
        cosObject.setObjectContent(cosObjectInputStream);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(cosObject);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        PowerMockito.mockStatic(MessageDigest.class);
        PowerMockito.when(MessageDigest.getInstance(anyString())).thenAnswer((m)->{
            NoSuchAlgorithmException nae = new NoSuchAlgorithmException();
            throw nae;
        });

        System.setProperty(DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY, "false");

        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket_, "testGetError");
        cosClient.getObject(getObjectRequest);

        PowerMockito.mockStatic(BinaryUtils.class);
        PowerMockito.when(BinaryUtils.fromHex(anyString())).thenAnswer((m)->{
            DecoderException de = new DecoderException();
            throw de;
        });

        try {
            getObjectRequest = new GetObjectRequest(bucket_, "testGetError");
            cosClient.getObject(getObjectRequest);
        } finally {
            inputStream.close();
            cosClient.shutdown();
        }
    }

    @Test
    public void testUploadPartError() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setETag("b6d81b360a5672d80c27430f39153e2c");
        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(metadata);

        byte data[] = new byte[1024 * 1024];
        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucketName(bucket_);
        uploadPartRequest.setKey("test");
        uploadPartRequest.setUploadId("00000000000");
        // 设置分块的数据来源输入流
        uploadPartRequest.setInputStream(new ByteArrayInputStream(data));
        // 设置分块的长度
        uploadPartRequest.setPartSize(data.length); // 设置数据长度
        uploadPartRequest.setPartNumber(1);     // 假设要上传的part编号是10

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            cosClient.uploadPart(uploadPartRequest);
        } catch (CosClientException cce) {
            if (!cce.getMessage().startsWith("Unable to verify integrity of data upload")) {
                fail(cce.getMessage());
            }
        } finally {
            cosClient.shutdown();
        }
    }

    @Test
    public void testCopyError() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        XmlResponsesSaxParser.CopyObjectResultHandler copyObjectResultHandler = PowerMockito.mock(XmlResponsesSaxParser.CopyObjectResultHandler.class);
        PowerMockito.when(copyObjectResultHandler.getErrorCode()).thenReturn("Unknown");
        PowerMockito.when(copyObjectResultHandler.getErrorMessage()).thenReturn("unknown error");
        PowerMockito.when(copyObjectResultHandler.getRequestId()).thenReturn("NjQXXXXXXXXXXXXXXXXXX");
        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(copyObjectResultHandler);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        CopyObjectRequest request = new CopyObjectRequest(bucket_, "testSrc", bucket_, "testDst");
        request.setSourceVersionId("1111111");
        request.setCannedAccessControlList(CannedAccessControlList.PublicRead);

        CopyObjectRequest request_with_params = new CopyObjectRequest(bucket_, "testSrc", bucket_, "testDst");
        AccessControlList acl = new AccessControlList();
        request_with_params.setAccessControlList(acl);
        request_with_params.setRedirectLocation("test_redirectLocation");
        request_with_params.setMetadataDirective("metadata_directive");

        CopyPartRequest copyPartRequest = new CopyPartRequest();
        // 要拷贝的源文件所在的region
        UserSpecifiedEndpointBuilder endpointBuilder = new UserSpecifiedEndpointBuilder("testApiEndpoint", "getServiceApiEndpoint");
        copyPartRequest.setSourceEndpointBuilder(endpointBuilder);
        copyPartRequest.setSourceAppid(appid_);
        copyPartRequest.setSourceVersionId("111111");
        // 要拷贝的源文件的bucket名称
        copyPartRequest.setSourceBucketName(bucket_);
        // 要拷贝的源文件的路径
        copyPartRequest.setSourceKey("aaa/ccc.txt");
        // 指定要拷贝的源文件的数据范围(类似content-range)
        copyPartRequest.setFirstByte(0L);
        copyPartRequest.setLastByte(1048575L);
        // 目的bucket名称
        copyPartRequest.setDestinationBucketName(bucket_);
        // 目的路径名称
        copyPartRequest.setDestinationKey("testdst");
        copyPartRequest.setPartNumber(1);
        // uploadId
        copyPartRequest.setUploadId("0000000000000000");

        try {
            CopyObjectResult result = cosClient.copyObject(request);
        } catch (CosServiceException cse) {
            assertEquals("Unknown", cse.getErrorCode());
        }

        try {
            cosClient.copyPart(copyPartRequest);
        } catch (CosServiceException cse) {
            assertEquals("Unknown", cse.getErrorCode());
        }

        try {
            cosClient.copyObject(request_with_params);
        } catch (CosServiceException cse) {
            assertEquals("Unknown", cse.getErrorCode());
        }

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenAnswer((m)->{
            CosServiceException cse = new CosServiceException("test error");
            cse.setStatusCode(Constants.FAILED_PRECONDITION_STATUS_CODE);
            throw cse;
        });

        request = new CopyObjectRequest(bucket_, "testSrc", bucket_, "testDst");

        CopyObjectResult result = cosClient.copyObject(request);
        assertNull(result);
        CopyPartResult copyPartResult = cosClient.copyPart(copyPartRequest);
        assertNull(copyPartResult);
        cosClient.shutdown();
    }

    @Test
    public void testCopyErrorNot412() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenAnswer((m)->{
            CosServiceException cse = new CosServiceException("test error");
            cse.setStatusCode(Constants.BUCKET_ACCESS_FORBIDDEN_STATUS_CODE);
            throw cse;
        });

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        CopyObjectRequest request = new CopyObjectRequest(bucket_, "testSrc", bucket_, "testDst");
        try {
            CopyObjectResult result = cosClient.copyObject(request);
        } catch (CosServiceException cse) {
            assertEquals(Constants.BUCKET_ACCESS_FORBIDDEN_STATUS_CODE, cse.getStatusCode());
        }

        CopyPartRequest copyPartRequest = new CopyPartRequest();
        // 要拷贝的源文件所在的region
        UserSpecifiedEndpointBuilder endpointBuilder = new UserSpecifiedEndpointBuilder("testApiEndpoint", "getServiceApiEndpoint");
        copyPartRequest.setSourceEndpointBuilder(endpointBuilder);
        copyPartRequest.setSourceVersionId("111111");
        // 要拷贝的源文件的bucket名称
        copyPartRequest.setSourceBucketName(bucket_);
        // 要拷贝的源文件的路径
        copyPartRequest.setSourceKey("aaa/ccc.txt");
        // 指定要拷贝的源文件的数据范围(类似content-range)
        copyPartRequest.setFirstByte(0L);
        copyPartRequest.setLastByte(1048575L);
        // 目的bucket名称
        copyPartRequest.setDestinationBucketName(bucket_);
        // 目的路径名称
        copyPartRequest.setDestinationKey("testdst");
        copyPartRequest.setPartNumber(1);
        // uploadId
        copyPartRequest.setUploadId("0000000000000000");

        try {
            cosClient.copyPart(copyPartRequest);
        } catch (CosServiceException cse) {
            assertEquals(Constants.BUCKET_ACCESS_FORBIDDEN_STATUS_CODE, cse.getStatusCode());
        } finally {
            cosClient.shutdown();
        }
    }

    @Test
    public void testListVersions() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        VersionListing listing = new VersionListing();
        listing.setBucketName(bucket_);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(listing);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        VersionListing result = cosClient.listVersions(bucket_, "test_prefix", "test_key_marker", "test_version_marker", "/", 1000);
        assertEquals(bucket_, result.getBucketName());
        cosClient.shutdown();
    }

    @Test
    public void testGetBucketInformationWith404() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenAnswer((m)->{
            CosServiceException cse = new CosServiceException("Not found");
            cse.setStatusCode(404);
            throw cse;
        });

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        BucketGetDomainCertificate domainCertificate = cosClient.getBucketDomainCertificate(bucket_, "userDefDomain");
        assertEquals(null, domainCertificate);

        BucketRefererConfiguration configuration = cosClient.getBucketRefererConfiguration(bucket_);
        assertEquals(null, configuration);

        BucketTaggingConfiguration bucketTaggingConfiguration = cosClient.getBucketTaggingConfiguration(bucket_);
        assertEquals(null, bucketTaggingConfiguration);
        cosClient.shutdown();
    }

    @Test
    public void testGetBucketInformationWithCSE() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenAnswer((m)->{
            CosServiceException cse = new CosServiceException("Access deny");
            cse.setStatusCode(403);
            throw cse;
        });

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            BucketGetDomainCertificate domainCertificate = cosClient.getBucketDomainCertificate(bucket_, "userDefDomain");
        } catch (CosServiceException cse) {
            assertEquals(403, cse.getStatusCode());
        }

        try {
            BucketRefererConfiguration configuration = cosClient.getBucketRefererConfiguration(bucket_);
        } catch (CosServiceException cse) {
            assertEquals(403, cse.getStatusCode());
        }

        try {
            BucketTaggingConfiguration configuration = cosClient.getBucketTaggingConfiguration(bucket_);
        } catch (CosServiceException cse) {
            assertEquals(403, cse.getStatusCode());
        } finally {
            cosClient.shutdown();
        }
    }

    @Test
    public void testDeleteWorkFlow() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(true);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket_);
        request.setWorkflowId("aaaa");
        Boolean response = cosClient.deleteWorkflow(request);
        assertEquals(true, response);
    }

    @Test
    public void testListObjectDecompressionJobs() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        ListJobsResult listJobsResult = new ListJobsResult();
        listJobsResult.setNextToken("test_next_token");
        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(listJobsResult);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            ListJobsResult result =
                    cosClient.listObjectDecompressionJobs(bucket_, "Success", "asc", "1000", "test_next_token");
            System.out.println(result);
        } finally {
            cosClient.shutdown();
        }
    }

    @Test
    public void testPostObjectDecompression() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        DecompressionResult decompressionResult = new DecompressionResult();
        decompressionResult.setJobId("00000");
        decompressionResult.setMsg("test PostObjectDecompression");

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(decompressionResult);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);
        try {
            DecompressionRequest decompressionRequest =
                    new DecompressionRequest()
                            .setObjectKey("a.tar.gz")
                            .setSourceBucketName(bucket_)
                            .setTargetBucketName(bucket_)
                            .setPrefixReplaced(true)
                            .setResourcesPrefix("")
                            .setTargetKeyPrefix("test_out/");
            DecompressionResult result = cosClient.postObjectDecompression(decompressionRequest);
            System.out.println(result);
        } finally {
            cosClient.shutdown();
        }
    }

    @Test
    public void testGetObjectDecompressionStatus() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        DecompressionResult decompressionResult = new DecompressionResult();
        decompressionResult.setJobId("00000");
        decompressionResult.setMsg("test PostObjectDecompression");

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(decompressionResult);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            DecompressionResult result = cosClient.getObjectDecompressionStatus(bucket_, "test_obj", "00000");
            System.out.println(result);
        } finally {
            cosClient.shutdown();
        }
    }

    @Test
    public void testGetObjectDecompressionStatus_String() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

//        DecompressionResult decompressionResult = new DecompressionResult();
//        decompressionResult.setJobId("00000");
//        decompressionResult.setMsg("test PostObjectDecompression");
        String status_str = "test_status";

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(status_str);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        try {
            String result = cosClient.getObjectDecompressionStatus(bucket_, "test_obj");
            System.out.println(result);
        } finally {
            cosClient.shutdown();
        }
    }

    @Test
    public void testDescribeMediaJobs() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        MediaListJobResponse response = new MediaListJobResponse();
        List<MediaJobObject> jobsDetailList = new LinkedList<>();
        MediaJobObject mediaJobObject = new MediaJobObject();
        jobsDetailList.add(mediaJobObject);
        response.setJobsDetailList(jobsDetailList);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(response);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket_);
        request.setQueueId("p9900025e4ec44b5e8225e70a5217****");
        request.setTag("Transcode");
        //3.调用接口,获取任务响应对象
        MediaListJobResponse mediaListJobResponse = cosClient.describeMediaJobs(request);
        List<MediaJobObject> jobsDetail = mediaListJobResponse.getJobsDetailList();
        assertEquals(0, jobsDetail.size());
        cosClient.shutdown();
    }

    @Test
    public void testAutoTranslationBlock() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        AutoTranslationBlockResponse response = new AutoTranslationBlockResponse();
        response.setTranslationResult("test_AutoTranslationBlockResponse");

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(response);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        //1.创建二维码生成请求对象
        AutoTranslationBlockRequest request = new AutoTranslationBlockRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket_);
        //2.1 待翻译的文本 必填
        request.setInputText("数据万象");
        //2.2 输入语言 必填
        request.setSourceLang("zh");
        //2.3 输出语言 必填
        request.setTargetLang("en");
        AutoTranslationBlockResponse autoTranslationBlock = cosClient.autoTranslationBlock(request);
        String translationResult = autoTranslationBlock.getTranslationResult();
        System.out.println(translationResult);
        cosClient.shutdown();
    }

    @Test
    public void testImageAuditing() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        ImageAuditingResponse response = new ImageAuditingResponse();
        response.setJobId("id00000");

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenReturn(response);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        List<ImageAuditingRequest> requestList = new ArrayList<>();
        ImageAuditingRequest request = new ImageAuditingRequest();
        request.setBucketName(bucket_);
        request.setObjectKey("1.png");
        requestList.add(request);

        request = new ImageAuditingRequest();
        request.setBucketName(bucket_);
        request.setObjectKey("1.jpg");
        requestList.add(request);

        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        MultipleImageAuditingImpl multipleImageAuditing = transferManager.batchPostImageAuditing(requestList);
        multipleImageAuditing.waitForCompletion();
        List<ImageAuditingImpl> imageAuditingList = multipleImageAuditing.getImageAuditingList();
        for (ImageAuditingImpl imageAuditing : imageAuditingList) {
            System.out.println(imageAuditing.getState());
            System.out.println(imageAuditing.getResponse());
        }
        transferManager.shutdownNow();
        cosClient.shutdown();
    }

    @Test
    public void testSetBucketACL() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenAnswer((m)->{
            return null;
        });

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        String ownerUin = System.getenv("owner_uin");
        String ownerId = String.format("qcs::cam::uin/%s:uin/%s", ownerUin, ownerUin);
        AccessControlList acl = new AccessControlList();
        Owner owner = new Owner();
        owner.setId(ownerId);
        acl.setOwner(owner);

        String granteeUin = "qcs::cam::uin/734505014:uin/734505014";
        UinGrantee uinGrantee = new UinGrantee(granteeUin);
        uinGrantee.setIdentifier(granteeUin);
        acl.grantPermission(uinGrantee, Permission.FullControl);
        cosClient.setBucketAcl(bucket_, acl);
        cosClient.setObjectAcl(bucket_, "testSetObjACL", acl);

        cosClient.shutdown();
    }

    @Test
    public void testGetBucketACL() throws Exception {
        COSSigner signer = PowerMockito.mock(COSSigner.class);
        PowerMockito.whenNew(COSSigner.class).withNoArguments().thenReturn(signer);
        PowerMockito.when(signer, "sign", any(), any(), any()).thenAnswer((m)->{return null;});
        ClientConfig clientConfig = new ClientConfig(new Region(region_));

        DefaultCosHttpClient cosHttpClient = PowerMockito.mock(DefaultCosHttpClient.class);
        PowerMockito.whenNew(DefaultCosHttpClient.class).withArguments(clientConfig).thenReturn(cosHttpClient);

        PowerMockito.when(cosHttpClient,"exeute", any(), any()).thenAnswer((m)->{
            AccessControlList acl = new AccessControlList();
            Owner owner = new Owner();
            String ownerId = "qcs::cam::uin/100000000001:uin/100000000001";
            owner.setId(ownerId);
            acl.setOwner(owner);
            String granteeUin = "qcs::cam::uin/100000000002:uin/100000000002";
            UinGrantee uinGrantee = new UinGrantee(granteeUin);
            acl.grantPermission(uinGrantee, Permission.FullControl);
            return acl;
        });

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        COSClient cosClient = new COSClient(cred, clientConfig);

        AccessControlList acl = cosClient.getBucketAcl(bucket_);
        List<Grant> grants = acl.getGrantsAsList();
        assertEquals(1L, grants.size());

        acl = cosClient.getObjectAcl(bucket_, "testGetObjACL");
        grants = acl.getGrantsAsList();
        assertEquals(1L, grants.size());

        cosClient.shutdown();
    }
}
