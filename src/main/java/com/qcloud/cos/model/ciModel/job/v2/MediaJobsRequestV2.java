package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.job.CallBackMqConfig;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 媒体处理 任务请求实体 https://cloud.tencent.com/document/product/460/48234
 */
@XStreamAlias("Request")
public class MediaJobsRequestV2 extends CIServiceRequest implements Serializable {
    /**
     * bucket名称
     */
    @XStreamOmitField
    private String bucketName;
    /**
     * 任务的队列id
     */
    @XStreamOmitField
    private String queueId;
    /**
     * 任务类型
     */
    @XStreamAlias("Tag")
    private String tag;
    /**
     * 时间顺序
     */
    @XStreamAlias("OrderByTime")
    private String orderByTime;

    /**
     * 下一个token
     */
    @XStreamAlias("NextToken")
    private String nextToken;

    /**
     * 查询数量，默认为十个
     */
    @XStreamAlias("Size")
    private Integer size;

    /**
     * 任务状态
     */
    @XStreamAlias("States")
    private String states;

    /**
     * 开始时间
     */
    @XStreamAlias("StartCreationTime")
    private String startCreationTime;

    /**
     * 结束时间
     */
    @XStreamAlias("EndCreationTime")
    private String endCreationTime;

    /**
     * 任务id
     */
    @XStreamAlias("JobId")
    private String jobId;

    /**
     * 输入对象
     */
    @XStreamAlias("Input")
    private MediaInputObject input;
    /**
     * 媒体操作对象
     */
    @XStreamAlias("Operation")
    private MediaJobOperation operation;
    /**
     * 回调参数
     */
    @XStreamAlias("CallBack")
    private String callBack;

    /**
     * 回调类型  json / xml  默认为xml
     */
    @XStreamAlias("CallBackFormat")
    private String callBackFormat;

    /**
     * 任务回调类型，Url 或 TDMQ，默认 Url，优先级高于队列的回调类型
     */
    @XStreamAlias("CallBackType")
    private String callBackType;
    @XStreamAlias("CallBackMqConfig")
    private CallBackMqConfig callBackMqConfig;
    @XStreamAlias("QueueType")
    private String queueType;

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getCallBackType() {
        return callBackType;
    }

    public void setCallBackType(String callBackType) {
        this.callBackType = callBackType;
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

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
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

    public MediaJobOperation getOperation() {
        if (operation == null) {
            operation = new MediaJobOperation();
        }
        return operation;
    }

    public void setOperation(MediaJobOperation operation) {
        this.operation = operation;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public String getCallBackFormat() {
        return callBackFormat;
    }

    public void setCallBackFormat(String callBackFormat) {
        this.callBackFormat = callBackFormat;
    }

}
