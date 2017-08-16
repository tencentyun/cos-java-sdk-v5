package com.qcloud.cos;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.CompleteMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadResult;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.InitiateMultipartUploadRequest;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.ListPartsRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PartETag;
import com.qcloud.cos.model.PartListing;
import com.qcloud.cos.model.PartSummary;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UploadPartRequest;
import com.qcloud.cos.model.UploadPartResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Md5Utils;

public class COSClientTest {
    private static String appid = null;
    private static String secretId = null;
    private static String secretKey = null;
    private static String region = null;
    private static String bucket = null;
    private static COSClient cosclient = null;
    private static File tmpDir = null;

    private static File buildTestFile(long fileSize) throws IOException {
        String prefix = String.format("ut_size_%d_time_%d_", fileSize, System.currentTimeMillis());
        String suffix = ".tmp";
        File tmpFile = null;
        tmpFile = File.createTempFile(prefix, suffix, tmpDir);

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
            final int buffSize = 1024;
            byte[] tmpBuf = new byte[buffSize];
            long byteWriten = 0;
            while (byteWriten < fileSize) {
                ThreadLocalRandom.current().nextBytes(tmpBuf);
                long maxWriteLen = Math.min(buffSize, fileSize - byteWriten);
                bos.write(tmpBuf, 0, new Long(maxWriteLen).intValue());
                byteWriten += maxWriteLen;
            }
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
        }
        return tmpFile;
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        appid = System.getenv("appid");
        secretId = System.getenv("secretId");
        secretKey = System.getenv("secretKey");
        region = System.getenv("region");
        bucket = System.getenv("bucket");

