package com.qcloud.cos.model.ciModel.auditing;

public class BatchImageAuditingInputObject extends AuditingInputObject {
    private String interval;
    private String maxFrames;
    private String largeImageDetect;

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getMaxFrames() {
        return maxFrames;
    }

    public void setMaxFrames(String maxFrames) {
        this.maxFrames = maxFrames;
    }

    public String getLargeImageDetect() {
        return largeImageDetect;
    }

    public void setLargeImageDetect(String largeImageDetect) {
        this.largeImageDetect = largeImageDetect;
    }

    @Override
    public String toString() {
        return "BatchImageAuditingInputObject{" +
                "interval='" + interval + '\'' +
                ", maxFrames='" + maxFrames + '\'' +
                ", largeImageDetect='" + largeImageDetect + '\'' +
                '}';
    }
}
