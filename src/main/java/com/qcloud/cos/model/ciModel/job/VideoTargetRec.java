package com.qcloud.cos.model.ciModel.job;

import java.util.ArrayList;
import java.util.List;

public class VideoTargetRec {
    /**
     * 是否开启人体检测
     */
    private String body;
    /**
     * 是否开启宠物检测
     */
    private String pet;
    /**
     * 是否开启车辆检测
     */
    private String car;

    private MediaRecognition bodyRecognition;

    private MediaRecognition carRecognition;

    private MediaRecognition petRecognition;

    private List<MediaTopkRecognition> topKRecognition;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public MediaRecognition getBodyRecognition() {
        if (bodyRecognition == null) {
            bodyRecognition = new MediaRecognition();
        }
        return bodyRecognition;
    }

    public void setBodyRecognition(MediaRecognition bodyRecognition) {

        this.bodyRecognition = bodyRecognition;
    }

    public MediaRecognition getCarRecognition() {
        if (carRecognition == null) {
            carRecognition = new MediaRecognition();
        }
        return carRecognition;
    }

    public void setCarRecognition(MediaRecognition carRecognition) {
        this.carRecognition = carRecognition;
    }

    public MediaRecognition getPetRecognition() {
        if (petRecognition == null) {
            petRecognition = new MediaRecognition();
        }
        return petRecognition;
    }

    public void setPetRecognition(MediaRecognition petRecognition) {
        this.petRecognition = petRecognition;
    }

    public List<MediaTopkRecognition> getTopKRecognition() {
        if (topKRecognition == null) {
            topKRecognition = new ArrayList<>();
        }
        return topKRecognition;
    }

    public void setTopKRecognition(List<MediaTopkRecognition> topKRecognition) {
        this.topKRecognition = topKRecognition;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VideoTargetRec{");
        sb.append("body='").append(body).append('\'');
        sb.append(", pet='").append(pet).append('\'');
        sb.append(", car='").append(car).append('\'');
        sb.append(", bodyRecognition=").append(bodyRecognition);
        sb.append(", carRecognition=").append(carRecognition);
        sb.append(", petRecognition=").append(petRecognition);
        sb.append('}');
        return sb.toString();
    }
}
