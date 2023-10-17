package com.qcloud.cos.model.ciModel.workflow;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 工作流列表请求实体类 请见：https://cloud.tencent.com/document/product/460/45947
 */
@XStreamAlias("Request")
public class MediaWorkflowListRequest extends CIServiceRequest implements Serializable {
    /**
     * 工作流 ID，以,符号分割字符串
     */
    @XStreamOmitField
    private String ids;
    /**
     * 名称
     */
    @XStreamOmitField
    private String name;
    /**
     * 第几页
     */
    @XStreamOmitField
    private String pageNumber;
    /**
     * 每页个数
     */
    @XStreamOmitField
    private String pageSize;

    /**
     * 工作流 ID
     */
    @XStreamOmitField
    private String workflowId;
    /**
     * Desc 或者 Asc。默认为 Desc
     */
    @XStreamOmitField
    private String orderByTime;
    /**
     * 拉取的最大任务数。默认为10。最大为100
     */
    @XStreamOmitField
    private String size;
    /**
     * 工作流实例状态，以,分割支持多状态
     * All，Success，Failed，Running，Cancel。默认为 All
     */
    @XStreamOmitField
    private String states;
    /**
     * 拉取创建时间大于该时间。格式为：%Y-%m-%dT%H:%m:%S%z
     */
    @XStreamOmitField
    private String startCreationTime;
    /**
     * 拉取创建时间小于该时间。格式为：%Y-%m-%dT%H:%m:%S%z
     */
    @XStreamOmitField
    private String endCreationTime;
    /**
     * 请求的上下文，用于翻页。下一页输入 token
     */
    @XStreamOmitField
    private String nextToken;

    /**
     * 工作流实例 ID
     */
    @XStreamOmitField
    private String runId;

    /**
     * 需要进行工作流处理的对象名称
     */
    @XStreamOmitField
    private String object;

    /**
     * 指定是否添加附加参数，如自定义水印等，目前仅自适应码率功能有效
     */
    @XStreamAlias("AttachParam")
    private AttachParam attachParam;

    public AttachParam getAttachParam() {
        return attachParam;
    }

    public void setAttachParam(AttachParam attachParam) {
        this.attachParam = attachParam;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getOrderByTime() {
        return orderByTime;
    }

    public void setOrderByTime(String orderByTime) {
        this.orderByTime = orderByTime;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
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

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaWorkflowListRequest{");
        sb.append("ids='").append(ids).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", pageNumber='").append(pageNumber).append('\'');
        sb.append(", pageSize='").append(pageSize).append('\'');
        sb.append(", workflowId='").append(workflowId).append('\'');
        sb.append(", orderByTime='").append(orderByTime).append('\'');
        sb.append(", size='").append(size).append('\'');
        sb.append(", states='").append(states).append('\'');
        sb.append(", startCreationTime='").append(startCreationTime).append('\'');
        sb.append(", endCreationTime='").append(endCreationTime).append('\'');
        sb.append(", nextToken='").append(nextToken).append('\'');
        sb.append(", runId='").append(runId).append('\'');
        sb.append(", object='").append(object).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
