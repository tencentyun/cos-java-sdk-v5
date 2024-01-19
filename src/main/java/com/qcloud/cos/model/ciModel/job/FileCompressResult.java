package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FileCompressResult {
    @XStreamAlias("Bucket")
    private String bucket;
    @XStreamAlias("CompressFileCount")
    private String compressFileCount;
    @XStreamAlias("ErrorCount")
    private String errorCount;
    @XStreamAlias("Object")
    private String object;
    @XStreamAlias("Region")
    private String region;

    @XStreamAlias("ErrorDetail")
    private ErrorDetail errorDetail;

    public ErrorDetail getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(ErrorDetail errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getCompressFileCount() {
        return compressFileCount;
    }

    public void setCompressFileCount(String compressFileCount) {
        this.compressFileCount = compressFileCount;
    }

    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
