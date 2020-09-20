package com.qcloud.cos.model.ciModel.queue;

public class MediaQueueResponse {
    private MediaQueueObject queue;
    private String requestId;


    public MediaQueueObject getQueue() {
        if (queue==null){
            queue = new MediaQueueObject();
        }
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
