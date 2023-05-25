package com.qcloud.cos.model.ciModel.job;


import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.model.ciModel.common.BatchInputObject;

/**
 * 媒体处理 任务请求实体 https://cloud.tencent.com/document/product/460/48234
 */
public class BatchJobRequest extends CIServiceRequest {
    private BatchInputObject input;
    private BatchJobOperation operation;
    private String name;
    private String type;
    private String jobId;

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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public void setInput(BatchInputObject input) {
        this.input = input;
    }
}
