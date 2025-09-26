
package com.qcloud.cos.internal.cihandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.job.AIGCMetadataResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 处理嵌套JSON字符串格式：{"AIGC": "{\"Label\":\"value\",...}"}
 */
public class AigcMetadataJsonResponseHandler {

    public AIGCMetadataResponse getResponse(InputStream in) throws IOException {
        String response = inputStreamToStringUsingBufferedReader(in);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        JsonNode rootNode = objectMapper.readTree(response);
        AIGCMetadataResponse aigcResponse = new AIGCMetadataResponse();

        if (rootNode.has("AIGC")) {
            JsonNode aigcNode = rootNode.get("AIGC");
            
            if (aigcNode.isTextual()) {
                String aigcJsonString = aigcNode.asText();

                AigcMetadata aigcMetadata = objectMapper.readValue(aigcJsonString, AigcMetadata.class);
                aigcResponse.setAigc(aigcMetadata);
            }
            else if (aigcNode.isObject()) {
                AigcMetadata aigcMetadata = objectMapper.treeToValue(aigcNode, AigcMetadata.class);
                aigcResponse.setAigc(aigcMetadata);
            }
        }
        
        return aigcResponse;
    }

    private static String inputStreamToStringUsingBufferedReader(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}