package com.qcloud.cos.demo;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.BucketIntelligentTierConfiguration;
import com.qcloud.cos.model.IntelligentTiering.BucketIntelligentTieringConfiguration;
import com.qcloud.cos.model.SetBucketIntelligentTierConfigurationRequest;
import com.qcloud.cos.region.Region;

import java.util.List;

public class BucketIntelligentTierDemo {
    private static String secretId = System.getenv("SECRETID");
    private static String secretKey = System.getenv("SECRETKEY");
    private static String bucketName = System.getenv("BUCKET_NAME");
    private static String region = System.getenv("REGION");

    private static COSClient cosClient = createCli();

    public static void main(String[] args) {
        try {
            setBucketIntelligentTieringConfiguration();
            getBucketIntelligentTieringConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }

    private static COSClient createCli() {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 生成cos客户端
        return new COSClient(cred, clientConfig);
    }

    private static void setBucketIntelligentTieringConfiguration() {
        BucketIntelligentTierConfiguration bucketIntelligentTierConfiguration = new BucketIntelligentTierConfiguration();
        bucketIntelligentTierConfiguration.setStatus(BucketIntelligentTierConfiguration.ENABLED);
        bucketIntelligentTierConfiguration.setTransition(new BucketIntelligentTierConfiguration.Transition(30));
        SetBucketIntelligentTierConfigurationRequest setBucketIntelligentTierConfigurationRequest = new SetBucketIntelligentTierConfigurationRequest();
        setBucketIntelligentTierConfigurationRequest.setBucketName(bucketName);
        setBucketIntelligentTierConfigurationRequest.setIntelligentTierConfiguration(bucketIntelligentTierConfiguration);
        cosClient.setBucketIntelligentTieringConfiguration(setBucketIntelligentTierConfigurationRequest);
        System.out.println("finish setting bucket intelligent tiering configuration");
    }

    private static void getBucketIntelligentTieringConfiguration() {
        BucketIntelligentTierConfiguration bucketIntelligentTierConfiguration1 = cosClient.getBucketIntelligentTierConfiguration(bucketName);
        System.out.println("finish getting bucket intelligent tiering configuration");
        System.out.println(bucketIntelligentTierConfiguration1.getStatus());
        System.out.println(bucketIntelligentTierConfiguration1.getTransition().getDays());
    }

    private static void listBucketIntelligentTieringConfiguration() {
        try {
            List<BucketIntelligentTieringConfiguration> configurations = cosClient.listBucketIntelligentTieringConfiguration(bucketName);
            if (configurations == null) {
                System.out.println("The Intelligenttiering configuration was not found about the specified bucket.");
            } else {
                System.out.println("finish list bucket intelligent tiering configuration");
            }
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }
}
