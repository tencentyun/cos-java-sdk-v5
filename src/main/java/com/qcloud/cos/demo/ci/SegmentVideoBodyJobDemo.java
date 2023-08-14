package com.qcloud.cos.demo.ci;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.model.ciModel.job.v2.SegmentVideoBody;
import com.qcloud.cos.utils.Jackson;

/**
 * 视频人像分离任务接口相关demo 详情见https://cloud.tencent.com/document/product/460/83973
 * 使用该接口需先开启AI处理功能
 */
public class SegmentVideoBodyJobDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建视频人像分离任务
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws JsonProcessingException {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("SegmentVideoBody");
        request.getInput().setObject("1.mp4");
        //2.1添加听歌识曲操作参数
        MediaJobOperation operation = request.getOperation();
        SegmentVideoBody segmentVideoBody = operation.getSegmentVideoBody();
        segmentVideoBody.setMode("Mask");
        operation.setUserData("demodata");
        operation.setJobLevel("0");

        request.setCallBack("https://cloud.tencent.com/xxx");
        MediaOutputObject output = request.getOperation().getOutput();
        output.setBucket("demo-1234567890");
        output.setRegion("ap-chongqing");
        output.setObject("test/1.mp4");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(response.getJobsDetail().getJobId());
    }

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
        request.setJobId("a07707de43a6f11ee97dc5f2f0afe840e");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(Jackson.toJsonString(response));
    }

}
