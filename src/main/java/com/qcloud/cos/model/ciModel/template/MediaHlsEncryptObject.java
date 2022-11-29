package com.qcloud.cos.model.ciModel.template;


public class MediaHlsEncryptObject {

    /**
     * 是否开启 HLS 加密
     * 1. true/false
     * 2. Segment.Format 为 HLS 时支持加密
     */
    private String isHlsEncrypt;
    /**
     * 当 IsHlsEncrypt 为 true 时，该参数才有意义
     */
    private String uriKey;

    public String getIsHlsEncrypt() {
        return isHlsEncrypt;
    }

    public void setIsHlsEncrypt(String isHlsEncrypt) {
        this.isHlsEncrypt = isHlsEncrypt;
    }

    public String getUriKey() {
        return uriKey;
    }

    public void setUriKey(String uriKey) {
        this.uriKey = uriKey;
    }

    @Override
    public String toString() {
        return "MediaHlsEncryptObject{" +
                "isHlsEncrypt='" + isHlsEncrypt + '\'' +
                ", uriKey='" + uriKey + '\'' +
                '}';
    }
}
