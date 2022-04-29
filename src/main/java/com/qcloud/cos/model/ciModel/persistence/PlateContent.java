package com.qcloud.cos.model.ciModel.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * @author markjrzhang
 * @date 2022/4/28 16:26
 */
public class PlateContent {
    /**
     * 车牌号信息
     */
    private String plate;
    /**
     * 车牌的颜色
     */
    private String color;
    /**
     * 车牌的种类，例如普通蓝牌
     */
    private String type;
    /**
     * 车牌的位置
     */
    private List<PlateLocation> plateLocationList = new ArrayList<>();

    public static class PlateLocation {
        /**
         * 定位出的车牌左上角、右上角、左下角、右下角的 X 坐标
         */
        private String x;
        /**
         * 定位出的车牌左上角、右上角、左下角、右下角的 Y 坐标
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
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PlateLocation> getPlateLocationList() {
        return plateLocationList;
    }

    public void setPlateLocationList(List<PlateLocation> plateLocationList) {
        this.plateLocationList = plateLocationList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PlateContent{");
        sb.append("plate='").append(plate).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", plateLocationList=").append(plateLocationList);
        sb.append('}');
        return sb.toString();
    }
}
