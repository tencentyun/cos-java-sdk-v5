package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.region.Region;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CosServiceExceptionTest {
    private static String bucketName = System.getenv("ErrBucket");
    private static String appid = System.getenv("ErrAppid");
    private static String region = System.getenv("ErrRegion");
    private static String errServerIp = System.getenv("ERR_HOST");

    private static COSClient cosClient = createCosClient(3);
    private static COSClient cosClientWithoutRetry = createCosClient(0);

    private static COSClient createCosClient(int maxErrorRetry) {
        String secretId = System.getenv("secretId");
        String secretKey = System.getenv("secretKey");
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId,secretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.http);
        clientConfig.setMaxErrorRetry(maxErrorRetry);
        // 生成cos客户端
        return new COSClient(cred, clientConfig);
    }

    @Test
    public void test5xxWithoutRetry() {
        /**
         * 500
         * */
        CosServiceException cse = new CosServiceException("");
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "500r");
        try {
            COSObject cosObject = cosClientWithoutRetry.getObject(getObjectRequest);
        } catch (CosServiceException e) {
            cse = e;
        }
        assertEquals(500, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "500");
        try {
            COSObject cosObject = cosClientWithoutRetry.getObject(getObjectRequest);
        } catch (CosServiceException e) {
            cse = e;
        }
        assertEquals(500, cse.getStatusCode());
        assertNull(cse.getRequestId());

        /**
         * 503
         * */
        getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "503r");
        try {
            COSObject cosObject = cosClientWithoutRetry.getObject(getObjectRequest);
        } catch (CosServiceException e) {
            cse = e;
        }
        assertEquals(503, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "503");
        try {
            COSObject cosObject = cosClientWithoutRetry.getObject(getObjectRequest);
        } catch (CosServiceException e) {
            cse = e;
        }
        assertEquals(503, cse.getStatusCode());
        assertNull(cse.getRequestId());

        /**
         * 504
         * */
        getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "504r");
        try {
            COSObject cosObject = cosClientWithoutRetry.getObject(getObjectRequest);
        } catch (CosServiceException e) {
            cse = e;
        }
        assertEquals(504, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "504");
        try {
            COSObject cosObject = cosClientWithoutRetry.getObject(getObjectRequest);
        } catch (CosServiceException e) {
            cse = e;
        }
        assertEquals(504, cse.getStatusCode());
        assertNull(cse.getRequestId());
    }

    private static void testGetObjectWithRetry(String key, String serverip) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, key);
        if (serverip != null && !serverip.isEmpty()) {
            getObjectRequest.setFixedEndpointAddr(serverip);
        }
        try {
            COSObject cosObject = cosClient.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException cse) {
            fail(cse.getErrorMessage() + cse.getRequestId());
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    private static void testGetObjectWithChangeHostRetry(String key, String serverip) {
        COSClient client = createCosClient(1);
        client.getClientConfig().setChangeEndpointRetry(true);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, key);
        if (serverip != null && !serverip.isEmpty()) {
            getObjectRequest.setFixedEndpointAddr(serverip);
        }
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException cse) {
            fail(cse.getErrorMessage() + cse.getRequestId());
        } catch (IOException e) {
            fail(e.getMessage());
        } finally {
            client.shutdown();
        }
    }

    @Test
    public void test5xxWithRetryNotChangeHost() {
        /**
         * 500
         * */
        testGetObjectWithRetry("500r", "");
        testGetObjectWithRetry("500r", errServerIp);

        testGetObjectWithRetry("500", "");
        testGetObjectWithRetry("500", errServerIp);

        /**
         * 503
         * */
        testGetObjectWithRetry("503r", "");
        testGetObjectWithRetry("503r", errServerIp);

        testGetObjectWithRetry("503", "");
        testGetObjectWithRetry("503", errServerIp);

        /**
         * 504
         * */
        testGetObjectWithRetry("504r", "");
        testGetObjectWithRetry("504r", errServerIp);

        testGetObjectWithRetry("504", "");
        testGetObjectWithRetry("504", errServerIp);
    }

    @Test
    public void test5xxWithRetryChangeHost() {
        /**
         * 500
         * */
        testGetObjectWithChangeHostRetry("500r", "");
        testGetObjectWithChangeHostRetry("500r", errServerIp);

        testGetObjectWithChangeHostRetry("500", "");
        testGetObjectWithChangeHostRetry("500", errServerIp);

        /**
         * 503
         * */
        testGetObjectWithChangeHostRetry("503r", "");
        testGetObjectWithChangeHostRetry("503r", errServerIp);

        testGetObjectWithChangeHostRetry("503", "");
        testGetObjectWithChangeHostRetry("503", errServerIp);

        /**
         * 504
         * */
        testGetObjectWithChangeHostRetry("504r", "");
        testGetObjectWithChangeHostRetry("504r", errServerIp);

        testGetObjectWithChangeHostRetry("504", "");
        testGetObjectWithChangeHostRetry("504", errServerIp);
    }

    @Test
    public void test302Exception() {
        /**
         * 302 no request id, default host, setChangeEndpointRetry(true)
         * */
        COSClient client = createCosClient(3);
        client.getClientConfig().setChangeEndpointRetry(true);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "302");
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException cse) {
            fail(cse.getErrorMessage() + cse.getRequestId());
        } catch (IOException e) {
            fail(e.getMessage());
        }

        /**
         * 302 no request id, not default host, setChangeEndpointRetry(true)
         * */
        CosServiceException cse = new CosServiceException("");
        getObjectRequest.setFixedEndpointAddr(errServerIp);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(302, cse.getStatusCode());
        assertNull(cse.getRequestId());

        /**
         * 302 no request id, default host, setChangeEndpointRetry(false)
         * */
        client.getClientConfig().setChangeEndpointRetry(false);
        getObjectRequest.setFixedEndpointAddr(null);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(302, cse.getStatusCode());
        assertNull(cse.getRequestId());

        /**
         * 302 no request id, not default host, setChangeEndpointRetry(false)
         * */
        getObjectRequest.setFixedEndpointAddr(errServerIp);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(302, cse.getStatusCode());
        assertNull(cse.getRequestId());

        /**
         * 302 with request id, default host, setChangeEndpointRetry(true)
         * */
        client.getClientConfig().setChangeEndpointRetry(true);
        getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "302r");
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(302, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        /**
         * 302 with request id, not default host, setChangeEndpointRetry(true)
         * */
        getObjectRequest.setFixedEndpointAddr(errServerIp);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(302, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        /**
         * 302 with request id, default host, setChangeEndpointRetry(false)
         * */
        client.getClientConfig().setChangeEndpointRetry(false);
        getObjectRequest.setFixedEndpointAddr(null);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(302, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        /**
         * 302 with request id, not default host, setChangeEndpointRetry(false)
         * */
        getObjectRequest.setFixedEndpointAddr(errServerIp);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(302, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        // shutdown cos client
        client.shutdown();
    }

    @Test
    public void test301Exception() {
        /**
         * 301 no request id, default host, setChangeEndpointRetry(true)
         * */
        COSClient client = createCosClient(3);
        client.getClientConfig().setChangeEndpointRetry(true);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "301");
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException cse) {
            fail(cse.getErrorMessage() + cse.getRequestId());
        } catch (IOException e) {
            fail(e.getMessage());
        }

        /**
         * 301 no request id, not default host, setChangeEndpointRetry(true)
         * */
        CosServiceException cse = new CosServiceException("");
        getObjectRequest.setFixedEndpointAddr(errServerIp);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(301, cse.getStatusCode());
        assertNull(cse.getRequestId());

        /**
         * 301 no request id, default host, setChangeEndpointRetry(false)
         * */
        client.getClientConfig().setChangeEndpointRetry(false);
        getObjectRequest.setFixedEndpointAddr(null);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(301, cse.getStatusCode());
        assertNull(cse.getRequestId());

        /**
         * 301 no request id, not default host, setChangeEndpointRetry(false)
         * */
        getObjectRequest.setFixedEndpointAddr(errServerIp);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(301, cse.getStatusCode());
        assertNull(cse.getRequestId());

        /**
         * 301 with request id, default host, setChangeEndpointRetry(true)
         * */
        client.getClientConfig().setChangeEndpointRetry(true);
        getObjectRequest = new GetObjectRequest(bucketName + "-" + appid, "301r");
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(301, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        /**
         * 301 with request id, not default host, setChangeEndpointRetry(true)
         * */
        getObjectRequest.setFixedEndpointAddr(errServerIp);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(301, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        /**
         * 301 with request id, default host, setChangeEndpointRetry(false)
         * */
        client.getClientConfig().setChangeEndpointRetry(false);
        getObjectRequest.setFixedEndpointAddr(null);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(301, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        /**
         * 301 with request id, not default host, setChangeEndpointRetry(false)
         * */
        getObjectRequest.setFixedEndpointAddr(errServerIp);
        try {
            COSObject cosObject = client.getObject(getObjectRequest);
            cosObject.close();
        } catch (CosServiceException e) {
            cse = e;
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertEquals(301, cse.getStatusCode());
        assertNotNull(cse.getRequestId());

        // shutdown cos client
        client.shutdown();
    }
}
