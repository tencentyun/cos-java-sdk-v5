
package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 文档AIGC元数据处理任务请求实体
 * API详情见 https://cloud.tencent.com/document/product/460/123076
 */
@XStreamAlias("Request")
public class DocAIGCMetadataJobRequest extends CIServiceRequest implements Serializable {
    
    /**
     * 存储桶名称
     */
    @XStreamOmitField
    private String bucketName;
    
    /**
     * 任务的队列ID
     */
    @XStreamOmitField
    private String queueId;
    
    /**
     * 创建任务的Tag，目前仅支持：DocAIGCMetadata
     */
    @XStreamAlias("Tag")
    private String tag = "DocAIGCMetadata";
    
    /**
     * 待操作的文件对象
     */
    @XStreamAlias("Input")
    private DocAIGCMetadataInput input;
    
    /**
     * 操作规则
     */
    @XStreamAlias("Operation")
    private DocAIGCMetadataOperation operation;
    
    /**
     * 回调格式，JSON 或 XML，默认 XML，优先级高于队列的回调格式
     */
    @XStreamOmitField
    private String callBackFormat;
    
    /**
     * 回调类型，Url 或 TDMQ，默认 Url，优先级高于队列的回调类型
     */
    @XStreamOmitField
    private String callBackType;
    
    /**
     * 任务回调地址，优先级高于队列的回调地址
     */
    @XStreamOmitField
    private String callBack;
    
    /**
     * 任务回调TDMQ配置，当 CallBackType 为 TDMQ 时必填
     */
    @XStreamOmitField
    private String callBackMqConfig;

    public DocAIGCMetadataJobRequest() {
        this.input = new DocAIGCMetadataInput();
        this.operation = new DocAIGCMetadataOperation();
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

    public String getCallBackMqConfig() {
        return callBackMqConfig;
    }

    public void setCallBackMqConfig(String callBackMqConfig) {
        this.callBackMqConfig = callBackMqConfig;
    }

    @Override
    public String toString() {
        return "DocAIGCMetadataJobRequest{" +
                "bucketName='" + bucketName + '\'' +
                ", queueId='" + queueId + '\'' +
                ", tag='" + tag + '\'' +
                ", input=" + input +
                ", operation=" + operation +
                ", callBackFormat='" + callBackFormat + '\'' +
                ", callBackType='" + callBackType + '\'' +
                ", callBack='" + callBack + '\'' +
                ", callBackMqConfig='" + callBackMqConfig + '\'' +
                '}';
    }
}