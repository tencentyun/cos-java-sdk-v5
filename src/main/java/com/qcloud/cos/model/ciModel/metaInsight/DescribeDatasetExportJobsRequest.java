package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;

/**
 * 查询数据集异步导出任务列表请求（V2.7.0 新增）。
 * <p>
 * 对应 MetaInsight 接口：GET /datasetquery/exports?datasetname=xxx&amp;maxresults=100&amp;nexttoken=xxx。
 * 字段名沿用项目 GET 接口约定（{@link DescribeDatasetsRequest} 同款），
 * 全部使用小写命名，便于 COSClient 直接以字段名拼接 URL query。
 * </p>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class DescribeDatasetExportJobsRequest extends CIServiceRequest {

    /**
     * 数据集名称。
     * 是否必传：是
     */
    private String datasetname;

    /**
     * 单次返回结果上限，默认 100，最大 200。
     * 是否必传：否
     */
    private Integer maxresults;

    /**
     * 分页 token，首次查询不传。
     * 是否必传：否
     */
    private String nexttoken;

    public String getDatasetname() { return datasetname; }

    public void setDatasetname(String datasetname) { this.datasetname = datasetname; }

    public Integer getMaxresults() { return maxresults; }

    public void setMaxresults(Integer maxresults) { this.maxresults = maxresults; }

    public String getNexttoken() { return nexttoken; }

    public void setNexttoken(String nexttoken) { this.nexttoken = nexttoken; }

}
