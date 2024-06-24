package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.CreateFileMetaIndexRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateFileMetaIndexResponse;
import com.qcloud.cos.model.ciModel.metaInsight.File;
import com.qcloud.cos.utils.Jackson;


/**
 * 创建元数据索引 详情见https://cloud.tencent.com/document/product/460/106022
 */
public class CreateFileMetaIndexDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createFileMetaIndex(client);
    }

    /**
     * createFileMetaIndex 提取一个 COS 文件的元数据，在数据集中建立索引。会根据数据集中的算子提取不同的元数据建立索引，也支持建立自定义的元数据索引。
     * 该接口属于 POST 请求。
     */
    public static void createFileMetaIndex(COSClient client) {
        CreateFileMetaIndexRequest request = new CreateFileMetaIndexRequest();
        request.setAppId("1234567890");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test001");
        File file = new File();
        file.setURI("cos://<BucketName>/<ObjectKey>");
        request.setFile(file);
        CreateFileMetaIndexResponse response = client.createFileMetaIndex(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
