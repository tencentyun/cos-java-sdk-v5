package com.qcloud.cos.model.ciModel.queue;

import com.qcloud.cos.internal.CIServiceRequest;

import java.io.Serializable;

public class MediaQueueRequest extends CIServiceRequest implements Serializable {
    /**
     * 队列 ID，以“,”符号分割字符串
     */
    private String queueId;
    /**
     * 1. Active 表示队列内的作业会被媒体转码服务调度转码执行
     * 2. Paused 表示队列暂停，作业不再会被媒体转码调度转码执行队列内的所有作业状态维持在暂停状态，
     * 已经处于转码中的任务将继续转码，不受影响
     */
    private String state;
    /**
     * 第几页
     */
    private String pageNumber;
    /**
     * 每页个数
     */
    private String pageSize;
    private String name;

    /**
     * CateAll：所有类型
     * Transcoding：媒体处理队列
     * SpeedTranscoding：媒体处理倍速转码队列
     * 默认为 Transcoding
     */
    private String category;

    private MediaNotifyConfig notifyConfig = new MediaNotifyConfig();

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public MediaNotifyConfig getNotifyConfig() {
        return notifyConfig;
    }

    public void setNotifyConfig(MediaNotifyConfig notifyConfig) {
        this.notifyConfig = notifyConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "MediaQueueRequest{" +
                "queueId='" + queueId + '\'' +
                ", state='" + state + '\'' +
                ", pageNumber='" + pageNumber + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", name='" + name + '\'' +
                ", notifyConfig=" + notifyConfig +
                '}';
    }
}
