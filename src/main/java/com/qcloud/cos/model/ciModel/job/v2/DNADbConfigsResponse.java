package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("Response")
public class DNADbConfigsResponse {
    @XStreamAlias("RequestId")
    private String requestId;

    @XStreamAlias("TotalCount")
    private int totalCount;

    @XStreamAlias("PageNumber")
    private int pageNumber;

    @XStreamAlias("PageSize")
    private int pageSize;

    @XStreamImplicit(itemFieldName = "DNADbConfig")
    private List<DNADbConfig> dnaDbConfigs;

    @XStreamImplicit(itemFieldName = "NonExistIDs")
    private List<String> nonExistIds;

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

    public List<DNADbConfig> getDnaDbConfigs() {
        return dnaDbConfigs;
    }

    public void setDnaDbConfigs(List<DNADbConfig> dnaDbConfigs) {
        this.dnaDbConfigs = dnaDbConfigs;
    }

    public List<String> getNonExistIds() {
        return nonExistIds;
    }

    public void setNonExistIds(List<String> nonExistIds) {
        this.nonExistIds = nonExistIds;
    }

}
