package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.AIGCMetadataResponse;

public class AIGCMetadataDemo {

    public static void main(String[] args) {
        // 初始化客户端
        COSClient cosClient = ClientUtils.getTestClient();

        getImageAIGCMetadata(cosClient);
    }

    /**
     * getImageAIGCMetadata 可以获取 JPG/JPEG、PNG、AVIF 格式图片的 AIGC 元数据标识信息。
     * 该接口属于 GET 请求。
     */
    public static void getImageAIGCMetadata(COSClient client) {
        String bucketName = "demoBuck-1251704708";
        String key = "test.jpg";

        AIGCMetadataResponse response = client.getImageAIGCMetadata(bucketName, key);

        System.out.println(response);
    }

    /**
     * 本接口获取视频、音频中的 AIGC 元数据标识信息。
     * 本接口属于 GET 请求。
     */
    public static void getMediaAIGCMetadata(COSClient client) {
        String bucketName = "demoBuck-1251704708";
        String key = "test.mp4";

        AIGCMetadataResponse response = client.getMediaAIGCMetadata(bucketName, key);

        System.out.println(response);
    }
}
