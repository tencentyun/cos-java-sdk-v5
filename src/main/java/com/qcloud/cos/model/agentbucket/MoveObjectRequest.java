package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Move Object / Move Object Recursive 共用请求：在同一存储桶内将对象（文件或目录）移动到新位置。
 * <ul>
 * <li>{@code moveObject}：冲突策略支持 ASK / RENAME / OVERWRITE</li>
 * <li>{@code moveObjectRecursive}：递归移动整个目录树，冲突策略仅支持 ASK / RENAME</li>
 * </ul>
 *
 * @see <a href="https://cloud.tencent.com/document/product/436/133865">Move Object</a>
 * @see <a href="https://cloud.tencent.com/document/product/436/133862">Move Object Recursive</a>
 * @since 5.6.283
 */
public class MoveObjectRequest extends CosServiceRequest implements Serializable {
    private String bucketName;
    private String sourceKey;
    private String destinationKey;
    /** 冲突解决策略，为 null 时不传该参数，由服务端按默认 ask 处理 */
    private ConflictResolutionStrategy conflictResolutionStrategy;

    public MoveObjectRequest() {
    }

    public MoveObjectRequest(String bucketName, String sourceKey, String destinationKey) {
        this.bucketName = bucketName;
        this.sourceKey = sourceKey;
        this.destinationKey = destinationKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public MoveObjectRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    public String getSourceKey() {
        return sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public MoveObjectRequest withSourceKey(String sourceKey) {
        setSourceKey(sourceKey);
        return this;
    }

    public String getDestinationKey() {
        return destinationKey;
    }

    public void setDestinationKey(String destinationKey) {
        this.destinationKey = destinationKey;
    }

    public MoveObjectRequest withDestinationKey(String destinationKey) {
        setDestinationKey(destinationKey);
        return this;
    }

    public ConflictResolutionStrategy getConflictResolutionStrategy() {
        return conflictResolutionStrategy;
    }

    public void setConflictResolutionStrategy(ConflictResolutionStrategy conflictResolutionStrategy) {
        this.conflictResolutionStrategy = conflictResolutionStrategy;
    }

    public MoveObjectRequest withConflictResolutionStrategy(
            ConflictResolutionStrategy conflictResolutionStrategy) {
        setConflictResolutionStrategy(conflictResolutionStrategy);
        return this;
    }
}
