package com.qcloud.cos.demo.ci;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.ImageLabelRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Request;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Response;

/**
 * 图片标签接口使用demo https://cloud.tencent.com/document/product/460/39082
 */
public class ImageLabelDemo {

    public static void main(String[] args) throws JsonProcessingException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        getImageLabelV2(client);
    }

    /**
     * getImageLabel 图片标签,返回图片中置信度较高的主题标签。
     *
     * @param client
     */
    public static void getImageLabel(COSClient client) throws JsonProcessingException {
        //1.创建任务请求对象
        ImageLabelRequest request = new ImageLabelRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        request.setObjectKey("mark1/1.png");
        //3.调用接口,获取任务响应对象
        ImageLabelResponse response = client.getImageLabel(request);
        String jsonStr = responseToJsonStr(response);
        System.out.println(jsonStr);
    }

    /**
     * getImageLabelV2 图片标签,返回图片中置信度较高的主题标签。
     *
     * @param client
     */
    public static void getImageLabelV2(COSClient client) throws JsonProcessingException {
        //1.创建任务请求对象
        ImageLabelV2Request request = new ImageLabelV2Request();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("shanghai-1251704708");
        request.setObjectKey("marksdk/data/img5.png");
        request.setScenes("web,product");
        //3.调用接口,获取任务响应对象
        ImageLabelV2Response response = client.getImageLabelV2(request);
        String jsonStr = responseToJsonStr(response);
        System.out.println(jsonStr);
    }

    public static String responseToJsonStr(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}
