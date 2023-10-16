package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 媒体处理 任务实体 https://cloud.tencent.com/document/product/460/48234
 */

public class MediaJobObject implements Serializable {
    @XStreamOmitField
    private String bucketName;

    @XStreamAlias("Code")
    private String code;

    @XStreamAlias("Message")
    private String message;

    @XStreamAlias("JobId")
    private String jobId;

    @XStreamAlias("State")
    private String state;

    @XStreamAlias("CreationTime")
    private String creationTime;

    @XStreamAlias("EndTime")
    private String endTime;

    @XStreamAlias("QueueId")
    private String queueId;

    @XStreamAlias("Tag")
    private String tag;

    @XStreamAlias("Name")
    private String name;

    @XStreamAlias("Type")
    private String type;

    @XStreamAlias("Progress")
    private String progress;

    @XStreamAlias("QueueType")
    private String queueType;

    @XStreamAlias("Input")
    private MediaInputObject input = new MediaInputObject();

    @XStreamAlias("Operation")
    private MediaJobOperation operation = new MediaJobOperation();

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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
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

    public MediaInputObject getInput() {
        if (input == null) {
            input = new MediaInputObject();
        }
        return input;
    }

    public void setInput(MediaInputObject input) {
        this.input = input;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public MediaJobOperation getOperation() {
        if (operation == null) {
            operation = new MediaJobOperation();
        }
        return operation;
    }

    public void setOperation(MediaJobOperation operation) {
        this.operation = operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaJobObject{");
        sb.append("code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", jobId='").append(jobId).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", creationTime='").append(creationTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", queueId='").append(queueId).append('\'');
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", progress='").append(progress).append('\'');
        sb.append(", queueType='").append(queueType).append('\'');
        sb.append(", input=").append(input);
        sb.append(", operation=").append(operation);
        sb.append('}');
        return sb.toString();
    }
}
