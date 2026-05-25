package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Conf")
public class VirusDetectConf {

    @XStreamAlias("DetectType")
    private String detectType;

    @XStreamAlias("Callback")
    private String callback;

    public String getDetectType() {
        return detectType;
    }

    public void setDetectType(String detectType) {
        this.detectType = detectType;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "VirusDetectConf{" +
                "detectType='" + detectType + '\'' +
                ", callback='" + callback + '\'' +
                '}';
    }
}
