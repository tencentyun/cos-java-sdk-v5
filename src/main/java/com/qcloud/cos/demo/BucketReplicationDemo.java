package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketReplicationConfiguration;
import com.qcloud.cos.model.ReplicationDestinationConfig;
import com.qcloud.cos.model.ReplicationRule;
import com.qcloud.cos.model.ReplicationRuleStatus;

public class BucketReplicationDemo {
    static COSClient cosClient = null;
    static String bucketName = "examplebucket-1251668577";

    public static void createCOSClient() {
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
        cosClient = new COSClient(cred, clientConfig);
    }

    public static void putBucketReplication() {
        BucketReplicationConfiguration bucketReplicationConfiguration = new BucketReplicationConfiguration();
        bucketReplicationConfiguration.setRoleName("qcs::cam::uin/1000000001:uin/1000000001");

        ReplicationRule replicationRule = new ReplicationRule();
        replicationRule.setID("test");
        replicationRule.setStatus(ReplicationRuleStatus.Disabled);
        replicationRule.setPrefix("testReplication");
        ReplicationDestinationConfig replicationDestinationConfig = new ReplicationDestinationConfig();
        replicationDestinationConfig.setBucketQCS("qcs::cos:ap-shanghai::examplebucket-cp-1251668577");
        replicationRule.setDestinationConfig(replicationDestinationConfig);

        bucketReplicationConfiguration.addRule(replicationRule);

        ReplicationRule replicationRule2 = new ReplicationRule();
        replicationRule2.setID("test2");
        replicationRule2.setStatus(ReplicationRuleStatus.Disabled);
        replicationRule2.setPrefix("test2Replication");
        ReplicationDestinationConfig replicationDestinationConfig2 = new ReplicationDestinationConfig();
        replicationDestinationConfig2.setBucketQCS("qcs::cos:ap-shanghai::examplebucket-cp-1251668577");
        replicationRule2.setDestinationConfig(replicationDestinationConfig2);

        bucketReplicationConfiguration.addRule(replicationRule2);

        ReplicationRule replicationRule3 = new ReplicationRule();
        replicationRule3.setID("test3");
        replicationRule3.setStatus(ReplicationRuleStatus.Disabled);
        replicationRule3.setPrefix("test3Replication");
        ReplicationDestinationConfig replicationDestinationConfig3 = new ReplicationDestinationConfig();
        replicationDestinationConfig3.setBucketQCS("qcs::cos:ap-shanghai::examplebucket-cp-1251668577");
        replicationRule2.setDestinationConfig(replicationDestinationConfig3);

        bucketReplicationConfiguration.addRule(replicationRule3);

        bucketReplicationConfiguration.removeRule(replicationRule3);

        cosClient.setBucketReplicationConfiguration(bucketName, bucketReplicationConfiguration);
    }

    public static void getBucketReplication() {
        BucketReplicationConfiguration configuration = cosClient.getBucketReplicationConfiguration(bucketName);
        System.out.println(configuration.toString());
    }

    public static void main(String[] argv) {
        createCOSClient();
        putBucketReplication();
        getBucketReplication();
    }
}
