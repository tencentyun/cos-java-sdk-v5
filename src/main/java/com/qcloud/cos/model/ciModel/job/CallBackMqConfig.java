package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class CallBackMqConfig {
    /**
     * 消息队列所属园区
     * 目前支持园区 sh（上海）、bj（北京）、gz（广州）、cd（成都）、hk（香港）
     */
    @XStreamAlias("MqRegion")
    private String mqRegion;
    /**
     * 消息队列使用模式，默认 Queue ：
     * 主题订阅：Topic
     * 队列服务: Queue
     */
    @XStreamAlias("MqMode")
    private String mqMode;
    /**
     * TDMQ 主题名称
     */
    @XStreamAlias("MqName")
    private String mqName;

    public String getMqRegion() {
        return mqRegion;
    }

    public void setMqRegion(String mqRegion) {
        this.mqRegion = mqRegion;
    }

    public String getMqMode() {
        return mqMode;
    }

    public void setMqMode(String mqMode) {
        this.mqMode = mqMode;
    }

    public String getMqName() {
        return mqName;
    }

    public void setMqName(String mqName) {
        this.mqName = mqName;
    }
}
