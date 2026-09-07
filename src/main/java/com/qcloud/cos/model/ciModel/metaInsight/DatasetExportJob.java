package com.qcloud.cos.model.ciModel.metaInsight;

/**
 * 数据集异步导出任务（V2.7.0 新增）。
 * <p>
 * 在异步导出 4 个接口（CreateDatasetExportJob / DescribeDatasetExportJob /
 * DescribeDatasetExportJobs / CancelDatasetExportJob）的请求与响应中复用。
 * 既作为 POST body 字段，也作为响应字段反序列化。
 * </p>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class DatasetExportJob {

    /**
     * 异步任务 ID（创建任务时由后端生成并返回）。
     */
    private String jobId;

    /**
     * 任务名称，用户自定义。
     */
    private String name;

    /**
     * 异步任务类型：
     * <ul>
     *   <li>{@code 1}：数据导出（结果回写到 {@link DatasetExportOutput} 指向的 COS 对象）</li>
     *   <li>{@code 2}：GFS 数据预热（按 {@link GfsPreHeatConfig} 预热到 GFS 集群）</li>
     * </ul>
     */
    private Integer exportJobType;

    /**
     * 任务状态：{@code Unstart} / {@code Running} / {@code Success} / {@code Failed} / {@code Cancelled}。
     */
    private String state;

    /**
     * 检索过滤条件，复用 {@link Query} 结构。
     */
    private Query query;

    /**
     * 数据导出结果输出目标（{@code ExportJobType=1} 时必填）。
     */
    private DatasetExportOutput output;

    /**
     * GFS 数据预热配置（{@code ExportJobType=2} 时必填）。
     */
    private GfsPreHeatConfig gfsPreHeatConfig;

    /**
     * 任务实际开始执行时间，ISO-8601 字符串。
     */
    private String startTime;

    /**
     * 任务结束时间，ISO-8601 字符串。
     */
    private String endTime;

    /**
     * 任务创建时间，ISO-8601 字符串。
     */
    private String createTime;

    /**
     * 任务执行进度，0-100。
     */
    private Integer progress;

    public String getJobId() { return jobId; }

    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getExportJobType() { return exportJobType; }

    public void setExportJobType(Integer exportJobType) { this.exportJobType = exportJobType; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public Query getQuery() { return query; }

    public void setQuery(Query query) { this.query = query; }

    public DatasetExportOutput getOutput() { return output; }

    public void setOutput(DatasetExportOutput output) { this.output = output; }

    public GfsPreHeatConfig getGfsPreHeatConfig() { return gfsPreHeatConfig; }

    public void setGfsPreHeatConfig(GfsPreHeatConfig gfsPreHeatConfig) { this.gfsPreHeatConfig = gfsPreHeatConfig; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }

    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public Integer getProgress() { return progress; }

    public void setProgress(Integer progress) { this.progress = progress; }

}
