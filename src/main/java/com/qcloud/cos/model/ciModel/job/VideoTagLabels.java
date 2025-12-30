
package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class VideoTagLabels {
    @XStreamAlias("Confidence")
    private String confidence;
    
    @XStreamAlias("First")
    private String first;
    
    @XStreamAlias("Second")
    private String second;

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "VideoTagLabels{" +
                "confidence='" + confidence + '\'' +
                ", first='" + first + '\'' +
                ", second='" + second + '\'' +
                '}';
    }
}