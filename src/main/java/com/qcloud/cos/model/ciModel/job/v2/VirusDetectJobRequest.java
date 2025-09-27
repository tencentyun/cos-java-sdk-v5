
package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.io.Serializable;

/**
 * 病毒检测任务查询请求类
 * 用于查询已提交的病毒检测任务的详细结果
 */
public class VirusDetectJobRequest extends CIServiceRequest implements Serializable {

    /**
     * bucket名称
     */
    @XStreamOmitField
    private String bucketName;

    /**
     * 任务ID
     */
    @XStreamOmitField
    private String jobId;

    public String getBucketName() {
        return bucketName;
    }

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
        return "VirusDetectJobRequest{" +
                "bucketName='" + bucketName + '\'' +
                ", jobId='" + jobId + '\'' +
                '}';
    }
}
