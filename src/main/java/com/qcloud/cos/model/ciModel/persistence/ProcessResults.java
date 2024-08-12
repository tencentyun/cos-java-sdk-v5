package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class ProcessResults {
    @XStreamImplicit(itemFieldName = "Object")
    private List<CIObject> objectList;

    public List<CIObject> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<CIObject> objectList) {
        this.objectList = objectList;
    }
}
