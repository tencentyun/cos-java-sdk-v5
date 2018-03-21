package com.qcloud.cos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.slf4j.Logger;

import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.ResponseHeaderOverrides;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.DateUtils;
import com.qcloud.cos.utils.Md5Utils;

public class GeneratePresignedUrlTest extends AbstractCOSClientTest {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BatchDeleteTest.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }


    @Test
    public void testGetFile() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        long localFileLen = 1024;
        File localFile = buildTestFile(1024);
        String key = "ut/" + localFile.getName();

        putObjectFromLocalFile(localFile, key);

        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucket, key, HttpMethodName.GET);
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
        req.setResponseHeaders(responseHeaders);
        URL url = cosclient.generatePresignedUrl(req);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);
        assertEquals(responseContentType, connection.getContentType());
        assertEquals(localFileLen, connection.getContentLength());
        assertEquals(responseContentLanguage, connection.getHeaderField("Content-Language"));
        assertEquals(responseContentDispositon, connection.getHeaderField("Content-Disposition"));
        assertEquals(responseCacheControl, connection.getHeaderField("Cache-Control"));

        clearObject(key);
        assertTrue(localFile.delete());
    }

    @Test
    public void testPutFile() throws IOException {
        if (!judgeUserInfoValid()) {
            return;
        }
        String key = "ut/generate_url_test_upload.txt";
        File localFile = buildTestFile(1024);
        Date expirationTime = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        URL url = cosclient.generatePresignedUrl(bucket, key, expirationTime, HttpMethodName.PUT);

        System.out.println(url.toString());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("PUT");

        BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFile));
        int readByte = -1;
        while ((readByte = bis.read()) != -1) {
            bos.write(readByte);
        }
        bis.close();
        bos.close();
        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);

        headSimpleObject(key, localFile.length(), Md5Utils.md5Hex(localFile));
        clearObject(key);
        assertTrue(localFile.delete());
    }
    
    @Test
    public void testAnonymousUrl() {
        if (!judgeUserInfoValid()) {
            return;
        }
        COSCredentials cred = new AnonymousCOSCredentials();
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        COSClient cosclient = new COSClient(cred, clientConfig);
        String bucketName = "mybucket-125110000";
        String key = "aaa.txt";

        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        URL url = cosclient.generatePresignedUrl(req);
        
        String urlStr = url.toString();
        // 匿名不包含签名
        assertEquals(false, urlStr.contains("sign="));
    }

}
