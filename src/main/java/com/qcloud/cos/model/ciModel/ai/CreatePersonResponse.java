package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("Response")
public class CreatePersonResponse  {

    @XStreamAlias("FaceId")
    private String faceId;

    @XStreamAlias("FaceRect")
    private FaceRect faceRect;

    @XStreamAlias("SimilarPersonId")
    private String similarPersonId;

    @XStreamAlias("RequestId")
    private String requestId;

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public FaceRect getFaceRect() {
        return faceRect;
    }

    public void setFaceRect(FaceRect faceRect) {
        this.faceRect = faceRect;
    }

    public String getSimilarPersonId() {
        return similarPersonId;
    }

    public void setSimilarPersonId(String similarPersonId) {
        this.similarPersonId = similarPersonId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
