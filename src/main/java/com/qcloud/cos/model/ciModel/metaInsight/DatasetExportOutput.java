package com.qcloud.cos.model.ciModel.metaInsight;

/**
 * 数据集导出任务的输出目标（V2.7.0 新增）。
 * <p>
 * 在 {@code ExportJobType=1} 的数据导出场景下使用，
 * 后端会把检索结果集打包写到该 COS Object 中。
 * </p>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class DatasetExportOutput {

    /**
     * 输出存储桶所在地域，例如 {@code ap-guangzhou}。
     */
    private String region;

    /**
     * 输出存储桶名称（含 APPID 后缀）。
     */
    private String bucket;

    /**
     * 输出对象 Key，例如 {@code export/result_20260101.json}。
     */
    private String object;

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public String getBucket() { return bucket; }

    public void setBucket(String bucket) { this.bucket = bucket; }

    public String getObject() { return object; }

    public void setObject(String object) { this.object = object; }

}
