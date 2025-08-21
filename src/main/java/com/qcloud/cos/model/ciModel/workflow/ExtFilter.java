
package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ExtFilter")
public class ExtFilter {
    @XStreamAlias("State")
    private String state;

    @XStreamAlias("Video")
    private boolean video;

    @XStreamAlias("Audio")
    private boolean audio;

    @XStreamAlias("ContentType")
    private boolean contentType;

    @XStreamAlias("Custom")
    private boolean custom;

    @XStreamAlias("CustomExts")
    private String customExts;

    @XStreamAlias("AllFile")
    private boolean allFile;

    @XStreamAlias("Image")
    private boolean image;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isAudio() {
        return audio;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }

    public boolean isContentType() {
        return contentType;
    }

    public void setContentType(boolean contentType) {
        this.contentType = contentType;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public String getCustomExts() {
        return customExts;
    }

    public void setCustomExts(String customExts) {
        this.customExts = customExts;
    }

    public boolean isAllFile() {
        return allFile;
    }

    public void setAllFile(boolean allFile) {
        this.allFile = allFile;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ExtFilter{" +
                "state='" + state + '\'' +
                ", video=" + video +
                ", audio=" + audio +
                ", contentType=" + contentType +
                ", custom=" + custom +
                ", customExts='" + customExts + '\'' +
                ", allFile=" + allFile +
                ", image=" + image +
                '}';
    }
}