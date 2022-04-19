package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Request;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Response;

/**
 * GetPrivateM3U8 接口用于获取私有 M3U8 ts 资源的下载授权
 * 请求详情参见：https://cloud.tencent.com/document/product/436/63740
 */
public class GetPrivateM3U8 {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        getPrivateM3U8(client);
    }

    public static void getPrivateM3U8(COSClient client)  {
        //1.创建截图请求对象
        PrivateM3U8Request request = new PrivateM3U8Request();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-1234567890");
        //私有 ts 资源 url 下载凭证的相对有效期，单位为秒，范围为[3600, 43200]
        request.setExpires("3600");
        request.setObject("1.m3u8");
        //3.调用接口,获取截图响应对象
        PrivateM3U8Response privateM3U8 = client.getPrivateM3U8(request);
        System.out.println(privateM3U8.getM3u8());
        System.out.println(privateM3U8.getRequestId());
    }
}
