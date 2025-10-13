package com.qcloud.cos.model.ciModel.workflow;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.LinkedList;

/**
 * 工作流请求实体类 请见：https://cloud.tencent.com/document/product/460/45947
 */
@XStreamAlias("WorkflowExecution")
public class MediaWorkflowExecutionObject {
    /**
     * 工作流实例 ID
     */
    @XStreamAlias("RunId")
    private String runId;
    /**
     * 工作流 ID
     */
    @XStreamAlias("WorkflowId")
    private String workflowId;
    /**
     * 工作流名称
     */
    @XStreamAlias("WorkflowName")
    private String workflowName;
    /**
     * 工作流名称
     */
    @XStreamAlias("Name")
    private String name;
    /**
     * 工作流实例状态
     */
    @XStreamAlias("State")
    private String state;
    /**
     * 创建时间
     */
    @XStreamAlias("CreateTime")
    private String createTime;
    /**
     * cos对象地址
     */
    @XStreamAlias("Object")
    private String object;
    /**
     * 拓扑信息
     */
    @XStreamAlias("Topology")
    private MediaTopology topology;
    /**
     * cos对象地址
     */
    @XStreamImplicit(itemFieldName="Tasks")
    private LinkedList<MediaTasks> tasks;

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public MediaTopology getTopology() {
        if (topology == null) {
            topology = new MediaTopology();
        }
        return topology;
    }

    public void setTopology(MediaTopology topology) {
        this.topology = topology;
    }

    public LinkedList<MediaTasks> getTasks() {
        if (tasks == null) {
            tasks = new LinkedList<>();
        }
        return tasks;
    }

    public void setTasks(LinkedList<MediaTasks> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "MediaWorkflowExecutionObject{" +
                "runId='" + runId + '\'' +
                ", workflowId='" + workflowId + '\'' +
                ", workflowName='" + workflowName + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", createTime='" + createTime + '\'' +
                ", object='" + object + '\'' +
                ", topology=" + topology +
                ", tasks=" + tasks +
                '}';
    }
}
