package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.utils.Md5Utils;
import com.qcloud.cos.utils.UrlEncoderUtils;

public class PutGetDelTest extends AbstractCOSClientTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    // 在本地生成不同大小的文件, 并上传， 下载，删除
    private void testPutGetDelObjectDiffSize(long size) throws CosServiceException, IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(size);
        File downLoadFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "ut/" + localFile.getName();
        testPutGetObjectAndClear(key, localFile, downLoadFile);
    }

    private void testPutGetObjectAndClear(String key, File localFile, File downLoadFile)
            throws CosServiceException, IOException {
        if (!judgeUserInfoValid()) {
            return;
        }

        try {
            // put object
            putObjectFromLocalFile(localFile, key);
            // head object
            headSimpleObject(key, localFile.length(), Md5Utils.md5Hex(localFile));
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

    private void testPutObjectByStreamDiffSize(long size) throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        testPutObjectByStreamDiffSize(size, new ObjectMetadata());
    }


    // 流式上传不同尺寸的文件
    private void testPutObjectByStreamDiffSize(long size, ObjectMetadata originMetaData)
            throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(size);
        File downLoadFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "ut/" + localFile.getName();
        originMetaData.setContentLength(size);
        try {
            // put object
            putObjectFromLocalFileByInputStream(localFile, localFile.length(),
                    Md5Utils.md5Hex(localFile), key, originMetaData);
            // get object
            getObject(key, downLoadFile);
            // head object
            ObjectMetadata queryObjectMeta =
                    headSimpleObject(key, size, Md5Utils.md5Hex(localFile));
            // check meta data
            checkMetaData(originMetaData, queryObjectMeta);
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
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(originSize);
        File downLoadFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "ut/" + localFile.getName();
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

    @Test
    public void testPutGetDelObjectNameContainSpecialLetter() throws IOException {
        File localFile = buildTestFile(0L);
        File downLoadFile = new File(localFile.getAbsolutePath() + ".down");
        // 目前server端有bug, 不支持冒号，问号，待修复后添加上
        String key =
                "→↓←→↖↗↙↘!? /\"#$%&()*+',-./0123456789;<=>@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        testPutGetObjectAndClear(key, localFile, downLoadFile);
    }

    @Test
    public void testPutGetDelObjectNameContainChinese() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(1L);
        File downLoadFile = new File(localFile.getAbsolutePath() + ".down");
        String key = "ut/" + "测试文件.png";
        testPutGetObjectAndClear(key, localFile, downLoadFile);
    }


    @Test
    public void testPutGetDelHttps() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        clientConfig.setHttpProtocol(HttpProtocol.https);
        testPutGetDelObjectDiffSize(1L);
        clientConfig.setHttpProtocol(HttpProtocol.http);
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

    @Ignore
    public void testPutGetDelObject100M() throws CosServiceException, IOException {
        testPutGetDelObjectDiffSize(100 * 1024 * 1024L);
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

    @Ignore
    public void testPutObjectByStream100M() throws IOException {
        testPutObjectByStreamDiffSize(100 * 1024 * 1024L);
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
    public void testPutObjectWithChineseContentDisposition() throws IOException {
        ObjectMetadata originObjectMeta = new ObjectMetadata();
        String disposition = "attachment;filename=\""
                + UrlEncoderUtils.encode(new String("测试文件.txt".getBytes(), "UTF-8")) + ".jpg\"";
        originObjectMeta.setContentDisposition(disposition);
        testPutObjectByStreamDiffSize(0L, originObjectMeta);
    }

    @Test
    public void testPutObjectWithContentType() throws IOException {
        ObjectMetadata originObjectMeta = new ObjectMetadata();
        originObjectMeta.setContentType("image/tiff");
        testPutObjectByStreamDiffSize(0L, originObjectMeta);
    }

    @Test
    public void testPutObjectWithServerSideEncryption() throws IOException {
        ObjectMetadata originObjectMeta = new ObjectMetadata();
        originObjectMeta.setServerSideEncryption("AES256");
        testPutObjectByStreamDiffSize(1L, originObjectMeta);
    }

    @Test
    public void testGetObjectIfMatchWrongEtag() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(1024);
        String key = "ut/" + localFile.getName();
        cosclient.putObject(bucket, key, localFile);
        try {
            String fileEtag = Md5Utils.md5Hex(localFile);
            // 打乱一下，得到一个错误的etag
            String wrongEtag = fileEtag.substring(5) + fileEtag.substring(0, 5);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            List<String> eTagList = new ArrayList<>();
            eTagList.add(wrongEtag);
            getObjectRequest.setMatchingETagConstraints(eTagList);

            COSObject cosObject = cosclient.getObject(getObjectRequest);
            assertNull(cosObject);
        }catch(CosServiceException cse) {
            fail(cse.toString());
        } finally {
            cosclient.deleteObject(bucket, key);
            assertTrue(localFile.delete());
        }
    }
    
    @Test
    public void testGetObjectIfMatchRightEtag() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(1024);
        String key = "ut/" + localFile.getName();
        cosclient.putObject(bucket, key, localFile);
        try {
            String fileEtag = Md5Utils.md5Hex(localFile);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            List<String> eTagList = new ArrayList<>();
            eTagList.add("\"" + fileEtag + "\"");
            getObjectRequest.setMatchingETagConstraints(eTagList);

            COSObject cosObject = cosclient.getObject(getObjectRequest);
            assertNotNull(cosObject);
        }catch(CosServiceException cse) {
            fail(cse.toString());
        } finally {
            cosclient.deleteObject(bucket, key);
            assertTrue(localFile.delete());
        }
    }

    @Ignore
    public void testGetObjectIfMatchContainRightEtag() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(1024);
        String key = "ut/" + localFile.getName();
        cosclient.putObject(bucket, key, localFile);
        try {
            String fileEtag = Md5Utils.md5Hex(localFile);
            String wrongEtag = fileEtag.substring(5) + fileEtag.substring(0, 5);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            List<String> eTagList = new ArrayList<>();
            eTagList.add("\"" + wrongEtag + "\"");
            eTagList.add("\"" + fileEtag + "\"");
            getObjectRequest.setMatchingETagConstraints(eTagList);

            COSObject cosObject = cosclient.getObject(getObjectRequest);
            assertNotNull(cosObject);
        }catch(CosServiceException cse) {
            fail(cse.toString());
        } finally {
            cosclient.deleteObject(bucket, key);
            assertTrue(localFile.delete());
        }
    }
    
    @Test
    public void testGetObjectIfNoneMatchRightEtag() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(1024);
        String key = "ut/" + localFile.getName();
        cosclient.putObject(bucket, key, localFile);
        try {
            String fileEtag = Md5Utils.md5Hex(localFile);
            String wrongEtag = fileEtag.substring(5) + fileEtag.substring(0, 5);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            List<String> eTagList = new ArrayList<>();
            eTagList.add("\"" + fileEtag + "\"");
            getObjectRequest.setNonmatchingETagConstraints(eTagList);

            COSObject cosObject = cosclient.getObject(getObjectRequest);
            assertNull(cosObject);
        }catch(CosServiceException cse) {
            fail(cse.toString());
        } finally {
            cosclient.deleteObject(bucket, key);
            assertTrue(localFile.delete());
        }
    }
    
    @Test
    public void testGetObjectIfNoneMatchWrongEtag() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        File localFile = buildTestFile(1024);
        String key = "ut/" + localFile.getName();
        cosclient.putObject(bucket, key, localFile);
        try {
            String fileEtag = Md5Utils.md5Hex(localFile);
            String wrongEtag = fileEtag.substring(5) + fileEtag.substring(0, 5);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            List<String> eTagList = new ArrayList<>();
            eTagList.add("\"" + wrongEtag + "\"");
            getObjectRequest.setNonmatchingETagConstraints(eTagList);

            COSObject cosObject = cosclient.getObject(getObjectRequest);
            assertNotNull(cosObject);
        }catch(CosServiceException cse) {
            fail(cse.toString());
        } finally {
            cosclient.deleteObject(bucket, key);
            assertTrue(localFile.delete());
        }
    }
    
}
