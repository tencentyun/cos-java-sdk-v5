package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.VocalScore;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;

public class VocalScoreJobDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建音乐评分
     * demo 创建音乐评分任务
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client){
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        request.setTag("VocalScore");
        request.getInput().setObject("1.mp4");
        //2.1添加音乐评分操作参数
        VocalScore vocalScore = request.getOperation().getVocalScore();
        vocalScore.setStandardObject("1.mp4");

        request.setCallBack("https://cloud.tencent.com/xxx");
        request.setCallBackFormat("JSON");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(response.getJobsDetail().getOperation().getTranscode());
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
        request.setBucketName("markjrzhang-1251704708");
        request.setJobId("j2b27107ee3ad11ebbf6d73cb5317****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(response.getJobsDetail().getOperation().getTranscode());
    }

}
