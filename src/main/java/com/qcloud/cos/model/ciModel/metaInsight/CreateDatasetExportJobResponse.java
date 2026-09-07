package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;

/**
 * 创建数据集异步导出任务响应（V2.7.0 新增）。
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class CreateDatasetExportJobResponse extends CiServiceResult {

    /**
     * 创建出的异步任务，含后端生成的 {@code JobId}、初始状态等。
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
