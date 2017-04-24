package com.qcloud.cos.event;

import com.qcloud.cos.transfer.PersistableTransfer;

/**
 * COS specific progress listener chain.
 */
public class COSProgressListenerChain extends ProgressListenerChain implements COSProgressListener {

    /**
     * Create a listener chain that directly passes all the progress events to the specified
     * listeners.
     * 
     * @param listeners only listeners of type {@link COSProgressListener} will be notified with the
     *        COS transfer events.
     */
    public COSProgressListenerChain(ProgressListener... listeners) {
        super(listeners);
    }

    @Override
    public void onPersistableTransfer(PersistableTransfer persistableTransfer) {
        for (ProgressListener listener : getListeners()) {
            if (listener instanceof COSProgressListener) {
                COSProgressListener cosListener = (COSProgressListener) listener;
                cosListener.onPersistableTransfer(persistableTransfer);
            }
        }
    }

}
