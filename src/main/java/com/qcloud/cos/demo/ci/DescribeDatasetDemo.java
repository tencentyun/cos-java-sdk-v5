package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeDatasetResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 查询数据集 详情见https://cloud.tencent.com/document/product/460/106155
 */
public class DescribeDatasetDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeDataset(client);
    }

    /**
     * describeDataset 查询一个数据集（Dataset）信息。
     * 该接口属于 GET 请求。
     */
    public static void describeDataset(COSClient client) {
        DescribeDatasetRequest request = new DescribeDatasetRequest();
        request.setBucketName("demo-1234567890");
        request.setDatasetname("数据集名称");// 设置数据集名称，同一个账户下唯一。

        DescribeDatasetResponse response = client.describeDataset(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
