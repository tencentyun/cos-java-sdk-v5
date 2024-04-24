package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OcrResults {
    @XStreamAlias("Text")
    private String text;
    @XStreamAlias("Keywords")
    private String keywords;
    @XStreamAlias("Location")
    private Location location;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OcrResults{");
        sb.append("text='").append(text).append('\'');
        sb.append(", keywords='").append(keywords).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
