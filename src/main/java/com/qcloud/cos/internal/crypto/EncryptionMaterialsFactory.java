package com.qcloud.cos.internal.crypto;

/**
 * Factory for providing the latest encryption materials.
 */
public interface EncryptionMaterialsFactory {
    /**
     * Returns EncryptionMaterials which the caller can use for encryption. Each implementation of
     * EncryptionMaterialsProvider can choose its own strategy for loading encryption material. For
     * example, an implementation might load encryption material from an existing key management
     * system, or load new encryption material when keys are rotated.
     *
     * @return EncryptionMaterials which the caller can use to encrypt or decrypt data.
     */
    public EncryptionMaterials getEncryptionMaterials();
}
