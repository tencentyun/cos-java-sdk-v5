package com.qcloud.cos.exception;

import com.qcloud.cos.transfer.PauseStatus;

public class PauseException extends CosClientException {

    private static final long serialVersionUID = 1L;

    /**
     * The reason why the pause operation failed.
     */
    private final PauseStatus status;

    public PauseException(PauseStatus status) {
        super("Failed to pause operation; status=" + status);
        if (status == null || status == PauseStatus.SUCCESS)
            throw new IllegalArgumentException();
        this.status = status;
    }

    /**
     * Returns more information on why the pause failed.
     */
    public PauseStatus getPauseStatus() {
        return status;
    }

    /**
     * {@inheritDoc} An paused exception is not intended to be retried.
     */
    @Override
    public boolean isRetryable() {
        return false;
    }
}
