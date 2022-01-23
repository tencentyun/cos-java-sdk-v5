package com.qcloud.cos.demo;

import java.io.File;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;

public class PicOperationDemo {
    public static void main(String[] args) {
        PicPersistentProcessing();
    }

    public static void PicPersistentProcessing() {
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
        // 4. bucket名需包含appid
        String bucketName = "examplebucket-1250000000";
        String key = "example.jpg";
        File localFile = new File("/data/example.jpg");

        // 5.对图像进行持久化处理
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setHeader("Pic-Operations", "{\"is_pic_info\":1,\"rules\":[{\"fileid\":\"example.png\",\"rule\":\"imageView2/format/png\"}]}");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        putObjectRequest.setMetadata(objectMetadata);
        try {
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        // 关闭客户端
        cosclient.shutdown();
    }
}
