package com.qcloud.cos.model.ciModel.mediaInfo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * MediaInfo 格式详情实体类 详情见：https://cloud.tencent.com/document/product/460/38935
 */
public class MediaInfoAudio {
    /**
     * 比特率，单位为 kbps
     */
    @XStreamAlias("Bitrate")
    private String bitrate;
    /**
     * 通道数量
     */
    @XStreamAlias("Channel")
    private String channel;

    /**
     * 通道格式
     */
    @XStreamAlias("ChannelLayout")
    private String channelLayout;

    /**
     * 编解码格式的详细名称
     */
    @XStreamAlias("CodecLongName")
    private String codecLongName;

    /**
     * 编解码格式名称
     */
    @XStreamAlias("CodecName")
    private String codecName;

    /**
     * 编码标签
     */
    @XStreamAlias("CodecTag")
    private String codecTag;

    /**
     * 编码标签名
     */
    @XStreamAlias("CodecTagString")
    private String codecTagString;

    /**
     * 编码时基
     */
    @XStreamAlias("CodecTimeBase")
    private String codecTimeBase;

    /**
     * 音频时长，单位为秒
     */
    @XStreamAlias("Duration")
    private String duration;

    /**
     * 该流的编号
     */
    @XStreamAlias("Index")
    private String index;

    /**
     * 采样格式
     */
    @XStreamAlias("SampleFmt")
    private String sampleFmt;

    /**
     * 采样率
     */
    @XStreamAlias("SampleRate")
    private String sampleRate;

    /**
     * 音频开始时间，单位为秒
     */
    @XStreamAlias("StartTime")
    private String startTime;

    /**
     * 时基
     */
    @XStreamAlias("Timebase")
    private String timebase;

    /**
     * 语言
     */
    @XStreamAlias("Language")
    private String language;

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelLayout() {
        return channelLayout;
    }

    public void setChannelLayout(String channelLayout) {
        this.channelLayout = channelLayout;
    }

    public String getCodecLongName() {
        return codecLongName;
    }

    public void setCodecLongName(String codecLongName) {
        this.codecLongName = codecLongName;
    }

    public String getCodecName() {
        return codecName;
    }

    public void setCodecName(String codecName) {
        this.codecName = codecName;
    }

    public String getCodecTag() {
        return codecTag;
    }

    public void setCodecTag(String codecTag) {
        this.codecTag = codecTag;
    }

    public String getCodecTagString() {
        return codecTagString;
    }

    public void setCodecTagString(String codecTagString) {
        this.codecTagString = codecTagString;
    }

    public String getCodecTimeBase() {
        return codecTimeBase;
    }

    public void setCodecTimeBase(String codecTimeBase) {
        this.codecTimeBase = codecTimeBase;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSampleFmt() {
        return sampleFmt;
    }

    public void setSampleFmt(String sampleFmt) {
        this.sampleFmt = sampleFmt;
    }

    public String getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(String sampleRate) {
        this.sampleRate = sampleRate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTimebase() {
        return timebase;
    }

    public void setTimebase(String timebase) {
        this.timebase = timebase;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "MediaInfoAudio{" +
                "bitrate='" + bitrate + '\'' +
                ", channel='" + channel + '\'' +
                ", channelLayout='" + channelLayout + '\'' +
                ", codecLongName='" + codecLongName + '\'' +
                ", codecName='" + codecName + '\'' +
                ", codecTag='" + codecTag + '\'' +
                ", codecTagString='" + codecTagString + '\'' +
                ", codecTimeBase='" + codecTimeBase + '\'' +
                ", duration='" + duration + '\'' +
                ", index='" + index + '\'' +
                ", sampleFmt='" + sampleFmt + '\'' +
                ", sampleRate='" + sampleRate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", timebase='" + timebase + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
