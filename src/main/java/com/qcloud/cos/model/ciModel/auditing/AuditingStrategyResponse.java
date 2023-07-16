package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class AuditingStrategyResponse extends CiServiceResult {
    @XStreamAlias("BizType")
    private String bizType;
    @XStreamAlias("Strategy")
    private AuditingStrategy strategy;

    public AuditingStrategy getStrategy() {
        if (strategy == null) {
            strategy = new AuditingStrategy();
        }
        return strategy;
    }

    public void setStrategy(AuditingStrategy strategy) {
        this.strategy = strategy;
    }


    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}
