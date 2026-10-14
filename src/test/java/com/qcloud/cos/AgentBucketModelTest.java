package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.internal.RequestIdHeaderHandler;
import com.qcloud.cos.internal.XmlResponsesSaxParser;
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

/**
 * AgentBucket 模型类单测：构造器、setter/getter、链式 withXxx、toString、序列化，以及 RequestIdHeaderHandler 的边界。
 * 无需凭证与网络。
 */
public class AgentBucketModelTest {
    private static final String BUCKET = "examplebucket-1250000000";

    @Test
    public void testConflictResolutionStrategy() {
        assertEquals("ask", ConflictResolutionStrategy.ASK.getValue());
        assertEquals("rename", ConflictResolutionStrategy.RENAME.getValue());
        assertEquals("overwrite", ConflictResolutionStrategy.OVERWRITE.getValue());
        assertEquals("rename", ConflictResolutionStrategy.RENAME.toString());
        assertEquals(3, ConflictResolutionStrategy.values().length);
    }

    @Test
    public void testSpaceQuotaRequests() {
        PutSpaceQuotaRequest put = new PutSpaceQuotaRequest();
        assertSame(put, put.withBucketName(BUCKET).withSpaceId("space").withCapacity(100L));
        assertEquals(BUCKET, put.getBucketName());
        assertEquals("space", put.getSpaceId());
        assertEquals(Long.valueOf(100L), put.getCapacity());
        put.setBucketName("b");
        put.setSpaceId("s");
        put.setCapacity(null);
        assertEquals("b", put.getBucketName());
        assertEquals("s", put.getSpaceId());
        assertNull(put.getCapacity());
        assertEquals(Long.valueOf(1L), new PutSpaceQuotaRequest(BUCKET, "s", 1L).getCapacity());

        GetSpaceQuotaRequest get = new GetSpaceQuotaRequest();
        assertSame(get, get.withBucketName(BUCKET).withSpaceId("space"));
        assertEquals(BUCKET, get.getBucketName());
        assertEquals("space", get.getSpaceId());
        get.setBucketName("b");
        get.setSpaceId("s");
        assertEquals("b", get.getBucketName());
        assertEquals("s", get.getSpaceId());
        assertEquals("s2", new GetSpaceQuotaRequest(BUCKET, "s2").getSpaceId());
    }

    @Test
    public void testSpaceExtensionRequests() {
        PutSpaceExtensionRequest put = new PutSpaceExtensionRequest();
        assertSame(put, put.withBucketName(BUCKET).withSpaceId("space").withSpaceTag("personal"));
        assertEquals(BUCKET, put.getBucketName());
        assertEquals("space", put.getSpaceId());
        assertEquals("personal", put.getSpaceTag());
        put.setBucketName("b");
        put.setSpaceId("s");
        put.setSpaceTag("team");
        assertEquals("b", put.getBucketName());
        assertEquals("s", put.getSpaceId());
        assertEquals("team", put.getSpaceTag());
        assertEquals("t", new PutSpaceExtensionRequest(BUCKET, "s", "t").getSpaceTag());

        GetSpaceExtensionRequest get = new GetSpaceExtensionRequest();
        assertSame(get, get.withBucketName(BUCKET).withSpaceId("space"));
        assertEquals(BUCKET, get.getBucketName());
        assertEquals("space", get.getSpaceId());
        get.setBucketName("b");
        get.setSpaceId("s");
        assertEquals("b", get.getBucketName());
        assertEquals("s", get.getSpaceId());
        assertEquals("s2", new GetSpaceExtensionRequest(BUCKET, "s2").getSpaceId());
    }

    @Test
    public void testCopyObjectRecursiveRequest() {
        CopyObjectRecursiveRequest request = new CopyObjectRecursiveRequest();
        assertSame(request, request.withSourceBucketName("src-1250000000").withSourceKey("a")
                .withDestinationBucketName("dst-1250000000").withDestinationKey("b")
                .withConflictResolutionStrategy(ConflictResolutionStrategy.ASK));
        assertEquals("src-1250000000", request.getSourceBucketName());
        assertEquals("a", request.getSourceKey());
        assertEquals("dst-1250000000", request.getDestinationBucketName());
        assertEquals("b", request.getDestinationKey());
        assertEquals(ConflictResolutionStrategy.ASK, request.getConflictResolutionStrategy());
        request.setSourceBucketName("s");
        request.setSourceKey("sk");
        request.setDestinationBucketName("d");
        request.setDestinationKey("dk");
        request.setConflictResolutionStrategy(null);
        assertEquals("s", request.getSourceBucketName());
        assertEquals("sk", request.getSourceKey());
        assertEquals("d", request.getDestinationBucketName());
        assertEquals("dk", request.getDestinationKey());
        assertNull(request.getConflictResolutionStrategy());
    }

