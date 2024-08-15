package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Subtitle {
    @XStreamAlias("Url")
    private String url;
    @XStreamAlias("Embed")
    private String embed;
    @XStreamAlias("FontType")
    private String fontType;
    @XStreamAlias("FontSize")
    private String fontSize;
    @XStreamAlias("FontColor")
    private String fontColor;
    @XStreamAlias("OutlineColor")
    private String outlineColor;
    @XStreamAlias("VMargin")
    private String vMargin;

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
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

    public String getOutlineColor() {
        return outlineColor;
    }

    public void setOutlineColor(String outlineColor) {
        this.outlineColor = outlineColor;
    }

    public String getvMargin() {
        return vMargin;
    }

    public void setvMargin(String vMargin) {
        this.vMargin = vMargin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
