package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DeleteDatasetBindingRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DeleteDatasetBindingResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * 解绑存储桶与数据集 详情见https://cloud.tencent.com/document/product/460/106160
 */
public class DeleteDatasetBindingDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        deleteDatasetBinding(client);
    }

    /**
     * deleteDatasetBinding 解绑数据集和对象存储（COS）Bucket ，解绑会导致 COS Bucket新增的变更不会同步到数据集，请谨慎操作。
     * 该接口属于 DELETE 请求。
     */
    public static void deleteDatasetBinding(COSClient client) {
        DeleteDatasetBindingRequest request = new DeleteDatasetBindingRequest();
        request.setBucketName("demo-1234567890");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置资源标识字段，表示需要与数据集绑定的资源，当前仅支持COS存储桶，字段规则：cos://<BucketName>，其中BucketName表示COS存储桶名称，例如：cos://examplebucket-1250000000;是否必传：是
        request.setURI("cos://examplebucket-1250000000");

        DeleteDatasetBindingResponse response = client.deleteDatasetBinding(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
