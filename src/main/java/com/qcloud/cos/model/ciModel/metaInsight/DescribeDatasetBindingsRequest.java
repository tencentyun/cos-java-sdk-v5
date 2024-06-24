package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class DescribeDatasetBindingsRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：否
     */
    private String datasetname;

    /**
     *返回绑定关系的最大个数，取值范围为0~200。不设置此参数或者设置为0时，则默认值为100。;是否必传：否
     */
    private Integer maxresults;

    /**
     *当绑定关系总数大于设置的MaxResults时，用于翻页的token。从NextToken开始按字典序返回绑定关系信息列表。第一次调用此接口时，设置为空。;是否必传：是
     */
    private String nexttoken;

    public String getDatasetname() { return datasetname; }

    public void setDatasetname(String datasetname) { this.datasetname = datasetname; }

    public Integer getMaxresults() { return maxresults; }

    public void setMaxresults(Integer maxresults) { this.maxresults = maxresults; }

    public String getNexttoken() { return nexttoken; }

    public void setNexttoken(String nexttoken) { this.nexttoken = nexttoken; }



}
