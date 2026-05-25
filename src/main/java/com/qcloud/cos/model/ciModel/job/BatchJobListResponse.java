package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.CiServiceResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量任务列表响应实体
 */
public class BatchJobListResponse extends CiServiceResult {
    private List<BatchJobDetail> jobsDetailList;
    private String nextToken;

    public List<BatchJobDetail> getJobsDetailList() {
        if (jobsDetailList == null) {
            jobsDetailList = new ArrayList<>();
        }
        return jobsDetailList;
    }

    public void setJobsDetailList(List<BatchJobDetail> jobsDetailList) {
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
        return "BatchJobListResponse{" +
                "jobsDetailList=" + jobsDetailList +
                ", nextToken='" + nextToken + '\'' +
                '}';
    }
}
