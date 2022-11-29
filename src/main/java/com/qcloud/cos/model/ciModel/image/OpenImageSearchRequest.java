package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CIServiceRequest;

public class OpenImageSearchRequest extends CIServiceRequest {
    /**
     * 操作的bucket名称
     */
    private String bucketName;
    /**
     * 图库容量限制
     */
    private String maxCapacity;
    /**
     * 图库访问限制，默认10
     */
    private String maxQps;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getMaxQps() {
        return maxQps;
    }

    public void setMaxQps(String maxQps) {
        this.maxQps = maxQps;
    }
}
