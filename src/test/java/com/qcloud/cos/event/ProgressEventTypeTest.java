package com.qcloud.cos.event;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProgressEventTypeTest {

    @Test
    public void isTransferEvent() {
        assertTrue(ProgressEventType.TRANSFER_CANCELED_EVENT.isTransferEvent());
        assertFalse(ProgressEventType.CLIENT_REQUEST_FAILED_EVENT.isTransferEvent());
    }

    @Test
    public void isRequestCycleEvent() {
        assertFalse(ProgressEventType.TRANSFER_CANCELED_EVENT.isRequestCycleEvent());
    }

    @Test
    public void isByteCountEvent() {
        assertTrue(ProgressEventType.HTTP_REQUEST_CONTENT_RESET_EVENT.isByteCountEvent());
        assertFalse(ProgressEventType.CLIENT_REQUEST_FAILED_EVENT.isByteCountEvent());
    }
}