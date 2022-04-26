package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.ExtractDigitalWatermark;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 媒体处理 提取数字水印 job接口相关demo 详情见https://cloud.tencent.com/document/product/460/66008
 */
public class ExtractDigitalWatermarkJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务。
     * demo 使用模板创建任务，如需自定义模板请先使用创建模板接口
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demobucket-1234567890");
        request.setTag("ExtractDigitalWatermark");
        request.getInput().setObject("DigitalWatermark.mp4");
        MediaJobOperation operation = request.getOperation();
        ExtractDigitalWatermark extractDigitalWatermark = operation.getExtractDigitalWatermark();
        //Type Version为必传参数
        extractDigitalWatermark.setType("Text");
        extractDigitalWatermark.setVersion("V1");
        request.setQueueId("p9900025e4ec44b5e8225e70a5217****");
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
        request.setBucketName("demobucket-1234567890");
        request.setJobId("j747b787ebbbf11ec86f46b5fdc18f37f");
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
        request.setBucketName("demobucket-1234567890");
        request.setQueueId("p9900025e4ec44b5e8225e70a5217****");
        request.setTag("ExtractDigitalWatermark");
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
        request.setBucketName("demobucket-1234567890");
        request.setJobId("jfb4039b0bb9e11ecbd2081a7c70******");
        //3.调用接口,获取任务响应对象
        Boolean response = client.cancelMediaJob(request);
        System.out.println(response);
    }
}
