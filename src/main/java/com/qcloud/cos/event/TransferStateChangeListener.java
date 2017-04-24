package com.qcloud.cos.event;

import com.qcloud.cos.transfer.Transfer;
import com.qcloud.cos.transfer.Transfer.TransferState;

/**
 * Listener for transfer state changes.  Not intended to be consumed externally.
 */
public interface TransferStateChangeListener {
    public void transferStateChanged(Transfer transfer, TransferState state);
}