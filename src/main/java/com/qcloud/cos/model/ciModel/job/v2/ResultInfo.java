package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ResultInfo {
    @XStreamAlias("Name")
    private String name; // 识别类型

    @XStreamAlias("Score")
    private int score; // 识别的置信度

    @XStreamAlias("Location")
    private Location location; // 图中识别到的坐标

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
