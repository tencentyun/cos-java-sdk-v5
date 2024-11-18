package com.qcloud.cos.model;

public class BucketEncryptionConfiguration {
    private String sseAlgorithm = "";

    private String kmsMasterKeyID = "";

    private String kMSAlgorithm = "";

    private String bucketEnabled = "";

    public String getKMSAlgorithm() {
        return kMSAlgorithm;
    }

    public void setKMSAlgorithm(String kMSAlgorithm) {
        this.kMSAlgorithm = kMSAlgorithm;
    }

    public String getKmsMasterKeyID() {
        return kmsMasterKeyID;
    }

    public void setKmsMasterKeyID(String kmsMasterKeyID) {
        this.kmsMasterKeyID = kmsMasterKeyID;
    }

    public String getSseAlgorithm() {
        return sseAlgorithm;
    }

    public void setSseAlgorithm(String sseAlgorithm) {
        this.sseAlgorithm = sseAlgorithm;
    }

    public String getBucketEnabled() {
        return bucketEnabled;
    }

    public void setBucketEnabled(String bucketEnabled) {
        this.bucketEnabled = bucketEnabled;
    }
}
