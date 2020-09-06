package com.qcloud.cos.model.ciModel.queue;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

@XStreamAlias("Request")
public class MediaQueueRequest extends CIServiceRequest implements Serializable {
    @XStreamAlias("QueueId")
    private String queueId;
    @XStreamAlias("State")
    private String state;
    @XStreamAlias("PageNumber")
    private String pageNumber;
    @XStreamAlias("PageSize")
    private String pageSize;
    @XStreamAlias("Name")
    private String name;
    @XStreamAlias("NotifyConfig")
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
}
