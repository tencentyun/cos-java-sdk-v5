package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSCredentialsProvider;
import com.qcloud.cos.auth.COSStaticCredentialsProvider;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static com.qcloud.cos.internal.SkipMd5CheckStrategy.DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY;
import static org.junit.Assert.fail;

public class TimeoutCosClientTest extends AbstractCOSClientTest{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(appid, secretId, secretKey);
        clientConfig = new ClientConfig(new Region(region));
        clientConfig.setRequestTimeOutEnable(true);
        clientConfig.setIsDistinguishHost(true);
        cosclient = new COSClient(cred, clientConfig);

        cosclient.setCOSCredentials(cred);
        COSCredentialsProvider provider = new COSStaticCredentialsProvider(cred);
        cosclient.setCOSCredentialsProvider(provider);

        deleteBucket();
        createBucket();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void testRequestTimeout() throws Exception {
        ClientConfig config = cosclient.getClientConfig();
        clientConfig.setShortConnection();
        config.setRequestTimeout(50);
        int inputStreamLength = 10 * 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);

        try {
            PutObjectRequest request = new PutObjectRequest(bucket.substring(0, bucket.lastIndexOf("-")), "testRequestTimeoutFile.txt", inputStream, new ObjectMetadata());
            cosclient.putObject(request);
        } catch (CosClientException cce) {
            if (!cce.isRequestTimeout()) {
                fail(cce.getMessage());
            }
        } finally {
            config.setRequestTimeout(5 * 60 * 1000);
            inputStream.close();
        }
    }

    @Test
    public void testPutGetNotTimeout() throws Exception {
        ClientConfig config = cosclient.getClientConfig();
        config.setRequestTimeout(60 * 1000);
        int inputStreamLength = 10 * 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);
        System.setProperty(DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY, "false");
        try {
            PutObjectRequest request = new PutObjectRequest(bucket, "testRequestNotTimeoutFile.txt", inputStream, new ObjectMetadata());
            request.setTrafficLimit(10 * 1024 * 1024);
            cosclient.putObject(request);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, "testRequestNotTimeoutFile.txt");
            COSObject cosObject = cosclient.getObject(getObjectRequest);
        } catch (CosClientException cce) {
            fail(cce.getMessage());
        } finally {
            config.setRequestTimeout(5 * 60 * 1000);
            inputStream.close();
        }
    }
}
