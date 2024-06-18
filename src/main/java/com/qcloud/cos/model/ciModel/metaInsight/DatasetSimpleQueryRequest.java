package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class DatasetSimpleQueryRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *简单查询参数条件，可自嵌套。;是否必传：是
     */
    private Query query;

    /**
     *返回文件元数据的最大个数，取值范围为0200。 使用聚合参数时，该值表示返回分组的最大个数，取值范围为02000。 不设置此参数或者设置为0时，则取默认值100。;是否必传：否
     */
    private Integer maxResults;

    /**
     *当绑定关系总数大于设置的MaxResults时，用于翻页的token。从NextToken开始按字典序返回绑定关系信息列表。第一次调用此接口时，设置为空。;是否必传：否
     */
    private String nextToken;

    /**
     *排序字段列表。请参考字段和操作符的支持列表。 多个排序字段可使用半角逗号（,）分隔，例如：Size,Filename。 最多可设置5个排序字段。 排序字段顺序即为排序优先级顺序。;是否必传：是
     */
    private String sort;

    /**
     *排序字段的排序方式。取值如下： asc：升序； desc（默认）：降序。 多个排序方式可使用半角逗号（,）分隔，例如：asc,desc。 排序方式不可多于排序字段，即参数Order的元素数量需小于等于参数Sort的元素数量。例如Sort取值为Size,Filename时，Order可取值为asc,desc或asc。 排序方式少于排序字段时，未排序的字段默认取值asc。例如Sort取值为Size,Filename，Order取值为asc时，Filename默认排序方式为asc，即升序排列;是否必传：是
     */
    private String order;

    /**
     *聚合字段信息列表。 当您使用聚合查询时，仅返回聚合结果，不再返回匹配到的元信息列表。;是否必传：是
     */
    private Aggregations aggregations;

    /**
     *仅返回特定字段的值，而不是全部已有的元信息字段。可用于降低返回的结构体大小。不填或留空则返回所有字段。;是否必传：是
     */
    private String withFields;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public Query getQuery() { 
        if(query == null){
            query = new Query(); 
        }
        return query;
    }

    public void setQuery(Query query) { this.query = query; }

    public Integer getMaxResults() { return maxResults; }

    public void setMaxResults(Integer maxResults) { this.maxResults = maxResults; }

    public String getNextToken() { return nextToken; }

    public void setNextToken(String nextToken) { this.nextToken = nextToken; }

    public String getSort() { return sort; }

    public void setSort(String sort) { this.sort = sort; }

    public String getOrder() { return order; }

    public void setOrder(String order) { this.order = order; }

    public Aggregations getAggregations() { 
        if(aggregations == null){
            aggregations = new Aggregations(); 
        }
        return aggregations;
    }

    public void setAggregations(Aggregations aggregations) { this.aggregations = aggregations; }

    public String getWithFields() { return withFields; }

    public void setWithFields(String withFields) { this.withFields = withFields; }

    


}
