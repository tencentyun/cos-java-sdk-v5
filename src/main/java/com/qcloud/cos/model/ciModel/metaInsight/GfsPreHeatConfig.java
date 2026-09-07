package com.qcloud.cos.model.ciModel.metaInsight;

/**
 * GFS 数据预热配置（V2.7.0 新增）。
 * <p>
 * 在 {@code ExportJobType=2} 的 GFS 预热场景下使用，
 * 指定将检索结果中的文件预热到哪个 GFS 集群、以何优先级。
 * </p>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 */
public class GfsPreHeatConfig {

    /**
     * GFS 集群 ID。
     */
    private String clusterId;

    /**
     * 预热优先级，数值越大越优先。
     */
    private Integer priority;

    public String getClusterId() { return clusterId; }

    public void setClusterId(String clusterId) { this.clusterId = clusterId; }

    public Integer getPriority() { return priority; }

    public void setPriority(Integer priority) { this.priority = priority; }

}
