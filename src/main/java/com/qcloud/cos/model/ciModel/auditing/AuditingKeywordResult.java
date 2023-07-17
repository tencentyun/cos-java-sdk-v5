package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Results")
public class AuditingKeywordResult {

    @XStreamAlias("Code")
    private String code;
    @XStreamAlias("ErrMsg")
    private String errMsg;
    @XStreamAlias("KeywordID")
    private String keywordID;
    @XStreamAlias("Content")
    private String content;
    @XStreamAlias("Label")
    private String label;
    @XStreamAlias("Remark")
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getKeywordID() {
        return keywordID;
    }

    public void setKeywordID(String keywordID) {
        this.keywordID = keywordID;
    }

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
}
