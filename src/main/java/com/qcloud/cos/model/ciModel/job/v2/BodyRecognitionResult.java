    package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

public class BodyRecognitionResult {
    /**
     * 混合置信度
     */
    @XStreamAlias("FusionScore")
    private String fusionScore;

    /**
     * 识别结果
     */
    @XStreamImplicit(itemFieldName = "ResultDetails")
    private List<ResultDetails> resultDetails;

    public String getFusionScore() {
        return fusionScore;
    }

    public void setFusionScore(String fusionScore) {
        this.fusionScore = fusionScore;
    }

    public List<ResultDetails> getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(List<ResultDetails> resultDetails) {
        this.resultDetails = resultDetails;
    }
}
