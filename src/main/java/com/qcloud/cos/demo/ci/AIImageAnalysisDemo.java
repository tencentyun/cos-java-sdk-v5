package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.image.AIImageAnalysisRequest;
import com.qcloud.cos.model.ciModel.image.AIImageAnalysisResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * 大模型图片分析（AIImageAnalysis）Demo
 */
public class AIImageAnalysisDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        aiImageAnalysisEcommerce(client);
    }

    /**
     * aiImageAnalysisEcommerce 电商场景标签识别
     * 通过 COS 中的图片 ObjectKey 进行大模型图片分析，返回品牌、类别等标签信息。
     */
    public static void aiImageAnalysisEcommerce(COSClient client) {
        //1.创建任务请求对象
        AIImageAnalysisRequest request = new AIImageAnalysisRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置bucket中的图片位置
        request.setObjectKey("sample.jpg");
        //2.3设置分析类型为 ImageLabels（标签模式）
        request.setType("ImageLabels");
        //2.4设置标签场景为 Ecommerce（电商场景）
        request.setLabelScenes("Ecommerce");
        //3.调用接口,获取任务响应对象
        AIImageAnalysisResponse response = client.aiImageAnalysis(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * aiImageAnalysisGeneral 通用场景标签识别
     * 不设置 labelScenes 时默认使用 General 通用场景，返回一级、二级、三级分类标签。
     */
    public static void aiImageAnalysisGeneral(COSClient client) {
        //1.创建任务请求对象
        AIImageAnalysisRequest request = new AIImageAnalysisRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setObjectKey("sample.jpg");
        request.setType("ImageLabels");
        // 不设置 labelScenes，默认使用 General 通用场景
        //3.调用接口,获取任务响应对象
        AIImageAnalysisResponse response = client.aiImageAnalysis(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * aiImageAnalysisWithUrl 使用外部图片 URL 进行分析
     * 通过公网可访问的图片 URL 进行大模型图片分析。
     * 若同时传入 objectKey 和 detectUrl，优先处理 detectUrl 对应的图片。
     *
     * 注意：使用 detectUrl 方式时，不传 objectKey，需要在创建 COSClient 时关闭路径校验，否则会抛出 IllegalArgumentException：
     *   ClientConfig clientConfig = new ClientConfig(region);
     *   clientConfig.setCheckRequestPath(false);
     *   COSClient client = new COSClient(cred, clientConfig);
     */
    public static void aiImageAnalysisWithUrl(COSClient client) {
        //1.创建任务请求对象
        AIImageAnalysisRequest request = new AIImageAnalysisRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置图片url
        request.setDetectUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/sample.jpg");
        //2.3设置分析类型
        request.setType("ImageLabels");
        //2.4设置标签场景
        request.setLabelScenes("Ecommerce");
        //3.调用接口,获取任务响应对象
        AIImageAnalysisResponse response = client.aiImageAnalysis(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * aiImageAnalysisCustomWithUrl 自定义场景分析
     * 使用 Custom 类型进行自定义场景的图片分析，返回 Base64 编码的自定义输出内容。
     *
     * 注意：使用 detectUrl 方式时，不传 objectKey，需要在创建 COSClient 时关闭路径校验，否则会抛出 IllegalArgumentException：
     *   ClientConfig clientConfig = new ClientConfig(region);
     *   clientConfig.setCheckRequestPath(false);
     *   COSClient client = new COSClient(cred, clientConfig);
     */
    public static void aiImageAnalysisCustomWithUrl(COSClient client) {
        //1.创建任务请求对象
        AIImageAnalysisRequest request = new AIImageAnalysisRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-1234567890");
        //2.2设置bucket中的图片位置
        request.setDetectUrl("https://demo-1234567890.cos.ap-chongqing.myqcloud.com/sample.jpg");
        //2.3设置分析类型为 Custom（自定义场景）
        request.setType("Custom");
        request.setTemplateId("template-id");
        //3.调用接口,获取任务响应对象
        AIImageAnalysisResponse response = client.aiImageAnalysis(request);
        //4.打印结果
        if (response.getAnalysisResult() != null
                && response.getAnalysisResult().getCustomResult() != null) {
            System.out.println(response.getAnalysisResult().getCustomResult().getCustomOutput());
        }
        System.out.println(Jackson.toJsonString(response));
    }
}
