package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

/**
 * 媒体处理 job接口相关demo 详情见https://cloud.tencent.com/document/product/460/84790
 */
public class JobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobsWithAigcMetadata(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务。
     * demo 使用模板创建任务，如需自定义模板请先使用创建模板接口
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Transcode");
        request.getInput().setObject("1.mp4");
        ArrayList<String> watermarkTemplateList = new ArrayList<>();
        watermarkTemplateList.add("templateId");
        request.getOperation().setWatermarkTemplateId(watermarkTemplateList);
        request.getOperation().setTemplateId("t0e09a9456d4124542b1f0e44d501*****");
        request.getOperation().getOutput().setBucket("demo-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("2.mp4");
        request.setCallBack("https://cloud.tencent.com/xxx");
//        request.setCallBackFormat("json");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(response);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务
     * demo 使用转码参数创建任务 推荐使用模板创建媒体任务
     *
     * @param client
     */
    public static void createMediaJobs2(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Transcode");
        request.getInput().setObject("2.mp4");
        //2.1添加媒体任务操作参数
        MediaJobOperation operation = request.getOperation();
//        operation.setCustomId("t11282671b1a0846a9be6b438563*****");
        MediaTranscodeObject transcode = operation.getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");
        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("0");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");

        List<MediaRemoveWaterMark> removeWatermarkList = operation.getRemoveWatermarkList();
        MediaRemoveWaterMark removeWaterMark = new MediaRemoveWaterMark();
        removeWaterMark.setDx("1");
        removeWaterMark.setDy("2");
        removeWaterMark.setWidth("10");
        removeWaterMark.setHeight("10");
        removeWaterMark.setStartTime("0");
        removeWaterMark.setEndTime("1");
        removeWatermarkList.add(removeWaterMark);

        removeWaterMark = new MediaRemoveWaterMark();
        removeWaterMark.setDx("2");
        removeWaterMark.setDy("3");
        removeWaterMark.setWidth("10");
        removeWaterMark.setHeight("10");
        removeWaterMark.setStartTime("1");
        removeWaterMark.setEndTime("3");
        removeWatermarkList.add(removeWaterMark);
        operation.getOutput().setBucket("demo-1234567890");
        operation.getOutput().setRegion("ap-chongqing");
        operation.getOutput().setObject("demo1.mp4");
        request.setCallBack("https://cloud.tencent.com/xxx");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(response.getJobsDetail().getJobId());
    }



    /**
     * createMediaJobs 接口用于创建媒体任务  并且添加水印参数
     * demo 使用转码参数创建任务 推荐使用模板创建媒体任务
     *
     * @param client
     */
    public static void createMediaJobs3(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Transcode");
        request.getInput().setObject("2.mp4");
        //2.1添加媒体任务操作参数
        MediaJobOperation operation = request.getOperation();
        MediaWatermark watermark = operation.getWatermark();
        watermark.setType("Text");
        watermark.setLocMode("Absolute");
        watermark.setDx("10");
        watermark.setDy("10");
        watermark.setPos("Pos");
        watermark.setPos("TopRight");
        watermark.setStartTime("0");
        watermark.setEndTime("100.5");
        MediaWaterMarkText text = watermark.getText();
        text.setFontType("simfang.ttf");
        text.setText("水印内容");
        text.setFontSize("30");
        text.setFontColor("0xFF0000");
        text.setTransparency("30");
        MediaTranscodeObject transcode = operation.getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");
        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("0");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");

        operation.getOutput().setBucket("demo-1234567890");
        operation.getOutput().setRegion("ap-chongqing");
        operation.getOutput().setObject("result.mp4");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(response.getJobsDetail().getJobId());
    }

    /**
     * createMediaJobsWithAigcMetadata 接口用于创建媒体转码任务（包含aigcMetadata信息）
     * demo 使用转码参数创建任务 推荐使用模板创建媒体任务
     *
     * @param client
     */
    public static void createMediaJobsWithAigcMetadata(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("chongqingtest-1251704708");
        request.setTag("Transcode");
        request.getInput().setObject("SDK/Media/demo.mp4");
        //2.1添加媒体任务操作参数
        MediaJobOperation operation = request.getOperation();
        MediaTranscodeObject transcode = operation.getTranscode();
        MediaContainerObject container = transcode.getContainer();
        container.setFormat("mp4");
        MediaTranscodeVideoObject video = transcode.getVideo();
        video.setCodec("H.264");
        video.setProfile("high");
        video.setBitrate("1000");
        video.setWidth("1280");
        video.setFps("30");
        video.setPreset("medium");
        video.setBufSize("10000");
        video.setMaxrate("50000");

        MediaAudioObject audio = transcode.getAudio();
        audio.setCodec("aac");
        audio.setSamplerate("44100");
        audio.setBitrate("128");
        audio.setChannels("4");

        MediaTimeIntervalObject timeInterval = transcode.getTimeInterval();
        timeInterval.setStart("0");
        timeInterval.setDuration("60");

        MediaTransConfigObject transConfig = transcode.getTransConfig();
        transConfig.setAdjDarMethod("scale");
        transConfig.setIsCheckAudioBitrate("false");
        transConfig.setResoAdjMethod("1");
        // 配置aigc元数据信息
        AigcMetadata aigcMetadata = transConfig.getAigcMetadata();
        aigcMetadata.setLabel("label");
        // 以下信息可选
//        aigcMetadata.setContentProducer("testProducer");
//        aigcMetadata.setProduceId("testProduceId");
        aigcMetadata.setReservedCode1(new String(Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setReservedCode2(new String(Base64.encodeBase64("test".getBytes())));
        aigcMetadata.setPropagateId("testPropagateId");
        aigcMetadata.setContentPropagator("testPropagator");

        operation.getOutput().setBucket("chongqingtest-1251704708");
        operation.getOutput().setRegion("ap-chongqing");
        operation.getOutput().setObject("SDK/Media/out/demo2.mp4");
//        request.setCallBack("https://cloud.tencent.com/xxx");
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
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("j12007314677b11f09e4931a92d9*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(response);
    }

    /**
     * describeMediaJobs 批量查询任务信息
     */
    public static void describeMediaJobs(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1251704708");
        request.setTag("Transcode");
        //3.调用接口,获取任务响应对象
        MediaListJobResponse response = client.describeMediaJobsV2(request);
        List<MediaJobObject> jobsDetail = response.getJobsDetailList();
        for (MediaJobObject mediaJobObject : jobsDetail) {
            System.out.println(mediaJobObject.getJobId());
            System.out.println(mediaJobObject.getState());
        }
    }

    public static void cancelMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("jbfb0d02a092111ebb3167781d*****");
        //3.调用接口,获取任务响应对象
        Boolean response = client.cancelMediaJob(request);
        System.out.println(response);
    }
}
