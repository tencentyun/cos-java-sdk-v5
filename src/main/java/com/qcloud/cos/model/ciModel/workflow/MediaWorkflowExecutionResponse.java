package com.qcloud.cos.model.ciModel.workflow;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 工作流响应实体类 请见：https://cloud.tencent.com/document/product/460/45947
 */
@XStreamAlias("Response")
public class MediaWorkflowExecutionResponse extends CiServiceResult {
    @XStreamAlias("RequestId")
    private String requestId;
    /**
     * 工作流实例详细信息
     */
    @XStreamAlias("WorkflowExecution")
    private MediaWorkflowExecutionObject workflowExecution;

    public MediaWorkflowExecutionObject getWorkflowExecution() {
        if (workflowExecution == null) {
            workflowExecution = new MediaWorkflowExecutionObject();
        }
        return workflowExecution;
    }

    public void setWorkflowExecution(MediaWorkflowExecutionObject workflowExecution) {
        this.workflowExecution = workflowExecution;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "MediaWorkflowExecutionResponse{" +
                "workflowExecution=" + workflowExecution +
                '}';
    }
}
