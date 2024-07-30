package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class ImageInfo {
    @XStreamAlias("Format")
    private String format;

    @XStreamAlias("Width")
    private int width;

    @XStreamAlias("Height")
    private int height;

    @XStreamAlias("Quality")
    private int quality;

    @XStreamAlias("Ave")
    private String ave;

    @XStreamAlias("Orientation")
    private int orientation;

    @XStreamAlias("FrameCount")
    private int frameCount;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getAve() {
        return ave;
    }

    public void setAve(String ave) {
        this.ave = ave;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }
}
