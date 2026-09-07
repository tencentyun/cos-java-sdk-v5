package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class DescribeDatasetRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetname;

    /**
     * 该参数用于指定是否需要立即统计一次数据集中最新的文件数量、大小等数据，参数有效值如下，默认值为 false。
     * false：不立即统计，接口返回的文件总大小、数量等数据存在10分钟的延迟。
     * true：立即统计，接口返回的文件总大小、数量等统计数据为实时统计的，统计实时数据需要一定时间，请耐心等待。
     * 是否必传：否
     */
    private boolean statistics;

    public String getDatasetname() { return datasetname; }

    public void setDatasetname(String datasetname) { this.datasetname = datasetname; }

    public boolean getStatistics() { return statistics; }

    public void setStatistics(boolean statistics) { this.statistics = statistics; }



}