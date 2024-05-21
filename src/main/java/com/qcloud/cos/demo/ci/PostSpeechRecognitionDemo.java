package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.PostSpeechRecognitionRequest;
import com.qcloud.cos.model.ciModel.job.PostSpeechRecognitionResponse;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.utils.Jackson;


/**
 * 提交任务 详情见https://cloud.tencent.com/document/product/460/84798
 */
public class PostSpeechRecognitionDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeMediaJob(client);
    }

    /**
     * postSpeechRecognition 提交一个语音识别任务
     * 该接口属于 POST 请求。
     */
//    public static void postSpeechRecognition(COSClient client) {
//        PostSpeechRecognitionRequest request = new PostSpeechRecognitionRequest();
//        request.setBucketName("demo-1234567890");
//        // 设置创建任务的 Tag：SpeechRecognition 必传
//        request.setTag("SpeechRecognition");
//        PostSpeechRecognitionRequest.Input input = request.getInput();
//        input.setObject("1.mp4");
//        // 设置引擎模型类型，分为电话场景和非电话场景 必传
//        PostSpeechRecognitionRequest.Operation operation = request.getOperation();
//        PostSpeechRecognitionRequest.SpeechRecognition speechRecognition = operation.getSpeechRecognition();
//        speechRecognition.setEngineModelType("8k_zh");
//        // 设置存储桶的地域 必传
//        operation.getOutput().setRegion("ap-chongqing");
//        // 设置存储结果的存储桶 必传
//        operation.getOutput().setBucket("demo-1234567890");
//        // 设置结果文件的名称 必传
//        operation.getOutput().setObject("1.mp4");
//        speechRecognition.setChannelNum("1");
//        speechRecognition.setFilterDirty("1");
//        speechRecognition.setFilterModal("1");
//        PostSpeechRecognitionResponse response = client.postSpeechRecognition(request);
//        System.out.println(Jackson.toJsonString(response));
//    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("s2bda8d8a6bf911ee97ab293941e*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
