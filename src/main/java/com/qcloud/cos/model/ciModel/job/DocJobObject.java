package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 媒体处理 动图任务实体 https://cloud.tencent.com/document/product/460/48217
 */
public class DocJobObject {
    /**
     * 任务的输入文件路径	(cos桶相对路径)
     */
    private MediaInputObject input;
    /**
     * 请求类型 文档预览固定使用 DocProcess
     */
    private String tag = "DocProcess";
    /**
     * 任务队列id 可在控制台查看或调用查询队列接口获取
     */
    private String queueId;
    /**
     * 任务规则
     */
    private DocOperationObject operation;

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
     * 任务回调类型，Url 或 TDMQ 或 Kafka，默认 Url，优先级高于队列的回调类型
     */
    @XStreamAlias("CallBackType")
    private String callBackType;
    @XStreamAlias("CallBackMqConfig")
    private CallBackMqConfig callBackMqConfig;

    @XStreamAlias("CallBackKafkaConfig")
    private CallBackKafkaConfig callBackKafkaConfig;

    public MediaInputObject getInput() {
        if (input == null) {
            input = new MediaInputObject();
        }
        return input;
    }

    public void setInput(MediaInputObject input) {
        this.input = input;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public DocOperationObject getOperation() {
        if (operation == null) {
            operation = new DocOperationObject();
        }
        return operation;
    }

    public void setOperation(DocOperationObject operation) {
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
        final StringBuilder sb = new StringBuilder("DocJobObject{");
        sb.append("input=").append(input);
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", queueId='").append(queueId).append('\'');
        sb.append(", operation=").append(operation);
        sb.append(", callback=").append(callBack);
        sb.append(", callBackType=").append(callBackType);
        sb.append(", callBackFormat=").append(callBackFormat);
        sb.append(", callBackMqConfig=").append(callBackMqConfig);
        sb.append(", callBackKafkaConfig=").append(callBackKafkaConfig);
        sb.append('}');
        return sb.toString();
    }
}
