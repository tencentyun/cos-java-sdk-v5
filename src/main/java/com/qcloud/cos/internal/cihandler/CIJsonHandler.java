package com.qcloud.cos.internal.cihandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class CIJsonHandler<T> {

    public T getResponse(InputStream in, Class<T> tClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(in, tClass);
    }

}
