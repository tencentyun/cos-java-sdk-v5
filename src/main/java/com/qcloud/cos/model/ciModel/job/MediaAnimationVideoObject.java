package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaAnimationVideoObject extends MediaVideoCommon {

    /**
     * 动图只保留关键帧
     */
    @XStreamAlias("AnimateOnlyKeepKeyFrame")
    private String animateOnlyKeepKeyFrame;
    /**
     * 动图抽帧间隔时间
     */
    @XStreamAlias("AnimateTimeIntervalOfFrame")
    private String animateTimeIntervalOfFrame;
    /**
     * Animation 每秒抽帧帧数
     */
    @XStreamAlias("AnimateFramesPerSecond")
    private String animateFramesPerSecond;
    /**
     * 设置相对质量
     */
    @XStreamAlias("Gality")
    private String qality;

    public String getAnimateOnlyKeepKeyFrame() {
        return animateOnlyKeepKeyFrame;
    }

    public void setAnimateOnlyKeepKeyFrame(String animateOnlyKeepKeyFrame) {
        this.animateOnlyKeepKeyFrame = animateOnlyKeepKeyFrame;
    }

    public String getAnimateTimeIntervalOfFrame() {
        return animateTimeIntervalOfFrame;
    }

    public void setAnimateTimeIntervalOfFrame(String animateTimeIntervalOfFrame) {
        this.animateTimeIntervalOfFrame = animateTimeIntervalOfFrame;
    }

    public String getAnimateFramesPerSecond() {
        return animateFramesPerSecond;
    }

    public void setAnimateFramesPerSecond(String animateFramesPerSecond) {
        this.animateFramesPerSecond = animateFramesPerSecond;
    }

    public String getQality() {
        return qality;
    }

    public void setQality(String qality) {
        this.qality = qality;
    }
}
