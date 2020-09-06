package com.qcloud.cos.model.ciModel.queue;

import com.qcloud.cos.model.ciModel.common.MediaCommonResponse;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Response")
public class MediaListQueueResponse extends MediaCommonResponse {
    @XStreamAlias("QueueList")
    private List<MediaQueueObject> queueList;
    @XStreamAlias("NonExistPIDs")
    private List<String> nonExistPIDs;

    public MediaListQueueResponse() {
        queueList = new ArrayList<>();
        nonExistPIDs = new ArrayList<>();
    }

    public List<MediaQueueObject> getQueueList() {
        return queueList;
    }

    public void setQueueList(List<MediaQueueObject> queueList) {
        this.queueList = queueList;
    }

    public List<String> getNonExistPIDs() {
        return nonExistPIDs;
    }

    public void setNonExistPIDs(List<String> nonExistPIDs) {
        this.nonExistPIDs = nonExistPIDs;
    }

    @Override
    public String toString() {
        return "MediaQueueResponse{" +
                "queueList=" + queueList +
                ", nonExistPIDs=" + nonExistPIDs +
                '}';
    }
}
