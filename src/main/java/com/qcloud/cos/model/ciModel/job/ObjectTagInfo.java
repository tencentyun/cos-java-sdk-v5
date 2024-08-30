package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class ObjectTagInfo {
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("Confidence")
    private String confidence;

    @XStreamImplicit(itemFieldName = "BBox")
    private List<BBox> bbox;

    public List<BBox> getBbox() {
        return bbox;
    }

    public void setBbox(List<BBox> bbox) {
        this.bbox = bbox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
