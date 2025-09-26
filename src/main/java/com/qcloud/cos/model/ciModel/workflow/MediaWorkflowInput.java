package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Input")
public class MediaWorkflowInput {
    @XStreamAlias("QueueId")
    private String queueId;

    @XStreamAlias("ObjectPrefix")
    private String objectPrefix;

    @XStreamAlias("ExtFilter")
    private ExtFilter extFilter;

    @XStreamAlias("PicProcessQueueId")
    private String picProcessQueueId;

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getObjectPrefix() {
        return objectPrefix;
    }

    public void setObjectPrefix(String objectPrefix) {
        this.objectPrefix = objectPrefix;
    }

    public ExtFilter getExtFilter() {
        if (extFilter == null) {
            extFilter = new ExtFilter();
        }
        return extFilter;
    }

    public void setExtFilter(ExtFilter extFilter) {
        this.extFilter = extFilter;
    }

    public String getPicProcessQueueId() {
        return picProcessQueueId;
    }

    public void setPicProcessQueueId(String picProcessQueueId) {
        this.picProcessQueueId = picProcessQueueId;
    }

    @Override
    public String toString() {
        return "MediaWorkflowInput{" +
                "queueId='" + queueId + '\'' +
                ", objectPrefix='" + objectPrefix + '\'' +
                ", extFilter=" + extFilter +
                ", picProcessQueueId='" + picProcessQueueId + '\'' +
                '}';
    }
}
