package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetBindingsRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetBindingsResponse;
import com.qcloud.cos.utils.Jackson;


/**
 * 查询绑定关系列表 详情见https://cloud.tencent.com/document/product/460/106161
 */
public class DescribeDatasetBindingsDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeDatasetBindings(client);
    }

    /**
     * describeDatasetBindings 查询数据集和对象存储（COS）Bucket 绑定关系列表。
     * 该接口属于 GET 请求。
     */
    public static void describeDatasetBindings(COSClient client) {
        DescribeDatasetBindingsRequest request = new DescribeDatasetBindingsRequest();
        request.setBucketName("demo-1234567890");
        request.setDatasetname("数据集名称");// 设置数据集名称，同一个账户下唯一。
        request.setMaxresults(10);// 设置返回绑定关系的最大个数，取值范围为0~200。不设置此参数或者设置为0时，则默认值为100。
        request.setNexttoken("token");// 设置当绑定关系总数大于设置的MaxResults时，用于翻页的token。从NextToken开始按字典序返回绑定关系信息列表。第一次调用此接口时，设置为空。

        DescribeDatasetBindingsResponse response = client.describeDatasetBindings(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
