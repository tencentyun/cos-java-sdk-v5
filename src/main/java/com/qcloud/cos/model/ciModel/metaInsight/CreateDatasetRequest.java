package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qcloud.cos.internal.CIServiceRequest;


public class CreateDatasetRequest extends CIServiceRequest {

    /**
     * 数据集名称，同一个账户下唯一。命名规则如下：
     * 长度为1~64字符。
     * 只能包含小写英文字母，数字，短划线（-）。
     * 必须以英文字母和数字开头。
     * 是否必传：是
     */
    private String datasetName;

    /**
     *数据集描述信息。长度为1~256个英文或中文字符，默认值为空。;是否必传：否
     */
    private String description;

    /**
     *指模板，在建立元数据索引时，后端将根据模板来决定收集哪些元数据。每个模板都包含一个或多个算子，不同的算子表示不同的元数据。
     * 目前支持的模板： Official:DefaultEmptyId：默认为空的模板，表示不进行元数据的采集。
     * Official:COSBasicMeta：基础信息模板，包含 COS 文件基础元信息算子，表示采集 COS 文件的名称、类型、ACL等基础元信息数据。
     * Official:FaceSearch：人脸检索模板，包含人脸检索、COS 文件基础元信息算子。
     * Official:ImageSearch：图像检索模板，包含图像检索、COS 文件基础元信息算子。;
     * 是否必传：否
     */
    private String templateId;

    /**
     * 表示数据集的类型，默认为0，表示普通数据集，值为1时表示该数据集为控制台文件列表专用的数据集。
     * 是否必传：否
     * @since 5.6.271
     */
    private Integer datasetType;

    /**
     * 数据集版本。basic、standard，默认为 basic
     * 是否必传：否
     * @since 5.6.271 
     */
    private String version;

    /**
     * 当前数据集预估数据量级，MetaInsight 将根据该参数调整数据分布策略以保证查询性能，
     * Version 为 basic 时为50w。
     * Version 为 standard 时，默认为500w
     * 可设置1-10000，单位w。传0采用默认值
     * 是否必传：否
     * @since 5.6.271 
     */
    private Integer volume;

    /**
     * 训练数据的来源模式。
     * 默认为0，表示训练数据来源于指定数据集；
     * 值为1时表示训练数据来源于 COS 某个 bucket 目录下的文件。
     * 仅在 Version 为 standard 时生效。
     * 是否必传：否
     * @since 5.6.271
     */
    private Integer trainingMode;

    /**
     * 训练数据的数据集名称。仅在 TrainingMode 为0时生效。
     * 是否必传：否
     * @since 5.6.271
     */
    private String trainingDataset;

    /**
     * 训练数据的资源路径。仅在 TrainingMode 为1时生效。
     * <p>
     * 该字段含连续大写字母「URI」，Jackson UPPER_CAMEL_CASE 全局策略对此类字段的转换行为不稳定，
     * 因此显式指定 JSON 键为 {@code TrainingURI} 进行兼容（仅此字段单独处理，不影响全局策略）。
     *
     * 是否必传：否
     * @since 5.6.271
     */
    @JsonProperty("TrainingURI")
    private String trainingURI;

    /**
     * 当 TemplateId=Official:ImageSearch 时生效，表示场景类型。可选值如下：
     * general（默认值）：通用场景
     * E-commercial：电商场景
     * iDrive：出行场景
     */
    private String sceneType;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getTemplateId() { return templateId; }

    public void setTemplateId(String templateId) { this.templateId = templateId; }

    public Integer getDatasetType() { return datasetType; }

    public void setDatasetType(Integer datasetType) { this.datasetType = datasetType; }

    public String getVersion() { return version; }

    public void setVersion(String version) { this.version = version; }

    public Integer getVolume() { return volume; }

    public void setVolume(Integer volume) { this.volume = volume; }

    public Integer getTrainingMode() { return trainingMode; }

    public void setTrainingMode(Integer trainingMode) { this.trainingMode = trainingMode; }

    public String getTrainingDataset() { return trainingDataset; }

    public void setTrainingDataset(String trainingDataset) { this.trainingDataset = trainingDataset; }

    public String getTrainingURI() { return trainingURI; }

    public void setTrainingURI(String trainingURI) { this.trainingURI = trainingURI; }

    public String getSceneType() { return sceneType; }

    public void setSceneType(String sceneType) { this.sceneType = sceneType; }

    


}
