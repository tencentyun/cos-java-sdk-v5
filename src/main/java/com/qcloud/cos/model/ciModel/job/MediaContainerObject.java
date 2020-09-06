package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaContainerObject {

    /**
     * 容器格式：gif，hgif，webp。hgif 为高质量 gif，即清晰度比较高的 gif 格式图
     */
    @XStreamAlias("Format")
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
