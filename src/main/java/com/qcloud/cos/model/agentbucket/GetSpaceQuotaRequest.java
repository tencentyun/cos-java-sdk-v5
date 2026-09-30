package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Get Space Quota 请求：查询 AgentBucket 指定空间的存储配额。
 *
 * @see <a href="https://cloud.tencent.com/document/product/436/133859">Get Space Quota</a>
 * @since 5.6.283
 */
public class GetSpaceQuotaRequest extends CosServiceRequest implements Serializable {
    private String bucketName;
    private String spaceId;

    public GetSpaceQuotaRequest() {
    }

    public GetSpaceQuotaRequest(String bucketName, String spaceId) {
        this.bucketName = bucketName;
        this.spaceId = spaceId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public GetSpaceQuotaRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public GetSpaceQuotaRequest withSpaceId(String spaceId) {
        setSpaceId(spaceId);
        return this;
    }
}
