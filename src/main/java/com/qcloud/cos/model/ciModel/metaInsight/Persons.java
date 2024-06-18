package com.qcloud.cos.model.ciModel.metaInsight;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Persons")
public class Persons {

    /**
     *自定义人物 ID。
     */
    @XStreamAlias("PersonId")
    private String personId;

    public String getPersonId() { return personId; }

    public void setPersonId(String personId) { this.personId = personId; }



}
