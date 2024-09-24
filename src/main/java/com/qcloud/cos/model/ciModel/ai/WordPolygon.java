package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class WordPolygon {
    @XStreamAlias("LeftTop")
    private OcrPolygon leftTop; // 左上顶点坐标

    @XStreamAlias("RightTop")
    private OcrPolygon rightTop; // 右上顶点坐标

    @XStreamAlias("RightBottom")
    private OcrPolygon rightBottom; // 右下顶点坐标

    @XStreamAlias("LeftBottom")
    private OcrPolygon leftBottom; // 左下顶点坐标

    public OcrPolygon getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(OcrPolygon leftTop) {
        this.leftTop = leftTop;
    }

    public OcrPolygon getRightTop() {
        return rightTop;
    }

    public void setRightTop(OcrPolygon rightTop) {
        this.rightTop = rightTop;
    }

    public OcrPolygon getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(OcrPolygon rightBottom) {
        this.rightBottom = rightBottom;
    }

    public OcrPolygon getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(OcrPolygon leftBottom) {
        this.leftBottom = leftBottom;
    }
}
