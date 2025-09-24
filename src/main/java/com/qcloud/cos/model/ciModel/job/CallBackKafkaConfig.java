package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class CallBackKafkaConfig {
    /**
     * kafka所属园区
     * 目前支持园区
     * ap-guangzhou(广州)
     * ap-shenzhen(深圳)
     * ap-shanghai(上海)
     * ap-nanjing(南京)
     * ap-chengdu(成都)
     * ap-beijing(北京)
     * ap-tianjin(天津)
     * ap-chongqing(重庆)
     * ap-hongkong(香港)
     */
    @XStreamAlias("Region")
    private String region;
    /**
     * 有效的kafka实例id
     */
    @XStreamAlias("InstanceId")
    private String instanceId;

    /**
     * 当前实例下有效的topic信息
     */
    @XStreamAlias("Topic")
    private String topic;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "CallBackKafkaConfig{" +
                "region='" + region + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }
}
