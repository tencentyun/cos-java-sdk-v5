package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class VideoTagResult {
    @XStreamAlias("StreamData")
    private StreamData streamData;

    public StreamData getStreamData() {
        return streamData;
    }

    public void setStreamData(StreamData streamData) {
        this.streamData = streamData;
    }
}
