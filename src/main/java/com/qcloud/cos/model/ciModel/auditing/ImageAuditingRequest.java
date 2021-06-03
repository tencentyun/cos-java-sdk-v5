package com.qcloud.cos.model.ciModel.auditing;


import com.qcloud.cos.internal.CosServiceRequest;

/**
 * 图片审核请求实体 参数详情参考：https://cloud.tencent.com/document/product/460/37318
 */
public class ImageAuditingRequest extends CosServiceRequest {
    /**
     * 审核类型，拥有 porn（涉黄识别）、terrorist（涉暴恐识别）、politics（涉政识别）、ads（广告识别）四种。用户可选择多种识别类型，
     * 例如 detectType=porn,ads 表示对图片进行涉黄及广告审核
     */
    private String detectType;

    /**
     * 操作的bucket名称
     */
    private String bucketName;

    /**
     * ObjectKey 对象在存储桶中的位置及名称
     * 例如根目录下pic文件夹中的1.jpg文件   pic/1.jpg
     */
    private String objectKey;

    public String getDetectType() {
        return detectType;
    }

    public void setDetectType(String detectType) {
        this.detectType = detectType;
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
        final StringBuffer sb = new StringBuffer("ImageAuditingRequest{");
        sb.append("detectType='").append(detectType).append('\'');
        sb.append(", bucketName='").append(bucketName).append('\'');
        sb.append(", objectKey='").append(objectKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
