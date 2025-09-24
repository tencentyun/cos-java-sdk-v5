package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class SDRtoHDR {
    @XStreamAlias("HdrMode")
    private String hdrMode;

    public String getHdrMode() {
        return hdrMode;
    }

    public void setHdrMode(String hdrMode) {
        this.hdrMode = hdrMode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SDRtoHDR{");
        sb.append("hdrMode='").append(hdrMode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
