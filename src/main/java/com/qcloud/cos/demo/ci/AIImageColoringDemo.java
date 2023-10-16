package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.AIImageColoringRequest;

/**
 * 图片上色 详情见https://cloud.tencent.com/document/product/460/83794
 */
public class AIImageColoringDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        aIImageColoring(client);
    }

    /**
     * aIImageColoring 腾讯云数据万象通过 AIImageColoring 接口对黑白图像进行上色
     * 该接口属于 GET 请求。
     */
    public static void aIImageColoring(COSClient client) {
        AIImageColoringRequest request = new AIImageColoringRequest();
        request.setBucket("demo-1234567890");
        request.setObjectKey("ObjectKey");
        request.setCiProcess("");// 设置数据万象处理能力，图片上色参固定为AIImageColoring。
        request.setDetectUrl("");// 设置待上色图片url，需要进行urlencode，与ObjectKey二选其一，如果同时存在，则默认以ObjectKey为准

        client.aIImageColoring(request);
    }
}
