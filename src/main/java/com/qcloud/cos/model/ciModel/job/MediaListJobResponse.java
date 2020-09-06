package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class MediaListJobResponse  {
    @XStreamImplicit(itemFieldName = "JobsDetail")
    private List<MediaJobObject> jobsDetail;

    @XStreamAlias("NextToken")
    private String nextToken;

    public List<MediaJobObject> getJobsDetail() {
        return jobsDetail;
    }

    public void setJobsDetail(List<MediaJobObject> jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }
}
