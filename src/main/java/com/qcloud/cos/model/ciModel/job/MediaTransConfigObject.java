package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class MediaTransConfigObject implements Serializable {

    /**
     * 分辨率调整方式
     */
    @XStreamAlias("AdjDarMethod")
    private String adjDarMethod;
    /**
     * 是否检查分辨率
     */
    @XStreamAlias("IsCheckReso")
    private String isCheckReso;
    /**
     * 分辨率调整方式
     */
    @XStreamAlias("ResoAdjMethod")
    private String resoAdjMethod;
    /**
     * 是否检查视频码率
     */
    @XStreamAlias("IsCheckVideoBitrate")
    private String isCheckVideoBitrate;
    /**
     * 视频码率调整方式
     */
    @XStreamAlias("VideoBitrateAdjMethod")
    private String videoBitrateAdjMethod;
    /**
     * 是否检查音频码率
     */
    @XStreamAlias("IsCheckAudioBitrate")
    private String isCheckAudioBitrate;
    /**
     * 音频码率调整方式
     */
    @XStreamAlias("AudioBitrateAdjMethod")
    private String audioBitrateAdjMethod;


    @XStreamAlias("TransMode")
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
