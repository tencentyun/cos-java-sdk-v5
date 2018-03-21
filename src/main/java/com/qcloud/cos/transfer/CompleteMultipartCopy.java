package com.qcloud.cos.transfer;

import static com.qcloud.cos.event.SDKProgressPublisher.publishProgress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.qcloud.cos.COS;
import com.qcloud.cos.event.ProgressEventType;
import com.qcloud.cos.event.ProgressListenerChain;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.CompleteMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadResult;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyResult;
import com.qcloud.cos.model.PartETag;


/**
 * Initiates a complete multi-part upload request for a TransferManager multi-part parallel copy.
 */
public class CompleteMultipartCopy implements Callable<CopyResult> {

    /** The upload id associated with the multi-part copy. */
    private final String uploadId;

    /**
     * The reference to underlying COS client to be used for initiating requests to COS.
     */
    private final COS cos;

    /** The reference to the request initiated by the user. */
    private final CopyObjectRequest origReq;

    /** The futures of threads that copies individual parts. */
    private final List<Future<PartETag>> futures;

    /** The monitor to which the copy progress has to be communicated. */
    private final CopyMonitor monitor;

    /** The listener where progress of the copy needs to be published. */
    private final ProgressListenerChain listener;

    public CompleteMultipartCopy(String uploadId, COS cos, CopyObjectRequest copyObjectRequest,
            List<Future<PartETag>> futures, ProgressListenerChain progressListenerChain,
            CopyMonitor monitor) {
        this.uploadId = uploadId;
        this.cos = cos;
        this.origReq = copyObjectRequest;
        this.futures = futures;
        this.listener = progressListenerChain;
        this.monitor = monitor;
    }

    @Override
    public CopyResult call() throws Exception {
        CompleteMultipartUploadResult res;

        try {
            CompleteMultipartUploadRequest req =
                    new CompleteMultipartUploadRequest(origReq.getDestinationBucketName(),
                            origReq.getDestinationKey(), uploadId, collectPartETags())
                                    .withGeneralProgressListener(
                                            origReq.getGeneralProgressListener());
            res = cos.completeMultipartUpload(req);
        } catch (Exception e) {
            publishProgress(listener, ProgressEventType.TRANSFER_FAILED_EVENT);
            throw e;
        }

        CopyResult copyResult = new CopyResult();
        copyResult.setSourceBucketName(origReq.getSourceBucketName());
        copyResult.setSourceKey(origReq.getSourceKey());
        copyResult.setDestinationBucketName(res.getBucketName());
        copyResult.setDestinationKey(res.getKey());
        copyResult.setETag(res.getETag());
        copyResult.setVersionId(res.getVersionId());
        copyResult.setRequestId(res.getRequestId());
        copyResult.setDateStr(res.getDateStr());

        monitor.copyComplete();

        return copyResult;
    }

    /**
     * Collects the Part ETags for initiating the complete multi-part copy request. This is blocking
     * as it waits until all the upload part threads complete.
     */
    private List<PartETag> collectPartETags() {

        final List<PartETag> partETags = new ArrayList<PartETag>();
        for (Future<PartETag> future : futures) {
            try {
                partETags.add(future.get());
            } catch (Exception e) {
                throw new CosClientException("Unable to copy part: " + e.getCause().getMessage(),
                        e.getCause());
            }
        }
        return partETags;
    }
}
