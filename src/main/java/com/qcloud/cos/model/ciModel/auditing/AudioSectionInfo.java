package com.qcloud.cos.model.ciModel.auditing;

/**
 * 视频中视频声音审核的结果
 */
public class AudioSectionInfo extends SnapshotInfo {
    private String OffsetTime;
    private String Duration;
    private String Label;
    private String Result;

    public String getOffsetTime() {
        return OffsetTime;
    }

    public void setOffsetTime(String offsetTime) {
        OffsetTime = offsetTime;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @Override
    public String toString() {
        return "AudioSectionInfo{" +
                "OffsetTime='" + OffsetTime + '\'' +
                ", Duration='" + Duration + '\'' +
                ", Label='" + Label + '\'' +
                ", Result='" + Result + '\'' +
                '}';
    }
}
