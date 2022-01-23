package com.qcloud.cos.demo;

import java.io.File;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.AppendObjectRequest;
import com.qcloud.cos.model.AppendObjectResult;

public class AppendObjectDemo {
    public static void AppendObjectFromLocal() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");

        ClientConfig clientConfig = new ClientConfig();

        // 2 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";
        String key = "aaa/bbb.txt";
        try {
            File localFile = new File("1M.txt");
            AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key, localFile);
            appendObjectRequest.setPosition(0L);
            AppendObjectResult appendObjectResult = cosclient.appendObject(appendObjectRequest);
            long nextAppendPosition = appendObjectResult.getNextAppendPosition();
            System.out.println(nextAppendPosition);

            localFile = new File("2M.txt");
            appendObjectRequest = new AppendObjectRequest(bucketName, key, localFile);
            appendObjectRequest.setPosition(nextAppendPosition);
            appendObjectResult = cosclient.appendObject(appendObjectRequest);
            nextAppendPosition = appendObjectResult.getNextAppendPosition();
            System.out.println(nextAppendPosition);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // 关闭客户端
        cosclient.shutdown();
    }

    public static void main(String[] args) {
        AppendObjectFromLocal();
    }
}
