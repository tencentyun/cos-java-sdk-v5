
package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 文档AIGC元数据配置实体
 * https://cloud.tencent.com/document/product/460/123076
 */
public class DocAIGCMetadataConfig implements Serializable {
    
    /**
     * 包含文档 AIGC 标识内容的具体参数
     */
    @XStreamAlias("AIGCMetadata")
    private DocAIGCMetadata aigcMetadata;

    public DocAIGCMetadataConfig() {
        this.aigcMetadata = new DocAIGCMetadata();
    }

    public DocAIGCMetadata getAigcMetadata() {
        if (aigcMetadata == null) {
            aigcMetadata = new DocAIGCMetadata();
        }
        return aigcMetadata;
    }

    public void setAigcMetadata(DocAIGCMetadata aigcMetadata) {
        this.aigcMetadata = aigcMetadata;
    }

    @Override
    public String toString() {
        return "DocAIGCMetadataConfig{" +
                "aigcMetadata=" + aigcMetadata +
                '}';
    }
}