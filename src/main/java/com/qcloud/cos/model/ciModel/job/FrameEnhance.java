package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FrameEnhance {
    @XStreamAlias("FrameDoubling")
    private String frameDoubling;

    public String getFrameDoubling() {
        return frameDoubling;
    }

    public void setFrameDoubling(String frameDoubling) {
        this.frameDoubling = frameDoubling;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FrameEnhance{");
        sb.append("frameDoubling='").append(frameDoubling).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
