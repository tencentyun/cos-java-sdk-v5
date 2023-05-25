package com.qcloud.cos.model.ciModel.job;

public class MsSharpen {
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
