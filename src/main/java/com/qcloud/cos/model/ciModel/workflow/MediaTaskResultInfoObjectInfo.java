package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ObjectInfo")
public class MediaTaskResultInfoObjectInfo {
    @XStreamAlias("ObjectName")
    private String objectName;

    @XStreamAlias("ObjectUrl")
    private String objectUrl;

    @XStreamAlias("InputObjectName")
    private String inputObjectName;

    @XStreamAlias("Code")
    private String code;

    @XStreamAlias("Message")
    private String message;

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

    public String getInputObjectName() {
        return inputObjectName;
    }

    public void setInputObjectName(String inputObjectName) {
        this.inputObjectName = inputObjectName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MediaTaskResultInfoObjectInfo{" +
                "objectName='" + objectName + '\'' +
                ", objectUrl='" + objectUrl + '\'' +
                ", inputObjectName='" + inputObjectName + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
