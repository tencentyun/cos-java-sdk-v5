
package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 文档AIGC元数据处理任务输出实体
 * https://cloud.tencent.com/document/product/460/123076
 */
public class DocAIGCMetadataOutput implements Serializable {
    
    /**
     * 存储桶的地域
     */
    @XStreamAlias("Region")
    private String region;
    
    /**
     * 存储结果的存储桶
     */
    @XStreamAlias("Bucket")
    private String bucket;
    
    /**
     * 输出文件路径
     * 非表格文件输出文件名需包含 ${Number} 或 ${Page} 参数
     * 表格文件输出路径需包含 ${SheetID} 占位符，输出文件名必须包含 ${Number} 参数
     */
    @XStreamAlias("Object")
    private String object;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "DocAIGCMetadataOutput{" +
                "region='" + region + '\'' +
                ", bucket='" + bucket + '\'' +
                ", object='" + object + '\'' +
                '}';
    }
}