package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class ProcessResults {
    @XStreamImplicit(itemFieldName = "Object")
    private List<CIObject> objectList;

    @XStreamAlias("Text")
    private String text;

    @XStreamAlias("WatermarkStatusCode")
    private String watermarkStatusCode;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWatermarkStatusCode() {
        return watermarkStatusCode;
    }

    public void setWatermarkStatusCode(String watermarkStatusCode) {
        this.watermarkStatusCode = watermarkStatusCode;
    }

    public List<CIObject> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<CIObject> objectList) {
        this.objectList = objectList;
    }
}
