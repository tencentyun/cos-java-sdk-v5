package com.qcloud.cos.model.ciModel.auditing;


public class LibResult {
    private String imageId;
    private String score;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LibResult{");
        sb.append("imageId='").append(imageId).append('\'');
        sb.append(", score='").append(score).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
