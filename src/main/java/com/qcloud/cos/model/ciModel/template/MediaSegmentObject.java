package com.qcloud.cos.model.ciModel.template;


public class MediaSegmentObject {

    /**
     * 封装格式	aac、mp3、flac、mp4、ts、mkv、avi、hls、m3u8
     */
    private String format;

    /**
     * 转封装时长，单位：秒  不小于5的整数
     */
    private String duration;

    private MediaHlsEncryptObject hlsEncrypt;

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
