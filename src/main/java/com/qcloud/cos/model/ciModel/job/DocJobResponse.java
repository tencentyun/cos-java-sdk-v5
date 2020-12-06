package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CIServiceRequest;

/**
 * 文档预览任务响应实体
 */
public class DocJobResponse extends CIServiceRequest {

    /**
     * 文档预览任务对象
     */
    private DocJobDetail jobsDetail;

    public DocJobDetail getJobsDetail() {
        if (jobsDetail == null) {
            jobsDetail = new DocJobDetail();
        }
        return jobsDetail;
    }

    public void setJobsDetail(DocJobDetail jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DocJobResponse{");
        sb.append("jobsDetail=").append(jobsDetail);
        sb.append('}');
        return sb.toString();
    }
}
