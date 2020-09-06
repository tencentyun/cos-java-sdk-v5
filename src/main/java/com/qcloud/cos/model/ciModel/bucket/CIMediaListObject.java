package com.qcloud.cos.model.ciModel.bucket;

import java.util.List;

public class CIMediaListObject  {
    private String requestId;
    private int totalCount;
    private int pageNumber;
    private int pageSize;
    private List<CIBucketObject> mediaBucketList;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<CIBucketObject> getMediaBucketList() {
        return mediaBucketList;
    }

    public void setMediaBucketList(List<CIBucketObject> mediaBucketList) {
        this.mediaBucketList = mediaBucketList;
    }
}
