package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * 文件哈希值同步计算请求
 * 详情见 https://cloud.tencent.com/document/product/460/79735
 */
public class FileHashCodeSyncRequest extends CosServiceRequest {
    
    /**
     * 存储桶名称，格式为：BucketName-APPID
     */
    private String bucketName;
    
    /**
     * 对象键（Key）是对象在存储桶中的唯一标识
     */
    private String objectKey;
    
    /**
     * 哈希算法类型，有效值：md5、sha1、sha256
     */
    private String type;
    
    /**
     * 是否将计算得到的哈希值，自动添加至文件的自定义header
     * 有效值：true、false，默认为false
     */
    private String addToHeader;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddToHeader() {
        return addToHeader;
    }

    public void setAddToHeader(String addToHeader) {
        this.addToHeader = addToHeader;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileHashCodeSyncRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", objectKey='").append(objectKey).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", addToHeader='").append(addToHeader).append('\'');
        sb.append('}');
        return sb.toString();
    }
}