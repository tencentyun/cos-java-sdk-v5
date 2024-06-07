package com.qcloud.cos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.qcloud.cos.event.COSProgressListenerChain;
import com.qcloud.cos.event.TransferProgressUpdatingListener;
import com.qcloud.cos.model.*;
import com.qcloud.cos.transfer.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Md5Utils;

import static org.junit.Assert.*;

public class TransferManagerTest extends AbstractCOSClientTest {
    private static TransferManager transferManager = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
        if (cosclient != null) {
            transferManager = new TransferManager(AbstractCOSClientTest.cosclient,
                    Executors.newFixedThreadPool(32));
            TransferManagerConfiguration configuration = new TransferManagerConfiguration();
            transferManager.setConfiguration(configuration);
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (transferManager != null) {
            transferManager.shutdownNow(false);
        }
        AbstractCOSClientTest.destoryCosClient();
    }


    @Test
    public void testTransferManagerUploadDownCopySmallFile()
            throws IOException, CosServiceException, CosClientException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        TransferManager transferManager = new TransferManager(cosclient);
        File localFile = buildTestFile(1024 * 1024 * 2L);
        File downFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "ut/" + localFile.getName();
        String destKey = key + ".copy";
        try {
            Upload upload = transferManager.upload(bucket, key, localFile);
            UploadResult uploadResult = upload.waitForUploadResult();
            // head object
            headSimpleObject(key, localFile.length(), Md5Utils.md5Hex(localFile));
            assertEquals(Md5Utils.md5Hex(localFile), uploadResult.getETag());
            assertFalse(upload.isResumeableMultipartUploadAfterFailed());
            assertNull(upload.getResumeableMultipartUploadId());
            assertNotNull(uploadResult.getRequestId());
            assertNotNull(uploadResult.getDateStr());
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            Download download = transferManager.download(getObjectRequest, downFile);
            download.waitForCompletion();
            // check file
            assertEquals(Md5Utils.md5Hex(localFile), Md5Utils.md5Hex(downFile));

            CopyObjectRequest copyObjectRequest =
                    new CopyObjectRequest(bucket, key, bucket, destKey);
            Copy copy = transferManager.copy(copyObjectRequest, cosclient, null);
            copy.waitForCompletion();
        } finally {
            // clear object
            clearObject(key);
            // clear dest object
            clearObject(destKey);
            // delete smallfile
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
            if (downFile.exists()) {
                assertTrue(downFile.delete());
            }
        }

    }

    @Test
    public void testTransferManagerUploadDownBigFile()
            throws IOException, CosServiceException, CosClientException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        TransferManager transferManager = new TransferManager(cosclient);
        File localFile = buildTestFile(1024 * 1024 * 10L);
        File downFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "ut/" + localFile.getName();
        String destKey = key + ".copy";
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setServerSideEncryption("AES256");
            putObjectRequest.setMetadata(objectMetadata);
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            assertTrue(uploadResult.getETag().contains("-"));
            assertTrue(upload.isResumeableMultipartUploadAfterFailed());
            assertNotNull(upload.getResumeableMultipartUploadId());
            assertNotNull(uploadResult.getRequestId());
            assertNotNull(uploadResult.getDateStr());
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            Download download = transferManager.download(getObjectRequest, downFile);
            download.waitForCompletion();
            // check file
            assertEquals(Md5Utils.md5Hex(localFile), Md5Utils.md5Hex(downFile));

            CopyObjectRequest copyObjectRequest =
                    new CopyObjectRequest(bucket, key, bucket, destKey);
            Copy copy = transferManager.copy(copyObjectRequest, cosclient, null);
            copy.waitForCompletion();
        } finally {
            // delete file on cos
            clearObject(key);
            // delete file on cos
            clearObject(destKey);
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
            if (downFile.exists()) {
                assertTrue(downFile.delete());
            }
        }
    }

    @Test
    public void testTransferManagerUploadLocalDir()
            throws IOException, CosServiceException, CosClientException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        String folderPrefix = "ut_uploaddir/";
        File localFile1 = buildTestFile(1L);
        File localFile2 = buildTestFile(1024L);
        String key1 = folderPrefix + localFile1.getName();
        String key2 = folderPrefix + localFile2.getName();
        try {
            MultipleFileUpload multipleFileUpload =
                    transferManager.uploadDirectory(bucket, folderPrefix, tmpDir, true);
            multipleFileUpload.waitForCompletion();
            headSimpleObject(key1, localFile1.length(), Md5Utils.md5Hex(localFile1));
            headSimpleObject(key2, localFile2.length(), Md5Utils.md5Hex(localFile2));
        } finally {
            if (localFile1.exists()) {
                assertTrue(localFile1.delete());
            }
            if (localFile2.exists()) {
                assertTrue(localFile2.delete());
            }
            clearObject(key1);
            clearObject(key2);
        }
    }

    @Test
    public void testTransferManagerUploadDownloadDir()
            throws IOException, CosServiceException, CosClientException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        String folderPrefix = "ut_uploaddir/";
        File localFile1 = buildTestFile(1L);
        File localFile2 = buildTestFile(1024L);
        String key1 = folderPrefix + localFile1.getName();
        String key2 = folderPrefix + localFile2.getName();
        String downloadDirName = "ut_download_dir";
        File downloaddir = new File(downloadDirName);
        if (!downloaddir.exists()) {
            downloaddir.mkdir();
        }
        File downloadFile1 = new File(downloadDirName + "/" +  folderPrefix + localFile1.getName());
        File downloadFile2 = new File(downloadDirName + "/" + folderPrefix + localFile2.getName());
        try {
            MultipleFileUpload multipleFileUpload =
                    transferManager.uploadDirectory(bucket, folderPrefix, tmpDir, true);
            multipleFileUpload.waitForCompletion();
            headSimpleObject(key1, localFile1.length(), Md5Utils.md5Hex(localFile1));
            headSimpleObject(key2, localFile2.length(), Md5Utils.md5Hex(localFile2));
            MultipleFileDownload multipleFileDownload =
                    transferManager.downloadDirectory(bucket, folderPrefix, downloaddir);
            multipleFileDownload.waitForCompletion();
            assertTrue(downloadFile1.exists());
            assertTrue(downloadFile2.exists());
            assertEquals(Md5Utils.md5Hex(localFile1), Md5Utils.md5Hex(downloadFile1));
            assertEquals(Md5Utils.md5Hex(localFile2), Md5Utils.md5Hex(downloadFile2));
        } finally {
            if (localFile1.exists()) {
                assertTrue(localFile1.delete());
            }
            if (localFile2.exists()) {
                assertTrue(localFile2.delete());
            }
            if (downloadFile1.exists()) {
                assertTrue(downloadFile1.delete());
            }
            if (downloadFile2.exists()) {
                assertTrue(downloadFile2.delete());
            }

            clearObject(key1);
            clearObject(key2);
            deleteDir(downloaddir);
        }
    }

    @Test
    public void testResumableDownload2M() throws Exception {
        testResumableDownload(2L * 1024 * 1024);
    }

    @Test
    public void testResumableDownload40M() throws Exception {
        testResumableDownload(40L * 1024 * 1024);
    }

    private static void testResumableDownload(long filesize) throws Exception {
        File localFile = buildTestFile(filesize);
        String key = "testResumableDownload.txt";
        Upload upload = transferManager.upload(bucket, key, localFile);
        UploadResult uploadResult = upload.waitForUploadResult();

        GetObjectRequest getObj = new GetObjectRequest(bucket, key);
        File dstFile = new File("dstFile");
        Download download = transferManager.download(getObj, dstFile, true);

        try {
            download.waitForCompletion();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            dstFile.delete();
            localFile.delete();
        }
    }

    @Test
    public void testAbortMultipartUploads() throws Exception {
        String key = "testAbortMultipartUploads.txt";
        try {
            InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucket, key);
            cosclient.initiateMultipartUpload(initiateMultipartUploadRequest);
            transferManager.abortMultipartUploads(bucket, new Date(System.currentTimeMillis()));
        } catch (CosServiceException e) {
            System.out.println(e.getErrorMessage());
            e.printStackTrace();
        } catch (CosClientException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testResumeUploadAndDownload() throws Exception {
        File localFile = buildTestFile(100L * 1024 * 1024);
        String key = "testResumeUploads.txt";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));
        // 3 生成cos客户端
        COSClient CosClient = new COSClient(cred, clientConfig);

        ExecutorService threadPool = Executors.newFixedThreadPool(32);
        TransferManager transManager = new TransferManager(CosClient, threadPool);

        try {
            // 返回一个异步结果Upload, 可同步的调用waitForUploadResult等待upload结束, 成功返回UploadResult, 失败抛出异常.
            Upload upload = transManager.upload(putObjectRequest);
            Thread.sleep(5000);
            PersistableUpload persistableUpload = upload.pause();
            upload = transManager.resumeUpload(persistableUpload);
            upload.waitForUploadResult();
        } catch (CosServiceException cse) {
            if (cse.getStatusCode() != 404) {
                cse.printStackTrace();
            }
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            localFile.delete();
        }

        File downloadFile = new File("download.txt");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        try {
            // 返回一个异步结果copy, 可同步的调用waitForCompletion等待download结束, 成功返回void, 失败抛出异常.
            Download download = transManager.download(getObjectRequest, downloadFile);
            Thread.sleep(1000);
            PersistableDownload persistableDownload = download.pause();
            download = transManager.resumeDownload(persistableDownload);
            download.waitForCompletion();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            downloadFile.delete();
            transManager.shutdownNow();
        }
    }

    @Test
    public void testPartCopy() throws Exception {
        TransferManagerConfiguration configuration = transferManager.getConfiguration();
        configuration.setMultipartCopyThreshold(2 * 1024 * 1024L);
        File localFile = buildTestFile(4L * 1024 * 1024);
        String key = "testPartCopy.txt";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
        Upload upload = transferManager.upload(putObjectRequest);
        upload.waitForCompletion();

        String dst_bucket = System.getenv("dst_bucket") + "-" + (int) (System.currentTimeMillis()) + "-" + appid;

        Boolean switch_to_stop = true;
        while (switch_to_stop) {
            try {
                cosclient.createBucket(dst_bucket);
                switch_to_stop = false;
            } catch (CosServiceException cse) {
                if (cse.getStatusCode() == 409) {
                    dst_bucket = System.getenv("dst_bucket") + (int) (Math.random() * 1000000) + "-" + appid;
                    continue;
                }
                cse.printStackTrace();
                fail(cse.getErrorMessage());
            }
        }

        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucket, key, dst_bucket, "dstObj.txt");
        copyObjectRequest.setStorageClass(StorageClass.Archive);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("text/plain");
        copyObjectRequest.setNewObjectMetadata(objectMetadata);
        try {
            Copy copy = transferManager.copy(copyObjectRequest);
            copy.waitForCompletion();
        } catch (CosServiceException cse) {
            if (404 != cse.getStatusCode()) {
                fail(cse.toString());
            }
        } finally {
            deleteBucket(dst_bucket);
            localFile.delete();
        }
    }

    @Test
    public void testCreateTransfWithError() {
        ClientConfig config = new ClientConfig();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        COSClient cos_Client = new COSClient(cred, config);
        try {
            TransferManager transfer_manager = new TransferManager(cos_Client);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertEquals("region in clientConfig of cosClient must be specified!", e.getMessage());
        }
    }

    @Test
    public void testDownloadWithRange() throws IOException {
        int inputStreamLength = 10 * 1024 * 1024;
        byte[] data = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(inputStreamLength);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, "testDownloadWithRange", inputStream, objectMetadata);
        File dstFile = new File("dstFile");
        File dstFile2 = new File("dstFile2");
        try {
            Upload upload = transferManager.upload(putObjectRequest);
            upload.waitForCompletion();

            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, "testDownloadWithRange");
            getObjectRequest.setRange(512, 1024);

            Download download = transferManager.download(getObjectRequest, dstFile);
            download.waitForCompletion();

            download = transferManager.download(bucket, "testDownloadWithRange", dstFile2);
            download.abort();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } catch (CosClientException cce) {
            System.out.println(cce.getMessage());
        } finally {
            dstFile.delete();
            dstFile2.delete();
            inputStream.close();
        }
    }

    @Test
    public void testDownloadImpl() {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, "testDownloadImpl");
        COSObject cosObject = new COSObject();
        ObjectMetadata metadata = new ObjectMetadata();
        cosObject.setObjectMetadata(metadata);
        cosObject.setBucketName(bucket);
        cosObject.setKey("testDownloadImpl");

        File dstFile = new File("dstFile");
        TransferProgress transferProgress = new TransferProgress();
        String description = "test downloading from " + getObjectRequest.getBucketName() + "/"
                + getObjectRequest.getKey();
        COSProgressListenerChain listenerChain = new COSProgressListenerChain(
                // The listener for updating transfer progress
                new TransferProgressUpdatingListener(transferProgress),
                getObjectRequest.getGeneralProgressListener(), null);

        DownloadImpl download = new DownloadImpl(description, transferProgress, listenerChain,
                null, null, getObjectRequest, dstFile);

        download.setCosObject(cosObject);

        ObjectMetadata objectMetadata = download.getObjectMetadata();
        String bucketname = download.getBucketName();
        String key = download.getKey();
        dstFile.delete();
    }

    // transfer manager对不同园区5G以上文件进行分块拷贝
    @Ignore
    public void testTransferManagerCopyBigFileFromDiffRegion()
            throws CosServiceException, CosClientException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        COSCredentials srcCred = new BasicCOSCredentials(secretId, secretKey);
        String srcRegion = "ap-guangzhou";
        ClientConfig srcClientConfig = new ClientConfig(new Region(srcRegion));
        COSClient srcCOSClient = new COSClient(srcCred, srcClientConfig);
        String srcBucketName = "chengwus3gz-12500000000";
        String srcKey = "ut_copy/len10G_1.txt";
        String destKey = "ut_copy_dest/len10G_1.txt";
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(new Region(srcRegion),
                srcBucketName, srcKey, bucket, destKey);
        Copy copy = transferManager.copy(copyObjectRequest, srcCOSClient, null);
        copy.waitForCompletion();
        clearObject(destKey);
    }

    // transfer manager对不同园区5G以下文件进行使用put object copy
    @Ignore
    public void testTransferManagerCopySmallFileFromDiffRegion()
            throws CosServiceException, CosClientException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        COSCredentials srcCred = new BasicCOSCredentials(secretId, secretKey);
        String srcRegion = "ap-guangzhou";
        ClientConfig srcClientConfig = new ClientConfig(new Region(srcRegion));
        COSClient srcCOSClient = new COSClient(srcCred, srcClientConfig);
        String srcBucketName = "chengwus3gz-12500000000";
        String srcKey = "ut_copy/len1G.txt";
        String destKey = "ut_copy_dest/len1G.txt";
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(new Region(srcRegion),
                srcBucketName, srcKey, bucket, destKey);
        Copy copy = transferManager.copy(copyObjectRequest, srcCOSClient, null);
        CopyResult copyResult = copy.waitForCopyResult();
        assertNotNull(copyResult.getRequestId());
        assertNotNull(copyResult.getDateStr());
        clearObject(destKey);
    }

    // transfer manager对相同园区使用put object copy
    @Ignore
    public void testTransferManagerCopyBigFileFromSameRegion()
            throws CosServiceException, CosClientException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        COSCredentials srcCred = new BasicCOSCredentials(secretId, secretKey);
        String srcRegion = region;
        ClientConfig srcClientConfig = new ClientConfig(new Region(srcRegion));
        COSClient srcCOSClient = new COSClient(srcCred, srcClientConfig);
        String srcBucketName = bucket;
        String srcKey = "ut_copy/len10G_1.txt";
        String destKey = "ut_copy_dest/len10G_2.txt";
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(new Region(srcRegion),
                srcBucketName, srcKey, bucket, destKey);
        Copy copy = transferManager.copy(copyObjectRequest, srcCOSClient, null);
        CopyResult copyResult = copy.waitForCopyResult();
        assertNotNull(copyResult.getRequestId());
        assertNotNull(copyResult.getDateStr());
    }

    // transfer manager对相同园区使用put object copy
    @Ignore
    public void testTransferManagerCopySmallFileFromSameRegion()
            throws CosServiceException, CosClientException, InterruptedException {
        if (!judgeUserInfoValid()) {
            return;
        }
        COSCredentials srcCred = new BasicCOSCredentials(secretId, secretKey);
        String srcRegion = region;
        ClientConfig srcClientConfig = new ClientConfig(new Region(srcRegion));
        COSClient srcCOSClient = new COSClient(srcCred, srcClientConfig);
        String srcBucketName = bucket;
        String srcKey = "ut_copy/len1G.txt";
        String destKey = "ut_copy_dest/len1G_2.txt";
        CopyObjectRequest copyObjectRequest =
                new CopyObjectRequest(srcBucketName, srcKey, bucket, destKey);
        Copy copy = transferManager.copy(copyObjectRequest, srcCOSClient, null);
        CopyResult copyResult = copy.waitForCopyResult();
        assertNotNull(copyResult.getRequestId());
        assertNotNull(copyResult.getDateStr());
    }

}
