package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 媒体处理 Audio实体 https://cloud.tencent.com/document/product/460/48234
 */

public class MediaAudioObject implements Serializable {

    /**
     * 编解码格式
     */
    @XStreamAlias("Codec")
    private String codec;

    /**
     * 采样率
     * 单位：Hz
     * 可选 8000、11025、12000、16000、22050、24000、32000、44100、48000、88200、96000
     * 不同的封装，mp3 支持不同的采样率，如下表所示
     * 当 Codec 设置为 amr 时，只支持8000
     * 当 Codec 设置为 opus 时，仅支持8000，16000，24000，48000
     */
    @XStreamAlias("Samplerate")
    private String samplerate;

    /**
     * 原始音频码率
     */
    @XStreamAlias("Bitrate")
    private String bitrate;

    /**
     * 声道数
     */
    @XStreamAlias("Channels")
    private String channels;

    /**
     * 是否删除音频流
     */
    @XStreamAlias("Remove")
    private String remove;

    @XStreamAlias("Profile")
    private String profile;

    /**
     * 保持双音轨
     * 取值 true、false。 当 Video.Codec 为H.265时，此参数无效。
     */
    @XStreamAlias("KeepTwoTracks")
    private String keepTwoTracks;

    /**
     * 转换轨道
     * 取值 true、false。 当 Video.Codec 为H.265时，此参数无效。
     */
    @XStreamAlias("SwitchTrack")
    private String switchTrack;

    /**
     * 采样位宽
     * 当 Codec 设置为 aac, 支持 fltp
     * 当 Codec 设置为 mp3, 支持 fltp、s16p、s32p
     * 当 Codec 设置为 flac, 支持s16、s32
     * 当 Codec 设置为 amr, 支持s16
     * 当 Video.Codec 为H.265时，此参数无效
     */
    @XStreamAlias("SampleFormat")
    private String sampleFormat;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getSamplerate() {
        return samplerate;
    }

    public void setSamplerate(String samplerate) {
        this.samplerate = samplerate;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getRemove() {
        return remove;
    }

    public void setRemove(String remove) {
        this.remove = remove;
    }

    public String getKeepTwoTracks() {
        return keepTwoTracks;
    }

    public void setKeepTwoTracks(String keepTwoTracks) {
        this.keepTwoTracks = keepTwoTracks;
    }

    public String getSwitchTrack() {
        return switchTrack;
    }

    public void setSwitchTrack(String switchTrack) {
        this.switchTrack = switchTrack;
    }

    public String getSampleFormat() {
        return sampleFormat;
    }

    public void setSampleFormat(String sampleFormat) {
        this.sampleFormat = sampleFormat;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaAudioObject{");
        sb.append("codec='").append(codec).append('\'');
        sb.append(", samplerate='").append(samplerate).append('\'');
        sb.append(", bitrate='").append(bitrate).append('\'');
        sb.append(", channels='").append(channels).append('\'');
        sb.append(", remove='").append(remove).append('\'');
        sb.append(", profile='").append(profile).append('\'');
        sb.append(", keepTwoTracks='").append(keepTwoTracks).append('\'');
        sb.append(", switchTrack='").append(switchTrack).append('\'');
        sb.append(", sampleFormat='").append(sampleFormat).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
