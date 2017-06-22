package com.qcloud.cos.demo;


import java.io.File;
import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.DeleteBucketRequest;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

public class Demo {
    public static void main(String[] args) throws Exception {
        // 用户基本信息
        String appid = "100000";
        String secret_id = "xxxxxxxxxxxxxxxx";
        String secret_key = "yyyyyyyyyyyy";

        // 设置秘钥
        COSCredentials cred = new BasicCOSCredentials(appid, secret_id, secret_key);
        // 设置区域, 这里设置为华北
        ClientConfig clientConfig = new ClientConfig(new Region("cn-north"));
        // 生成cos客户端对象
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 创建bucket
        // bucket数量上限200个, bucket是重操作, 一般不建议创建如此多的bucket
        // 重复创建同名bucket会报错
        String bucketName = "mybucket1511";
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicReadWrite);
        Bucket created_bucket = cosClient.createBucket(createBucketRequest);

        // 上传object, 建议20M以下的文件使用该接口
        File localFile = new File("src/test/resources/len5M.txt");
        String key = "/upload_single_demo.txt";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        // 下载object
        File downFile = new File("src/test/resources/len5M_down.txt");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);

        // 获取object属性
        GetObjectMetadataRequest getObjectMetadataRequest =
                new GetObjectMetadataRequest(bucketName, key);
        ObjectMetadata statObjectMeta = cosClient.getObjectMetadata(getObjectMetadataRequest);

        // 删除object
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, key);
        cosClient.deleteObject(deleteObjectRequest);

        // 上传文件(推荐), 支持根据文件的大小自动选择单文件上传或者分块上传
        // 同时支持同时上传不同的文件
        TransferManager transferManager = new TransferManager(cosClient);
        localFile = new File("src/test/resources/len30M.txt");
        // transfermanger upload是异步上传
        key = "/upload_transfer_manager_demo.txt";
        Upload upload = transferManager.upload(bucketName, key, localFile);
        // 等待传输结束
        upload.waitForCompletion();
        transferManager.shutdownNow();

        // list bucket下的成员
        ObjectListing objectListing = cosClient.listObjects(bucketName);
        List<COSObjectSummary> objectListSummary = objectListing.getObjectSummaries();

        // 删除刚上传的文件
        deleteObjectRequest = new DeleteObjectRequest(bucketName, key);
        cosClient.deleteObject(deleteObjectRequest);

        // 删除空bucket, bucket需要为空，不包含任何object
        DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest(bucketName);
        cosClient.deleteBucket(deleteBucketRequest);

        // 关闭客户端(关闭后台线程)
        cosClient.shutdown();
    }
}
