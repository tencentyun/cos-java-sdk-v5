package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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

    @Test(expected = IllegalArgumentException.class)
    public void updateMediaTemplateTest() {
        //1.创建模板请求对象
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
    }


    @Test
    public void updateMediaTemplateTest2() {
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
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateMediaTemplateTest3() {
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
    }
}
