package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("InputObjectInfo")
public class InputObjectInfoObject {
    @XStreamAlias("Width")
    private String width;

    @XStreamAlias("Height")
    private String height;

    @XStreamAlias("Dar")
    private String dar;

    @XStreamAlias("Duration")
    private String duration;

    @XStreamAlias("Size")
    private String size;

    @XStreamAlias("ImageWidth")
    private String imageWidth;

    @XStreamAlias("ImageHeight")
    private String imageHeight;

    @XStreamAlias("ImageDar")
    private String imageDar;

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

    public String getDar() {
        return dar;
    }

    public void setDar(String dar) {
        this.dar = dar;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getImageDar() {
        return imageDar;
    }

    public void setImageDar(String imageDar) {
        this.imageDar = imageDar;
    }
}
