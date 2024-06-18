package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeFileMetaIndexRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DescribeFileMetaIndexResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 查询元数据索引 详情见https://cloud.tencent.com/document/product/460/106164
 */
public class DescribeFileMetaIndexDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeFileMetaIndex(client);
    }

    /**
     * describeFileMetaIndex 获取数据集内已完成索引的一个文件的元数据。
     * 该接口属于 GET 请求。
     */
    public static void describeFileMetaIndex(COSClient client) {
        DescribeFileMetaIndexRequest request = new DescribeFileMetaIndexRequest();
        request.setBucketName("demo-1234567890");

        DescribeFileMetaIndexResponse response = client.describeFileMetaIndex(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
