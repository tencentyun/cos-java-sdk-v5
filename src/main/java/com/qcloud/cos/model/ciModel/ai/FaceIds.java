package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FaceIds {
    /**
     *待删除的人脸ID;是否必传：是
     */
    @XStreamAlias("FaceId")
    private String faceId;

    public String getFaceId() { return faceId; }

    public void setFaceId(String faceId) { this.faceId = faceId; }
}
