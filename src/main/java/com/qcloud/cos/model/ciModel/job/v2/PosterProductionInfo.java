package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PosterProductionInfo {

    /**
     * 图片宽度
     */
    @XStreamAlias("width")
    private String width;

    /**
     * 图片高度
     */
    @XStreamAlias("height")
    private String height;

    /**
     * 图片名称
     */
    @XStreamAlias("name")
    private String name;

    /**
     * 图片缩略图
     */
    @XStreamAlias("thumb")
    private String thumb;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Override
    public String toString() {
        return "PosterProductionInfo{" +
                "width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", name='" + name + '\'' +
                ", thumb='" + thumb + '\'' +
                '}';
    }
}
