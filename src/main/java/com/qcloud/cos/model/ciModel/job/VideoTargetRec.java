package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

public class VideoTargetRec {

    @XStreamAlias("ProcessType")
    private String processType;

    /**
     * 是否开启人体检测
     */
    @XStreamAlias("Body")
    private String body;
    /**
     * 是否开启宠物检测
     */
    @XStreamAlias("Pet")
    private String pet;
    /**
     * 是否开启车辆检测
     */
    @XStreamAlias("Car")
    private String car;
    @XStreamAlias("Face")
    private String face;
    @XStreamAlias("Version")
    private String version;
    @XStreamAlias("SnapshotFreq")
    private String snapshotFreq;
    @XStreamAlias("Plate")
    private String plate;
    @XStreamAlias("BodyRecognition")
    private MediaRecognition bodyRecognition;
    @XStreamAlias("CarRecognition")
    private MediaRecognition carRecognition;
    @XStreamAlias("PetRecognition")
    private MediaRecognition petRecognition;
    @XStreamAlias("FaceRecognition")
    private MediaRecognition faceRecognition;
    @XStreamAlias("TopKRecognition")
    private List<MediaTopkRecognition> topKRecognition;

    @XStreamAlias("TransTpl")
    private TransTpl transTpl;

    public TransTpl getTransTpl() {
        return transTpl;
    }

    public void setTransTpl(TransTpl transTpl) {
        this.transTpl = transTpl;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }


    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSnapshotFreq() {
        return snapshotFreq;
    }

    public void setSnapshotFreq(String snapshotFreq) {
        this.snapshotFreq = snapshotFreq;
    }

    public MediaRecognition getFaceRecognition() {
        return faceRecognition;
    }

    public void setFaceRecognition(MediaRecognition faceRecognition) {
        this.faceRecognition = faceRecognition;
    }


    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VideoTargetRec{");
        sb.append("processType='").append(processType).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append(", pet='").append(pet).append('\'');
        sb.append(", car='").append(car).append('\'');
        sb.append(", face='").append(face).append('\'');
        sb.append(", version='").append(version).append('\'');
        sb.append(", snapshotFreq='").append(snapshotFreq).append('\'');
        sb.append(", plate='").append(plate).append('\'');
        sb.append(", bodyRecognition=").append(bodyRecognition);
        sb.append(", carRecognition=").append(carRecognition);
        sb.append(", petRecognition=").append(petRecognition);
        sb.append(", faceRecognition=").append(faceRecognition);
        sb.append(", topKRecognition=").append(topKRecognition);
        sb.append(", transTpl=").append(transTpl);
        sb.append('}');
        return sb.toString();
    }
}
