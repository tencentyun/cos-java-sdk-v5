package com.qcloud.cos.demo;

import java.io.File;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSCredentialsProvider;
import com.qcloud.cos.auth.InstanceCredentialsFetcher;
import com.qcloud.cos.auth.InstanceCredentialsProvider;
import com.qcloud.cos.auth.InstanceMetadataCredentialsEndpointProvider;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;

public class CAMRoleDemo {

    public static void SimpleUploadFileFromLocal() {
        InstanceMetadataCredentialsEndpointProvider endpointProvider =
                new InstanceMetadataCredentialsEndpointProvider(InstanceMetadataCredentialsEndpointProvider.Instance.CVM);

        InstanceCredentialsFetcher instanceCredentialsFetcher = new InstanceCredentialsFetcher(endpointProvider);
        COSCredentialsProvider cosCredentialsProvider = new InstanceCredentialsProvider(instanceCredentialsFetcher);

        ClientConfig clientConfig = new ClientConfig();
        // 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        COSClient cosClient = new COSClient(cosCredentialsProvider,clientConfig);

        String bucketName = "3399demo-125xxxxxxxx";
        String key = "test/demo.txt";
        File localFile = new File("test");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        putObjectRequest.setStorageClass(StorageClass.Standard);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
    }

    public static void SimpleUploadFileFromEMR() {
        InstanceMetadataCredentialsEndpointProvider endpointProvider =
                new InstanceMetadataCredentialsEndpointProvider(InstanceMetadataCredentialsEndpointProvider.Instance.EMR);

        InstanceCredentialsFetcher instanceCredentialsFetcher = new InstanceCredentialsFetcher(endpointProvider);

        COSCredentialsProvider cosCredentialsProvider = new InstanceCredentialsProvider(instanceCredentialsFetcher);

        COSCredentials cred = cosCredentialsProvider.getCredentials();
        System.out.println(cred.getCOSAccessKeyId());
        System.out.println(cred.getCOSSecretKey());
        System.out.println(cred.getCOSAppId());

        ClientConfig clientConfig = new ClientConfig();
        // 设置 bucket 的域名, bucket 对应的 COS 地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        String region = "ap-guangzhou";
        // 如果是公网环境
        clientConfig.setEndpoint(String.format("cos.%s.tencentcos.cn", region));
        // 如果是腾讯云内网环境
        clientConfig.setEndpoint(String.format("cos-internal.%s.tencentcos.cn", region));

        COSClient cosClient = new COSClient(cosCredentialsProvider,clientConfig);
        String bucketName = "aaa-125xxx";
        String key = "test_emr.txt";
        File localFile = new File("./test");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        putObjectRequest.setStorageClass(StorageClass.Standard);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        System.out.println("upload file etag: " + putObjectResult.getETag());
        System.out.println("upload file requestId: " + putObjectResult.getRequestId());

        ObjectMetadata getMeta = cosClient.getObjectMetadata(bucketName, key);
        System.out.println("get file etag: " + getMeta.getETag());

        cosClient.deleteObject(bucketName, key);
        if (cosClient.doesObjectExist(bucketName, key)) {
            System.out.println("delete failed");
        } else {
            System.out.println("delete successfully");
        }

        cosClient.shutdown();
    }

    public static void main(String[] args) {
        SimpleUploadFileFromEMR();
    }
}
