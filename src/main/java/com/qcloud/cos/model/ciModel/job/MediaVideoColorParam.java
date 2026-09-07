package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 媒体处理 视频颜色参数 https://cloud.tencent.com/document/product/460/84733#.E8.AF.B7.E6.B1.82
 */
public class MediaVideoColorParam implements Serializable {

    /**
     * 色彩空间
     */
    @XStreamAlias("ColorSpace")
    private String colorSpace;

    /**
     * 色彩范围
     */
    @XStreamAlias("ColorRange")
    private String colorRange;

    /**
     * 色彩轨迹
     */
    @XStreamAlias("ColorTrc")
    private String colorTrc;

    /**
     * 色彩原色
     */
    @XStreamAlias("ColorPrimaries")
    private String colorPrimaries;

    public String getColorSpace() {
        return colorSpace;
    }

    public void setColorSpace(String colorSpace) {
        this.colorSpace = colorSpace;
    }

    public String getColorRange() {
        return colorRange;
    }

    public void setColorRange(String colorRange) {
        this.colorRange = colorRange;
    }

    public String getColorTrc() {
        return colorTrc;
    }

    public void setColorTrc(String colorTrc) {
        this.colorTrc = colorTrc;
    }

    public String getColorPrimaries() {
        return colorPrimaries;
    }

    public void setColorPrimaries(String colorPrimaries) {
        this.colorPrimaries = colorPrimaries;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaVideoColorParam{");
        sb.append("colorSpace='").append(colorSpace).append('\'');
        sb.append(", colorRange='").append(colorRange).append('\'');
        sb.append(", colorTrc='").append(colorTrc).append('\'');
        sb.append(", colorPrimaries='").append(colorPrimaries).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
