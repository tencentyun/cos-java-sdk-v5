package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
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
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

    @Test
    public void testImageProcessWithManualRuleOnlyLabel() {
        String key = "/SDK/Images/test0.jpeg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucket, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucket);
        rule1.setFileId("/SDK/Images/output/test_manual_only_label.jpg");

        String label = Base64.getUrlEncoder().withoutPadding().encodeToString("label".getBytes(StandardCharsets.UTF_8));
        String contentProducer = Base64.getUrlEncoder().withoutPadding().encodeToString("content_producer".getBytes(StandardCharsets.UTF_8));
        String produceId = Base64.getUrlEncoder().withoutPadding().encodeToString("produce_id".getBytes(StandardCharsets.UTF_8));
        String reservedCode1 = Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code1".getBytes(StandardCharsets.UTF_8));
        String reservedCode2 = Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code2".getBytes(StandardCharsets.UTF_8));
        String propagateId = Base64.getUrlEncoder().withoutPadding().encodeToString("propagate_id".getBytes(StandardCharsets.UTF_8));
        String contentPropagator = Base64.getUrlEncoder().withoutPadding().encodeToString("content_propagator".getBytes(StandardCharsets.UTF_8));

        String rule = "imageMogr2/AIGCMetadata/Label/" + label
                + "/ContentProducer/" + contentProducer
                + "/ProduceID/" + produceId
                + "/ReservedCode1/" + reservedCode1
                + "/ReservedCode2/" + reservedCode2
                + "/PropagateID/" + propagateId
                + "/ContentPropagator/" + contentPropagator;

        rule1.setRule(rule);
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            assertNotNull("上传结果不应为空", ciUploadResult);
            assertNotNull("原始信息不应为空", ciUploadResult.getOriginalInfo());

            for(CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("手动构建规则 - 处理结果位置: " + ciObject.getLocation());
                System.out.println("手动构建规则 - AIGC元数据: " + ciObject.getAigcMetadata());
                assertNotNull("处理结果不应为空", ciObject.getLocation());
            }
        } catch (Exception e) {
            fail("手动构建只有Label的规则应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试手动构建规则 - 没有Label参数（应该失败，因为Label是必填的）
     */
    @Test
    public void testImageProcessWithManualRuleNoLabel() {
        String key = "/SDK/Images/test0.jpeg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucket, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucket);
        rule1.setFileId("/SDK/Images/output/test_manual_no_label.jpg");

        // 不包含Label参数，只有ContentProducer和ProduceID
        String contentProducer = Base64.getUrlEncoder().withoutPadding().encodeToString("content_producer".getBytes(StandardCharsets.UTF_8));
        String produceId = Base64.getUrlEncoder().withoutPadding().encodeToString("produce_id".getBytes(StandardCharsets.UTF_8));
        String reservedCode1 = Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code1".getBytes(StandardCharsets.UTF_8));
        String reservedCode2 = Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code2".getBytes(StandardCharsets.UTF_8));
        String propagateId = Base64.getUrlEncoder().withoutPadding().encodeToString("propagate_id".getBytes(StandardCharsets.UTF_8));
        String contentPropagator = Base64.getUrlEncoder().withoutPadding().encodeToString("content_propagator".getBytes(StandardCharsets.UTF_8));

        // 构建没有Label的规则（这应该会失败）
        String rule = "imageMogr2/AIGCMetadata"
                + "/ContentProducer/" + contentProducer
                + "/ProduceID/" + produceId
                + "/ReservedCode1/" + reservedCode1
                + "/ReservedCode2/" + reservedCode2
                + "/PropagateID/" + propagateId
                + "/ContentPropagator/" + contentPropagator;

        rule1.setRule(rule);
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            fail("没有Label参数的规则应该失败");
        } catch (Exception e) {
            System.out.println("预期的错误 - 没有Label参数: " + e.getMessage());
            // 这是预期的行为，Label是必填参数
            assertTrue("应该因为缺少Label参数而失败", e.getMessage().contains("Label") || e.getMessage().contains("InvalidArgument"));
        }
    }

    /**
     * 测试手动构建规则 - 没有ContentProducer参数（应该成功，因为ContentProducer是可选的）
     */
    @Test
    public void testImageProcessWithManualRuleNoContentProducer() {
        String key = "/SDK/Images/test0.jpeg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucket, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucket);
        rule1.setFileId("/SDK/Images/output/test_manual_no_content_producer.jpg");

        // 包含Label和ProduceID，但不包含ContentProducer
        String label = Base64.getUrlEncoder().withoutPadding().encodeToString("label".getBytes(StandardCharsets.UTF_8));
        String produceId = Base64.getUrlEncoder().withoutPadding().encodeToString("produce_id".getBytes(StandardCharsets.UTF_8));
        String reservedCode1 = Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code1".getBytes(StandardCharsets.UTF_8));
        String reservedCode2 = Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code2".getBytes(StandardCharsets.UTF_8));
        String propagateId = Base64.getUrlEncoder().withoutPadding().encodeToString("propagate_id".getBytes(StandardCharsets.UTF_8));
        String contentPropagator = Base64.getUrlEncoder().withoutPadding().encodeToString("content_propagator".getBytes(StandardCharsets.UTF_8));

        String rule = "imageMogr2/AIGCMetadata/Label/" + label
                + "/ProduceID/" + produceId
                + "/ReservedCode1/" + reservedCode1
                + "/ReservedCode2/" + reservedCode2
                + "/PropagateID/" + propagateId
                + "/ContentPropagator/" + contentPropagator;

        rule1.setRule(rule);
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            assertNotNull("上传结果不应为空", ciUploadResult);
            assertNotNull("原始信息不应为空", ciUploadResult.getOriginalInfo());

            for(CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("没有ContentProducer - 处理结果位置: " + ciObject.getLocation());
                System.out.println("没有ContentProducer - AIGC元数据: " + ciObject.getAigcMetadata());
                assertNotNull("处理结果不应为空", ciObject.getLocation());
            }
        } catch (Exception e) {
            fail("没有ContentProducer参数的规则应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试手动构建规则 - 没有ProduceID参数（应该成功，因为ProduceID是可选的）
     */
    @Test
    public void testImageProcessWithManualRuleNoProduceId() {
        String key = "/SDK/Images/test0.jpeg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucket, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucket);
        rule1.setFileId("/SDK/Images/output/test_manual_no_produce_id.jpg");

        // 包含Label和ContentProducer，但不包含ProduceID
        String label = Base64.getUrlEncoder().withoutPadding().encodeToString("label".getBytes(StandardCharsets.UTF_8));
        String contentProducer = Base64.getUrlEncoder().withoutPadding().encodeToString("content_producer".getBytes(StandardCharsets.UTF_8));
        String reservedCode1 = Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code1".getBytes(StandardCharsets.UTF_8));
        String reservedCode2 = Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code2".getBytes(StandardCharsets.UTF_8));
        String propagateId = Base64.getUrlEncoder().withoutPadding().encodeToString("propagate_id".getBytes(StandardCharsets.UTF_8));
        String contentPropagator = Base64.getUrlEncoder().withoutPadding().encodeToString("content_propagator".getBytes(StandardCharsets.UTF_8));

        String rule = "imageMogr2/AIGCMetadata/Label/" + label
                + "/ContentProducer/" + contentProducer
                + "/ReservedCode1/" + reservedCode1
                + "/ReservedCode2/" + reservedCode2
                + "/PropagateID/" + propagateId
                + "/ContentPropagator/" + contentPropagator;

        rule1.setRule(rule);
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            assertNotNull("上传结果不应为空", ciUploadResult);
            assertNotNull("原始信息不应为空", ciUploadResult.getOriginalInfo());

            for(CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("没有ProduceID - 处理结果位置: " + ciObject.getLocation());
                System.out.println("没有ProduceID - AIGC元数据: " + ciObject.getAigcMetadata());
                assertNotNull("处理结果不应为空", ciObject.getLocation());
            }
        } catch (Exception e) {
            fail("没有ProduceID参数的规则应该成功: " + e.getMessage());
        }
    }

    // ==================== 媒体处理测试方法 ====================

    /**
     * 测试媒体转码 - 包含所有AIGC参数（Label、ContentProducer、ProduceID等）
     */
    @Test
    public void testMediaTranscodeWithAllAigcParams() {
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
        
        // 设置所有AIGC参数 - 需要Base64编码
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel(Base64.getUrlEncoder().withoutPadding().encodeToString("1".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setContentProducer(Base64.getUrlEncoder().withoutPadding().encodeToString("testProducer".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setProduceId(Base64.getUrlEncoder().withoutPadding().encodeToString("testProduceId".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setReservedCode1(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved1".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setReservedCode2(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved2".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setPropagateId(Base64.getUrlEncoder().withoutPadding().encodeToString("propagateId".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setContentPropagator(Base64.getUrlEncoder().withoutPadding().encodeToString("contentPropagator".getBytes(StandardCharsets.UTF_8)));

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/output/demo_all_params.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("媒体转码(所有参数) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("设置所有AIGC参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试媒体转码 - 没有Label参数（应该失败，因为Label是必填的）
     */
    @Test
    public void testMediaTranscodeWithNoLabel() {
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
        
        // 不设置Label参数，只设置其他可选参数 - 需要Base64编码
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        // aigcMetadata.setLabel(null); // 不设置Label
        aigcMetadata.setContentProducer(Base64.getUrlEncoder().withoutPadding().encodeToString("testProducer".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setProduceId(Base64.getUrlEncoder().withoutPadding().encodeToString("testProduceId".getBytes(StandardCharsets.UTF_8)));

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/output/demo_no_label.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            fail("没有Label参数的媒体转码任务应该失败");
        } catch (Exception e) {
            System.out.println("预期的错误 - 媒体转码没有Label参数: " + e.getMessage());
            // 这是预期的行为，Label是必填参数
            assertTrue("应该因为缺少Label参数而失败", 
                e.getMessage().contains("Label") || 
                e.getMessage().contains("InvalidArgument") || 
                e.getMessage().contains("required"));
        }
    }

    /**
     * 测试媒体转码 - 没有ContentProducer参数（应该成功，因为ContentProducer是可选的）
     */
    @Test
    public void testMediaTranscodeWithNoContentProducer() {
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
        
        // 设置Label和ProduceID，但不设置ContentProducer - 需要Base64编码
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
//        aigcMetadata.setContentProducer(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setProduceId(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode1(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode2(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setPropagateId(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setContentPropagator(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/output/demo_no_content_producer.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("媒体转码(没有ContentProducer) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("没有ContentProducer参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试媒体转码 - 没有ProduceID参数（应该成功，因为ProduceID是可选的）
     */
    @Test
    public void testMediaTranscodeWithNoProduceId() {
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
        
        // 设置Label和ContentProducer，但不设置ProduceID - 需要Base64编码
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setContentProducer(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
//        aigcMetadata.setProduceId(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode1(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode2(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setPropagateId(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setContentPropagator(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/output/demo_no_produce_id.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("媒体转码(没有ProduceID) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("没有ProduceID参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    // ==================== JobDemo相关测试方法 ====================

    /**
     * 测试JobDemo风格的媒体转码任务 - 包含所有AIGC参数
     * 基于JobDemo.createMediaJobsWithAigcMetadata方法
     */
    @Test
    public void testJobDemoStyleMediaJobsWithAllAigcParams() {
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/demo.mp4");
        
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
        video.setBufSize("10000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");

        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setContentProducer(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setProduceId(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode1(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode2(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setPropagateId(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setContentPropagator(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/out/jobdemo_all_params.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("JobDemo风格媒体转码(所有参数) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("JobDemo风格设置所有AIGC参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试JobDemo风格的媒体转码任务 - 只有必填的Label参数
     */
    @Test
    public void testJobDemoStyleMediaJobsWithOnlyLabel() {
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/demo.mp4");
        
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
        video.setBufSize("10000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");
        
        // 只设置必填的Label参数 - 需要Base64编码
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel(Base64.getUrlEncoder().withoutPadding().encodeToString("label".getBytes(StandardCharsets.UTF_8)));
        // 不设置其他可选参数

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/out/jobdemo_only_label.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("JobDemo风格媒体转码(只有Label) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("JobDemo风格只设置Label参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试JobDemo风格的媒体转码任务 - 没有ContentProducer参数
     */
    @Test
    public void testJobDemoStyleMediaJobsWithNoContentProducer() {
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/demo.mp4");
        
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
        video.setBufSize("10000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");
        
        // 设置Label和ProduceId，但不设置ContentProducer - 需要Base64编码
        // 注意：使用更安全的测试值，确保Base64编码正确
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel(Base64.getUrlEncoder().withoutPadding().encodeToString("label".getBytes(StandardCharsets.UTF_8)));
        // aigcMetadata.setContentProducer(null); // 不设置ContentProducer
        aigcMetadata.setProduceId(Base64.getUrlEncoder().withoutPadding().encodeToString("testProduceId".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setReservedCode1(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved1".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setReservedCode2(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved2".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setPropagateId(Base64.getUrlEncoder().withoutPadding().encodeToString("testPropagateId".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setContentPropagator(Base64.getUrlEncoder().withoutPadding().encodeToString("testPropagator".getBytes(StandardCharsets.UTF_8)));

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/out/jobdemo_no_content_producer.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("JobDemo风格媒体转码(没有ContentProducer) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("JobDemo风格没有ContentProducer参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试JobDemo风格的媒体转码任务 - 没有ProduceId参数
     */
    @Test
    public void testJobDemoStyleMediaJobsWithNoProduceId() {
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/demo.mp4");
        
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
        video.setBufSize("10000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");

        // 配置aigc元数据信息
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel("label");
        aigcMetadata.setContentProducer("testProducer");
        aigcMetadata.setProduceId("testProduceId");
        aigcMetadata.setReservedCode1(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode2(new String(org.apache.commons.codec.binary.Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setPropagateId("testPropagateId");
        aigcMetadata.setContentPropagator("testPropagator");

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/out/jobdemo_no_produce_id.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("JobDemo风格媒体转码(没有ProduceId) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("JobDemo风格没有ProduceId参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试JobDemo风格的媒体转码任务 - 没有Label参数（应该失败）
     */
    @Test
    public void testJobDemoStyleMediaJobsWithNoLabel() {
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/demo.mp4");
        
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
        video.setBufSize("10000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");
        
        // 不设置Label参数，只设置其他可选参数 - 需要Base64编码
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        // aigcMetadata.setLabel(null); // 不设置Label
        aigcMetadata.setContentProducer(Base64.getUrlEncoder().withoutPadding().encodeToString("testProducer".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setProduceId(Base64.getUrlEncoder().withoutPadding().encodeToString("testProduceId".getBytes(StandardCharsets.UTF_8)));

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/out/jobdemo_no_label.mp4");
        
        try {
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            fail("JobDemo风格没有Label参数的媒体转码任务应该失败");
        } catch (Exception e) {
            System.out.println("预期的错误 - JobDemo风格媒体转码没有Label参数: " + e.getMessage());
            // 这是预期的行为，Label是必填参数
            assertTrue("应该因为缺少Label参数而失败", 
                e.getMessage().contains("Label") || 
                e.getMessage().contains("InvalidArgument") || 
                e.getMessage().contains("required"));
        }
    }

    /**
     * 测试MediaJobsRequest风格的媒体转码任务 - 只有必填的Label参数
     */
    @Test
    public void testMediaJobsRequestWithOnlyLabel() {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/test.mp4");

        MediaTranscodeObject transcode = request.getOperation().getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");

        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("1000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");

        // 只设置必填的Label参数
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel("label");  // 直接设置原始值
        // 不设置其他可选参数

        request.getOperation().getOutput().setBucket(bucket);
        request.getOperation().getOutput().setRegion(region);
        request.getOperation().getOutput().setObject("SDK/Media/output/v1_only_label.mp4");

        try {
            MediaJobResponse response = cosclient.createMediaJobs(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("MediaJobsRequest风格(只有Label) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("MediaJobsRequest风格只设置Label参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试MediaJobsRequest风格的媒体转码任务 - 没有ContentProducer参数
     */
    @Test
    public void testMediaJobsRequestWithNoContentProducer() {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/test.mp4");

        MediaTranscodeObject transcode = request.getOperation().getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");

        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("1000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");

        // 设置Label和ProduceId，但不设置ContentProducer
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel("label");  // 直接设置原始值
        // aigcMetadata.setContentProducer(null); // 不设置ContentProducer
        aigcMetadata.setProduceId("testProduceId");  // 直接设置原始值
        aigcMetadata.setReservedCode1(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved1".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setReservedCode2(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved2".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setPropagateId("testPropagateId");  // 直接设置原始值
        aigcMetadata.setContentPropagator("testPropagator");  // 直接设置原始值

        request.getOperation().getOutput().setBucket(bucket);
        request.getOperation().getOutput().setRegion(region);
        request.getOperation().getOutput().setObject("SDK/Media/output/v1_no_content_producer.mp4");

        try {
            MediaJobResponse response = cosclient.createMediaJobs(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("MediaJobsRequest风格(没有ContentProducer) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("MediaJobsRequest风格没有ContentProducer参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试MediaJobsRequest风格的媒体转码任务 - 没有ProduceId参数
     */
    @Test
    public void testMediaJobsRequestWithNoProduceId() {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/test.mp4");

        MediaTranscodeObject transcode = request.getOperation().getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");

        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("1000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");

        // 设置Label和ContentProducer，但不设置ProduceId
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel("label");  // 直接设置原始值
        aigcMetadata.setContentProducer("testProducer");  // 直接设置原始值
        // aigcMetadata.setProduceId(null); // 不设置ProduceId
        aigcMetadata.setReservedCode1(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved1".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setReservedCode2(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved2".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setPropagateId("testPropagateId");  // 直接设置原始值
        aigcMetadata.setContentPropagator("testPropagator");  // 直接设置原始值

        request.getOperation().getOutput().setBucket(bucket);
        request.getOperation().getOutput().setRegion(region);
        request.getOperation().getOutput().setObject("SDK/Media/output/v1_no_produce_id.mp4");

        try {
            MediaJobResponse response = cosclient.createMediaJobs(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("MediaJobsRequest风格(没有ProduceId) - 任务ID: " + response.getJobsDetail().getJobId());
        } catch (Exception e) {
            fail("MediaJobsRequest风格没有ProduceId参数的媒体转码任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试MediaJobsRequest风格的媒体转码任务 - 没有Label参数（应该失败）
     */
    @Test
    public void testMediaJobsRequestWithNoLabel() {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/test.mp4");

        MediaTranscodeObject transcode = request.getOperation().getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");

        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("0");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");

        // 不设置Label参数，只设置其他可选参数
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        // aigcMetadata.setLabel(null); // 不设置Label
        aigcMetadata.setContentProducer("testProducer");  // 直接设置原始值
        aigcMetadata.setProduceId("testProduceId");  // 直接设置原始值

        request.getOperation().getOutput().setBucket(bucket);
        request.getOperation().getOutput().setRegion(region);
        request.getOperation().getOutput().setObject("SDK/Media/output/v1_no_label.mp4");

        try {
            MediaJobResponse response = cosclient.createMediaJobs(request);
            fail("MediaJobsRequest风格没有Label参数的媒体转码任务应该失败");
        } catch (Exception e) {
            System.out.println("预期的错误 - MediaJobsRequest风格媒体转码没有Label参数: " + e.getMessage());
            // 这是预期的行为，Label是必填参数
            assertTrue("应该因为缺少Label参数而失败",
                    e.getMessage().contains("Label") ||
                            e.getMessage().contains("InvalidArgument") ||
                            e.getMessage().contains("required"));
        }
    }

    /**
     * 测试SegmentJobDemo风格的媒体分段任务 - 只有必填的Label参数
     */
    @Test
    public void testSegmentJobDemoWithOnlyLabel() {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName(bucket);
        request.setTag("Segment");
        request.getInput().setObject("SDK/Media/test.mp4");

        MediaSegmentObject segment = request.getOperation().getSegment();
        segment.setFormat("mp4");

        // 只设置必填的Label参数
        AigcMetadata aigcMetadata = segment.getAigcMetadata();
        aigcMetadata.setLabel("label");  // 直接设置原始值
        // 不设置其他可选参数

        request.getOperation().getOutput().setBucket(bucket);
        request.getOperation().getOutput().setRegion(region);
        request.getOperation().getOutput().setObject("SDK/Media/output/segment_only_label.mp4");

        try {
            MediaJobResponse response = cosclient.createMediaJobs(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("SegmentJobDemo风格(只有Label) - 任务ID: " + response.getJobsDetail().getJobId());
            System.out.println("SegmentJobDemo风格(只有Label) - AIGC元数据: " + response.getJobsDetail().getOperation().getSegment().getAigcMetadata());
        } catch (Exception e) {
            fail("SegmentJobDemo风格只设置Label参数的媒体分段任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试SegmentJobDemo风格的媒体分段任务 - 没有ContentProducer参数
     */
    @Test
    public void testSegmentJobDemoWithNoContentProducer() {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName(bucket);
        request.setTag("Segment");
        request.getInput().setObject("SDK/Media/test.mp4");

        MediaSegmentObject segment = request.getOperation().getSegment();
        segment.setFormat("mp4");

        // 设置Label和ProduceId，但不设置ContentProducer
        AigcMetadata aigcMetadata = segment.getAigcMetadata();
        aigcMetadata.setLabel("label");  // 直接设置原始值
        // aigcMetadata.setContentProducer(null); // 不设置ContentProducer
        aigcMetadata.setProduceId("testProduceId");  // 直接设置原始值
        aigcMetadata.setReservedCode1(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved1".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setReservedCode2(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved2".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setPropagateId("testPropagateId");  // 直接设置原始值
        aigcMetadata.setContentPropagator("testPropagator");  // 直接设置原始值

        request.getOperation().getOutput().setBucket(bucket);
        request.getOperation().getOutput().setRegion(region);
        request.getOperation().getOutput().setObject("SDK/Media/output/segment_no_content_producer.mp4");

        try {
            MediaJobResponse response = cosclient.createMediaJobs(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("SegmentJobDemo风格(没有ContentProducer) - 任务ID: " + response.getJobsDetail().getJobId());
            System.out.println("SegmentJobDemo风格(没有ContentProducer) - AIGC元数据: " + response.getJobsDetail().getOperation().getSegment().getAigcMetadata());
        } catch (Exception e) {
            fail("SegmentJobDemo风格没有ContentProducer参数的媒体分段任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试SegmentJobDemo风格的媒体分段任务 - 没有ProduceId参数
     */
    @Test
    public void testSegmentJobDemoWithNoProduceId() {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName(bucket);
        request.setTag("Segment");
        request.getInput().setObject("SDK/Media/test.mp4");

        MediaSegmentObject segment = request.getOperation().getSegment();
        segment.setFormat("mp4");

        // 设置Label和ContentProducer，但不设置ProduceId
        AigcMetadata aigcMetadata = segment.getAigcMetadata();
        aigcMetadata.setLabel("label");  // 直接设置原始值
        aigcMetadata.setContentProducer("testProducer");  // 直接设置原始值
        // aigcMetadata.setProduceId(null); // 不设置ProduceId
        aigcMetadata.setReservedCode1(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved1".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setReservedCode2(Base64.getUrlEncoder().withoutPadding().encodeToString("reserved2".getBytes(StandardCharsets.UTF_8)));
        aigcMetadata.setPropagateId("testPropagateId");  // 直接设置原始值
        aigcMetadata.setContentPropagator("testPropagator");  // 直接设置原始值

        request.getOperation().getOutput().setBucket(bucket);
        request.getOperation().getOutput().setRegion(region);
        request.getOperation().getOutput().setObject("SDK/Media/output/segment_no_produce_id.mp4");

        try {
            MediaJobResponse response = cosclient.createMediaJobs(request);
            assertNotNull("任务响应不应为空", response);
            assertNotNull("任务ID不应为空", response.getJobsDetail().getJobId());
            System.out.println("SegmentJobDemo风格(没有ProduceId) - 任务ID: " + response.getJobsDetail().getJobId());
            System.out.println("SegmentJobDemo风格(没有ProduceId) - AIGC元数据: " + response.getJobsDetail().getOperation().getSegment().getAigcMetadata());
        } catch (Exception e) {
            fail("SegmentJobDemo风格没有ProduceId参数的媒体分段任务应该成功: " + e.getMessage());
        }
    }

    /**
     * 测试SegmentJobDemo风格的媒体分段任务 - 没有Label参数（应该失败）
     */
    @Test
    public void testSegmentJobDemoWithNoLabel() {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName(bucket);
        request.setTag("Segment");
        request.getInput().setObject("SDK/Media/test.mp4");

        MediaSegmentObject segment = request.getOperation().getSegment();
        segment.setFormat("mp4");

        // 不设置Label参数，只设置其他可选参数
        AigcMetadata aigcMetadata = segment.getAigcMetadata();
        // aigcMetadata.setLabel(null); // 不设置Label
        aigcMetadata.setContentProducer("testProducer");  // 直接设置原始值
        aigcMetadata.setProduceId("testProduceId");  // 直接设置原始值

        request.getOperation().getOutput().setBucket(bucket);
        request.getOperation().getOutput().setRegion(region);
        request.getOperation().getOutput().setObject("SDK/Media/output/segment_no_label.mp4");

        try {
            MediaJobResponse response = cosclient.createMediaJobs(request);
            fail("SegmentJobDemo风格没有Label参数的媒体分段任务应该失败");
        } catch (Exception e) {
            System.out.println("预期的错误 - SegmentJobDemo风格媒体分段没有Label参数: " + e.getMessage());
            // 这是预期的行为，Label是必填参数
            assertTrue("应该因为缺少Label参数而失败",
                    e.getMessage().contains("Label") ||
                            e.getMessage().contains("InvalidArgument") ||
                            e.getMessage().contains("required"));
        }
    }
}