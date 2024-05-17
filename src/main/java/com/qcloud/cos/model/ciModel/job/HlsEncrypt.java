package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class HlsEncrypt {
    @XStreamAlias("IsHlsEncrypt")
    private String isHlsEncrypt;

    @XStreamAlias("UriKey")
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
}
