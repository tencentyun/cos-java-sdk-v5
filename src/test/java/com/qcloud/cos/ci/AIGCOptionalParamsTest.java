
package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTransConfigObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.qcloud.cos.utils.AIGCMetadataRuleBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * AIGC可选参数测试类
 * 用于验证ContentProducer和ProduceID参数的可选性，Label参数的必填性
 */
public class AIGCOptionalParamsTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    /**
     * 测试只设置必填的Label参数（ContentProducer和ProduceID为空）
     */
    @Test
    public void testImageProcessWithOnlyLabelRequired() {
        String key = "/SDK/Images/test0.jpeg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucket, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucket);
        rule1.setFileId("/SDK/Images/output/test_only_label.jpg");
        
        // 使用AIGCMetadataRuleBuilder构建规则，只设置必填的Label参数
        String rule = AIGCMetadataRuleBuilder.create("1")
                .build();
        
        rule1.setRule(rule);
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            assertNotNull("上传结果不应为空", ciUploadResult);
            assertNotNull("原始信息不应为空", ciUploadResult.getOriginalInfo());
            
            for(CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("处理结果位置: " + ciObject.getLocation());
                System.out.println("AIGC元数据: " + ciObject.getAigcMetadata());
                assertNotNull("处理结果不应为空", ciObject.getLocation());
            }
        } catch (Exception e) {
            fail("只设置Label参数应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试设置Label和ContentProducer参数（ProduceID为空）
     */
    @Test
    public void testImageProcessWithLabelAndContentProducer() {
        String key = "/SDK/Images/test0.jpeg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucket, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucket);
        rule1.setFileId("/SDK/Images/output/test_label_producer.jpg");
        
        // 使用AIGCMetadataRuleBuilder构建规则，设置Label和ContentProducer
        String rule = AIGCMetadataRuleBuilder.create("1")
                .contentProducer("testProducer")
                .build();
        
        rule1.setRule(rule);
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            assertNotNull("上传结果不应为空", ciUploadResult);
            
            for(CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("处理结果位置: " + ciObject.getLocation());
                System.out.println("AIGC元数据: " + ciObject.getAigcMetadata());
            }
        } catch (Exception e) {
            fail("设置Label和ContentProducer参数应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试设置Label和ProduceID参数（ContentProducer为空）
     */
    @Test
    public void testImageProcessWithLabelAndProduceId() {
        String key = "/SDK/Images/test0.jpeg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucket, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucket);
        rule1.setFileId("/SDK/Images/output/test_label_produceid.jpg");
        
        // 使用AIGCMetadataRuleBuilder构建规则，设置Label和ProduceID
        String rule = AIGCMetadataRuleBuilder.create("1")
                .produceId("testProduceId")
                .build();
        
        rule1.setRule(rule);
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            assertNotNull("上传结果不应为空", ciUploadResult);
            
            for(CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("处理结果位置: " + ciObject.getLocation());
                System.out.println("AIGC元数据: " + ciObject.getAigcMetadata());
            }
        } catch (Exception e) {
            fail("设置Label和ProduceID参数应该成功: " + e.getMessage());
        }
    }


    /**
     * 测试视频转码任务中只设置必填的Label参数
     */
    @Test
    public void testVideoTranscodeWithOnlyLabelRequired() {
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/test.mp4");
        
        MediaJobOperation operation = request.getOperation();
        MediaTranscodeObject transcode = operation.getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");
        
        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("2000");
        video.setMaxrate("50000");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");
        
        // 只设置必填的Label参数，ContentProducer和ProduceID为空
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel("1");
        // 不设置ContentProducer和ProduceID，验证它们是可选的
        // aigcMetadata.setContentProducer(null);
        // aigcMetadata.setProduceId(null);

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/output/demo_only_label.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("只设置Label参数的视频转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试视频转码任务中设置Label和ContentProducer参数
     */
    @Test
    public void testVideoTranscodeWithLabelAndContentProducer() {
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/test.mp4");
        
        MediaJobOperation operation = request.getOperation();
        MediaTranscodeObject transcode = operation.getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");
        
        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("2000"); // 修复BufSize问题
        video.setMaxrate("50000");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");
        
        // 设置Label和ContentProducer，ProduceID为空
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel("1");
        aigcMetadata.setContentProducer("testProducer");
        // 不设置ProduceID，验证它是可选的

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/output/demo_label_producer.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("设置Label和ContentProducer参数的视频转码任务应该成功: " + e.getMessage());
        }
    }

}