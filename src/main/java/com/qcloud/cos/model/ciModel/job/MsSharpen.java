package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class MsSharpen {
    @XStreamAlias("SharpenLevel")
    private String sharpenLevel;

    public String getSharpenLevel() {
        return sharpenLevel;
    }

    public void setSharpenLevel(String sharpenLevel) {
        this.sharpenLevel = sharpenLevel;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MsSharpen{");
        sb.append("sharpenLevel='").append(sharpenLevel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
