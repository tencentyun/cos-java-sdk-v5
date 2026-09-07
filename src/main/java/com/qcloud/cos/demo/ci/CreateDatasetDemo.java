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
//        createDataset(client);
        // 训练场景数据集示例（可选，需要时取消注释）
         createTrainingDataset(client);
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

    /**
     * createTrainingDataset 演示如何创建一个"训练场景"数据集，覆盖 5.6.271 版本新增字段：
     * DatasetType / Version / Volume / TrainingMode / TrainingDataset / TrainingURI / SceneType。
     * 这些字段全部为可选字段，仅在需要时设置。
     * 该接口属于 POST 请求。
     */
    public static void createTrainingDataset(COSClient client) {
        CreateDatasetRequest request = new CreateDatasetRequest();
        request.setAppId("1251704708");

        // ===== 基础字段 =====
        // 数据集名称（必传），账户下唯一
        request.setDatasetName("demo-training-dataset");
        // 数据集描述（可选）
        request.setDescription("training dataset demo");
        // 模板 ID（可选）
        request.setTemplateId("Official:COSBasicMeta");

        // ===== 5.6.271 新增字段（均为可选） =====
        // 数据集类型：0-通用（默认），1-训练数据集，2-评测数据集，具体取值参见官方文档
        request.setDatasetType(1);
        // 数据集版本。basic、standard，默认为 basic
        request.setVersion("standard");
        // 数据集容量配额，单位：万
        request.setVolume(100);
        // 训练模式，用于标识当前数据集采用的训练流水线模式
        request.setTrainingMode(1);
        // 训练数据集名称，当本数据集需要引用一个外部训练集时使用
        request.setTrainingDataset("upstream-dataset");
        // 训练数据 URI，标识外部训练数据的 COS 路径
        request.setTrainingURI("cos://demo-1251704708/");
        // 场景类型
        request.setSceneType("general");

        // 打印实际发送到服务端的 JSON body，便于调试字段名/值是否符合预期
        System.out.println(CIJackson.toJsonString(request));
        CreateDatasetResponse response = client.createDataset(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
