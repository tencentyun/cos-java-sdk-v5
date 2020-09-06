package com.qcloud.cos.demo.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.*;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;

import java.io.File;

public class CAMRoleDemo {

    public static void SimpleUploadFileFromLocal() {
        InstanceMetadataCredentialsEndpointProvider endpointProvider =
                new InstanceMetadataCredentialsEndpointProvider(InstanceMetadataCredentialsEndpointProvider.Instance.CVM);

        InstanceCredentialsFetcher instanceCredentialsFetcher = new InstanceCredentialsFetcher(endpointProvider);
        COSCredentialsProvider cosCredentialsProvider = new InstanceCredentialsProvider(instanceCredentialsFetcher);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        COSClient cosClient = new COSClient(cosCredentialsProvider,clientConfig);
        String bucketName = "3399demo-125xxxxxxxx";
        String key = "test/demo.txt";
        File localFile = new File("test");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        putObjectRequest.setStorageClass(StorageClass.Standard);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
    }

    public static void main(String[] args) {
        SimpleUploadFileFromLocal();
    }
}
