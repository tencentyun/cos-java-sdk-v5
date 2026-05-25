package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.BatchInputObject;

public class BatchJobDetail {
    private BatchInputObject input;
    private BatchJobOperation operation;
    private String name;
    private String type;
    private String code;
    private String message;
    private String jobId;
    private String state;
    private String creationTime;
    private String endTime;

    public BatchJobOperation getOperation() {
        if (operation == null) {
            operation = new BatchJobOperation();
        }
        return operation;
    }

    public void setOperation(BatchJobOperation operation) {
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

    public BatchInputObject getInput() {
        if (input == null) {
            input = new BatchInputObject();
        }
        return input;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setInput(BatchInputObject input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "BatchJobDetail{" +
                "input=" + input +
                ", operation=" + operation +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", jobId='" + jobId + '\'' +
                ", state='" + state + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
