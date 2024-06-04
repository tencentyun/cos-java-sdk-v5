package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class KeyConfig {
    @XStreamAlias("Key")
    private String key;
    @XStreamAlias("Rename")
    private String rename;
    @XStreamAlias("ImageParams")
    private String imageParams;

    public String getImageParams() {
        return imageParams;
    }

    public void setImageParams(String imageParams) {
        this.imageParams = imageParams;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRename() {
        return rename;
    }

    public void setRename(String rename) {
        this.rename = rename;
    }
}
