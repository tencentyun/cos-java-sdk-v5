package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MatchDetail {

    @XStreamAlias("MatchStartTime")
    public Integer matchStartTime;
    @XStreamAlias("MatchEndTime")
    public Integer matchEndTime;
    @XStreamAlias("SrcStartTime")
    public Integer srcStartTime;
    @XStreamAlias("SrcEndTime")
    public Integer srcEndTime;

    public Integer getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(Integer matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public Integer getMatchEndTime() {
        return matchEndTime;
    }

    public void setMatchEndTime(Integer matchEndTime) {
        this.matchEndTime = matchEndTime;
    }

    public Integer getSrcStartTime() {
        return srcStartTime;
    }

    public void setSrcStartTime(Integer srcStartTime) {
        this.srcStartTime = srcStartTime;
    }

    public Integer getSrcEndTime() {
        return srcEndTime;
    }

    public void setSrcEndTime(Integer srcEndTime) {
        this.srcEndTime = srcEndTime;
    }
}
