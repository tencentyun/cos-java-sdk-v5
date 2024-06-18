package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetSimpleQueryRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetSimpleQueryResponse;
import com.qcloud.cos.utils.Jackson;


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
        request.setBucketName("demo-1234567890");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置返回文件元数据的最大个数，取值范围为0200。 使用聚合参数时，该值表示返回分组的最大个数，取值范围为02000。 不设置此参数或者设置为0时，则取默认值100。;是否必传：否
        request.setMaxResults(100);
        // 设置排序字段列表。请参考字段和操作符的支持列表。 多个排序字段可使用半角逗号（,）分隔，例如：Size,Filename。 最多可设置5个排序字段。 排序字段顺序即为排序优先级顺序。;是否必传：是
        request.setSort("CustomId");
        // 设置排序字段的排序方式。取值如下： asc：升序； desc（默认）：降序。 多个排序方式可使用半角逗号（,）分隔，例如：asc,desc。 排序方式不可多于排序字段，即参数Order的元素数量需小于等于参数Sort的元素数量。例如Sort取值为Size,Filename时，Order可取值为asc,desc或asc。 排序方式少于排序字段时，未排序的字段默认取值asc。例如Sort取值为Size,Filename，Order取值为asc时，Filename默认排序方式为asc，即升序排列;是否必传：是
        request.setOrder("desc");

        DatasetSimpleQueryResponse response = client.datasetSimpleQuery(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
