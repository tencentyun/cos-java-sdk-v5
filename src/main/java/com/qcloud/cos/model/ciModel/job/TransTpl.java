package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class TransTpl {
    @XStreamAlias("Container")
    private MediaContainerObject container;
    @XStreamAlias("Video")
    private MediaVideoObject video;
    @XStreamAlias("Codec")
    private String codec;
    @XStreamAlias("Bitrate")
    private String bitrate;
    @XStreamAlias("Crf")
    private String crf;
    @XStreamAlias("Width")
    private String width;
    @XStreamAlias("Height")
    private String height;
    @XStreamAlias("Fps")
    private String fps;

    public MediaContainerObject getContainer() {
        return container;
    }

    public void setContainer(MediaContainerObject container) {
        this.container = container;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getCrf() {
        return crf;
    }

    public void setCrf(String crf) {
        this.crf = crf;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFps() {
        return fps;
    }

    public void setFps(String fps) {
        this.fps = fps;
    }

    public MediaVideoObject getVideo() {
        return video;
    }

    public void setVideo(MediaVideoObject video) {
        this.video = video;
    }
}
