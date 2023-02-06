package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.ColorEnhance;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;
import com.qcloud.cos.model.ciModel.job.SuperResolution;
import com.qcloud.cos.model.ciModel.job.VideoEnhance;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaTemplateResponse;

import java.io.UnsupportedEncodingException;

/**
 * 媒体处理 画质增强任务接口相关demo 详情见https://cloud.tencent.com/document/product/460/48216
 */
public class VideoEnhanceJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务
     * demo 使用画质增强参数创建任务 推荐使用模板创建媒体任务
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        request.setTag("VideoEnhance");
        request.getInput().setObject("1.mp4");

        VideoEnhance videoEnhance = request.getOperation().getVideoEnhance();
        MediaTranscodeObject trascode = videoEnhance.getTrascode();
        MediaContainerObject container = trascode.getContainer();
        container.setFormat("mp4");
        MediaTranscodeVideoObject video = trascode.getVideo();
        video.setCodec("H.264");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        MediaAudioObject audio = trascode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        SuperResolution superResolution = videoEnhance.getSuperResolution();
        superResolution.setResolution("sdtohd");
        superResolution.setEnableScaleUp("true");
        superResolution.setVersion("Enhance");

        ColorEnhance colorEnhance = videoEnhance.getColorEnhance();
        colorEnhance.setContrast("50");
        colorEnhance.setCorrection("100");
        colorEnhance.setSaturation("100");

        videoEnhance.getMsSharpen().setSharpenLevel("5");
        videoEnhance.getSdrToHDR().setHdrMode("HDR10");

        request.getOperation().getOutput().setBucket("markjrzhang-1251704708");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("VideoEnhance.mp4");
        request.setQueueId("p9900025e4ec44b5e8225e70a52170834");
        request.setCallBack("https://cloud.tencent.com/xxx");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response.getJobsDetail().getJobId());
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
        request.setJobId("jbfb0d02a092111ebb3167781d*****");
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
        request.setBucketName("markjrzhang-1251704708");
        request.setTag("VideoEnhance");
        request.setName("mark-VideoEnhance");

        VideoEnhance videoEnhance = request.getVideoEnhance();
        MediaTranscodeObject trascode = videoEnhance.getTrascode();
        MediaContainerObject container = trascode.getContainer();
        container.setFormat("mp4");
        MediaTranscodeVideoObject video = trascode.getVideo();
        video.setCodec("H.264");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        MediaAudioObject audio = trascode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        SuperResolution superResolution = videoEnhance.getSuperResolution();
        superResolution.setResolution("sdtohd");
        superResolution.setEnableScaleUp("true");
        superResolution.setVersion("Enhance");

        ColorEnhance colorEnhance = videoEnhance.getColorEnhance();
        colorEnhance.setContrast("50");
        colorEnhance.setCorrection("100");
        colorEnhance.setSaturation("100");

        videoEnhance.getMsSharpen().setSharpenLevel("5");
        videoEnhance.getSdrToHDR().setHdrMode("HDR10");

        MediaTemplateResponse response = client.createMediaTemplate(request);
        System.out.println(response);
    }
}
