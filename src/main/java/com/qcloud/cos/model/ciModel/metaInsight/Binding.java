package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Binding {

    /**
     * 资源标识字段，表示需要与数据集绑定的资源，当前仅支持COS存储桶，字段规则：cos://，其中BucketName表示COS存储桶名称，例如：cos://examplebucket-1250000000 
     */
    @JsonProperty("URI")
    private String uRI;

    /**
     * 数据集和 COS Bucket绑定关系的状态。取值范围如下：Running：绑定关系运行中。 
     */
    @JsonProperty("State")
    private String state;

    /**
     * 数据集和 COS Bucket绑定关系创建时间的时间戳，格式为RFC3339Nano。 
     */
    @JsonProperty("CreateTime")
    private String createTime;

    /**
     * 数据集和 COS Bucket的绑定关系修改时间的时间戳，格式为RFC3339Nano。创建绑定关系后，如果未暂停或者未重启过绑定关系，则绑定关系修改时间的时间戳和绑定关系创建时间的时间戳相同。 
     */
    @JsonProperty("UpdateTime")
    private String updateTime;

    /**
     *数据集名称。
     */
    @JsonProperty("DatasetName")
    private String datasetName;

    /**
     *详情
     */
    @JsonProperty("Detail")
    private String detail;

    public String getURI() { return uRI; }

    public void setURI(String uRI) { this.uRI = uRI; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUpdateTime() { return updateTime; }

    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getDetail() { return detail; }

    public void setDetail(String detail) { this.detail = detail; }



}
