package com.qcloud.cos.model.Policy;

import java.util.ArrayList;
import java.util.List;

public class BucketStatementResult {
    public List<SubUinResult> formatPolicy = new ArrayList<>();

    public List<SubUinResult> getFormatPolicy() {
        return formatPolicy;
    }

    public void setFormatPolicy(List<SubUinResult> subUinResults) {
        this.formatPolicy = subUinResults;
    }

    public void addFormatPolicy(SubUinResult subUinResult) {
        this.formatPolicy.add(subUinResult);
    }
}
