package com.qcloud.cos.ci;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.qcloud.cos.internal.cihandler.CICommonJsonResponseHandler;
import com.qcloud.cos.model.ciModel.metaInsight.MediaFaceClipSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.MediaFaceClipSearchResponse;
import com.qcloud.cos.utils.CIJackson;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class MediaFaceClipSearchTest {

    // 文档「响应体」一节示例（视频媒资，Score 99.04，含一片段两帧 TrackData）
    private static final String RESPONSE_BODY_EXAMPLE =
            "{\n" +
            "\t\"MediaClipList\": [{\n" +
            "\t\t\"Score\": 99.04,\n" +
            "\t\t\"LabelName\": \"张三\",\n" +
            "\t\t\"Category\": \"celebrity\",\n" +
            "\t\t\"OccurrencesInfos\": [{\n" +
            "\t\t\t\"From\": 61.066353,\n" +
            "\t\t\t\"To\": 69.06635,\n" +
            "\t\t\t\"TrackData\": [{\n" +
            "\t\t\t\t\"Timestamp\": 62.03302,\n" +
            "\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\"Left\": 517,\n" +
            "\t\t\t\t\t\"Top\": 409,\n" +
            "\t\t\t\t\t\"Width\": 128,\n" +
            "\t\t\t\t\t\"Height\": 168\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}, {\n" +
            "\t\t\t\t\"Timestamp\": 63.5,\n" +
            "\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\"Left\": 520,\n" +
            "\t\t\t\t\t\"Top\": 412,\n" +
            "\t\t\t\t\t\"Width\": 130,\n" +
            "\t\t\t\t\t\"Height\": 170\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}]\n" +
            "\t\t}, {\n" +
            "\t\t\t\"From\": 120.5,\n" +
            "\t\t\t\"To\": 135.8,\n" +
            "\t\t\t\"TrackData\": [{\n" +
            "\t\t\t\t\"Timestamp\": 121.03302,\n" +
            "\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\"Left\": 300,\n" +
            "\t\t\t\t\t\"Top\": 250,\n" +
            "\t\t\t\t\t\"Width\": 140,\n" +
            "\t\t\t\t\t\"Height\": 180\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}]\n" +
            "\t\t}]\n" +
            "\t}],\n" +
            "\t\"RequestId\": \"E44FFACD-9E90-555A-A09A-6FD3B7335E39\"\n" +
            "}";

    // 案例一：定位人脸在视频中的出现片段
    private static final String CASE_VIDEO =
            "{\n" +
            "\t\"MediaClipList\": [{\n" +
            "\t\t\"Score\": 96,\n" +
            "\t\t\"LabelName\": \"张三\",\n" +
            "\t\t\"Category\": \"celebrity\",\n" +
            "\t\t\"OccurrencesInfos\": [{\n" +
            "\t\t\t\"From\": 58,\n" +
            "\t\t\t\"To\": 62.115,\n" +
            "\t\t\t\"TrackData\": [{\n" +
            "\t\t\t\t\"Timestamp\": 60,\n" +
            "\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\"Left\": 618,\n" +
            "\t\t\t\t\t\"Top\": 133,\n" +
            "\t\t\t\t\t\"Width\": 146,\n" +
            "\t\t\t\t\t\"Height\": 183\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}]\n" +
            "\t\t}, {\n" +
            "\t\t\t\"From\": 70,\n" +
            "\t\t\t\"To\": 75.769,\n" +
            "\t\t\t\"TrackData\": [{\n" +
            "\t\t\t\t\"Timestamp\": 73,\n" +
            "\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\"Left\": 646,\n" +
            "\t\t\t\t\t\"Top\": 177,\n" +
            "\t\t\t\t\t\"Width\": 175,\n" +
            "\t\t\t\t\t\"Height\": 221\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}]\n" +
            "\t\t}, {\n" +
            "\t\t\t\"From\": 131,\n" +
            "\t\t\t\"To\": 138,\n" +
            "\t\t\t\"TrackData\": [{\n" +
            "\t\t\t\t\"Timestamp\": 134,\n" +
            "\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\"Left\": 662,\n" +
            "\t\t\t\t\t\"Top\": 223,\n" +
            "\t\t\t\t\t\"Width\": 177,\n" +
            "\t\t\t\t\t\"Height\": 235\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}]\n" +
            "\t\t}, {\n" +
            "\t\t\t\"From\": 197,\n" +
            "\t\t\t\"To\": 200,\n" +
            "\t\t\t\"TrackData\": [{\n" +
            "\t\t\t\t\"Timestamp\": 198,\n" +
            "\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\"Left\": 707,\n" +
            "\t\t\t\t\t\"Top\": 208,\n" +
            "\t\t\t\t\t\"Width\": 159,\n" +
            "\t\t\t\t\t\"Height\": 205\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}]\n" +
            "\t\t}]\n" +
            "\t}],\n" +
            "\t\"RequestId\": \"NmExZDJiNzhfMjYyOTVhMTVfMTYyYzk0XzIxODg=\"\n" +
            "}";

    // 案例二：定位人脸在图片中的位置（From/To/Timestamp 均为 0 属预期行为）
    private static final String CASE_IMAGE =
            "{\n" +
            "\t\"MediaClipList\": [{\n" +
            "\t\t\"Score\": 94,\n" +
            "\t\t\"LabelName\": \"李四\",\n" +
            "\t\t\"Category\": \"custom\",\n" +
            "\t\t\"OccurrencesInfos\": [{\n" +
            "\t\t\t\"From\": 0,\n" +
            "\t\t\t\"To\": 0,\n" +
            "\t\t\t\"TrackData\": [{\n" +
            "\t\t\t\t\"Timestamp\": 0,\n" +
            "\t\t\t\t\"BoxPosition\": {\n" +
            "\t\t\t\t\t\"Left\": 220,\n" +
            "\t\t\t\t\t\"Top\": 56,\n" +
            "\t\t\t\t\t\"Width\": 184,\n" +
            "\t\t\t\t\t\"Height\": 261\n" +
            "\t\t\t\t}\n" +
            "\t\t\t}]\n" +
            "\t\t}]\n" +
            "\t}],\n" +
            "\t\"RequestId\": \"NjYwYzEwYjhfNGQ2ODk0MGJfMjcxxxx\"\n" +
            "}";

    private static MediaFaceClipSearchResponse unmarshall(String json) throws Exception {
        return new CICommonJsonResponseHandler<MediaFaceClipSearchResponse>()
                .getResponse(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)),
                        MediaFaceClipSearchResponse.class);
    }

    @Test
    public void responseBodyExampleTest() throws Exception {
        MediaFaceClipSearchResponse response = unmarshall(RESPONSE_BODY_EXAMPLE);
        assertEquals("E44FFACD-9E90-555A-A09A-6FD3B7335E39", response.getRequestId());
        assertEquals(1, response.getMediaClipList().size());

        MediaFaceClipSearchResponse.MediaClip clip = response.getMediaClipList().get(0);
        assertEquals(99.04, clip.getScore(), 0.0001);
        assertEquals("张三", clip.getLabelName());
        assertEquals("celebrity", clip.getCategory());
        assertEquals(2, clip.getOccurrencesInfos().size());

        MediaFaceClipSearchResponse.OccurrencesInfo first = clip.getOccurrencesInfos().get(0);
        assertEquals(61.066353, first.getFrom(), 0.000001);
        assertEquals(69.06635, first.getTo(), 0.000001);
        assertEquals(2, first.getTrackData().size());
        assertEquals(62.03302, first.getTrackData().get(0).getTimestamp(), 0.000001);
        assertEquals(Integer.valueOf(517), first.getTrackData().get(0).getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(409), first.getTrackData().get(0).getBoxPosition().getTop());
        assertEquals(Integer.valueOf(128), first.getTrackData().get(0).getBoxPosition().getWidth());
        assertEquals(Integer.valueOf(168), first.getTrackData().get(0).getBoxPosition().getHeight());
        assertEquals(63.5, first.getTrackData().get(1).getTimestamp(), 0.0001);
        assertEquals(Integer.valueOf(520), first.getTrackData().get(1).getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(170), first.getTrackData().get(1).getBoxPosition().getHeight());

        MediaFaceClipSearchResponse.OccurrencesInfo second = clip.getOccurrencesInfos().get(1);
        assertEquals(120.5, second.getFrom(), 0.0001);
        assertEquals(135.8, second.getTo(), 0.0001);
        assertEquals(1, second.getTrackData().size());
        assertEquals(121.03302, second.getTrackData().get(0).getTimestamp(), 0.000001);
        assertEquals(Integer.valueOf(300), second.getTrackData().get(0).getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(180), second.getTrackData().get(0).getBoxPosition().getHeight());
    }

    @Test
    public void caseVideoTest() throws Exception {
        MediaFaceClipSearchResponse response = unmarshall(CASE_VIDEO);
        assertEquals("NmExZDJiNzhfMjYyOTVhMTVfMTYyYzk0XzIxODg=", response.getRequestId());
        assertEquals(1, response.getMediaClipList().size());

        MediaFaceClipSearchResponse.MediaClip clip = response.getMediaClipList().get(0);
        assertEquals(96.0, clip.getScore(), 0.0001);
        assertEquals("张三", clip.getLabelName());
        assertEquals("celebrity", clip.getCategory());
        assertEquals(4, clip.getOccurrencesInfos().size());

        MediaFaceClipSearchResponse.OccurrencesInfo first = clip.getOccurrencesInfos().get(0);
        assertEquals(58.0, first.getFrom(), 0.0001);
        assertEquals(62.115, first.getTo(), 0.0001);
        assertEquals(1, first.getTrackData().size());
        assertEquals(60.0, first.getTrackData().get(0).getTimestamp(), 0.0001);
        assertEquals(Integer.valueOf(618), first.getTrackData().get(0).getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(133), first.getTrackData().get(0).getBoxPosition().getTop());
        assertEquals(Integer.valueOf(146), first.getTrackData().get(0).getBoxPosition().getWidth());
        assertEquals(Integer.valueOf(183), first.getTrackData().get(0).getBoxPosition().getHeight());

        MediaFaceClipSearchResponse.OccurrencesInfo last = clip.getOccurrencesInfos().get(3);
        assertEquals(197.0, last.getFrom(), 0.0001);
        assertEquals(200.0, last.getTo(), 0.0001);
        assertEquals(198.0, last.getTrackData().get(0).getTimestamp(), 0.0001);
        assertEquals(Integer.valueOf(707), last.getTrackData().get(0).getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(205), last.getTrackData().get(0).getBoxPosition().getHeight());
    }

    @Test
    public void caseImageTest() throws Exception {
        MediaFaceClipSearchResponse response = unmarshall(CASE_IMAGE);
        assertEquals("NjYwYzEwYjhfNGQ2ODk0MGJfMjcxxxx", response.getRequestId());
        assertEquals(1, response.getMediaClipList().size());

        MediaFaceClipSearchResponse.MediaClip clip = response.getMediaClipList().get(0);
        assertEquals(94.0, clip.getScore(), 0.0001);
        assertEquals("李四", clip.getLabelName());
        assertEquals("custom", clip.getCategory());
        assertEquals(1, clip.getOccurrencesInfos().size());

        // 图片媒资：From/To/Timestamp 均为 0，属文档声明的预期行为
        MediaFaceClipSearchResponse.OccurrencesInfo occurrencesInfo = clip.getOccurrencesInfos().get(0);
        assertEquals(0.0, occurrencesInfo.getFrom(), 0.0001);
        assertEquals(0.0, occurrencesInfo.getTo(), 0.0001);
        assertEquals(1, occurrencesInfo.getTrackData().size());
        assertEquals(0.0, occurrencesInfo.getTrackData().get(0).getTimestamp(), 0.0001);
        assertEquals(Integer.valueOf(220), occurrencesInfo.getTrackData().get(0).getBoxPosition().getLeft());
        assertEquals(Integer.valueOf(56), occurrencesInfo.getTrackData().get(0).getBoxPosition().getTop());
        assertEquals(Integer.valueOf(184), occurrencesInfo.getTrackData().get(0).getBoxPosition().getWidth());
        assertEquals(Integer.valueOf(261), occurrencesInfo.getTrackData().get(0).getBoxPosition().getHeight());
    }

    @Test
    public void responseFieldsFullCoverageTest() throws Exception {
        // 严格模式反序列化：文档 JSON 中任何未被模型声明覆盖的字段都会抛 UnrecognizedPropertyException
        ObjectMapper strictMapper = new ObjectMapper();
        strictMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        strictMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        strictMapper.readValue(RESPONSE_BODY_EXAMPLE, MediaFaceClipSearchResponse.class);
        strictMapper.readValue(CASE_VIDEO, MediaFaceClipSearchResponse.class);
        strictMapper.readValue(CASE_IMAGE, MediaFaceClipSearchResponse.class);
    }

    @Test
    public void requestSerializeTest() throws Exception {
        // 文档案例一请求体示例，序列化结果须与其逐字段全等（不多不少）
        String docRequestBody =
                "{\n" +
                "\t\"DatasetName\": \"your-dataset-name-001\",\n" +
                "\t\"URI\": \"cos://examplebucket-1250000000/videos/interview_001.mp4\",\n" +
                "\t\"FaceId\": \"face_20260206_0001\"\n" +
                "}";
        MediaFaceClipSearchRequest request = new MediaFaceClipSearchRequest();
        request.setDatasetName("your-dataset-name-001");
        request.setURI("cos://examplebucket-1250000000/videos/interview_001.mp4");
        request.setFaceId("face_20260206_0001");

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(docRequestBody), mapper.readTree(CIJackson.toJsonString(request)));
    }
}
