package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;

/**
 * 查询单个数据集异步导出任务响应（V2.7.0 新增）。
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class DescribeDatasetExportJobResponse extends CiServiceResult {

    /**
     * 任务详情，含状态、进度、起止时间等。
     */
    private DatasetExportJob job;

    /**
     * 请求 ID。
     */
    private String requestId;

    public DatasetExportJob getJob() { return job; }

    public void setJob(DatasetExportJob job) { this.job = job; }

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

}
