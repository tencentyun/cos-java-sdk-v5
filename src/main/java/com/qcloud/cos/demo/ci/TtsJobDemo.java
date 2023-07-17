package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaTtsConfig;
import com.qcloud.cos.model.ciModel.job.TtsTpl;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaTemplateResponse;

import java.io.UnsupportedEncodingException;

/**
 * 媒体处理语音合成job接口相关demo 详情见https://cloud.tencent.com/document/product/460/76914
 */
public class TtsJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务。
     * demo 语音合成任务
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Tts");

        MediaTtsConfig ttsConfig = request.getOperation().getTtsConfig();
        ttsConfig.setInput("床前明月光，疑是地上霜");
        ttsConfig.setInputType("Text");

        //裸参数调用任务时，TtsTpl需传入参数，不可为空
        TtsTpl ttsTpl = request.getOperation().getTtsTpl();
        ttsTpl.setMode("Sync");

        request.getOperation().getOutput().setBucket("demo-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("demo/Tts-demo.mp3");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response);
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("j52d878ba872611edbc36f91076fad8e7");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.describeMediaJob(request);
        System.out.println(response.getJobsDetail().getOperation().getTtsTpl());
    }

    /**
     * CreateMediaTemplate 用于新增模板。
     * demo 语音合成模板
     */
    public static void createMediaTemplate(COSClient client) throws UnsupportedEncodingException {
        //1.创建模板请求对象
        MediaTemplateRequest request = new MediaTemplateRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Tts");
        request.setName("mark-Tts");
        request.setCodec("pcm");
        request.setSpeed("200");
        request.setVoiceType("ruxue");
        request.setMode("Sync");
        MediaTemplateResponse response = client.createMediaTemplate(request);
        System.out.println(response);
    }
}
