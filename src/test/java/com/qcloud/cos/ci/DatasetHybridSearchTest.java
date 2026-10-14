package com.qcloud.cos.ci;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.qcloud.cos.internal.cihandler.CICommonJsonResponseHandler;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetHybridSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetHybridSearchResponse;
import com.qcloud.cos.utils.CIJackson;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DatasetHybridSearchTest {

    // 文档「请求体示例一」：以文搜视频片段（场景/动作描述）
    private static final String DOC_REQUEST_BODY =
            "{\n" +
            "\t\"DatasetName\": \"videosearch\",\n" +
            "\t\"Mode\": \"text\",\n" +
            "\t\"Templates\": \"VideoSearch\",\n" +
            "\t\"SearchText\": \"熊猫在草地上玩耍的片段\",\n" +
            "\t\"Limit\": 10,\n" +
            "\t\"MatchThreshold\": 0,\n" +
            "\t\"Filter\": {\n" +
            "\t\t\"$and\": [{\n" +
            "\t\t\t\t\"MediaType\": {\n" +
            "\t\t\t\t\t\"$in\": [\"image\", \"document\", \"video\"]\n" +
            "\t\t\t\t}\n" +
            "\t\t\t},\n" +
            "\t\t\t{\n" +
            "\t\t\t\t\"Size\": {\n" +
            "\t\t\t\t\t\"$gt\": 123\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}\n" +
            "\t\t]\n" +
            "\t}\n" +
            "}";

    // 案例一响应：以文搜视频片段
    private static final String CASE_TEXT_RESPONSE =
            "{\n" +
            "\t\"VideoResult\": [{\n" +
            "\t\t\"URI\": \"cos://examplebucket-1250000000/panda_play_001.mp4\",\n" +
            "\t\t\"From\": 8.333333,\n" +
            "\t\t\"To\": 14.916667,\n" +
            "\t\t\"Score\": 43\n" +
            "\t}, {\n" +
            "\t\t\"URI\": \"cos://examplebucket-1250000000/panda_play_002.mp4\",\n" +
            "\t\t\"From\": 0,\n" +
            "\t\t\"To\": 8.333333,\n" +
            "\t\t\"Score\": 42\n" +
            "\t}],\n" +
            "\t\"DocResult\": [],\n" +
            "\t\"ImageResult\": [],\n" +
            "\t\"RequestId\": \"NjYwYzEwYjhfNGQ2ODk0MGJfMjcxxxx\"\n" +
            "}";

    // 案例二响应：以人名搜视频片段
    private static final String CASE_PERSON_RESPONSE =
            "{\n" +
            "\t\"VideoResult\": [{\n" +
            "\t\t\"URI\": \"cos://examplebucket-1250000000/interview_001.mp4\",\n" +
            "\t\t\"From\": 211.166672,\n" +
            "\t\t\"To\": 215.583328,\n" +
            "\t\t\"Score\": 51\n" +
            "\t}, {\n" +
            "\t\t\"URI\": \"cos://examplebucket-1250000000/interview_002.mp4\",\n" +
            "\t\t\"From\": 0,\n" +
            "\t\t\"To\": 8.333333,\n" +
            "\t\t\"Score\": 46\n" +
            "\t}],\n" +
            "\t\"DocResult\": [],\n" +
            "\t\"ImageResult\": [],\n" +
            "\t\"RequestId\": \"NjYwYzEwYjhfNGQ2ODk0MGJfMjcxxxx\"\n" +
            "}";

    // 案例三响应（2.8.2 需求单自测报告真实返回）：视频片段新增 Description 大模型描述
    private static final String CASE_VIDEO_DESCRIPTION_RESPONSE =
            "{\n" +
            "\t\"VideoResult\": [{\n" +
            "\t\t\"URI\": \"cos://ci-qta-bj-1251704708/data/meta/video/3/江苏省长1.mp4\",\n" +
            "\t\t\"From\": 10.083333,\n" +
            "\t\t\"To\": 15.083333,\n" +
            "\t\t\"Score\": 85,\n" +
            "\t\t\"Description\": \"视频为一段关于刘小涛的官方人物介绍，画面中刘小涛身着深色西装、白色衬衫并系红色领带，背景为标准的蓝色证件照底色。\"\n" +
            "\t}, {\n" +
            "\t\t\"URI\": \"cos://ci-qta-bj-1251704708/data/meta/video/3/江苏省长1.mp4\",\n" +
            "\t\t\"From\": 0,\n" +
            "\t\t\"To\": 5,\n" +
            "\t\t\"Score\": 85,\n" +
            "\t\t\"Description\": \"视频为关于刘小涛当选江苏省人民政府省长的官方公告展示。画面主体为一张静态的江苏省人民代表大会公告文件截图。\"\n" +
            "\t}],\n" +
            "\t\"DocResult\": [],\n" +
            "\t\"ImageResult\": [],\n" +
            "\t\"RequestId\": \"NmE3Yzc5NDBfZDQ2NzhiMGJfNDIwMF8zNWM=\"\n" +
            "}";

    private static DatasetHybridSearchResponse unmarshall(String json) throws Exception {
        return new CICommonJsonResponseHandler<DatasetHybridSearchResponse>()
                .getResponse(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)),
                        DatasetHybridSearchResponse.class);
    }

    @Test
    public void requestSerializeTest() throws Exception {
        // 序列化结果须与文档请求体示例逐字段全等（Filter 经 @JsonRawValue 以原始 JSON 输出为对象）
        DatasetHybridSearchRequest request = new DatasetHybridSearchRequest();
        request.setDatasetName("videosearch");
        request.setMode("text");
        request.setTemplates("VideoSearch");
        request.setSearchText("熊猫在草地上玩耍的片段");
        request.setLimit(10);
        request.setMatchThreshold(0);
        request.setFilter("{\"$and\":[{\"MediaType\":{\"$in\":[\"image\",\"document\",\"video\"]}},{\"Size\":{\"$gt\":123}}]}");

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(DOC_REQUEST_BODY), mapper.readTree(CIJackson.toJsonString(request)));
    }

    @Test
    public void caseTextResponseTest() throws Exception {
        DatasetHybridSearchResponse response = unmarshall(CASE_TEXT_RESPONSE);
        assertEquals("NjYwYzEwYjhfNGQ2ODk0MGJfMjcxxxx", response.getRequestId());
        assertNotNull(response.getVideoResult());
        assertEquals(2, response.getVideoResult().size());

        assertEquals("cos://examplebucket-1250000000/panda_play_001.mp4", response.getVideoResult().get(0).getURI());
        assertEquals(8.333333, response.getVideoResult().get(0).getFrom(), 0.0001);
        assertEquals(14.916667, response.getVideoResult().get(0).getTo(), 0.0001);
        assertEquals(Integer.valueOf(43), response.getVideoResult().get(0).getScore());

        assertEquals("cos://examplebucket-1250000000/panda_play_002.mp4", response.getVideoResult().get(1).getURI());
        assertEquals(0.0, response.getVideoResult().get(1).getFrom(), 0.0001);
        assertEquals(8.333333, response.getVideoResult().get(1).getTo(), 0.0001);
        assertEquals(Integer.valueOf(42), response.getVideoResult().get(1).getScore());

        assertNotNull(response.getDocResult());
        assertTrue(response.getDocResult().isEmpty());
        assertNotNull(response.getImageResult());
        assertTrue(response.getImageResult().isEmpty());
    }

    @Test
    public void casePersonResponseTest() throws Exception {
        DatasetHybridSearchResponse response = unmarshall(CASE_PERSON_RESPONSE);
        assertEquals("NjYwYzEwYjhfNGQ2ODk0MGJfMjcxxxx", response.getRequestId());
        assertEquals(2, response.getVideoResult().size());

        assertEquals("cos://examplebucket-1250000000/interview_001.mp4", response.getVideoResult().get(0).getURI());
        assertEquals(211.166672, response.getVideoResult().get(0).getFrom(), 0.0001);
        assertEquals(215.583328, response.getVideoResult().get(0).getTo(), 0.0001);
        assertEquals(Integer.valueOf(51), response.getVideoResult().get(0).getScore());

        assertEquals("cos://examplebucket-1250000000/interview_002.mp4", response.getVideoResult().get(1).getURI());
        assertEquals(0.0, response.getVideoResult().get(1).getFrom(), 0.0001);
        assertEquals(8.333333, response.getVideoResult().get(1).getTo(), 0.0001);
        assertEquals(Integer.valueOf(46), response.getVideoResult().get(1).getScore());
    }

    @Test
    public void caseVideoDescriptionResponseTest() throws Exception {
        DatasetHybridSearchResponse response = unmarshall(CASE_VIDEO_DESCRIPTION_RESPONSE);
        assertEquals("NmE3Yzc5NDBfZDQ2NzhiMGJfNDIwMF8zNWM=", response.getRequestId());
        assertEquals(2, response.getVideoResult().size());

        assertEquals("cos://ci-qta-bj-1251704708/data/meta/video/3/江苏省长1.mp4", response.getVideoResult().get(0).getURI());
        assertEquals(10.083333, response.getVideoResult().get(0).getFrom(), 0.0001);
        assertEquals(15.083333, response.getVideoResult().get(0).getTo(), 0.0001);
        assertEquals(Integer.valueOf(85), response.getVideoResult().get(0).getScore());
        assertEquals("视频为一段关于刘小涛的官方人物介绍，画面中刘小涛身着深色西装、白色衬衫并系红色领带，背景为标准的蓝色证件照底色。",
                response.getVideoResult().get(0).getDescription());

        assertEquals("cos://ci-qta-bj-1251704708/data/meta/video/3/江苏省长1.mp4", response.getVideoResult().get(1).getURI());
        assertEquals(0.0, response.getVideoResult().get(1).getFrom(), 0.0001);
        assertEquals(5.0, response.getVideoResult().get(1).getTo(), 0.0001);
        assertEquals(Integer.valueOf(85), response.getVideoResult().get(1).getScore());
        assertEquals("视频为关于刘小涛当选江苏省人民政府省长的官方公告展示。画面主体为一张静态的江苏省人民代表大会公告文件截图。",
                response.getVideoResult().get(1).getDescription());
    }

    @Test
    public void responseFieldsFullCoverageTest() throws Exception {
        // 严格模式反序列化：文档 JSON 中任何未被模型声明覆盖的字段都会抛 UnrecognizedPropertyException
        ObjectMapper strictMapper = new ObjectMapper();
        strictMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        strictMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        strictMapper.readValue(CASE_TEXT_RESPONSE, DatasetHybridSearchResponse.class);
        strictMapper.readValue(CASE_PERSON_RESPONSE, DatasetHybridSearchResponse.class);
        strictMapper.readValue(CASE_VIDEO_DESCRIPTION_RESPONSE, DatasetHybridSearchResponse.class);
    }
}
