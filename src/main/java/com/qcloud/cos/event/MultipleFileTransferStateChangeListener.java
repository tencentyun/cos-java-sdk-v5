package com.qcloud.cos.event;

import java.util.concurrent.CountDownLatch;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.transfer.MultipleFileTransfer;
import com.qcloud.cos.transfer.Transfer;
import com.qcloud.cos.transfer.Transfer.TransferState;

public final class MultipleFileTransferStateChangeListener implements TransferStateChangeListener {
    private final CountDownLatch latch;
    private final MultipleFileTransfer<?> multipleFileTransfer;

    public MultipleFileTransferStateChangeListener(CountDownLatch latch,
            MultipleFileTransfer<?> multipleFileTransfer) {
        this.latch = latch;
        this.multipleFileTransfer = multipleFileTransfer;
    }

    @Override
    public void transferStateChanged(Transfer upload, TransferState state) {
        // There's a race here: we can't start monitoring the state of
        // individual transfers until we have added all the transfers to the
        // list, or we may incorrectly report completion.
        try {
            latch.await();
        } catch ( InterruptedException e ) {
            throw new CosClientException("Couldn't wait for all downloads to be queued");
        }

        synchronized (multipleFileTransfer) {
            if ( multipleFileTransfer.getState() == state || multipleFileTransfer.isDone() )
                return;

            /*
             * If we're not already in a terminal state, allow a transition
             * to a non-waiting state. Mark completed if this download is
             * completed and the monitor says all of the rest are as well.
             */
            if ( state == TransferState.InProgress ) {
                multipleFileTransfer.setState(state);
            } else if ( multipleFileTransfer.getMonitor().isDone() ) {
                multipleFileTransfer.collateFinalState();
            } else {
                multipleFileTransfer.setState(TransferState.InProgress);
            }
        }
    }
}