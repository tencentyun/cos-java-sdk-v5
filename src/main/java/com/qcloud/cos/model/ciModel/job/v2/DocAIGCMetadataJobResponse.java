
package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文档AIGC元数据处理任务响应实体
 * https://cloud.tencent.com/document/product/460/123076
 */
@XStreamAlias("Response")
public class DocAIGCMetadataJobResponse extends CiServiceResult {
    
    /**
     * 任务的详细信息
     */
    @XStreamAlias("JobsDetail")
    private DocAIGCMetadataJobObject jobsDetail;

    public DocAIGCMetadataJobObject getJobsDetail() {
        if (jobsDetail == null) {
            jobsDetail = new DocAIGCMetadataJobObject();
        }
        return jobsDetail;
    }

    public void setJobsDetail(DocAIGCMetadataJobObject jobsDetail) {
        this.jobsDetail = jobsDetail;
    }

    @Override
    public String toString() {
        return "DocAIGCMetadataJobResponse{" +
                "jobsDetail=" + jobsDetail +
                '}';
    }
}