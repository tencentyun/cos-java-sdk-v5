package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CIServiceRequest;

import java.io.Serializable;

public class MediaJobsRequest extends CIServiceRequest implements Serializable {
    private String bucketName;
    private String queueId;
    private String tag;
    private String orderByTime;
    private String nextToken;
    private Integer size = 10;
    private String states;
    private String startCreationTime;
    private String endCreationTime;
    private String jobId;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getOrderByTime() {
        return orderByTime;
    }

    public void setOrderByTime(String orderByTime) {
        this.orderByTime = orderByTime;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getStartCreationTime() {
        return startCreationTime;
    }

    public void setStartCreationTime(String startCreationTime) {
        this.startCreationTime = startCreationTime;
    }

    public String getEndCreationTime() {
        return endCreationTime;
    }

    public void setEndCreationTime(String endCreationTime) {
        this.endCreationTime = endCreationTime;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

}
