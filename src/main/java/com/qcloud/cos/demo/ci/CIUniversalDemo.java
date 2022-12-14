package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;

public class CIUniversalDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        customCIMethod(client);
    }

    /**
     * createImageAuditingJob 接口用于创建图片审核任务。(发送单个任务 推荐)
     *
     * @param client
     */
    public static void customCIMethod(COSClient client) {
        //1.创建任务请求对象
        ImageAuditingRequest request = new ImageAuditingRequest();

    }
}
