package com.qcloud.cos.model.ciModel.image;

import com.qcloud.cos.internal.CIServiceRequest;
import com.qcloud.cos.internal.CosServiceRequest;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 通用图片分析（任务式，POST）请求实体
 *
 * <p>基于大模型多模态能力，通过 POST 请求一次性传入多张图片进行联合分析。
 * 与 {@link AIImageAnalysisRequest}（GET 版单图接口）为两套独立并存的接口。</p>
 *
 * <p>请求方式：POST /?ci-process=AIImageAnalysis
 * Content-Type: application/xml</p>
 *
 * <p>使用限制：</p>
 * <ul>
 *   <li>Part 总数最多 20 个，其中图片类型 Part 最多 10 个</li>
 *   <li>请求中必须包含至少一个图片类型的 Part（Type=Image），否则返回 NoImageProvided 错误</li>
 *   <li>单张图片大小不超过 10 MB，分辨率不超过 7680px × 4320px</li>
 * </ul>
 *
 * <p>授权：使用子账号调用需要 {@code ci:CreateImageAnalysisJob} 权限。</p>
 */
@XStreamAlias("Request")
public class AIImageAnalysisJobRequest extends CIServiceRequest {

    /**
     * 操作的 bucket 名称，不参与 XML 序列化
     */
    @XStreamOmitField
    private String bucketName;

    /**
     * 需要分析的输入内容，包含 Message 消息容器。
     * 请求中必须包含至少一个图片类型的 Part。
     */
    @XStreamAlias("Input")
    private AIImageAnalysisJobInput input;

    /**
     * 分析配置项，包含 Type（分析类型）、TemplateName（模板）、AiModel（模型）等。
     */
    @XStreamAlias("Conf")
    private AIImageAnalysisJobConf conf;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public AIImageAnalysisJobInput getInput() {
        if (input == null) {
            input = new AIImageAnalysisJobInput();
        }
        return input;
    }

    public void setInput(AIImageAnalysisJobInput input) {
        this.input = input;
    }

    public AIImageAnalysisJobConf getConf() {
        if (conf == null) {
            conf = new AIImageAnalysisJobConf();
        }
        return conf;
    }

    public void setConf(AIImageAnalysisJobConf conf) {
        this.conf = conf;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AIImageAnalysisJobRequest{");
        sb.append("bucketName='").append(bucketName).append('\'');
        sb.append(", input=").append(input);
        sb.append(", conf=").append(conf);
        sb.append('}');
        return sb.toString();
    }
}
