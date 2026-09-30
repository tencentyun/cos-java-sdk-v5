package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.qcloud.cos.internal.XmlResponsesSaxParser;
import com.qcloud.cos.model.agentbucket.AgentBucketXmlFactory;
import com.qcloud.cos.model.agentbucket.PutSpaceExtensionRequest;
import com.qcloud.cos.model.agentbucket.PutSpaceQuotaRequest;
import com.qcloud.cos.model.agentbucket.SpaceExtensionResult;
import com.qcloud.cos.model.agentbucket.SpaceQuotaResult;

/**
 * AgentBucket 增强 API 的 XML 解析与生成单测，样例取自官方文档 436/133858 各子页。无需凭证。
 */
public class AgentBucketXmlParseTest {

    private static InputStream xml(String s) {
        return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testParseSpaceQuota() throws Exception {
        SpaceQuotaResult result = new XmlResponsesSaxParser().parseSpaceQuotaResponse(xml(
                "<SpaceQuota>\n    <QuotaId>12345</QuotaId>\n    <Capacity>102400</Capacity>\n</SpaceQuota>"))
                .getResult();
        assertEquals("12345", result.getQuotaId());
        assertEquals(Long.valueOf(102400L), result.getCapacity());
    }

    @Test
    public void testParseSpaceQuotaInt64Max() throws Exception {
        SpaceQuotaResult result = new XmlResponsesSaxParser().parseSpaceQuotaResponse(xml(
                "<SpaceQuota><QuotaId>1</QuotaId><Capacity>9223372036854775807</Capacity></SpaceQuota>"))
                .getResult();
        assertEquals(Long.valueOf(Long.MAX_VALUE), result.getCapacity());
    }

    @Test
    public void testParseSpaceQuotaInvalidCapacity() throws Exception {
        SpaceQuotaResult result = new XmlResponsesSaxParser().parseSpaceQuotaResponse(xml(
                "<SpaceQuota><QuotaId>1</QuotaId><Capacity>abc</Capacity></SpaceQuota>")).getResult();
        assertEquals("1", result.getQuotaId());
        assertNull(result.getCapacity());
    }

    @Test
    public void testParseSpaceQuotaEmptyBody() throws Exception {
        SpaceQuotaResult result = new XmlResponsesSaxParser().parseSpaceQuotaResponse(xml("")).getResult();
        assertNull(result.getQuotaId());
        assertNull(result.getCapacity());
    }

    @Test
    public void testParseSpaceExtension() throws Exception {
        SpaceExtensionResult result = new XmlResponsesSaxParser().parseSpaceExtensionResponse(xml(
                "<SpaceExtension>\n    <IsPublicRead>false</IsPublicRead>\n"
                        + "    <RecognizeSensitiveContent>true</RecognizeSensitiveContent>\n"
                        + "    <SpaceTag>personal</SpaceTag>\n</SpaceExtension>")).getResult();
        assertEquals(Boolean.FALSE, result.getIsPublicRead());
        assertEquals(Boolean.TRUE, result.getRecognizeSensitiveContent());
        assertEquals("personal", result.getSpaceTag());
    }

    @Test
    public void testParseSpaceExtensionMissingFields() throws Exception {
        SpaceExtensionResult result = new XmlResponsesSaxParser().parseSpaceExtensionResponse(xml(
                "<SpaceExtension><SpaceTag>team</SpaceTag></SpaceExtension>")).getResult();
        assertNull(result.getIsPublicRead());
        assertNull(result.getRecognizeSensitiveContent());
        assertEquals("team", result.getSpaceTag());
    }

    @Test
    public void testParseCopyObjectRecursiveSuccess() throws Exception {
        XmlResponsesSaxParser.AgentObjectOperationResultHandler handler = new XmlResponsesSaxParser()
                .parseAgentObjectOperationResponse(xml("<CopyObjectResult>\n"
                        + "  <ETag>\"d-1780579801973809791\"</ETag>\n"
                        + "  <LastModified>2026-06-04T13:30:01Z</LastModified>\n"
                        + "  <CRC64>5404623458528365127</CRC64>\n</CopyObjectResult>"));
        assertFalse(handler.isErrorResponse());
        assertEquals("d-1780579801973809791", handler.getETag());
        assertEquals("2026-06-04T13:30:01Z", handler.getLastModifiedStr());
        assertNotNull(handler.getLastModified());
        assertEquals(1780579801000L, handler.getLastModified().getTime());
        assertEquals("5404623458528365127", handler.getCrc64Ecma());
    }

    @Test
    public void testParseMoveObjectSuccess() throws Exception {
        XmlResponsesSaxParser.AgentObjectOperationResultHandler handler = new XmlResponsesSaxParser()
                .parseAgentObjectOperationResponse(xml("<MoveObjectResult>\n"
                        + "    <ETag>\"etag-value\"</ETag>\n"
                        + "    <LastModified>2025-01-15T10:30:00Z</LastModified>\n"
                        + "    <CRC64>123</CRC64>\n</MoveObjectResult>"));
        assertFalse(handler.isErrorResponse());
        assertEquals("etag-value", handler.getETag());
        assertNotNull(handler.getLastModified());
        assertEquals("123", handler.getCrc64Ecma());
    }

    @Test
    public void testParseObjectOperationInvalidDate() throws Exception {
        XmlResponsesSaxParser.AgentObjectOperationResultHandler handler = new XmlResponsesSaxParser()
                .parseAgentObjectOperationResponse(xml(
                        "<MoveObjectResult><LastModified>not-a-date</LastModified></MoveObjectResult>"));
        assertEquals("not-a-date", handler.getLastModifiedStr());
        assertNull(handler.getLastModified());
    }

    @Test
    public void testParseObjectOperationErrorBody() throws Exception {
        XmlResponsesSaxParser.AgentObjectOperationResultHandler handler = new XmlResponsesSaxParser()
                .parseAgentObjectOperationResponse(xml("<Error>\n"
                        + "  <Code>FileAlreadyExists</Code>\n"
                        + "  <Message>Recursive move operation failed: File already exists.</Message>\n"
                        + "  <Resource>/smhxxx-1250000000/myspace/bbb</Resource>\n</Error>"));
        assertTrue(handler.isErrorResponse());
        assertEquals("FileAlreadyExists", handler.getErrorCode());
        assertEquals("Recursive move operation failed: File already exists.", handler.getErrorMessage());
        assertEquals("/smhxxx-1250000000/myspace/bbb", handler.getErrorResource());
        assertNull(handler.getErrorRequestId());
        assertNull(handler.getETag());
    }

    @Test
    public void testParseObjectOperationEmptyBody() throws Exception {
        XmlResponsesSaxParser.AgentObjectOperationResultHandler handler =
                new XmlResponsesSaxParser().parseAgentObjectOperationResponse(xml(""));
        assertFalse(handler.isErrorResponse());
        assertNull(handler.getETag());
    }

    @Test
    public void testParseDeleteObjectRecursive() throws Exception {
        XmlResponsesSaxParser.DeleteObjectRecursiveHandler handler = new XmlResponsesSaxParser()
                .parseDeleteObjectRecursiveResponse(xml("<DeleteResult>\n"
                        + "    <Deleted><Key>myspace/bbb</Key></Deleted>\n"
                        + "    <Deleted><Key>myspace/bbb/a.txt</Key></Deleted>\n</DeleteResult>"));
        assertFalse(handler.isErrorResponse());
        assertEquals(2, handler.getDeletedKeys().size());
        assertEquals("myspace/bbb", handler.getDeletedKeys().get(0));
        assertEquals("myspace/bbb/a.txt", handler.getDeletedKeys().get(1));
        assertTrue(handler.getErrors().isEmpty());
    }

    @Test
    public void testParseDeleteObjectRecursivePartialError() throws Exception {
        XmlResponsesSaxParser.DeleteObjectRecursiveHandler handler = new XmlResponsesSaxParser()
                .parseDeleteObjectRecursiveResponse(xml("<DeleteResult>"
                        + "<Deleted><Key>a/1</Key></Deleted>"
                        + "<Error><Key>a/2</Key><Code>AccessDenied</Code><Message>denied</Message></Error>"
                        + "</DeleteResult>"));
        assertFalse("DeleteResult 下的 Error 是逐条失败，不是整体失败", handler.isErrorResponse());
        assertEquals(1, handler.getDeletedKeys().size());
        assertEquals(1, handler.getErrors().size());
        assertEquals("a/2", handler.getErrors().get(0).getKey());
        assertEquals("AccessDenied", handler.getErrors().get(0).getCode());
        assertEquals("denied", handler.getErrors().get(0).getMessage());
    }

    @Test
    public void testParseDeleteObjectRecursiveErrorBody() throws Exception {
        XmlResponsesSaxParser.DeleteObjectRecursiveHandler handler = new XmlResponsesSaxParser()
                .parseDeleteObjectRecursiveResponse(xml("<Error><Code>InternalError</Code>"
                        + "<Message>delete failed</Message><RequestId>req-123</RequestId></Error>"));
        assertTrue(handler.isErrorResponse());
        assertEquals("InternalError", handler.getErrorCode());
        assertEquals("req-123", handler.getErrorRequestId());
        assertTrue(handler.getDeletedKeys().isEmpty());
    }

    @Test
    public void testSpaceQuotaXml() throws Exception {
        byte[] bytes = new AgentBucketXmlFactory().convertToXmlByteArray(
                new PutSpaceQuotaRequest("examplebucket-1250000000", "space", 1099511627776L));
        assertEquals("<SpaceQuota><Capacity>1099511627776</Capacity></SpaceQuota>",
                new String(bytes, StandardCharsets.UTF_8));
    }

    @Test
    public void testSpaceExtensionXmlEscaped() throws Exception {
        byte[] bytes = new AgentBucketXmlFactory().convertToXmlByteArray(
                new PutSpaceExtensionRequest("examplebucket-1250000000", "space", "a<b>&\"c"));
        assertEquals("<SpaceExtension><SpaceTag>a&lt;b&gt;&amp;&quot;c</SpaceTag></SpaceExtension>",
                new String(bytes, StandardCharsets.UTF_8));
    }
}
