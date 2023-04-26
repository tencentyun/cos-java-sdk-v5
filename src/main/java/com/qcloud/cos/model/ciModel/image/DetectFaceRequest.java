package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CosServiceRequest;

public class DetectFaceRequest extends CosServiceRequest {
    /**
     * bucket信息
     */
    private String bucketName;

    /**
     * 对象文件名，例如：folder/document.jpg。
     */
    private String objectKey;
    /**
     * 最多处理的人脸数目。默认值为1（仅检测图片中面积最大的那张人脸），最大值为120。
     * 此参数用于控制处理待检测图片中的人脸个数，值越小，处理速度越快。
     */
    private String maxFaceNum;

    /**
     * 外部源url信息
     */
    private String detectUrl;


    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getMaxFaceNum() {
        return maxFaceNum;
    }

    public void setMaxFaceNum(String maxFaceNum) {
        this.maxFaceNum = maxFaceNum;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DetectFaceRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", maxFaceNum='").append(maxFaceNum).append('\'');
        sb.append(", detectUrl='").append(detectUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
