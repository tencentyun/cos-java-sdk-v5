package com.qcloud.cos.model.ciModel.recognition;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class QRcodeInfo {
    @XStreamAlias("codeUrl")
    private String codeUrl;
    @XStreamAlias("CodeLocation")
    private CodeLocation codeLocation;

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public CodeLocation getCodeLocation() {
        return codeLocation;
    }

    public void setCodeLocation(CodeLocation codeLocation) {
        this.codeLocation = codeLocation;
    }
}
