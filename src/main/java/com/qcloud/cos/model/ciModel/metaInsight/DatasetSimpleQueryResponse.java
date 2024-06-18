package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.model.CiServiceResult;
import java.util.List;


public class DatasetSimpleQueryResponse extends CiServiceResult {

    /**
     *请求ID
     */
    private String requestId;

    /**
     *文件信息列表。仅在请求的Aggregations为空时返回。
     */
    private List<File> files;

    /**
     *聚合字段信息列表。仅在请求的Aggregations不为空时返回。
     */
    private List<Aggregations> aggregations;

    /**
     *翻页标记。当文件总数大于设置的MaxResults时，用于翻页的Token。符合条件的文件信息未全部返回时，此参数才有值。下一次列出文件信息时将此值作为NextToken传入，将后续的文件信息返回。
     */
    private String nextToken;

    public String getRequestId() { return requestId; }

    public void setRequestId(String requestId) { this.requestId = requestId; }

    public List<File> getFiles() { return files; }

    public void setFiles(List<File> files) { this.files = files; }

    public List<Aggregations> getAggregations() { return aggregations; }

    public void setAggregations(List<Aggregations> aggregations) { this.aggregations = aggregations; }

    public String getNextToken() { return nextToken; }

    public void setNextToken(String nextToken) { this.nextToken = nextToken; }

    
}
