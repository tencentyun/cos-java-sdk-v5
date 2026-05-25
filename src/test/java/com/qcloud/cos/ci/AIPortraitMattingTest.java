package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.ai.AIPortraitMattingRequest;
import com.qcloud.cos.model.ciModel.ai.AIPortraitMattingResponse;
import com.qcloud.cos.utils.Jackson;
import com.qcloud.cos.utils.UrlEncoderUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AIPortraitMattingTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    /**
     * 使用COS中的图片进行人像抠图
     */
    @Test
    public void testAIPortraitMattingWithCOSObject() throws Exception {
        AIPortraitMattingRequest request = new AIPortraitMattingRequest();
        request.setBucketName("chongqingtest-1251704708");
        request.setObjectKey("SDK/Images/test0.jpeg");
        request.setCenterLayout(1);
        request.setPaddingLayout("20x10");

        AIPortraitMattingResponse response = cosclient.aiPortraitMatting(request);

        // 验证响应
        assert response != null;
        assert response.getImageStream() != null;
        assert response.getMetadata() != null;
        
        // 保存结果到临时文件
        InputStream imageStream = response.getImageStream();
        File tempFile = File.createTempFile("portrait_matting_test_", ".png");
        FileOutputStream fos = new FileOutputStream(tempFile);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = imageStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        imageStream.close();
        
        System.out.println("AI Portrait Matting with COS Object - Result saved to: " + tempFile.getAbsolutePath());
        System.out.println("Response: " + Jackson.toJsonString(response));
        
        // 清理临时文件
        tempFile.deleteOnExit();
    }

    /**
     * 使用外部URL进行人像抠图
     */
    @Test
    public void testAIPortraitMattingWithExternalUrl() throws Exception {
        AIPortraitMattingRequest request = new AIPortraitMattingRequest();
        request.setBucketName("chongqingtest-1251704708");

        // 使用外部图片URL（SDK会自动进行URL编码，不需要手动编码）
        String externalUrl = "https://chongqingtest-1251704708.cos.ap-chongqing.myqcloud.com/SDK/Images/13123213_download_test.png";
        request.setDetectUrl(externalUrl);
        request.setCenterLayout(1);

        AIPortraitMattingResponse response = cosclient.aiPortraitMatting(request);

        // 验证响应
        assert response != null;
        assert response.getImageStream() != null;
        
        // 保存结果到临时文件
        InputStream imageStream = response.getImageStream();
        File tempFile = File.createTempFile("portrait_matting_url_test_", ".png");
        FileOutputStream fos = new FileOutputStream(tempFile);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = imageStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        imageStream.close();
        
        System.out.println("AI Portrait Matting with External URL - Result saved to: " + tempFile.getAbsolutePath());
        System.out.println("Original URL: " + externalUrl);
        System.out.println("Response: " + Jackson.toJsonString(response));
        
        // 清理临时文件
        tempFile.deleteOnExit();
    }

    
}