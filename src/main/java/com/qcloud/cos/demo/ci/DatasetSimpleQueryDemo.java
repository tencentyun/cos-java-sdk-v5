package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetSimpleQueryRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetSimpleQueryResponse;
import com.qcloud.cos.model.ciModel.metaInsight.Query;
import com.qcloud.cos.model.ciModel.metaInsight.SubQueries;
import com.qcloud.cos.utils.CIJackson;
import com.qcloud.cos.utils.Jackson;

import java.util.ArrayList;
import java.util.List;


/**
 * 简单查询 详情见https://cloud.tencent.com/document/product/460/106375
 */
public class DatasetSimpleQueryDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        datasetSimpleQuery(client);
    }

    /**
     * datasetSimpleQuery 可以根据已提取的文件元数据（包含文件名、标签、路径、自定义标签、文本等字段）查询和统计数据集内文件，支持逻辑关系表达方式。
     * 该接口属于 POST 请求。
     */
    public static void datasetSimpleQuery(COSClient client) {
        DatasetSimpleQueryRequest request = new DatasetSimpleQueryRequest();
        request.setAppId("1251704708");
        request.setDatasetName("test");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        Query query = new Query();
        query.setOperation("and");
        List<SubQueries> subQueriesList = new ArrayList<>();
        SubQueries subQueries = new SubQueries();
        subQueries.setOperation("eq");
        subQueries.setField("ContentType");
        subQueries.setValue("image/jpeg");
        subQueriesList.add(subQueries);
        query.setSubQueries(subQueriesList);
        request.setQuery(query);

        request.setSort("CustomId");
        request.setOrder("desc");
        request.setMaxResults(100);
        DatasetSimpleQueryResponse response = client.datasetSimpleQuery(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
