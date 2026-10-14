package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Get Space Extension 请求：查询 AgentBucket 指定空间的扩展属性。
 *
 * @see <a href="https://cloud.tencent.com/document/product/436/133863">Get Space Extension</a>
 * @since 5.6.283
 */
public class GetSpaceExtensionRequest extends CosServiceRequest implements Serializable {
    private String bucketName;
    private String spaceId;

    public GetSpaceExtensionRequest() {
    }

    public GetSpaceExtensionRequest(String bucketName, String spaceId) {
        this.bucketName = bucketName;
        this.spaceId = spaceId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public GetSpaceExtensionRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public GetSpaceExtensionRequest withSpaceId(String spaceId) {
        setSpaceId(spaceId);
        return this;
    }
}
