package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;

/**
 * 创建数据集异步导出任务请求（V2.7.0 新增）。
 * <p>
 * 对应 MetaInsight 接口：POST /datasetquery/export。
 * 支持两类导出任务，由 {@link #exportJobType} 控制：
 * </p>
 * <ul>
 *   <li>{@code 1}：数据导出（必须配合 {@link #output}）</li>
 *   <li>{@code 2}：GFS 数据预热（必须配合 {@link #gfsPreHeatConfig}）</li>
 * </ul>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class CreateDatasetExportJobRequest extends CIServiceRequest {

    /**
     * 任务名称，用户自定义，便于后续在控制台/查询接口中识别。
     * 是否必传：是
     */
    private String name;

    /**
     * 数据集名称，同一账户下唯一。
     * 是否必传：是
     */
    private String datasetName;

    /**
     * 异步任务类型：
     * <ul>
     *   <li>{@code 1}：数据导出（必须配合 {@link #output}）</li>
     *   <li>{@code 2}：GFS 数据预热（必须配合 {@link #gfsPreHeatConfig}）</li>
     * </ul>
     * 是否必传：是
     */
    private Integer exportJobType;

    /**
     * 检索过滤条件，复用 {@link Query} 结构。
     * 是否必传：否（不传时导出/预热整个数据集）
     */
    private Query query;

    /**
     * 数据导出结果输出目标，{@code ExportJobType=1} 时必传。
     * 是否必传：条件必传
     */
    private DatasetExportOutput output;

    /**
     * GFS 数据预热配置，{@code ExportJobType=2} 时必传。
     * 是否必传：条件必传
     */
    private GfsPreHeatConfig gfsPreHeatConfig;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public Integer getExportJobType() { return exportJobType; }

    public void setExportJobType(Integer exportJobType) { this.exportJobType = exportJobType; }

    public Query getQuery() { return query; }

    public void setQuery(Query query) { this.query = query; }

    public DatasetExportOutput getOutput() { return output; }

    public void setOutput(DatasetExportOutput output) { this.output = output; }

    public GfsPreHeatConfig getGfsPreHeatConfig() { return gfsPreHeatConfig; }

    public void setGfsPreHeatConfig(GfsPreHeatConfig gfsPreHeatConfig) { this.gfsPreHeatConfig = gfsPreHeatConfig; }

}
