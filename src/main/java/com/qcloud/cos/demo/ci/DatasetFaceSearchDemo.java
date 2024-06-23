package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetFaceSearchRequest;
import com.qcloud.cos.model.ciModel.metaInsight.DatasetFaceSearchResponse;
import com.qcloud.cos.utils.CIJackson;
import com.qcloud.cos.utils.Jackson;


/**
 * 人脸搜索 详情见https://cloud.tencent.com/document/product/460/106166
 */
public class DatasetFaceSearchDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        datasetFaceSearch(client);
    }

    /**
     * datasetFaceSearch 从数据集中搜索与指定图片最相似的前N张图片并返回人脸坐标可对数据集内文件进行一个或多个人员的人脸识别。
     * 该接口属于 POST 请求。
     */
    public static void datasetFaceSearch(COSClient client) {
        DatasetFaceSearchRequest request = new DatasetFaceSearchRequest();
        request.setAppId("1251704708");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("test");
        // 设置资源标识字段，表示需要建立索引的文件地址。;是否必传：是
        request.setURI("cos://<BucketName>/<ObjectKey>");
        // 设置输入图片中检索的人脸数量，默认值为1(传0或不传采用默认值)，最大值为10。;是否必传：否
        request.setMaxFaceNum(1);
        // 设置检索的每张人脸返回相关人脸数量，默认值为10，最大值为100。;是否必传：否
        request.setLimit(10);
        // 设置出参 Score 中，只有超过 MatchThreshold 值的结果才会返回。范围：1-100，默认值为0，推荐值为80。;是否必传：否
        request.setMatchThreshold(10);
        DatasetFaceSearchResponse response = client.datasetFaceSearch(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
