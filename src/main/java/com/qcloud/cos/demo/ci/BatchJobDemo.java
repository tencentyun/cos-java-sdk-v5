package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.job.BatchJobListResponse;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import com.qcloud.cos.model.ciModel.job.CallBackKafkaConfig;
import com.qcloud.cos.model.ciModel.job.MediaPicProcessTemplateObject;

import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
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
     * createInventoryTriggerJob 接口用于创建批量处理任务（独立节点类型）。
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
        request.setType("Job");  // 独立节点类型
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
     * createInventoryTriggerJobWorkflow 接口用于创建批量处理任务（工作流类型）。
     * 通过工作流ID触发批量任务demo
     *
     * @param client
     */
    public static void createInventoryTriggerJobWorkflow(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setName("workflow-demo");
        request.setType("Workflow");  // 工作流类型
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        // 设置工作流ID（可以设置多个，用逗号分隔）
        operation.setWorkflowIds("w7476ff3564ee45b7b490d64bccaba6cc");
        
        // 可选：设置时间过滤条件
        operation.getTimeInterval().setStart("2022-02-01T12:00:00+0800");
        operation.getTimeInterval().setEnd("2022-05-01T12:00:00+0800");
        
        //3.调用接口,获取任务响应对象
        BatchJobResponse response = client.createInventoryTriggerJob(request);
    }

    /**
     * createInventoryTriggerJobWorkflowWithManifest 接口用于创建批量处理任务（工作流类型 + COS清单）。
     * 通过工作流ID和COS清单文件触发批量任务demo
     *
     * @param client
     */
    public static void createInventoryTriggerJobWorkflowWithManifest(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setName("workflow-manifest-demo");
        request.setType("Workflow");  // 工作流类型
        
        // 使用COS清单文件指定待处理的对象列表
        request.getInput().setManifest("manifest.json");
        
        BatchJobOperation operation = request.getOperation();
        // 设置工作流ID
        operation.setWorkflowIds("w7476ff3564ee45b7b490d64bccaba6cc");
        
        //3.调用接口,获取任务响应对象
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("工作流任务ID: " + response.getJobDetail().getJobId());
    }

    /**
     * createInventoryTriggerJobWorkflowWithUrlFile 接口用于创建批量处理任务（工作流类型 + URL文件）。
     * 通过工作流ID和URL文件触发批量任务demo
     *
     * @param client
     */
    public static void createInventoryTriggerJobWorkflowWithUrlFile(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setName("workflow-urlfile-demo");
        request.setType("Workflow");  // 工作流类型
        
        // 使用URL文件指定待处理的对象列表
        request.getInput().setUrlFile("url-list.txt");
        
        BatchJobOperation operation = request.getOperation();
        // 设置工作流ID
        operation.setWorkflowIds("w7476ff3564ee45b7b490d64bccaba6cc");
        
        //3.调用接口,获取任务响应对象
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("工作流任务ID: " + response.getJobDetail().getJobId());
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
     * 使用倍速转码队列创建批量处理任务demo
     * 当Tag为Transcode且QueueId为空时，可以设置QueueType为SpeedTranscoding开启倍速转码
     *
     * @param client
     */
    public static void createInventoryTriggerJobWithSpeedTranscoding(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setName("demo-speed-transcoding");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        BatchJobOperation operation = request.getOperation();
        operation.setTag("Transcode");
        // 当Tag为Transcode且QueueId为空时，设置QueueType为SpeedTranscoding表示开启倍速转码
        operation.setQueueType("SpeedTranscoding");
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

    /**
     * describeInventoryTriggerJobs 批量拉取任务列表
     *
     * @param client
     */
    public static void describeInventoryTriggerJobs(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setOrderByTime("Desc");
        request.setSize(10);
        request.setStates("Success");
        //3.调用接口,获取任务响应对象
        BatchJobListResponse response = client.describeInventoryTriggerJobs(request);
    }

    /**
     * cancelInventoryTriggerJob 取消批量任务
     *
     * @param client
     */
    public static void cancelInventoryTriggerJob(COSClient client) {
        //1.创建任务请求对象
        BatchJobRequest request = new BatchJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("ba88df4de7b9e11eda55652540038936c");
        //3.调用接口,获取任务响应对象
        Boolean response = client.cancelInventoryTriggerJob(request);
    }

    /**
     * 创建视频转动图批量任务 (Animation)
     *
     * @param client
     */
    public static void createBatchJobAnimation(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-animation");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("Animation");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/animation/${InventoryTriggerJobId}.gif");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("Animation Job ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建智能封面批量任务 (SmartCover)
     *
     * @param client
     */
    public static void createBatchJobSmartCover(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-smartcover");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("SmartCover");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        // 智能封面可能生成多张图片，使用 ${Number} 占位符
        output.setObject("output/cover/${InventoryTriggerJobId}_${Number}.jpg");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("SmartCover Job ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建视频截图批量任务 (Snapshot)
     * 支持普通截图和雪碧图
     *
     * @param client
     */
    public static void createBatchJobSnapshot(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-snapshot");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("Snapshot");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        // 普通截图输出，多张截图使用 ${Number} 占位符
        output.setObject("output/snapshot/${InventoryTriggerJobId}_${Number}.jpg");
        // 雪碧图输出（可选）
        output.setSpriteObject("output/sprite/${InventoryTriggerJobId}.jpg");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("Snapshot Job ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建人声分离批量任务 (VoiceSeparate)
     * 需要设置 AuObject 输出人声结果
     *
     * @param client
     */
    public static void createBatchJobVoiceSeparate(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-voice-separate");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("VoiceSeparate");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        // 背景音输出
        output.setObject("output/voice/background_${InventoryTriggerJobId}.mp3");
        // 人声输出
        output.setAuObject("output/voice/vocal_${InventoryTriggerJobId}.mp3");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("VoiceSeparate Job ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建语音识别批量任务 (SpeechRecognition)
     *
     * @param client
     */
    public static void createBatchJobSpeechRecognition(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-speech-recognition");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("SpeechRecognition");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/speech/${InventoryTriggerJobId}.txt");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("SpeechRecognition Job ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建视频增强批量任务 (VideoProcess)
     *
     * @param client
     */
    public static void createBatchJobVideoProcess(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-video-process");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("VideoProcess");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/enhanced/${InventoryTriggerJobId}.mp4");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("VideoProcess Job ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建超级分辨率批量任务 (SuperResolution)
     *
     * @param client
     */
    public static void createBatchJobSuperResolution(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-super-resolution");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("SuperResolution");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/super/${InventoryTriggerJobId}.mp4");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("SuperResolution Job ID: " + response.getJobDetail().getJobId());
    }

    // ==================== 高级参数配置示例 ====================

    /**
     * 创建带HTTP回调配置的批量任务
     * 演示 CallBackFormat、CallBackType、CallBack 参数的使用
     *
     * @param client
     */
    public static void createBatchJobWithHttpCallback(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-job-http-callback");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("Transcode");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        // 设置回调参数
        operation.setCallBackFormat("JSON");  // 回调格式：JSON 或 XML，默认 XML
        operation.setCallBackType("Url");     // 回调类型：Url 或 TDMQ，默认 Url
        operation.setCallBack("https://example.com/callback");  // 回调地址
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/callback/${InventoryTriggerJobId}.mp4");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("Job with HTTP Callback ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建带TDMQ回调配置的批量任务
     * 演示 CallBackMqConfig 参数的使用
     *
     * @param client
     */
    public static void createBatchJobWithTDMQCallback(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-job-tdmq-callback");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("Transcode");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        // 设置 TDMQ 回调参数
        operation.setCallBackType("TDMQ");
        operation.getCallBackMqConfig().setMqRegion("sh");      // 消息队列所属园区：sh、bj、gz、cd、hk
        operation.getCallBackMqConfig().setMqMode("Queue");     // 使用模式：Queue（队列服务）或 Topic（主题订阅）
        operation.getCallBackMqConfig().setMqName("test-queue"); // TDMQ 主题名称
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/tdmq/${InventoryTriggerJobId}.mp4");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("Job with TDMQ Callback ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建带时间过滤的批量任务
     * 演示 TimeInterval 参数的使用（独立节点类型）
     *
     * @param client
     */
    public static void createBatchJobWithTimeInterval(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-job-time-filter");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("Transcode");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        // 设置时间过滤条件：只处理指定时间范围内上传的文件
        operation.getTimeInterval().setStart("2024-01-01T00:00:00+0800");
        operation.getTimeInterval().setEnd("2024-12-31T23:59:59+0800");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/time-filter/${InventoryTriggerJobId}.mp4");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("Job with Time Filter ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建使用COS清单文件的批量任务
     * 演示 Input.Manifest 参数的使用
     *
     * @param client
     */
    public static void createBatchJobWithManifest(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-job-manifest");
        request.setType("Job");
        
        // 使用 COS 清单文件指定待处理的对象列表
        // manifest.json 是 COS 清单生成的文件
        request.getInput().setManifest("inventory/manifest.json");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("Transcode");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/manifest/${InventoryTriggerJobId}.mp4");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("Job with Manifest ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建使用URL文件的批量任务
     * 演示 Input.UrlFile 参数的使用
     *
     * @param client
     */
    public static void createBatchJobWithUrlFile(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-job-urlfile");
        request.setType("Job");
        
        // 使用 URL 文件指定待处理的对象列表
        // 文件中每行的 URL 为一个 COS 中对象的访问地址
        request.getInput().setUrlFile("url-list.txt");
        
        BatchJobOperation operation = request.getOperation();
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("Transcode");
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/urlfile/${InventoryTriggerJobId}.mp4");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("Job with UrlFile ID: " + response.getJobDetail().getJobId());
    }

    /**
     * 创建完整参数配置的批量任务
     * 演示所有可选参数的组合使用
     *
     * @param client
     */
    public static void createBatchJobWithFullParameters(COSClient client) {
        BatchJobRequest request = new BatchJobRequest();
        request.setBucketName("demo-1234567890");
        request.setName("batch-job-full-params");
        request.setType("Job");
        request.getInput().setPrefix("media/");
        
        BatchJobOperation operation = request.getOperation();
        
        // 队列配置
        operation.setQueueId("p3b1b5caea2d448d3bcd3f62534d8a2af");
        operation.setTag("Transcode");
        
        // 任务参数
        operation.setUserData("custom-user-data-12345");  // 用户自定义信息
        operation.setJobLevel("2");  // 任务优先级：0、1、2，级别越大优先级越高
        
        // 回调配置
        operation.setCallBackFormat("JSON");
        operation.setCallBackType("Url");
        operation.setCallBack("https://example.com/batch-callback");
        
        // 时间过滤
        operation.getTimeInterval().setStart("2024-01-01T00:00:00+0800");
        operation.getTimeInterval().setEnd("2024-12-31T23:59:59+0800");
        
        // 任务模板
        operation.getJobParam().setTemplateId("t0e2b9f4cd25184c6ab73d0c85a6ee9cb5");
        
        // 输出配置
        MediaOutputObject output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1234567890");
        output.setObject("output/full-params/${InventoryTriggerJobId}.mp4");
        
        BatchJobResponse response = client.createInventoryTriggerJob(request);
        System.out.println("Job with Full Parameters ID: " + response.getJobDetail().getJobId());
        System.out.println("Job Level: " + response.getJobDetail().getOperation().getJobLevel());
        System.out.println("User Data: " + response.getJobDetail().getOperation().getUserData());
    }

}
