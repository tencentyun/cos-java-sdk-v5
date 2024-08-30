package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class DetailPerSecond {
    @XStreamAlias("TimeStamp")
    private String timeStamp;
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("Confidence")
    private String confidence;
    @XStreamImplicit(itemFieldName = "BBox")
    private List<BBox> bboxList;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public List<BBox> getBboxList() {
        return bboxList;
    }

    public void setBboxList(List<BBox> bboxList) {
        this.bboxList = bboxList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
