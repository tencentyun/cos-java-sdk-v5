package com.qcloud.cos.model.ciModel.job;


import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 去除水印实体类
 */
public class MediaRemoveWaterMark {
    /**
     * 距离左上角原点x偏移, [1, 4096]
     */
    @XStreamAlias("Dx")
    private String dx;
    /**
     * 距离左上角原点y偏移, [1, 4096]
     */
    @XStreamAlias("Dy")
    private String dy;
    /**
     * 宽, [1, 4096]
     */
    @XStreamAlias("Width")
    private String width;
    /**
     * 高, [1, 4096]
     */
    @XStreamAlias("Height")
    private String height;

    /**
     * 开关
     */
    @XStreamAlias("Switch")
    private String _switch;

    @XStreamAlias("StartTime")
    private String startTime;

    @XStreamAlias("EndTime")
    private String endTime;

    public String getDx() {
        return dx;
    }

    public void setDx(String dx) {
        this.dx = dx;
    }

    public String getDy() {
        return dy;
    }

    public void setDy(String dy) {
        this.dy = dy;
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


    public String get_switch() {
        return _switch;
    }

    public void set_switch(String _switch) {
        this._switch = _switch;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "MediaRemoveWaterMark{" +
                "dx='" + dx + '\'' +
                ", dy='" + dy + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                '}';
    }
}
