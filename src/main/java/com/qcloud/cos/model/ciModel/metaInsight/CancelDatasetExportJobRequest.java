package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;

/**
 * 取消数据集异步导出任务请求（V2.7.0 新增）。
 * <p>
 * 对应 MetaInsight 接口：DELETE /datasetquery/export，
 * 请求体走 JSON body（PascalCase 字段），与 {@link DeleteDatasetRequest} 风格一致。
 * </p>
 * <p><b>注意</b>：仅可取消处于 {@code Unstart} / {@code Running} 状态的任务，
 * 已完成（{@code Success} / {@code Failed} / {@code Cancelled}）的任务再取消会得到后端的 4xx 错误。</p>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class CancelDatasetExportJobRequest extends CIServiceRequest {

    /**
     * 数据集名称。
     * 是否必传：是
     */
    private String datasetName;

    /**
     * 待取消的异步任务 ID。
     * 是否必传：是
     */
    private String jobId;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getJobId() { return jobId; }

    public void setJobId(String jobId) { this.jobId = jobId; }

}
