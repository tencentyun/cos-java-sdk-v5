package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 大模型图片分析响应实体
 *
 * <p>XML 响应根节点为 {@code <Response>}，包含 AnalysisResult 和 RequestId。</p>
 *
 * <p>响应示例（ImageLabels 类型）：</p>
 * <pre>{@code
 * <Response>
 *     <AnalysisResult>
 *         <Type>ImageLabels</Type>
 *         <ImageLabelsResult>
 *             <Description>xxxxx</Description>
 *             <LabelDetail>
 *                 <LabelInfos>
 *                     <LabelInfo>
 *                         <LabelName>Brand</LabelName>
 *                         <LabelValue>Nike</LabelValue>
 *                     </LabelInfo>
 *                 </LabelInfos>
 *                 <Confidence>high</Confidence>
 *             </LabelDetail>
 *         </ImageLabelsResult>
 *     </AnalysisResult>
 *     <RequestId>NWFjMzQ0MDZfOTBmYTUwXzZkZV8z****</RequestId>
 * </Response>
 * }</pre>
 *
 * <p>响应示例（Custom 类型）：</p>
 * <pre>{@code
 * <Response>
 *     <AnalysisResult>
 *         <CustomResult>
 *             <CustomOutput>YGBganNvbgp7Li4ufQpgYGA=</CustomOutput>
 *         </CustomResult>
 *         <Type>Custom</Type>
 *     </AnalysisResult>
 *     <RequestId>NjlkZjUwMjdfY2RiYzAwMTVfNWNmNl8x</RequestId>
 * </Response>
 * }</pre>
 */
@XStreamAlias("Response")
public class AIImageAnalysisResponse {

    /**
     * 图片理解结果总容器
     */
    @XStreamAlias("AnalysisResult")
    private AnalysisResult analysisResult;

    /**
     * 请求唯一标识，用于问题排查
     */
    @XStreamAlias("RequestId")
    private String requestId;

    public AnalysisResult getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(AnalysisResult analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisResponse{");
        sb.append("analysisResult=").append(analysisResult);
        sb.append(", requestId='").append(requestId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
