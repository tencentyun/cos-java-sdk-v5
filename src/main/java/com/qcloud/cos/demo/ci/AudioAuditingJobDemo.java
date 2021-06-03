package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingResponse;


/**
 * 内容审核 音频审核接口相关demo 详情见https://cloud.tencent.com/document/product/460/53395
 */
public class AudioAuditingJobDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeAudioAuditingJob(client);
    }

    /**
     * createImageAuditingJob 接口用于创建音频审核任务。
     *
     * @param client
     */
    public static void createAudioAuditingJobs(COSClient client) {
        //1.创建任务请求对象
        AudioAuditingRequest request = new AudioAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-123456789");
        request.getInput().setObject("pron.mp3");
        request.getConf().setDetectType("Porn,Terrorism,Politics,Ads");
        request.getConf().setCallback("http://cloud.tencent.com/");
        //3.调用接口,获取任务响应对象
        AudioAuditingResponse response = client.createAudioAuditingJobs(request);
        System.out.println(response);
    }

    /**
     * describeAudioAuditingJob 接口用于创建音频审核任务。
     *
     * @param client
     */
    public static void describeAudioAuditingJob(COSClient client) {
        //1.创建任务请求对象
        AudioAuditingRequest request = new AudioAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-123456789");
        request.setJobId("sacbf7269cbd2e11eba5325254009*****");
        //3.调用接口,获取任务响应对象
        AudioAuditingResponse response = client.describeAudioAuditingJob(request);
        System.out.println(response);
    }
}
