package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class ErrorDetail {
    @XStreamAlias("ErrorCount")
    private String errorCount;
    @XStreamImplicit(itemFieldName = "ErrorFile")
    private List<String> errorFile;

    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }

    public List<String> getErrorFile() {
        return errorFile;
    }

    public void setErrorFile(List<String> errorFile) {
        this.errorFile = errorFile;
    }
}
