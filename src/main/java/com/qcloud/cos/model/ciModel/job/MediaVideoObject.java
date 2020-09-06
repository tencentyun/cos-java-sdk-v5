package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 视频信息实体类
 */
public class MediaVideoObject implements Serializable {

    /**
     * 编解码格式 仅限 gif，webp
     */
    @XStreamAlias("Codec")
    private String codec;
    /**
     * 宽
     */
    @XStreamAlias("Width")
    private String width;
    /**
     * 高
     */
    @XStreamAlias("Height")
    private String height;
    /**
     * 帧率
     */
    @XStreamAlias("Fps")
    private String fps;

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
    @XStreamAlias("Bufsize")
    private String bufsize;
    /**
     * 视频码率峰值
     */
    @XStreamAlias("Maxrate")
    private String maxrate;

    @XStreamAlias("ScanMode")
    private String scanMode;

    @XStreamAlias("PixFmt")
    private String pixFmt;

    @XStreamAlias("Quality")
    private String quality;

    @XStreamAlias("LongShortMode")
    private String longShortMode;

    @XStreamAlias("Pad")
    private String pad;

    @XStreamAlias("Crop")
    private String crop;

    public String getBufsize() {
        return bufsize;
    }

    public void setBufsize(String bufsize) {
        this.bufsize = bufsize;
    }

    public String getScanMode() {
        return scanMode;
    }

    public void setScanMode(String scanMode) {
        this.scanMode = scanMode;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
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

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

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
        return bufsize;
    }

    public void setBufSize(String bufSize) {
        this.bufsize = bufSize;
    }

    public String getMaxrate() {
        return maxrate;
    }

    public void setMaxrate(String maxrate) {
        this.maxrate = maxrate;
    }

    public String getPixFmt() {
        return pixFmt;
    }

    public void setPixFmt(String pixFmt) {
        this.pixFmt = pixFmt;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getLongShortMode() {
        return longShortMode;
    }

    public void setLongShortMode(String longShortMode) {
        this.longShortMode = longShortMode;
    }

    public String getPad() {
        return pad;
    }

    public void setPad(String pad) {
        this.pad = pad;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    @Override
    public String toString() {
        return "MediaVideoObject{" +
                "codec='" + codec + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", fps='" + fps + '\'' +
                ", animateOnlyKeepKeyFrame='" + animateOnlyKeepKeyFrame + '\'' +
                ", animateTimeIntervalOfFrame='" + animateTimeIntervalOfFrame + '\'' +
                ", animateFramesPerSecond='" + animateFramesPerSecond + '\'' +
                ", qality='" + qality + '\'' +
                ", remove='" + remove + '\'' +
                ", profile='" + profile + '\'' +
                ", bitrate='" + bitrate + '\'' +
                ", crf='" + crf + '\'' +
                ", gop='" + gop + '\'' +
                ", preset='" + preset + '\'' +
                ", bufsize='" + bufsize + '\'' +
                ", maxrate='" + maxrate + '\'' +
                ", scanMode='" + scanMode + '\'' +
                ", pixFmt='" + pixFmt + '\'' +
                ", quality='" + quality + '\'' +
                ", longShortMode='" + longShortMode + '\'' +
                ", pad='" + pad + '\'' +
                ", crop='" + crop + '\'' +
                '}';
    }
}
