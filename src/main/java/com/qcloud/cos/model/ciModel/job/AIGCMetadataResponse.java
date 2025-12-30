package com.qcloud.cos.model.ciModel.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.model.CosServiceResult;

public class AIGCMetadataResponse extends CosServiceResult {

    @JsonProperty("AIGC")
    private AigcMetadata AIGC;

    public AigcMetadata getAigc() {
        if (AIGC == null) {
            AIGC = new AigcMetadata();
        }
        return AIGC;
    }

    public void setAigc(AigcMetadata aigc) {
        this.AIGC = aigc;
    }

    public AIGCMetadataResponse() {}

    @Override
    public String toString() {
        return "AigcMetadataResponse{" +
                "aigc=" + AIGC +
                '}';
    }

}
