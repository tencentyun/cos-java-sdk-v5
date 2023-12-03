package com.qcloud.cos.model.ciModel.job.v2;

import com.qcloud.cos.internal.CIServiceRequest;

public class DNADbFilesRequest extends CIServiceRequest {

    private String object;

    private String dnaDbId;

    private Integer pageNumber;

    private Integer pageSize;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDnaDbId() {
        return dnaDbId;
    }

    public void setDnaDbId(String dnaDbId) {
        this.dnaDbId = dnaDbId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
