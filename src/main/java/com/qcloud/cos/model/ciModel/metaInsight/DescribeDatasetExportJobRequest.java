package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;

/**
 * 查询单个数据集异步导出任务请求（V2.7.0 新增）。
 * <p>
 * 对应 MetaInsight 接口：GET /datasetquery/export?datasetname=xxx&amp;jobid=yyy。
 * 字段名沿用项目 GET 接口约定（{@link DescribeDatasetRequest} 同款），
 * 全部使用小写命名，便于 COSClient 直接以字段名拼接 URL query。
 * </p>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class DescribeDatasetExportJobRequest extends CIServiceRequest {

    /**
     * 数据集名称。
     * 是否必传：是
     */
    private String datasetname;

    /**
     * 异步任务 ID（创建任务时返回的 JobId）。
     * 是否必传：是
     */
    private String jobid;

    public String getDatasetname() { return datasetname; }

    public void setDatasetname(String datasetname) { this.datasetname = datasetname; }

    public String getJobid() { return jobid; }

    public void setJobid(String jobid) { this.jobid = jobid; }

}
