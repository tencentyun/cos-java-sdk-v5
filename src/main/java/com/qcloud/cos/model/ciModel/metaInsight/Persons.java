package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Persons {

    /**
     *自定义人物 ID。
     */
    @JsonProperty("PersonId")
    private String personId;

    public String getPersonId() { return personId; }

    public void setPersonId(String personId) { this.personId = personId; }



}
