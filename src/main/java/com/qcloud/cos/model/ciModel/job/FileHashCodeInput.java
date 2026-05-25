package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文件哈希值计算输入信息
 */
@XStreamAlias("Input")
public class FileHashCodeInput {
    
    @XStreamAlias("Bucket")
    private String bucket;
    
    @XStreamAlias("Object")
    private String object;
    
    @XStreamAlias("Region")
    private String region;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileHashCodeInput{");
        sb.append("bucket='").append(bucket).append('\'');
        sb.append(", object='").append(object).append('\'');
        sb.append(", region='").append(region).append('\'');
        sb.append('}');
        return sb.toString();
    }
}