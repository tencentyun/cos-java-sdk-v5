package com.qcloud.cos.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

public class PutObjectDemo {
    private static String secretId = "AKIDXXXXXXXX";
    private static String secretKey = "1A2Z3YYYYYYYYYY";
    private static String bucketName = "examplebucket-12500000000";
    private static String region = "ap-guangzhou";
    private static COSClient cosClient = createCli();;

    public static void main(String[] args) {
        try {
            putLocalFileDemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }

    private static COSClient createCli() {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 生成cos客户端
        return new COSClient(cred, clientConfig);
    }

    private static void putLocalFileDemo() {
        String key = "abc/abc.txt";
        String localPath = "abc.txt";

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, new File(localPath));
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getRequestId());
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }

    private static void putInputStreamDemo() {
        String key = "abc/def.txt";
        // 这里创建一个 ByteArrayInputStream 来作为示例，实际中这里应该是您要上传的 InputStream 类型的流
        int inputStreamLength = 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
        // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
        objectMetadata.setContentLength(inputStreamLength);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        // 设置单链接限速（如有需要），不需要可忽略
        putObjectRequest.setTrafficLimit(8*1024*1024);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getRequestId());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }
}
