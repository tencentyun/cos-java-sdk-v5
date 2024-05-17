package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 媒体处理 任务转码实体 https://cloud.tencent.com/document/product/460/48234
 */
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

    /**
     * 视频颜色格式
     */
    @XStreamAlias("PixFmt")
    private String pixfmt;
    /**
     * 长短边自适应
     */
    @XStreamAlias("LongShortMode")
    private String longShortMode;
    /**
     * 旋转角度
     */
    @XStreamAlias("Rotate")
    private String rotate;

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


    public String getPixfmt() {
        return pixfmt;
    }

    public void setPixfmt(String pixfmt) {
        this.pixfmt = pixfmt;
    }

    public String getLongShortMode() {
        return longShortMode;
    }

    public void setLongShortMode(String longShortMode) {
        this.longShortMode = longShortMode;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaTranscodeVideoObject{");
        sb.append("remove='").append(remove).append('\'');
        sb.append(", profile='").append(profile).append('\'');
        sb.append(", bitrate='").append(bitrate).append('\'');
        sb.append(", crf='").append(crf).append('\'');
        sb.append(", gop='").append(gop).append('\'');
        sb.append(", preset='").append(preset).append('\'');
        sb.append(", bufSize='").append(bufSize).append('\'');
        sb.append(", maxrate='").append(maxrate).append('\'');
        sb.append(", scanMode='").append(scanMode).append('\'');
        sb.append(", pixfmt='").append(pixfmt).append('\'');
        sb.append(", longShortMode='").append(longShortMode).append('\'');
        sb.append(", rotate='").append(rotate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
