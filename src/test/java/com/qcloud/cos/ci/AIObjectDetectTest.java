
package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.ai.CreateAIObjectDetectJobRequest;
import com.qcloud.cos.model.ciModel.ai.CreateAIObjectDetectJobResponse;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AIObjectDetectTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    /**
     * 使用COS中的图片
     */
    @Test
    public void testAIObjectDetectWithCOSObject() {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName(bucket);
        request.setObjectKey("SDK/Images/test0.jpeg");

        CreateAIObjectDetectJobResponse response = cosclient.createAIObjectDetectJob(request);

        // 验证响应
        assert response != null;
        System.out.println("AI Object Detect with COS Object Response: " + Jackson.toJsonString(response));
    }

    /**
     * 使用外部URL
     */
    @Test
    public void testAIObjectDetectWithExternalUrl() {
        CreateAIObjectDetectJobRequest request = new CreateAIObjectDetectJobRequest();
        request.setBucketName(bucket);

        // 使用外部图片URL（SDK会自动进行URL编码，不需要手动编码）
        String externalUrl = "https://chongqingtest-1251704708.cos.ap-chongqing.myqcloud.com/SDK/Images/13123213_download_test.png";
        request.setDetectUrl(externalUrl);

        CreateAIObjectDetectJobResponse response = cosclient.createAIObjectDetectJob(request);

        // 验证响应
        assert response != null;
        System.out.println("AI Object Detect with External URL Response: " + Jackson.toJsonString(response));
    }

}