package com.qcloud.cos.model.ciModel.job;

public class MediaClipConfig {
    /**
     * 分片时长, 默认5s
     */
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
