package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetBucketInventoryConfigurationResult;
import com.qcloud.cos.model.ListBucketInventoryConfigurationsRequest;
import com.qcloud.cos.model.ListBucketInventoryConfigurationsResult;
import com.qcloud.cos.model.SetBucketInventoryConfigurationRequest;
import com.qcloud.cos.model.DeleteBucketInventoryConfigurationRequest;
import com.qcloud.cos.model.PostBucketInventoryConfigurationResult;
import com.qcloud.cos.model.inventory.InventoryConfiguration;
import com.qcloud.cos.model.inventory.InventoryCosBucketDestination;
import com.qcloud.cos.model.inventory.InventoryFrequency;
import com.qcloud.cos.model.inventory.ServerSideEncryptionCOS;
import com.qcloud.cos.model.inventory.InventoryFormat;
import com.qcloud.cos.model.inventory.InventoryDestination;
import com.qcloud.cos.model.inventory.InventorySchedule;
import com.qcloud.cos.model.inventory.InventoryPrefixPredicate;
import com.qcloud.cos.model.inventory.InventoryFilter;
import com.qcloud.cos.model.inventory.InventoryIncludedObjectVersions;
import com.qcloud.cos.model.inventory.InventoryOptionalField;
import com.qcloud.cos.region.Region;

import java.util.LinkedList;
import java.util.List;

public class BucketInventoryDemo {
    private static String secretId = System.getenv("SECRETID");
    private static String secretKey = System.getenv("SECRETKEY");
    private static String bucketName = System.getenv("BUCKET_NAME");
    private static String region = System.getenv("REGION");
    private static COSClient cosClient = createCli();

    public static void main(String[] args) {
        try {
            setGetDeleteBucketInventoryDemo();
            setBucketInventoryDemo();
//            postBucketInventoryDemo();
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

    private static void setGetDeleteBucketInventoryDemo() {
        InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        InventoryCosBucketDestination inventoryCosBucketDestination = new InventoryCosBucketDestination();
        // 设置清单的输出目标存储桶的格式和前缀等
        inventoryCosBucketDestination.setAccountId("100000000001");
        inventoryCosBucketDestination.setBucketArn("qcs::cos:ap-guangzhou::mybucket-12500000000");
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
        cosClient.setBucketInventoryConfiguration(setBucketInventoryConfigurationRequest);

        inventoryConfiguration.setId("2");
        inventorySchedule.setFrequency(InventoryFrequency.Weekly);
        cosClient.setBucketInventoryConfiguration(setBucketInventoryConfigurationRequest);

        // 获取指定id的清单配置
        GetBucketInventoryConfigurationResult getBucketInventoryConfigurationResult = cosClient.getBucketInventoryConfiguration(bucketName, "1");

        // 批量获取清单
        ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest = new ListBucketInventoryConfigurationsRequest();
        listBucketInventoryConfigurationsRequest.setBucketName(bucketName);
        ListBucketInventoryConfigurationsResult listBucketInventoryConfigurationsResult = cosClient.listBucketInventoryConfigurations(listBucketInventoryConfigurationsRequest);

        // 删除指定清单
        DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest = new DeleteBucketInventoryConfigurationRequest();
        deleteBucketInventoryConfigurationRequest.setBucketName(bucketName);
        deleteBucketInventoryConfigurationRequest.setId("1");
        cosClient.deleteBucketInventoryConfiguration(deleteBucketInventoryConfigurationRequest);
    }

    private static void setBucketInventoryDemo() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-12500000000";

        SetBucketInventoryConfigurationRequest request = new SetBucketInventoryConfigurationRequest();
        request.setBucketName(bucketName);
        // 打开开关，选择直接传入清单文本内容的模式发起请求
        request.useInventoryText();

        InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        // 注意这里的 Id 要与下方 inventoryText 里的 Id 保持一致
        inventoryConfiguration.setId("12");
        request.setInventoryConfiguration(inventoryConfiguration);

        String inventoryText = "<InventoryConfiguration>\n" +
                                    "<Id>12</Id>\n" +
                                    "<IsEnabled>true</IsEnabled>\n" +
                                    "<IncludedObjectVersions>All</IncludedObjectVersions>\n" +
                                    "<Destination>\n" +
                                        "<COSBucketDestination>\n" +
                                            "<AccountId>100000000001</AccountId>\n" +
                                            "<Bucket>qcs::cos:ap-guangzhou::mybucket-12500000000</Bucket>\n" +
                                            "<Prefix>inventory-output</Prefix>\n" +
                                            "<Format>CSV</Format>\n" +
                                            "<Encryption><SSECOS></SSECOS></Encryption>\n" +
                                        "</COSBucketDestination>\n" +
                                    "</Destination>\n" +
                                    "<Schedule>\n" +
                                        "<Frequency>Daily</Frequency>\n" +
                                    "</Schedule>\n" +
                                    "<Filter>\n" +
                                        "<Prefix>test/</Prefix>\n" +
                                        "<Period>\n" +
                                            "<StartTime>1681661544</StartTime>\n" +
                                            "<EndTime>1681733589</EndTime>\n" +
                                        "</Period>\n" +
                                    "</Filter>\n" +
                                    "<OptionalFields>\n" +
                                        "<Field>Size</Field>\n" +
                                        "<Field>LastModifiedDate</Field>\n" +
                                    "</OptionalFields>\n" +
                                "</InventoryConfiguration>\n";
        request.setInventoryText(inventoryText);

        cosclient.setBucketInventoryConfiguration(request);
        cosclient.shutdown();
    }

    private static void postBucketInventoryDemo() {
        InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        InventoryCosBucketDestination inventoryCosBucketDestination = new InventoryCosBucketDestination();
        // 设置清单的输出目标存储桶的格式和前缀等
        inventoryCosBucketDestination.setAccountId("100000000001");
        inventoryCosBucketDestination.setBucketArn("qcs::cos:ap-guangzhou::mybucket-12500000000");
        inventoryCosBucketDestination.setEncryption(new ServerSideEncryptionCOS());
        inventoryCosBucketDestination.setFormat(InventoryFormat.CSV);
        inventoryCosBucketDestination.setPrefix("inventory-output");
        InventoryDestination inventoryDestination = new InventoryDestination();
        inventoryDestination.setCosBucketDestination(inventoryCosBucketDestination);
        inventoryConfiguration.setDestination(inventoryDestination);

        // 设置清单的调度周期，扫描前缀和id等
        inventoryConfiguration.setId("4");
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
        PostBucketInventoryConfigurationResult result = cosClient.postBucketInventoryConfiguration(setBucketInventoryConfigurationRequest);
        System.out.println("finish post bucket inventory, jobId: " + result.getJobId() + ", reqId: " + result.getRequestId());
    }
}
