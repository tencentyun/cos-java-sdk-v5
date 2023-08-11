package com.qcloud.cos.model.Policy;

import java.util.ArrayList;
import java.util.List;

public class SubUinResult {
    public String subUin;

    public String accountName;

    public List<SchemaResult> schemaResults = new ArrayList<>();

    public void setSubUin(String subUin) {
        this.subUin = subUin;
    }

    public String getSubUin() {
        return subUin;
    }

    public void setSchemaResults(List<SchemaResult> schemaResults) {
        this.schemaResults = schemaResults;
    }

    public List<SchemaResult> getSchemaResults() {
        return schemaResults;
    }

    public void addSchemaResult(SchemaResult schemaResult) {
        this.schemaResults.add(schemaResult);
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
