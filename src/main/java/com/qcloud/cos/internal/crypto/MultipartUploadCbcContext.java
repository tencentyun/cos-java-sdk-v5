package com.qcloud.cos.internal.crypto;

public class MultipartUploadCbcContext extends MultipartUploadCryptoContext {
    private byte[] nextIV;

    MultipartUploadCbcContext(String bucketName, String key, ContentCryptoMaterial cekMaterial) {
        super(bucketName, key, cekMaterial);
    }

    public void setNextInitializationVector(byte[] nextIV) {
        this.nextIV = nextIV;
    }

    public byte[] getNextInitializationVector() {
        return nextIV;
    }
}
