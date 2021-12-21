package com.qcloud.cos.model.fetch;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PutAsyncFetchTaskSerializer extends StdSerializer<PutAsyncFetchTaskRequest> {

    public PutAsyncFetchTaskSerializer(Class<PutAsyncFetchTaskRequest> t) {
        super(t);
    }

    @Override
    public void serialize(PutAsyncFetchTaskRequest request, JsonGenerator jsonGenerator,
                    SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("Url", request.getUrl());
        jsonGenerator.writeStringField("Key", request.getKey());
        jsonGenerator.writeBooleanField("IgnoreSameKey", request.getIgnoreSameKey());

        setIfNotEmpty(jsonGenerator, "MD5", request.getMd5());
        setIfNotEmpty(jsonGenerator, "SuccessCallbackUrl", request.getSuccessCallbackUrl());
        setIfNotEmpty(jsonGenerator, "FailureCallbackUrl", request.getFailureCallbackUrl());
        setIfNotEmpty(jsonGenerator, "OnKeyExist", request.getOnKeyExist());

        jsonGenerator.writeEndObject();
    }

    void setIfNotEmpty(JsonGenerator jsonGenerator, String key, String value) throws IOException {
        if (value!= null && !value.isEmpty()) {
            jsonGenerator.writeStringField(key, value);
        }
    }
}
