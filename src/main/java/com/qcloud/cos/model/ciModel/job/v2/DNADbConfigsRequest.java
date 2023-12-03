package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.internal.CIServiceRequest;

public class DNADbConfigsRequest extends CIServiceRequest {
    private String ids;
    private String pageNumber;
    private String pageSize;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
