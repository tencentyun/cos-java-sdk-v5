package com.qcloud.cos.model.ciModel.template;

/**
 * 水印图片实体类 参数详情见: https://cloud.tencent.com/document/product/460/48176
 */
public class MediaWaterMarkImage {

    /**
     * 水印地址 需为桶bucket的水印图片地址
     */
    private String url;
    /**
     * 尺寸模式
     */
    private String mode;
    /**
     * 宽
     */
    private String width;
    /**
     * 搞
     */
    private String height;
    /**
     * 透明度
     */
    private String transparency;
    /**
     * 是否背景图 true、false
     */
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
