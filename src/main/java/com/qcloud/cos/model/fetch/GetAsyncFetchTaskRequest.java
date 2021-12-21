package com.qcloud.cos.model.fetch;

import com.qcloud.cos.internal.CosServiceRequest;

public class GetAsyncFetchTaskRequest extends CosServiceRequest {
    private String bucketName;
    private String taskId;

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return this.taskId;
    }
}