package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketCrossOriginConfiguration;
import com.qcloud.cos.model.CORSRule;
import com.qcloud.cos.region.Region;

import java.util.ArrayList;
import java.util.List;

public class BucketCorsDemo {
    private static String bucketName = "examplebucket-1250000000";

    private static COSClient cosClient = createClient();
    public static void main(String[] args) {
        try {
            setBucketCorsDemo();
            getBucketCorsDemo();
            deleteBucketCorsDemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }

    private static COSClient createClient() {
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        return cosclient;
    }

    private static void setBucketCorsDemo() {
        BucketCrossOriginConfiguration bucketCORS = new BucketCrossOriginConfiguration();
        List<CORSRule> corsRules = new ArrayList<CORSRule>();
        CORSRule corsRule = new CORSRule();
        // 规则名称
        corsRule.setId("set-bucket-cors-test");
        // 允许的 HTTP 方法
        corsRule.setAllowedMethods(CORSRule.AllowedMethods.PUT, CORSRule.AllowedMethods.GET, CORSRule.AllowedMethods.HEAD);
        corsRule.setAllowedHeaders("x-cos-grant-full-control");
        corsRule.setAllowedOrigins("http://mail.qq.com", "http://www.qq.com", "http://video.qq.com");
        corsRule.setExposedHeaders("x-cos-request-id");
        corsRule.setMaxAgeSeconds(60);
        corsRules.add(corsRule);
        bucketCORS.setRules(corsRules);
        cosClient.setBucketCrossOriginConfiguration(bucketName, bucketCORS);
        System.out.println("finish set bucket cors");
    }

    private static void getBucketCorsDemo() {
        BucketCrossOriginConfiguration bucketCrossOriginConfiguration = cosClient.getBucketCrossOriginConfiguration(bucketName);
        List<CORSRule> corsRules = bucketCrossOriginConfiguration.getRules();
        for (CORSRule rule : corsRules) {
            List<CORSRule.AllowedMethods> allowedMethods = rule.getAllowedMethods();
            List<String> allowedHeaders = rule.getAllowedHeaders();
            List<String> allowedOrigins = rule.getAllowedOrigins();
            List<String> exposedHeaders = rule.getExposedHeaders();
            int maxAgeSeconds = rule.getMaxAgeSeconds();
            System.out.println("allow methods:" + allowedMethods);
            System.out.println("allow headers:" + allowedHeaders);
            System.out.println("allow origins:" + allowedOrigins);
            System.out.println("exposed headers:" + exposedHeaders);
            System.out.println("max age seconds:" + maxAgeSeconds);
        }
    }

    private static void deleteBucketCorsDemo() {
        cosClient.deleteBucketCrossOriginConfiguration(bucketName);
        System.out.println("finish delete bucket cors");
    }
}
