package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DeleteFileMetaIndexRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DeleteFileMetaIndexResponse;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 删除元数据索引 详情见https://cloud.tencent.com/document/product/460/106163
 */
public class DeleteFileMetaIndexDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        deleteFileMetaIndex(client);
    }

    /**
     * deleteFileMetaIndex 从数据集内删除一个文件的元信息。无论该文件的元信息是否在数据集内存在，均会返回删除成功。


     * 该接口属于 DELETE 请求。
     */
    public static void deleteFileMetaIndex(COSClient client) {
        DeleteFileMetaIndexRequest request = new DeleteFileMetaIndexRequest();
        request.setBucketName("demo-1234567890");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置资源标识字段，表示需要建立索引的文件地址。;是否必传：是
        request.setURI("cos://examplebucket-1250000000/test.jpg");

        DeleteFileMetaIndexResponse response = client.deleteFileMetaIndex(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
