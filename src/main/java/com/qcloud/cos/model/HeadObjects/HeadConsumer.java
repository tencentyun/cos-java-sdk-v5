package com.qcloud.cos.model.HeadObjects;

import com.qcloud.cos.COS;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GetObjectMetadataRequest;
import com.qcloud.cos.model.ObjectMetadata;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class HeadConsumer implements Runnable{
    private final LinkedBlockingQueue<GetObjectMetadataRequest> requests;
    private final COS cos;
    private final LinkedBlockingQueue<HeadObjectsResult.HeadedObject> headObjects;
    private final CountDownLatch countDownLatch;

    public HeadConsumer(LinkedBlockingQueue<GetObjectMetadataRequest> requests, LinkedBlockingQueue<HeadObjectsResult.HeadedObject> headObjects, CountDownLatch countDownLatch, COS cos) {
        this.requests = requests;
        this.headObjects = headObjects;
        this.countDownLatch = countDownLatch;
        this.cos = cos;
    }

    @Override
    public void run() {
        try {
            while (true) {
                GetObjectMetadataRequest request = requests.poll();
                if (request == null) {
                    break;
                }
                try {
                    ObjectMetadata objectMetadata = cos.getObjectMetadata(request);
                    HeadObjectsResult.HeadedObject headedObject = new HeadObjectsResult.HeadedObject(request.getKey(), objectMetadata, true, true);
                    headObjects.add(headedObject);
                } catch (CosServiceException cse) {
                    if (cse.getStatusCode() == 404) {
                        HeadObjectsResult.HeadedObject headedObject = new HeadObjectsResult.HeadedObject(request.getKey(), new ObjectMetadata(), false, true);
                        headObjects.add(headedObject);
                    } else {
                        HeadObjectsResult.HeadedObject headedObject = new HeadObjectsResult.HeadedObject(request.getKey(), new ObjectMetadata(), false, false);
                        headObjects.add(headedObject);
                        throw cse;
                    }
                }
            }
        } finally {
            countDownLatch.countDown();
        }
    }
}
