package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class DnaConfig {
    @XStreamAlias("RuleType")
    private String ruleType;
    @XStreamAlias("DnaDbId")
    private String dnaDbId;
    @XStreamAlias("VideoId")
    private String videoId;

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getDnaDbId() {
        return dnaDbId;
    }

    public void setDnaDbId(String dnaDbId) {
        this.dnaDbId = dnaDbId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
