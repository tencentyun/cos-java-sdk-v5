package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.ai.RecognizeLogoRequest;
import com.qcloud.cos.model.ciModel.ai.RecognizeLogoResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * Logo 识别 详情见https://cloud.tencent.com/document/product/460/79736
 */
public class RecognizeLogoDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        recognizeLogo(client);
    }

    /**
     * recognizeLogo 腾讯云数据万象通过 RecognizeLogo 接口实现对图片内电商 Logo 的识别，返回图片中 Logo 的名称、坐标、置信度分值。
     * 返回图片中Logo的名称、坐标、置信度分值。图片Logo识别请求包属于 GET 请求
     * 该接口属于 GET 请求。
     */
    public static void recognizeLogo(COSClient client) {
        RecognizeLogoRequest request = new RecognizeLogoRequest();
        request.setBucketName("demo-1234567890");
        request.setDetectUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/car.jpg");// 设置待检查图片url，需要进行urlencode

        RecognizeLogoResponse response = client.recognizeLogo(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
