package com.qcloud.cos.model.ciModel.bucket;

/**
 * 数据万象媒体bucket实体类 字段详情见 https://cloud.tencent.com/document/product/460/38914
 */
public class MediaBucketObject {
    private String bucketId;
    private String Name;
    private String Region;
    private String CreateTime;

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    @Override
    public String toString() {
        return "MediaBucketObject{" +
                "bucketId='" + bucketId + '\'' +
                ", Name='" + Name + '\'' +
                ", Region='" + Region + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                '}';
    }
}
