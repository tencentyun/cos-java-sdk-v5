package com.qcloud.cos.event;

import com.qcloud.cos.transfer.TransferProgress;

public class TransferProgressUpdatingListener extends SyncProgressListener {
    private final TransferProgress transferProgress;

    public TransferProgressUpdatingListener(TransferProgress transferProgress) {
        this.transferProgress = transferProgress;
    }

    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytesTransferred();
        if (bytes == 0)
            return; // only interested in non-zero bytes
        transferProgress.updateProgress(bytes);
    }
}