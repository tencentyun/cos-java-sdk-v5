package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class DNADbConfig {
    @XStreamAlias("BucketId")
    private String bucketId;

    @XStreamAlias("Region")
    private String region;

    @XStreamAlias("DNADbId")
    private String dnaDbId;

    @XStreamAlias("DNADbName")
    private String dnaDbName;

    @XStreamAlias("Capacity")
    private int capacity;

    @XStreamAlias("Description")
    private String description;

    @XStreamAlias("CreateTime")
    private String createTime;

    @XStreamAlias("UpdateTime")
    private String updateTime;

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

    public String getDnaDbId() {
        return dnaDbId;
    }

    public void setDnaDbId(String dnaDbId) {
        this.dnaDbId = dnaDbId;
    }

    public String getDnaDbName() {
        return dnaDbName;
    }

    public void setDnaDbName(String dnaDbName) {
        this.dnaDbName = dnaDbName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
