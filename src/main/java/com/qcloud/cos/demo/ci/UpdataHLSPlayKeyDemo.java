package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.hls.UpdataHLSPlayKeyRequest;
import com.qcloud.cos.model.ciModel.hls.UpdataHLSPlayKeyResponse;
import com.qcloud.cos.utils.Jackson;


/**
 * 更新hls播放密钥 详情见暂无
 */
public class UpdataHLSPlayKeyDemo {

    /**
     * updataHLSPlayKey 该接口用于更新hls播放密钥。
     * 该接口属于 PUT 请求。
     */
    public static void updataHLSPlayKey(COSClient client) {
        UpdataHLSPlayKeyRequest request = new UpdataHLSPlayKeyRequest();
        request.setBucketName("demo-1234567890");
        // 设置备播放密钥;是否必传：是
        request.setBackupPlayKey("");

        UpdataHLSPlayKeyResponse response = client.updataHLSPlayKey(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
