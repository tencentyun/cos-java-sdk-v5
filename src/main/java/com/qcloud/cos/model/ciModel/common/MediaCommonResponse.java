package com.qcloud.cos.model.ciModel.common;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * ci公用返回实体
 */
public class MediaCommonResponse {
    /**
     * 请求id
     */
    @XStreamAlias("RequestId")
    private String requestId;
    /**
     * 查询的总条数 用于list相关接口
     */
    @XStreamAlias("TotalCount")
    private String totalCount;
    /**
     * 页码
     */
    @XStreamAlias("PageNumber")
    private String pageNumber;
    /**
     * 每页展示数量
     */
    @XStreamAlias("PageSize")
    private String pageSize;
    /**
     * 创建时间
     */
    @XStreamAlias("CreateTime")
    private String createTime;
    /**
     * 修改时间
     */
    @XStreamAlias("UpdateTime")
    private String updateTime;

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

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MediaCommonResponse{" +
                "requestId='" + requestId + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", PageNumber='" + pageNumber + '\'' +
                ", PageSize='" + pageSize + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

}
