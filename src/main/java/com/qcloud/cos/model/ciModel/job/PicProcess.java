package com.qcloud.cos.model.ciModel.job;

public class PicProcess {
    private String isPicInfo;
    private String processRule;

    public String getIsPicInfo() {
        return isPicInfo;
    }

    public void setIsPicInfo(String isPicInfo) {
        this.isPicInfo = isPicInfo;
    }

    public String getProcessRule() {
        return processRule;
    }

    public void setProcessRule(String processRule) {
        this.processRule = processRule;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PicProcess{");
        sb.append("isPicInfo='").append(isPicInfo).append('\'');
        sb.append(", processRule='").append(processRule).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
