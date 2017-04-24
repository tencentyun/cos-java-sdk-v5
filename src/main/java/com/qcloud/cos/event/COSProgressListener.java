package com.qcloud.cos.event;

import com.qcloud.cos.transfer.PersistableTransfer;

public interface COSProgressListener extends ProgressListener {
    /**
     * Called when the information to resume an upload/download operation is
     * available, The execution of the callback of this listener is managed by
     * {@link COSProgressListener}. Implementation of this interface should
     * never block.

     * Note any exception thrown by the listener will get ignored. Should there
     * be need to capture any such exception, you may consider wrapping the
     * listener with
     * {@link ExceptionReporter#wrap(ProgressListener)}.
     * 
     * @param persistableTransfer
     *            A non null opaque token used to resume an upload or download.
     * 
     * @see COSProgressListener
     * @see ExceptionReporter
     */
    public void onPersistableTransfer(final PersistableTransfer persistableTransfer);
}
