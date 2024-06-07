package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.qcloud.cos.model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.qcloud.cos.exception.CosServiceException;

public class MultipartUploadTest extends AbstractCOSClientTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }


    @Test
    public void testMultipartUploadObjectSize_4M_Part_1M() throws IOException {
        testMultiPartUploadObject(4 * 1024 * 1024L, 1 * 1024 * 1024L);
    }

    // 测试分块上传
    @Test
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
        String key1 = "ut/testListMultipart1.txt";
        InitiateMultipartUploadRequest initiateMultipartUploadRequest1 = new InitiateMultipartUploadRequest(bucket, key1);
        String uploadId = testInitMultipart(initiateMultipartUploadRequest1);

        String key2 = "ut/testListMultipart2.txt";
        InitiateMultipartUploadRequest initiateMultipartUploadRequest2 = new InitiateMultipartUploadRequest(bucket, key2);
        String uploadId2 = testInitMultipart(initiateMultipartUploadRequest2);

        ListMultipartUploadsRequest listMultipartUploadsRequest =
                new ListMultipartUploadsRequest(bucket);
        listMultipartUploadsRequest.setMaxUploads(100);
        listMultipartUploadsRequest.setPrefix("ut/");
        listMultipartUploadsRequest.setKeyMarker("ut/testListMultipart1.txt");
        listMultipartUploadsRequest.setDelimiter("/");
        listMultipartUploadsRequest.setEncodingType("url");
        listMultipartUploadsRequest.setUploadIdMarker(uploadId);
        while (true) {
            MultipartUploadListing multipartUploadListing =
                    cosclient.listMultipartUploads(listMultipartUploadsRequest);
            List<MultipartUpload> multipartUploads = multipartUploadListing.getMultipartUploads();
            for (MultipartUpload mUpload : multipartUploads) {
                if (mUpload.getUploadId().equals(uploadId2)) {
                    assertEquals(key2, mUpload.getKey());
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
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucket, key);
        String uploadId = testInitMultipart(initiateMultipartUploadRequest);
        AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucket, key, uploadId);
        cosclient.abortMultipartUpload(abortMultipartUploadRequest);
        try {
            testListMultipart(key, uploadId, 1, new ArrayList<String>());
            fail("Aborted uploadid is still valid. uploadId:" + uploadId);
        } catch (CosServiceException e) {
        }
    }
}
