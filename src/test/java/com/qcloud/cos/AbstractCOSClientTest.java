package com.qcloud.cos;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.internal.SkipMd5CheckStrategy;
import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.BucketVersioningConfiguration;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.HeadBucketRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.Permission;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ResponseHeaderOverrides;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UinGrantee;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.DateUtils;
import com.qcloud.cos.utils.Md5Utils;

public class AbstractCOSClientTest {
    protected static String appid = null;
    protected static String secretId = null;
    protected static String secretKey = null;
    protected static String region = null;
    protected static String bucket = null;
    protected static ClientConfig clientConfig = null;
    protected static COSClient cosclient = null;
    protected static File tmpDir = null;

    protected static File buildTestFile(long fileSize) throws IOException {
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

    protected static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void initCosClient() throws Exception {
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

        if (secretId == null || secretKey == null || bucket == null || region == null) {
            System.out.println("cos ut user info missing. skip all test");
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        clientConfig = new ClientConfig(new Region(region));
        cosclient = new COSClient(cred, clientConfig);
        tmpDir = new File("ut_test_tmp_data");
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
        createBucket();
    }

    private static void createBucket() throws Exception {
        try {
            String bucketName = bucket;
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            Bucket createdBucket = cosclient.createBucket(createBucketRequest);
            assertEquals(bucketName, createdBucket.getName());
            Thread.sleep(5000L);
            assertTrue(cosclient.doesBucketExist(bucketName));
        } catch (CosServiceException cse) {
            fail(cse.toString());
        }
    }

    private static void deleteBucket() throws Exception {
        try {
            String bucketName = bucket;
            cosclient.deleteBucket(bucketName);
            // 删除bucket后, 由于server端有缓存 需要稍后查询, 这里sleep 5 秒
            Thread.sleep(5000L);
            assertFalse(cosclient.doesBucketExist(bucketName));
        } catch (CosServiceException cse) {
            fail(cse.toString());
        }
    }

    public static void destoryCosClient() throws Exception {
        if (cosclient != null) {
            deleteBucket();
            cosclient.shutdown();
        }
        if (tmpDir != null) {
            deleteDir(tmpDir);
        }
    }

    protected static boolean judgeUserInfoValid() {
        return cosclient != null;
    }

    // 从本地上传
    protected static PutObjectResult putObjectFromLocalFile(File localFile, String key) {
        if (!judgeUserInfoValid()) {
            return null;
        }

        AccessControlList acl = new AccessControlList();
        UinGrantee uinGrantee = new UinGrantee("734000014");
        acl.grantPermission(uinGrantee, Permission.Read);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        // putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        // putObjectRequest.setAccessControlList(acl);

        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
        assertNotNull(putObjectResult.getRequestId());
        assertNotNull(putObjectResult.getDateStr());
        String etag = putObjectResult.getETag();
        String expectEtag = null;
        try {
            expectEtag = Md5Utils.md5Hex(localFile);
        } catch (IOException e) {
            fail(e.toString());
        }
        assertEquals(expectEtag, etag);
        return putObjectResult;
    }

    // 流式上传
    protected static void putObjectFromLocalFileByInputStream(File localFile, long uploadSize,
            String uploadEtag, String key) {
        if (!judgeUserInfoValid()) {
            return;
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(uploadSize);
        putObjectFromLocalFileByInputStream(localFile, uploadSize, uploadEtag, key, objectMetadata);
    }

    protected static void putObjectFromLocalFileByInputStream(File localFile, long uploadSize,
            String uploadEtag, String key, ObjectMetadata objectMetadata) {
        if (!judgeUserInfoValid()) {
            return;
        }

        FileInputStream input = null;
        try {
            input = new FileInputStream(localFile);
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



    protected static ObjectMetadata headSimpleObject(String key, long expectedLength,
            String expectedEtag) {
        ObjectMetadata objectMetadata =
                cosclient.getObjectMetadata(new GetObjectMetadataRequest(bucket, key));
        assertEquals(expectedLength, objectMetadata.getContentLength());
        assertEquals(expectedEtag, objectMetadata.getETag());
        assertNotNull(objectMetadata.getLastModified());
        return objectMetadata;
    }

    protected static ObjectMetadata headMultiPartObject(String key, long expectedLength,
            int expectedPartNum) {
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
        return objectMetadata;
    }

    // 下载COS的object
    protected static void getObject(String key, File localDownFile) {
        System.setProperty(SkipMd5CheckStrategy.DISABLE_GET_OBJECT_MD5_VALIDATION_PROPERTY, "true");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        String responseContentType = "image/x-icon";
        String responseContentLanguage = "zh-CN";
        String responseContentDispositon = "filename=\"abc.txt\"";
        String responseCacheControl = "no-cache";
        String expireStr =
                DateUtils.formatRFC822Date(new Date(System.currentTimeMillis() + 24 * 3600 * 1000));
        responseHeaders.setContentType(responseContentType);
        responseHeaders.setContentLanguage(responseContentLanguage);
        responseHeaders.setContentDisposition(responseContentDispositon);
        responseHeaders.setCacheControl(responseCacheControl);
        responseHeaders.setExpires(expireStr);

        getObjectRequest.setResponseHeaders(responseHeaders);
        try {
            ObjectMetadata objectMetadata = cosclient.getObject(getObjectRequest, localDownFile);
            assertEquals(responseContentType, objectMetadata.getContentType());
            assertEquals(responseContentLanguage, objectMetadata.getContentLanguage());
            assertEquals(responseContentDispositon, objectMetadata.getContentDisposition());
            assertEquals(responseCacheControl, objectMetadata.getCacheControl());
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    protected void checkMetaData(ObjectMetadata originMetaData, ObjectMetadata queryMetaData) {
        Map<String, Object> originRawMeta = originMetaData.getRawMetadata();
        Map<String, Object> queryRawMeta = queryMetaData.getRawMetadata();
        for (Entry<String, Object> entry : originRawMeta.entrySet()) {
            assertTrue(queryRawMeta.containsKey(entry.getKey()));
            assertEquals(entry.getValue(), queryRawMeta.get(entry.getKey()));
        }
    }

    // 删除COS上的object
    protected static void clearObject(String key) {
        if (!judgeUserInfoValid()) {
            return;
        }

        cosclient.deleteObject(bucket, key);
        assertFalse(cosclient.doesObjectExist(bucket, key));
    }
}
