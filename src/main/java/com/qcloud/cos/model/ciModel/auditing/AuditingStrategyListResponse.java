package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("Response")
public class AuditingStrategyListResponse extends CiServiceResult {
    @XStreamAlias("TotalCount")
    private String totalCount;
    @XStreamImplicit(itemFieldName = "Strategies")
    private List<AuditingStrategy> strategy;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<AuditingStrategy> getStrategy() {
        return strategy;
    }

    public void setStrategy(List<AuditingStrategy> strategy) {
        this.strategy = strategy;
    }
}
