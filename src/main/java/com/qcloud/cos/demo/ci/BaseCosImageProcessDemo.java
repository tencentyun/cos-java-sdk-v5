package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.CImageProcessRequest;

public class BaseCosImageProcessDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        processImage(client);
    }

    public static void processImage(COSClient client){
        String bucketName = "beijingtest-1251704708";
        String key = "1.jpg";
        CImageProcessRequest request = new CImageProcessRequest(bucketName,key);
        request.setCiSpecialEndParameter("");
        boolean ciUploadResult = client.processImage2(request);

    }
}
