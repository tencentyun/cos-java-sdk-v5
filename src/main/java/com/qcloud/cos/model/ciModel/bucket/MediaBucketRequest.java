package com.qcloud.cos.model.ciModel.bucket;

import com.qcloud.cos.internal.CIServiceRequest;

import java.io.Serializable;

public class MediaBucketRequest extends CIServiceRequest implements Serializable {

    /**
     * 地域信息，以“,”分隔字符串，支持 All、ap-shanghai、ap-beijing
     */
    private String regions;
    /**
     * 存储桶名称，以“,”分隔，支持多个存储桶，精确搜索
     */
    private String bucketNames;
    /**
     * 存储桶名称前缀，前缀搜索
     */
    private String bucketName;
    /**
     * 第几页
     */
    private String pageNumber;
    /**
     * 每页个数
     */
    private String pageSize;

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }

    public String getBucketNames() {
        return bucketNames;
    }

    public void setBucketNames(String bucketNames) {
        this.bucketNames = bucketNames;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @Override
    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
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
