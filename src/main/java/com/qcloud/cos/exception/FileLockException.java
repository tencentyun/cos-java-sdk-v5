package com.qcloud.cos.exception;

public class FileLockException extends CosClientException {

    private static final long serialVersionUID = 1L;

    public FileLockException(Throwable t) {
        super(t);
    }

    public FileLockException(String msg) {
        super(msg);
    }

    @Override
    public boolean isRetryable() {
        return false;
    }
}
