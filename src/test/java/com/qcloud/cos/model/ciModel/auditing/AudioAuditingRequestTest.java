package com.qcloud.cos.model.ciModel.auditing;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AudioAuditingRequestTest {

    private AudioAuditingRequest audioAuditingRequestUnderTest;

    @Before
    public void setUp() throws Exception {
        audioAuditingRequestUnderTest = new AudioAuditingRequest();
    }

    @Test
    public void testToString() {
        assertEquals("AudioAuditingRequest{conf=null, input=null, jobId='null'}", audioAuditingRequestUnderTest.toString());
    }
}
