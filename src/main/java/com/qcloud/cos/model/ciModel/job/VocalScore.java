package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("VocalScore")
public class VocalScore {

    @XStreamAlias("StandardObject")
    private String standardObject;

    @XStreamAlias("StandardUrl")
    private String standardUrl;

    public String getStandardObject() {
        return standardObject;
    }

    public void setStandardObject(String standardObject) {
        this.standardObject = standardObject;
    }

    public String getStandardUrl() {
        return standardUrl;
    }

    public void setStandardUrl(String standardUrl) {
        this.standardUrl = standardUrl;
    }
}
