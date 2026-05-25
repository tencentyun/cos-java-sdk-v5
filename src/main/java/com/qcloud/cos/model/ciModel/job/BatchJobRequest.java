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
    
    // 列表查询参数
    private String orderByTime;
    private String nextToken;
    private Integer size = 10;
    private String states;
    private String startCreationTime;
    private String endCreationTime;
    private String workflowId;

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

    public String getOrderByTime() {
        return orderByTime;
    }

    public void setOrderByTime(String orderByTime) {
        this.orderByTime = orderByTime;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getStartCreationTime() {
        return startCreationTime;
    }

    public void setStartCreationTime(String startCreationTime) {
        this.startCreationTime = startCreationTime;
    }

    public String getEndCreationTime() {
        return endCreationTime;
    }

    public void setEndCreationTime(String endCreationTime) {
        this.endCreationTime = endCreationTime;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}
