package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CosServiceRequest;

import java.io.Serializable;

public class ImageQualityRequest  extends CosServiceRequest implements Serializable {

    private String bucketName;

    private String objectKey;

    private String ciProcess = "AssessQuality";

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getCiProcess() {
        return ciProcess;
    }

    public void setCiProcess(String ciProcess) {
        this.ciProcess = ciProcess;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
