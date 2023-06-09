package com.qcloud.cos.model.ciModel.auditing;

public class AuditingLiveInfo {
    private String startTime;
    private String endTime;
    private String output;


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

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuditingLiveInfo{");
        sb.append("startTime='").append(startTime).append('\'');
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", output='").append(output).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
