package com.qcloud.cos.model.ciModel.auditing;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("Response")
public class AuditingTextLibResponse extends CiServiceResult {
    @XStreamAlias("LibID")
    private String libID;
    @XStreamAlias("TotalCount")
    private Integer totalCount;
    @XStreamImplicit(itemFieldName = "Libs")
    private List<AuditingTextLib> libs;

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

    public List<AuditingTextLib> getLibs() {
        return libs;
    }

    public void setLibs(List<AuditingTextLib> libs) {
        this.libs = libs;
    }
}
