package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Put Space Quota 请求：设置 AgentBucket 指定空间的存储配额。
 * <p>
 * SpaceId 对应 ObjectKey 的一级目录，需预先通过 PUT Object 上传一个空的一级目录对象（如 "my-space/"）来创建。
 * </p>
 *
 * @see <a href="https://cloud.tencent.com/document/product/436/133860">Put Space Quota</a>
 * @since 5.6.283
 */
public class PutSpaceQuotaRequest extends CosServiceRequest implements Serializable {
    private String bucketName;
    private String spaceId;
    /** 配额大小，单位字节，必须为非负整数 */
    private Long capacity;

    public PutSpaceQuotaRequest() {
    }

    public PutSpaceQuotaRequest(String bucketName, String spaceId, long capacity) {
        this.bucketName = bucketName;
        this.spaceId = spaceId;
        this.capacity = capacity;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public PutSpaceQuotaRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public PutSpaceQuotaRequest withSpaceId(String spaceId) {
        setSpaceId(spaceId);
        return this;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public PutSpaceQuotaRequest withCapacity(Long capacity) {
        setCapacity(capacity);
        return this;
    }
}
