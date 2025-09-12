package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class VideoTagResult {
    @XStreamAlias("StreamData")
    private StreamData streamData;

    public StreamData getStreamData() {
        if (streamData == null) {
            streamData = new StreamData();
        }
        return streamData;
    }

    public void setStreamData(StreamData streamData) {
        this.streamData = streamData;
    }

    @Override
    public String toString() {
        return "VideoTagResult{" +
                "streamData=" + streamData +
                '}';
    }
}
