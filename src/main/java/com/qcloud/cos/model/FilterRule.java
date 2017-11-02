package com.qcloud.cos.model;

import java.io.Serializable;

/**
 * Model class representing a Filter Rule for a {@link NotificationConfiguration}.
 */
public class FilterRule implements Serializable {

    private String name;
    private String value;

    /**
     * Returns the name for this {@link FilterRule}.
     * 
     * @return Name of this {@link FilterRule}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for this {@link FilterRule}.
     * 
     * @param name
     *            New name for this {@link FilterRule}.
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("FilterRule Name is a required argument");
        }
        this.name = name;
    }

    /**
     * Sets the name for this {@link FilterRule} and returns this object for method chaining.
     * 
     * @param name
     *            New name for this {@link FilterRule}.
     * @return This object for method chaining
     */
    public FilterRule withName(String name) {
        setName(name);
        return this;
    }

    /**
     * Returns the value for this {@link FilterRule}
     * 
     * @return Value for this {@link FilterRule}
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value for this {@link FilterRule}
     * 
     * @param value
     *            New value for this {@link FilterRule}
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the value for this {@link FilterRule} and returns this object for method chaining
     * 
     * @param value
     *            New value for this {@link FilterRule}
     * @return This object for method chaining
     */
    public FilterRule withValue(String value) {
        setValue(value);
        return this;
    }
}