    @Test
    public void testMoveObjectRequest() {
        MoveObjectRequest request = new MoveObjectRequest();
        assertSame(request, request.withBucketName(BUCKET).withSourceKey("a").withDestinationKey("b")
                .withConflictResolutionStrategy(ConflictResolutionStrategy.OVERWRITE));
        assertEquals(BUCKET, request.getBucketName());
        assertEquals("a", request.getSourceKey());
        assertEquals("b", request.getDestinationKey());
        assertEquals(ConflictResolutionStrategy.OVERWRITE, request.getConflictResolutionStrategy());
        request.setBucketName("x");
        request.setSourceKey("y");
        request.setDestinationKey("z");
        request.setConflictResolutionStrategy(ConflictResolutionStrategy.RENAME);
        assertEquals("x", request.getBucketName());
        assertEquals("y", request.getSourceKey());
        assertEquals("z", request.getDestinationKey());
        assertEquals(ConflictResolutionStrategy.RENAME, request.getConflictResolutionStrategy());
    }

    @Test
    public void testDeleteObjectRecursiveRequest() {
        DeleteObjectRecursiveRequest request = new DeleteObjectRecursiveRequest();
        assertSame(request, request.withBucketName(BUCKET).withKey("dir/"));
        assertEquals(BUCKET, request.getBucketName());
        assertEquals("dir/", request.getKey());
        request.setBucketName("b");
        request.setKey("k");
        assertEquals("b", request.getBucketName());
        assertEquals("k", request.getKey());
    }

    @Test
    public void testResultsToString() {
        SpaceQuotaResult quota = new SpaceQuotaResult();
        quota.setQuotaId("q1");
        quota.setCapacity(10L);
        quota.setRequestId("rid");
        assertEquals("SpaceQuotaResult{quotaId='q1', capacity=10, requestId='rid'}", quota.toString());

        SpaceExtensionResult extension = new SpaceExtensionResult();
        extension.setIsPublicRead(Boolean.TRUE);
        extension.setRecognizeSensitiveContent(Boolean.FALSE);
        extension.setSpaceTag("personal");
        assertTrue(extension.toString().contains("isPublicRead=true"));
        assertTrue(extension.toString().contains("spaceTag='personal'"));

        MoveObjectResult move = new MoveObjectResult();
        Date now = new Date();
        move.setETag("e");
        move.setLastModified(now);
        move.setLastModifiedStr("2026-06-04T13:30:01Z");
        move.setCrc64Ecma("123");
        assertEquals(now, move.getLastModified());
        assertTrue(move.toString().contains("eTag='e'"));
        assertTrue(move.toString().contains("crc64Ecma='123'"));
    }

    @Test
    public void testDeleteObjectRecursiveResult() {
        DeleteObjectRecursiveResult result = new DeleteObjectRecursiveResult();
        assertFalse(result.hasErrors());
        result.setDeletedKeys(null);
        result.setErrors(null);
        assertNotNull("null 被规整为空列表", result.getDeletedKeys());
        assertNotNull(result.getErrors());

        DeleteObjectRecursiveResult.DeleteError error = new DeleteObjectRecursiveResult.DeleteError();
        error.setKey("k");
        error.setCode("AccessDenied");
        error.setMessage("denied");
        assertEquals("DeleteError{key='k', code='AccessDenied', message='denied'}", error.toString());
        result.setDeletedKeys(Arrays.asList("a", "b"));
        result.setErrors(Collections.singletonList(error));
        assertTrue(result.hasErrors());
        assertEquals("DeleteObjectRecursiveResult{deletedKeys=2, errors=1, requestId='null'}", result.toString());
    }

    @Test
    public void testModelsSerializable() throws Exception {
        MoveObjectRequest request = new MoveObjectRequest(BUCKET, "a", "b")
                .withConflictResolutionStrategy(ConflictResolutionStrategy.RENAME);
        MoveObjectRequest copy = roundTrip(request);
        assertEquals("a", copy.getSourceKey());
        assertEquals(ConflictResolutionStrategy.RENAME, copy.getConflictResolutionStrategy());

        DeleteObjectRecursiveResult result = new DeleteObjectRecursiveResult();
        DeleteObjectRecursiveResult.DeleteError error = new DeleteObjectRecursiveResult.DeleteError();
        error.setKey("k");
        result.setErrors(Collections.singletonList(error));
        assertEquals("k", roundTrip(result).getErrors().get(0).getKey());
    }

    @Test
    public void testRequestIdHeaderHandler() {
        RequestIdHeaderHandler<XmlResponsesSaxParser.DeleteObjectRecursiveHandler> handler =
                new RequestIdHeaderHandler<XmlResponsesSaxParser.DeleteObjectRecursiveHandler>();
        XmlResponsesSaxParser.DeleteObjectRecursiveHandler result = new XmlResponsesSaxParser.DeleteObjectRecursiveHandler();

        handler.handle(null, new CosHttpResponse(null, null));
        handler.handle(result, null);
        assertNull(result.getHeaderRequestId());

        CosHttpResponse noHeaders = new CosHttpResponse(null, null);
        noHeaders.setHeaders(null);
        handler.handle(result, noHeaders);
        assertNull(result.getHeaderRequestId());

        CosHttpResponse response = new CosHttpResponse(null, null);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(Headers.REQUEST_ID, "rid-1");
        response.setHeaders(headers);
        handler.handle(result, response);
        assertEquals("rid-1", result.getHeaderRequestId());
    }

    @SuppressWarnings("unchecked")
    private static <T> T roundTrip(T object) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);
        out.writeObject(object);
        out.close();
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()));
        return (T) in.readObject();
    }
}
