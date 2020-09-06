package com.qcloud.cos.model.ciModel.template;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaSnapshotObject {

    /**
     * 模式
     */
    @XStreamAlias("Mode")
    private String mode ;
    /**
     * 开始时间
     */
    @XStreamAlias("Start")
    private String start;
    /**
     * 截图频率
     */
    @XStreamAlias("TimeInterval")
    private String timeInterval;
    /**
     * 截图数量
     */
    @XStreamAlias("Count")
    private String count;
    /**
     * 视频原始宽度
     */
    @XStreamAlias("Width")
    private String width;
    /**
     * 视频原始高度
     */
    @XStreamAlias("Height")
    private String height;


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "MediaSnapshotObject{" +
                "mode='" + mode + '\'' +
                ", start='" + start + '\'' +
                ", timeInterval='" + timeInterval + '\'' +
                ", count='" + count + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
