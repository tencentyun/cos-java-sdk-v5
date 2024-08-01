package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaVideoObject;
import com.qcloud.cos.model.ciModel.job.TransTpl;
import com.qcloud.cos.model.ciModel.job.VideoTargetRec;
import com.qcloud.cos.model.ciModel.job.v2.*;
import com.qcloud.cos.model.ciModel.template.MediaListTemplateResponse;
import com.qcloud.cos.model.ciModel.template.MediaTemplateObject;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.utils.CIJackson;

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
        describeMediaTemplates(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务
     * 使用目标检测模板创建任务 推荐
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("VideoTargetRec");
        request.getInput().setObject("1.mp4");
        //2.1添加媒体任务操作参数
        MediaJobOperation operation = request.getOperation();

        VideoTargetRec videoTargetRec = operation.getVideoTargetRec();
        videoTargetRec.setBody("true");
        videoTargetRec.setPet("true");
        videoTargetRec.setCar("true");
        videoTargetRec.setFace("true");

        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(response.getJobsDetail().getJobId());
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     *
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("a794b193e4fad11ef8a36bfd795e*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(CIJackson.toJsonString(response));
    }

    /**
     * CreateMediaTemplate 用于新增水印模板。
     *
     * @param client
     */
    public static void createMediaTemplate(COSClient client) throws UnsupportedEncodingException {
        //1.创建模板请求对象
        MediaTemplateRequestV2 request = new MediaTemplateRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("VideoTargetRec");
        request.setName("mark-VideoTargetRec-175");
        VideoTargetRec videoTargetRec = new VideoTargetRec();
        videoTargetRec.setCar("false");
        videoTargetRec.setBody("false");
        videoTargetRec.setPet("false");
        videoTargetRec.setFace("true");
        videoTargetRec.setProcessType("Mosaic");

        TransTpl transTpl = new TransTpl();
        MediaContainerObject container = new MediaContainerObject();
        container.setFormat("mp4");
        transTpl.setContainer(container);

        MediaVideoObject video = new MediaVideoObject();
        video.setCodec("H.264");
        video.setCrf("23");
        transTpl.setVideo(video);
        videoTargetRec.setTransTpl(transTpl);
        request.setVideoTargetRec(videoTargetRec);
        MediaTemplateResponseV2 response = client.createMediaTemplateV2(request);

        System.out.println(CIJackson.toJsonString(response));
    }

    public static void describeMediaTemplates(COSClient client) {
        //1.创建模板请求对象
        MediaTemplateRequest request = new MediaTemplateRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("VideoTargetRec");
        //3.调用接口,获取模板响应对象
        MediaListTemplateResponse response = client.describeMediaTemplatesV2(request);
        List<MediaTemplateObject> templateList = response.getTemplateList();
        for (MediaTemplateObject mediaTemplateObject : templateList) {
            System.out.println(CIJackson.toJsonString(mediaTemplateObject));
        }
    }
}
