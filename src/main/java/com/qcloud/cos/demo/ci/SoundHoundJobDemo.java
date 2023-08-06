package com.qcloud.cos.demo.ci;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.VocalScore;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.utils.Jackson;

/**
 * 媒体处理 听歌识曲任务接口相关demo 详情见https://cloud.tencent.com/document/product/460/48216
 */
public class SoundHoundJobDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建听歌识曲任务
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws JsonProcessingException {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang2-1251704708");
        request.setTag("SoundHound");
        request.getInput().setObject("1.mp4");
        //2.1添加听歌识曲操作参数
        MediaJobOperation operation = request.getOperation();
        operation.setUserData("demodata");
        operation.setJobLevel("0");

        request.setCallBack("https://cloud.tencent.com/xxx");
        request.setCallBackFormat("JSON");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(response.getJobsDetail().getJobId());
        System.out.println(JSONObject.toJSONString(response));
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
        request.setBucketName("markjrzhang2-1251704708");
        request.setJobId("se98173f6326c11ee88fb8950d73fcaaa");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(response.getJobsDetail().getOperation().getTranscode());
    }

}
