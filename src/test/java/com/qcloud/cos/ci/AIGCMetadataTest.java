package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
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
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadata;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadataJobRequest;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadataJobResponse;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.qcloud.cos.utils.Jackson;
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
        video.setBufSize("1100");
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

    /**
     * 测试获取文档AIGC元数据
     * 支持获取 pdf、xmind、md、docx、pptx、xlsx、dotx、potx、xltx 格式的 AIGC 元数据标识信息
     */
    @Test
    public void testGetDocumentAIGCMetadata() {
        String key = "for-test.pdf";  // 支持的文档格式：pdf、xmind、md、docx、pptx、xlsx、dotx、potx、xltx

        try {
            AIGCMetadataResponse response = cosclient.getDocumentAIGCMetadata(bucket, key);
            
            System.out.println("文档AIGC元数据获取成功:");
            System.out.println("完整响应: " + response);
            
            if (response != null && response.getAigc() != null) {
                AigcMetadata aigc = response.getAigc();
                System.out.println("Label: " + aigc.getLabel());
                System.out.println("ContentProducer: " + aigc.getContentProducer());
                System.out.println("ProduceID: " + aigc.getProduceId());
                System.out.println("ReservedCode1: " + aigc.getReservedCode1());
                System.out.println("ContentPropagator: " + aigc.getContentPropagator());
                System.out.println("PropagateID: " + aigc.getPropagateId());
                System.out.println("ReservedCode2: " + aigc.getReservedCode2());
            } else {
                System.out.println("响应为空或AIGC元数据为空");
            }
        } catch (CosServiceException e) {
            System.out.println("服务异常: " + e.getMessage());
            System.out.println("错误码: " + e.getErrorCode());
            System.out.println("状态码: " + e.getStatusCode());
            e.printStackTrace();
        } catch (CosClientException e) {
            System.out.println("客户端异常: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 测试创建文档AIGC元数据处理任务
     */
    @Test
    public void testCreateDocAIGCMetadataJobMinimal() {
        try {
            // 1.创建任务请求对象
            DocAIGCMetadataJobRequest request = new DocAIGCMetadataJobRequest();
            
            // 2.设置基本参数
            request.setBucketName(bucket);
            
            // 设置输入文件
            request.getInput().setObject("SDK/Documents/simple_contract.pdf");
            
            // 设置AIGC元数据 - 仅设置必填的Label
            DocAIGCMetadata aigcMetadata = request.getOperation()
                .getDocAIGCMetadata().getAigcMetadata();
            aigcMetadata.setLabel("1"); // 必填项
            
            // 设置输出参数
            request.getOperation().getOutput().setRegion(region);
            request.getOperation().getOutput().setBucket(bucket);
            request.getOperation().getOutput().setObject("SDK/Documents/output/simple_contract_with_aigc.pdf");
            
            // 3.调用接口
            DocAIGCMetadataJobResponse response = cosclient.createDocAIGCMetadataJob(request);

            System.out.println("任务ID: " + response.getJobsDetail().getJobId());
            System.out.println("任务状态: " + response.getJobsDetail().getState());
            
            // 验证响应
            assert response != null : "响应不应为空";
            assert response.getJobsDetail().getJobId() != null : "任务ID不应为空";
            
        } catch (CosServiceException e) {
            System.err.println("服务异常 - 创建文档AIGC元数据任务失败: " + e.getMessage());
            e.printStackTrace();
        } catch (CosClientException e) {
            System.err.println("客户端异常 - 创建文档AIGC元数据任务失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 测试创建文档AIGC元数据处理任务
     */
    @Test
    public void testCreateDocAIGCMetadataJobWithAllParams() {
        try {
            // 1.创建任务请求对象
            DocAIGCMetadataJobRequest request = new DocAIGCMetadataJobRequest();
            
            // 2.设置基本参数
            request.setBucketName(bucket);
            
            // 设置输入文件
            request.getInput().setObject("SDK/Documents/contract.pdf");
            
            // 设置AIGC元数据 - 包含所有参数
            DocAIGCMetadata aigcMetadata = request.getOperation()
                .getDocAIGCMetadata().getAigcMetadata();
            
            // 必填参数
            aigcMetadata.setLabel("1"); // 生成合成标签要素
            
            // 可选参数
            aigcMetadata.setContentProducer("TestProducer"); // 生成合成服务提供者
            aigcMetadata.setProduceId("${JobId}_${InputName}"); // 内容制作编号，支持通配符
            aigcMetadata.setContentPropagator("TestPropagator"); // 内容传播服务提供者
            aigcMetadata.setPropagateId("${JobId}_propagate"); // 内容传播编号
            
            // Base64编码的预留字段
            String reservedCode1 = java.util.Base64.getUrlEncoder().withoutPadding()
                .encodeToString("security_info_1".getBytes(StandardCharsets.UTF_8));
            aigcMetadata.setReservedCode1(reservedCode1);
            
            String reservedCode2 = java.util.Base64.getUrlEncoder().withoutPadding()
                .encodeToString("security_info_2".getBytes(StandardCharsets.UTF_8));
            aigcMetadata.setReservedCode2(reservedCode2);
            
            // 设置输出参数
            request.getOperation().getOutput().setRegion(region);
            request.getOperation().getOutput().setBucket(bucket);
            request.getOperation().getOutput().setObject("SDK/Documents/output/contract_with_aigc_${JobId}.pdf");
            
            // 3.调用接口
            DocAIGCMetadataJobResponse response = cosclient.createDocAIGCMetadataJob(request);

            System.out.println(Jackson.toJsonString(response));

        } catch (CosServiceException e) {
            System.err.println("服务异常 - 创建文档AIGC元数据任务失败: " + e.getMessage());
            System.err.println("错误码: " + e.getErrorCode());
            System.err.println("状态码: " + e.getStatusCode());
            e.printStackTrace();
        } catch (CosClientException e) {
            System.err.println("客户端异常 - 创建文档AIGC元数据任务失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void getDocumentAIGCMetadata() {
        String bucketName = "chongqingtest-1251704708";
        // 支持的文档格式：pdf、xmind、md、docx、pptx、xlsx、dotx、potx、xltx
        String key = "/SDK/doc/output/abc_0.pdf";

        AIGCMetadataResponse response = cosclient.getDocumentAIGCMetadata(bucketName, key);

        System.out.println(Jackson.toJsonString(response));
    }
}