package com.qcloud.cos.model.ciModel.persistence;

public class CarLocation {
    /**
     * 定位出的车辆的横坐标
     */
    private String x;
    /**
     * 定位出的车辆的横坐标
     */
    private String y;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CarLocation{");
        sb.append("x='").append(x).append('\'');
        sb.append(", y='").append(y).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
