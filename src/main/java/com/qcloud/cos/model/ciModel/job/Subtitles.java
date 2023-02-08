package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

public class Subtitles {
    private List<Subtitle> subtitle;

    public List<Subtitle> getSubtitle() {
        if (subtitle == null) {
            subtitle = new ArrayList<>();
        }
        return subtitle;
    }

    public void setSubtitle(List<Subtitle> subtitle) {
        this.subtitle = subtitle;
    }
}
