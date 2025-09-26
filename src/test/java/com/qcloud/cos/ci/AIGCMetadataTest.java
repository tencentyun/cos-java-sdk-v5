package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.job.AIGCMetadataResponse;
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
import org.apache.commons.codec.binary.Base64;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class AIGCMetadataTest  extends AbstractCOSClientCITest {

    private String jobId;
    public static final String TAG = "Transcode";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            AbstractCOSClientCITest.initCosClient();
        }catch (Exception e){

        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void persistenceImagePostWithAigcMetadata() {
        String key = "/SDK/Images/test0.jpeg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucket, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucket);
        rule1.setFileId("/SDK/Images/output/test.jpg");
        String label = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString("1".getBytes(StandardCharsets.UTF_8));
        String contentProducer = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString("content_producer".getBytes(StandardCharsets.UTF_8));
        String produceId = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString("produce_id".getBytes(StandardCharsets.UTF_8));
        String reservedCode1 = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code1".getBytes(StandardCharsets.UTF_8));
        String reservedCode2 = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString("reserved_code2".getBytes(StandardCharsets.UTF_8));
        String propagateId = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString("propagate_id".getBytes(StandardCharsets.UTF_8));
        String contentPropagator = java.util.Base64.getUrlEncoder().withoutPadding().encodeToString("content_propagator".getBytes(StandardCharsets.UTF_8));

        String rule = "imageMogr2/AIGCMetadata/Label/" + label
                + "/ContentProducer/" + contentProducer
                + "/ProduceID/" + produceId
                + "/ReservedCode1/" + reservedCode1
                + "/ReservedCode2/" + reservedCode2
                + "/PropagateID/" + propagateId
                + "/ContentPropagator/" + contentPropagator;
        rule1.setRule(rule);
        ruleList.add(rule1);

        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for(CIObject ciObject:ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
                System.out.println(ciObject.getEtag());
                System.out.println(ciObject.getAigcMetadata());
            }
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCreateMediaJobsWithAigcMetadata()  {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/test.mp4");
        //2.1添加媒体任务操作参数
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
        video.setBufSize("0");
        video.setMaxrate("50000");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");
        // 配置aigc元数据信息
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel("1");
        aigcMetadata.setContentProducer("testProducer");
        aigcMetadata.setProduceId("testProduceId");
        aigcMetadata.setReservedCode1(new String(Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode2(new String(Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setPropagateId("testPropagateId");
        aigcMetadata.setContentPropagator("testPropagator");

        operation.getOutput().setBucket(bucket);
        operation.getOutput().setRegion(region);
        operation.getOutput().setObject("SDK/Media/output/demo1.mp4");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
        System.out.println(response.getJobsDetail().getJobId());
    }

    @Test
    public void getImageAIGCMetadata() {
        String key = "SDK/Images/output/test.jpg";

        AIGCMetadataResponse response = cosclient.getImageAIGCMetadata(bucket, key);

        System.out.println(response);
    }

    @Test
    public void testGetMediaAIGCMetadata() {
        String key = "SDK/Media/output/demo1.mp4";

        AIGCMetadataResponse response = cosclient.getMediaAIGCMetadata(bucket, key);

        System.out.println(response);
    }
}
