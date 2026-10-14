package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.GetAIMediaInfoRequest;
import com.qcloud.cos.model.ciModel.metaInsight.GetAIMediaInfoResponse;
import com.qcloud.cos.utils.Jackson;


/**
 * 获取媒资 AI 分析信息 详情见https://cloud.tencent.com/document/product/460/135100
 */
public class GetAIMediaInfoDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        getAIMediaInfo(client);
    }

    /**
     * getAIMediaInfo 查询数据集内某个媒资文件（视频、图片）的完整 AI 分析结果，
     * 包括文件基础信息、内容标签、语音识别、文字识别、人脸识别、内容粗分类等结构化元数据。
     * 该接口属于 POST 请求。
     */
    public static void getAIMediaInfo(COSClient client) {
        GetAIMediaInfoRequest request = new GetAIMediaInfoRequest();
        request.setAppId("1250000000");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置对象文件名，需包含 COS 完整路径，格式为 cos://<BucketName>/<Path>。;是否必传：是
        request.setURI("cos://<BucketName>/<ObjectKey>");
        GetAIMediaInfoResponse response = client.getAIMediaInfo(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
