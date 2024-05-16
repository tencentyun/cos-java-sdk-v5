package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.SSEAlgorithm;
import com.qcloud.cos.region.Region;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SSECOSDemo {
    private static COSClient cosClient = createCOSClient();

    private static String bucketName = "examplebucket-1250000000";

    private static String key = "aaa/bbb.txt";

    public static void main(String[] args) {
        try {
            SSECOSUpload();
            SSECOSDownload();
            SSECOSHead();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }

    private static COSClient createCOSClient() {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("COS_SECRETID", "COS_SECRETKEY");
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        return cosclient;
    }

    private static void SSECOSUpload() {
        File localFile = new File("test.txt");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 设置加密算法为AES256
        objectMetadata.setServerSideEncryption(SSEAlgorithm.AES256.getAlgorithm());
        putObjectRequest.setMetadata(objectMetadata);

        try {
            PutObjectResult result = cosClient.putObject(putObjectRequest);
            System.out.println("finish upload, reqid:" + result.getRequestId());
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }

    private static void SSECOSDownload() throws IOException {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        COSObject cosObject = null;
        try {
            cosObject = cosClient.getObject(getObjectRequest);
            COSObjectInputStream cosObjectInputStream = cosObject.getObjectContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cosObjectInputStream));
            System.out.println(bufferedReader.readLine());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (cosObject != null) {
                cosObject.close();
            }
        }
    }

    private static void SSECOSHead() {
        try {
            GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest(bucketName, key);
            ObjectMetadata objectMetadata = cosClient.getObjectMetadata(getObjectMetadataRequest);
            System.out.println(objectMetadata);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }
}
