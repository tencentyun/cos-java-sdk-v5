
package com.qcloud.cos.model.ciModel.persistence;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 宠物信息实体类
 */
@XStreamAlias("ResultInfo")
public class PetInfo {
    /**
     * 分数，范围在0 - 100之间，值越高，表示目标为相应结果的可能性越高
     */
    @XStreamAlias("Score")
    private Integer score;
    
    /**
     * 宠物名称
     */
    @XStreamAlias("Name")
    private String name;
    
    /**
     * 宠物位置信息
     */
    @XStreamAlias("Location")
    private PetLocation location;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetLocation getLocation() {
        return location;
    }

    public void setLocation(PetLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PetInfo{");
        sb.append("score=").append(score);
        sb.append(", name='").append(name).append('\'');
        sb.append(", location=").append(location);
        sb.append('}');
        return sb.toString();
    }
}