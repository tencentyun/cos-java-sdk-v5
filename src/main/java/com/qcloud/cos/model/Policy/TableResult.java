package com.qcloud.cos.model.Policy;

public class TableResult {
    public String tableName;
    public String active;

    public TableResult(String table_name, String active) {
        this.tableName = table_name;
        this.active = active;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }
}
