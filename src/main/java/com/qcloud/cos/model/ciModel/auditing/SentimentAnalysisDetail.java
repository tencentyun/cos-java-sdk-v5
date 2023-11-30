package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SentimentAnalysisDetail {
    @XStreamAlias("Negative")
    private Integer negative;

    @XStreamAlias("Positive")
    private Integer positive;

    public Integer getNegative() {
        return negative;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
    }

    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }
}
