package com.qcloud.cos;

import java.io.File;
import java.io.IOException;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.InitiateMultipartUploadRequest;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.SSECustomerKey;
import com.qcloud.cos.model.CopyPartRequest;
import com.qcloud.cos.model.CopyPartResult;
import com.qcloud.cos.model.PartETag;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.utils.Md5Utils;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertNotNull;

public class PutObjectCopyTest extends AbstractCOSClientTest {
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    private void testCopySameRegionDiffSize(long fileSize, ObjectMetadata newObjectMetaData) throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(fileSize);
        String srcEtag = Md5Utils.md5Hex(localFile);

        String srcKey = String.format("ut/src_len_%d.txt", fileSize);
        String destKey = String.format("ut/dest_len_%d.txt", fileSize);
        try {
            PutObjectResult putObjectResult = putObjectFromLocalFile(localFile, srcKey);
            CopyObjectRequest copyObjectRequest =
                    new CopyObjectRequest(bucket, srcKey, bucket, destKey);
            copyObjectRequest.setSourceVersionId(putObjectResult.getVersionId());
            copyObjectRequest.setStorageClass(StorageClass.Standard_IA);
            if (newObjectMetaData != null) {
                copyObjectRequest.setNewObjectMetadata(newObjectMetaData);
            }
            CopyObjectResult copyObjectResult = cosclient.copyObject(copyObjectRequest);
            assertNotNull(copyObjectResult.getRequestId());
            assertNotNull(copyObjectResult.getDateStr());
//            assertEquals(srcEtag, copyObjectResult.getETag());
            headSimpleObject(srcKey, fileSize, srcEtag);
            /*
            ObjectMetadata destObjectMetadata = headSimpleObject(destKey, fileSize, srcEtag);
            if (newObjectMetaData != null) {
                checkMetaData(newObjectMetaData, destObjectMetadata);
            }
            */
            
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
    
    private void testUpdateDiffSize(long fileSize, ObjectMetadata newObjectMetaData) throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(fileSize);
        String srcEtag = Md5Utils.md5Hex(localFile);

        String srcKey = String.format("ut/src_len_%d.txt", fileSize);
        try {
            putObjectFromLocalFile(localFile, srcKey);
            cosclient.updateObjectMetaData(bucket, srcKey, newObjectMetaData);
            ObjectMetadata destObjectMetadata = headSimpleObject(srcKey, fileSize, srcEtag);
            if (newObjectMetaData != null) {
                checkMetaData(newObjectMetaData, destObjectMetadata);
            }
            
        } finally {
            // delete file on cos
            clearObject(srcKey);
            // delete local file
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
        }
    }

    @Test
    public void testCopySameRegionEmpty() throws IOException {
        testCopySameRegionDiffSize(0L, null);
    }

    @Test
    public void testCopySameRegion1M() throws IOException {
        testCopySameRegionDiffSize(1 * 1024 * 1024L, null);
    }
    
    @Test
    public void testCopySameRegion10M() throws IOException {
        testCopySameRegionDiffSize(10 * 1024 * 1024L, null);
    }
    
    @Test
    public void testCopySameRegionEmptyWithNewMetaData() throws IOException {
        ObjectMetadata newObjectMetadata = new ObjectMetadata();
        newObjectMetadata.setServerSideEncryption("AES256");
        newObjectMetadata.setContentType("image/tiff");
        testCopySameRegionDiffSize(0, newObjectMetadata);
    }
    
    @Test
    public void testCopySameRegion10MWithNewMetaData() throws IOException {
        ObjectMetadata newObjectMetadata = new ObjectMetadata();
        newObjectMetadata.setServerSideEncryption("AES256");
        newObjectMetadata.setContentType("image/png");
        newObjectMetadata.setCacheControl("no-cache");
        testCopySameRegionDiffSize(10 * 1024 * 1024L, newObjectMetadata);
    }
    
    @Test
    public void testUpdateObjectAttr() throws IOException {
        ObjectMetadata newMetadata = new ObjectMetadata();
        newMetadata.setContentType("application/json");
        newMetadata.setContentDisposition("filename=\"abc.txt\"");
        newMetadata.setCacheControl("no-cache");
        newMetadata.setContentEncoding("gzip");
        newMetadata.addUserMetadata("school", "football sport");
        testUpdateDiffSize(0, newMetadata);
    }

    @Test
    public void testCopyPart() throws IOException {
        String bucketName = bucket;

        String srckey = "copysrc.txt";
        String dstkey = "copydst.txt";

        File localFile = buildTestFile(10 * 1024 * 1024L);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, srckey, localFile);
        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);

        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, dstkey);
        // 设置存储类型, 默认是标准(Standard), 低频(Standard_IA), 归档(Archive)
        request.setStorageClass(StorageClass.Standard);
        String uploadId = "";
        try {
            InitiateMultipartUploadResult initResult = cosclient.initiateMultipartUpload(request);
            // 获取uploadid
            uploadId = initResult.getUploadId();
        } catch (CosClientException e) {
            throw e;
        }
        String base64EncodedKey = System.getenv("sse_customer_key");
        SSECustomerKey sseCustomerKey = new SSECustomerKey(base64EncodedKey);

        CopyPartRequest copyPartRequest = new CopyPartRequest();

        copyPartRequest.setSourceSSECustomerKey(sseCustomerKey);

        // 要拷贝的源文件所在的region
        copyPartRequest.setSourceBucketRegion(new Region(region));
        // 要拷贝的源文件的bucket名称
        copyPartRequest.setSourceBucketName(bucketName);
        // 要拷贝的源文件的路径
        copyPartRequest.setSourceKey(srckey);
        copyPartRequest.setSourceAppid(appid);
        // 指定要拷贝的源文件的数据范围(类似content-range)
        copyPartRequest.setFirstByte(0L);
        copyPartRequest.setLastByte(5 * 1024 * 1024L);
        // 目的bucket名称
        copyPartRequest.setDestinationBucketName(bucketName);
        // 目的路径名称
        copyPartRequest.setDestinationKey(dstkey);
        copyPartRequest.setPartNumber(1);
        // uploadId
        copyPartRequest.setUploadId(uploadId);
        try {
            CopyPartResult copyPartResult = cosclient.copyPart(copyPartRequest);
            PartETag partETag = copyPartResult.getPartETag();
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
        }
    }
}
