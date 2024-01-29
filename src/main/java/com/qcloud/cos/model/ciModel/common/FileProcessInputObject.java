package com.qcloud.cos.model.ciModel.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FileProcessInputObject extends MediaInputObject {
    @XStreamAlias("BucketId")
    private String bucketId;
    @XStreamAlias("Region")
    private String region;

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileProcessInputObject{");
        sb.append("bucketId='").append(bucketId).append('\'');
        sb.append(", region='").append(region).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
