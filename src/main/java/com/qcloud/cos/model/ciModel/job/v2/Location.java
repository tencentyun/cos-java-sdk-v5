package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Location {
    @XStreamAlias("X")
    private String x; // X 坐标

    @XStreamAlias("Y")
    private String y; // Y 坐标

    @XStreamAlias("Height")
    private String height; // (X,Y)坐标距离高度

    @XStreamAlias("Width")
    private String width; // (X,Y)坐标距离长度

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
