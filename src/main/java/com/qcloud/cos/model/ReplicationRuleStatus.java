package com.qcloud.cos.model;

/**
 * A enum class for status of a QCloud bucket replication rule.
 */
public enum ReplicationRuleStatus {
    Enabled("Enabled"),

    Disabled("Disabled");

    private final String status;

    private ReplicationRuleStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}