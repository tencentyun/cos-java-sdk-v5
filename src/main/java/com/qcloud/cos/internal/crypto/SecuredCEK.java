package com.qcloud.cos.internal.crypto;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Internal class used to carry both the secured CEK and the key wrapping
 * algorithm, if any. Byte array cloning is intentionally skipped for
 * performance reasons.
 */
class SecuredCEK {
    /**
     * The encrypted CEK either via key wrapping or simple encryption.
     */
    private final byte[] encrypted;
    /**
     * The key wrapping algorithm used, or null if the CEK is not secured via
     * key wrapping.
     */
    private final String keyWrapAlgorithm;

    /** Unmodifiable material description. */
    private final Map<String,String> matdesc;

    SecuredCEK(byte[] encryptedKey, String keyWrapAlgorithm, Map<String,String> matdesc) {
        this.encrypted = encryptedKey;
        this.keyWrapAlgorithm = keyWrapAlgorithm;
        this.matdesc = Collections.unmodifiableMap(new TreeMap<String, String>(matdesc));
    }

    byte[] getEncrypted() {
        return encrypted;
    }

    String getKeyWrapAlgorithm() {
        return keyWrapAlgorithm;
    }

    /**
     * Returns an unmodifable material description of this secured CEK.
     */
    Map<String, String> getMaterialDescription() {
        return matdesc;
    }
}