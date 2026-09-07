package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用图片分析（任务式）Description 模式下的 LabelDetail 容器。
 *
 * <p>包含多组 LabelInfos，每一组代表一个识别对象（一个物品/一组维度），对应一个置信度。</p>
 *
 * <p>与旧接口的 {@link LabelDetail} 区分：本类内部为 {@code List<JobLabelInfos>}，
 * 每个 JobLabelInfos 独立携带 ConfidenceLevel 字段；旧的 {@code LabelDetail} 使用单个 {@code Confidence} 字段。</p>
 *
 * <p>XML 示例：</p>
 * <pre>{@code
 * <LabelDetail>
 *     <LabelInfos>
 *         <ConfidenceLevel>high</ConfidenceLevel>
 *         <LabelInfo>
 *             <LabelName>Brand</LabelName>
 *             <LabelValue>Nike</LabelValue>
 *         </LabelInfo>
 *     </LabelInfos>
 * </LabelDetail>
 * }</pre>
 */
@XStreamAlias("LabelDetail")
public class JobLabelDetail {

    /**
     * 标签信息容器列表，每一组 LabelInfos 代表一个识别对象，对应一个置信度，包含多个 LabelInfo。
     * 后端已过滤掉无效标签项（如 LabelName 为空、为 none、格式不正确等），仅保留有效标签。
     */
    @XStreamImplicit(itemFieldName = "LabelInfos")
    private List<JobLabelInfos> labelInfos = new ArrayList<>();

    public List<JobLabelInfos> getLabelInfos() {
        if (labelInfos == null) {
            labelInfos = new ArrayList<>();
        }
        return labelInfos;
    }

    public void setLabelInfos(List<JobLabelInfos> labelInfos) {
        this.labelInfos = labelInfos;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("JobLabelDetail{");
        sb.append("labelInfos=").append(labelInfos);
        sb.append('}');
        return sb.toString();
    }
}
