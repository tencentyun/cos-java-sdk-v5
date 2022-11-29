package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.CiServiceResult;


public class PicProcessResponse extends CiServiceResult {
    private MediaJobObject jobsDetail;

    public MediaJobObject getJobsDetail() {
        if (jobsDetail==null){
            jobsDetail = new MediaJobObject();
        }
        return jobsDetail;
    }

    @Override
    public String toString() {
        return "MediaJobResponse{" +
                "jobsDetail=" + jobsDetail +
                '}';
    }
}
