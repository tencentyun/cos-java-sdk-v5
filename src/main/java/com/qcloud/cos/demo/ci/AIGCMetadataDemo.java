package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.AIGCMetadataResponse;
import com.qcloud.cos.utils.Jackson;

public class AIGCMetadataDemo {

    public static void main(String[] args) {
        // 初始化客户端
        COSClient cosClient = ClientUtils.getTestClient();

        getDocumentAIGCMetadata(cosClient);
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

    /**
     * 本接口获取文档中的 AIGC 元数据标识信息。
     * 支持获取 pdf、xmind、md、docx、pptx、xlsx、dotx、potx、xltx 格式的 AIGC 元数据标识信息。
     * 该接口属于 GET 请求。
     */
    public static void getDocumentAIGCMetadata(COSClient client) {
        String bucketName = "chongqingtest-1251704708";
        // 支持的文档格式：pdf、xmind、md、docx、pptx、xlsx、dotx、potx、xltx
        String key = "/SDK/doc/output/abc_0.pdf";

        AIGCMetadataResponse response = client.getDocumentAIGCMetadata(bucketName, key);

        System.out.println(Jackson.toJsonString(response));
    }
}
