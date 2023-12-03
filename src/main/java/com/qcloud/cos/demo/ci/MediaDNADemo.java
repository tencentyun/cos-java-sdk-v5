package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.v2.DNADbConfigsRequest;
import com.qcloud.cos.model.ciModel.job.v2.DNADbConfigsResponse;
import com.qcloud.cos.model.ciModel.job.v2.DNADbFilesRequest;
import com.qcloud.cos.model.ciModel.job.v2.DNADbFilesResponse;
import com.qcloud.cos.model.ciModel.job.v2.DnaConfig;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.utils.Jackson;

/**
 * 视频 DNA 任务可实现视频入库、视频查重、视频出库操作。
 * 详细内容请参考API : https://cloud.tencent.com/document/product/460/96115
 */
public class MediaDNADemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建DNA任务
     * demo 创建DNA 任务任务
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("DNA");
        request.getInput().setObject("media/test.mp4");
        //2.1添加DNA 任务操作参数
        DnaConfig dnaConfig = request.getOperation().getDnaConfig();
        dnaConfig.setRuleType("GetFingerPrint");
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
        request.setJobId("j9cdbb46e845411ee9d800f9b074616ce");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(Jackson.toJsonString(response));

    }

    /**
     * describeMediaDnaDbFiles 获取 DNA 库中文件列表
     */
    public static void describeMediaDnaDbFiles(COSClient client) {
        //1.创建任务请求对象
        DNADbFilesRequest request = new DNADbFilesRequest();
        request.setBucketName("demo-1234567890");
        request.setDnaDbId("Dnadeed68f8d15a45e6b867491195904695");
        //2.添加请求参数 参数详情请见api接口文档
        //3.调用接口,获取任务响应对象
        DNADbFilesResponse response = client.describeMediaDnaDbFiles(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * describeMediaDnaDbs 查询 DNA 库列表
     */
    public static void describeMediaDnaDbs(COSClient client) {
        //1.创建任务请求对象
        DNADbConfigsRequest request = new DNADbConfigsRequest();
        request.setBucketName("demo-1234567890");
        //2.添加请求参数 参数详情请见api接口文档
        //3.调用接口,获取任务响应对象
        DNADbConfigsResponse response = client.describeMediaDnaDbs(request);
        System.out.println(Jackson.toJsonString(response));
    }


}
