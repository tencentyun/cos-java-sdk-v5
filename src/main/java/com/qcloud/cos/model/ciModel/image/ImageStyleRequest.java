package com.qcloud.cos.model.ciModel.image;


import com.qcloud.cos.internal.CIPicServiceRequest;

public class ImageStyleRequest extends CIPicServiceRequest {
    /**
     * bucket信息
     */
    private String bucketName;
     /**
     * 样式名称
     */
    private String styleName;
     /**
     * 样式详情
     */
    private String styleBody;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleBody() {
        return styleBody;
    }

    public void setStyleBody(String styleBody) {
        this.styleBody = styleBody;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageStyleRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", styleName='").append(styleName).append('\'');
        sb.append(", styleBody='").append(styleBody).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
