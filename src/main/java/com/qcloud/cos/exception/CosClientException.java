package com.qcloud.cos.exception;

/**
 * Base exception class for any errors that occur while attempting to use an COS
 * client from COS SDK for Java to make service calls to Tencent Qcloud Cos.
 * 
 * Error responses from services will be handled as CosServiceExceptions. 
 * This class is primarily for errors that occur when unable to get a response
 * from a service, or when the client is unable to parse the response from a
 * service. For example, if a caller tries to use a client to make a service
 * call, but no network connection is present, an CosClientException will be
 * thrown to indicate that the client wasn't able to successfully make the
 * service call, and no information from the service is available.
 * 
 * Note : If the SDK is able to parse the response; but doesn't recognize the 
 * error code from the service, an CosServiceException is thrown
 * 
 * Callers should typically deal with exceptions through CosServiceException,
 * which represent error responses returned by services. CosServiceException
 * has much more information available for callers to appropriately deal with
 * different types of errors that can occur.
 * 
 * @see CosServiceException
 */

public class CosClientException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new CosClientException with the specified message, and root
     * cause.
     * 
     * @param message
     *            An error message describing why this exception was thrown.
     * @param t
     *            The underlying cause of this exception.
     */
    public CosClientException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * Creates a new CosClientException with the specified message.
     * 
     * @param message
     *            An error message describing why this exception was thrown.
     */
    public CosClientException(String message) {
        super(message);
    }

    public CosClientException(Throwable t) {
        super(t);
    }

    /**
     * Returns a hint as to whether it makes sense to retry upon this exception.
     * Default is true, but subclass may override. 
     */
    public boolean isRetryable() {
        return true;
    }
}
