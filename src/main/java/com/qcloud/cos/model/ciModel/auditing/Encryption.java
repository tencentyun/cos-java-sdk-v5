package com.qcloud.cos.model.ciModel.auditing;

public class Encryption {
    private String algorithm;
    private String key;
    private String iV;
    private String keyId;
    private String keyType;

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIV() {
        return iV;
    }

    public void setIV(String iV) {
        this.iV = iV;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Encryption{");
        sb.append("algorithm='").append(algorithm).append('\'');
        sb.append(", key='").append(key).append('\'');
        sb.append(", iV='").append(iV).append('\'');
        sb.append(", keyId='").append(keyId).append('\'');
        sb.append(", keyType='").append(keyType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
