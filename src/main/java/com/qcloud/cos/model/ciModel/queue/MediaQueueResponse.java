package com.qcloud.cos.model.ciModel.queue;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class MediaQueueResponse {
    @XStreamAlias("Queue")
    private MediaQueueObject queue;
    @XStreamAlias("RequestId")
    private String requestId;


    public MediaQueueObject getQueue() {
        return queue;
    }

    public void setQueue(MediaQueueObject queue) {
        this.queue = queue;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "MediaQueueResponse{" +
                "queue=" + queue +
                '}';
    }
}
