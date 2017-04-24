package com.qcloud.cos.internal;

import java.util.Map;

import com.qcloud.cos.Headers;

public class ResponseMetadata {
    protected final Map<String, String> metadata;

    public ResponseMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public ResponseMetadata(ResponseMetadata originalResponseMetadata) {
        this(originalResponseMetadata.metadata);
    }

    public String getRequestId() {
        return metadata.get(Headers.REQUEST_ID);
    }
    
    public String getTraceId() {
        return metadata.get(Headers.TRACE_ID);
    }

    @Override
    public String toString() {
        if (metadata == null)
            return "{}";
        return metadata.toString();
    }

}

