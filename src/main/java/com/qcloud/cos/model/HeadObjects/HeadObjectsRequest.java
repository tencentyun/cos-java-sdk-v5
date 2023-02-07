package com.qcloud.cos.model.HeadObjects;

import com.qcloud.cos.model.GetObjectMetadataRequest;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class HeadObjectsRequest {
    /**
     * The name of the COS bucket containing the object(s) to head.
     */
    private String bucketName;

    private LinkedBlockingQueue<GetObjectMetadataRequest> headRequests;

    public HeadObjectsRequest(String bucketName, List<GetObjectMetadataRequest> requests) {
        setBucketName(bucketName);
        setHeadRequests(requests);
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
    /**
     * Sets the list of requests to head, clearing any existing list of requests.
     *
     * @param requests The list of requests to head
     */
    public void setHeadRequests(List<GetObjectMetadataRequest> requests) {
        this.headRequests = new LinkedBlockingQueue<>(requests.size());
        for (GetObjectMetadataRequest getObjectMetadataRequest : requests) {
            if (getObjectMetadataRequest == null) {
                throw new IllegalArgumentException("The head object request must be specified");
            }
            this.headRequests.add(getObjectMetadataRequest);
        }
    }

    public LinkedBlockingQueue<GetObjectMetadataRequest> getRequests() {
        return headRequests;
    }
}
