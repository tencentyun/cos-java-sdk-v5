package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 媒体处理 任务video响应实体 https://cloud.tencent.com/document/product/460/48234
 */
public class MediaVideoObject implements Serializable {

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
    @XStreamAlias("Qality")
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
    @XStreamAlias("BufSize")
    private String bufSize;

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

    @XStreamAlias("HlsTsTime")
    private String hlsTsTime;

    @XStreamAlias("Language")
    private String language;

    @XStreamAlias("Rotate")
    private String rotate;

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
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

    public String getBufSize() {
        return bufSize;
    }

    public void setBufSize(String bufSize) {
        this.bufSize = bufSize;
    }

    public String getHlsTsTime() {
        return hlsTsTime;
    }

    public void setHlsTsTime(String hlsTsTime) {
        this.hlsTsTime = hlsTsTime;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaVideoObject{");
        sb.append("codec='").append(codec).append('\'');
        sb.append(", width='").append(width).append('\'');
        sb.append(", height='").append(height).append('\'');
        sb.append(", fps='").append(fps).append('\'');
        sb.append(", animateOnlyKeepKeyFrame='").append(animateOnlyKeepKeyFrame).append('\'');
        sb.append(", animateTimeIntervalOfFrame='").append(animateTimeIntervalOfFrame).append('\'');
        sb.append(", animateFramesPerSecond='").append(animateFramesPerSecond).append('\'');
        sb.append(", qality='").append(qality).append('\'');
        sb.append(", remove='").append(remove).append('\'');
        sb.append(", profile='").append(profile).append('\'');
        sb.append(", bitrate='").append(bitrate).append('\'');
        sb.append(", crf='").append(crf).append('\'');
        sb.append(", gop='").append(gop).append('\'');
        sb.append(", preset='").append(preset).append('\'');
        sb.append(", bufSize='").append(bufSize).append('\'');
        sb.append(", maxrate='").append(maxrate).append('\'');
        sb.append(", scanMode='").append(scanMode).append('\'');
        sb.append(", pixFmt='").append(pixFmt).append('\'');
        sb.append(", quality='").append(quality).append('\'');
        sb.append(", longShortMode='").append(longShortMode).append('\'');
        sb.append(", pad='").append(pad).append('\'');
        sb.append(", crop='").append(crop).append('\'');
        sb.append(", hlsTsTime='").append(hlsTsTime).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
