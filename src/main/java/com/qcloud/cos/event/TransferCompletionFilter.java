package com.qcloud.cos.event;

public class TransferCompletionFilter implements ProgressEventFilter {

    @Override
    public ProgressEvent filter(ProgressEvent progressEvent) {
        // Block COMPLETE events from the low-level GetObject operation,
        // but we still want to keep the BytesTransferred
        return progressEvent.getEventType() == ProgressEventType.TRANSFER_COMPLETED_EVENT
             ? null // discard this event
             : progressEvent
             ;
    }

}
