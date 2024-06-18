package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.UpdateFileMetaIndexRequest;
import com.qcloud.cos.model.ciModel.metaInsight.UpdateFileMetaIndexResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 更新元数据索引 详情见https://cloud.tencent.com/document/product/460/106162
 */
public class UpdateFileMetaIndexDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        updateFileMetaIndex(client);
    }

    /**
     * updateFileMetaIndex 更新数据集内已索引的一个文件的部分元数据。

并非所有的元数据都允许您自定义更新，在您发起更新请求时需要填写数据集，默认会根据该数据集的算子进行元数据重新提取并更新已存在的索引，此外您也可以更新部分自定义的元数据索引，如CustomTags、CustomId等字段，具体请参考请求参数一节。
     * 该接口属于 PUT 请求。
     */
    public static void updateFileMetaIndex(COSClient client) {
        UpdateFileMetaIndexRequest request = new UpdateFileMetaIndexRequest();
        request.setBucketName("demo-1234567890");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test001");
        // 设置元数据索引结果（以回调形式发送至您的回调地址，支持以 http:// 或者 https:// 开头的地址，例如： http://www.callback.com;是否必传：是
        request.setCallback("http://www.callback.com");

        UpdateFileMetaIndexResponse response = client.updateFileMetaIndex(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
