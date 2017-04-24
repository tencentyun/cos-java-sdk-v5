package com.qcloud.cos.model;

/**
 * Implemented by classes that support the option of using SSE Customer key.
 */
public interface SSECustomerKeyProvider {
    /**
     * Returns the optional customer-provided server-side encryption key to use
     * to encrypt the uploaded object.
     *
     * @return The optional customer-provided server-side encryption key to use
     *         to encrypt the uploaded object.
     */
    public SSECustomerKey getSSECustomerKey();
}