package com.qcloud.cos.internal.crypto;

import static com.qcloud.cos.internal.crypto.CryptoMode.StrictAuthenticatedEncryption;

import com.qcloud.cos.auth.COSCredentialsProvider;
import com.qcloud.cos.internal.COSDirect;

/**
 * Strict Authenticated encryption (AE) cryptographic module for the COS encryption client.
 */
public class COSCryptoModuleAEStrict extends COSCryptoModuleAE {
    /**
     * @param cryptoConfig a read-only copy of the crypto configuration.
     */
    COSCryptoModuleAEStrict(QCLOUDKMS kms, COSDirect cos,
            COSCredentialsProvider credentialsProvider,
            EncryptionMaterialsProvider encryptionMaterialsProvider,
            CryptoConfiguration cryptoConfig) {
        super(kms, cos, credentialsProvider, encryptionMaterialsProvider, cryptoConfig);
        if (cryptoConfig.getCryptoMode() != StrictAuthenticatedEncryption)
            throw new IllegalArgumentException();
    }

    protected final boolean isStrict() {
        return true;
    }

    protected void securityCheck(ContentCryptoMaterial cekMaterial, COSObjectWrapper retrieved) {
        if (!ContentCryptoScheme.AES_GCM.equals(cekMaterial.getContentCryptoScheme())) {
            throw new SecurityException("COS object [bucket: " + retrieved.getBucketName()
                    + ", key: " + retrieved.getKey()
                    + "] not encrypted using authenticated encryption");
        }
    }

}
