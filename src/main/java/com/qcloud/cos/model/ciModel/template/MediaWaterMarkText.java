package com.qcloud.cos.model.ciModel.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 水印Text实体类 https://cloud.tencent.com/document/product/460/48176
 */
public class MediaWaterMarkText {
    /**
     * 水印text节点
     * 水印类内容 长度不超过64个字符，仅支持中文、英文、数字、_、-和*
     */
    @XStreamAlias("Text")
    private String text;

    /**
     * 字体大小 值范围：[5 100]，单位为px
     */
    @XStreamAlias("FontSize")
    private String fontSize;

    /**
     * 字体颜色
     */
    @XStreamAlias("FontColor")
    private String fontColor;

    /**
     * 字体
     */
    @XStreamAlias("FontType")
    private String fontType;

    /**
     * 透明度 值范围：[1 100]，单位为%
     */
    @XStreamAlias("Transparency")
    private String transparency;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getTransparency() {
        return transparency;
    }

    public void setTransparency(String transparency) {
        this.transparency = transparency;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    @Override
    public String toString() {
        return "MediaWaterMarkText{" + "text='" + text + '\'' + ", fontSize='" + fontSize + '\'' + ", fontColor='" + fontColor + '\'' + ", transparency='" + transparency + '\'' + '}';
    }
}
