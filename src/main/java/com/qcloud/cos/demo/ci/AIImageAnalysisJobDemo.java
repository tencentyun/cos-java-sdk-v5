package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.*;
import com.qcloud.cos.utils.CIJackson;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 通用图片分析（任务式，POST）Demo
 *
 * <p>与 {@link AIImageAnalysisDemo}（GET 单图版本）为两套独立的接口。</p>
 *
 * <p>本 Demo 演示：</p>
 * <ul>
 *   <li>Description 模式 + ecommerce 电商模板：多张商品图联合标签分析</li>
 *   <li>Description 模式 + general 通用模板：ObjectKey 与 Url 混合传入</li>
 *   <li>Custom 模式：多图 + 文本 Prompt 联合分析</li>
 *   <li>响应结果解析（Description / Custom 两种情况）</li>
 * </ul>
 */
public class AIImageAnalysisJobDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createAIImageAnalysisJobWithPrompt(client);
    }

    public static void createAIImageAnalysisJobWithPrompt(COSClient client) {
        String prompt = "提示词";

        // 1.创建请求对象
        AIImageAnalysisJobRequest request = new AIImageAnalysisJobRequest();
        // 2.设置请求 bucket
        request.setBucketName("demo-1234567890");
        // 3.构造 Input.Message.Content：追加多张图片 Part（通过 ObjectKey）
        AIImageAnalysisJobContent content = request.getInput().getMessage().getContent();
        List<AIImageAnalysisJobPart> parts = content.getParts();

        AIImageAnalysisJobPart part = new AIImageAnalysisJobPart();
        part.setType("Image");
        part.setObjectKey("case/ImageAnalysis/1.jpg");
        part.setText(prompt);
        parts.add(part);

        part = new AIImageAnalysisJobPart();
        part.setType("Image");
        part.setUrl("https://examplebucket-1250000000.cos.ap-chongqing.myqcloud.com/case/ImageAnalysis/1.jpg");
        part.setText(prompt);
        parts.add(part);

        // 4.配置 Conf：
        AIImageAnalysisJobConf conf = request.getConf();
        conf.setType("Custom");
        conf.setTemplateName("general");
//        conf.setAiModel("");
        // 5.调用接口
        System.out.println(CIJackson.toJsonString(request));

        AIImageAnalysisJobResponse response = client.createAIImageAnalysisJob(request);
        // 6.打印响应
        System.out.println(CIJackson.toJsonString(response));
    }
    
    /**
     * createAIImageAnalysisJobEcommerce
     * Description 模式 + ecommerce 电商模板：多张商品图联合标签分析
     */
    public static void createAIImageAnalysisJobEcommerce(COSClient client) {
        // 1.创建请求对象
        AIImageAnalysisJobRequest request = new AIImageAnalysisJobRequest();
        // 2.设置请求 bucket
        request.setBucketName("examplebucket-1250000000");
        // 3.构造 Input.Message.Content：追加多张图片 Part（通过 ObjectKey）
        AIImageAnalysisJobContent content = request.getInput().getMessage().getContent();
        content.addImagePartByObjectKey("products/shoe_front.jpg")
                .addImagePartByObjectKey("products/shoe_side.jpg")
                .addImagePartByUrl("https://example.com/products/shoe_back.jpg");
        // 4.配置 Conf：Description 模式 + 电商模板
        AIImageAnalysisJobConf conf = request.getConf();
        conf.setType("Description");
        conf.setTemplateName("ecommerce");
        // 5.调用接口
        AIImageAnalysisJobResponse response = client.createAIImageAnalysisJob(request);
        // 6.打印响应
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * createAIImageAnalysisJobGeneralMixed
     * Description 模式 + general 通用模板：ObjectKey 与 Url 混合传入
     */
    public static void createAIImageAnalysisJobGeneralMixed(COSClient client) {
        AIImageAnalysisJobRequest request = new AIImageAnalysisJobRequest();
        request.setBucketName("examplebucket-1250000000");
        // ObjectKey + Url 混合
        request.getInput().getMessage().getContent()
                .addImagePartByObjectKey("sample/image1.jpg")
                .addImagePartByUrl("https://example.com/image2.jpg");
        // 不设置 templateName 时默认使用 general
        request.getConf().setType("Description");
        // AiModel 可选，默认为 qwen3.5-4b
        request.getConf().setAiModel("qwen3.5-4b");
        AIImageAnalysisJobResponse response = client.createAIImageAnalysisJob(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * createAIImageAnalysisJobCustomWithPrompt
     * Custom 模式：多图 + 文本 Prompt 跨图比较分析
     * <p>Text Part 长度限制 1024 字符，可通过"第一张图"、"第二张图"引用图片。</p>
     */
    public static void createAIImageAnalysisJobCustomWithPrompt(COSClient client) {
        AIImageAnalysisJobRequest request = new AIImageAnalysisJobRequest();
        request.setBucketName("examplebucket-1250000000");
        // 多张图片 + 文本 Part（顺序即为大模型输入顺序）
        request.getInput().getMessage().getContent()
                .addImagePartByUrl("https://example.com/demo_img_1.png")
                .addImagePartByUrl("https://example.com/demo_img_2.png")
                .addImagePartByObjectKey("reports/chart.png")
                .addTextPart("请比较前两张图片的主要区别，并结合第三张图表数据给出分析结论。");
        request.getConf().setType("Custom");
        request.getConf().setAiModel("qwen3.5-4b");
        AIImageAnalysisJobResponse response = client.createAIImageAnalysisJob(request);
        // Custom 模式：读取自定义输出内容
        if (response.getAnalysisResult() != null
                && response.getAnalysisResult().getCustomResult() != null) {
            String customOutput = response.getAnalysisResult().getCustomResult().getCustomOutput();
            System.out.println("Custom Output: " + customOutput);
        }
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * parseDescriptionResult
     * 演示如何解析 Description 模式的响应结果（Description + 多组标签）
     */
    public static void parseDescriptionResult(AIImageAnalysisJobResponse response) {
        if (response == null || !"Success".equals(response.getState())) {
            System.out.println("analysis failed, code=" + (response == null ? null : response.getCode())
                    + ", message=" + (response == null ? null : response.getMessage()));
            return;
        }
        AIImageAnalysisJobResult analysisResult = response.getAnalysisResult();
        if (analysisResult == null || !"Description".equals(analysisResult.getType())) {
            return;
        }
        DescriptionResult descriptionResult = analysisResult.getDescriptionResult();
        if (descriptionResult == null) {
            return;
        }
        // 图片整体描述
        System.out.println("Description: " + descriptionResult.getDescription());
        // 标签结果（可能为空：模型未识别到有效标签时 LabelDetail 缺失）
        JobLabelDetail labelDetail = descriptionResult.getLabelDetail();
        if (labelDetail == null) {
            System.out.println("no valid labels returned by the model.");
            return;
        }
        // 遍历多组 LabelInfos（每一组对应一个识别对象/一组维度，独立置信度）
        for (JobLabelInfos labelInfos : labelDetail.getLabelInfos()) {
            System.out.println("--- LabelInfos, ConfidenceLevel=" + labelInfos.getConfidenceLevel());
            for (LabelInfo labelInfo : labelInfos.getLabelInfo()) {
                System.out.println("  " + labelInfo.getLabelName() + " = " + labelInfo.getLabelValue());
            }
        }
    }

    /**
     * parseCustomResult
     * 演示如何解析 Custom 模式的响应结果（自定义文本输出）
     */
    public static void parseCustomResult(AIImageAnalysisJobResponse response) {
        if (response == null || !"Success".equals(response.getState())) {
            return;
        }
        AIImageAnalysisJobResult analysisResult = response.getAnalysisResult();
        if (analysisResult == null || !"Custom".equals(analysisResult.getType())) {
            return;
        }
        JobCustomResult customResult = analysisResult.getCustomResult();
        if (customResult != null) {
            // CustomOutput 已由后端做过 XML 实体转义，客户端拿到即为明文 UTF-8
            System.out.println("Custom Output: " + customResult.getCustomOutput());
        }
    }
}
