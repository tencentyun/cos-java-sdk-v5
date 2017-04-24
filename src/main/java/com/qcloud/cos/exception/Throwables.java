package com.qcloud.cos.exception;

public class Throwables {

    /**
     * Used to help perform common throw-up with minimal wrapping.
     */
    public static RuntimeException failure(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException)t;
        if (t instanceof Error)
            throw (Error)t;
        return t instanceof InterruptedException
             ? new AbortedException(t)
             : new CosClientException(t);
    }
    
}
