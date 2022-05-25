package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CIServiceRequest;

public class ImageSearchRequest extends CIServiceRequest {
    /**
     * 操作的bucket名称
     */
    private String bucketName;
    /**
     * 对象存储中的资源路径
     */
    private String objectKey;
    /**
     * 物品 ID，最多支持64个字符。若 EntityId 已存在，则对其追加图片
     */
    private String entityId;
    /**
     * 用户自定义的内容，最多支持4096个字符，查询时原样带回
     */
    private String customContent;
    /**
     * 图片自定义标签，最多不超过10个，json 字符串，格式为 key:value 对
     */
    private String tags;

    /**
     * 出参 Score 中，只有超过 MatchThreshold 值的结果才会返回。默认为0
     */
    private String matchThreshold;
    /**
     * 起始序号，默认值为0
     */
    private String offset;
    /**
     * 返回数量，默认值为10，最大值为100
     */
    private String limit;
    /**
     * 针对入库时提交的 Tags 信息进行条件过滤。支持>、>=、<、<=、=、!=，多个条件之间支持 AND 和 OR 进行连接
     */
    private String filter;

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCustomContent() {
        return customContent;
    }

    public void setCustomContent(String customContent) {
        this.customContent = customContent;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMatchThreshold() {
        return matchThreshold;
    }

    public void setMatchThreshold(String matchThreshold) {
        this.matchThreshold = matchThreshold;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "ImageSearchRequest{" +
                "bucketName='" + bucketName + '\'' +
                ", objectKey='" + objectKey + '\'' +
                ", entityId='" + entityId + '\'' +
                ", customContent='" + customContent + '\'' +
                ", tags='" + tags + '\'' +
                ", matchThreshold='" + matchThreshold + '\'' +
                ", offset='" + offset + '\'' +
                ", limit='" + limit + '\'' +
                ", filter='" + filter + '\'' +
                '}';
    }
}
