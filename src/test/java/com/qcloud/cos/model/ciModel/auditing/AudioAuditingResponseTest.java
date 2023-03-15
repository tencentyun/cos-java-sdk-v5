package com.qcloud.cos.model.ciModel.auditing;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AudioAuditingResponseTest {

    private AudioAuditingResponse audioAuditingResponseUnderTest;

    @Before
    public void setUp() throws Exception {
        audioAuditingResponseUnderTest = new AudioAuditingResponse();
    }

    @Test
    public void testToString() {
        assertEquals(audioAuditingResponseUnderTest.toString(), audioAuditingResponseUnderTest.toString());
    }
}
