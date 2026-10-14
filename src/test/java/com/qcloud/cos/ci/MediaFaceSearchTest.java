package com.qcloud.cos.ci;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.qcloud.cos.internal.cihandler.CICommonJsonResponseHandler;
import com.qcloud.cos.model.ciModel.metaInsight.MediaFaceSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.MediaFaceSearchResponse;
import com.qcloud.cos.utils.CIJackson;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class MediaFaceSearchTest {

    // 文档「响应体」示例与实际案例响应内容相同
    private static final String RESPONSE_EXAMPLE =
            "{\n" +
            "\t\"RequestId\": \"7CA7D615-CFB1-5437-9A12-2D185C3EE6CB\",\n" +
            "\t\"MediaInfoList\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"FaceId\": \"face_20260206_0001\",\n" +
            "\t\t\t\"UriList\": [\n" +
            "\t\t\t\t\"cos://examplebucket-1250000000/photo_001.jpg\",\n" +
            "\t\t\t\t\"cos://examplebucket-1250000000/photo_002.jpg\"\n" +
            "\t\t\t]\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"FaceId\": \"face_20260206_0002\",\n" +
            "\t\t\t\"UriList\": [\n" +
            "\t\t\t\t\"cos://examplebucket-1250000000/photo_003.jpg\",\n" +
            "\t\t\t\t\"cos://examplebucket-1250000000/photo_004.jpg\"\n" +
            "\t\t\t]\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";

    @Test
    public void responseExampleTest() throws Exception {
        MediaFaceSearchResponse response = new CICommonJsonResponseHandler<MediaFaceSearchResponse>()
                .getResponse(new ByteArrayInputStream(RESPONSE_EXAMPLE.getBytes(StandardCharsets.UTF_8)),
                        MediaFaceSearchResponse.class);

        assertEquals("7CA7D615-CFB1-5437-9A12-2D185C3EE6CB", response.getRequestId());
        assertEquals(2, response.getMediaInfoList().size());

        MediaFaceSearchResponse.MediaInfo first = response.getMediaInfoList().get(0);
        assertEquals("face_20260206_0001", first.getFaceId());
        assertEquals(2, first.getUriList().size());
        assertEquals("cos://examplebucket-1250000000/photo_001.jpg", first.getUriList().get(0));
        assertEquals("cos://examplebucket-1250000000/photo_002.jpg", first.getUriList().get(1));

        MediaFaceSearchResponse.MediaInfo second = response.getMediaInfoList().get(1);
        assertEquals("face_20260206_0002", second.getFaceId());
        assertEquals(2, second.getUriList().size());
        assertEquals("cos://examplebucket-1250000000/photo_003.jpg", second.getUriList().get(0));
        assertEquals("cos://examplebucket-1250000000/photo_004.jpg", second.getUriList().get(1));
    }

    @Test
    public void responseFieldsFullCoverageTest() throws Exception {
        // 严格模式反序列化：文档 JSON 中任何未被模型声明覆盖的字段都会抛 UnrecognizedPropertyException
        ObjectMapper strictMapper = new ObjectMapper();
        strictMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        strictMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        strictMapper.readValue(RESPONSE_EXAMPLE, MediaFaceSearchResponse.class);
    }

    @Test
    public void requestSerializeTest() throws Exception {
        // 文档案例请求体示例，序列化结果须与其逐字段全等（不多不少）
        String docRequestBody =
                "{\n" +
                "\t\"DatasetName\": \"your-dataset-name-001\",\n" +
                "\t\"URI\": \"cos://examplebucket-1250000000/face_query.jpg\"\n" +
                "}";
        MediaFaceSearchRequest request = new MediaFaceSearchRequest();
        request.setDatasetName("your-dataset-name-001");
        request.setURI("cos://examplebucket-1250000000/face_query.jpg");

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(docRequestBody), mapper.readTree(CIJackson.toJsonString(request)));
    }
}
