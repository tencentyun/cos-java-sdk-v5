package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文本审核响应实体 参数详情参考：https://cloud.tencent.com/document/product/460/37318
 */
@XStreamAlias("Response")
public class TextAuditingResponse extends CiServiceResult {
    /**
     * 任务的详细信息
     */
    @XStreamAlias("JobsDetail")
    private AuditingJobsDetail jobsDetail;

    public AuditingJobsDetail getJobsDetail() {
        if (jobsDetail == null) {
            jobsDetail = new AuditingJobsDetail();
        }
        return jobsDetail;
    }

    public void setJobsDetail(AuditingJobsDetail jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VideoAuditingResponse{");
        sb.append("jobsDetail=").append(jobsDetail);
        sb.append('}');
        return sb.toString();
    }
}
