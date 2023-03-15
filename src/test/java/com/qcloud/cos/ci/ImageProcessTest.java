package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Request;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImageProcessTest extends AbstractCOSClientCITest {
    private String queueId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

//    @Test
//    public void testImageAuditing() {
//        // Setup
//        ImageAuditingRequest imageAuditingRequest = new ImageAuditingRequest();
//        imageAuditingRequest.setDetectType("detectType");
//        imageAuditingRequest.setBucketName(bucket);
//        imageAuditingRequest.setObjectKey("destinationKey");
//        imageAuditingRequest.setInterval(0);
//        imageAuditingRequest.setMaxFrames(0);
//        imageAuditingRequest.setBizType("bizType");
//        imageAuditingRequest.setDetectUrl("detectUrl");
//        imageAuditingRequest.setLargeImageDetect("largeImageDetect");
//        imageAuditingRequest.setJobId("jobId");
//        imageAuditingRequest.setDataId("dataId");
//        imageAuditingRequest.setAsync("async");
//        imageAuditingRequest.setCallback("callback");
//
//        ImageAuditingResponse result = cosclient.imageAuditing(imageAuditingRequest);
//    }
//
//    @Test
//    public void testGetImageLabel() {
//        // Setup
//        ImageLabelRequest imageLabelRequest = new ImageLabelRequest();
//        imageLabelRequest.setObjectKey("destinationKey");
//        imageLabelRequest.setBucketName(bucket);
//        imageLabelRequest.setDetectUrl("detectUrl");
//
//
//        // Run the test
//        ImageLabelResponse result = cosclient.getImageLabel(imageLabelRequest);
//
//        // Verify the results
//    }
//
//    @Test
//    public void testGetImageLabelV2() {
//        // Setup
//        ImageLabelV2Request imageLabelV2Request = new ImageLabelV2Request();
//        imageLabelV2Request.setBucketName(bucket);
//        imageLabelV2Request.setObjectKey("destinationKey");
//        imageLabelV2Request.setScenes("scenes");
//
//
//        // Run the test
//        ImageLabelV2Response result = cosclient.getImageLabelV2(imageLabelV2Request);
//
//        // Verify the results
//    }

}
