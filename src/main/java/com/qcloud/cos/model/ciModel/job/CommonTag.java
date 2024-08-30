package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class CommonTag {
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("Confidence")
    private String confidence;
    @XStreamAlias("Count")
    private String count;
    @XStreamImplicit(itemFieldName = "DetailPerSecond")
    List<DetailPerSecond> DetailPerSecondList;

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<DetailPerSecond> getDetailPerSecondList() {
        return DetailPerSecondList;
    }

    public void setDetailPerSecondList(List<DetailPerSecond> detailPerSecondList) {
        DetailPerSecondList = detailPerSecondList;
    }
}
