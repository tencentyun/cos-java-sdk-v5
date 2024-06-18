package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


public class DescribeDatasetsResponse extends CiServiceResult {

    /**
     *请求ID
     */
    private String requestId;

    /**
     *数据集信息
     */
    private List<Dataset> datasets;

    /**
     *翻页标记。当任务列表总数大于设置的MaxResults时，用于翻页的Token。符合条件的任务列表未全部返回时，此参数才有值。下一次列出任务列表时将此值作为NextToken传入，将后续的任务列表返回。
     */
    private String nextToken;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public List<Dataset> getDatasets() { return datasets; }

    public void setDatasets(List<Dataset> datasets) { this.datasets = datasets; }

    public String getNextToken() { return nextToken; }

    public void setNextToken(String nextToken) { this.nextToken = nextToken; }

    
}
