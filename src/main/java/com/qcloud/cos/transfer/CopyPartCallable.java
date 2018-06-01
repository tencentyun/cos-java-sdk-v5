package com.qcloud.cos.transfer;

import java.util.concurrent.Callable;

import com.qcloud.cos.COS;
import com.qcloud.cos.model.CopyPartRequest;
import com.qcloud.cos.model.CopyPartResult;
import com.qcloud.cos.model.PartETag;

/**
 * An implementation of the Callable interface responsible for carrying out the
 * Copy part requests.
 *
 */
public class CopyPartCallable implements Callable<PartETag> {

    /** Reference to the COS client object used for initiating copy part request.*/
    private final COS cos;
    /** Copy part request to be initiated.*/
    private final CopyPartRequest request;

    public CopyPartCallable(COS cos, CopyPartRequest request) {
        this.cos = cos;
        this.request = request;
    }

    public PartETag call() throws Exception {
        CopyPartResult copyPartResult = cos.copyPart(request);
        return copyPartResult == null ? null : copyPartResult.getPartETag();
    }
}