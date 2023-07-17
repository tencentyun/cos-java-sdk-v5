package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("Strategy")
public class AuditingStrategy {

    @XStreamAlias("Service")
    private String service;
    @XStreamAlias("Bucket")
    private String bucket;
    @XStreamAlias("BizType")
    private String bizType;
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("Labels")
    private StrategyLabels labels;
    @XStreamImplicit(itemFieldName = "TextLibs")
    private List<String> textLabel;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StrategyLabels getLabels() {
        return labels;
    }

    public void setLabels(StrategyLabels labels) {
        this.labels = labels;
    }

    public List<String> getTextLabel() {
        return textLabel;
    }

    public void setTextLabel(List<String> textLabel) {
        this.textLabel = textLabel;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
