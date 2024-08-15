package com.qcloud.cos.model.ciModel.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SlideConfig {
    /**
     * 幻灯片模式
     */
    @XStreamAlias("SlideMode")
    private String slideMode;

    /**
     * X轴滑动速度
     */
    @XStreamAlias("XSlideSpeed")
    private String xSlideSpeed;

    /**
     * Y轴滑动速度
     */
    @XStreamAlias("YSlideSpeed")
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
