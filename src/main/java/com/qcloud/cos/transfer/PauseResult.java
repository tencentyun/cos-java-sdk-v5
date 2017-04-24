package com.qcloud.cos.transfer;

/**
 * The result of a pause operation.
 *
 * @param <T> information that can be used to resume the paused operation;
 * can be null if the pause failed.
 */
public final class PauseResult<T> {
    private final PauseStatus pauseStatus;
    private final T infoToResume;   // non-null only if pauseStatus == SUCCESS

    public PauseResult(PauseStatus pauseStatus, T infoToResume) {
        if (pauseStatus == null)
            throw new IllegalArgumentException();
        this.pauseStatus = pauseStatus;
        this.infoToResume = infoToResume;
    }

    public PauseResult(PauseStatus pauseStatus) {
        this(pauseStatus, null);
    }

    /**
     * Returns information about whether the pause was successful or not; and
     * if not why.
     */
    public PauseStatus getPauseStatus() {
        return pauseStatus;
    }

    /**
     * Returns the information that can be used to resume a successfully paused
     * operation.
     */
    public T getInfoToResume() {
        return infoToResume;
    }
}