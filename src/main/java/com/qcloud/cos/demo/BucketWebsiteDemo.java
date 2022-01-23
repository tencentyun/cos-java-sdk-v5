package com.qcloud.cos.demo;

import java.util.ArrayList;
import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketWebsiteConfiguration;
import com.qcloud.cos.model.RedirectRule;
import com.qcloud.cos.model.RoutingRule;
import com.qcloud.cos.model.RoutingRuleCondition;

public class BucketWebsiteDemo {
    public static void SetGetDeleteWebisteDemo() {
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
        // 设置bucket website
        BucketWebsiteConfiguration bucketWebsiteConfiguration = new BucketWebsiteConfiguration();
        // 索引文件
        bucketWebsiteConfiguration.setIndexDocumentSuffix("index.html");
        // 路由规则
        List<RoutingRule> routingRuleList = new ArrayList<RoutingRule>();
        RoutingRule routingRule = new RoutingRule();
        RoutingRuleCondition routingRuleCondition = new RoutingRuleCondition();
        routingRuleCondition.setHttpErrorCodeReturnedEquals("404");
        routingRule.setCondition(routingRuleCondition);
        RedirectRule redirectRule = new RedirectRule();
        redirectRule.setProtocol("https");
        redirectRule.setReplaceKeyPrefixWith("404.html");
        routingRule.setRedirect(redirectRule);
        routingRuleList.add(routingRule);
        bucketWebsiteConfiguration.setRoutingRules(routingRuleList);
        cosclient.setBucketWebsiteConfiguration(bucketName, bucketWebsiteConfiguration);

        // 获取bucket website
        BucketWebsiteConfiguration bucketWebsiteConfiguration1 = cosclient.getBucketWebsiteConfiguration(bucketName);

        // 删除bucket website
        cosclient.deleteBucketWebsiteConfiguration(bucketName);
    }

    public static void main(String[] args) {
        SetGetDeleteWebisteDemo();
    }
}
