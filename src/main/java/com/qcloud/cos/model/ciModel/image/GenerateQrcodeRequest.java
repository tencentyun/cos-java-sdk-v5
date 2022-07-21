package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CosServiceRequest;

public class GenerateQrcodeRequest extends CosServiceRequest {
    /**
     * bucket信息
     */
    private String bucketName;
    /**
     * 可识别的二维码文本信息
     */
    private String qrcodeContent;
    /**
     * 生成的二维码类型，可选值：0或1。
     * 0为二维码，1为条形码，默认值为0
     */
    private String mode;

    /**
     * 指定生成的二维码或条形码的宽度，高度会进行等比压缩
     */
    private String width;

    public String getQrcodeContent() {
        return qrcodeContent;
    }

    public void setQrcodeContent(String qrcodeContent) {
        this.qrcodeContent = qrcodeContent;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GenerateQrcodeRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", qrcodeContent='").append(qrcodeContent).append('\'');
        sb.append(", mode='").append(mode).append('\'');
        sb.append(", width='").append(width).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
