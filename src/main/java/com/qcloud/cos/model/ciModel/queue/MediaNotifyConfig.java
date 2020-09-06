package com.qcloud.cos.model.ciModel.queue;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NotifyConfig")
public class MediaNotifyConfig {
    @XStreamAlias("Url")
    private String url;
    @XStreamAlias("Type")
    private String type;
    @XStreamAlias("Event")
    private String event;
    @XStreamAlias("State")
    private String state;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "NotifyConfig{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", event='" + event + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
