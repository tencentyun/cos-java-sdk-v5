package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaTags {
    @XStreamAlias("Tag")
    private String tag;
    @XStreamAlias("TagCls")
    private String tagCls;
    @XStreamAlias("Confidence")
    private String confidence;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTagCls() {
        return tagCls;
    }

    public void setTagCls(String tagCls) {
        this.tagCls = tagCls;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
