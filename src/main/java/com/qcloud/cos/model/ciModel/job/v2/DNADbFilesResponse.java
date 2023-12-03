package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("Response")
public class DNADbFilesResponse {

    @XStreamAlias("RequestId")
    private String requestId;

    @XStreamAlias("TotalCount")
    private int totalCount;

    @XStreamAlias("PageNumber")
    private int pageNumber;

    @XStreamAlias("PageSize")
    private int pageSize;

    @XStreamImplicit(itemFieldName = "DNADbFiles")
    private List<DNADbFile> dnaDbFiles;

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

    public List<DNADbFile> getDnaDbFiles() {
        return dnaDbFiles;
    }

    public void setDnaDbFiles(List<DNADbFile> dnaDbFiles) {
        this.dnaDbFiles = dnaDbFiles;
    }

    @XStreamAlias("DNADbFile")
    public static class DNADbFile {
        @XStreamAlias("BucketId")
        private String bucketId;

        @XStreamAlias("Region")
        private String region;

        @XStreamAlias("DNADbId")
        private String dnaDbId;

        @XStreamAlias("VideoId")
        private String videoId;

        @XStreamAlias("Object")
        private String object;

        @XStreamAlias("ETag")
        private String eTag;

        @XStreamAlias("CreateTime")
        private String createTime;

        @XStreamAlias("UpdateTime")
        private String updateTime;

        public String getBucketId() {
            return bucketId;
        }

        public void setBucketId(String bucketId) {
            this.bucketId = bucketId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDnaDbId() {
            return dnaDbId;
        }

        public void setDnaDbId(String dnaDbId) {
            this.dnaDbId = dnaDbId;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }

        public String geteTag() {
            return eTag;
        }

        public void seteTag(String eTag) {
            this.eTag = eTag;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

    }

}
