package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketDomainConfiguration;
import com.qcloud.cos.model.DomainRule;

public class BucketDomainDemo {
    public static void SetGetDeleteBucketDomainDemo() {
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
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";
        BucketDomainConfiguration bucketDomainConfiguration = new BucketDomainConfiguration();
        DomainRule domainRule = new DomainRule();
        domainRule.setStatus(DomainRule.ENABLED);
        domainRule.setType(DomainRule.REST);
        domainRule.setName("test.com");
        //domainRule.setForcedReplacement(DomainRule.TXT);
        bucketDomainConfiguration.getDomainRules().add(domainRule);
        cosclient.setBucketDomainConfiguration(bucketName, bucketDomainConfiguration);

        BucketDomainConfiguration bucketDomainConfiguration1 = cosclient.getBucketDomainConfiguration(bucketName);
        System.out.println(bucketDomainConfiguration1.getDomainTxtVerification());
        for (DomainRule rule : bucketDomainConfiguration1.getDomainRules()) {
            System.out.println(rule.getName());
            System.out.println(rule.getStatus());
            System.out.println(rule.getType());
            System.out.println(rule.getClass());
        }

        cosclient.deleteBucketDomainConfiguration(bucketName);
        BucketDomainConfiguration bucketDomainConfiguration2 = cosclient.getBucketDomainConfiguration(bucketName);
        assert(bucketDomainConfiguration2 == null);
    }

    public static void main(String[] args) {
        SetGetDeleteBucketDomainDemo();
    }
}
