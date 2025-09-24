package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;


/**
 * 媒体处理 任务列表响应实体 https://cloud.tencent.com/document/product/460/48234
 */
@XStreamAlias("Response")
public class MediaListJobResponse extends CiServiceResult {
    @XStreamImplicit(itemFieldName = "JobsDetail")
    private List<MediaJobObject> jobsDetailList;

    @XStreamAlias("NextToken")
    private String nextToken;

    public List<MediaJobObject> getJobsDetailList() {
        if (jobsDetailList == null) {
            jobsDetailList = new ArrayList<>();
        }
        return jobsDetailList;
    }

    public void setJobsDetailList(List<MediaJobObject> jobsDetailList) {
        this.jobsDetailList = jobsDetailList;
    }

    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    @Override
    public String toString() {
        return "MediaListJobResponse{" +
                "jobsDetail=" + jobsDetailList +
                ", nextToken='" + nextToken + '\'' +
                '}';
    }
}


