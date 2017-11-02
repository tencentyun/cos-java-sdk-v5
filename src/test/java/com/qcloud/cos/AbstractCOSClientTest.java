package com.qcloud.cos;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.AccessControlList;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.DeleteObjectRequest;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.Permission;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UinGrantee;
import com.qcloud.cos.region.Region;
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

        if (appid == null || secretId == null || secretKey == null || bucket == null
                || region == null) {
            throw new Exception("UT account info missing");
        }
        COSCredentials cred = new BasicCOSCredentials(appid, secretId, secretKey);
        clientConfig = new ClientConfig(new Region(region));
        cosclient = new COSClient(cred, clientConfig);
        tmpDir = new File("ut_test_tmp_data");
        if (!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
    }

    public static void destoryCosClient() throws Exception {
        if (cosclient != null) {
            cosclient.shutdown();
        }
        tmpDir.delete();
    }

    // 从本地上传
    protected static void putObjectFromLocalFile(File localFile, String key) {
        assertNotNull(cosclient);
        assertNotNull(bucket);

        AccessControlList acl = new AccessControlList();
        UinGrantee uinGrantee = new UinGrantee("734000014");
        acl.grantPermission(uinGrantee, Permission.Read);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
        putObjectRequest.setStorageClass(StorageClass.Standard_IA);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        putObjectRequest.setAccessControlList(acl);

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
    protected static void putObjectFromLocalFileByInputStream(File localFile, long uploadSize,
            String uploadEtag, String key) {
        assertNotNull(cosclient);
        assertNotNull(bucket);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(uploadSize);
        putObjectFromLocalFileByInputStream(localFile, uploadSize, uploadEtag, key, objectMetadata);
    }

    protected static void putObjectFromLocalFileByInputStream(File localFile, long uploadSize,
            String uploadEtag, String key, ObjectMetadata objectMetadata) {
        assertNotNull(cosclient);
        assertNotNull(bucket);

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
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
        try {
            cosclient.getObject(getObjectRequest, localDownFile);
        } catch (Exception e) {
            fail(e.toString());
        }
    }


    // 删除COS上的object
    protected static void clearObject(String key) {
        assertNotNull(cosclient);
        assertNotNull(bucket);

        cosclient.deleteObject(bucket, key);

        assertFalse(cosclient.doesObjectExist(bucket, key));
    }
}
