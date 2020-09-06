package com.qcloud.cos.model.ciModel.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaOutputObject {
    @XStreamAlias("Region")
    private String region;
    @XStreamAlias("Bucket")
    private String bucket;
    @XStreamAlias("Object")
    private String object;

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

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "MediaOutputObject{" +
                "region='" + region + '\'' +
                ", bucket='" + bucket + '\'' +
                ", object='" + object + '\'' +
                '}';
    }
}
