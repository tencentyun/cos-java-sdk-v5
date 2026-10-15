package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Copy Object Recursive 请求：将整个目录树（含所有子文件和子目录）复制到目标位置。
 * <p>
 * 冲突策略仅支持 {@link ConflictResolutionStrategy#ASK}（默认）与 {@link ConflictResolutionStrategy#RENAME}；
 * 不支持 OVERWRITE，传入时 SDK 直接抛出 IllegalArgumentException。
 * 源桶与目标桶使用 COSClient 所配置的同一地域 endpoint。
 * </p>
 *
 * @see <a href="https://cloud.tencent.com/document/product/436/133866">Copy Object Recursive</a>
 * @since 5.6.283
 */
public class CopyObjectRecursiveRequest extends CosServiceRequest implements Serializable {
    private String sourceBucketName;
    private String sourceKey;
    private String destinationBucketName;
    private String destinationKey;
    /** 冲突解决策略，为 null 时不传该参数 */
    private ConflictResolutionStrategy conflictResolutionStrategy;

    public CopyObjectRecursiveRequest() {
    }

    public CopyObjectRecursiveRequest(String sourceBucketName, String sourceKey,
            String destinationBucketName, String destinationKey) {
        this.sourceBucketName = sourceBucketName;
        this.sourceKey = sourceKey;
        this.destinationBucketName = destinationBucketName;
        this.destinationKey = destinationKey;
    }

    public String getSourceBucketName() {
        return sourceBucketName;
    }

    public void setSourceBucketName(String sourceBucketName) {
        this.sourceBucketName = sourceBucketName;
    }

    public CopyObjectRecursiveRequest withSourceBucketName(String sourceBucketName) {
        setSourceBucketName(sourceBucketName);
        return this;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public CopyObjectRecursiveRequest withSourceKey(String sourceKey) {
        setSourceKey(sourceKey);
        return this;
    }

    public String getDestinationBucketName() {
        return destinationBucketName;
    }

    public void setDestinationBucketName(String destinationBucketName) {
        this.destinationBucketName = destinationBucketName;
    }

    public CopyObjectRecursiveRequest withDestinationBucketName(String destinationBucketName) {
        setDestinationBucketName(destinationBucketName);
        return this;
    }

    public String getDestinationKey() {
        return destinationKey;
    }

    public void setDestinationKey(String destinationKey) {
        this.destinationKey = destinationKey;
    }

    public CopyObjectRecursiveRequest withDestinationKey(String destinationKey) {
        setDestinationKey(destinationKey);
        return this;
    }

    public ConflictResolutionStrategy getConflictResolutionStrategy() {
        return conflictResolutionStrategy;
    }

    public void setConflictResolutionStrategy(ConflictResolutionStrategy conflictResolutionStrategy) {
        this.conflictResolutionStrategy = conflictResolutionStrategy;
    }

    public CopyObjectRecursiveRequest withConflictResolutionStrategy(
            ConflictResolutionStrategy conflictResolutionStrategy) {
        setConflictResolutionStrategy(conflictResolutionStrategy);
        return this;
    }
}
