package com.qcloud.cos.model.ciModel.auditing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("Libs")
public class AuditingTextLib {

    @XStreamAlias("LibID")
    private String libID;
    @XStreamAlias("LibName")
    private String libName;
    @XStreamAlias("Suggestion")
    private String suggestion;
    @XStreamAlias("MatchType")
    private String matchType;
    @XStreamAlias("CreateTime")
    private String createTime;
    @XStreamImplicit(itemFieldName = "Strategies")
    private List<AuditingStrategy> Strategies;

    public String getLibID() {
        return libID;
    }

    public void setLibID(String libID) {
        this.libID = libID;
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<AuditingStrategy> getStrategies() {
        return Strategies;
    }

    public void setStrategies(List<AuditingStrategy> strategies) {
        Strategies = strategies;
    }
}
