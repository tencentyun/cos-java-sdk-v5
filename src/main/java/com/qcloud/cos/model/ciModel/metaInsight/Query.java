package com.qcloud.cos.model.ciModel.metaInsight;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("Query")
public class Query {

    /**
     *操作运算符。枚举值： not：逻辑非。 or：逻辑或。 and：逻辑与。 lt：小于。 lte：小于等于。 gt：大于。 gte：大于等于。 eq：等于。 exist：存在性查询。 prefix：前缀查询。 match-phrase：字符串匹配查询。 nested：字段为数组时，其中同一对象内逻辑条件查询。
     */
    @XStreamAlias("Operation")
    private String operation;

    /**
     *子查询的结构体。 只有当Operations为逻辑运算符（and、or、not或nested）时，才能设置子查询条件。 在逻辑运算符为and/or/not时，其SubQueries内描述的所有条件需符合父级设置的and/or/not逻辑关系。 在逻辑运算符为nested时，其父级的Field必须为一个数组类的字段（如：Labels）。 子查询条件SubQueries组的Operation必须为and/or/not中的一个或多个，其Field必须为父级Field的子属性。
     */
    @XStreamImplicit(itemFieldName = "SubQueries")
    private List<SubQueries> subQueries;

    /**
     *字段名称。关于支持的字段，请参考字段和操作符的支持列表。
     */
    @XStreamAlias("Field")
    private String field;

    /**
     *查询的字段值。当Operations为逻辑运算符（and、or、not或nested）时，该字段无效。
     */
    @XStreamAlias("Value")
    private String value;

    public String getOperation() { return operation; }

    public void setOperation(String operation) { this.operation = operation; }

    public List<SubQueries> getSubQueries() { return subQueries; }

    public void setSubQueries(List<SubQueries> subQueries) { this.subQueries = subQueries; }

    public String getField() { return field; }

    public void setField(String field) { this.field = field; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }



}
