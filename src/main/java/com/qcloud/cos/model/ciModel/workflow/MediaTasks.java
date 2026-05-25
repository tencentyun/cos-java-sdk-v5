package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Tasks")
public class MediaTasks {
    @XStreamAlias("Type")
    private String type;

    @XStreamAlias("State")
    private String state;

    @XStreamAlias("JobId")
    private String jobId;

    @XStreamAlias("CreateTime")
    private String createTime;

    @XStreamAlias("StartTime")
    private String startTime;

    @XStreamAlias("EndTime")
    private String endTime;

    @XStreamAlias("Code")
    private String code;

    @XStreamAlias("Message")
    private String message;

    @XStreamAlias("Name")
    private String name;

    @XStreamAlias("ResultInfo")
    private MediaTaskResultInfo resultInfo;

    @XStreamAlias("JudgementInfo")
    private MediaTaskJudgementInfo judgementInfo;

    @XStreamAlias("FileInfo")
    private FileInfoObject fileInfo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public MediaTaskResultInfo getResultInfo() {
        if (resultInfo == null) {
            resultInfo = new MediaTaskResultInfo();
        }
        return resultInfo;
    }

    public void setResultInfo(MediaTaskResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public MediaTaskJudgementInfo getJudgementInfo() {
        if (judgementInfo == null) {
            judgementInfo = new MediaTaskJudgementInfo();
        }
        return judgementInfo;
    }

    public void setJudgementInfo(MediaTaskJudgementInfo judgementInfo) {
        this.judgementInfo = judgementInfo;
    }

    public FileInfoObject getFileInfo() {
        if (fileInfo == null) {
            fileInfo = new FileInfoObject();
        }
        return fileInfo;
    }

    public void setFileInfo(FileInfoObject fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public String toString() {
        return "MediaTasks{" +
                "type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", jobId='" + jobId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", name='" + name + '\'' +
                ", resultInfo=" + resultInfo +
                ", judgementInfo=" + judgementInfo +
                ", fileInfo=" + fileInfo +
                '}';
    }
}
