package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.CosHttpClient;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpResponseHandler;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.CosServiceResponse;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.agentbucket.ConflictResolutionStrategy;
import com.qcloud.cos.model.agentbucket.CopyObjectRecursiveRequest;
import com.qcloud.cos.model.agentbucket.DeleteObjectRecursiveRequest;
import com.qcloud.cos.model.agentbucket.DeleteObjectRecursiveResult;
import com.qcloud.cos.model.agentbucket.GetSpaceExtensionRequest;
import com.qcloud.cos.model.agentbucket.GetSpaceQuotaRequest;
import com.qcloud.cos.model.agentbucket.MoveObjectRequest;
import com.qcloud.cos.model.agentbucket.MoveObjectResult;
import com.qcloud.cos.model.agentbucket.PutSpaceExtensionRequest;
import com.qcloud.cos.model.agentbucket.PutSpaceQuotaRequest;
import com.qcloud.cos.model.agentbucket.SpaceExtensionResult;
import com.qcloud.cos.model.agentbucket.SpaceQuotaResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.BinaryUtils;
import com.qcloud.cos.utils.Md5Utils;

/**
 * AgentBucket 增强 API 离线测试：用桩 CosHttpClient 替换真实 HTTP 层，
 * 断言请求构造（方法/路径/参数/Header/Body/签名），并让桩响应走真实的 handler 链，
 * 端到端验证响应解析与 HTTP 200 + Error 转异常。无需凭证与网络。
 */
public class AgentBucketMockTest {
    private static final String BUCKET = "examplebucket-1250000000";
    private static final String REGION = "ap-guangzhou";
    private static final String HOST = BUCKET + ".cos." + REGION + ".myqcloud.com";
    private static final String REQUEST_ID = "NWQ1YmUwYTdfM2FiMDJhMDlfYzczN18zMGM1";

    private COSClient client;
    private StubHttpClient stub;

    /** 捕获请求并以给定响应走真实 handler 链的桩 */
    private static class StubHttpClient implements CosHttpClient {
        CosHttpRequest<?> lastRequest;
        String lastBody;
        String responseBody = "";
        Map<String, String> responseHeaders = new HashMap<String, String>();

