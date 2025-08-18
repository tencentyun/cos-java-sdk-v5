package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ciModel.job.ImageQualityRequest;
import com.qcloud.cos.model.ciModel.job.ImageQualityResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImageQualityTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(AbstractCOSClientCITest.region);
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    // 测试正常图片质量评估
    @Test
    public void testAssessImageQuality() {
        ImageQualityRequest request = new ImageQualityRequest();
        request.setBucketName(bucket);
        request.setObjectKey("demo.jpeg");

        ImageQualityResponse response = cosclient.AccessImageQulity(request);

        assertNotNull(response);
        System.out.println(response);
    }

    // 测试空参数
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyParameters() {
        try {
            ImageQualityRequest request = new ImageQualityRequest();
            ImageQualityResponse response = cosclient.AccessImageQulity(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 测试无效图片路径
    @Test(expected = CosServiceException.class)
    public void testInvalidImagePath() {
        try {
            ImageQualityRequest request = new ImageQualityRequest();
            request.setBucketName(bucket);
            request.setObjectKey("non-existent-image.jpg");

            ImageQualityResponse response = cosclient.AccessImageQulity(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}