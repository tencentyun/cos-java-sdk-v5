package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Input")
public class VirusDetectInput {

    @XStreamAlias("Object")
    private String object;

    @XStreamAlias("Url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "VirusDetectInput{" +
                "object='" + object + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
