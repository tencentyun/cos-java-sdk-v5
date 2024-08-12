package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PlateDetail {
    @XStreamAlias("UpperLeftX")
    private int upperLeftX;

    @XStreamAlias("UpperLeftY")
    private int upperLeftY;

    @XStreamAlias("BottomLeftX")
    private int bottomLeftX;

    @XStreamAlias("BottomLeftY")
    private int bottomLeftY;

    @XStreamAlias("BottomRightX")
    private int bottomRightX;

    @XStreamAlias("BottomRightY")
    private int bottomRightY;

    @XStreamAlias("UpperRightX")
    private int upperRightX;

    @XStreamAlias("UpperRightY")
    private int upperRightY;

    public int getUpperLeftX() {
        return upperLeftX;
    }

    public void setUpperLeftX(int upperLeftX) {
        this.upperLeftX = upperLeftX;
    }

    public int getUpperLeftY() {
        return upperLeftY;
    }

    public void setUpperLeftY(int upperLeftY) {
        this.upperLeftY = upperLeftY;
    }

    public int getBottomLeftX() {
        return bottomLeftX;
    }

    public void setBottomLeftX(int bottomLeftX) {
        this.bottomLeftX = bottomLeftX;
    }

    public int getBottomLeftY() {
        return bottomLeftY;
    }

    public void setBottomLeftY(int bottomLeftY) {
        this.bottomLeftY = bottomLeftY;
    }

    public int getBottomRightX() {
        return bottomRightX;
    }

    public void setBottomRightX(int bottomRightX) {
        this.bottomRightX = bottomRightX;
    }

    public int getBottomRightY() {
        return bottomRightY;
    }

    public void setBottomRightY(int bottomRightY) {
        this.bottomRightY = bottomRightY;
    }

    public int getUpperRightX() {
        return upperRightX;
    }

    public void setUpperRightX(int upperRightX) {
        this.upperRightX = upperRightX;
    }

    public int getUpperRightY() {
        return upperRightY;
    }

    public void setUpperRightY(int upperRightY) {
        this.upperRightY = upperRightY;
    }
}
