package com.qcloud.cos.model;

/**
 * CAS retrieval tier at which the restore will be processed.
 */
public enum Tier {

    Standard("Standard"),
    Bulk("Bulk"),
    Expedited("Expedited");

    private final String value;

    Tier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Use this in place of valueOf.
     *
     * @param value real value
     * @return Tier corresponding to the value
     */
    public static Tier fromValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty!");
        }

        for (Tier enumEntry : Tier.values()) {
            if (enumEntry.toString().equals(value)) {
                return enumEntry;
            }
        }

        throw new IllegalArgumentException("Cannot create enum from " + value + " value!");
    }
}