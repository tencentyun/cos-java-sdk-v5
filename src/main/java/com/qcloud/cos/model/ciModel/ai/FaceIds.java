package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class FaceIds {
    /**
     *待删除的人脸ID;是否必传：是
     */
    @XStreamImplicit(itemFieldName = "FaceId")
    private List<String> faceId;

    public List<String> getFaceId() {
        return faceId;
    }

    public void setFaceId(List<String> faceId) {
        this.faceId = faceId;
    }
}
