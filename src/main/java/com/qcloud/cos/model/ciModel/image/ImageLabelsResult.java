package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片标签类型的分析结果，包含图片描述和标签详情
 *
 * <p>XML 示例：</p>
 * <pre>{@code
 * <ImageLabelsResult>
 *     <Description>xxxxx</Description>
 *     <LabelDetail>
 *         <LabelInfos>
 *             <LabelInfo>
 *                 <LabelName>Brand</LabelName>
 *                 <LabelValue>Nike</LabelValue>
 *             </LabelInfo>
 *         </LabelInfos>
 *         <Confidence>high</Confidence>
 *     </LabelDetail>
 * </ImageLabelsResult>
 * }</pre>
 */
@XStreamAlias("ImageLabelsResult")
public class ImageLabelsResult {

    /**
     * 图片标签场景默认也返回一段图片的描述内容
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 识别到的标签结果容器列表，每个 LabelDetail 包含一组标签信息和置信度。
     * 服务端可能返回多个 LabelDetail 节点。
     */
    @XStreamImplicit(itemFieldName = "LabelDetail")
    private List<LabelDetail> labelDetail = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LabelDetail> getLabelDetail() {
        return labelDetail;
    }

    public void setLabelDetail(List<LabelDetail> labelDetail) {
        this.labelDetail = labelDetail;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ImageLabelsResult{");
        sb.append("description='").append(description).append('\'');
        sb.append(", labelDetail=").append(labelDetail);
        sb.append('}');
        return sb.toString();
    }
}
