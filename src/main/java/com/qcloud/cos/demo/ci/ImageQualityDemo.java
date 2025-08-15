package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.ImageQualityRequest;
import com.qcloud.cos.model.ciModel.job.ImageQualityResponse;

/*
 * 图片质量评估相关demo https://cloud.tencent.com/document/product/460/63228
 */
public class ImageQualityDemo {
    public static void main(String[] args) {
        // 1. 初始化客户端
        COSClient client = ClientUtils.getTestClient();
        ImageQualityResponse response = assessImageQuality(client);
        System.out.println(response.toString());
    }

    public static ImageQualityResponse assessImageQuality(COSClient client) {
        // 1. 创建请求对象
        ImageQualityRequest request = new ImageQualityRequest();

        // 2. 设置请求参数
        request.setBucketName("demobucket-1251704708");
        request.setObjectKey("demo.jpeg");

        // 3. 发起GET请求，返回结果
        return client.AccessImageQulity(request);
    }
}
