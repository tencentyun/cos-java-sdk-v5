package com.qcloud.cos;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.internal.SkipMd5CheckStrategy;
import com.qcloud.cos.model.*;
import com.qcloud.cos.utils.Base64;
import com.qcloud.cos.utils.Md5Utils;
import java.io.File;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SSECustomerTest extends AbstractCOSClientTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void testSSECustomerUploadDownload() throws Exception{
        clientConfig.setHttpProtocol(HttpProtocol.https);

        String key = "images/tiger.jpg";
        File localFile = buildTestFile(1024);
        String expectedMd5 = Md5Utils.md5Hex(localFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
        String base64EncodedKey = System.getenv("sse_customer_key");
        SSECustomerKey sseCustomerKey = new SSECustomerKey(base64EncodedKey);
        putObjectRequest.setSSECustomerKey(sseCustomerKey);
        cosclient.putObject(putObjectRequest);

        GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest(bucket, key);
        getObjectMetadataRequest.setSSECustomerKey(sseCustomerKey);
        ObjectMetadata objectMetadata = cosclient.getObjectMetadata(getObjectMetadataRequest);

        File downloadFile = new File(localFile.getAbsolutePath() + ".down");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        getObjectRequest.setSSECustomerKey(sseCustomerKey);
        cosclient.getObject(getObjectRequest, downloadFile);
        String resultMd5 = Md5Utils.md5Hex(downloadFile);
        assertEquals(expectedMd5, resultMd5);
        cosclient.deleteObject(bucket, key);
    }

    @Test
    public void testSSE_KMSWithNonexistentKmsKey() throws Exception {
        File tempFile = buildTestFile(100 * 1024 * 1024L);
        String key = "testSSE_KMSWithNonexistentKmsKey";

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, tempFile);
        String kmsKeyId = "kms-test";
        String encryptionContext = Base64.encodeAsString("{\"Ssekmstest\":\"Ssekmstest\"}".getBytes());
        SSECOSKeyManagementParams ssecosKeyManagementParams = new SSECOSKeyManagementParams(kmsKeyId, encryptionContext);
        putObjectRequest.setSSECOSKeyManagementParams(ssecosKeyManagementParams);
        // 服务端加密场景下，返回的etag不再代表文件的md5，所以需要去掉客户端的md5校验
        // 如有需要，可获取crc64，自行校验
        System.setProperty(SkipMd5CheckStrategy.DISABLE_PUT_OBJECT_MD5_VALIDATION_PROPERTY, "true");
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            String crc64 = putObjectResult.getCrc64Ecma();
        } catch (CosServiceException e) {
            if (e.getStatusCode() != 400 && !Objects.equals(e.getMessage(), "The kms key you provided can not be found in kms")) {
                fail(e.getMessage());
            }
        } catch (CosClientException e) {
            e.printStackTrace();
        }finally {
            tempFile.delete();
        }
    }
}

