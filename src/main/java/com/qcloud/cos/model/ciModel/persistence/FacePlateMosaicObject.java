package com.qcloud.cos.model.ciModel.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class FacePlateMosaicObject {
    @JsonProperty("is_pic_info")
    private int isPicInfo = 1;
    private List<RuleItem> rules;

    public int getIsPicInfo() {
        return isPicInfo;
    }

    public void setIsPicInfo(int isPicInfo) {
        this.isPicInfo = isPicInfo;
    }

    public List<RuleItem> getRules() {
        if (rules == null) {
            rules = new ArrayList<>();
        }
        return rules;
    }

    public void setRules(List<RuleItem> rules) {
        this.rules = rules;
    }
}

