package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("JudgementResult")
public class JudgementResultObject {
    @XStreamAlias("ObjectName")
    private String objectName;

    @XStreamAlias("ObjectUrl")
    private String objectUrl;

    @XStreamAlias("State")
    private String state;

    @XStreamAlias("InputObjectInfo")
    private InputObjectInfoObject inputObjectInfo;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public InputObjectInfoObject getInputObjectInfo() {
        if (inputObjectInfo == null) {
            inputObjectInfo = new InputObjectInfoObject();
        }
        return inputObjectInfo;
    }

    public void setInputObjectInfo(InputObjectInfoObject inputObjectInfo) {
        this.inputObjectInfo = inputObjectInfo;
    }
}
