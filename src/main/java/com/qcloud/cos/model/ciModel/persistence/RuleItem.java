package com.qcloud.cos.model.ciModel.persistence;

public class RuleItem {
    private String fileid;
    private Rule rule;

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
