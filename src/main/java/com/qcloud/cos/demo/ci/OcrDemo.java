package com.qcloud.cos.demo.ci;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.ai.ImageOCRRequest;
import com.qcloud.cos.model.ciModel.ai.ImageOCRResponse;
import com.qcloud.cos.utils.Jackson;

import java.io.UnsupportedEncodingException;

/**
 * OCR 同步请求demo https://cloud.tencent.com/document/product/460/63227
 */
public class OcrDemo {

    public static void main(String[] args) throws JsonProcessingException, UnsupportedEncodingException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        imageOCR(client);
    }

    /**
     * imageOCR 通用文字识别功能（Optical Character Recognition，OCR）
     * 基于行业前沿的深度学习技术，将图片上的文字内容，智能识别为可编辑的文本
     * 可应用于随手拍扫描、纸质文档电子化、电商广告审核等多种场景，大幅提升信息处理效率。
     */
    public static void imageOCR(COSClient client)  {
        //1.创建任务请求对象
        ImageOCRRequest request = new ImageOCRRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        //2.1设置图片位置或直接传入图片的url
        request.setObjectKey("1.jpg");
//        request.setDetectUrl("https://demo-123456789.cos.ap-chongqing.myqcloud.com/1.png");
        //3.调用接口,获取任务响应对象
        ImageOCRResponse response = client.imageOCR(request);
        System.out.println(Jackson.toJsonString(response));
    }

}
