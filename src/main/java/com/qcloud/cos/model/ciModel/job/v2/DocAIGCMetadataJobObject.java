
package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 文档AIGC元数据处理任务详情实体
 * https://cloud.tencent.com/document/product/460/123076
 */
public class DocAIGCMetadataJobObject implements Serializable {
    
    @XStreamOmitField
    private String bucketName;

    /**
     * 错误码，只有 State 为 Failed 时生效
     */
    @XStreamAlias("Code")
    private String code;

    /**
     * 错误描述，只有 State 为 Failed 时生效
     */
    @XStreamAlias("Message")
    private String message;

    /**
     * 新创建任务的 ID
     */
    @XStreamAlias("JobId")
    private String jobId;

    /**
     * 任务状态
     * Submitted：已提交，待执行
     * Running：执行中
     * Success：执行成功
     * Failed：执行失败
     * Pause：任务暂停，当暂停队列时，待执行的任务会变为暂停状态
     * Cancel：任务被取消执行
     */
    @XStreamAlias("State")
    private String state;

    /**
     * 任务的创建时间
     */
    @XStreamAlias("CreationTime")
    private String creationTime;

    /**
     * 任务的开始时间
     */
    @XStreamAlias("StartTime")
    private String startTime;

    /**
     * 任务的结束时间
     */
    @XStreamAlias("EndTime")
    private String endTime;

    /**
     * 任务所属的队列 ID
     */
    @XStreamAlias("QueueId")
    private String queueId;

    /**
     * 新创建任务的 Tag：DocAIGCMetadata
     */
    @XStreamAlias("Tag")
    private String tag;

    /**
     * 该任务的输入文件路径
     */
    @XStreamAlias("Input")
    private DocAIGCMetadataInput input;

    /**
     * 该任务的规则
     */
    @XStreamAlias("Operation")
    private DocAIGCMetadataOperation operation;

    public DocAIGCMetadataJobObject() {
        this.input = new DocAIGCMetadataInput();
        this.operation = new DocAIGCMetadataOperation();
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
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

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public DocAIGCMetadataInput getInput() {
        if (input == null) {
            input = new DocAIGCMetadataInput();
        }
        return input;
    }

    public void setInput(DocAIGCMetadataInput input) {
        this.input = input;
    }

    public DocAIGCMetadataOperation getOperation() {
        if (operation == null) {
            operation = new DocAIGCMetadataOperation();
        }
        return operation;
    }

    public void setOperation(DocAIGCMetadataOperation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "DocAIGCMetadataJobObject{" +
                "bucketName='" + bucketName + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", jobId='" + jobId + '\'' +
                ", state='" + state + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", queueId='" + queueId + '\'' +
                ", tag='" + tag + '\'' +
                ", input=" + input +
                ", operation=" + operation +
                '}';
    }
}