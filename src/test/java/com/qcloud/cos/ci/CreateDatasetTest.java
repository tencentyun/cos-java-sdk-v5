package com.qcloud.cos.ci;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.qcloud.cos.internal.cihandler.CICommonJsonResponseHandler;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetResponse;
import com.qcloud.cos.utils.CIJackson;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateDatasetTest {

    // 文档「请求体」示例
    private static final String DOC_REQUEST_BODY =
            "{\n" +
            "    \"DatasetName\": \"test\",\n" +
            "    \"Description\": \"test\",\n" +
            "    \"TemplateId\": \"Official:COSBasicMeta\"\n" +
            "}";

    // 文档「响应体」示例
    private static final String DOC_RESPONSE_BODY =
            "{\n" +
            "    \"Dataset\": {\n" +
            "        \"BindCount\": 0,\n" +
            "        \"CreateTime\": \"2023-12-25 15:16:20.692674978 +0800 CST\",\n" +
            "        \"DatasetName\": \"test\",\n" +
            "        \"Description\": \"test\",\n" +
            "        \"FileCount\": 0,\n" +
            "        \"TemplateId\": \"Official:COSBasicMeta\",\n" +
            "        \"TotalFileSize\": 0,\n" +
            "        \"UpdateTime\": \"2023-12-25 15:16:20.692675128 +0800 CST\"\n" +
            "    },\n" +
            "    \"RequestId\": \"NWFjMzQ0MDZfOTBmYTUwXzZkZV8z****\"\n" +
            "}";

    @Test
    public void requestSerializeTest() throws Exception {
        // 序列化结果须与文档请求体示例逐字段全等（不多不少）
        CreateDatasetRequest request = new CreateDatasetRequest();
        request.setDatasetName("test");
        request.setDescription("test");
        request.setTemplateId("Official:COSBasicMeta");

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(DOC_REQUEST_BODY), mapper.readTree(CIJackson.toJsonString(request)));
    }

    @Test
    public void responseExampleTest() throws Exception {
        CreateDatasetResponse response = new CICommonJsonResponseHandler<CreateDatasetResponse>()
                .getResponse(new ByteArrayInputStream(DOC_RESPONSE_BODY.getBytes(StandardCharsets.UTF_8)),
                        CreateDatasetResponse.class);

        assertEquals("NWFjMzQ0MDZfOTBmYTUwXzZkZV8z****", response.getRequestId());
        assertNotNull(response.getDataset());
        assertEquals(Integer.valueOf(0), response.getDataset().getBindCount());
        assertEquals("2023-12-25 15:16:20.692674978 +0800 CST", response.getDataset().getCreateTime());
        assertEquals("test", response.getDataset().getDatasetName());
        assertEquals("test", response.getDataset().getDescription());
        assertEquals(Integer.valueOf(0), response.getDataset().getFileCount());
        assertEquals("Official:COSBasicMeta", response.getDataset().getTemplateId());
        // totalFileSize 在 SDK 中为 String 类型（文档定义为 Long），数字 0 经 Jackson coercion 转为 "0"
        assertEquals("0", response.getDataset().getTotalFileSize());
        assertEquals("2023-12-25 15:16:20.692675128 +0800 CST", response.getDataset().getUpdateTime());
    }

    @Test
    public void responseFieldsFullCoverageTest() throws Exception {
        // 严格模式反序列化：文档 JSON 中任何未被模型声明覆盖的字段都会抛 UnrecognizedPropertyException
        ObjectMapper strictMapper = new ObjectMapper();
        strictMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        strictMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        strictMapper.readValue(DOC_RESPONSE_BODY, CreateDatasetResponse.class);
    }
}
