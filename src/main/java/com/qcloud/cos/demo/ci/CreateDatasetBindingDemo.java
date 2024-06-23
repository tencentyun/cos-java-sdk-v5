package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetBindingRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetBindingResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 绑定存储桶与数据集 详情见https://cloud.tencent.com/document/product/460/106159
 */
public class CreateDatasetBindingDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createDatasetBinding(client);
    }

    /**
     * createDatasetBinding 本文档介绍创建数据集（Dataset）和对象存储（COS）Bucket 的绑定关系
     * 绑定后将使用创建数据集时所指定算子对文件进行处理。
     * 绑定关系创建后，将对 COS 中新增的文件进行准实时的增量追踪扫描，使用创建数据集时所指定算子对文件进行处理，抽取文件元数据信息进行索引。
     * 通过此方式为文件建立索引后，您可以使用元数据查询API对元数据进行查询、管理和统计。
     * 该接口属于 POST 请求。
     */
    public static void createDatasetBinding(COSClient client) {
        CreateDatasetBindingRequest request = new CreateDatasetBindingRequest();
        request.setAppId("1251704708");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("mark");
        // 设置资源标识字段，表示需要与数据集绑定的资源，当前仅支持COS存储桶，字段规则：cos://<BucketName>，其中BucketName表示COS存储桶名称，例如：cos://examplebucket-1250000000;是否必传：是
        request.setURI("cos://markjrzhang-1251704708");

        CreateDatasetBindingResponse response = client.createDatasetBinding(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
