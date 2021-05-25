package com.qcloud.cos.model.ciModel.auditing;

/**
 * 审核信息公共实体类 https://cloud.tencent.com/document/product/460/37318
 */
public class AudtingCommonInfo {
    /**
     * 错误码，0为正确，其他数字对应相应错误。详情请参见 https://cloud.tencent.com/document/product/460/8523
     */
    private String code;
    /**
     * 具体错误信息，如正常则为 OK
     */
    private String msg;
    /**
     * 是否命中该审核分类，0表示未命中，1表示命中，2表示疑似
     */
    private String hitFlag;
    /**
     * 审核分值。0 - 60分表示图片正常，60 - 90分表示图片疑似敏感，90 - 100分表示图片确定敏感
     */
    private String score;
    /**
     * 识别出的图片标签
     */
    private String label;

    /**
     * 次数
     */
    private String count;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AudtingCommonInfo{");
        sb.append("code='").append(code).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", hitFlag='").append(hitFlag).append('\'');
        sb.append(", score='").append(score).append('\'');
        sb.append(", label='").append(label).append('\'');
        sb.append(", count='").append(count).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
