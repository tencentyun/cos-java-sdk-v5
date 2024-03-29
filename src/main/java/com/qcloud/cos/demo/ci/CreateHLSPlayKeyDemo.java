package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.hls.CreateHLSPlayKeyRequest;
import com.qcloud.cos.model.ciModel.hls.CreateHLSPlayKeyResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 生成hls播放密钥 详情见暂无
 */
public class CreateHLSPlayKeyDemo {

    public static void main(String[] args) {
        // 2 调用要使用的方法。
    }

    /**
     * createHLSPlayKey 该接口用于生成hls播放密钥。
     * 该接口属于 POST 请求。
     */
    public static void createHLSPlayKey(COSClient client) {
        CreateHLSPlayKeyRequest request = new CreateHLSPlayKeyRequest();
        request.setBucketName("demo-1234567890");

        CreateHLSPlayKeyResponse response = client.createHLSPlayKey(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
