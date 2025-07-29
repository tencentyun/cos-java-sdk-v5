package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import com.qcloud.cos.model.ciModel.job.CallBackKafkaConfig;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaPicProcessTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTransConfigObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;

import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 * 批量任务job接口相关demo
 * API详情见https://cloud.tencent.com/document/product/460/80155
 * 目前仅支持模板和图片处理任务的批量任务
 */
public class BatchJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeMediaJob(client);
    }

    /**
     * createInventoryTriggerJob 接口用于创建批量处理任务。
     * 使用模板创建任务demo
     *
     * @param client
     */
    public static void createInventoryTriggerJob(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setName("demo");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        BatchJobOperation operation = request.getOperation();
        operation.setTag("Transcode");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("out/${InventoryTriggerJobId}.mp4");
        //3.调用接口,获取任务响应对象
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println(response.getJobDetail().getJobId());
    }

    /**
     * 图片处理参数创建批量处理任务demo
     */
    public static void createInventoryTriggerJob2(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setName("demo");
        request.setType("Job");
        request.getInput().setPrefix("mark1/1");
        BatchJobOperation operation = request.getOperation();
        operation.setTag("PicProcess");
        MediaPicProcessTemplateObject picProcess = operation.getJobParam().getPicProcess();
        picProcess.setIsPicInfo("true");
        picProcess.setProcessRule("imageMogr2/thumbnail/!50p");
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("out/${InventoryTriggerJobId}");
//        request.setCallBackFormat("json");
        //3.调用接口,获取任务响应对象
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println(response.getJobDetail().getJobId());
    }

    /**
     * createInventoryTriggerJobSegmentWithAigcMetadata 接口用于创建批量处理任务(转封装+aigc元数据信息)。
     *
     * @param client
     */
    public static void createInventoryTriggerJobSegmentWithAigcMetadata(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setName("demo");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        BatchJobOperation operation = request.getOperation();
        operation.setTag("Segment");
        MediaSegmentObject segment = operation.getJobParam().getSegment();
        segment.setFormat("mp4");
        AigcMetadata aigcMetadata = segment.getAigcMetadata();
        aigcMetadata.setLabel("label");
        aigcMetadata.setContentProducer("testProducer");
        aigcMetadata.setProduceId("testProduceId");
        aigcMetadata.setReservedCode1(new String(Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode2(new String(Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setPropagateId("testPropagateId");
        aigcMetadata.setContentPropagator("testPropagator");;
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("out/${InventoryTriggerJobId}.mp4");
        //3.调用接口,获取任务响应对象
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println(response.getJobDetail().getJobId());
        System.out.println(response.getJobDetail().getOperation().getJobParam().getSegment().getAigcMetadata());
    }

    /**
     * 图片处理参数创建批量处理任务demo（包含配置kafka回调信息）
     */
    public static void createInventoryTriggerJobWithKafkaCallback(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setName("demo");
        request.setType("Job");
        request.getInput().setPrefix("mark1/1");
        BatchJobOperation operation = request.getOperation();
        operation.setTag("PicProcess");
        operation.setCallBackFormat("json");
        operation.setCallBackType("Kafka");
        CallBackKafkaConfig callBackKafkaConfig = operation.getCallBackKafkaConfig();
        callBackKafkaConfig.setRegion("ap-chongqing");
        callBackKafkaConfig.setInstanceId("instance_id");
        callBackKafkaConfig.setTopic("topic");
        MediaPicProcessTemplateObject picProcess = operation.getJobParam().getPicProcess();
        picProcess.setIsPicInfo("true");
        picProcess.setProcessRule("imageMogr2/thumbnail/!50p");
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("out/${InventoryTriggerJobId}");
//        request.setCallBackFormat("json");
        //3.调用接口,获取任务响应对象
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println(response.getJobDetail().getJobId());
    }


    /**
     * describeMediaJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("ba88df4de7b9e11eda55652540038936c");
        //3.调用接口,获取任务响应对象
        BatchJobResponse response = client.describeInventoryTriggerJob(request);
    }

}
