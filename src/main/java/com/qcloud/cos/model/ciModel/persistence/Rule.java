package com.qcloud.cos.model.ciModel.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule {
    @JsonProperty("ci-process")
    private String ciProcess;
    private String cover;

    public String getCiProcess() {
        return ciProcess;
    }

    public void setCiProcess(String ciProcess) {
        this.ciProcess = ciProcess;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
