package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaContainerObject {

    /**
     * 容器格式：gif，hgif，webp。hgif 为高质量 gif，即清晰度比较高的 gif 格式图
     */
    @XStreamAlias("Format")
    private String format;

    /**
     * 分片配置,当format为hls和dash时有效
     */
    @XStreamAlias("ClipConfig")
    private MediaClipConfig clipConfig = new MediaClipConfig();

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public MediaClipConfig getClipConfig() {
        return clipConfig;
    }

    public void setClipConfig(MediaClipConfig clipConfig) {
        this.clipConfig = clipConfig;
    }

    @Override
    public String toString() {
        return "MediaContainerObject{" +
                "format='" + format + '\'' +
                '}';
    }
}
