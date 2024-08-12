package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.SearchImageRequest;
import com.qcloud.cos.model.ciModel.metaInsight.SearchImageResponse;
import com.qcloud.cos.utils.Jackson;


/**
 * 图像检索 详情见https://cloud.tencent.com/document/product/460/106376
 */
public class SearchImageDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        searchImage(client);
    }

    /**
     * searchImage 可通过输入自然语言或图片，基于语义对数据集内文件进行图像检索。
     * 该接口属于 POST 请求。
     */
    public static void searchImage(COSClient client) {
        SearchImageRequest request = new SearchImageRequest();
        request.setAppId("1234567890");
        // 设置数据集名称，同一个账户下唯一。;是否必传：是
        request.setDatasetName("ImageSearch001");
        // 设置指定检索方式为图片或文本，pic 为图片检索，text 为文本检索，默认为 pic。;是否必传：否
        request.setMode("pic");
        // 设置资源标识字段，表示需要建立索引的文件地址(Mode 为 pic 时必选)。;是否必传：否
        request.setURI("cos://facesearch-1258726280/huge_base.jpg");
        // 设置返回相关图片的数量，默认值为10，最大值为100。;是否必传：否
        request.setLimit(10);
        // 设置出参 Score（相关图片匹配得分） 中，只有超过 MatchThreshold 值的结果才会返回。默认值为0，推荐值为80。;是否必传：否
        request.setMatchThreshold(1);

        SearchImageResponse response = client.searchImage(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
