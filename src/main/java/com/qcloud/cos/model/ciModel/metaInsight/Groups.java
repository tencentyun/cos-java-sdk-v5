package com.qcloud.cos.model.ciModel.metaInsight;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Groups")
public class Groups {

    /**
     * 分组聚合的总个数。      
     */
    @XStreamAlias("Count")
    private Integer count;

    /**
     *分组聚合的值。
     */
    @XStreamAlias("Value")
    private String value;

    public Integer getCount() { return count; }

    public void setCount(Integer count) { this.count = count; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }



}
