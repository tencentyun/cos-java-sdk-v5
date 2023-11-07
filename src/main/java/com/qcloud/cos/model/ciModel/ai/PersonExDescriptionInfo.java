package com.qcloud.cos.model.ciModel.ai;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PersonExDescriptionInfo {

    @XStreamAlias("PersonExDescriptionIndex")
    private Integer personExDescriptionIndex;
    @XStreamAlias("PersonExDescription")
    private String personExDescription;

    public Integer getPersonExDescriptionIndex() {
        return personExDescriptionIndex;
    }

    public void setPersonExDescriptionIndex(Integer personExDescriptionIndex) {
        this.personExDescriptionIndex = personExDescriptionIndex;
    }

    public String getPersonExDescription() {
        return personExDescription;
    }

    public void setPersonExDescription(String personExDescription) {
        this.personExDescription = personExDescription;
    }
}
