package com.qcloud.cos.model.ciModel.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 水印图片实体类 参数详情见: https://cloud.tencent.com/document/product/460/48176
 */
public class MediaWaterMarkImage {

    /**
     * 透明度 值范围：[1 100]，单位为%
     */
    @XStreamAlias("Transparency")
    private String transparency;

    /**
     * 水印地址 需为桶bucket的水印图片地址
     */
    @XStreamAlias("Url")
    private String url;

    /**
     * 尺寸模式
     */
    @XStreamAlias("Mode")
    private String mode;

    /**
     * 宽
     */
    @XStreamAlias("Width")
    private String width;

    /**
     * 高
     */
    @XStreamAlias("Height")
    private String height;

    /**
     * 是否背景图 true、false
     */
    @XStreamAlias("Background")
    private String background;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

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

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return "MediaWaterMarkImage{" +
                "url='" + url + '\'' +
                ", mode='" + mode + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", transparency='" + transparency + '\'' +
                ", background='" + background + '\'' +
                '}';
    }
}
