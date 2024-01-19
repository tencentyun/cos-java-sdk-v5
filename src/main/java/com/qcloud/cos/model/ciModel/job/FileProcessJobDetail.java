package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.FileProcessInputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FileProcessJobDetail {
    @XStreamAlias("Code")
    private String code;

    @XStreamAlias("Message")
    private String Message;

    @XStreamAlias("JobId")
    private String JobId;

    @XStreamAlias("Tag")
    private String Tag;

    @XStreamAlias("State")
    private String State;

    @XStreamAlias("CreationTime")
    private String CreationTime;

    @XStreamAlias("StartTime")
    private String StartTime;

    @XStreamAlias("EndTime")
    private String EndTime;

    @XStreamAlias("QueueId")
    private String QueueId;

    @XStreamAlias("Progress")
    private String progress;

    @XStreamAlias("Input")
    private FileProcessInputObject input;

    @XStreamAlias("Operation")
    private FileProcessOperation operation;

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getJobId() {
        return JobId;
    }

    public void setJobId(String jobId) {
        JobId = jobId;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(String creationTime) {
        CreationTime = creationTime;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getQueueId() {
        return QueueId;
    }

    public void setQueueId(String queueId) {
        QueueId = queueId;
    }

    public FileProcessInputObject getInput() {
        if (input == null) {
            input = new FileProcessInputObject();
        }
        return input;
    }

    public void setInput(FileProcessInputObject input) {
        this.input = input;
    }

    public FileProcessOperation getOperation() {
        if (operation == null) {
            operation = new FileProcessOperation();
        }
        return operation;
    }

    public void setOperation(FileProcessOperation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileProcessJobDetail{");
        sb.append("code='").append(code).append('\'');
        sb.append(", Message='").append(Message).append('\'');
        sb.append(", JobId='").append(JobId).append('\'');
        sb.append(", Tag='").append(Tag).append('\'');
        sb.append(", State='").append(State).append('\'');
        sb.append(", CreationTime='").append(CreationTime).append('\'');
        sb.append(", StartTime='").append(StartTime).append('\'');
        sb.append(", EndTime='").append(EndTime).append('\'');
        sb.append(", QueueId='").append(QueueId).append('\'');
        sb.append(", input=").append(input);
        sb.append(", operation=").append(operation);
        sb.append('}');
        return sb.toString();
    }
}
