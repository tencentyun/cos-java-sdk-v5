package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;


public class DescribeDatasetBindingsResponse extends CiServiceResult {

    /**
     *请求ID
     */
    private String requestId;

    /**
     *当绑定关系总数大于设置的MaxResults时，用于翻页的token。下一次列出绑定关系信息时以此值为NextToken，将未返回的结果返回。当绑定关系未全部返回时，此参数才有值。
     */
    private String nextToken;

    /**
     *数据集和 COS Bucket 绑定关系信息的列表。
     */
    private List<Binding> bindings;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public String getNextToken() { return nextToken; }

    public void setNextToken(String nextToken) { this.nextToken = nextToken; }

    public List<Binding> getBindings() { return bindings; }

    public void setBindings(List<Binding> bindings) { this.bindings = bindings; }

    
}
