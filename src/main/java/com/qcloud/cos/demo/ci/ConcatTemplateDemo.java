package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.template.MediaListTemplateResponse;
import com.qcloud.cos.model.ciModel.template.MediaTemplateObject;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaTemplateResponse;
import com.qcloud.cos.utils.Jackson;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 媒体处理拼接模板接口相关demo 请求详情参见：https://cloud.tencent.com/document/product/460/77089
 */
public class ConcatTemplateDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createMediaTemplate(client);
    }

    /**
     * CreateMediaTemplate 用于新增水印模板。
     * @param client
     */
    public static void createMediaTemplate(COSClient client) throws UnsupportedEncodingException {
        //1.创建模板请求对象
        MediaTemplateRequest request = new MediaTemplateRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Concat");
        request.setName("ConcatTemplate42");
        MediaConcatTemplateObject concatTemplate = request.getConcat();
        concatTemplate.getContainer().setFormat("mp4");
        List<MediaConcatFragmentObject> concatList = concatTemplate.getConcatFragmentList();
        MediaConcatFragmentObject fragment = new MediaConcatFragmentObject();
        fragment.setMode("Start");
        fragment.setUrl("http://demo-1234567890.cos.ap-chongqing.myqcloud.com/demo1.mp4");
        concatList.add(fragment);

        SceneChangeInfo sceneChangeInfo = concatTemplate.getSceneChangeInfo();
        sceneChangeInfo.setMode("XFADE");
        sceneChangeInfo.setTime("5");
        sceneChangeInfo.setTransitionType("fade");

        MediaTemplateResponse response = client.createMediaTemplate(request);
        System.out.println(response);
    }

    /**
     * DescribeMediaTemplates 用于查询模板。
     *
     * @param client
     */
    public static void describeMediaTemplatesV2(COSClient client) {
        //1.创建模板请求对象
        MediaTemplateRequest request = new MediaTemplateRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag("Concat");
        MediaListTemplateResponse response = client.describeMediaTemplatesV2(request);
        List<MediaTemplateObject> templateList = response.getTemplateList();
        for (MediaTemplateObject mediaTemplateObject : templateList) {
            System.out.println(Jackson.toJsonString(mediaTemplateObject));
        }
    }
}
