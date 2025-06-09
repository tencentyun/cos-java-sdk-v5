package com.qcloud.cos.model.inventory;

import com.qcloud.cos.model.CosServiceResult;

public class PostBucketInventoryConfigurationResult extends CosServiceResult {
    private String jobId;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
