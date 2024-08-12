package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Aggregations {

    /**
     * 聚合字段的操作符。枚举值：min：最小值。max：最大值。average：平均数sum：求和。count：计数。distinct：去重计数。group：分组计数，按照分组计数结果从高到低排序。 
     */
    @JsonProperty("Operation")
    private String operation;

    /**
     *字段名称。关于支持的字段，请参考字段和操作符的支持列表。
     */
    @JsonProperty("Field")
    private String field;

    public String getOperation() { return operation; }

    public void setOperation(String operation) { this.operation = operation; }

    public String getField() { return field; }

    public void setField(String field) { this.field = field; }



}
