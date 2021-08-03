package com.qcloud.cos.model.ciModel.auditing;

/**
 * 审核结果信息
 */
public class AuditingInfo {
    /**
     * 结果类型
     */
    private DetectType type;

    /**
     * 结果类型中文名
     */
    private String typeName;

    private String[] keyWords;

    /**
     * 是否命中该审核分类，0表示未命中，1表示命中，2表示疑似
     */
    private String hitFlag;
    /**
     * 审核分值。0 - 60分表示图片正常，60 - 90分表示图片疑似敏感，90 - 100分表示图片确定敏感
     */
    private String score;

    /**
     * 次数
     */
    private String count;

    public AuditingInfo(DetectType type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public DetectType getType() {
        return type;
    }

    public void setType(DetectType type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public AuditingInfo() {
    }

    public String[] getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String[] keyWords) {
        this.keyWords = keyWords;
    }

    public String getHitFlag() {
        return hitFlag;
    }

    public void setHitFlag(String hitFlag) {
        this.hitFlag = hitFlag;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
