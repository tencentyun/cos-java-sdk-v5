package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;

/**
 * 取消数据集异步导出任务响应（V2.7.0 新增）。
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class CancelDatasetExportJobResponse extends CiServiceResult {

    /**
     * 请求 ID。
     */
    private String requestId;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

}
