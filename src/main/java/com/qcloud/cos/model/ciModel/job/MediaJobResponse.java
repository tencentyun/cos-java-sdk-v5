package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaJobResponse extends MediaJobObject {
    @XStreamAlias("JobsDetail")
    private MediaJobObject jobsDetail;

    public MediaJobObject getJobsDetail() {
        return jobsDetail;
    }

    public void setJobsDetail(MediaJobObject jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

    @Override
    public String toString() {
        return "MediaJobResponse{" +
                "jobsDetail=" + jobsDetail +
                '}';
    }
}
