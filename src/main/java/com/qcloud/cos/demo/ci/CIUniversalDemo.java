package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.AICommonRequest;

public class CIUniversalDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        customCIMethod(client);
    }

    public static void customCIMethod(COSClient client) {
        //1.创建任务请求对象
        AICommonRequest request = new AICommonRequest();
        request.setBucketName("demo-1234567890");
        request.setObjectKey("ObjectKey");
    }
}
