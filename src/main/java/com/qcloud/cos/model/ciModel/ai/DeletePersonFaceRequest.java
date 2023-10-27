package com.qcloud.cos.model.ciModel.ai;

import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;


@XStreamAlias("Request")
public class DeletePersonFaceRequest extends CosServiceRequest {

    @XStreamOmitField
    private String objectKey;

    /**
     *人员ID，取值为创建人员接口中的PersonId;是否必传：是
     */
    @XStreamAlias("PersonId")
    private String personId;

    /**
     *待删除的人脸ID数组;是否必传：是
     */
    @XStreamAlias("FaceIds")
    private FaceIds faceIds;

    private String bucketName;

    public String getObjectKey() { return objectKey; }

    public void setObjectKey(String objectKey) { this.objectKey = objectKey; }

    public String getPersonId() { return personId; }

    public void setPersonId(String personId) { this.personId = personId; }

    public FaceIds getFaceIds() { 
        if(faceIds == null){
            faceIds = new FaceIds(); 
        }
        return faceIds;
    }

    public void setFaceIds(FaceIds faceIds) { this.faceIds = faceIds; }


    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
