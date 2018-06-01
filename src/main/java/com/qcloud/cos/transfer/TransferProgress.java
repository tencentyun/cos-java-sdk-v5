package com.qcloud.cos.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Describes the progress of a transfer.
 */
public final class TransferProgress {

    private static final Logger log = LoggerFactory.getLogger(TransferProgress.class);
    private volatile long bytesTransferred = 0;
    private volatile long totalBytesToTransfer = -1;

    /**
     * @deprecated Replaced by {@link #getBytesTransferred()}
     */
    public long getBytesTransfered() {
        return getBytesTransferred();
    }

    /**
     * Returns the number of bytes completed in the associated transfer.
     *
     * @return The number of bytes completed in the associated transfer.
     */
    public long getBytesTransferred() {
        return bytesTransferred;
    }

    /**
     * Returns the total size in bytes of the associated transfer, or -1 if the total size isn't
     * known.
     *
     * @return The total size in bytes of the associated transfer. Returns or -1 if the total size
     *         of the associated transfer isn't known yet.
     */
    public long getTotalBytesToTransfer() {
        return totalBytesToTransfer;
    }

    /**
     * @deprecated Replaced by {@link #getPercentTransferred()}
     */
    @Deprecated
    public synchronized double getPercentTransfered() {
        return getPercentTransferred();
    }

    /**
     * Returns a percentage of the number of bytes transferred out of the total number of bytes to
     * transfer.
     *
     * @return A percentage of the number of bytes transferred out of the total number of bytes to
     *         transfer; or -1.0 if the total length is not known.
     */
    public synchronized double getPercentTransferred() {
        if (getBytesTransferred() < 0)
            return 0;
        if (totalBytesToTransfer < 0) {
            return -1.0;
        } else if (totalBytesToTransfer == 0) {
            return 100.0;
        } else {
            return ((double) bytesTransferred / (double) totalBytesToTransfer) * (double) 100;
        }
    }

    public synchronized void updateProgress(long bytes) {
        this.bytesTransferred += bytes;
        if (totalBytesToTransfer > -1 && this.bytesTransferred > this.totalBytesToTransfer) {
            this.bytesTransferred = this.totalBytesToTransfer;
            if (log.isDebugEnabled()) {
                log.debug(
                        "Number of bytes transfered is more than the actual total bytes to transfer. Total number of bytes to Transfer : "
                                + totalBytesToTransfer + ". Bytes Transferred : "
                                + (bytesTransferred + bytes));
            }
        }
    }

    public void setTotalBytesToTransfer(long totalBytesToTransfer) {
        this.totalBytesToTransfer = totalBytesToTransfer;
    }
}
