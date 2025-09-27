
package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CosServiceRequest;

import java.io.Serializable;

/**
 * 图片极智压缩请求类
 */
public class ImageSlimRequest extends CosServiceRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 存储桶名称
     */
    private String bucketName;
    
    /**
     * 对象键
     */
    private String objectKey;
    

    
    public ImageSlimRequest() {
    }
    
    public ImageSlimRequest(String bucketName, String objectKey) {
        this.bucketName = bucketName;
        this.objectKey = objectKey;
    }
    
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

    @Override
    public String toString() {
        return "ImageSlimRequest{" +
                "bucketName='" + bucketName + '\'' +
                ", objectKey='" + objectKey + '\'' +
                '}';
    }
}