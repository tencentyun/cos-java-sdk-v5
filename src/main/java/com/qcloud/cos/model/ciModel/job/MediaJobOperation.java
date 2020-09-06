package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoObjcet;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaJobOperation {
    @XStreamAlias("TemplateId")
    private String templateId;
    @XStreamAlias("Output")
    private MediaOutputObject output;
    @XStreamAlias("Animation")
    private MediaAnimationObject mediaAnimation;
    @XStreamAlias("MediaInfo")
    private MediaInfoObjcet mediaInfo;

    public MediaJobOperation() {
        this.output = new MediaOutputObject();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public MediaOutputObject getOutput() {
        return output;
    }

    public void setOutput(MediaOutputObject output) {
        this.output = output;
    }

    public MediaAnimationObject getMediaAnimation() {
        if (mediaAnimation == null) {
            mediaAnimation = new MediaAnimationObject();
        }
        return mediaAnimation;
    }

    public void setMediaAnimation(MediaAnimationObject mediaAnimation) {
        this.mediaAnimation = mediaAnimation;
    }

    @Override
    public String toString() {
        return "MediaJobOperation{" +
                "templateId='" + templateId + '\'' +
                ", output=" + output +
                ", mediaAnimation=" + mediaAnimation +
                ", mediaInfo=" + mediaInfo +
                '}';
    }
}
