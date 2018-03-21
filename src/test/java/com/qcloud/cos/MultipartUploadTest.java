package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.AbortMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadResult;
import com.qcloud.cos.model.InitiateMultipartUploadRequest;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.ListMultipartUploadsRequest;
import com.qcloud.cos.model.ListPartsRequest;
import com.qcloud.cos.model.MultipartUpload;
import com.qcloud.cos.model.MultipartUploadListing;
import com.qcloud.cos.model.PartETag;
import com.qcloud.cos.model.PartListing;
import com.qcloud.cos.model.PartSummary;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UploadPartRequest;
import com.qcloud.cos.model.UploadPartResult;
import com.qcloud.cos.utils.Md5Utils;

public class MultipartUploadTest extends AbstractCOSClientTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    public String testInitMultipart(String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucket, key);
        request.setStorageClass(StorageClass.Standard_IA);
        InitiateMultipartUploadResult initResult = cosclient.initiateMultipartUpload(request);
        return initResult.getUploadId();
    }

    public void testUploadPart(String key, String uploadId, int partNumber, byte[] data) {
        UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setBucketName(bucket);
        uploadPartRequest.setKey(key);
        uploadPartRequest.setUploadId(uploadId);
        uploadPartRequest.setInputStream(new ByteArrayInputStream(data));
        uploadPartRequest.setPartSize(data.length);
        uploadPartRequest.setPartNumber(partNumber);

        UploadPartResult uploadPartResult = cosclient.uploadPart(uploadPartRequest);
        assertEquals(Md5Utils.md5Hex(data), uploadPartResult.getETag());
        assertEquals(partNumber, uploadPartResult.getPartNumber());
    }

    public List<PartETag> testListMultipart(String key, String uploadId, int expectedPartNum) {
        List<PartETag> partETags = new LinkedList<>();
        PartListing partListing = null;
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucket, key, uploadId);
        long currentPartNum = 0;
        do {
            partListing = cosclient.listParts(listPartsRequest);
            for (PartSummary partSummary : partListing.getParts()) {
                ++currentPartNum;
                assertEquals(currentPartNum, partSummary.getPartNumber());
                partETags.add(new PartETag(partSummary.getPartNumber(), partSummary.getETag()));
            }
            listPartsRequest.setPartNumberMarker(partListing.getNextPartNumberMarker());
        } while (partListing.isTruncated());
        assertEquals(expectedPartNum, currentPartNum);
        return partETags;
    }

    public void testCompleteMultiPart(String key,
                                      String uploadId,
                                      List<PartETag> partETags,
                                      int expectedPartNum) {
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucket, key, uploadId, partETags);
        CompleteMultipartUploadResult completeResult =
                cosclient.completeMultipartUpload(completeMultipartUploadRequest);
        assertNotNull(completeResult.getRequestId());
        assertNotNull(completeResult.getDateStr());
        String etag = completeResult.getETag();
        assertTrue(etag.contains("-"));
        try {
            int etagPartNum = Integer.valueOf(etag.substring(etag.indexOf("-") + 1));
            assertEquals(expectedPartNum, etagPartNum);
        } catch (NumberFormatException e) {
            fail("part number in etag is invalid. etag: " + etag);
        }
    }

    public void testMultiPartUploadObject(long filesize, long partSize) throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        assertTrue(partSize >= 1024 * 1024L);
        assertTrue(filesize >= partSize);
        String key =
                String.format("ut_multipart_size_%d_part_%d_time_%d_random_%d",
                              filesize,
                              partSize,
                              System.currentTimeMillis(),
                              ThreadLocalRandom.current().nextLong());

        try {
            String uploadId = testInitMultipart(key);
            assertNotNull(uploadId);

            int partNum = 0;
            long byteUploaded = 0;
            while (byteUploaded < filesize) {
                ++partNum;
                long currentPartSize = Math.min(filesize - byteUploaded, partSize);
                byte[] dateUploaded = new byte[new Long(currentPartSize).intValue()];
                ThreadLocalRandom.current().nextBytes(dateUploaded);
                testUploadPart(key, uploadId, partNum, dateUploaded);
                byteUploaded += currentPartSize;
            }

            List<PartETag> partETags = testListMultipart(key, uploadId, partNum);
            testCompleteMultiPart(key, uploadId, partETags, partNum);

            headMultiPartObject(key, filesize, partNum);
        } finally {
            clearObject(key);
        }
    }

    @Test
    public void testMultipartUploadObjectSize_32M_Part_1M() throws IOException {
        testMultiPartUploadObject(1 * 1024 * 1024L, 1 * 1024 * 1024L);
    }

    // 测试分块上传
    @Ignore
    public void testMultipartUploadObjectSize_100M_Part_3M() throws IOException {
        testMultiPartUploadObject(100 * 1024 * 1024L, 3 * 1024 * 1024L);
    }

    @Ignore
    public void testMultipartUploadObjectSize_100M_Part_NotAlign_1M() throws IOException {
        // 这里用一个任意尺寸的，非1M对齐的来做个测试
        testMultiPartUploadObject(100 * 1024 * 1024L, 3 * 1024 * 1024L + 13);
    }

    @Ignore
    public void testMultipartUploadObjectSize_1G_Part_1M() throws IOException {
        testMultiPartUploadObject(1024 * 1024 * 1024L, 1024 * 1024L);
    }

    @Ignore
    public void testMultipartUploadObjectSize_1G_Part_NotAlign_1M() throws IOException {
        testMultiPartUploadObject(1024 * 1024 * 1024L, 1024 * 1024L + 37);
    }

    @Test
    public void testListMultipartUploads() {
        if (!judgeUserInfoValid()) {
            return;
        }
        String key = "ut/testListMultipart.txt";
        String uploadId = testInitMultipart(key);
        ListMultipartUploadsRequest listMultipartUploadsRequest =
                new ListMultipartUploadsRequest(bucket);
        listMultipartUploadsRequest.setMaxUploads(100);
        listMultipartUploadsRequest.setPrefix("ut/");
        while (true) {
            MultipartUploadListing multipartUploadListing =
                    cosclient.listMultipartUploads(listMultipartUploadsRequest);
            List<MultipartUpload> multipartUploads = multipartUploadListing.getMultipartUploads();
            for (MultipartUpload mUpload : multipartUploads) {
                if (mUpload.getUploadId().equals(uploadId)) {
                    assertEquals(key, mUpload.getKey());
                    return;
                }
            }
            if (!multipartUploadListing.isTruncated()) {
                break;
            }
            listMultipartUploadsRequest.setKeyMarker(multipartUploadListing.getNextKeyMarker());
            listMultipartUploadsRequest.setUploadIdMarker(multipartUploadListing.getNextUploadIdMarker());
        }
    }
    
    @Test
    public void testAbortMultipartUploads() {
        if (!judgeUserInfoValid()) {
            return;
        }
        String key = "ut/testAbortMultipart.txt";
        String uploadId = testInitMultipart(key);
        AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucket, key, uploadId);
        cosclient.abortMultipartUpload(abortMultipartUploadRequest);
        try {
            testListMultipart(key, uploadId, 1);
            fail("Aborted uploadid is still valid. uploadId:" + uploadId);
        } catch (CosServiceException e) {
        }
    }
}
