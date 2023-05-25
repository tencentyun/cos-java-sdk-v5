package com.qcloud.cos.model.ciModel.job;


/**
 * 媒体处理 任务时间参数实体 https://cloud.tencent.com/document/product/460/48234
 */
public class MediaTimeIntervalObject {
    /**
     * 开始时间
     */
    private String start;
    /**
     * 结束时间
     */
    private String end;
    /**
     * 持续时间
     */
    private String duration;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaTimeIntervalObject{");
        sb.append("start='").append(start).append('\'');
        sb.append(", end='").append(end).append('\'');
        sb.append(", duration='").append(duration).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
