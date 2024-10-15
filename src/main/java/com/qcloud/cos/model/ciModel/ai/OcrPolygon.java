package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OcrPolygon {
    @XStreamAlias("X")
    private Integer x; // 横坐标

    @XStreamAlias("Y")
    private Integer y; // 纵坐标

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
