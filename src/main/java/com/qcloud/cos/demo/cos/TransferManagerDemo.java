package com.qcloud.cos.demo.cos;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyResult;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;

import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Copy;
import com.qcloud.cos.transfer.Download;
import com.qcloud.cos.transfer.PersistableDownload;
import com.qcloud.cos.transfer.PersistableUpload;
import com.qcloud.cos.transfer.Transfer;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferProgress;
import com.qcloud.cos.transfer.Upload;
import com.qcloud.cos.transfer.TransferManagerConfiguration;

// TransferManager提供异步的上传文件, 下载文件，copy文件的高级API接口
// 可以根据文件大小自动的选择上传接口或者copy接口,方便用户使用, 无需自行封装较复杂的分块上传或者分块copy
public class TransferManagerDemo {
    // Prints progress while waiting for the transfer to finish.
    private static void showTransferProgress(Transfer transfer) {
        System.out.println(transfer.getDescription());
        do {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return;
            }
            TransferProgress progress = transfer.getProgress();
            long so_far = progress.getBytesTransferred();
            long total = progress.getTotalBytesToTransfer();
            double pct = progress.getPercentTransferred();
            System.out.printf("[%d / %d]\n", so_far, total);
        } while (transfer.isDone() == false);
        System.out.println(transfer.getState());
    }

    // 上传文件, 根据文件大小自动选择简单上传或者分块上传。
    public static void uploadFile() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";

        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);

        String key = "aaa/bbb.txt";
        File localFile = new File("src/test/resources/len30M.txt");

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        try {
            // 返回一个异步结果Upload, 可同步的调用waitForUploadResult等待upload结束, 成功返回UploadResult, 失败抛出异常.
            long startTime = System.currentTimeMillis();
            Upload upload = transferManager.upload(putObjectRequest);
            showTransferProgress(upload);
            UploadResult uploadResult = upload.waitForUploadResult();
            long endTime = System.currentTimeMillis();
            System.out.println("used time: " + (endTime - startTime) / 1000);
            System.out.println(uploadResult.getETag());
            System.out.println(uploadResult.getCrc64Ecma());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transferManager.shutdownNow();
        cosclient.shutdown();
    }

    // 上传文件（上传过程中暂停, 并继续上传)
    public static void pauseUploadFileAndResume() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);

        String key = "aaa/bbb.txt";
        File localFile = new File("src/test/resources/len30M.txt");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        try {
            // 返回一个异步结果Upload, 可同步的调用waitForUploadResult等待upload结束, 成功返回UploadResult, 失败抛出异常.
            Upload upload = transferManager.upload(putObjectRequest);
            Thread.sleep(10000);
            PersistableUpload persistableUpload = upload.pause();
            upload = transferManager.resumeUpload(persistableUpload);
            showTransferProgress(upload);
            UploadResult uploadResult = upload.waitForUploadResult();
            System.out.println(uploadResult.getETag());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transferManager.shutdownNow();
        cosclient.shutdown();
    }

    // 将文件下载到本地
    public static void downLoadFile() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";

        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);

        String key = "aaa/bbb.txt";
        File downloadFile = new File("src/test/resources/download.txt");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        try {
            // 返回一个异步结果copy, 可同步的调用waitForCompletion等待download结束, 成功返回void, 失败抛出异常.
            Download download = transferManager.download(getObjectRequest, downloadFile);
            download.waitForCompletion();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transferManager.shutdownNow();
        cosclient.shutdown();
    }

    // 将文件下载到本地(中途暂停并继续开始)
    public static void pauseDownloadFileAndResume() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";

        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);

        String key = "aaa/bbb.txt";
        File downloadFile = new File("src/test/resources/download.txt");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        try {
            // 返回一个异步结果copy, 可同步的调用waitForCompletion等待download结束, 成功返回void, 失败抛出异常.
            Download download = transferManager.download(getObjectRequest, downloadFile);
            Thread.sleep(5000L);
            PersistableDownload persistableDownload = download.pause();
            download = transferManager.resumeDownload(persistableDownload);
            showTransferProgress(download);
            download.waitForCompletion();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        transferManager.shutdownNow();
        cosclient.shutdown();
    }


    // copy接口支持根据文件大小自动选择copy或者分块copy
    // 以下代码展示跨园区拷贝, 即将一个园区的文件拷贝到另一个园区
    public static void copyFileForDiffRegion() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);


        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);

        // 要拷贝的bucket region, 支持跨园区拷贝
        Region srcBucketRegion = new Region("ap-shanghai");
        // 源bucket, bucket名需包含appid
        String srcBucketName = "srcBucket-1251668577";
        // 要拷贝的源文件
        String srcKey = "aaa/bbb.txt";
        // 目的bucket, bucket名需包含appid
        String destBucketName = "destBucket-1251668577";
        // 要拷贝的目的文件
        String destKey = "ccc/ddd.txt";

        COSClient srcCOSClient = new COSClient(cred, new ClientConfig(srcBucketRegion));
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketRegion, srcBucketName,
                srcKey, destBucketName, destKey);
        try {
            Copy copy = transferManager.copy(copyObjectRequest, srcCOSClient, null);
            // 返回一个异步结果copy, 可同步的调用waitForCopyResult等待copy结束, 成功返回CopyResult, 失败抛出异常.
            CopyResult copyResult = copy.waitForCopyResult();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transferManager.shutdownNow();
        srcCOSClient.shutdown();
        cosclient.shutdown();
    }

    // copy接口支持根据文件大小自动选择copy或者分块copy
    // 以下代码展示同园区拷贝, 即将同园区的文件拷贝到另一个园区
    public static void copyFileForSameRegion() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);


        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosclient, threadPool);
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        transferManagerConfiguration.setMultipartCopyThreshold(20*1024*1024);
        transferManager.setConfiguration(transferManagerConfiguration);

        // 要拷贝的bucket region, 支持跨园区拷贝
        Region srcBucketRegion = new Region("ap-beijing-1");
        // 源bucket, bucket名需包含appid
        String srcBucketName = "srcBucket-1251668577";
        // 要拷贝的源文件
        String srcKey = "aaa/bbb.txt";
        // 目的bucket, bucket名需包含appid
        String destBucketName = "destBucket-1251668577";
        // 要拷贝的目的文件
        String destKey = "ccc/ddd.txt";

        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketRegion, srcBucketName,
                srcKey, destBucketName, destKey);
        try {
            Copy copy = transferManager.copy(copyObjectRequest);
            // 返回一个异步结果copy, 可同步的调用waitForCopyResult等待copy结束, 成功返回CopyResult, 失败抛出异常.
            CopyResult copyResult = copy.waitForCopyResult();
            System.out.println(copyResult.getCrc64Ecma());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transferManager.shutdownNow();
        cosclient.shutdown();
    }

    public static void main(String[] args) {
        copyFileForSameRegion();
    }
}
