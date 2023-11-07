package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OutputFile {
    @XStreamAlias("Region")
    private String region;
    @XStreamAlias("Bucket")
    private String bucket;
    @XStreamAlias("ObjectPrefix")
    private String objectPrefix;
    @XStreamAlias("ObjectName")
    private String objectName;
    @XStreamAlias("Md5Info")
    private Md5Info md5Info;
    @XStreamAlias("ObjectUrl")
    private String objectUrl;

    public String getObjectUrl() {
        return objectUrl;
    }

    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObjectPrefix() {
        return objectPrefix;
    }

    public void setObjectPrefix(String objectPrefix) {
        this.objectPrefix = objectPrefix;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Md5Info getMd5Info() {
        if (md5Info == null) {
            md5Info = new Md5Info();
        }
        return md5Info;
    }

    public void setMd5Info(Md5Info md5Info) {
        this.md5Info = md5Info;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OutputFile{");
        sb.append("region='").append(region).append('\'');
        sb.append(", bucket='").append(bucket).append('\'');
        sb.append(", objectPrefix='").append(objectPrefix).append('\'');
        sb.append(", objectName='").append(objectName).append('\'');
        sb.append(", md5Info=").append(md5Info);
        sb.append('}');
        return sb.toString();
    }
}
