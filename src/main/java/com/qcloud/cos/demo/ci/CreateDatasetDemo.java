package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetRequest;
import com.qcloud.cos.model.ciModel.metaInsight.CreateDatasetResponse;
import com.qcloud.cos.utils.CIJackson;
import com.qcloud.cos.utils.Jackson;

/**
 * 创建数据集 详情见https://cloud.tencent.com/document/product/460/106020
 */
public class CreateDatasetDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createDataset(client);
    }

    /**
     * createDataset 本接口用于创建一个数据集（Dataset），数据集是由文件元数据构成的集合，用于存储和管理元数据。
     * 该接口属于 POST 请求。
     */
    public static void createDataset(COSClient client) {
        CreateDatasetRequest request = new CreateDatasetRequest();
        request.setAppId("1251704708");
        // 设置数据集名称，同一个账户下唯一。命名规则如下： 长度为1~32字符。
        // 只能包含小写英文字母，数字，短划线（-）。 必须以英文字母和数字开头。
        // 是否必传：是
        request.setDatasetName("mark-face-search");
        // 设置数据集描述信息。长度为1~256个英文或中文字符，默认值为空。;是否必传：否
        request.setDescription("demo");
        // 设置指模板，在建立元数据索引时，后端将根据模板来决定收集哪些元数据。
        // 每个模板都包含一个或多个算子，不同的算子表示不同的元数据。
        // 目前支持的模板： Official:DefaultEmptyId：默认为空的模板，表示不进行元数据的采集。
        //               Official:COSBasicMeta：基础信息模板，包含 COS 文件基础元信息算子，表示采集 COS 文件的名称、类型、ACL等基础元信息数据。
        //               Official:FaceSearch：人脸检索模板，包含人脸检索、COS 文件基础元信息算子。
        //               Official:ImageSearch：图像检索模板，包含图像检索、COS 文件基础元信息算子。
        request.setTemplateId("Official:FaceSearch");
        System.out.println(CIJackson.toJsonString(request));
        CreateDatasetResponse response = client.createDataset(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
