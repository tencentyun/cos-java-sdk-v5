package com.qcloud.cos.demo;

import java.util.LinkedList;
import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.DeleteBucketInventoryConfigurationRequest;
import com.qcloud.cos.model.GetBucketInventoryConfigurationResult;
import com.qcloud.cos.model.ListBucketInventoryConfigurationsRequest;
import com.qcloud.cos.model.ListBucketInventoryConfigurationsResult;
import com.qcloud.cos.model.SetBucketInventoryConfigurationRequest;
import com.qcloud.cos.model.inventory.InventoryConfiguration;
import com.qcloud.cos.model.inventory.InventoryCosBucketDestination;
import com.qcloud.cos.model.inventory.InventoryDestination;
import com.qcloud.cos.model.inventory.InventoryFilter;
import com.qcloud.cos.model.inventory.InventoryFormat;
import com.qcloud.cos.model.inventory.InventoryFrequency;
import com.qcloud.cos.model.inventory.InventoryIncludedObjectVersions;
import com.qcloud.cos.model.inventory.InventoryOptionalField;
import com.qcloud.cos.model.inventory.InventoryPrefixPredicate;
import com.qcloud.cos.model.inventory.InventorySchedule;
import com.qcloud.cos.model.inventory.ServerSideEncryptionCOS;

public class BucketInventoryDemo {
    public static void SetGetDeleteBucketInventoryDemo() {
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

        InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        InventoryCosBucketDestination inventoryCosBucketDestination = new InventoryCosBucketDestination();
        // 设置清单的输出目标存储桶的格式和前缀等
        inventoryCosBucketDestination.setAccountId("2779643970");
        inventoryCosBucketDestination.setBucketArn("qcs::cos:ap-guangzhou::mybucket-1251668577");
        inventoryCosBucketDestination.setEncryption(new ServerSideEncryptionCOS());
        inventoryCosBucketDestination.setFormat(InventoryFormat.CSV);
        inventoryCosBucketDestination.setPrefix("inventory-output");
        InventoryDestination inventoryDestination = new InventoryDestination();
        inventoryDestination.setCosBucketDestination(inventoryCosBucketDestination);
        inventoryConfiguration.setDestination(inventoryDestination);

        // 设置清单的调度周期，扫描前缀和id等
        inventoryConfiguration.setEnabled(true);
        inventoryConfiguration.setId("1");
        InventorySchedule inventorySchedule = new InventorySchedule();
        inventorySchedule.setFrequency(InventoryFrequency.Daily);
        inventoryConfiguration.setSchedule(inventorySchedule);
        InventoryPrefixPredicate inventoryFilter = new InventoryPrefixPredicate("test/");
        inventoryConfiguration.setInventoryFilter(new InventoryFilter(inventoryFilter));
        inventoryConfiguration.setIncludedObjectVersions(InventoryIncludedObjectVersions.All);
        // 设置可选的输出字段
        List<String> optionalFields = new LinkedList<String>();
        optionalFields.add(InventoryOptionalField.Size.toString());
        optionalFields.add(InventoryOptionalField.LastModifiedDate.toString());
        inventoryConfiguration.setOptionalFields(optionalFields);
        SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest = new SetBucketInventoryConfigurationRequest();
        setBucketInventoryConfigurationRequest.setBucketName(bucketName);
        setBucketInventoryConfigurationRequest.setInventoryConfiguration(inventoryConfiguration);
        cosclient.setBucketInventoryConfiguration(setBucketInventoryConfigurationRequest);

        inventoryConfiguration.setId("2");
        inventorySchedule.setFrequency(InventoryFrequency.Weekly);
        cosclient.setBucketInventoryConfiguration(setBucketInventoryConfigurationRequest);

        // 获取指定id的清单配置
        GetBucketInventoryConfigurationResult getBucketInventoryConfigurationResult = cosclient.getBucketInventoryConfiguration(bucketName, "1");

        // 批量获取清单
        ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest = new ListBucketInventoryConfigurationsRequest();
        listBucketInventoryConfigurationsRequest.setBucketName(bucketName);
        ListBucketInventoryConfigurationsResult listBucketInventoryConfigurationsResult = cosclient.listBucketInventoryConfigurations(listBucketInventoryConfigurationsRequest);

        // 删除指定清单
        DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest = new DeleteBucketInventoryConfigurationRequest();
        deleteBucketInventoryConfigurationRequest.setBucketName(bucketName);
        deleteBucketInventoryConfigurationRequest.setId("1");
        cosclient.deleteBucketInventoryConfiguration(deleteBucketInventoryConfigurationRequest);
    }
    public static void main(String[] args) {
        SetGetDeleteBucketInventoryDemo();
    }
}
