package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.region.Region;

/**
 * 展示了创建bucket, 删除bucket, 查询bucket是否存在的demo
 *
 */
public class BucketDemo {
    // 创建bucket
    public static void CreateBucketDemo() {
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名称, 需包含appid
        String bucketName = "publicreadbucket-1251668577";
        
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 设置bucket的权限为PublicRead(公有读私有写), 其他可选有私有读写, 公有读私有写
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        Bucket bucket = cosclient.createBucket(createBucketRequest);
        
        // 关闭客户端
        cosclient.shutdown();
    }
    
    // 删除bucket, 只用于空bucket, 含有数据的bucket需要在删除前清空删除。
    public static void DeleteBucketDemo() {
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名称, 需包含appid        
        String bucketName = "publicreadbucket-1251668577";
        // 删除bucket
        cosclient.deleteBucket(bucketName);
        
        // 关闭客户端
        cosclient.shutdown();
    }
    
    // 查询bucket是否存在
    public static void JudgeBucketExistDemo() {
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        
        String bucketName = "publicreadbucket-1251668577";
        // 判断bucket是否存在
        cosclient.doesBucketExist(bucketName);
        
        // 关闭客户端
        cosclient.shutdown();
    }    
}
