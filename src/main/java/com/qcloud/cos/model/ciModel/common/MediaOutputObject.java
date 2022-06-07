package com.qcloud.cos.model.ciModel.common;


/**
 * 媒体处理 输出信息实体
 */
public class MediaOutputObject {
    private String region;
    private String bucket;

    /**
     * 对象在桶中的地址
     */
    private String object;

    /**
     * 雪碧图的名字。
     * 当任务类型为 Snapshot使用，必须包含 ${Number} 参数。
     * 如 Object 为 snapshot-${Number}.jpg **
     * 仅支持jpg格式
     */
    private String spriteObject;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

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

    public String getSpriteObject() {
        return spriteObject;
    }

    public void setSpriteObject(String spriteObject) {
        this.spriteObject = spriteObject;
    }

    @Override
    public String toString() {
        return "MediaOutputObject{" +
                "region='" + region + '\'' +
                ", bucket='" + bucket + '\'' +
                ", object='" + object + '\'' +
                ", spriteObject='" + spriteObject + '\'' +
                '}';
    }
}
