package com.qcloud.cos;

import com.qcloud.cos.internal.ResponseMetadata;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResponseMetadataTest {
    @Test
    public void testResponseMetadata() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put(Headers.REQUEST_ID, "NIQXXXXXXXXXXX");
        metadata.put(Headers.TRACE_ID, "XXXXXXXXXX");
        metadata.put(Headers.BUCKET_ARCH, "Standard");

        ResponseMetadata origin_responseMetadata = new ResponseMetadata(metadata);
        ResponseMetadata responseMetadata = new ResponseMetadata(origin_responseMetadata);

        assertEquals("NIQXXXXXXXXXXX", responseMetadata.getRequestId());
        assertEquals("XXXXXXXXXX", responseMetadata.getTraceId());
        assertEquals("Standard", responseMetadata.getBucketType());
        System.out.println(responseMetadata.toString());

        origin_responseMetadata = new ResponseMetadata((Map<String, String>) null);
        assertEquals("{}", origin_responseMetadata.toString());
    }
}
