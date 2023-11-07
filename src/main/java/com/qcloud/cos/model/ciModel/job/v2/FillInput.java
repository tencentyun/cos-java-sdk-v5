package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FillInput {
    @XStreamAlias("Url")
    private String url;
    @XStreamAlias("FillTime")
    private String fillTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFillTime() {
        return fillTime;
    }

    public void setFillTime(String fillTime) {
        this.fillTime = fillTime;
    }
}
