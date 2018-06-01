package com.qcloud.cos.internal.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

class AesGcm extends ContentCryptoScheme {
    @Override String getKeyGeneratorAlgorithm() { return "AES"; }
    @Override String getCipherAlgorithm() { return "AES/GCM/NoPadding"; }
    @Override int getKeyLengthInBits() { return 256; }
    @Override int getBlockSizeInBytes() { return 16; }
    @Override int getIVLengthInBytes() { return 12; }
    @Override long getMaxPlaintextSize() { return MAX_GCM_BYTES; }
    /**
     * Used to explicitly record the tag length in COS for interoperability
     * with other services.
     */
    @Override int getTagLengthInBits() { return 128; }
    /**
     * Currently only Bouncy Castle can support the AES/GCM cipher in
     * Java 6 without having to use the AEAD API in Java 7+.
     */
    @Override String getSpecificCipherProvider() { return "BC"; }

    @Override
    CipherLite createAuxillaryCipher(SecretKey cek, byte[] ivOrig,
            int cipherMode, Provider securityProvider, long startingBytePos)
            throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException,
            InvalidAlgorithmParameterException {
        byte[] iv = AES_CTR.adjustIV(ivOrig, startingBytePos);
        return AES_CTR.createCipherLite(cek, iv, cipherMode, securityProvider);
    }

    @Override
    protected CipherLite newCipherLite(Cipher cipher,  SecretKey cek, int cipherMode) {
        return new GCMCipherLite(cipher, cek, cipherMode);
    }
}
