package com.qcloud.cos.event;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransferCompletionFilterTest {

    @Test
    public void filter() {
        ProgressEventFilter f = new TransferCompletionFilter();
        assertNull(f.filter(new ProgressEvent(ProgressEventType.TRANSFER_COMPLETED_EVENT)));
        assertNotNull(f.filter(new ProgressEvent(ProgressEventType.TRANSFER_STARTED_EVENT)));
    }
}