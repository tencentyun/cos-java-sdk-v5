package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.fail;

public class TimeoutCosClientTest extends AbstractCOSClientTest{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        clientConfig = new ClientConfig(new Region(region));
        clientConfig.setRequestTimeOutEnable(true);
        cosclient = new COSClient(cred, clientConfig);

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
        config.setRequestTimeout(100);
        File tempFile = buildTestFile(100 * 1024 * 1024L);

        try {
            PutObjectRequest request = new PutObjectRequest(bucket, "testRequestTimeoutFile.txt", tempFile);
            cosclient.putObject(request);
        } catch (CosClientException cce) {
            if (!cce.isRequestTimeout()) {
                fail(cce.getMessage());
            }
        } finally {
            config.setRequestTimeout(5 * 60 * 1000);
            tempFile.delete();
        }
    }
}
