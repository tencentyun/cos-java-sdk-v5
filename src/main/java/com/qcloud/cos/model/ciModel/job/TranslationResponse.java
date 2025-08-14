package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Response")
public class TranslationResponse extends CiServiceResult {

    @XStreamAlias("JobsDetail")
    private JobsDetail jobsDetail;

    public String getJobId() {
        return jobsDetail != null ? jobsDetail.getJobId() : null;
    }

    public JobsDetail getJobsDetail() {
        return jobsDetail;
    }

    public void setJobsDetail(JobsDetail jobsDetail) {
        this.jobsDetail = jobsDetail;
    }
}