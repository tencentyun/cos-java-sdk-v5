package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.BucketEncryptionConfiguration;
import com.qcloud.cos.region.Region;

public class BucketEncryptionDemo {
    private static String secretId = System.getenv("SECRETID");
    private static String secretKey = System.getenv("SECRETKEY");
    private static String bucketName = System.getenv("BUCKET_NAME");
    private static String region = System.getenv("REGION");
    private static COSClient cosClient = createCli();

    public static void main(String[] args) {
        try {
            putBucketEncryption();
            getBucketEncryption();
            deleteBucketEncrytion();
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

    private static void putBucketEncryption() {
        BucketEncryptionConfiguration configuration = new BucketEncryptionConfiguration();
        configuration.setSseAlgorithm("AES256");
        try {
            cosClient.putBucketEncryptionConfiguration(bucketName, configuration);
            System.out.println("finish put bucket Encryption");
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }

    private static void getBucketEncryption() {
        try {
            BucketEncryptionConfiguration configuration = cosClient.getBucketEncryptionConfiguration(bucketName);
            System.out.println(configuration.getSseAlgorithm());
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }

    private static void deleteBucketEncrytion() {
        try {
            cosClient.deleteBucketEncryptionConfiguration(bucketName);
            System.out.println("finish delete bucket encrytion");
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }
}
