
package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 文档AIGC元数据处理任务输入实体
 * https://cloud.tencent.com/document/product/460/123076
 */
public class DocAIGCMetadataInput implements Serializable {
    
    /**
     * 存储桶的地域
     */
    @XStreamAlias("Region")
    private String region;
    
    /**
     * 输入文件所在的存储桶
     */
    @XStreamAlias("BucketId")
    private String bucketId;
    
    /**
     * 输入文件所在存储桶中的文件路径
     * 支持的文档格式：pdf、md、xmind、docx、pptx、xlsx、dotx、potx、xltx
     */
    @XStreamAlias("Object")
    private String object;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "DocAIGCMetadataInput{" +
                "region='" + region + '\'' +
                ", bucketId='" + bucketId + '\'' +
                ", object='" + object + '\'' +
                '}';
    }
}