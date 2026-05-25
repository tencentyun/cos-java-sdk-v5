package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("Request")
public class GoodsMattingRequest extends CosServiceRequest {
    private String bucketName;
    @XStreamOmitField
    private String objectKey;

    /**
     * 抠图主体居中显示；值为1时居中显示，值为0不做处理，默认为0;是否必传：否
     */
    @XStreamOmitField
    private Integer centerLayout;

    /**
     * 将处理后的图片四边进行留白，形式为 padding-layout=WxH，左右两边各进行 W 像素的留白，
     * 上下两边各进行 H 像素的留白，例如：padding-layout=20x10，默认不进行留白操作，W、H最大值为1000像素。;是否必传：否
     */
    @XStreamOmitField
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

    public Integer getCenterLayout() {
        return centerLayout;
    }

    public void setCenterLayout(Integer centerLayout) {
        this.centerLayout = centerLayout;
    }

    public String getPaddingLayout() {
        return paddingLayout;
    }

    public void setPaddingLayout(String paddingLayout) {
        this.paddingLayout = paddingLayout;
    }

}
