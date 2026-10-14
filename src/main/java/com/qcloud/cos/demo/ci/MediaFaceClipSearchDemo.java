package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.MediaFaceClipSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.MediaFaceClipSearchResponse;
import com.qcloud.cos.utils.Jackson;


/**
 * 人脸定位媒资片段 详情见https://cloud.tencent.com/document/product/460/135102
 */
public class MediaFaceClipSearchDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        mediaFaceClipSearch(client);
    }

    /**
     * mediaFaceClipSearch 在人脸查找媒资（MediaFaceSearch）返回的 FaceId 与 UriList 基础上，
     * 精确定位该人脸在指定媒资文件中出现的时间片段与逐帧坐标框。
     * 该接口属于 POST 请求。
     */
    public static void mediaFaceClipSearch(COSClient client) {
        MediaFaceClipSearchRequest request = new MediaFaceClipSearchRequest();
        request.setAppId("1250000000");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置需要进行人脸精确定位的媒资文件地址，取值为人脸查找媒资返回的 UriList 中的某一 URI。;是否必传：是
        request.setURI("cos://<BucketName>/<ObjectKey>");
        // 设置人脸 ID，取值为人脸查找媒资返回的 FaceId。;是否必传：是
        request.setFaceId("face_20260206_0001");
        MediaFaceClipSearchResponse response = client.mediaFaceClipSearch(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
