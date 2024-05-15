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
    private static COSClient cosClient = createClient();
    public static void main(String[] args) {
        getObjectToFileDemo();
    }

    private static COSClient createClient() {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX","1A2Z3YYYYYYYYYY");
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        return cosclient;
    }

    private static void getObjectToFileDemo() {
        String key = "test/my_test.json";
        String bucketName = "mybucket-12500000000";
        boolean useTrafficLimit = false;
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        if(useTrafficLimit) {
            getObjectRequest.setTrafficLimit(8*1024*1024);
        }
        File localFile = new File("my_test.json");
        ObjectMetadata objectMetadata = cosClient.getObject(getObjectRequest, localFile);
        System.out.println(objectMetadata.getContentLength());
    }

    private static void getObjectDemo() throws IOException {
        String key = "test/my_test.json";
        String bucketName = "mybucket-12500000000";
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

