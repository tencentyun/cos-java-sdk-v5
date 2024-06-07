package com.qcloud.cos.model.ciModel.metaInsight;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Dataset")
public class Dataset {

    /**
     *   模板ID。   
     */
    @XStreamAlias("TemplateId")
    private String templateId;

    /**
     *   数据集描述信息 
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 数据集创建时间的时间戳，格式为RFC3339Nano 
     */
    @XStreamAlias("CreateTime")
    private String createTime;

    /**
     * 数据集修改时间的时间戳，格式为RFC3339Nano创建数据集后，如果未更新过数据集，则数据集修改时间的时间戳和数据集创建时间的时间戳相同 
     */
    @XStreamAlias("UpdateTime")
    private String updateTime;

    /**
     * 数据集当前绑定的COS Bucket数量                               
     */
    @XStreamAlias("BindCount")
    private Integer bindCount;

    /**
     * 数据集当前文件数量                                           
     */
    @XStreamAlias("FileCount")
    private Integer fileCount;

    /**
     * 数据集中当前文件总大小，单位为字节                           
     */
    @XStreamAlias("TotalFileSize")
    private Integer totalFileSize;

    /**
     *数据集名称
     */
    @XStreamAlias("DatasetName")
    private String datasetName;

    public String getTemplateId() { return templateId; }

    public void setTemplateId(String templateId) { this.templateId = templateId; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public String getUpdateTime() { return updateTime; }

    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }

    public Integer getBindCount() { return bindCount; }

    public void setBindCount(Integer bindCount) { this.bindCount = bindCount; }

    public Integer getFileCount() { return fileCount; }

    public void setFileCount(Integer fileCount) { this.fileCount = fileCount; }

    public Integer getTotalFileSize() { return totalFileSize; }

    public void setTotalFileSize(Integer totalFileSize) { this.totalFileSize = totalFileSize; }

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }



}
