package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PosterProductionInfo {
    
    /**
     * 主图片URL
     */
    @XStreamAlias("main")
    private String main;
    
    /**
     * 主文本内容
     */
    @XStreamAlias("text_main")
    private String textMain;
    
    /**
     * 副文本内容
     */
    @XStreamAlias("text_sub")
    private String textSub;
    
    /**
     * 背景图片URL
     */
    @XStreamAlias("background")
    private String background;
    
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

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getTextMain() {
        return textMain;
    }

    public void setTextMain(String textMain) {
        this.textMain = textMain;
    }

    public String getTextSub() {
        return textSub;
    }

    public void setTextSub(String textSub) {
        this.textSub = textSub;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
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
}
