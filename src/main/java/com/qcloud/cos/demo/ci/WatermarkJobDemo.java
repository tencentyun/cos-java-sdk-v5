package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.MediaAudioObject;
import com.qcloud.cos.model.ciModel.job.MediaContainerObject;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeObject;
import com.qcloud.cos.model.ciModel.job.MediaTranscodeVideoObject;
import com.qcloud.cos.model.ciModel.template.MediaSnapshotObject;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.model.ciModel.template.SpriteSnapshotConfig;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 媒体处理 snapshot job接口相关demo 详情见https://cloud.tencent.com/document/product/460/48222
 */
public class WatermarkJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建媒体任务。
     * demo 使用模板创建任务，如需自定义模板请先使用创建模板接口
     *
     * @param client
     */
    public static void createMediaJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        MediaJobsRequest request = new MediaJobsRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demobucket-1234567890");
        request.setTag("Transcode");
        request.getInput().setObject("1.mp4");
        MediaTranscodeObject transcode = request.getOperation().getTranscode();
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

        List<MediaWatermark> watermarkList = request.getOperation().getWatermarkList();
        MediaWatermark waterMark = new MediaWatermark();
        waterMark.setType("Text");
        waterMark.setLocMode("Absolute");
        waterMark.setDx("128");
        waterMark.setDy("128");
        waterMark.setPos("TopRight");
        waterMark.setStartTime("0");
        waterMark.setEndTime("100.5");
        MediaWaterMarkText text = waterMark.getText();
        text.setText("水印内容");
        text.setFontSize("30");
        text.setFontType("simfang.ttf");
        text.setFontColor("0x112233");
        text.setTransparency("30");
        watermarkList.add(waterMark);

        waterMark = new MediaWatermark();
        waterMark.setType("Text");
        waterMark.setLocMode("Absolute");
        waterMark.setDx("20");
        waterMark.setDy("20");
        waterMark.setPos("TopRight");
        waterMark.setStartTime("0");
        waterMark.setEndTime("100.5");
        text = waterMark.getText();
        text.setText("水印内容");
        text.setFontSize("30");
        text.setFontType("simfang.ttf");
        text.setFontColor("0x112233");
        text.setTransparency("30");
        watermarkList.add(waterMark);

        request.getOperation().getOutput().setBucket("demobucket-1234567890");
        request.getOperation().getOutput().setRegion("ap-chongqing");
        request.getOperation().getOutput().setObject("watermark-demo.mp4");
        request.setQueueId("p9900025e4ec44b5e8225e70a521*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponse response = client.createMediaJobs(request);
        System.out.println(response);
    }

}
