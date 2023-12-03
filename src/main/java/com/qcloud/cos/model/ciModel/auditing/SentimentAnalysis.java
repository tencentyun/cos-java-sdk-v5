package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SentimentAnalysis {
    @XStreamAlias("Label")
    private String label;
    @XStreamAlias("Score")
    private Integer Score;
    @XStreamAlias("Detail")
    private SentimentAnalysisDetail detail;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getScore() {
        return Score;
    }

    public void setScore(Integer score) {
        Score = score;
    }

    public SentimentAnalysisDetail getDetail() {
        return detail;
    }

    public void setDetail(SentimentAnalysisDetail detail) {
        this.detail = detail;
    }
}
