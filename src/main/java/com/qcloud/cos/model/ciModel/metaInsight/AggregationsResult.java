package com.qcloud.cos.model.ciModel.metaInsight;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.util.List;

@XStreamAlias("AggregationsResult")
public class AggregationsResult {

    /**
     * 聚合字段的聚合操作符。      
     */
    @XStreamAlias("Operation")
    private String operation;

    /**
     * 聚合的统计结果。     
     */
    @XStreamAlias("Value")
    private float value;

    /**
     * 分组聚合的结果列表。仅在请求的Aggregations中存在group类型的Operation时才会返回。
     */
    @XStreamImplicit(itemFieldName = "Groups")
    private List<Groups> groups;

    /**
     *聚合字段名称。
     */
    @XStreamAlias("Field")
    private String field;

    public String getOperation() { return operation; }

    public void setOperation(String operation) { this.operation = operation; }

    public float getValue() { return value; }

    public void setValue(float value) { this.value = value; }

    public List<Groups> getGroups() { return groups; }

    public void setGroups(List<Groups> groups) { this.groups = groups; }

    public String getField() { return field; }

    public void setField(String field) { this.field = field; }



}
