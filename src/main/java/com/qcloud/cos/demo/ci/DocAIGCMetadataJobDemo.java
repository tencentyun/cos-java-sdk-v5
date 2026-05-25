package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadata;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadataConfig;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadataInput;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadataJobRequest;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadataJobResponse;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadataOperation;
import com.qcloud.cos.model.ciModel.job.v2.DocAIGCMetadataOutput;
import com.qcloud.cos.utils.Jackson;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

/**
 * 文档AIGC元数据处理任务Demo
 * 腾讯云数据万象支持通过异步任务的方式，为文档添加 AIGC 元数据标识
 * API详情见 https://cloud.tencent.com/document/product/460/123076
 */
public class DocAIGCMetadataJobDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法
        createDocAIGCMetadataJob(client);
    }

    /**
     * 创建文档AIGC元数据处理任务
     * 支持的文档格式：pdf、md、xmind、docx、pptx、xlsx、dotx、potx、xltx
     *
     * @param client COS客户端
     */
    public static void createDocAIGCMetadataJob(COSClient client) {
        //1.创建任务请求对象
        DocAIGCMetadataJobRequest request = new DocAIGCMetadataJobRequest();

        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1251704708");

        // 设置输入文件
        DocAIGCMetadataInput input = request.getInput();
        input.setObject("test.pdf");

        // 设置操作参数
        DocAIGCMetadataOperation operation = request.getOperation();

        // 设置文档AIGC元数据配置
        DocAIGCMetadataConfig docAIGCConfig = operation.getDocAIGCMetadata();
        DocAIGCMetadata aigcMetadata = docAIGCConfig.getAigcMetadata();

        // 设置AIGC元数据参数
        aigcMetadata.setLabel("1"); // 必填：生成合成标签要素
        aigcMetadata.setContentProducer("testProducer"); // 可选：生成合成服务提供者
        aigcMetadata.setProduceId("${JobId}_${InputName}"); // 可选：内容制作编号，支持通配符

        // Base64编码的预留字段
        String reservedCode1 = Base64.getUrlEncoder().withoutPadding()
                .encodeToString("security_info_1".getBytes(StandardCharsets.UTF_8));
        aigcMetadata.setReservedCode1(reservedCode1);

        aigcMetadata.setContentPropagator("testPropagator"); // 可选：内容传播服务提供者
        aigcMetadata.setPropagateId("${JobId}_propagate"); // 可选：内容传播编号

        String reservedCode2 = Base64.getUrlEncoder().withoutPadding()
                .encodeToString("security_info_2".getBytes(StandardCharsets.UTF_8));
        aigcMetadata.setReservedCode2(reservedCode2);

        // 设置输出参数
        DocAIGCMetadataOutput output = operation.getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("demo-1251704708");
        output.setObject("/abc_${Number}.pdf");

        // 设置回调参数（可选）
        // request.setCallBackFormat("JSON");
        // request.setCallBack("https://your-callback-url.com/callback");

        //3.调用接口,获取任务响应对象
        DocAIGCMetadataJobResponse response = client.createDocAIGCMetadataJob(request);

        System.out.println(Jackson.toJsonString(response));
    }
}
