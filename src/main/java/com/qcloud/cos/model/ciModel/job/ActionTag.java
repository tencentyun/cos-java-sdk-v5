package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class ActionTag {
    @XStreamAlias("StartTime")
    private String startTime;
    @XStreamAlias("EndTime")
    private String endTime;
    @XStreamImplicit(itemFieldName = "Tags")
    private List<MediaTags> tags;

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
