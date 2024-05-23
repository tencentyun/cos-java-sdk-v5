package com.qcloud.cos.demo;

import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.region.Region;

/**
 * 展示了创建bucket, 删除bucket, 查询bucket是否存在的demo
 *
 */
public class BucketDemo {
    private static String secretId = "AKIDXXXXXXXX";
    private static String secretKey = "1A2Z3YYYYYYYYYY";
    private static String cosRegion = "ap-guangzhou";
    private static String bucketName = "examplebucket-12500000000";
    private static COSClient cosClient = createCli();

    public static void main(String[] args) {
        try {
            createBucketDemo();
            judgeBucketExistDemo();
            listBuckets();
            deleteBucketDemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cosClient.shutdown();
        }
    }

    private static COSClient createCli() {
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(cosRegion));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        return cosclient;
    }

    // 创建bucket
    private static void createBucketDemo() {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 设置bucket的权限为PublicRead(公有读私有写), 其他可选有私有读写, 公有读私有写
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        Bucket bucket = cosClient.createBucket(createBucketRequest);
        System.out.println("create bucket, bucketName is " + bucket.getName());
    }
    
    // 删除bucket, 只用于空bucket, 含有数据的bucket需要在删除前清空删除。
    private static void deleteBucketDemo() {
        // 删除bucket
        cosClient.deleteBucket(bucketName);
        System.out.println("delete bucket, bucketName is " + bucketName);
    }
    
    // 查询bucket是否存在
    private static void judgeBucketExistDemo() {
        // 判断bucket是否存在
        boolean isExist = cosClient.doesBucketExist(bucketName);
        if (isExist) {
            System.out.println(bucketName + " is exist");
        } else {
            System.out.println(bucketName + " is not exist");
        }
    }

    private static void listBuckets() {
        List<Bucket> buckets = cosClient.listBuckets();

        for (Bucket bucket : buckets) {
            System.out.println(bucket.getName());
            System.out.println(bucket.getLocation());
            System.out.println(bucket.getOwner());
            System.out.println(bucket.getType());
            System.out.println(bucket.getBucketType());
        }
    }

    //创多AZ桶
    private static void createMAZBucketDemo() {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);

        try {
            Bucket bucket = cosClient.createMAZBucket(createBucketRequest);
            System.out.println("create MAZ bucket, bucketName is " + bucket.getName());
        } catch (CosServiceException cse) {
            cse.printStackTrace();
        } catch (CosClientException cce) {
            cce.printStackTrace();
        }
    }
}
