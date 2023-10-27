package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Results {
    @XStreamAlias("Candidates")
    private Candidates candidates;

    @XStreamAlias("FaceRect")
    private FaceRect faceRect;

    @XStreamAlias("RetCode")
    private String retCode;

    public Candidates getCandidates() {
        return candidates;
    }

    public void setCandidates(Candidates candidates) {
        this.candidates = candidates;
    }

    public FaceRect getFaceRect() {
        return faceRect;
    }

    public void setFaceRect(FaceRect faceRect) {
        this.faceRect = faceRect;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }


}
