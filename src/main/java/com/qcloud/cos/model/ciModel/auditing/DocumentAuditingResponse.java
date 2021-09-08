package com.qcloud.cos.model.ciModel.auditing;


import com.qcloud.cos.internal.CIServiceRequest;

/**
 * 文档审核响应实体
 */
public class DocumentAuditingResponse extends CIServiceRequest {
    private DocumentAuditingJobsDetail jobsDetail;

    public DocumentAuditingJobsDetail getJobsDetail() {
        if (jobsDetail == null) {
            jobsDetail = new DocumentAuditingJobsDetail();
        }
        return jobsDetail;
    }

    public void setJobsDetail(DocumentAuditingJobsDetail jobsDetail) {
        this.jobsDetail = jobsDetail;
    }
}
