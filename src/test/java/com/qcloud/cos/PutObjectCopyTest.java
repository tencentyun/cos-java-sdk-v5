package com.qcloud.cos;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.utils.Md5Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PutObjectCopyTest extends AbstractCOSClientTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    private void testCopySameRegionDiffSize(long fileSize) throws IOException {
        File localFile = buildTestFile(fileSize);
        String srcEtag = Md5Utils.md5Hex(localFile);

        String srcKey = String.format("ut/src_len_%d.txt", fileSize);
        String destKey = String.format("ut/dest_len_%d.txt", fileSize);
        try {
            putObjectFromLocalFile(localFile, srcKey);
            
            CopyObjectRequest copyObjectRequest =
                    new CopyObjectRequest(bucket, srcKey, bucket, destKey);
            copyObjectRequest.setStorageClass(StorageClass.Standard_IA);
            CopyObjectResult copyObjectResult = cosclient.copyObject(copyObjectRequest);
            assertEquals(srcEtag, copyObjectResult.getETag());
            headSimpleObject(srcKey, fileSize, srcEtag);
            headSimpleObject(destKey, fileSize, srcEtag);
        } finally {
            // delete file on cos
            clearObject(srcKey);
            clearObject(destKey);
            // delete local file
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
        }
    }

    @Test
    public void testCopySameRegionEmpty() throws IOException {
        testCopySameRegionDiffSize(0L);
    }

    @Test
    public void testCopySameRegion1M() throws IOException {
        testCopySameRegionDiffSize(1 * 1024 * 1024L);
    }
}
