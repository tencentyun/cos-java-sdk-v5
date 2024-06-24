package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class DescribeDatasetsRequest extends CIServiceRequest {

    /**
     *本次返回数据集的最大个数，取值范围为0~200。不设置此参数或者设置为0时，则默认值为100。;是否必传：否
     */
    private Integer maxresults;

    /**
     *翻页标记。当文件总数大于设置的MaxResults时，用于翻页的Token。从NextToken开始按字典序返回文件信息列表。填写上次查询返回的值，首次使用时填写为空。;是否必传：否
     */
    private String nexttoken;

    /**
     *数据集名称前缀。;是否必传：否
     */
    private String prefix;

    public Integer getMaxresults() { return maxresults; }

    public void setMaxresults(Integer maxresults) { this.maxresults = maxresults; }

    public String getNexttoken() { return nexttoken; }

    public void setNexttoken(String nexttoken) { this.nexttoken = nexttoken; }

    public String getPrefix() { return prefix; }

    public void setPrefix(String prefix) { this.prefix = prefix; }



}
