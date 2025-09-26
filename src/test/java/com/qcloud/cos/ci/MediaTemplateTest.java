package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.job.MediaVideoObject;
import com.qcloud.cos.model.ciModel.template.*;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class MediaTemplateTest extends AbstractCOSClientCITest {

    private String templateId;
    public static final String TAG = "Transcode";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void updateMediaTemplateTest() {
        try {//1.创建模板请求对象
            MediaTemplateRequest request = new MediaTemplateRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            request.setTemplateId("t131234b230be049ab9e11b39fa613bb45");
            request.setTag("Watermark");
            MediaWatermark waterMark = request.getWatermark();
            waterMark.setType("Text");
            waterMark.setLocMode("Absolute");
            waterMark.setDx("128");
            waterMark.setDy("128");
            waterMark.setPos("TopRight");
            waterMark.setStartTime("0");
            waterMark.setEndTime("100.5");
            MediaWaterMarkText text = waterMark.getText();
            text.setText("修改水印内容");
            text.setFontSize("30");
            text.setFontType("simfang.ttf");
            text.setFontColor("0x112233");
            text.setTransparency("30");
            Boolean aBoolean = cosclient.updateMediaTemplate(request);
            System.out.println(aBoolean);
        } catch (Exception e) {

        }

    }


    @Test
    public void updateMediaTemplateTest2() {
        try {
            //1.创建模板请求对象
            MediaTemplateRequest request = new MediaTemplateRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            request.setTemplateId("t131234b230be049ab9e11b39fa613bb45");
            request.setTag("Watermark");
            String s = UUID.randomUUID().toString();
            request.setName(s);
            MediaWatermark waterMark = request.getWatermark();
            waterMark.setType("Text");
            waterMark.setLocMode("Absolute");
            waterMark.setDx("128");
            waterMark.setDy("128");
            waterMark.setPos("TopRight");
            waterMark.setStartTime("0");
            waterMark.setEndTime("100.5");
            MediaWaterMarkText text = waterMark.getText();
            text.setText("修改水印内容");
            text.setFontSize("30");
            text.setFontType("simfang.ttf");
            text.setFontColor("0x112233");
            text.setTransparency("30");
            Boolean aBoolean = cosclient.updateMediaTemplate(request);
            System.out.println(aBoolean);
        } catch (Exception e) {

        }

    }

    @Test
    public void updateMediaTemplateTest3() {
        try {
            //1.创建模板请求对象
            MediaTemplateRequest request = new MediaTemplateRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            request.setTemplateId("t131234b230be049ab9e11b39fa613bb45");
            String s = UUID.randomUUID().toString();
            request.setName(s);
            MediaWatermark waterMark = request.getWatermark();
            waterMark.setType("Text");
            waterMark.setLocMode("Absolute");
            waterMark.setDx("128");
            waterMark.setDy("128");
            waterMark.setPos("TopRight");
            waterMark.setStartTime("0");
            waterMark.setEndTime("100.5");
            MediaWaterMarkText text = waterMark.getText();
            text.setText("修改水印内容");
            text.setFontSize("30");
            text.setFontType("simfang.ttf");
            text.setFontColor("0x112233");
            text.setTransparency("30");
            Boolean aBoolean = cosclient.updateMediaTemplate(request);
            System.out.println(aBoolean);
        } catch (Exception e) {

        }

    }

    @Test
    public void updateMediaTemplateTest4() {
        try {
            //1.创建模板请求对象
            MediaTemplateRequest request = new MediaTemplateRequest();
            //2.添加请求参数 参数详情请见api接口文档
            request.setBucketName(bucket);
            request.setTag("Concat");
            MediaListTemplateResponse response = cosclient.describeMediaTemplatesV2(request);
            List<MediaTemplateObject> templateList = response.getTemplateList();
            for (MediaTemplateObject mediaTemplateObject : templateList) {
                System.out.println(Jackson.toJsonString(mediaTemplateObject));
            }
        } catch (Exception e) {

        }

    }

    @Test
    public void createMediaTemplateWithVideoFpsCheckTest() {
        try {
            // 1. 创建模板请求对象
            MediaTemplateRequest request = new MediaTemplateRequest();

            // 2. 设置基本参数
            request.setBucketName(bucket);
            String uniqueName = "unit-test-fps-check-" + UUID.randomUUID();
            request.setName(uniqueName);
            request.setTag("Transcode");

            // 3. 设置Container格式
            request.getContainer().setFormat("mp4");

            // 4. 设置Video参数
            MediaVideoObject video = request.getVideo();
            video.setCodec("H.264");
            video.setProfile("high");
            video.setBitrate("1000");
            video.setWidth("1280");
            video.setFps("30");
            video.setPreset("medium");
            video.setBufSize("1000");
            video.setMaxrate("1000");

            // 5. 设置Audio参数
            request.getAudio().setCodec("aac");
            request.getAudio().setSamplerate("44100");
            request.getAudio().setBitrate("128");
            request.getAudio().setChannels("2");

            // 6. 设置转码配置，新增的两个字段
            request.getTransConfig().setAdjDarMethod("scale");
            request.getTransConfig().setIsCheckReso("false");
            request.getTransConfig().setResoAdjMethod("1");
            // 6.1 新增：开启视频帧率检查
            request.getTransConfig().setIsCheckVideoFps("true");
            // 6.2 新增：帧率调整方式，0使用原帧率
            request.getTransConfig().setVideoFpsAdjMethod("0");

            // 7. 调用接口创建模板
            MediaTemplateResponse response = cosclient.createMediaTemplate(request);

            System.out.println(response.getTemplate().getTemplateId());
            System.out.println("IsCheckVideoFps: " + response.getTemplate().getTransConfig().getIsCheckVideoFps());
            System.out.println("VideoFpsAdjMethod: " + response.getTemplate().getTransConfig().getVideoFpsAdjMethod());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
