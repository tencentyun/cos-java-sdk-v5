package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.List;


@XStreamAlias("Request")
public class CreatePersonRequest extends CosServiceRequest {

    @XStreamOmitField
    private String objectKey;
    @XStreamOmitField
    private String bucketName;
    @XStreamAlias("PersonId")
    private String personId;
    @XStreamAlias("PersonName")
    private String personName;
    @XStreamAlias("UniquePersonControl")
    private Integer uniquePersonControl;
    @XStreamAlias("QualityControl")
    private Integer qualityControl;
    @XStreamAlias("NeedRotateDetection")
    private Integer needRotateDetection;

    @XStreamImplicit(itemFieldName = "PersonExDescriptionInfos")
    private List<PersonExDescriptionInfo> personExDescriptionInfos;

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

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getUniquePersonControl() {
        return uniquePersonControl;
    }

    public void setUniquePersonControl(Integer uniquePersonControl) {
        this.uniquePersonControl = uniquePersonControl;
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

    public List<PersonExDescriptionInfo> getPersonExDescriptionInfos() {
        return personExDescriptionInfos;
    }

    public void setPersonExDescriptionInfos(List<PersonExDescriptionInfo> personExDescriptionInfos) {
        this.personExDescriptionInfos = personExDescriptionInfos;
    }
}
