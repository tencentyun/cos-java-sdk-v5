package com.qcloud.cos.model.IntelligentTiering;

import java.util.List;

public class BucketIntelligentTieringConfiguration {
    private String ruleId;

    private String status;

    private IntelligentTieringFilter filter;

    private List<IntelligentTieringTransition> tieringTransitions;

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setFilter(IntelligentTieringFilter filter) {
        this.filter = filter;
    }

    public IntelligentTieringFilter getFilter() {
        return filter;
    }

    public void setTieringTransitions(List<IntelligentTieringTransition> tieringTransitions) {
        this.tieringTransitions = tieringTransitions;
    }

    public List<IntelligentTieringTransition> getTieringTransitions() {
        return tieringTransitions;
    }
}
