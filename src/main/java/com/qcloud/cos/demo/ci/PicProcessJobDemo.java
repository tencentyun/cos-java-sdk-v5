package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;
import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 图片处理异步接口相关demo 详情见https://cloud.tencent.com/document/product/460/77012
 */
public class PicProcessJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describePicProcessQueues(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务。
     * demo 使用模板创建任务，如需自定义模板请先使用创建模板接口
     *
     * @param client
     */
    public static void createPicProcessJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("PicProcess");
        request.getInput().setObject("1.png");
        request.getOperation().getOutput().setBucket("demo-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("2.png");
        request.getOperation().getPicProcess().setProcessRule("imageMogr2/rotate/90");
        request.getOperation().getPicProcess().setIsPicInfo("true");
        request.setQueueId("p86ede0188f844ac99d50f5fa63005237");
        request.setCallBack("https://cloud.tencent.com/xxx");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createPicProcessJob(request);
        System.out.println(response);
    }

    /**
     * describeJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("cabd41ea0355b11ed847a618901112dcf");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.describeMediaJob(request);
        System.out.println(Jackson.toJsonString(response.getJobsDetail().getOperation()));
    }

    /**
     * describeJobs 查询任务列表
     *
     * @param client
     */
    public static void describeJobs(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setQueueId("p9900025e4ec44b5e8225e70a5217****");
        request.setTag("PicProcess");
        //3.调用接口,获取任务响应对象
        MediaListJobResponse response = client.describeMediaJobs(request);
        List<MediaJobObject> jobsDetail = response.getJobsDetailList();
        for (MediaJobObject mediaJobObject : jobsDetail) {
            System.out.println(mediaJobObject.getOperation().getTranscode());
        }
    }

    /**
     * describePicProcessQueues 接口用于搜索队列。
     * @param client
     */
    public static void describePicProcessQueues(COSClient client){
        //1.创建队列请求对象
        MediaQueueRequest request = new MediaQueueRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        //3.调用接口,获取队列响应对象
        MediaListQueueResponse response = client.describePicProcessQueues(request);
        System.out.println(response);
    }
}
