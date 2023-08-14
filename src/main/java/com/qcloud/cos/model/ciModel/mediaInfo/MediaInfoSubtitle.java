package com.qcloud.cos.model.ciModel.mediaInfo;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * MediaInfo 字幕详情实体类 详情见：https://cloud.tencent.com/document/product/460/38935
 */
public class MediaInfoSubtitle {
    /**
     * 该流的编号
     */
    @XStreamAlias("Index")
    private String index;

    /**
     * 语言
     */
    @XStreamAlias("Language")
    private String language;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "MediaInfoSubtitle{" +
                "index='" + index + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
