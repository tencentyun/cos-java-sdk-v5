package com.qcloud.cos.demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.IOUtils;

public class GetObjectDemo {
    private static String secretId = "AKIDXXXXXXXX";
    private static String secretKey = "1A2Z3YYYYYYYYYY";
    private static String bucketName = "examplebucket-12500000000";
    private static String region = "ap-guangzhou";
    private static COSClient cosClient = createClient();

    public static void main(String[] args) {
        try {
            getObjectToFileDemo();
            //getObjectInputStreamDemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }

    private static COSClient createClient() {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        return cosclient;
    }

    private static void getObjectToFileDemo() {
        String key = "test/my_test.json";
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        // 设置下载的单链接限速（如有需要），不需要可忽略
        getObjectRequest.setTrafficLimit(8*1024*1024);

        try {
            File localFile = new File("my_test.json");
            ObjectMetadata objectMetadata = cosClient.getObject(getObjectRequest, localFile);
            System.out.println(objectMetadata.getContentLength());
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }

    private static void getObjectInputStreamDemo() throws IOException {
        String key = "test/my_test.json";
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        InputStream cosObjectInput = null;

        try {
            COSObject cosObject = cosClient.getObject(getObjectRequest);
            cosObjectInput = cosObject.getObjectContent();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }

        // 处理下载到的流
        // 这里是直接读取，按实际情况来处理
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(cosObjectInput);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 用完流之后一定要调用 close()
            cosObjectInput.close();
        }
    }
}