        File propFile = new File("ut_account.prop");
        if (propFile.exists() && propFile.canRead()) {
            Properties prop = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(propFile);
                prop.load(fis);
                appid = prop.getProperty("appid");
                secretId = prop.getProperty("secretId");
                secretKey = prop.getProperty("secretKey");
                region = prop.getProperty("region");
                bucket = prop.getProperty("bucket");
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e) {
                    }
                }
            }
        }

        if (appid == null || secretId == null || secretKey == null || bucket == null
                || region == null) {
            throw new Exception("UT account info missing");
        }
        COSCredentials cred = new BasicCOSCredentials(appid, secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        cosclient = new COSClient(cred, clientConfig);
        tmpDir = new File("ut_test_tmp_data");
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (cosclient != null) {
            cosclient.shutdown();
        }
        tmpDir.delete();
    }

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    // 从本地上传
    private void putObjectFromLocalFile(File localFile, String key) {
        assertNotNull(cosclient);
        assertNotNull(bucket);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
        String etag = putObjectResult.getETag();
        String expectEtag = null;
        try {
            expectEtag = Md5Utils.md5Hex(localFile);
        } catch (IOException e) {
            fail(e.toString());
        }
        assertEquals(expectEtag, etag);

    }

    // 流式上传
    private void putObjectFromLocalFileByInputStream(File localFile, long uploadSize,
            String uploadEtag, String key) {
        assertNotNull(cosclient);
        assertNotNull(bucket);

        FileInputStream input = null;
        try {
            input = new FileInputStream(localFile);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(uploadSize);
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucket, key, input, objectMetadata);
            putObjectRequest.setStorageClass(StorageClass.Standard_IA);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            String etag = putObjectResult.getETag();
            assertEquals(uploadEtag, etag);
        } catch (IOException e) {
            fail(e.toString());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
        }

    }

    private void headSimpleObject(String key, long expectedLength, String expectedEtag) {
        ObjectMetadata objectMetadata =
                cosclient.getObjectMetadata(new GetObjectMetadataRequest(bucket, key));
        assertEquals(expectedLength, objectMetadata.getContentLength());
        assertEquals(expectedEtag, objectMetadata.getETag());
        assertNotNull(objectMetadata.getLastModified());
    }

    private void headMultiPartObject(String key, long expectedLength, int expectedPartNum) {
        ObjectMetadata objectMetadata =
                cosclient.getObjectMetadata(new GetObjectMetadataRequest(bucket, key));
        assertEquals(expectedLength, objectMetadata.getContentLength());
        String etag = objectMetadata.getETag();
        assertTrue(etag.contains("-"));
        try {
            int etagPartNum = Integer.valueOf(etag.substring(etag.indexOf("-") + 1));
            assertEquals(expectedPartNum, etagPartNum);
        } catch (NumberFormatException e) {
            fail("part number in etag is invalid. etag: " + etag);
        }
        assertNotNull(objectMetadata.getLastModified());
    }

    // 下载COS的object
    private void getObject(String key, File localDownFile) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        try {
            cosclient.getObject(getObjectRequest, localDownFile);
        } catch (Exception e) {
            fail(e.toString());
        }
    }


    // 删除COS上的object
    private void clearObject(String key) {
        assertNotNull(cosclient);
        assertNotNull(bucket);

        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        cosclient.deleteObject(deleteObjectRequest);

        try {
            cosclient.getobjectMetadata(bucket, key);
            fail("delete key fail. key: " + key);
        } catch (CosServiceException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    // 在本地生成不同大小的文件, 并上传， 下载，删除
    private void testPutGetDelObjectDiffSize(long size) throws CosServiceException, IOException {
        File localFile = buildTestFile(size);
        File downLoadFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "/ut/" + localFile.getName();
        try {
            // put object
            putObjectFromLocalFile(localFile, key);
            // head object
            headSimpleObject(key, size, Md5Utils.md5Hex(localFile));
            // get object
            getObject(key, downLoadFile);
            // check file
            assertEquals(Md5Utils.md5Hex(localFile), Md5Utils.md5Hex(downLoadFile));
        } finally {
            // delete file on cos
            clearObject(key);
            // delete local file
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
            if (downLoadFile.exists()) {
                assertTrue(downLoadFile.delete());
            }
        }
    }

    // 流式上传不同尺寸的文件
    private void testPutObjectByStreamDiffSize(long size) throws IOException {
        File localFile = buildTestFile(size);
        File downLoadFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "/ut/" + localFile.getName();
        try {
            // put object
            putObjectFromLocalFileByInputStream(localFile, localFile.length(),
                    Md5Utils.md5Hex(localFile), key);
            // get object
            getObject(key, downLoadFile);
            // head object
            headSimpleObject(key, size, Md5Utils.md5Hex(localFile));
            // check file
            assertEquals(Md5Utils.md5Hex(localFile), Md5Utils.md5Hex(downLoadFile));
        } finally {
            // delete file on cos
            clearObject(key);
            // delete local file
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
            if (downLoadFile.exists()) {
                assertTrue(downLoadFile.delete());
            }
        }
    }

    public void testPutObjectByTruncateDiffSize(long originSize, long truncateSize)
            throws IOException {
        File localFile = buildTestFile(originSize);
        File downLoadFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "/ut/" + localFile.getName();
        try {
            byte[] partByte = getFilePartByte(localFile, 0, new Long(truncateSize).intValue());
            String uploadEtag = Md5Utils.md5Hex(partByte);
            // put object
            putObjectFromLocalFileByInputStream(localFile, truncateSize, uploadEtag, key);
            // head object
            headSimpleObject(key, truncateSize, uploadEtag);
            // get object
            getObject(key, downLoadFile);
            // check file
            assertEquals(uploadEtag, Md5Utils.md5Hex(downLoadFile));
        } finally {
            // delete file on cos
            clearObject(key);
            // delete local file
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
            if (downLoadFile.exists()) {
                assertTrue(downLoadFile.delete());
            }
        }
    }

    private byte[] getFilePartByte(File localFile, int offset, int len) {
        byte[] content = new byte[len];
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bis.skip(offset);
            bis.read(content);
        } catch (IOException e) {
            fail(e.toString());
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
        }
        return content;
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

    public void testCompleteMultiPart(String key, String uploadId, List<PartETag> partETags,
            int expectedPartNum) {
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucket, key, uploadId, partETags);
        CompleteMultipartUploadResult completeResult =
                cosclient.completeMultipartUpload(completeMultipartUploadRequest);
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
        assertTrue(partSize >= 1024 * 1024L);
        assertTrue(filesize >= partSize);
        String key = String.format("ut_multipart_size_%d_part_%d_time_%d_random_%d", filesize,
                partSize, System.currentTimeMillis(), ThreadLocalRandom.current().nextLong());

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

    // 测试从本地上传文件
    @Test
    public void testPutGetDelObjectEmpty() throws CosServiceException, IOException {
        testPutGetDelObjectDiffSize(0L);
    }

    @Test
    public void testPutGetDelObject256k() throws CosServiceException, IOException {
        testPutGetDelObjectDiffSize(256 * 1024L);
    }

    @Test
    public void testPutGetDelObject1M() throws CosServiceException, IOException {
        testPutGetDelObjectDiffSize(1024 * 1024L);
    }

    @Test
    public void testPutGetDelObject4M() throws CosServiceException, IOException {
        testPutGetDelObjectDiffSize(4 * 1024 * 1024L);
    }

    @Test
    public void testPutGetDelObject32M() throws CosServiceException, IOException {
        testPutGetDelObjectDiffSize(32 * 1024 * 1024L);
    }

    @Test
    public void testPutGetDelObject100M() throws CosServiceException, IOException {
        testPutGetDelObjectDiffSize(100 * 1024 * 1024L);
    }

    @Test
    public void testPutGetDelObject1G() throws CosServiceException, IOException {
        testPutGetDelObjectDiffSize(1024 * 1024 * 1024L);
    }


    // 测试从流上传文件
    @Test
    public void testPutObjectByStreamEmpty() throws IOException {
        testPutObjectByStreamDiffSize(0L);
    }

    @Test
    public void testPutObjectByStream256K() throws IOException {
        testPutObjectByStreamDiffSize(256 * 1024L);
    }

    @Test
    public void testPutObjectByStream1M() throws IOException {
        testPutObjectByStreamDiffSize(1024 * 1024L);
    }

    @Test
    public void testPutObjectByStream4M() throws IOException {
        testPutObjectByStreamDiffSize(4 * 1024 * 1024L);
    }

    @Test
    public void testPutObjectByStream32M() throws IOException {
        testPutObjectByStreamDiffSize(32 * 1024 * 1024L);
    }

    @Test
    public void testPutObjectByStream100M() throws IOException {
        testPutObjectByStreamDiffSize(100 * 1024 * 1024L);
    }

    @Test
    public void testPutObjectByStream1G() throws IOException {
        testPutObjectByStreamDiffSize(1024 * 1024 * 1024L);
    }

    // 测试只上传流中的一部分字节
    @Test
    public void testPutObjectByTruncateSize_0() throws IOException {
        testPutObjectByTruncateDiffSize(4 * 1024 * 1024, 0L);
    }

    @Test
    public void testPutObjectByTruncateSize_1M() throws IOException {
        testPutObjectByTruncateDiffSize(4 * 1024 * 1024, 1024 * 1024L);
    }

    @Test
    public void testPutObjectByTruncateSize_8M() throws IOException {
        testPutObjectByTruncateDiffSize(32 * 1024 * 1024, 8 * 1024 * 1024L - 1);
    }

    @Test
    public void testMultipartUploadObjectSize_32M_Part_1M() throws IOException {
        testMultiPartUploadObject(32 * 1024 * 1024L, 1 * 1024 * 1024L);
    }

    // 测试分块上传
    @Test
    public void testMultipartUploadObjectSize_100M_Part_3M() throws IOException {
        testMultiPartUploadObject(100 * 1024 * 1024L, 3 * 1024 * 1024L);
    }

    @Test
    public void testMultipartUploadObjectSize_100M_Part_NotAlign_1M() throws IOException {
        // 这里用一个任意尺寸的，非1M对齐的来做个测试
        testMultiPartUploadObject(100 * 1024 * 1024L, 3 * 1024 * 1024L + 13);
    }

    @Ignore
    public void testMultipartUploadObjectSize_1G_Part_1M() throws IOException {
        testMultiPartUploadObject(1024 * 1024 * 1024L, 1024 * 1024L);
    }

    @Test
    public void testMultipartUploadObjectSize_1G_Part_NotAlign_1M() throws IOException {
        testMultiPartUploadObject(1024 * 1024 * 1024L, 1024 * 1024L + 37);
    }

}
