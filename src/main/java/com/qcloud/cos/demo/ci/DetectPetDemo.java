
package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.persistence.DetectPetRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectPetResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * 宠物识别demo https://cloud.tencent.com/document/product/460/95753
 * 宠物识别功能为同步请求方式，您可以通过本接口识别并输出画面中宠物，输出其位置（矩形框）和置信度。该接口属于 GET 请求。
 */
public class DetectPetDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        detectPet(client);
    }

    /**
     * 宠物识别接口demo
     */
    public static void detectPet(COSClient client) {
        //1.创建任务请求对象
        DetectPetRequest request = new DetectPetRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置bucket中的图片位置
        request.setObjectKey("pet.jpg");
        DetectPetResponse response = client.detectPet(request);
        System.out.println(Jackson.toJsonString(response));
    }
}