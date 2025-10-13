
package com.qcloud.cos.model.ciModel.persistence;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 宠物识别请求实体：https://cloud.tencent.com/document/product/460/95753
 */
public class DetectPetRequest extends CosServiceRequest {
    /**
     * 操作的bucket名称
     */
    @XStreamOmitField
    private String bucketName;

    /**
     * ObjectKey 对象在存储桶中的位置及名称
     * 例如根目录下pic文件夹中的1.jpg文件   pic/1.jpg
     */
    @XStreamOmitField
    private String objectKey;

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
        final StringBuffer sb = new StringBuffer("DetectPetRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", objectKey='").append(objectKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}