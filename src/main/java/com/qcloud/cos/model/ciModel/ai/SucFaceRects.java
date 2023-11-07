package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SucFaceRects {
    @XStreamAlias("X")
    private Integer x;
    @XStreamAlias("Y")
    private Integer y;
    @XStreamAlias("Width")
    private Integer width;
    @XStreamAlias("Height")
    private Integer height;

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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
