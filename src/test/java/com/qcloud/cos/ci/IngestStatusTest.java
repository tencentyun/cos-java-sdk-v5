package com.qcloud.cos.ci;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.qcloud.cos.internal.cihandler.CICommonJsonResponseHandler;
import com.qcloud.cos.model.ciModel.metaInsight.IngestStatusRequest;
import com.qcloud.cos.model.ciModel.metaInsight.IngestStatusResponse;
import com.qcloud.cos.utils.CIJackson;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class IngestStatusTest {

    // 接口文档「成功示例」响应体
    private static final String SUCCESS_RESPONSE_EXAMPLE =
            "{\n" +
            "\t\"RequestId\": \"2FDE2411-DB8D-4A9A-875B-275798F14A5E\",\n" +
            "\t\"Status\": \"Success\",\n" +
            "\t\"JobsDetail\": {\n" +
            "\t\t\"Code\": \"Success\",\n" +
            "\t\t\"Message\": \"success\"\n" +
            "\t}\n" +
            "}";

    // 接口文档「失败示例」响应体
    private static final String FAILED_RESPONSE_EXAMPLE =
            "{\n" +
            "\t\"RequestId\": \"3ABE3512-EC9E-5B0B-986C-386899F25B6F\",\n" +
            "\t\"Status\": \"Failed\",\n" +
            "\t\"JobsDetail\": {\n" +
            "\t\t\"Code\": \"ResourceNotFound\",\n" +
            "\t\t\"Message\": \"The specified resource does not exist.\"\n" +
            "\t}\n" +
            "}";

    @Test
    public void successResponseExampleTest() throws Exception {
        IngestStatusResponse response = new CICommonJsonResponseHandler<IngestStatusResponse>()
                .getResponse(new ByteArrayInputStream(SUCCESS_RESPONSE_EXAMPLE.getBytes(StandardCharsets.UTF_8)),
                        IngestStatusResponse.class);

        assertEquals("2FDE2411-DB8D-4A9A-875B-275798F14A5E", response.getRequestId());
        assertEquals("Success", response.getStatus());
        assertEquals("Success", response.getJobsDetail().getCode());
        assertEquals("success", response.getJobsDetail().getMessage());
    }

    @Test
    public void failedResponseExampleTest() throws Exception {
        IngestStatusResponse response = new CICommonJsonResponseHandler<IngestStatusResponse>()
                .getResponse(new ByteArrayInputStream(FAILED_RESPONSE_EXAMPLE.getBytes(StandardCharsets.UTF_8)),
                        IngestStatusResponse.class);

        assertEquals("3ABE3512-EC9E-5B0B-986C-386899F25B6F", response.getRequestId());
        assertEquals("Failed", response.getStatus());
        assertEquals("ResourceNotFound", response.getJobsDetail().getCode());
        assertEquals("The specified resource does not exist.", response.getJobsDetail().getMessage());
    }

    @Test
    public void responseFieldsFullCoverageTest() throws Exception {
        // 严格模式反序列化：文档 JSON 中任何未被模型声明覆盖的字段都会抛 UnrecognizedPropertyException
        ObjectMapper strictMapper = new ObjectMapper();
        strictMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        strictMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        strictMapper.readValue(SUCCESS_RESPONSE_EXAMPLE, IngestStatusResponse.class);
        strictMapper.readValue(FAILED_RESPONSE_EXAMPLE, IngestStatusResponse.class);
    }

    @Test
    public void requestSerializeTest() throws Exception {
        // 文档案例请求体示例，序列化结果须与其逐字段全等（不多不少）
        String docRequestBody =
                "{\n" +
                "\t\"URI\": \"cos://examplebucket-1250000000/test.mp4\",\n" +
                "\t\"DatasetName\": \"your-dataset-name-001\"\n" +
                "}";
        IngestStatusRequest request = new IngestStatusRequest();
        request.setURI("cos://examplebucket-1250000000/test.mp4");
        request.setDatasetName("your-dataset-name-001");

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(docRequestBody), mapper.readTree(CIJackson.toJsonString(request)));
    }
}
