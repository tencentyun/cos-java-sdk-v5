package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class PlaceTag extends MediaTags {
    @XStreamImplicit(itemFieldName = "ClipFrameResult")
    private List<String> clipFrameResult;
    @XStreamAlias("StartIndex")
    private String startIndex;
    @XStreamAlias("EndIndex")
    private String endIndex;
    @XStreamAlias("StartTime")
    private String startTime;
    @XStreamAlias("EndTime")
    private String endTime;
    @XStreamImplicit(itemFieldName = "Tags")
    List<MediaTags> tags;

    public List<String> getClipFrameResult() {
        return clipFrameResult;
    }

    public void setClipFrameResult(List<String> clipFrameResult) {
        this.clipFrameResult = clipFrameResult;
    }

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<MediaTags> getTags() {
        return tags;
    }

    public void setTags(List<MediaTags> tags) {
        this.tags = tags;
    }
}
