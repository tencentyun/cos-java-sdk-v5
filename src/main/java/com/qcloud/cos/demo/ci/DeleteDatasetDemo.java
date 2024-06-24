package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DeleteDatasetRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DeleteDatasetResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * 删除数据集 详情见https://cloud.tencent.com/document/product/460/106157
 */
public class DeleteDatasetDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        deleteDataset(client);
    }

    /**
     * deleteDataset 删除一个数据集（Dataset）。
     * 该接口属于 DELETE 请求。
     */
    public static void deleteDataset(COSClient client) {
        DeleteDatasetRequest request = new DeleteDatasetRequest();
        request.setAppId("1234567890");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");

        DeleteDatasetResponse response = client.deleteDataset(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
