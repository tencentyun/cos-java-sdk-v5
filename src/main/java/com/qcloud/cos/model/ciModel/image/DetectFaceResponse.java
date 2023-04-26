package com.qcloud.cos.model.ciModel.image;


import java.util.ArrayList;
import java.util.List;

public class DetectFaceResponse {
    private String imageWidth;
    private String imageHeight;
    private String faceModelVersion;
    private String requestId;
    private List<FaceInfo> faceInfos;

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getFaceModelVersion() {
        return faceModelVersion;
    }

    public void setFaceModelVersion(String faceModelVersion) {
        this.faceModelVersion = faceModelVersion;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<FaceInfo> getFaceInfo() {
        if (faceInfos == null) {
            faceInfos = new ArrayList<>();
        }
        return faceInfos;
    }

    public void setFaceInfo(List<FaceInfo> faceInfo) {
        this.faceInfos = faceInfo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DetectFaceResponse{");
        sb.append("imageWidth='").append(imageWidth).append('\'');
        sb.append(", imageHeight='").append(imageHeight).append('\'');
        sb.append(", faceModelVersion='").append(faceModelVersion).append('\'');
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append(", faceInfos=").append(faceInfos);
        sb.append('}');
        return sb.toString();
    }
}
