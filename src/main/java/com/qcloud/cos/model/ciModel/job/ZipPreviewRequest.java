package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文件处理请求类
 */
@XStreamAlias("Request")
public class ZipPreviewRequest extends CosServiceRequest {
    private String bucketName;
    private String objectKey;
    private String uncompressKey;

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

    public String getUncompressKey() {
        return uncompressKey;
    }

    public void setUncompressKey(String uncompressKey) {
        this.uncompressKey = uncompressKey;
    }
}
