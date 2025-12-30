package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class HlsEncrypt {

    @XStreamAlias("CiphertextBlob")
    private String ciphertextBlob;

    @XStreamAlias("KeyID")
    private String keyID;

    @XStreamAlias("KmsRegion")
    private String kmsRegion;

    @XStreamAlias("PlainText")
    private String plainText;

    @XStreamAlias("UriKey")
    private String uriKey;

    @XStreamAlias("IsHlsEncrypt")
    private String isHlsEncrypt;

    public String getCiphertextBlob() {
        return ciphertextBlob;
    }

    public void setCiphertextBlob(String ciphertextBlob) {
        this.ciphertextBlob = ciphertextBlob;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getKmsRegion() {
        return kmsRegion;
    }

    public void setKmsRegion(String kmsRegion) {
        this.kmsRegion = kmsRegion;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

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
