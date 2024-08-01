package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class VideoTargetRecResult {
    /**
     * 脱敏标记
     */
    @XStreamAlias("Sensitive")
    private String sensitive;

    /**
     * 人体识别结果
     */
    @XStreamImplicit(itemFieldName = "BodyRecognitionResult")
    private List<BodyRecognitionResult> bodyRecognitionResult;

    /**
     * 宠物识别结果
     */
    @XStreamImplicit(itemFieldName = "PetRecognitionResult")
    private List<PetRecognitionResult> petRecognitionResult;

    /**
     * 车辆识别结果
     */
    @XStreamImplicit(itemFieldName = "CarRecognitionResult")
    private List<CarRecognitionResult> carRecognitionResult;

    /**
     * 人脸识别结果
     */
    @XStreamImplicit(itemFieldName = "FaceRecognitionResult")
    private List<FaceRecognitionResult> faceRecognitionResult;

    /**
     * 车牌识别结果
     */
    @XStreamImplicit(itemFieldName = "LicenseRecognitionResult")
    private List<LicenseRecognitionResult> licenseRecognitionResult;

    public String getSensitive() {
        return sensitive;
    }

    public void setSensitive(String sensitive) {
        this.sensitive = sensitive;
    }

    public List<BodyRecognitionResult> getBodyRecognitionResult() {
        return bodyRecognitionResult;
    }

    public void setBodyRecognitionResult(List<BodyRecognitionResult> bodyRecognitionResult) {
        this.bodyRecognitionResult = bodyRecognitionResult;
    }

    public List<PetRecognitionResult> getPetRecognitionResult() {
        return petRecognitionResult;
    }

    public void setPetRecognitionResult(List<PetRecognitionResult> petRecognitionResult) {
        this.petRecognitionResult = petRecognitionResult;
    }

    public List<CarRecognitionResult> getCarRecognitionResult() {
        return carRecognitionResult;
    }

    public void setCarRecognitionResult(List<CarRecognitionResult> carRecognitionResult) {
        this.carRecognitionResult = carRecognitionResult;
    }

    public List<FaceRecognitionResult> getFaceRecognitionResult() {
        return faceRecognitionResult;
    }

    public void setFaceRecognitionResult(List<FaceRecognitionResult> faceRecognitionResult) {
        this.faceRecognitionResult = faceRecognitionResult;
    }

    public List<LicenseRecognitionResult> getLicenseRecognitionResult() {
        return licenseRecognitionResult;
    }

    public void setLicenseRecognitionResult(List<LicenseRecognitionResult> licenseRecognitionResult) {
        this.licenseRecognitionResult = licenseRecognitionResult;
    }
}

