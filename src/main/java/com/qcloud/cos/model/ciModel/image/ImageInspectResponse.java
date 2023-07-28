package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.model.CosServiceResult;


public class ImageInspectResponse extends CosServiceResult {
   private String picSize;
   private String picType;
   private boolean suspicious;
   private String suspiciousBeginByte;
   private String suspiciousEndByte;
   private String suspiciousSize;
   private String suspiciousType;

    public String getPicSize() {
        return picSize;
    }

    public void setPicSize(String picSize) {
        this.picSize = picSize;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public boolean isSuspicious() {
        return suspicious;
    }

    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }

    public String getSuspiciousBeginByte() {
        return suspiciousBeginByte;
    }

    public void setSuspiciousBeginByte(String suspiciousBeginByte) {
        this.suspiciousBeginByte = suspiciousBeginByte;
    }

    public String getSuspiciousEndByte() {
        return suspiciousEndByte;
    }

    public void setSuspiciousEndByte(String suspiciousEndByte) {
        this.suspiciousEndByte = suspiciousEndByte;
    }

    public String getSuspiciousSize() {
        return suspiciousSize;
    }

    public void setSuspiciousSize(String suspiciousSize) {
        this.suspiciousSize = suspiciousSize;
    }

    public String getSuspiciousType() {
        return suspiciousType;
    }

    public void setSuspiciousType(String suspiciousType) {
        this.suspiciousType = suspiciousType;
    }
}
