package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ItemPolygon extends OcrPolygon{

    @XStreamAlias("Width")
    private Integer width; // 宽

    @XStreamAlias("Height")
    private Integer height; // 高

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
