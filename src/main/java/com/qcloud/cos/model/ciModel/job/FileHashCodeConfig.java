package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FileHashCodeConfig {
    @XStreamAlias("Type")
    private String type;

    @XStreamAlias("AddToHeader")
    private String addToHeader;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddToHeader() {
        return addToHeader;
    }

    public void setAddToHeader(String addToHeader) {
        this.addToHeader = addToHeader;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileHashCodeConfig{");
        sb.append("type='").append(type).append('\'');
        sb.append(", addToHeader='").append(addToHeader).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
