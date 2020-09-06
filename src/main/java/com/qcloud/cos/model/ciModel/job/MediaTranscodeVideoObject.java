package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaTranscodeVideoObject extends MediaVideoCommon {
    /**
     * 是否删除视频流
     */
    @XStreamAlias("Remove")
    private String remove;
    /**
     * 编码级别
     */
    @XStreamAlias("Profile")
    private String profile;
    /**
     * 视频输出文件的码率
     */
    @XStreamAlias("Bitrate")
    private String bitrate;
    /**
     * 码率-质量控制因子
     */
    @XStreamAlias("Crf")
    private String crf;
    /**
     * 关键帧间最大帧数
     */
    @XStreamAlias("Gop")
    private String gop;
    /**
     * 视频算法器预置
     */
    @XStreamAlias("Preset")
    private String preset;
    /**
     * 缓冲区大小
     */
    @XStreamAlias("BufSize")
    private String bufSize;
    /**
     * 视频码率峰值
     */
    @XStreamAlias("Maxrate")
    private String maxrate;

    @XStreamAlias("ScanMode")
    private String scanMode;



    public String getRemove() {
        return remove;
    }

    public void setRemove(String remove) {
        this.remove = remove;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getCrf() {
        return crf;
    }

    public void setCrf(String crf) {
        this.crf = crf;
    }

    public String getGop() {
        return gop;
    }

    public void setGop(String gop) {
        this.gop = gop;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    public String getBufSize() {
        return bufSize;
    }

    public void setBufSize(String bufSize) {
        this.bufSize = bufSize;
    }

    public String getMaxrate() {
        return maxrate;
    }

    public void setMaxrate(String maxrate) {
        this.maxrate = maxrate;
    }

    public String getScanMode() {
        return scanMode;
    }

    public void setScanMode(String scanMode) {
        this.scanMode = scanMode;
    }

    @Override
    public String toString() {
        return "MediaTranscodeVideoObject{" +
                "remove='" + remove + '\'' +
                ", profile='" + profile + '\'' +
                ", bitrate='" + bitrate + '\'' +
                ", crf='" + crf + '\'' +
                ", gop='" + gop + '\'' +
                ", preset='" + preset + '\'' +
                ", bufSize='" + bufSize + '\'' +
                ", maxrate='" + maxrate + '\'' +
                ", scanMode='" + scanMode + '\'' +
                '}';
    }
}
