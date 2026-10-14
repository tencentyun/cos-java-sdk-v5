package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.IngestStatusRequest;
import com.qcloud.cos.model.ciModel.metaInsight.IngestStatusResponse;
import com.qcloud.cos.utils.Jackson;


/**
 * 查询媒资入库任务的执行状态（IngestStatus）。
 */
public class IngestStatusDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        ingestStatus(client);
    }

    /**
     * ingestStatus 通过 COS 资源地址和数据集名称，查询对应入库任务的处理进度和结果。
     * 该接口属于 POST 请求。
     */
    public static void ingestStatus(COSClient client) {
        IngestStatusRequest request = new IngestStatusRequest();
        request.setAppId("1250000000");
        // 设置对象文件名，需包含 COS 完整路径，格式为 cos://<BucketName>/<Path>。;是否必传：是
        request.setURI("cos://<BucketName>/<ObjectKey>");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        IngestStatusResponse response = client.ingestStatus(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
