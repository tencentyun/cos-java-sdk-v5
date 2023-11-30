package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 截图信息，只返回违规的截图信息
 */
public class AudioSectionInfo extends SectionInfo {
    @XStreamAlias("OffsetTime")
    private String offsetTime;
    @XStreamAlias("Duration")
    private String duration;

    public String getOffsetTime() {
        return offsetTime;
    }

    public void setOffsetTime(String offsetTime) {
        this.offsetTime = offsetTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AudioSectionInfo{");
        sb.append(", offsetTime='").append(offsetTime).append('\'');
        sb.append(", duration='").append(duration).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
