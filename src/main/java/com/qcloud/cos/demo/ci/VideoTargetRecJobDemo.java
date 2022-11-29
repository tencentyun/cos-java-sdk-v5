package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;
import com.qcloud.cos.model.ciModel.job.VideoTargetRec;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaTemplateResponse;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkImage;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.utils.Jackson;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 媒体处理 目标检测任务 job接口相关demo
 */
public class VideoTargetRecJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeMediaJob(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务
     * 使用目标检测模板创建任务 推荐
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("VideoTargetRec");
        request.getInput().setObject("1.mp4");
        //2.1添加媒体任务操作参数
        MediaJobOperation operation = request.getOperation();
        operation.setTemplateId("t1c842352fb4514b99a6e8ba6bf625778e");
        operation.setJobLevel("0");

        operation.getOutput().setBucket("demo-1234567890");
        operation.getOutput().setRegion("ap-shanghai");
        operation.getOutput().setObject("1.mp4");
        request.setQueueId("p048685dcc97145b7a3d6d20b872fa094");
        request.setCallBack("https://cloud.tencent.com/xxx");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response.getJobsDetail().getJobId());
    }

    /**
     * createMediaJobs 接口用于创建媒体任务
     * 使用目标检测参数创建任务
     */
    public static void createMediaJobs2(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("VideoTargetRec");
        request.getInput().setObject("1.mp4");
        //2.1添加媒体任务操作参数
        MediaJobOperation operation = request.getOperation();
        operation.setJobLevel("0");

        VideoTargetRec videoTargetRec = operation.getVideoTargetRec();
        videoTargetRec.setBody("false");
        videoTargetRec.setPet("true");
        videoTargetRec.setCar("false");

        operation.getOutput().setBucket("demo-1234567890");
        operation.getOutput().setRegion("ap-shanghai");
        operation.getOutput().setObject("1.mp4");
        request.setQueueId("p048685dcc97145b7a3d6d20b872fa094");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response);
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     *
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("abc9b178e6c8d11ed83b92b9bc98*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.describeMediaJob(request);
        System.out.println(response);
    }

    /**
     * CreateMediaTemplate 用于新增水印模板。
     *
     * @param client
     */
    public static void createMediaTemplate(COSClient client) throws UnsupportedEncodingException {
        //1.创建模板请求对象
        MediaTemplateRequest request = new MediaTemplateRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("VideoTargetRec");
        request.setName("mark-VideoTargetRec");
        VideoTargetRec videoTargetRec = request.getVideoTargetRec();
        videoTargetRec.setCar("true");
        videoTargetRec.setBody("true");
        videoTargetRec.setPet("false");

        MediaTemplateResponse response = client.createMediaTemplate(request);
        System.out.println(response);
    }

}
