package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetBindingRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetBindingResponse;
import com.qcloud.cos.utils.Jackson;


/**
 * 查询数据集与存储桶的绑定关系 详情见https://cloud.tencent.com/document/product/460/106485
 */
public class DescribeDatasetBindingDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeDatasetBinding(client);
    }

    /**
     * describeDatasetBinding 查询数据集和对象存储（COS）Bucket 绑定关系列表。
     * 该接口属于 GET 请求。
     */
    public static void describeDatasetBinding(COSClient client) {
        DescribeDatasetBindingRequest request = new DescribeDatasetBindingRequest();
        request.setBucketName("demo-1234567890");
        request.setDatasetname("数据集名称");// 设置数据集名称，同一个账户下唯一。
        request.setUri("uri");// 设置资源标识字段，表示需要与数据集绑定的资源，当前仅支持COS存储桶，字段规则：cos://，其中BucketName表示COS存储桶名称，例如（需要进行urlencode）：cos%3A%2F%2Fexample-125000

        DescribeDatasetBindingResponse response = client.describeDatasetBinding(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
