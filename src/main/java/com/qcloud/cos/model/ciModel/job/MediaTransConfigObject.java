package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 媒体处理 任务转码配置实体 https://cloud.tencent.com/document/product/460/48234
 */
public class MediaTransConfigObject implements Serializable {
    /**
     * 分辨率调整方式 取值 scale、crop、pad、none
     * 当输出视频的宽高比与原视频不等时，根据此参数做分辨率的相应调整
     */
    @XStreamAlias("AdjDarMethod")
    private String adjDarMethod;
    /**
     * 是否检查分辨率 true、false
     * 当为 false时，按照配置参数转码
     */
    @XStreamAlias("IsCheckReso")
    private String isCheckReso;
    /**
     * 分辨率调整方式 取值0、1；0 表示使用原视频分辨率；
     * 1表示返回转码失败
     * 当 IsCheckReso 为 true 时生效
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
    /**
     * 是否删除文件中的 MetaData 信息
     * true、false
     * 当为 false 时, 保留源文件信息
     */
    @XStreamAlias("DeleteMetadata")
    private String deleteMetadata;
    /**
     * 是否开启 HDR 转 SDR	 true/false
     */
    @XStreamAlias("IsHdr2Sdr")
    private String isHdr2Sdr;
    /**
     * hls 加密配置
     */
    @XStreamAlias("HlsEncrypt")
    private HlsEncrypt hlsEncrypt;

    @XStreamAlias("InitialClipNum")
    private String initialClipNum;

    @XStreamAlias("CosTag")
    private String cosTag;

    public String getInitialClipNum() {
        return initialClipNum;
    }

    public void setInitialClipNum(String initialClipNum) {
        this.initialClipNum = initialClipNum;
    }

    public String getCosTag() {
        return cosTag;
    }

    public void setCosTag(String cosTag) {
        this.cosTag = cosTag;
    }

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

    public String getDeleteMetadata() {
        return deleteMetadata;
    }

    public void setDeleteMetadata(String deleteMetadata) {
        this.deleteMetadata = deleteMetadata;
    }

    public String getIsHdr2Sdr() {
        return isHdr2Sdr;
    }

    public void setIsHdr2Sdr(String isHdr2Sdr) {
        this.isHdr2Sdr = isHdr2Sdr;
    }

    public HlsEncrypt getHlsEncrypt() {
        if (hlsEncrypt == null) {
            hlsEncrypt = new HlsEncrypt();
        }
        return hlsEncrypt;
    }

    public void setHlsEncrypt(HlsEncrypt hlsEncrypt) {
        this.hlsEncrypt = hlsEncrypt;
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
