package com.qcloud.cos.model.ciModel.image;

public class FaceInfo {
    private String x;
    private String y;
    private String Width;
    private String Height;

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

    public String getWidth() {
        return Width;
    }

    public void setWidth(String width) {
        Width = width;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FaceInfo{");
        sb.append("x='").append(x).append('\'');
        sb.append(", y='").append(y).append('\'');
        sb.append(", Width='").append(Width).append('\'');
        sb.append(", Height='").append(Height).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
