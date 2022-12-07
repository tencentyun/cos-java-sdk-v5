package com.qcloud.cos.model.ciModel.auditing;

public class Freeze {
    private String pornScore;
    private String politicsScore;
    private String terrorismScore;
    private String adsScore;

    public String getPornScore() {
        return pornScore;
    }

    public void setPornScore(String pornScore) {
        this.pornScore = pornScore;
    }

    public String getPoliticsScore() {
        return politicsScore;
    }

    public void setPoliticsScore(String politicsScore) {
        this.politicsScore = politicsScore;
    }

    public String getTerrorismScore() {
        return terrorismScore;
    }

    public void setTerrorismScore(String terrorismScore) {
        this.terrorismScore = terrorismScore;
    }

    public String getAdsScore() {
        return adsScore;
    }

    public void setAdsScore(String adsScore) {
        this.adsScore = adsScore;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Freeze{");
        sb.append("pornScore='").append(pornScore).append('\'');
        sb.append(", politicsScore='").append(politicsScore).append('\'');
        sb.append(", terrorismScore='").append(terrorismScore).append('\'');
        sb.append(", adsScore='").append(adsScore).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
