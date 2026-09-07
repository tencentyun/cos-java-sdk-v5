package com.qcloud.cos.model.ciModel.metaInsight;

import com.qcloud.cos.internal.CIServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;


public class UpdateDatasetRequest extends CIServiceRequest {

    /**
     *数据集名称，同一个账户下唯一。;是否必传：是
     */
    private String datasetName;

    /**
     *数据集描述信息。长度为1~256个英文或中文字符，默认值为空。;是否必传：否
     */
    private String description;

    /**
     * 检索数据集关联的检索模板，在建立元数据索引时，后端将根据检索模板来决定采集文件的哪些元数据。
     * 每个检索模板都包含若干个算子，不同的算子表示不同的处理能力。
     * 目前支持的模板：
     * Official:DefaultEmptyId：默认为空的模板，表示不进行元数据的采集。
     * Official:COSBasicMeta：基础信息模板，包含 COS 文件基础元信息算子，表示采集 COS 文件的名称、类型、ACL 等基础元信息数据。
     * Official:FaceSearch：人脸检索模板，包含人脸检索、COS 文件基础元信息算子。
     * Official:ImageSearch：图像检索模板，包含图像检索、COS 文件基础元信息算子。
     * 默认值为空，即不关联检索模板，不进行任何元数据的采集。
     * 是否必传：否
     */
    private String templateId;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getTemplateId() { return templateId; }

    public void setTemplateId(String templateId) { this.templateId = templateId; }

    


}