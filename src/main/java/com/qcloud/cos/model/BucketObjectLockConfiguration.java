package com.qcloud.cos.model;

public class BucketObjectLockConfiguration {
    private String status;
    private String retentionMode;
    private int retentionDays = -1;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRetentionMode(String retentionMode) {
        this.retentionMode = retentionMode;
    }

    public String getRetentionMode() {
        return retentionMode;
    }

    public void setRetentionDays(int retentionDays) {
        this.retentionDays = retentionDays;
    }

    public int getRetentionDays() {
        return retentionDays;
    }
}
