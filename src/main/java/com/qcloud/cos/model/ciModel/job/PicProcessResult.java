package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.persistence.OriginalInfo;

public class PicProcessResult {
    private String objectName;
    private OriginalInfo originalInfo;
    private ProcessResult processResult;

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public OriginalInfo getOriginalInfo() {
        if (originalInfo == null) {
            originalInfo = new OriginalInfo();
        }
        return originalInfo;
    }

    public void setOriginalInfo(OriginalInfo originalInfo) {
        this.originalInfo = originalInfo;
    }

    public ProcessResult getProcessResult() {
        if (processResult == null) {
            processResult = new ProcessResult();
        }
        return processResult;
    }

    public void setProcessResult(ProcessResult processResult) {
        this.processResult = processResult;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PicProcessResult{");
        sb.append("objectName='").append(objectName).append('\'');
        sb.append(", originalInfo=").append(originalInfo);
        sb.append(", processResult=").append(processResult);
        sb.append('}');
        return sb.toString();
    }
}
