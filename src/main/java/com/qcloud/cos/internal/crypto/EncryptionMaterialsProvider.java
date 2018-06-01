package com.qcloud.cos.internal.crypto;

/**
 * Interface for providing encryption materials.
 * Implementations are free to use any strategy for providing encryption
 * materials, such as simply providing static material that doesn't change,
 * or more complicated implementations, such as integrating with existing
 * key management systems.
 */
public interface EncryptionMaterialsProvider extends
        EncryptionMaterialsAccessor, EncryptionMaterialsFactory {
    /**
     * Forces this encryption materials provider to refresh its encryption
     * material.  For many implementations of encryption materials provider,
     * this may simply be a no-op, such as any encryption materials provider
     * implementation that vends static/non-changing encryption material.
     * For other implementations that vend different encryption material
     * throughout their lifetime, this method should force the encryption
     * materials provider to refresh its encryption material.
     */
    public void refresh();
}
