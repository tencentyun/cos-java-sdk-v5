package com.qcloud.cos.model.ciModel.job;


/**
 * 数字水印配置	 参数详情见:https://cloud.tencent.com/document/product/460/66000
 */
public class MediaDigitalWatermark {
    /**
     * 数字水印嵌入的字符串信息
     * 长度不超过64个字符，仅支持中文、英文、数字、_、-和*
     */
    private String message;
    /**
     * 水印类型	目前只支持Text
     */
    private String type;
    /**
     * 水印版本
     */
    private String version;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MediaDigitalWatermark{");
        sb.append("message='").append(message).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
