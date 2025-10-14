
package com.qcloud.cos.model.ciModel.job.v2;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * 文档AIGC元数据实体
 * https://cloud.tencent.com/document/product/460/123076
 */
public class DocAIGCMetadata implements Serializable {
    
    /**
     * 生成合成标签要素，用于表示文档属于、可能、疑似为人工智能生成合成的属性信息
     * 长度限制：2048个 UTF-8字符数
     * 字符限制：GB18030—2022（ASCII）中码位为0x21 - 0x7E 的字符，即包含阿拉伯数字、大小写英文及可打印字符
     * 必填参数
     */
    @XStreamAlias("Label")
    private String label;
    
    /**
     * 生成合成服务提供者要素，内容为文档生成合成服务提供者的名称或编码
     * 长度限制：2048个 UTF-8字符数
     * 字符限制：GB18030—2022（ASCII）中码位为0x21 - 0x7E 的字符，即包含阿拉伯数字、大小写英文及可打印字符
     * 可选参数
     */
    @XStreamAlias("ContentProducer")
    private String contentProducer;
    
    /**
     * 内容制作编号要素，内容为文档生成合成服务提供者对该内容的唯一编号
     * 支持通配符：
     * ${InputName}: COS 源文件名，不带后缀，不带路径
     * ${InputNameAndExt}: COS 源文件名，带后缀，不带路径
     * ${JobId}: 添加 AIGC 元数据信息的执行任务 id
     * ${InputPath}: COS源文件路径，不包含文件名
     * 长度限制：2048个 UTF-8字符数
     * 字符限制：GB18030—2022（ASCII）中码位为0x21 - 0x7E 的字符，即包含阿拉伯数字、大小写英文及可打印字符
     * 可选参数
     */
    @XStreamAlias("ProduceID")
    private String produceId;
    
    /**
     * 预留字段1，内容为用户自主开展安全防护，保护内容、标识完整性的信息。需经过 base64编码后传入
     * 长度限制（base64编码前）：2048个 UTF-8字符数
     * 字符限制（base64编码前）：GB18030—2022（ASCII）中码位为0x21 - 0x7E 的字符，即包含阿拉伯数字、大小写英文及可打印字符
     * 可选参数
     */
    @XStreamAlias("ReservedCode1")
    private String reservedCode1;
    
    /**
     * 内容传播服务提供者要素，内容为文档传播服务提供者的名称或编码
     * 长度限制：2048个 UTF-8字符数
     * 字符限制：GB18030—2022（ASCII）中码位为0x21 - 0x7E 的字符，即包含阿拉伯数字、大小写英文及可打印字符
     * 可选参数
     */
    @XStreamAlias("ContentPropagator")
    private String contentPropagator;
    
    /**
     * 内容传播编号要素，内容为文档传播服务提供者对该文档的唯一编号
     * 支持通配符：
     * ${InputName}: COS 源文件名，不带后缀，不带路径
     * ${InputNameAndExt}: COS 源文件名，带后缀，不带路径
     * ${JobId}: 添加 AIGC 元数据信息的执行任务 id
     * ${InputPath}: COS 源文件路径，不包含文件名
     * 长度限制：2048个 UTF-8字符数
     * 字符限制：GB18030—2022（ASCII）中码位为0x21 - 0x7E 的字符，即包含阿拉伯数字、大小写英文及可打印字符
     * 可选参数
     */
    @XStreamAlias("PropagateID")
    private String propagateId;
    
    /**
     * 预留字段2，内容为用户自主开展安全防护，保护内容、标识完整性的信息。需经过 base64编码后传入
     * 长度限制（base64编码前）：2048个 UTF-8字符数
     * 字符限制（base64编码前）：GB18030—2022（ASCII）中码位为0x21 - 0x7E 的字符，即包含阿拉伯数字、大小写英文及可打印字符
     * 可选参数
     */
    @XStreamAlias("ReservedCode2")
    private String reservedCode2;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getContentProducer() {
        return contentProducer;
    }

    public void setContentProducer(String contentProducer) {
        this.contentProducer = contentProducer;
    }

    public String getProduceId() {
        return produceId;
    }

    public void setProduceId(String produceId) {
        this.produceId = produceId;
    }

    public String getReservedCode1() {
        return reservedCode1;
    }

    public void setReservedCode1(String reservedCode1) {
        this.reservedCode1 = reservedCode1;
    }

    public String getContentPropagator() {
        return contentPropagator;
    }

    public void setContentPropagator(String contentPropagator) {
        this.contentPropagator = contentPropagator;
    }

    public String getPropagateId() {
        return propagateId;
    }

    public void setPropagateId(String propagateId) {
        this.propagateId = propagateId;
    }

    public String getReservedCode2() {
        return reservedCode2;
    }

    public void setReservedCode2(String reservedCode2) {
        this.reservedCode2 = reservedCode2;
    }

    @Override
    public String toString() {
        return "DocAIGCMetadata{" +
                "label='" + label + '\'' +
                ", contentProducer='" + contentProducer + '\'' +
                ", produceId='" + produceId + '\'' +
                ", reservedCode1='" + reservedCode1 + '\'' +
                ", contentPropagator='" + contentPropagator + '\'' +
                ", propagateId='" + propagateId + '\'' +
                ", reservedCode2='" + reservedCode2 + '\'' +
                '}';
    }
}