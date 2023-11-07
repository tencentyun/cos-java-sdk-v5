package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("Request")
public class AuditingTextLibRequest extends CIServiceRequest {
    /**
     * 自定义文本库的名称，支持中文、英文、数字、下划线组合，不超过32个字符。
     */
    @XStreamAlias("LibName")
    private String libName;
    /**
     * 表示审核的文本命中了该文本库中的关键词时，返回的处理建议
     * 可选值：Review(表示疑似)、Block(表示敏感)。
     */
    @XStreamAlias("Suggestion")
    private String suggestion;
    /**
     * 表示审核的内容与该文本库中关键词的匹配模式
     * 可选值：Exact(表示精确匹配)、Fuzzy(表示模糊匹配)。
     */
    @XStreamAlias("MatchType")
    private String matchType;
    /**
     * 自定义文本库的ID，填写则表示查询指定的文本库信息。
     * 不填写则表示查询当前账号下的文本库列表。
     */
    @XStreamOmitField
    private String libid;
    /**
     * 查询列表的起始位置，表示从该条开始查询后续的文本库，默认为0。当未使用libid时生效。
     */
    @XStreamAlias("Offset")
    private Integer offset;
    /**
     * 查询的最大条数，默认为20。当未使用libid时生效。
     */
    @XStreamAlias("Limit")
    private Integer limit;

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

    public String getLibid() {
        return libid;
    }

    public void setLibid(String libid) {
        this.libid = libid;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
