package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaTimeIntervalObject {
    /**
     * 开始时间
     */
    @XStreamAlias("Start")
    private String start;
    /**
     * 持续时间
     */
    @XStreamAlias("Duration")
    private String duration;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
