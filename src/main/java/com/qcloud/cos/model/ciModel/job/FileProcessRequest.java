package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文件处理请求类
 */
@XStreamAlias("Request")
public class FileProcessRequest extends CIServiceRequest {
    @XStreamAlias("Tag")
    private FileProcessJobType tag;

    @XStreamAlias("QueueId")
    private String queueId;

    @XStreamAlias("CallBackFormat")
    private String callBackFormat;

    @XStreamAlias("CallBackType")
    private String callBackType;

    @XStreamAlias("CallBack")
    private String callBack;

    @XStreamAlias("CallBackMqConfig")
    private CallBackMqConfig callBackMqConfig;

    @XStreamAlias("JobId")
    private String jobId;

    @XStreamAlias("Input")
    private MediaInputObject input;

    @XStreamAlias("Operation")
    private FileProcessOperation operation;

    public FileProcessJobType getTag() {
        return tag;
    }

    public void setTag(FileProcessJobType tag) {
        this.tag = tag;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
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

    public FileProcessOperation getOperation() {
        if (operation == null) {
            operation = new FileProcessOperation();
        }
        return operation;
    }

    public void setOperation(FileProcessOperation operation) {
        this.operation = operation;
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileProcessRequest{");
        sb.append("tag=").append(tag);
        sb.append(", queueId='").append(queueId).append('\'');
        sb.append(", callBackFormat='").append(callBackFormat).append('\'');
        sb.append(", callBackType='").append(callBackType).append('\'');
        sb.append(", callBack='").append(callBack).append('\'');
        sb.append(", callBackMqConfig='").append(callBackMqConfig).append('\'');
        sb.append(", operation=").append(operation);
        sb.append('}');
        return sb.toString();
    }
}
