package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Candidates {
    @XStreamAlias("PersonId")
    private String personId;

    @XStreamAlias("FaceId")
    private String faceId;

    @XStreamAlias("Score")
    private Integer score;

    @XStreamAlias("PersonName")
    private String personName;

    @XStreamAlias("Gender")
    private String gender;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
