package com.qcloud.cos.model.Policy;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyContent {
    public String version = "2.0";
    public List<PolicyStatement> statement = new ArrayList<>();

    public void addStatement(PolicyStatement statement) {
        this.statement.add(statement);
    }

    public void setStatement(List<PolicyStatement> statements) {
        this.statement = statements;
    }

    public void setVersion(String policy_version) {
        this.version = policy_version;
    }

    public List<PolicyStatement> getStatement() {
        return statement;
    }
}
