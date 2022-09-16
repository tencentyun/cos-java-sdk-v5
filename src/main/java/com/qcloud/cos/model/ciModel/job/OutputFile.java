package com.qcloud.cos.model.ciModel.job;

public class OutputFile {
    private String region;
    private String bucket;
    private String objectPrefix;
    private String objectName;
    private Md5Info md5Info;

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
}
