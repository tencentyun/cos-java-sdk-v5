package com.qcloud.cos.model.ciModel.job;

import java.io.Serializable;
/**
 * 媒体处理 任务转码配置实体 https://cloud.tencent.com/document/product/460/48234
 */
public class MediaTransConfigObject implements Serializable {

    /**
     * 分辨率调整方式
     */
    private String adjDarMethod;
    /**
     * 是否检查分辨率
     */
    private String isCheckReso;
    /**
     * 分辨率调整方式
     */
    private String resoAdjMethod;
    /**
     * 是否检查视频码率
     */
    private String isCheckVideoBitrate;
    /**
     * 视频码率调整方式
     */
    private String videoBitrateAdjMethod;
    /**
     * 是否检查音频码率
     */
    private String isCheckAudioBitrate;
    /**
     * 音频码率调整方式
     */
    private String audioBitrateAdjMethod;

    private String transMode;

    public String getAdjDarMethod() {
        return adjDarMethod;
    }

    public void setAdjDarMethod(String adjDarMethod) {
        this.adjDarMethod = adjDarMethod;
    }

    public String getIsCheckReso() {
        return isCheckReso;
    }

    public void setIsCheckReso(String isCheckReso) {
        this.isCheckReso = isCheckReso;
    }

    public String getResoAdjMethod() {
        return resoAdjMethod;
    }

    public void setResoAdjMethod(String resoAdjMethod) {
        this.resoAdjMethod = resoAdjMethod;
    }

    public String getIsCheckVideoBitrate() {
        return isCheckVideoBitrate;
    }

    public void setIsCheckVideoBitrate(String isCheckVideoBitrate) {
        this.isCheckVideoBitrate = isCheckVideoBitrate;
    }

    public String getVideoBitrateAdjMethod() {
        return videoBitrateAdjMethod;
    }

    public void setVideoBitrateAdjMethod(String videoBitrateAdjMethod) {
        this.videoBitrateAdjMethod = videoBitrateAdjMethod;
    }

    public String getIsCheckAudioBitrate() {
        return isCheckAudioBitrate;
    }

    public void setIsCheckAudioBitrate(String isCheckAudioBitrate) {
        this.isCheckAudioBitrate = isCheckAudioBitrate;
    }

    public String getAudioBitrateAdjMethod() {
        return audioBitrateAdjMethod;
    }

    public void setAudioBitrateAdjMethod(String audioBitrateAdjMethod) {
        this.audioBitrateAdjMethod = audioBitrateAdjMethod;
    }

    public String getTransMode() {
        return transMode;
    }

    public void setTransMode(String transMode) {
        this.transMode = transMode;
    }

    @Override
    public String toString() {
        return "MediaTransConfigObject{" +
                "adjDarMethod='" + adjDarMethod + '\'' +
                ", isCheckReso='" + isCheckReso + '\'' +
                ", resoAdjMethod='" + resoAdjMethod + '\'' +
                ", isCheckVideoBitrate='" + isCheckVideoBitrate + '\'' +
                ", videoBitrateAdjMethod='" + videoBitrateAdjMethod + '\'' +
                ", isCheckAudioBitrate='" + isCheckAudioBitrate + '\'' +
                ", audioBitrateAdjMethod='" + audioBitrateAdjMethod + '\'' +
                '}';
    }
}