        @Override
        public <X, Y extends CosServiceRequest> X exeute(CosHttpRequest<Y> request,
                HttpResponseHandler<CosServiceResponse<X>> responseHandler) {
            lastRequest = request;
            lastBody = readBody(request.getContent());
            CosHttpResponse response = new CosHttpResponse(request, null);
            response.setStatusCode(200);
            response.setContent(new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8)));
            response.setHeaders(new HashMap<String, String>(responseHeaders));
            try {
                return responseHandler.handle(response).getResult();
            } catch (CosClientException e) {
                throw e;
            } catch (Exception e) {
                throw new CosClientException("stub handle failed", e);
            }
        }

        private static String readBody(InputStream in) {
            if (in == null) {
                return null;
            }
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n;
                while ((n = in.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
                return new String(out.toByteArray(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void shutdown() {
        }
    }

    @Before
    public void setUp() throws Exception {
        client = new COSClient(new BasicCOSCredentials("AKIDfakeSecretId", "fakeSecretKey"),
                new ClientConfig(new Region(REGION)));
        stub = new StubHttpClient();
        stub.responseHeaders.put(Headers.REQUEST_ID, REQUEST_ID);
        Field field = COSClient.class.getDeclaredField("cosHttpClient");
        field.setAccessible(true);
        ((CosHttpClient) field.get(client)).shutdown();
        field.set(client, stub);
    }

    @After
    public void tearDown() {
        client.shutdown();
    }

    private Map<String, String> params() {
        return stub.lastRequest.getParameters();
    }

    private Map<String, String> headers() {
        return stub.lastRequest.getHeaders();
    }

    private void assertSignedParam(String param) {
        String auth = headers().get(Headers.COS_AUTHORIZATION);
        assertNotNull("请求必须签名", auth);
        assertTrue("签名 q-url-param-list 应包含 " + param + ": " + auth, auth.contains(param));
    }

    // ------------------------------ Space Quota ------------------------------

    @Test
    public void testPutSpaceQuotaRequest() throws Exception {
        stub.responseBody = "<SpaceQuota><QuotaId>1</QuotaId><Capacity>102400</Capacity></SpaceQuota>";
        SpaceQuotaResult result = client.putSpaceQuota(new PutSpaceQuotaRequest(BUCKET, "test-space", 102400L));

        assertEquals(HttpMethodName.PUT, stub.lastRequest.getHttpMethod());
        assertEquals("/test-space/", stub.lastRequest.getResourcePath());
        assertTrue(params().containsKey("space-quota"));
        assertNull(params().get("space-quota"));
        assertEquals(HOST, headers().get(Headers.HOST));
        assertEquals("application/xml", headers().get(Headers.CONTENT_TYPE));
        String expectedBody = "<SpaceQuota><Capacity>102400</Capacity></SpaceQuota>";
        assertEquals(expectedBody, stub.lastBody);
        assertEquals(String.valueOf(expectedBody.length()), headers().get(Headers.CONTENT_LENGTH));
        assertEquals(BinaryUtils.toBase64(Md5Utils.computeMD5Hash(expectedBody.getBytes(StandardCharsets.UTF_8))),
                headers().get(Headers.CONTENT_MD5));
        assertSignedParam("space-quota");

        assertEquals("1", result.getQuotaId());
        assertEquals(Long.valueOf(102400L), result.getCapacity());
        assertEquals(REQUEST_ID, result.getRequestId());
    }

    @Test
    public void testPutSpaceQuotaAcceptsTrailingSlash() {
        stub.responseBody = "<SpaceQuota><QuotaId>1</QuotaId><Capacity>0</Capacity></SpaceQuota>";
        client.putSpaceQuota(new PutSpaceQuotaRequest(BUCKET, "test-space/", 0L));
        assertEquals("/test-space/", stub.lastRequest.getResourcePath());
    }

    @Test
    public void testGetSpaceQuotaRequest() {
        stub.responseBody = "<SpaceQuota><QuotaId>12345</QuotaId><Capacity>102400</Capacity></SpaceQuota>";
        SpaceQuotaResult result = client.getSpaceQuota(new GetSpaceQuotaRequest(BUCKET, "test-space"));
        assertEquals(HttpMethodName.GET, stub.lastRequest.getHttpMethod());
        assertEquals("/test-space/", stub.lastRequest.getResourcePath());
        assertTrue(params().containsKey("space-quota"));
        assertNull(stub.lastBody);
        assertEquals("12345", result.getQuotaId());
        assertEquals(REQUEST_ID, result.getRequestId());
    }

    @Test
    public void testSpaceQuotaValidation() {
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.putSpaceQuota(new PutSpaceQuotaRequest(BUCKET, "test-space", -1L));
            }
        });
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.putSpaceQuota(new PutSpaceQuotaRequest().withBucketName(BUCKET).withSpaceId("s"));
            }
        });
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.getSpaceQuota(new GetSpaceQuotaRequest(BUCKET, "a/b"));
            }
        });
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.getSpaceQuota(new GetSpaceQuotaRequest(BUCKET, "/"));
            }
        });
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.getSpaceQuota(new GetSpaceQuotaRequest(BUCKET, null));
            }
        });
        assertNull("参数校验失败时不应发出请求", stub.lastRequest);
    }

    // ------------------------------ Space Extension ------------------------------

    @Test
    public void testPutSpaceExtensionRequest() {
        client.putSpaceExtension(new PutSpaceExtensionRequest(BUCKET, "test-space", "personal"));
        assertEquals(HttpMethodName.PUT, stub.lastRequest.getHttpMethod());
        assertEquals("/test-space/", stub.lastRequest.getResourcePath());
        assertTrue(params().containsKey("space-extension"));
        assertEquals("<SpaceExtension><SpaceTag>personal</SpaceTag></SpaceExtension>", stub.lastBody);
        assertNotNull(headers().get(Headers.CONTENT_MD5));
        assertSignedParam("space-extension");
    }

    @Test
    public void testPutSpaceExtensionValidation() {
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.putSpaceExtension(new PutSpaceExtensionRequest(BUCKET, "test-space", ""));
            }
        });
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.putSpaceExtension(new PutSpaceExtensionRequest(BUCKET, "test-space", null));
            }
        });
    }

    @Test
    public void testGetSpaceExtensionRequest() {
        stub.responseBody = "<SpaceExtension><IsPublicRead>true</IsPublicRead>"
                + "<RecognizeSensitiveContent>false</RecognizeSensitiveContent>"
                + "<SpaceTag>personal</SpaceTag></SpaceExtension>";
        SpaceExtensionResult result = client.getSpaceExtension(new GetSpaceExtensionRequest(BUCKET, "test-space"));
        assertEquals(HttpMethodName.GET, stub.lastRequest.getHttpMethod());
        assertEquals("/test-space/", stub.lastRequest.getResourcePath());
        assertTrue(params().containsKey("space-extension"));
        assertEquals(Boolean.TRUE, result.getIsPublicRead());
        assertEquals(Boolean.FALSE, result.getRecognizeSensitiveContent());
        assertEquals("personal", result.getSpaceTag());
        assertEquals(REQUEST_ID, result.getRequestId());
    }

    // ------------------------------ Copy Object Recursive ------------------------------

    @Test
    public void testCopyObjectRecursiveRequest() {
        stub.responseBody = "<CopyObjectResult><ETag>\"d-1780579801973809791\"</ETag>"
                + "<LastModified>2026-06-04T13:30:01Z</LastModified>"
                + "<CRC64>5404623458528365127</CRC64></CopyObjectResult>";
        CopyObjectResult result = client.copyObjectRecursive(
                new CopyObjectRecursiveRequest(BUCKET, "myspace/aaa", BUCKET, "myspace/bbb")
                        .withConflictResolutionStrategy(ConflictResolutionStrategy.RENAME));

        assertEquals(HttpMethodName.PUT, stub.lastRequest.getHttpMethod());
        assertEquals("/myspace/bbb", stub.lastRequest.getResourcePath());
        assertTrue(params().containsKey("recursive"));
        assertNull(params().get("recursive"));
        assertEquals("rename", params().get("conflict_resolution_strategy"));
        assertEquals(HOST + "/myspace/aaa", headers().get(Headers.COPY_SOURCE));
        assertEquals("0", headers().get(Headers.CONTENT_LENGTH));
        assertNull(stub.lastBody);
        assertSignedParam("recursive");

        assertEquals("d-1780579801973809791", result.getETag());
        assertNotNull(result.getLastModifiedDate());
        assertEquals("5404623458528365127", result.getCrc64Ecma());
        assertEquals(REQUEST_ID, result.getRequestId());
    }

    @Test
    public void testCopyObjectRecursiveSourceKeyEncoded() {
        stub.responseBody = "<CopyObjectResult><ETag>\"e\"</ETag></CopyObjectResult>";
        client.copyObjectRecursive(new CopyObjectRecursiveRequest(BUCKET, "my space/中文 dir", BUCKET, "dst"));
        assertEquals(HOST + "/my%20space/%E4%B8%AD%E6%96%87%20dir", headers().get(Headers.COPY_SOURCE));
        assertFalse("未设置策略时不传该参数", params().containsKey("conflict_resolution_strategy"));
    }

    @Test
    public void testCopyObjectRecursiveErrorIn200() {
        stub.responseBody = "<Error><Code>FileAlreadyExists</Code>"
                + "<Message>The destination directory already exists.</Message>"
                + "<Resource>/smhxxx-1250000000/myspace/bbb</Resource></Error>";
        try {
            client.copyObjectRecursive(new CopyObjectRecursiveRequest(BUCKET, "myspace/aaa", BUCKET, "myspace/bbb"));
            fail("HTTP 200 + Error 响应体必须抛出 CosServiceException");
        } catch (CosServiceException e) {
            assertEquals("FileAlreadyExists", e.getErrorCode());
            assertEquals(200, e.getStatusCode());
            assertEquals(REQUEST_ID, e.getRequestId());
            assertEquals(CosServiceException.ErrorType.Service, e.getErrorType());
            assertTrue(e.getErrorMessage().contains("The destination directory already exists."));
            assertTrue(e.getErrorMessage().contains("/smhxxx-1250000000/myspace/bbb"));
        }
    }

    @Test
    public void testCopyObjectRecursiveRejectOverwrite() {
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.copyObjectRecursive(new CopyObjectRecursiveRequest(BUCKET, "a", BUCKET, "b")
                        .withConflictResolutionStrategy(ConflictResolutionStrategy.OVERWRITE));
            }
        });
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.copyObjectRecursive(new CopyObjectRecursiveRequest(BUCKET, "/", BUCKET, "b"));
            }
        });
        assertNull(stub.lastRequest);
    }

    // ------------------------------ Move Object ------------------------------

    @Test
    public void testMoveObjectRequest() {
        stub.responseBody = "<MoveObjectResult><ETag>\"d-1780579801973809791\"</ETag>"
                + "<LastModified>2026-06-04T13:30:01Z</LastModified>"
                + "<CRC64>5404623458528365127</CRC64></MoveObjectResult>";
        MoveObjectResult result = client.moveObject(new MoveObjectRequest(BUCKET, "myspace/aaa", "myspace/bbb")
                .withConflictResolutionStrategy(ConflictResolutionStrategy.OVERWRITE));

        assertEquals(HttpMethodName.PUT, stub.lastRequest.getHttpMethod());
        assertEquals("/myspace/bbb", stub.lastRequest.getResourcePath());
        assertFalse("非递归 Move 不带 recursive", params().containsKey("recursive"));
        assertEquals("overwrite", params().get("conflict_resolution_strategy"));
        assertEquals(HOST + "/myspace/aaa", headers().get(Headers.MOVE_SOURCE));
        assertFalse(headers().containsKey(Headers.COPY_SOURCE));
        assertEquals("0", headers().get(Headers.CONTENT_LENGTH));

        assertEquals("d-1780579801973809791", result.getETag());
        assertEquals("2026-06-04T13:30:01Z", result.getLastModifiedStr());
        assertNotNull(result.getLastModified());
        assertEquals("5404623458528365127", result.getCrc64Ecma());
        assertEquals(REQUEST_ID, result.getRequestId());
    }

    @Test
    public void testMoveObjectRecursiveRequest() {
        stub.responseBody = "<MoveObjectResult><ETag>\"e\"</ETag></MoveObjectResult>";
        client.moveObjectRecursive(new MoveObjectRequest(BUCKET, "myspace/aaa", "myspace/bbb")
                .withConflictResolutionStrategy(ConflictResolutionStrategy.ASK));
        assertTrue(params().containsKey("recursive"));
        assertEquals("ask", params().get("conflict_resolution_strategy"));
        assertEquals(HOST + "/myspace/aaa", headers().get(Headers.MOVE_SOURCE));
        assertSignedParam("recursive");
    }

    @Test
    public void testMoveObjectRecursiveRejectOverwrite() {
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.moveObjectRecursive(new MoveObjectRequest(BUCKET, "a", "b")
                        .withConflictResolutionStrategy(ConflictResolutionStrategy.OVERWRITE));
            }
        });
        assertNull(stub.lastRequest);
    }

    @Test
    public void testMoveObjectRecursiveErrorIn200() {
        stub.responseBody = "<Error><Code>FileAlreadyExists</Code>"
                + "<Message>Recursive move operation failed: File already exists.</Message>"
                + "<Resource>/smhxxx-1250000000/myspace/bbb</Resource></Error>";
        try {
            client.moveObjectRecursive(new MoveObjectRequest(BUCKET, "myspace/ccc", "myspace/bbb"));
            fail("HTTP 200 + Error 响应体必须抛出 CosServiceException");
        } catch (CosServiceException e) {
            assertEquals("FileAlreadyExists", e.getErrorCode());
            assertEquals(200, e.getStatusCode());
            assertEquals(REQUEST_ID, e.getRequestId());
        }
    }

    @Test
    public void testErrorBodyRequestIdPreferred() {
        stub.responseBody = "<Error><Code>InternalError</Code><Message>m</Message>"
                + "<RequestId>body-request-id</RequestId></Error>";
        try {
            client.moveObject(new MoveObjectRequest(BUCKET, "a", "b"));
            fail("expect CosServiceException");
        } catch (CosServiceException e) {
            assertEquals("body-request-id", e.getRequestId());
        }
    }

    // ------------------------------ Delete Object Recursive ------------------------------

    @Test
    public void testDeleteObjectRecursiveRequest() {
        stub.responseBody = "<DeleteResult><Deleted><Key>myspace/bbb</Key></Deleted>"
                + "<Deleted><Key>myspace/bbb/a.txt</Key></Deleted></DeleteResult>";
        DeleteObjectRecursiveResult result =
                client.deleteObjectRecursive(new DeleteObjectRecursiveRequest(BUCKET, "myspace/bbb"));
        assertEquals(HttpMethodName.DELETE, stub.lastRequest.getHttpMethod());
        assertEquals("/myspace/bbb", stub.lastRequest.getResourcePath());
        assertTrue(params().containsKey("recursive"));
        assertSignedParam("recursive");
        assertEquals(2, result.getDeletedKeys().size());
        assertEquals("myspace/bbb", result.getDeletedKeys().get(0));
        assertFalse(result.hasErrors());
        assertEquals(REQUEST_ID, result.getRequestId());
    }

    @Test
    public void testDeleteObjectRecursiveErrorIn200() {
        stub.responseBody = "<Error><Code>InternalError</Code><Message>delete failed</Message></Error>";
        try {
            client.deleteObjectRecursive(new DeleteObjectRecursiveRequest(BUCKET, "myspace/bbb"));
            fail("HTTP 200 + Error 响应体必须抛出 CosServiceException");
        } catch (CosServiceException e) {
            assertEquals("InternalError", e.getErrorCode());
            assertEquals(200, e.getStatusCode());
            assertEquals(REQUEST_ID, e.getRequestId());
        }
    }

    @Test
    public void testDeleteObjectRecursiveRejectRoot() {
        for (final String key : new String[] {"/", "//", ""}) {
            expectIllegalArgument(new Runnable() {
                public void run() {
                    client.deleteObjectRecursive(new DeleteObjectRecursiveRequest(BUCKET, key));
                }
            });
        }
        expectIllegalArgument(new Runnable() {
            public void run() {
                client.deleteObjectRecursive(new DeleteObjectRecursiveRequest(BUCKET, null));
            }
        });
        assertNull("防误删：不应发出任何请求", stub.lastRequest);
    }

    private static void expectIllegalArgument(Runnable runnable) {
        try {
            runnable.run();
            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
