package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.hls.GetHLSPlayKeyRequest;
import com.qcloud.cos.model.ciModel.hls.GetHLSPlayKeyResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 获取hls播放密钥 详情见暂无
 */
public class GetHLSPlayKeyDemo {

    /**
     * getHLSPlayKey 该接口用于获取hls播放密钥。
     * 该接口属于 GET 请求。
     */
    public static void getHLSPlayKey(COSClient client) {
        GetHLSPlayKeyRequest request = new GetHLSPlayKeyRequest();
        request.setBucketName("demo-1234567890");

        GetHLSPlayKeyResponse response = client.getHLSPlayKey(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
