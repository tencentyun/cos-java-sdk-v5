package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * Delete Object Recursive 请求：递归删除整个目录树（含所有子文件和子目录）。
 * <p>
 * <b>删除后无法恢复，请谨慎操作。</b>为防止误删整桶，SDK 拒绝空 key 与 "/"。
 * </p>
 * <p>
 * 删除目录时 key 以 "/" 结尾（如 "my-space/dir/"）；删除单个文件时直接传文件 key。
 * </p>
 *
 * @see <a href="https://cloud.tencent.com/document/product/436/133861">Delete Object Recursive</a>
 * @since 5.6.283
 */
public class DeleteObjectRecursiveRequest extends CosServiceRequest implements Serializable {
    private String bucketName;
    private String key;

    public DeleteObjectRecursiveRequest() {
    }

    public DeleteObjectRecursiveRequest(String bucketName, String key) {
        this.bucketName = bucketName;
        this.key = key;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public DeleteObjectRecursiveRequest withBucketName(String bucketName) {
        setBucketName(bucketName);
        return this;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DeleteObjectRecursiveRequest withKey(String key) {
        setKey(key);
        return this;
    }
}
