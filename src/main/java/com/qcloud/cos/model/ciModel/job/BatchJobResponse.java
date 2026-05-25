package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.CiServiceResult;

public class BatchJobResponse extends CiServiceResult {
    private BatchJobDetail jobDetail;

    public BatchJobDetail getJobDetail() {
        if (jobDetail == null) {
            jobDetail = new BatchJobDetail();
        }
        return jobDetail;
    }

    public void setJobDetail(BatchJobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    @Override
    public String toString() {
        return "BatchJobResponse{" +
                "jobDetail=" + jobDetail +
                ", requestId='" + getRequestId() + '\'' +
                '}';
    }
}
