package com.qcloud.cos.model.ciModel.auditing;

public class MaskInfo {
    private AuditingLiveInfo auditingLiveInfo;
    private AuditingRecordInfo recordInfo;

    public AuditingLiveInfo getAuditingLiveInfo() {
        if (auditingLiveInfo == null) {
            auditingLiveInfo = new AuditingLiveInfo();
        }
        return auditingLiveInfo;
    }

    public void setAuditingLiveInfo(AuditingLiveInfo auditingLiveInfo) {
        this.auditingLiveInfo = auditingLiveInfo;
    }

    public AuditingRecordInfo getRecordInfo() {
        if (recordInfo == null) {
            recordInfo = new AuditingRecordInfo();
        }
        return recordInfo;
    }

    public void setRecordInfo(AuditingRecordInfo recordInfo) {
        this.recordInfo = recordInfo;
    }
}
