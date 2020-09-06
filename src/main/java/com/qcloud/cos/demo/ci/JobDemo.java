package com.qcloud.cos.demo.ci;

import com.qcloud.cos.CIClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class JobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "secretId";
        String secretKey = "secretKey";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, CI 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 https), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("chongqing");
        CIClientConfig clientConfig = new CIClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient client = new COSClient(cred, clientConfig);

        // 4 调用要使用的方法。
        describeMediaJobs(client);
//        cancelMediaJob(client);
    }

    /**
     * createMediaJobs 接口用于创建任务。
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        MediaJobObject request = new MediaJobObject();
        request.setBucketName("markjrzhang-1251704708");
        request.setTag("Snapshot");
        request.getInput().setObject("1.mp4");

        request.getOperation().setTemplateId("t07740e32081b44ad7a0aea03adcffd54a");
        request.getOperation().getOutput().setBucket("markjrzhang-1251704708");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("test${Number}-trans.mkv");
        request.setQueueId("p9900025e4ec44b5e8225e70a52170834");
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response);
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     * @param client
     */
    public static void describeMediaJob(COSClient client)  {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setJobId("jae776cb4ec3011eab2cdd3817d4d5e64");
        MediaJobResponse response = client.describeMediaJob(request);
        System.out.println(response);
    }

    /**
     * describeMediaJobs 查询任务列表
     * @param client
     */
    public static void describeMediaJobs(COSClient client)  {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setQueueId("p9900025e4ec44b5e8225e70a52170834");
        request.setTag("Transcode");
        MediaListJobResponse response = client.describeMediaJobs(request);
        List<MediaJobObject> jobsDetail = response.getJobsDetail();
        for (MediaJobObject mediaJobObject : jobsDetail) {
            System.out.println(mediaJobObject);
        }
    }

    /**
     * cancelMediaJob 删除任务
     * @param client
     */
    public static void cancelMediaJob(COSClient client) {
        MediaJobsRequest request = new MediaJobsRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setJobId("jae776cb4ec3011eab2cdd3817d4d5e64");
        Boolean response = client.cancelMediaJob(request);
        System.out.println(response);
    }
}
