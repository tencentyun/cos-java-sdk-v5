package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.Download;
import com.qcloud.cos.transfer.MultipleFileDownload;
import com.qcloud.cos.transfer.MultipleFileUpload;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import com.qcloud.cos.utils.Md5Utils;

public class TransferManagerTest extends AbstractCOSClientTest {
    private static TransferManager transferManager = null;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
        transferManager = new TransferManager(AbstractCOSClientTest.cosclient);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        transferManager.shutdownNow();
        AbstractCOSClientTest.destoryCosClient();
    }


    @Test
    public void testTransferManagerUploadDownSmallFile()
            throws IOException, CosServiceException, CosClientException, InterruptedException {
        TransferManager transferManager = new TransferManager(cosclient);
        File localFile = buildTestFile(1024 * 1024 * 2L);
        File downFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "/ut/" + localFile.getName();
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            // head object
            headSimpleObject(key, localFile.length(), Md5Utils.md5Hex(localFile));
            assertEquals(Md5Utils.md5Hex(localFile), uploadResult.getETag());
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            Download download = transferManager.download(getObjectRequest, downFile);
            download.waitForCompletion();
            // check file
            assertEquals(Md5Utils.md5Hex(localFile), Md5Utils.md5Hex(downFile));
        } finally {
            // clear object
            clearObject(key);
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
        TransferManager transferManager = new TransferManager(cosclient);
        File localFile = buildTestFile(1024 * 1024 * 10L);
        File downFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "/ut/" + localFile.getName();
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();
            assertTrue(uploadResult.getETag().contains("-"));
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            Download download = transferManager.download(getObjectRequest, downFile);
            download.waitForCompletion();
            // check file
            assertEquals(Md5Utils.md5Hex(localFile), Md5Utils.md5Hex(downFile));
        } finally {
            // delete file on cos
            clearObject(key);
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
        String folderPrefix = "/ut_uploaddir/";
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
        String folderPrefix = "/ut_uploaddir/";
        File localFile1 = buildTestFile(1L);
        File localFile2 = buildTestFile(1024L);
        String key1 = folderPrefix + localFile1.getName();
        String key2 = folderPrefix + localFile2.getName();
        String downloadDirName = "ut_download_dir";
        File downloaddir = new File(downloadDirName);
        if (!downloaddir.exists()) {
            downloaddir.mkdir();
        }
        File downloadFile1 = new File(downloadDirName + folderPrefix + localFile1.getName());
        File downloadFile2 = new File(downloadDirName + folderPrefix + localFile2.getName());
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
        }
    }
}
