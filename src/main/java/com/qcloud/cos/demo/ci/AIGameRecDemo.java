package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.persistence.AIGameRecResponse;
import com.qcloud.cos.model.ciModel.persistence.AIRecRequest;
import com.qcloud.cos.utils.Jackson;

/**
 * 游戏图场景识别demo
 */
public class AIGameRecDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        aiGameRec(client);
    }

    public static void aiGameRec(COSClient client) {
        //1.创建任务请求对象
        AIRecRequest request = new AIRecRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置bucket中的图片位置
//        request.setObjectKey("1.jpg");
        //2.3或设置图片url
        request.setDetectUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/1.jpg");
        AIGameRecResponse response = client.aiGameRec(request);
        System.out.println(Jackson.toJsonString(response));
    }

}
