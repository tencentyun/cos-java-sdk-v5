package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SpriteObjectInfo")
public class SpriteObjectInfo {
    @XStreamAlias("ObjectName")
    private String objectName;

    @XStreamAlias("ObjectUrl")
    private String objectUrl;

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String toString() {
        return "SpriteObjectInfo{" +
                "objectName='" + objectName + '\'' +
                ", objectUrl='" + objectUrl + '\'' +
                '}';
    }
}
