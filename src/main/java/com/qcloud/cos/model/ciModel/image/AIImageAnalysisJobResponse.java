package com.qcloud.cos.model.ciModel.image;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通用图片分析（任务式）响应实体
 *
 * <p>XML 响应根节点为 {@code <Response>}，包含 code、message、state 状态字段和 analysisResult 结果容器。</p>
 *
 * <p>响应示例（Description 模式）：</p>
 * <pre>{@code
 * <Response>
 *     <Code>0</Code>
 *     <Message>OK</Message>
 *     <State>Success</State>
 *     <AnalysisResult>
 *         <Type>Description</Type>
 *         <DescriptionResult>
 *             <Description>三张图片展示了同一款运动鞋的正面、侧面和背面视角……</Description>
 *             <LabelDetail>
 *                 <LabelInfos>
 *                     <ConfidenceLevel>high</ConfidenceLevel>
 *                     <LabelInfo>
 *                         <LabelName>Brand</LabelName>
 *                         <LabelValue>Nike</LabelValue>
 *                     </LabelInfo>
 *                 </LabelInfos>
 *             </LabelDetail>
 *         </DescriptionResult>
 *     </AnalysisResult>
 * </Response>
 * }</pre>
 */
@XStreamAlias("Response")
public class AIImageAnalysisJobResponse {

    /**
     * 错误码，成功时为 "0"，失败时返回具体错误码
     */
    @XStreamAlias("Code")
    private String code;

    /**
     * 错误描述，成功时为 "OK"
     */
    @XStreamAlias("Message")
    private String message;

    /**
     * 分析状态，取值：Success（成功）、Failed（失败）
     */
    @XStreamAlias("State")
    private String state;

    /**
     * 分析结果容器，分析成功时返回
     */
    @XStreamAlias("AnalysisResult")
    private AIImageAnalysisJobResult analysisResult;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public AIImageAnalysisJobResult getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(AIImageAnalysisJobResult analysisResult) {
        this.analysisResult = analysisResult;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisJobResponse{");
        sb.append("code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", analysisResult=").append(analysisResult);
        sb.append('}');
        return sb.toString();
    }
}
