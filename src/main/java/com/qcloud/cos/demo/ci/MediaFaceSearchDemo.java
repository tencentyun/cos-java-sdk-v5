package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.MediaFaceSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.MediaFaceSearchResponse;
import com.qcloud.cos.utils.Jackson;


/**
 * 人脸查找媒资 详情见https://cloud.tencent.com/document/product/460/135087
 */
public class MediaFaceSearchDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        mediaFaceSearch(client);
    }

    /**
     * mediaFaceSearch 输入一张外部人脸图片，在指定数据集中查找出包含该人脸的媒资文件，
     * 返回系统为每张人脸分配的人脸 ID（FaceId）及其所归属的媒资文件列表（UriList）。
     * 该接口属于 POST 请求。
     */
    public static void mediaFaceSearch(COSClient client) {
        MediaFaceSearchRequest request = new MediaFaceSearchRequest();
        request.setAppId("1250000000");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置用于人脸查找的外部人脸图片地址，需包含 COS 完整路径。;是否必传：是
        request.setURI("cos://<BucketName>/<ObjectKey>");
        MediaFaceSearchResponse response = client.mediaFaceSearch(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
