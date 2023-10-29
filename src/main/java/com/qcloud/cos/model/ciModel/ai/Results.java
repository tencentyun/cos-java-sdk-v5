package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class Results {
    @XStreamImplicit(itemFieldName = "Candidates")
    private List<Candidates> candidates;

    @XStreamAlias("FaceRect")
    private FaceRect faceRect;

    @XStreamAlias("RetCode")
    private String retCode;

    public List<Candidates> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidates> candidates) {
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
