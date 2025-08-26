package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Input")
public class TranslationInput {

    @XStreamAlias("Object")
    private String object;

    @XStreamAlias("Lang")
    private String lang;

    @XStreamAlias("Type")
    private String type;

    @XStreamAlias("BasicType")
    private String basicType;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

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

    public String getBasicType() {
        return basicType;
    }

    public void setBasicType(String basicType) {
        this.basicType = basicType;
    }
}
