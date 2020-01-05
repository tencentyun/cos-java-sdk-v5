package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketDomainConfiguration;
import com.qcloud.cos.model.DomainRule;
import com.qcloud.cos.region.Region;

public class BucketDomainDemo {
    public static void SetGetBucketDomainDemo() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";
        BucketDomainConfiguration bucketDomainConfiguration = new BucketDomainConfiguration();
        DomainRule domainRule = new DomainRule();
        domainRule.setStatus(DomainRule.ENABLED);
        domainRule.setType(DomainRule.REST);
        domainRule.setName("qq.com");
        domainRule.setForcedReplacement(DomainRule.CNAME);
        bucketDomainConfiguration.getDomainRules().add(domainRule);
        cosclient.setBucketDomainConfiguration(bucketName, bucketDomainConfiguration);
        BucketDomainConfiguration bucketDomainConfiguration1 = cosclient.getBucketDomainConfiguration(bucketName);
        System.out.println(bucketDomainConfiguration1.getDomainTxtVerification());
    }

    public static void main(String[] args) {
        SetGetBucketDomainDemo();
    }
}
