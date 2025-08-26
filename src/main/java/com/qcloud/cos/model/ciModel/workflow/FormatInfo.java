
package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Format")
public class FormatInfo {
    @XStreamAlias("Duration")
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "FormatInfo{" +
                "duration='" + duration + '\'' +
                '}';
    }
}