package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.fail;

public class DefaultHttpClientTest extends AbstractCOSClientTest{
    private String appid_ = System.getenv("appid");
    private String secretId_ = System.getenv("secretId");
    private String secretKey_ = System.getenv("secretKey");
    private String region_ = System.getenv("region");
    private String bucket_ = System.getenv("bucket") + (int) (Math.random() * 10000) + "-" + appid_;

    @Test
    public void testInitClientWithProxy() {
        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        ClientConfig clientConfig = new ClientConfig(new Region(region_));
        clientConfig.setHttpProxyIp("127.0.0.1");
        clientConfig.setHttpProxyPort(80);
        COSClient cosClient = new COSClient(cred, clientConfig);
        cosClient.shutdown();
    }

    @Test
    public void testRequestWithCustomerParams() throws Exception{
        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        ClientConfig clientConfig = new ClientConfig(new Region(region_));
        COSClient cosClient = new COSClient(cred, clientConfig);

        Boolean switch_to_stop = true;
        while (switch_to_stop) {
            try {
                cosClient.createBucket(bucket_);
                switch_to_stop = false;
            } catch (CosServiceException cse) {
                if (cse.getStatusCode() == 409) {
                    bucket_ = System.getenv("bucket") + (int) (Math.random() * 10000) + "-" + appid_;
                    continue;
                }
                cse.printStackTrace();
                fail(cse.getErrorMessage());
            }
        }

        int inputStreamLength = 1 * 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket_, "test", inputStream, new ObjectMetadata());
            cosClient.putObject(putObjectRequest);

            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket_, "test");
            getObjectRequest.putCustomQueryParameter("response-content-disposition", "attachment");

            cosClient.getObject(getObjectRequest);
        } finally {
            inputStream.close();
            try {
                cosClient.deleteObject(bucket_, "test");
                cosClient.deleteBucket(bucket_);
            } catch (CosServiceException cse) {
                cse.printStackTrace();
            } finally {
                if (cosClient != null) {
                    cosClient.shutdown();
                }
            }
        }
    }

    @Test
    public void testCustomerHeaders() throws Exception {
        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        ClientConfig clientConfig = new ClientConfig(new Region(region_));
        COSClient cosClient = new COSClient(cred, clientConfig);

        bucket_ = System.getenv("bucket") + (int) (Math.random() * 10000) + "-" + appid_;
        Boolean switch_to_stop = true;
        while (switch_to_stop) {
            try {
                cosClient.createBucket(bucket_);
                switch_to_stop = false;
            } catch (CosServiceException cse) {
                if (cse.getStatusCode() == 409) {
                    bucket_ = System.getenv("bucket") + (int) (Math.random() * 10000) + "-" + appid_;
                    continue;
                }
                cse.printStackTrace();
                fail(cse.getErrorMessage());
            }
        }

        int inputStreamLength = 1 * 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket_, "test", inputStream, new ObjectMetadata());
        putObjectRequest.putCustomRequestHeader(Headers.CONTENT_LENGTH, "1048576");
        putObjectRequest.putCustomRequestHeader("x-cos-acl", "public-read");

        try {
            cosClient.putObject(putObjectRequest);
        } finally {
            inputStream.close();
            try {
                cosClient.deleteObject(bucket_, "test");
                cosClient.deleteBucket(bucket_);
            } catch (CosServiceException cse) {
                cse.printStackTrace();
            } finally {
                if (cosClient != null) {
                    cosClient.shutdown();
                }
            }
        }
    }

}
