package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Md5Info {
    @XStreamAlias("Md5")
    private String md5Info;
    @XStreamAlias("ObjectName")
    private String objectName;

    public String getMd5Info() {
        return md5Info;
    }

    public void setMd5Info(String md5Info) {
        this.md5Info = md5Info;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Md5Info{");
        sb.append("md5Info='").append(md5Info).append('\'');
        sb.append(", objectName='").append(objectName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
