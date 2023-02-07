package com.qcloud.cos.model.HeadObjects;

import com.qcloud.cos.COS;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class HeadConsumer extends Thread {
    private final LinkedBlockingQueue<GetObjectMetadataRequest> requests;
    private final COS cos;
    private final LinkedBlockingQueue<HeadObjectResult> headObjects;
    private final CountDownLatch countDownLatch;
    private static final Logger log = LoggerFactory.getLogger(HeadConsumer.class);
    private boolean isInterrupted;

    public HeadConsumer(LinkedBlockingQueue<GetObjectMetadataRequest> requests, LinkedBlockingQueue<HeadObjectResult> headObjects, CountDownLatch countDownLatch, COS cos) {
        this.requests = requests;
        this.headObjects = headObjects;
        this.countDownLatch = countDownLatch;
        this.cos = cos;
        this.isInterrupted = false;
    }

    public void setInterruptFlag(boolean isInterrupted) {
        this.isInterrupted = isInterrupted;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted) {
                GetObjectMetadataRequest request = requests.poll();
                if (request == null) {
                    break;
                }
                try {
                    ObjectMetadata objectMetadata = cos.getObjectMetadata(request);
                    HeadObjectResult headedObject = new HeadObjectResult(request.getKey(), objectMetadata, true, true);
                    headObjects.add(headedObject);
                } catch (CosServiceException cse) {
                    if (cse.getStatusCode() == 404) {
                        HeadObjectResult headedObject = new HeadObjectResult(request.getKey(), new ObjectMetadata(), false, true);
                        headObjects.add(headedObject);
                    } else {
                        HeadObjectResult headedObject = new HeadObjectResult(request.getKey(), new ObjectMetadata(), false, false);
                        headObjects.add(headedObject);
                        String errorMsg = String.format("failed to execute head request: bucket %s, key %s, versionID %s, due to service exception,",
                                                            request.getBucketName(), request.getKey(), request.getVersionId());
                        log.error(errorMsg, cse);
                        throw cse;
                    }
                } catch (Exception e) {
                    HeadObjectResult headedObject = new HeadObjectResult(request.getKey(), new ObjectMetadata(), false, false);
                    headObjects.add(headedObject);
                    String expName = e.getClass().getName();
                    String errorMsg = String.format("HeadConsumer execute occur an unknown exception:%s, head request:  bucket %s, key %s, versionID %s",
                                                    expName, request.getBucketName(), request.getKey(), request.getVersionId());
                    log.error(errorMsg, e);
                    throw new CosClientException(errorMsg, e);
                }
            }
        } finally {
            countDownLatch.countDown();
        }
    }
}
