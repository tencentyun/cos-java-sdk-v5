package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OriginalInfo {
    @XStreamAlias("Key")
    private String key;

    @XStreamAlias("Location")
    private String location;

    @XStreamAlias("ETag")
    private String etag;

    @XStreamAlias("ImageInfo")
    private ImageInfo imageInfo;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ImageInfo getImageInfo() {
        if (imageInfo == null) {
            imageInfo = new ImageInfo();
        }
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }
}
