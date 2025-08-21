
package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ImageInfo")
public class ImageInfo {
    @XStreamAlias("Format")
    private String format;

    @XStreamAlias("Height")
    private String height;

    @XStreamAlias("Width")
    private String width;

    @XStreamAlias("Md5")
    private String md5;

    @XStreamAlias("FrameCount")
    private String frameCount;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(String frameCount) {
        this.frameCount = frameCount;
    }
}