package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

public class MediaJobObject extends CIServiceRequest implements Serializable {
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
        return operation;
    }

    public void setOperation(MediaJobOperation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "MediaJobObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", jobId='" + jobId + '\'' +
                ", state='" + state + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", queueId='" + queueId + '\'' +
                ", tag='" + tag + '\'' +
                ", input=" + input +
                ", operation=" + operation +
                '}';
    }
}
