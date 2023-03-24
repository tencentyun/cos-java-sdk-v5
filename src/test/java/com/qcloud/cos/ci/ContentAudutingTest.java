package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.demo.ci.AuditingResultUtil;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingInfo;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingInputObject;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.BatchImageJobDetail;
import com.qcloud.cos.model.ciModel.auditing.CallbackVersion;
import com.qcloud.cos.model.ciModel.auditing.DescribeImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.DocumentAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.DocumentAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.ReportBadCaseRequest;
import com.qcloud.cos.model.ciModel.auditing.SuggestedLabel;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingResponse;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ContentAudutingTest extends AbstractCOSClientCITest {
    private String jobId;
    private String videoId;
    private String audioId;
    private String textId;
    private String docId;
    private String webId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Before
    public void testImageAuditing() {
        //1.创建任务请求对象
        ImageAuditingRequest request = new ImageAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName(bucket);
        //2.2设置审核类型 不设置值时表示审核所有类型
//        request.setDetectType("porn");
        //2.3设置bucket中的图片位置
        request.setObjectKey("1.png");
        //2.4设置图片压缩参数 0（不压缩），1（压缩）默认为零
        request.setLargeImageDetect("1");
        //2.6 是否设置异步审核 默认为0 同步返回结果  1为异步任务
        request.setAsync("1");
        //3.调用接口,获取任务响应对象
        ImageAuditingResponse response = cosclient.imageAuditing(request);
        jobId = response.getJobId();
        System.out.println(Jackson.toJsonString(response));
        //4调用工具类，获取各审核类型详情集合 (也可自行根据业务解析)
        List<AuditingInfo> imageInfoList = AuditingResultUtil.getImageInfoList(response);
        System.out.println(response);
    }

    @Test
    public void testBatchImageAuditing() {
        //1.创建任务请求对象
        BatchImageAuditingRequest request = new BatchImageAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName(bucket);
        //2.2添加请求内容
        List<BatchImageAuditingInputObject> inputList = request.getInputList();
        BatchImageAuditingInputObject input = new BatchImageAuditingInputObject();
        input.setObject("1.jpg");
        input.setDataId("DataId");
        inputList.add(input);

        input = new BatchImageAuditingInputObject();
        input.setUrl("https://" + bucket + ".cos.ap-chongqing.myqcloud.com/1.png");
        input.setDataId("DataId");

        inputList.add(input);

        //2.3设置审核类型
        request.getConf().setDetectType("all");
        //2.3.1 是否设置异步审核 默认为0 同步返回结果  1为异步任务
//        request.getConf().setAsync("1");
        //3.调用接口,获取任务响应对象
        BatchImageAuditingResponse response = cosclient.batchImageAuditing(request);
        List<BatchImageJobDetail> jobList = response.getJobList();
        for (BatchImageJobDetail batchImageJobDetail : jobList) {
            List<AuditingInfo> imageInfoList = AuditingResultUtil.getBatchImageInfoList(batchImageJobDetail);
            System.out.println(imageInfoList);
        }
    }

    @Test
    public void testDescribeAuditingImageJob() {
        DescribeImageAuditingRequest imageAuditingRequest = new DescribeImageAuditingRequest();
        imageAuditingRequest.setBucketName(bucket);
        imageAuditingRequest.setJobId(jobId);
        ImageAuditingResponse result = cosclient.describeAuditingImageJob(imageAuditingRequest);
    }

    @Before
    public void testCreateVideoAuditingJob() {
        //1.创建任务请求对象
        VideoAuditingRequest request = new VideoAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        //2.1.对象地址或直接传入审核资源url
        request.getInput().setObject("1.mp4");
        request.getConf().setDetectType("all");
        request.getConf().getSnapshot().setCount("10");
        request.getConf().getSnapshot().setMode("Interval");
        request.getConf().getSnapshot().setTimeInterval("10");
        //3.调用接口,获取任务响应对象
        VideoAuditingResponse response = cosclient.createVideoAuditingJob(request);
        videoId = response.getJobsDetail().getJobId();
        System.out.println(response);
    }

    @Test
    public void testDescribeAuditingJob() throws InterruptedException {
        //1.创建任务请求对象
        VideoAuditingRequest request = new VideoAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.setJobId(videoId);
        //3.调用接口,获取任务响应对象
        while (true) {
            //3.调用接口,获取任务响应对象
            VideoAuditingResponse response = cosclient.describeAuditingJob(request);
            String state = response.getJobsDetail().getState();
            if ("Success".equalsIgnoreCase(state) || "Failed".equalsIgnoreCase(state)) {
                System.out.println(response.getRequestId());
                System.out.println(state);
                System.out.println(response.getJobsDetail());
                //4.根据业务逻辑进行处理结果，此处工具类处理操作仅供参考。
                List<AuditingInfo> auditingInfoList = AuditingResultUtil.getAuditingInfoList(response.getJobsDetail());
                System.out.println(auditingInfoList);
                break;
            }
            Thread.sleep(100);
        }
    }

    @Before
    public void testCreateAudioAuditingJobs() {
        //1.创建任务请求对象
        AudioAuditingRequest request = new AudioAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
//        request.getInput().setObject("pron.mp3");
        request.getInput().setObject("pron.mp3");
        request.getInput().setDataId("TestDataId");
        request.getConf().setDetectType("all");
        request.getConf().setCallback("http://cloud.tencent.com/");
        //3.调用接口,获取任务响应对象
        AudioAuditingResponse response = cosclient.createAudioAuditingJobs(request);
        audioId = response.getJobsDetail().getJobId();
    }

    @Test
    public void testDescribeAudioAuditingJob() throws InterruptedException {
        //1.创建任务请求对象
        AudioAuditingRequest request = new AudioAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.setJobId(audioId);
        while (true) {
            //3.调用接口,获取任务响应对象
            AudioAuditingResponse response = cosclient.describeAudioAuditingJob(request);
            String state = response.getJobsDetail().getState();
            if ("Success".equalsIgnoreCase(state) || "Failed".equalsIgnoreCase(state)) {
                System.out.println(response.getRequestId());
                System.out.println(response.getJobsDetail());
                //4.根据业务逻辑进行处理结果，此处工具类处理操作仅供参考。
                List<AuditingInfo> auditingInfoList = AuditingResultUtil.getAuditingInfoList(response.getJobsDetail());
                System.out.println(auditingInfoList);
                break;
            }
            Thread.sleep(100);
        }
    }

    @Before
    public void testCreateAuditingTextJobs() {
        //1.创建任务请求对象
        TextAuditingRequest request = new TextAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        //2.1.1设置对象地址
        //2.1.2或直接设置请求内容,文本内容的Base64编码
        request.getInput().setContent("QUs0NyDogonkvr/lmaggIOa4qeWutuWunSAg5Y+q6KaBOTk4");
        //2.2设置审核类型参数
        request.getConf().setDetectType("all");
        //2.3设置审核模板（可选）
//        request.getConf().setBizType("aa3e9d84a6a079556b0109a935c*****");
        //设置回调信息内容类型 simple精简 Detail详细
        request.getConf().setCallbackVersion(CallbackVersion.Simple);
        //3.调用接口,获取任务响应对象
        TextAuditingResponse response = cosclient.createAuditingTextJobs(request);
        textId = response.getJobsDetail().getJobId();
    }

    @Test
    public void testDescribeAuditingTextJob() {
        //1.创建任务请求对象
        TextAuditingRequest request = new TextAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.setJobId(textId);
        //3.调用接口,获取任务响应对象
        TextAuditingResponse response = cosclient.describeAuditingTextJob(request);
    }

    @Test
    public void testReportBadCase() {
        //1.创建任务请求对象
        ReportBadCaseRequest request = new ReportBadCaseRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.setContentType("1");
        request.setLabel("Porn");
        request.setSuggestedLabel(SuggestedLabel.Normal);
        request.setText("QUs0NyDogonkvr/lmaggIOa4qeWutuWunSAg5Y+q6KaBOTk4");
        request.setJobId(textId);
        //3.调用接口,获取任务响应对象 上报成功则返回
        String requestId = cosclient.reportBadCase(request);
    }

    @Before
    public void testCreateAuditingDocumentJobs() {
        //1.创建任务请求对象
        DocumentAuditingRequest request = new DocumentAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        //2.1.1设置对象地址
//        request.getInput().setObject("1.txt");
        //2.1.2或直接设置请求内容,文本内容的Base64编码
        request.getInput().setUrl("https://" + bucket + ".cos.ap-chongqing.myqcloud.com/test.docx");
        //2.2设置审核类型参数
        request.getConf().setDetectType("all");
        //2.3设置审核模板（可选）
//        request.getConf().setBizType("aa3e9d84a6a079556b0109a935c*****");
        //3.调用接口,获取任务响应对象
        DocumentAuditingResponse response = cosclient.createAuditingDocumentJobs(request);
        docId = response.getJobsDetail().getJobId();
    }

    @Test
    public void testDescribeAuditingDocumentJob() {
        //1.创建任务请求对象
        DocumentAuditingRequest request = new DocumentAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.setJobId(docId);
        //3.调用接口,获取任务响应对象
        DocumentAuditingResponse response = cosclient.describeAuditingDocumentJob(request);
    }

    @Before
    public void createWebpageAuditingJobTest() {
        WebpageAuditingRequest request = new WebpageAuditingRequest();
        request.setBucketName(bucket);
        request.getInput().setUrl("https://console.cloud.tencent.com/");
        request.getConf().setDetectType("all");
        WebpageAuditingResponse response = cosclient.createWebpageAuditingJob(request);
        webId = response.getJobsDetail().getJobId();
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWebpageAuditingJobTest2() {
        WebpageAuditingRequest request = null;
        WebpageAuditingResponse response = cosclient.createWebpageAuditingJob(request);
        webId = response.getJobsDetail().getJobId();
    }

    @Test
    public void describeWebpageAuditingJobTest() {
        //1.创建任务请求对象
        WebpageAuditingRequest request = new WebpageAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.setJobId(webId);
        WebpageAuditingResponse webpageAuditingResponse = cosclient.describeWebpageAuditingJob(request);
    }


}
