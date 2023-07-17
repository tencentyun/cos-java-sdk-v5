package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Keywords")
public class AuditingKeyword {
    @XStreamAlias("Content")
    private String content;
    @XStreamAlias("Label")
    private String label;
    @XStreamAlias("Remark")
    private String remark;
    @XStreamAlias("CreateTime")
    private String createTime;
    @XStreamAlias("KeywordID")
    private String keywordID;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getKeywordID() {
        return keywordID;
    }

    public void setKeywordID(String keywordID) {
        this.keywordID = keywordID;
    }
}
