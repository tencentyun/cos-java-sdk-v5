package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CosServiceRequest;


public class ImageInspectRequest extends CosServiceRequest {
    /**
     * 对象在cos中的相对位置，例如 demo/picture.jpg
     */
    private String objectKey;

    /**
     * 操作的bucket名称
     */
    private String bucketName;

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

}
