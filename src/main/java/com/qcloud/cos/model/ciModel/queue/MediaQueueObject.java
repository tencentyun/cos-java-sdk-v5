package com.qcloud.cos.model.ciModel.queue;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class MediaQueueObject extends CIServiceRequest implements Serializable {
    @XStreamAlias("QueueId")
    private String queueId;
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("State")
    private String state;
    @XStreamAlias("NotifyConfig")
    private MediaNotifyConfig notifyConfig;
    @XStreamAlias("MaxSize")
    private String maxSize;
    @XStreamAlias("MaxConcurrent")
    private String maxConcurrent;
    @XStreamAlias("CreateTime")
    private String createTime;
    @XStreamAlias("UpdateTime")
    private String updateTime;
    @XStreamAlias("Category")
    private String category;
    @XStreamAlias("BucketId")
    private String bucketId;

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public MediaQueueObject() {
        this.notifyConfig = new MediaNotifyConfig();
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public MediaNotifyConfig getNotifyConfig() {
        return notifyConfig;
    }

    public void setNotifyConfig(MediaNotifyConfig notifyConfig) {
        this.notifyConfig = notifyConfig;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getMaxConcurrent() {
        return maxConcurrent;
    }

    public void setMaxConcurrent(String maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "MediaQueueObject{" +
                "queueId='" + queueId + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", notifyConfig=" + notifyConfig +
                ", maxSize='" + maxSize + '\'' +
                ", maxConcurrent='" + maxConcurrent + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
