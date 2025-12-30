package com.qcloud.cos.model.ciModel.recognition;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class CodeLocation {
    @XStreamImplicit(itemFieldName = "Point")
    List<String> points;

    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }
}
