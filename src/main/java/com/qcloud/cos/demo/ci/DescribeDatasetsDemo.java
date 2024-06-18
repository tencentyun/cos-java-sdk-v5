package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetsRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetsResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 列出数据集 详情见https://cloud.tencent.com/document/product/460/106158
 */
public class DescribeDatasetsDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeDatasets(client);
    }

    /**
     * describeDatasets 获取数据集（Dataset）列表。
     * 该接口属于 GET 请求。
     */
    public static void describeDatasets(COSClient client) {
        DescribeDatasetsRequest request = new DescribeDatasetsRequest();
        request.setBucketName("demo-1234567890");
        request.setMaxresults(100);// 设置本次返回数据集的最大个数，取值范围为0~200。不设置此参数或者设置为0时，则默认值为100。
        request.setNexttoken("下一页");// 设置翻页标记。当文件总数大于设置的MaxResults时，用于翻页的Token。从NextToken开始按字典序返回文件信息列表。填写上次查询返回的值，首次使用时填写为空。
        request.setPrefix("数据集前缀");// 设置数据集名称前缀。

        DescribeDatasetsResponse response = client.describeDatasets(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
