package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.MediaDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 媒体处理 添加水印 job接口相关demo 详情见https://cloud.tencent.com/document/product/460/66000
 */
public class DigitalWatermarkJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务。
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-1234567890");
        request.setTag("DigitalWatermark");
        request.getInput().setObject("1.mp4");
        MediaJobOperation operation = request.getOperation();
        MediaDigitalWatermark digitalWatermark = operation.getDigitalWatermark();
        //Message Type Version为必传参数
        digitalWatermark.setMessage("demo");
        digitalWatermark.setType("Text");
        digitalWatermark.setVersion("V1");
        operation.getOutput().setBucket("DemoBucket-1234567890");
        operation.getOutput().setRegion("ap-chongqing");
        operation.getOutput().setObject("DigitalWatermark.mp4");
        request.setQueueId("p9900025e4ec44b5e8225e70a521*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response);
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-1234567890");
        request.setJobId("j625e550abb9f11ecae7dcbeb4a2*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.describeMediaJob(request);
        System.out.println(response);
    }

    /**
     * describeMediaJobs 查询任务列表
     *
     * @param client
     */
    public static void describeMediaJobs(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-1234567890");
        request.setQueueId("p9900025e4ec44b5e8225e70a521*****");
        request.setTag("DigitalWatermark");
        //3.调用接口,获取任务响应对象
        MediaListJobResponse response = client.describeMediaJobs(request);
        List<MediaJobObject> jobsDetail = response.getJobsDetailList();
        for (MediaJobObject mediaJobObject : jobsDetail) {
            System.out.println(mediaJobObject);
        }
    }

    /**
     * cancelMediaJob 取消任务
     *
     * @param client
     */
    public static void cancelMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-1234567890");
        request.setJobId("jfb4039b0bb9e11ecbd2081a7c70******");
        //3.调用接口,获取任务响应对象
        Boolean response = client.cancelMediaJob(request);
        System.out.println(response);
    }
}
