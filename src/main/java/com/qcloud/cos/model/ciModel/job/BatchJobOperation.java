package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.MediaOutputObject;

/**
 * 媒体处理 operation实体 https://cloud.tencent.com/document/product/460/48234
 */

public class BatchJobOperation {
    private MediaTimeIntervalObject timeInterval;
    private String tag;
    private MediaOutputObject output;
    private JobParam jobParam;
    private String queueId;
    private String queueType;
    private String userData;
    private String jobLevel;
    private String callBackFormat;
    private String callBackType;
    private String callBack;
    private CallBackMqConfig callBackMqConfig;
    private CallBackKafkaConfig callBackKafkaConfig;
    private String workflowIds;

    public MediaTimeIntervalObject getTimeInterval() {
        if (timeInterval == null) {
            timeInterval = new MediaTimeIntervalObject();
        }
        return timeInterval;
    }

    public void setTimeInterval(MediaTimeIntervalObject timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public MediaOutputObject getOutput() {
        if (output == null) {
            output = new MediaOutputObject();
        }
        return output;
    }

    public void setOutput(MediaOutputObject output) {
        this.output = output;
    }

    public JobParam getJobParam() {
        if (jobParam == null) {
            jobParam = new JobParam();
        }
        return jobParam;
    }

    public void setJobParam(JobParam jobParam) {
        this.jobParam = jobParam;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public String getCallBackFormat() {
        return callBackFormat;
    }

    public void setCallBackFormat(String callBackFormat) {
        this.callBackFormat = callBackFormat;
    }

    public String getCallBackType() {
        return callBackType;
    }

    public void setCallBackType(String callBackType) {
        this.callBackType = callBackType;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public CallBackMqConfig getCallBackMqConfig() {
        if (callBackMqConfig == null) {
            callBackMqConfig = new CallBackMqConfig();
        }
        return callBackMqConfig;
    }

    public void setCallBackMqConfig(CallBackMqConfig callBackMqConfig) {
        this.callBackMqConfig = callBackMqConfig;
    }

    public String getWorkflowIds() {
        return workflowIds;
    }

    public void setWorkflowIds(String workflowIds) {
        this.workflowIds = workflowIds;
    }

    public CallBackKafkaConfig getCallBackKafkaConfig() {
        if (callBackKafkaConfig == null) {
            callBackKafkaConfig = new CallBackKafkaConfig();
        }
        return callBackKafkaConfig;
    }

    public void setCallBackKafkaConfig(CallBackKafkaConfig callBackKafkaConfig) {
        this.callBackKafkaConfig = callBackKafkaConfig;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BatchJobOperation{");
        sb.append("timeInterval=").append(timeInterval);
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", output=").append(output);
        sb.append(", jobParam=").append(jobParam);
        sb.append(", queueId='").append(queueId).append('\'');
        sb.append(", queueType='").append(queueType).append('\'');
        sb.append(", userData='").append(userData).append('\'');
        sb.append(", jobLevel='").append(jobLevel).append('\'');
        sb.append(", callBackFormat='").append(callBackFormat).append('\'');
        sb.append(", callBackType='").append(callBackType).append('\'');
        sb.append(", callBack='").append(callBack).append('\'');
        sb.append(", callBackMqConfig='").append(callBackMqConfig).append('\'');
        sb.append(", callBackKafkaConfig='").append(callBackKafkaConfig).append('\'');
        sb.append(", workflowIds='").append(workflowIds).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
