package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("JobsDetail")
public class JobsDetail {
    @XStreamAlias("JobId")
    private String jobId;
    @XStreamAlias("Code")
    private String code;
    @XStreamAlias("Message")
    private String message;
    @XStreamAlias("State")
    private String state;
    @XStreamAlias("CreationTime")
    private String creationTime;
    @XStreamAlias("StartTime")
    private String startTime;
    @XStreamAlias("EndTime")
    private String endTime;
    @XStreamAlias("QueueType")
    private String queueType;
    @XStreamAlias("QueueId")
    private String queueId;
    @XStreamAlias("Tag")
    private String tag;
    @XStreamAlias("Input")
    private TranslationInput input;
    @XStreamAlias("Operation")
    private TranslationOperation operation;


    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public TranslationInput getInput() {
        return input;
    }

    public void setInput(TranslationInput input) {
        this.input = input;
    }

    public TranslationOperation getOperation() {
        return operation;
    }

    public void setOperation(TranslationOperation operation) {
        this.operation = operation;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    @Override
    public String toString() {
        return "JobsDetail{" +
                "jobId='" + jobId + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", state='" + state + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", queueType='" + queueType + '\'' +
                ", queueId='" + queueId + '\'' +
                ", tag='" + tag + '\'' +
                ", input=" + input +
                ", operation=" + operation +
                '}';
    }
}
