package com.qcloud.cos.model.ciModel.job;

public class QualityEstimate {
    private VqaPlusResult vqaPlusResult;
    private String score;

    public VqaPlusResult getVqaPlusResult() {
        if (vqaPlusResult == null) {
            vqaPlusResult = new VqaPlusResult();
        }
        return vqaPlusResult;
    }

    public void setVqaPlusResult(VqaPlusResult vqaPlusResult) {
        this.vqaPlusResult = vqaPlusResult;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
