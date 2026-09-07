package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;

import java.util.List;

/**
 * 查询数据集异步导出任务列表响应（V2.7.0 新增）。
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class DescribeDatasetExportJobsResponse extends CiServiceResult {

    /**
     * 异步任务列表。
     */
    private List<DatasetExportJob> jobs;

    /**
     * 翻页 token，下一次请求传该值即可继续翻页。
     * 为空字符串时表示已查到末尾。
     */
    private String nextToken;

    /**
     * 请求 ID。
     */
    private String requestId;

    public List<DatasetExportJob> getJobs() { return jobs; }

    public void setJobs(List<DatasetExportJob> jobs) { this.jobs = jobs; }

    public String getNextToken() { return nextToken; }

    public void setNextToken(String nextToken) { this.nextToken = nextToken; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

}
