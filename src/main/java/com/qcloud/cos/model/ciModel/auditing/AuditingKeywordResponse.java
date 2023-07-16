package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("Response")
public class AuditingKeywordResponse extends CiServiceResult {
    @XStreamAlias("LibID")
    private String libID;
    @XStreamAlias("TotalCount")
    private Integer totalCount;
    @XStreamImplicit(itemFieldName = "Results")
    private List<AuditingKeywordResult> results;
    @XStreamImplicit(itemFieldName = "Keywords")
    private List<AuditingKeyword> keywords;

    public List<AuditingKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<AuditingKeyword> keywords) {
        this.keywords = keywords;
    }

    public List<AuditingKeywordResult> getResults() {
        return results;
    }

    public void setResults(List<AuditingKeywordResult> results) {
        this.results = results;
    }

    public String getLibID() {
        return libID;
    }

    public void setLibID(String libID) {
        this.libID = libID;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

}
