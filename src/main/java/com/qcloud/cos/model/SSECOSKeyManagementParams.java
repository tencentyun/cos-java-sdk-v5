package com.qcloud.cos.model;

import java.io.Serializable;

public class SSECOSKeyManagementParams implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * The COS Key Management Key id to be used for Server Side Encryption of
     * the Qcloud COS object.
     */
    private final String COSKmsKeyId;

    /**
     * Constructs a new instance of SSECOSKeyManagementParams. The default COS
     * KMS Key id is used for encryption.
     */
    public SSECOSKeyManagementParams() {
        this.COSKmsKeyId = null;
    }

    /**
     * Constructs a new instance of SSECOSKeyManagementParams with the user
     * specified COS Key Management System Key Id.
     */
    public SSECOSKeyManagementParams(String COSKmsKeyId) {
        if (COSKmsKeyId == null || COSKmsKeyId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "COS Key Management System Key id cannot be null");
        }
        this.COSKmsKeyId = COSKmsKeyId;
    }

    /**
     * Returns the COS Key Management System Key Id used for encryption. Returns
     * null if default Key Id is used.
     */
    public String getCOSKmsKeyId() {
        return COSKmsKeyId;
    }

    /**
     * Returns the scheme used for encrypting the Qcloud COS object. Currently
     * the encryption is always "COS:kms".
     */
    public String getEncryption() {
        return SSEAlgorithm.KMS.getAlgorithm();
    }
}
