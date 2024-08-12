package com.qcloud.cos.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.CopyResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Copy;
import com.qcloud.cos.transfer.TransferManager;

public class CopyFileDemo {
    private static String secretId = System.getenv("SECRETID");
    private static String secretKey = System.getenv("SECRETKEY");
    private static String srcBucketName = "srcBucket-12500000000";
    private static String srcKey = "aaa/bbb.txt";
    private static String destBucketName = "destBucket-12500000000";
    private static String destKey = "ccc/ddd.txt";
    private static String srcRegion = "ap-guangzhou";
    private static String dstRegion = "ap-guangzhou";
    private static COSClient srcCosClient = createCli(srcRegion);
    private static COSClient dstCosClient = createCli(dstRegion);
    public static void main(String[] args) {
        try {
            copySmallFileDemo();
            //copyBigFileDemo();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }
    }

    private static COSClient createCli(String region) {
        // 1 初始化用户身份信息(appid, secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        return cosclient;
    }

    private static void shutdown() {
        if (srcCosClient != null) {
            srcCosClient.shutdown();
            srcCosClient = null;
        }

        if (dstCosClient != null) {
            dstCosClient.shutdown();
            dstCosClient = null;
        }
    }

    // copyObject最大支持5G文件的copy, 5G以上的文件copy请参照TransferManagerDemo中的copy示例
    private static void copySmallFileDemo() {
        // 要拷贝的bucket region, 支持跨园区拷贝
        Region srcBucketRegion = new Region(srcRegion);
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketRegion, srcBucketName,
                srcKey, destBucketName, destKey);
        try {
            CopyObjectResult copyObjectResult = dstCosClient.copyObject(copyObjectRequest);
            String crc64 = copyObjectResult.getCrc64Ecma();
            System.out.println("succeed to copy object");
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    // 对于5G以上的文件，需要通过分块上传中的copypart来实现，步骤较多,实现较复杂。
    // 因此在TransferManager中封装了一个copy接口，能根据文件大小自动的选择接口，既支持5G以下的文件copy, 也支持5G以上的文件copy。推荐使用该接口进行文件的copy。
    private static void copyBigFileDemo() {
        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(dstCosClient, threadPool);

        // 要拷贝的bucket region, 支持跨园区拷贝
        Region srcBucketRegion = new Region(srcRegion);
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketRegion, srcBucketName,
                srcKey, destBucketName, destKey);
        try {
            Copy copy = transferManager.copy(copyObjectRequest, srcCosClient, null);
            // 返回一个异步结果copy, 可同步的调用waitForCopyResult等待copy结束, 成功返回CopyResult, 失败抛出异常.
            CopyResult copyResult = copy.waitForCopyResult();
            System.out.println("succeed to copy object");
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
