package com.qcloud.cos;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Md5Utils;

public class COSClientTest {
    private static String appid = null;
    private static String secretId = null;
    private static String secretKey = null;
    private static String region = null;
    private static String bucket = null;
    private static COSClient cosclient = null;

    private static File buildTestFile(long fileSize) throws IOException {
        File tmpDir = new File("src/test/resources");
        String prefix = String.format("ut_size_%d_time_%d_", fileSize, System.currentTimeMillis());
        String suffix = ".tmp";
        File tmpFile = null;
        tmpFile = File.createTempFile(prefix, suffix, tmpDir);
        tmpFile.deleteOnExit();

        Random random = new Random();
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(tmpFile));
            final int buffSize = 1024;
            byte[] tmpBuf = new byte[buffSize];
            long byteWriten = 0;
            while (byteWriten < fileSize) {
                random.nextBytes(tmpBuf);
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
        appid = System.getProperty("appid");
        secretId = System.getProperty("secretId");
        secretKey = System.getProperty("secretKey");
        region = System.getProperty("region");
        bucket = System.getProperty("bucket");

        File propFile = new File("src/test/resources/ut_account.prop");
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
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        if (cosclient != null) {
            cosclient.shutdown();
        }
    }

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    private void putObjectFromLocalFile(File localFile) {
        assertNotNull(cosclient);
        assertNotNull(bucket);

        String key = "/ut/" + localFile.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, localFile);
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

    @Test
    public void testPutObjectEmptyFile() throws CosServiceException, IOException {
        File localFile = buildTestFile(0L);
        putObjectFromLocalFile(localFile);
        // 确保删除成功, 检测文件在putobject里面被打开未关闭
        assertTrue(localFile.delete());
    }

    @Test
    public void testPutObject256k() throws CosServiceException, IOException {
        File localFile = buildTestFile(256 * 1024L);
        putObjectFromLocalFile(localFile);
        // 确保删除成功, 检测文件在putobject里面被打开未关闭
        assertTrue(localFile.delete());
    }

    @Test
    public void testPutObject1M() throws CosServiceException, IOException {
        File localFile = buildTestFile(1 * 1024 * 1024L);
        putObjectFromLocalFile(localFile);
        // 确保删除成功, 检测文件在putobject里面被打开未关闭
        assertTrue(localFile.delete());
    }

    @Test
    public void testPutObject4M() throws CosServiceException, IOException {
        File localFile = buildTestFile(4 * 1024 * 1024L);
        putObjectFromLocalFile(localFile);
        // 确保删除成功, 检测文件在putobject里面被打开未关闭
        assertTrue(localFile.delete());
    }

    @Test
    public void testPutObject32M() throws CosServiceException, IOException {
        File localFile = buildTestFile(32 * 1024 * 1024L);
        putObjectFromLocalFile(localFile);
        // 确保删除成功, 检测文件在putobject里面被打开未关闭
        assertTrue(localFile.delete());
    }

    @Ignore
    public void testPutObject100M() throws CosServiceException, IOException {
        File localFile = buildTestFile(100 * 1024 * 1024L);
        putObjectFromLocalFile(localFile);
        // 确保删除成功, 检测文件在putobject里面被打开未关闭
        assertTrue(localFile.delete());
    }

    @Ignore
    public void testPutObject1G() throws CosServiceException, IOException {
        File localFile = buildTestFile(1024 * 1024 * 1024L);
        putObjectFromLocalFile(localFile);
        // 确保删除成功, 检测文件在putobject里面被打开未关闭
        assertTrue(localFile.delete());
    }
}
