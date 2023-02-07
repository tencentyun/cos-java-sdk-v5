package com.qcloud.cos.model.HeadObjects;

import com.qcloud.cos.COS;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GetObjectMetadataRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public class HeadManager {
    private static final int DEFAULT_THREADS_COUNTs = 10;

    //默认请求超时时间 60s
    private static final int DEFAULT_REQUEST_TIMEOUT = 60 * 1000;

    private final COS cos;

    private int nThreads = DEFAULT_THREADS_COUNTs;

    private int request_timeout = DEFAULT_REQUEST_TIMEOUT;

    public HeadManager(COS cos) {
        this.cos = cos;
    }

    public void setThreadsCounts(int t_Count) {
        this.nThreads = t_Count;
    }

    public int getThreadsCounts() {
        return nThreads;
    }

    public void setRequestTimeout(int request_timeout) {
        this.request_timeout = request_timeout;
    }

    public int getRequestTimeout() {
        return request_timeout;
    }

    public List<HeadObjectResult> headObjects(String bucketName, List<GetObjectMetadataRequest> requests)
            throws CosClientException, CosServiceException {
        HeadObjectsRequest headObjectsRequest = new HeadObjectsRequest(bucketName, requests);
        return headObjects(headObjectsRequest);
    }

    public List<HeadObjectResult> headObjects(HeadObjectsRequest headObjectsRequest) {
        if (nThreads <= 0) {
            throw new IllegalArgumentException("The thread counts must be greater than 0");
        }
        if (headObjectsRequest.getRequests().isEmpty()) {
            throw new IllegalArgumentException("The size of the requests must be greater than 0");
        }

        int count_of_keys = headObjectsRequest.getRequests().size();
        if (count_of_keys > 1000) {
            throw new IllegalArgumentException("The size of the requests must be smaller than 1000");
        }

        CountDownLatch countDownLatch = new CountDownLatch(nThreads);
        LinkedBlockingQueue<HeadObjectResult> headedObjects = new LinkedBlockingQueue<>(count_of_keys);
        List<Thread> threads = new ArrayList<>(nThreads);
        for (int i = 0; i < nThreads; i++) {
            HeadConsumer headConsumer = new HeadConsumer(headObjectsRequest.getRequests(), headedObjects, countDownLatch, cos);
            Thread thread = new Thread(headConsumer);
            threads.add(thread);
            thread.start();
        }
        try {
            boolean isTimeout = countDownLatch.await(request_timeout, TimeUnit.MILLISECONDS);
            if (!isTimeout) {
                for (Thread thread : threads) {
                    thread.interrupt();
                }
                throw new CosClientException("ExecutorService: The wait " + request_timeout + " timed out: ");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
            throw new CosClientException(e.getMessage(),e);
        }

        return new ArrayList<>(headedObjects);
    }
}
