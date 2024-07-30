package com.qcloud.cos.model.ciModel.persistence;

import com.qcloud.cos.model.CosServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("UploadResult")
public class CIUploadResult extends CosServiceResult {
    @XStreamAlias("OriginalInfo")
    private OriginalInfo originalInfo;
    @XStreamAlias("ProcessResults")
    private ProcessResults processResults;
    @XStreamAlias("FaceDetailInfos")
    private FaceDetailInfos faceDetailInfos;
    @XStreamAlias("PlateDetailInfos")
    private PlateDetailInfos plateDetailInfos;
    @XStreamAlias("Face")
    private FailedMessage face;
    @XStreamAlias("Plate")
    private FailedMessage plate;

    public OriginalInfo getOriginalInfo() {
        return originalInfo;
    }

    public void setOriginalInfo(OriginalInfo originalInfo) {
        this.originalInfo = originalInfo;
    }

    public ProcessResults getProcessResults() {
        return processResults;
    }

    public void setProcessResults(ProcessResults processResults) {
        this.processResults = processResults;
    }


    public FailedMessage getFace() {
        return face;
    }

    public void setFace(FailedMessage face) {
        this.face = face;
    }

    public FailedMessage getPlate() {
        return plate;
    }

    public void setPlate(FailedMessage plate) {
        this.plate = plate;
    }

    public FaceDetailInfos getFaceDetailInfos() {
        return faceDetailInfos;
    }

    public void setFaceDetailInfos(FaceDetailInfos faceDetailInfos) {
        this.faceDetailInfos = faceDetailInfos;
    }

    public PlateDetailInfos getPlateDetailInfos() {
        return plateDetailInfos;
    }

    public void setPlateDetailInfos(PlateDetailInfos plateDetailInfos) {
        this.plateDetailInfos = plateDetailInfos;
    }
}
