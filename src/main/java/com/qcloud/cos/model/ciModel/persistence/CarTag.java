package com.qcloud.cos.model.ciModel.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * @author markjrzhang
 * @date 2022/4/28 16:24
 */
public class CarTag {
    /**
     * 车系
     */
    private String serial;
    /**
     * 车辆品牌
     */
    private String brand;
    /**
     * 车辆类型
     */
    private String type;
    /**
     * 车辆颜色
     */
    private String color;
    /**
     * 置信度，0 - 100
     */
    private String confidence;
    /**
     * 年份，识别不出年份时返回0
     */
    private String year;
    /**
     * 车辆在图片中的坐标信息，可能返回多个坐标点的值
     */
    private List<CarLocation> carLocations = new ArrayList<>();
    /**
     * 车牌信息，包含车牌号、车牌颜色、车牌位置。支持返回多个车牌
     */
    private PlateContent plateContent = new PlateContent();

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<CarLocation> getCarLocations() {
        return carLocations;
    }

    public void setCarLocations(List<CarLocation> carLocations) {
        this.carLocations = carLocations;
    }

    public PlateContent getPlateContent() {
        return plateContent;
    }

    public void setPlateContent(PlateContent plateContent) {
        this.plateContent = plateContent;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CarTags{");
        sb.append("serial='").append(serial).append('\'');
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", confidence='").append(confidence).append('\'');
        sb.append(", year='").append(year).append('\'');
        sb.append(", carLocations=").append(carLocations);
        sb.append(", plateContent=").append(plateContent);
        sb.append('}');
        return sb.toString();
    }
}
