package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.hls.GetHLSPlayKey;
import com.qcloud.cos.model.ciModel.hls.GetHLSPlayKeyRequest;
import com.qcloud.cos.model.ciModel.hls.GetHLSPlayKeyResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 获取hls播放密钥 详情见暂无
 */
public class GetHLSPlayKeyDemo {

    public static void main(String[] args) {

        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX","1A2Z3YYYYYYYYYY");
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 生成cos客户端
        COSClient client = new COSClient(cred, clientConfig);
        // 2 调用要使用的方法。
        getHLSPlayKey(client);
    }

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
