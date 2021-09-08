/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 
 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.transfer;

import com.qcloud.cos.COS;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import com.qcloud.cos.transfer.Transfer.TransferState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

final class ImageAuditingCallable implements Callable<ImageAuditingResponse> {
    private static final Logger log = LoggerFactory.getLogger(ImageAuditingCallable.class);

    private final COS cos;
    private final CountDownLatch latch;
    private final ImageAuditingRequest req;
    private final ImageAuditingImpl imageAuditing;

    ImageAuditingCallable(COS cos, CountDownLatch latch, ImageAuditingRequest request, ImageAuditingImpl imageAuditing) {
        if (cos == null || latch == null || imageAuditing == null )
            throw new IllegalArgumentException();
        this.cos = cos;
        this.latch = latch;
        this.req = request;
        this.imageAuditing = imageAuditing;
    }

    /**
     * This method must return a non-null object, or else the existing implementation in
     * {@link AbstractTransfer#waitForCompletion()} would block forever.
     * 
     * @return the ImageAuditingResponse
     */
    @Override
    public ImageAuditingResponse call() throws Exception {
        try {
            latch.await();
            imageAuditing.setState(TransferState.InProgress);
            ImageAuditingResponse imageAuditingResponse = cos.imageAuditing(req);

            if (imageAuditingResponse == null) {
                imageAuditing.setState(TransferState.Canceled);
                imageAuditing.setMonitor(new ImageAuditingMonitor(imageAuditing, null));
            } else {
                imageAuditing.setState(TransferState.Completed);
            }
            return imageAuditingResponse;
        } catch (Throwable t) {
            // Downloads aren't allowed to move from canceled to failed
            if (imageAuditing.getState() != TransferState.Canceled) {
                imageAuditing.setState(TransferState.Failed);
            }
            if (t instanceof Exception)
                throw (Exception) t;
            else
                throw (Error) t;
        }
    }
}
