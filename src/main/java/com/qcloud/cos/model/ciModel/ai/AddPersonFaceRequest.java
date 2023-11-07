package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("Request")
public class AddPersonFaceRequest extends CosServiceRequest {

    @XStreamOmitField
    private String objectKey;
    @XStreamOmitField
    private String bucketName;
    @XStreamAlias("PersonId")
    private String personId;
    @XStreamAlias("PersonName")
    private String personName;
    @XStreamAlias("FaceMatchThreshold")
    private Float faceMatchThreshold;

    @XStreamAlias("QualityControl")
    private Integer qualityControl;

    @XStreamAlias("NeedRotateDetection")
    private Integer needRotateDetection;

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Float getFaceMatchThreshold() {
        return faceMatchThreshold;
    }

    public void setFaceMatchThreshold(Float faceMatchThreshold) {
        this.faceMatchThreshold = faceMatchThreshold;
    }

    public Integer getQualityControl() {
        return qualityControl;
    }

    public void setQualityControl(Integer qualityControl) {
        this.qualityControl = qualityControl;
    }

    public Integer getNeedRotateDetection() {
        return needRotateDetection;
    }

    public void setNeedRotateDetection(Integer needRotateDetection) {
        this.needRotateDetection = needRotateDetection;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
