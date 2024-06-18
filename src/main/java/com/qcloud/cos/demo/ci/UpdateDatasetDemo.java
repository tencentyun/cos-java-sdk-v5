package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.UpdateDatasetRequest;
import com.qcloud.cos.model.ciModel.metaInsight.UpdateDatasetResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 更新数据集 详情见https://cloud.tencent.com/document/product/460/106156
 */
public class UpdateDatasetDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        updateDataset(client);
    }

    /**
     * updateDataset 更新一个数据集（Dataset）信息。
     * 该接口属于 PUT 请求。
     */
    public static void updateDataset(COSClient client) {
        UpdateDatasetRequest request = new UpdateDatasetRequest();
        request.setBucketName("demo-1234567890");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置数据集描述信息。长度为1~256个英文或中文字符，默认值为空。;是否必传：否
        request.setDescription("test");
        // 设置该参数表示模板，在建立元数据索引时，后端将根据模板来决定收集哪些元数据。每个模板都包含一个或多个算子，不同的算子表示不同的元数据。目前支持的模板： Official:Empty：默认为空的模板，表示不进行元数据的采集。 Official:COSBasicMeta：基础信息模板，包含COS文件基础元信息算子，表示采集cos文件的名称、类型、acl等基础元信息数据。;是否必传：否
        request.setTemplateId("Official:COSBasicMeta");

        UpdateDatasetResponse response = client.updateDataset(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
