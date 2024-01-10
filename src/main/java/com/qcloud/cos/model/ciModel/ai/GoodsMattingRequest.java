package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("Request")
public class GoodsMattingRequest extends CosServiceRequest {
    private String bucketName;
    @XStreamOmitField
    private String objectKey;

    private String centerLayout;

    private String paddingLayout;

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    @XStreamOmitField
    private String detectUrl;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDetectUrl() {
        return detectUrl;
    }

    public void setDetectUrl(String detectUrl) {
        this.detectUrl = detectUrl;
    }

}
