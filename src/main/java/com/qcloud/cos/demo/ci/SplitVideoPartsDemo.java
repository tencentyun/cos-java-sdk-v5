package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.model.ciModel.job.v2.SplitVideoInfoResult;
import com.qcloud.cos.model.ciModel.job.v2.SplitVideoParts;
import com.qcloud.cos.model.ciModel.job.v2.TimeInfo;

import java.util.List;

/**
 * 媒体处理视频拆条接口相关 demo
 * 详细内容请参考API : https://cloud.tencent.com/document/product/460/90888
 */
public class SplitVideoPartsDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeMediaJob(client);
    }

    /**
     * createMediaJobs 接口用于创建视频拆条
     * demo 创建视频拆条任务
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client){
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("SplitVideoParts");
        request.getInput().setObject("media/1.mp4");
        //2.1添加视频拆条操作参数
        SplitVideoParts splitVideoParts = request.getOperation().getSplitVideoParts();
        splitVideoParts.setMode("SHOTDETECT");

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
        request.setJobId("jb190c6d07d6d11ee97ab293941*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        SplitVideoInfoResult splitVideoInfoResult = response.getJobsDetail().getOperation().getSplitVideoInfoResult();
        List<TimeInfo> timeInfos = splitVideoInfoResult.getTimeInfos();
    }

}
