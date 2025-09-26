package com.qcloud.cos.model.ciModel.workflow;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MediaWorkflowDependency {
    @XStreamAlias("value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MediaWorkflowDependency{" +
                "value='" + value + '\'' +
                '}';
    }
}
