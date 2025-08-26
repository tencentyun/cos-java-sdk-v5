package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Translation")
public class OperationTranslation {
    @XStreamAlias("Lang")
    private String lang;

    @XStreamAlias("Type")
    private String type;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}