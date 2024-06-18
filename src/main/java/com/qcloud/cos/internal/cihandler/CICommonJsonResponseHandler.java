package com.qcloud.cos.internal.cihandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class CICommonJsonResponseHandler<T> {

    public T getResponse(InputStream in, Class<T> tClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(in, tClass);
    }

}
