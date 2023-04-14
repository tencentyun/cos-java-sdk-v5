package com.qcloud.cos.event;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProgressEventTest {

    @Test
    public void getBytes() {
        ProgressEvent p = new ProgressEvent(123);
        assertEquals(123, p.getBytes());
    }

    @Test
    public void failConstruct() {
        assertThrows(IllegalArgumentException.class, () -> {new ProgressEvent(ProgressEventType.TRANSFER_STARTED_EVENT, -1);});
        assertThrows(IllegalArgumentException.class, () -> {new ProgressEvent(null, 1);});
    }

    @Test
    public void getBytesTransferred() {
        ProgressEvent p = new ProgressEvent(ProgressEventType.RESPONSE_BYTE_DISCARD_EVENT,1);
        assertEquals(-1, p.getBytesTransferred());
        assertNotNull(p.toString());
    }


    @Test
    public void getEventCode() {
        ProgressEvent p = new ProgressEvent(ProgressEventType.TRANSFER_PREPARING_EVENT,1);
        assertEquals(ProgressEvent.PREPARING_EVENT_CODE, p.getEventCode());
    }
}