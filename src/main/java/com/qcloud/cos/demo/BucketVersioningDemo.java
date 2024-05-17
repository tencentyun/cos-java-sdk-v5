package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketVersioningConfiguration;
import com.qcloud.cos.model.SetBucketVersioningConfigurationRequest;
import com.qcloud.cos.region.Region;

public class BucketVersioningDemo {
    public static void main(String[] args) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "examplebucket-12500000000";

        // open bucket versioning
        BucketVersioningConfiguration bucketVersioningConfiguration = new BucketVersioningConfiguration(BucketVersioningConfiguration.ENABLED);
        SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(bucketName, bucketVersioningConfiguration);
        cosClient.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
        System.out.println("finish open bucket versioning.");

        // get bucket versioning
        bucketVersioningConfiguration = cosClient.getBucketVersioningConfiguration(bucketName);
        System.out.println(bucketVersioningConfiguration.getStatus());

        // suspend bucket versioning
        bucketVersioningConfiguration = new BucketVersioningConfiguration(BucketVersioningConfiguration.SUSPENDED);
        setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(bucketName, bucketVersioningConfiguration);
        cosClient.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);
        System.out.println("finish suspend bucket versioning.");

        cosClient.shutdown();
    }
}
