package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

public class MediaRecognition {
    private List<MediaBodyInfo> infoList;
    private String time;
    private String url;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MediaBodyInfo> getInfoList() {
        if (infoList == null) {
            infoList = new ArrayList<>();
        }
        return infoList;
    }

    public void setInfoList(List<MediaBodyInfo> infoList) {
        this.infoList = infoList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BodyRecognition{");
        sb.append("bodyInfoList=").append(infoList);
        sb.append(", time='").append(time).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
