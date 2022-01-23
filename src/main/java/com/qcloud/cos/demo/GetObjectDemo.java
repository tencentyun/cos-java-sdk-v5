package com.qcloud.cos.demo;

import java.io.File;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;

public class GetObjectDemo {
    public static void main(String[] args) {
        GetObjectToFileDemo();
    }

    public static void GetObjectToFileDemo() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");

        ClientConfig clientConfig = new ClientConfig();

        // 2 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        String key = "test/my_test.json";
        String bucketName = "mybucket-1251668577";
        boolean useTrafficLimit = false;
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        if(useTrafficLimit) {
            getObjectRequest.setTrafficLimit(8*1024*1024);
        }
        File localFile = new File("my_test.json");
        ObjectMetadata objectMetadata = cosclient.getObject(getObjectRequest, localFile);
        System.out.println(objectMetadata.getContentLength());
    }
}

