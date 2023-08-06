package com.qcloud.cos.model.ciModel.common;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 媒体处理 输出信息实体
 */
public class MediaOutputObject {
    /**
     * 消息队列所属园区
     * 目前支持园区 sh（上海）、bj（北京）、gz（广州）、cd（成都）、hk（香港）
     */
    @XStreamAlias("Region")
    private String region;

    /**
     * 消息队列使用模式，默认 Queue ：
     * 主题订阅：Topic
     * 队列服务: Queue
     */
    @XStreamAlias("Bucket")
    private String bucket;

    /**
     * 对象在桶中的地址
     */
    @XStreamAlias("Object")
    private String object;

    /**
     * 雪碧图的名字。
     * 当任务类型为 Snapshot使用，必须包含 ${Number} 参数。
     * 如 Object 为 snapshot-${Number}.jpg **
     * 仅支持jpg格式
     */
    @XStreamAlias("SpriteObject")
    private String spriteObject;

    /**
     * 人声结果文件名，不能与 Object 同时为空
     */
    @XStreamAlias("AuObject")
    private String auObject;


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

    public String getAuObject() {
        return auObject;
    }

    public void setAuObject(String auObject) {
        this.auObject = auObject;
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
