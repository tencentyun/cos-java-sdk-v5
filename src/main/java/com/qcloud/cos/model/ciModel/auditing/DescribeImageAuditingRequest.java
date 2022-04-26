package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.internal.CIServiceRequest;

/**
 * 图片审核请求实体 参数详情参考：https://cloud.tencent.com/document/product/460/37318
 */
public class DescribeImageAuditingRequest extends CIServiceRequest {

    /**
     * 操作的bucket名称
     */
    private String bucketName;
    /**
     * 任务id 用于查询
     */
    private String jobId;

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @Override
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "DescriptionImageAuditingRequest{" +
                "bucketName='" + bucketName + '\'' +
                ", jobId='" + jobId + '\'' +
                '}';
    }
}
