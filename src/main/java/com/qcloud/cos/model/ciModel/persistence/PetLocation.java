
package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 宠物位置信息实体类
 */
@XStreamAlias("Location")
public class PetLocation {
    /**
     * x 轴坐标
     */
    @XStreamAlias("X")
    private Integer x;
    
    /**
     * y 轴坐标
     */
    @XStreamAlias("Y")
    private Integer y;
    
    /**
     * (x,y)坐标距离高度
     */
    @XStreamAlias("Height")
    private Integer height;
    
    /**
     * (x,y)坐标距离长度
     */
    @XStreamAlias("Width")
    private Integer width;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PetLocation{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", height=").append(height);
        sb.append(", width=").append(width);
        sb.append('}');
        return sb.toString();
    }
}