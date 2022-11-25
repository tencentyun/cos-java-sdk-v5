package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

public class MediaTopkRecognition {
    private List<MediaBodyInfo> bodyInfoList;
    private List<MediaBodyInfo> carInfoList;
    private List<MediaBodyInfo> petInfoList;
    private String time;
    private String url;
    private String score;

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

    public List<MediaBodyInfo> getBodyInfoList() {
        if (bodyInfoList == null) {
            bodyInfoList = new ArrayList<>();
        }
        return bodyInfoList;
    }

    public void setBodyInfoList(List<MediaBodyInfo> bodyInfoList) {
        this.bodyInfoList = bodyInfoList;
    }

    public List<MediaBodyInfo> getCarInfoList() {
        if (carInfoList == null) {
            carInfoList = new ArrayList<>();
        }
        return carInfoList;
    }

    public void setCarInfoList(List<MediaBodyInfo> carInfoList) {
        this.carInfoList = carInfoList;
    }

    public List<MediaBodyInfo> getPetInfoList() {
        if (petInfoList == null) {
            petInfoList = new ArrayList<>();
        }
        return petInfoList;
    }

    public void setPetInfoList(List<MediaBodyInfo> petInfoList) {
        this.petInfoList = petInfoList;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MediaTopkRecognition{");
        sb.append("bodyInfoList=").append(bodyInfoList);
        sb.append(", carInfoList=").append(carInfoList);
        sb.append(", petInfoList=").append(petInfoList);
        sb.append(", time='").append(time).append('\'');
        sb.append(", url='").append(url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
