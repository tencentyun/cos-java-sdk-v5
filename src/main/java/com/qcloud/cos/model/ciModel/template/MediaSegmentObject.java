package com.qcloud.cos.model.ciModel.template;


import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaSegmentObject {

    /**
     * 封装格式 aac、mp3、flac、mp4、ts、mkv、avi、hls、m3u8
     */
    @XStreamAlias("Format")
    private String format;

    /**
     * 转封装时长，单位：秒  不小于5的整数
     */
    @XStreamAlias("Duration")
    private String duration;

    /**
     * 处理的流编号
     */
    @XStreamAlias("TranscodeIndex")
    private String transcodeIndex;

    /**
     * 开始时间
     * 取值范围： [0,视频时长]，默认值为0
     * 单位为秒
     * 支持 float 格式，执行精度精确到毫秒
     */
    @XStreamAlias("StartTime")
    private String startTime;

    /**
     * Request.Operation.Segment
     * 结束时间
     * 取值范围：[0, 视频时长]，默认值为视频结束时间
     * 单位为秒
     * 支持 float 格式，执行精度精确到毫秒
     */
    @XStreamAlias("EndTime")
    private String endTime;

    @XStreamAlias("MediaHlsEncrypt")
    private MediaHlsEncryptObject hlsEncrypt;
    public String getTranscodeIndex() {
        return transcodeIndex;
    }

    public void setTranscodeIndex(String transcodeIndex) {
        this.transcodeIndex = transcodeIndex;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public MediaHlsEncryptObject getHlsEncrypt() {
        if (hlsEncrypt == null) {
            hlsEncrypt = new MediaHlsEncryptObject();
        }
        return hlsEncrypt;
    }

    public void setHlsEncrypt(MediaHlsEncryptObject hlsEncrypt) {
        this.hlsEncrypt = hlsEncrypt;
    }


    @Override
    public String toString() {
        return "MediaSegmentObject{" +
                "format='" + format + '\'' +
                ", duration='" + duration + '\'' +
                ", hlsEncrypt=" + hlsEncrypt +
                '}';
    }
}
