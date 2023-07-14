package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class AuditingStrategyResponse extends CiServiceResult {
    @XStreamAlias("RequestId")
    private String requestId;
    @XStreamAlias("BizType")
    private String bizType;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }
}
