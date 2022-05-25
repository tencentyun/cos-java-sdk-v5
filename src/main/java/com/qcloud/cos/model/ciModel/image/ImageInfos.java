package com.qcloud.cos.model.ciModel.image;

public class ImageInfos {
    /**
     * 物品 ID
     */
    private String entityId;
    /**
     * 图片自定义的内容
     */
    private String customContent;
    /**
     * 自定义标签信息，json 字符串
     */
    private String tags;
    /**
     * 图片名称
     */
    private String picName;
    /**
     * 相似度
     */
    private String score;

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

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ImageInfos{" +
                "entityId='" + entityId + '\'' +
                ", customContent='" + customContent + '\'' +
                ", tags='" + tags + '\'' +
                ", picName='" + picName + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
