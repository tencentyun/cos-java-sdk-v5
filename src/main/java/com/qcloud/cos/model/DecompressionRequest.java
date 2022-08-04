package com.qcloud.cos.model;

import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.XmlWriter;

import java.io.Serializable;

public class DecompressionRequest extends CosServiceRequest implements Serializable {
    /**
     * 待解压的文件所在桶
     */
    private String sourceBucketName;
    /**
     * 解压结果所在桶
     */
    private String targetBucketName;
    /**
     * 是否替换前缀
     */
    private boolean prefixReplaced;
    /**
     * 原前缀
     */
    private String resourcesPrefix;
    /**
     * 替换的前缀，与prefixReplaced搭配使用
     */
    private String targetKeyPrefix;
    /**
     * 对象的key
     */
    private String objectKey;

    public String getObjectKey() {
        return objectKey;
    }

    public String getTargetBucketName() {
        return targetBucketName;
    }

    public String getSourceBucketName() {
        return sourceBucketName;
    }

    public boolean isPrefixReplaced() {
        return prefixReplaced;
    }

    public boolean getPrefixReplaced() {
        return prefixReplaced;
    }

    public String getResourcesPrefix() {
        return resourcesPrefix;
    }

    public String getTargetKeyPrefix() {
        return targetKeyPrefix;
    }

    public DecompressionRequest setTargetBucketName(String targetBucketName) {
        this.targetBucketName = targetBucketName;
        return this;
    }

    public DecompressionRequest setPrefixReplaced(boolean prefixReplaced) {
        this.prefixReplaced = prefixReplaced;
        return this;
    }

    public DecompressionRequest setResourcesPrefix(String resourcesPrefix) {
        this.resourcesPrefix = resourcesPrefix;
        return this;
    }

    public DecompressionRequest setTargetKeyPrefix(String targetKeyPrefix) {
        this.targetKeyPrefix = targetKeyPrefix;
        return this;
    }

    public DecompressionRequest setObjectKey(String objectKey) {
        this.objectKey = objectKey;
        return this;
    }

    public DecompressionRequest setSourceBucketName(String sourceBucketName) {
        this.sourceBucketName = sourceBucketName;
        return this;
    }

    @Override
    public String toString() {
        return "DecompressionRequest{" +
                "sourceBucketName='" + sourceBucketName + '\'' +
                ", targetBucketName='" + targetBucketName + '\'' +
                ", prefixReplaced=" + prefixReplaced +
                ", resourcesPrefix='" + resourcesPrefix + '\'' +
                ", targetKeyPrefix='" + targetKeyPrefix + '\'' +
                ", objectKey='" + objectKey + '\'' +
                '}';
    }

    public static byte[] convertToByteArray(DecompressionRequest decompressionRequest) {
        XmlWriter xml = new XmlWriter();
        xml.start("DecompressionRequest").start("OutputConfiguration").start("BucketName")
                .value(decompressionRequest.getTargetBucketName()).end().start("PrefixReplaced")
                .value(String.valueOf(decompressionRequest.getPrefixReplaced())).end()
                .start("ResourcesPrefix").value(decompressionRequest.getResourcesPrefix()).end()
                .start("TargetKeyPrefix").value(decompressionRequest.getTargetKeyPrefix()).end().end()
                .end();
        return xml.getBytes();
    }
}
