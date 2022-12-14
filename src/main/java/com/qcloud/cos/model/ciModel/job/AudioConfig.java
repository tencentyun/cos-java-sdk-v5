package com.qcloud.cos.model.ciModel.job;

public class AudioConfig {
    /**
     * 取值 aac、mp3、flac、amr
     */
    private String codec;

    /**
     * 1. 单位：Hz
     * 2. 可选 8000、11025、22050、32000、44100、48000、96000
     * 3. 当 Codec 设置为 aac/flac 时，不支持8000
     * 4. 当 Codec 设置为 mp3 时，不支持8000和96000
     * 5. 当 Codec 设置为 amr 时，只支持8000
     */
    private String samplerate;

    /**
     * 1. 单位：Kbps
     * 2. 值范围：[8，1000]
     */
    private String bitrate;

    /**
     * 1. 当 Codec 设置为 aac/flac，支持1、2、4、5、6、8
     * 2. 当 Codec 设置为 mp3，支持1、2
     * 3. 当 Codec 设置为 amr，只支持1
     */
    private String channels;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AudioConfig{");
        sb.append("codec='").append(codec).append('\'');
        sb.append(", samplerate='").append(samplerate).append('\'');
        sb.append(", bitrate='").append(bitrate).append('\'');
        sb.append(", channels='").append(channels).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
