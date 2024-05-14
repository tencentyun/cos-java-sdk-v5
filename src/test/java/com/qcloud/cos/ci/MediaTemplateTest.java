package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.template.MediaListTemplateResponse;
import com.qcloud.cos.model.ciModel.template.MediaTemplateObject;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaWaterMarkText;
import com.qcloud.cos.model.ciModel.template.MediaWatermark;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Before;
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
}
