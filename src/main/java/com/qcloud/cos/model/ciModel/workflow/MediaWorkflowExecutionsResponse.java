package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 工作流请求实体类 请见：https://cloud.tencent.com/document/product/460/45947
 */
public class MediaWorkflowExecutionsResponse  {

    /**
     * 工作流实例详细信息
     */
    @XStreamImplicit(itemFieldName = "WorkflowExecutionList")
    private List<MediaWorkflowExecutionObject> WorkflowExecutionList;

    @XStreamAlias("NextToken")
    private String nextToken;

    @XStreamAlias("RequestId")
    private String requestId;

    public List<MediaWorkflowExecutionObject> getWorkflowExecutionList() {
        return WorkflowExecutionList;
    }

    public void setWorkflowExecutionList(List<MediaWorkflowExecutionObject> workflowExecutionList) {
        WorkflowExecutionList = workflowExecutionList;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }


    @Override
    public String toString() {
        return "MediaWorkflowExecutionsResponse{" +
                "WorkflowExecutionList=" + WorkflowExecutionList +
                ", nextToken='" + nextToken + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
