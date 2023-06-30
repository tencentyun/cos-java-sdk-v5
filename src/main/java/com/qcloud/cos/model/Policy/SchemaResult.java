package com.qcloud.cos.model.Policy;

import java.util.List;

public class SchemaResult {
    public String schemaName;
    public List<TableResult> tableResults;

    public SchemaResult(String schemaname, List<TableResult> tableResults) {
        this.schemaName = schemaname;
        this.tableResults = tableResults;
    }

    public void setSchemaName(String schemaname) {
        this.schemaName = schemaname;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setTableResults(List<TableResult> tableResults) {
        this.tableResults = tableResults;
    }

    public List<TableResult> getTableResults() {
        return tableResults;
    }
}
