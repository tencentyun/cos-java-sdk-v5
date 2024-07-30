package com.qcloud.cos.model.ciModel.persistence;

import com.qcloud.cos.model.ciModel.ai.FaceRect;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class FaceDetailInfos {
    @XStreamImplicit(itemFieldName = "FaceRect")
    List<FaceRect> faceRectList;

    public List<FaceRect> getFaceRectList() {
        return faceRectList;
    }

    public void setFaceRectList(List<FaceRect> faceRectList) {
        this.faceRectList = faceRectList;
    }
}
