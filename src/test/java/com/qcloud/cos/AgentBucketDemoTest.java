package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.demo.AgentBucketDemo;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.region.Region;

/**
 * AgentBucketDemo 离线验证：用 HTTP 层桩逐个运行 demo 中的示例方法，保证示例代码可运行、请求正确。
 * 不发起任何网络请求，无需凭证。
 */
public class AgentBucketDemoTest {
    private static final String COPY_RESULT = "<CopyObjectResult><ETag>\"e\"</ETag>"
            + "<LastModified>2026-06-04T13:30:01Z</LastModified><CRC64>1</CRC64></CopyObjectResult>";
    private static final String MOVE_RESULT = "<MoveObjectResult><ETag>\"e\"</ETag>"
            + "<LastModified>2026-06-04T13:30:01Z</LastModified><CRC64>1</CRC64></MoveObjectResult>";
    private static final String CONFLICT = "<Error><Code>FileAlreadyExists</Code>"
            + "<Message>Recursive move operation failed: File already exists.</Message></Error>";

    private COSClient client;
    private AgentBucketStubHttpClient stub;

    @Before
    public void setUp() throws Exception {
        client = new COSClient(new BasicCOSCredentials("AKIDfakeSecretId", "fakeSecretKey"),
                new ClientConfig(new Region("ap-chengdu")));
        stub = AgentBucketStubHttpClient.install(client);
        stub.responseHeaders.put(Headers.REQUEST_ID, "demo-request-id");
    }

    @After
    public void tearDown() {
        client.shutdown();
    }

    private void run(String method) throws Exception {
        Method m = AgentBucketDemo.class.getDeclaredMethod(method, COSClient.class);
        m.setAccessible(true);
        try {
            m.invoke(null, client);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Exception) {
                throw (Exception) e.getCause();
            }
            throw e;
        }
    }

    @Test
    public void testCreateSpace() throws Exception {
        run("createSpace");
        assertEquals(HttpMethodName.PUT, stub.lastRequest.getHttpMethod());
        assertEquals("/my-space/", stub.lastRequest.getResourcePath());
    }

    @Test
    public void testSpaceQuotaAndExtension() throws Exception {
        stub.responseBody = "<SpaceQuota><QuotaId>1</QuotaId><Capacity>1099511627776</Capacity></SpaceQuota>";
        run("putGetSpaceQuota");
        assertEquals(2, stub.requests.size());

        stub.enqueue("").enqueue("<SpaceExtension><IsPublicRead>false</IsPublicRead>"
                + "<RecognizeSensitiveContent>false</RecognizeSensitiveContent>"
                + "<SpaceTag>personal</SpaceTag></SpaceExtension>");
        run("putGetSpaceExtension");
        assertEquals(4, stub.requests.size());
        assertTrue(stub.requests.get(2).getParameters().containsKey("space-extension"));
    }

    @Test
    public void testCopyAndMove() throws Exception {
        stub.responseBody = COPY_RESULT;
        run("copyObjectRecursive");
        assertEquals("rename", stub.lastRequest.getParameters().get("conflict_resolution_strategy"));

        stub.responseBody = MOVE_RESULT;
        run("moveObject");
        assertEquals("overwrite", stub.lastRequest.getParameters().get("conflict_resolution_strategy"));

        run("moveObjectRecursive");
        assertTrue(stub.lastRequest.getParameters().containsKey("recursive"));
    }

    @Test
    public void testMoveObjectRecursiveConflictHandled() throws Exception {
        stub.responseBody = CONFLICT;
        run("moveObjectRecursive");
    }

    @Test
    public void testMoveObjectRecursiveOtherErrorRethrown() throws Exception {
        stub.responseBody = "<Error><Code>InternalError</Code><Message>m</Message></Error>";
        try {
            run("moveObjectRecursive");
            fail("非冲突错误应继续抛出");
        } catch (CosServiceException e) {
            assertEquals("InternalError", e.getErrorCode());
        }
    }

    @Test
    public void testDeleteObjectRecursive() throws Exception {
        stub.responseBody = "<DeleteResult><Deleted><Key>my-space/ccc/</Key></Deleted></DeleteResult>";
        run("deleteObjectRecursive");
        assertEquals(HttpMethodName.DELETE, stub.lastRequest.getHttpMethod());
        assertEquals("/my-space/ccc/", stub.lastRequest.getResourcePath());

        stub.responseBody = "<DeleteResult><Error><Key>my-space/ccc/a</Key><Code>AccessDenied</Code>"
                + "<Message>denied</Message></Error></DeleteResult>";
        run("deleteObjectRecursive");
    }

    @Test
    public void testMainWithoutCredentials() {
        Assume.assumeTrue("配置了 COS_SECRET_ID 时跳过，避免 demo 发起真实请求",
                System.getenv("COS_SECRET_ID") == null && System.getenv("COS_SECRET_KEY") == null);
        try {
            AgentBucketDemo.main(new String[0]);
            fail("未配置密钥时应提示密钥缺失");
        } catch (IllegalArgumentException expected) {
            assertTrue(expected.getMessage().contains("cannot be null"));
        }
    }
}
