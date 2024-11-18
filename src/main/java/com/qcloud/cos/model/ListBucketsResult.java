package com.qcloud.cos.model;

import java.util.List;

public class ListBucketsResult {
    private List<Bucket> buckets;

    private boolean isTruncated;

    private String nextMarker;

    private Owner bucketOwner;

    public List<Bucket> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<Bucket> buckets) {
        this.buckets = buckets;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public void setTruncated(boolean truncated) {
        isTruncated = truncated;
    }

    public String getNextMarker() {
        return nextMarker;
    }

    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    public Owner getBucketOwner() {
        return bucketOwner;
    }

    public void setBucketOwner(Owner bucketOwner) {
        this.bucketOwner = bucketOwner;
    }
}
