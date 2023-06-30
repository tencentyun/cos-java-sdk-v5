package com.qcloud.cos.model.Policy;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatementPrincipal implements Serializable {
    private List<String> qcs = new LinkedList<>();

    public List<String> getQcs() {
        return qcs;
    }

    public void addQcs(String principal_qcs) {
        this.qcs.add(principal_qcs);
    }
}
