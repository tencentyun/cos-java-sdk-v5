package com.qcloud.cos.model.ciModel.metaInsight;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("SubQueries")
public class SubQueries {

    /**
     *查询的字段值。当Operations为逻辑运算符（and、or、not或nested）时，该字段无效。
     */
    @XStreamAlias("Value")
    private String value;

    /**
     * 操作运算符。枚举值：not：逻辑非。or：逻辑或。and：逻辑与。lt：小于。lte：小于等于。gt：大于。gte：大于等于。eq：等于。exist：存在性查询。prefix：前缀查询。match-phrase：字符串匹配查询。nested：字段为数组时，其中同一对象内逻辑条件查询。 
     */
    @XStreamAlias("Operation")
    private String operation;

    /**
     *字段名称。关于支持的字段，请参考字段和操作符的支持列表。
     */
    @XStreamAlias("Field")
    private String field;

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public String getOperation() { return operation; }

    public void setOperation(String operation) { this.operation = operation; }

    public String getField() { return field; }

    public void setField(String field) { this.field = field; }



}
