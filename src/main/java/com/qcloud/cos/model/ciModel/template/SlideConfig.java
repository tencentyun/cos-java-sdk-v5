package com.qcloud.cos.model.ciModel.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SlideConfig {
    @XStreamAlias("SlideMode")
    /**
     * 幻灯片模式
     */
    private String slideMode;

    @XStreamAlias("XSlideSpeed")
    /**
     * X轴滑动速度
     */
    private String xSlideSpeed;

    @XStreamAlias("YSlideSpeed")
    /**
     * Y轴滑动速度
     */
    private String ySlideSpeed;

    public String getSlideMode() {
        return slideMode;
    }

    public void setSlideMode(String slideMode) {
        this.slideMode = slideMode;
    }

    public String getxSlideSpeed() {
        return xSlideSpeed;
    }

    public void setxSlideSpeed(String xSlideSpeed) {
        this.xSlideSpeed = xSlideSpeed;
    }

    public String getySlideSpeed() {
        return ySlideSpeed;
    }

    public void setySlideSpeed(String ySlideSpeed) {
        this.ySlideSpeed = ySlideSpeed;
    }
}
