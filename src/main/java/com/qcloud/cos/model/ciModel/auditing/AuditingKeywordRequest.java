package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Request")
public class AuditingKeywordRequest extends CIServiceRequest {
    /**
     * 自定义文本库的名称，支持中文、英文、数字、下划线组合，不超过32个字符。
     */
    @XStreamOmitField
    private String libId;

    @XStreamImplicit(itemFieldName = "Keywords")
    private List<AuditingKeyword> keywords;

    @XStreamAlias("Offset")
    private Integer offset;
    /**
     * 查询的最大条数，默认为20。当未使用libid时生效。
     */
    @XStreamAlias("Limit")
    private Integer limit;

    @XStreamAlias("Content")
    private String content;
    @XStreamAlias("Label")
    private String label;

    @XStreamImplicit(itemFieldName = "KeywordIDs")
    private List<String> keywordIDs;

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

    public String getLibId() {
        return libId;
    }

    public void setLibId(String libId) {
        this.libId = libId;
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

    public List<AuditingKeyword> getKeywords() {
        if (keywords == null) {
            keywords = new ArrayList<>();
        }
        return keywords;
    }

    public List<String> getKeywordIDs() {
        if (keywordIDs == null) {
            keywordIDs = new ArrayList<>();
        }
        return keywordIDs;
    }

    public void setKeywordIDs(List<String> keywordIDs) {
        this.keywordIDs = keywordIDs;
    }

    public void setKeywords(List<AuditingKeyword> keywords) {
        this.keywords = keywords;
    }

}
