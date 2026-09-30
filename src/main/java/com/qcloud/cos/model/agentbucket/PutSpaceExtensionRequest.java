package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Put Space Extension 请求：设置 AgentBucket 指定空间的扩展属性。
 *
 * @see <a href="https://cloud.tencent.com/document/product/436/133864">Put Space Extension</a>
 * @since 5.6.283
 */
public class PutSpaceExtensionRequest extends CosServiceRequest implements Serializable {
    private String bucketName;
    private String spaceId;
    /** 空间标签，用于区分空间类型（如个人空间与团队空间），必选 */
    private String spaceTag;

    public PutSpaceExtensionRequest() {
    }

    public PutSpaceExtensionRequest(String bucketName, String spaceId, String spaceTag) {
        this.bucketName = bucketName;
        this.spaceId = spaceId;
        this.spaceTag = spaceTag;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public PutSpaceExtensionRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public PutSpaceExtensionRequest withSpaceId(String spaceId) {
        setSpaceId(spaceId);
        return this;
    }

    public String getSpaceTag() {
        return spaceTag;
    }

    public void setSpaceTag(String spaceTag) {
        this.spaceTag = spaceTag;
    }

    public PutSpaceExtensionRequest withSpaceTag(String spaceTag) {
        setSpaceTag(spaceTag);
        return this;
    }
}
