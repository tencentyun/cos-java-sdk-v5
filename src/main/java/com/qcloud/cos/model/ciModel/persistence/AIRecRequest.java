package com.qcloud.cos.model.ciModel.persistence;

import com.qcloud.cos.internal.CosServiceRequest;

/**
 * 车辆识别请求实体 参数详情参考：https://cloud.tencent.com/document/product/460/63225
 */
public class AIRecRequest extends CosServiceRequest {
    /**
     * 操作的bucket名称
     */
    private String bucketName;

    /**
     * ObjectKey 对象在存储桶中的位置及名称
     * 例如根目录下pic文件夹中的1.jpg文件   pic/1.jpg
     */
    private String objectKey;

    /**
     * 待识别图⽚url,需要进⾏urlencode
     */
    private String detectUrl;

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

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DetectCarRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", objectKey='").append(objectKey).append('\'');
        sb.append(", detectUrl='").append(detectUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
