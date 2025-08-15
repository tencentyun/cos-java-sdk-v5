package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.TranslationInput;
import com.qcloud.cos.model.ciModel.job.TranslationOutput;
import com.qcloud.cos.model.ciModel.job.TranslationRequest;
import com.qcloud.cos.model.ciModel.job.TranslationResponse;

/**
 * 异步翻译相关demo https://cloud.tencent.com/document/product/460/84799
 */
public class TranslationDemo {
    public static void main(String[] args) {
        // 1. 初始化客户端
        COSClient client = ClientUtils.getTestClient();

        // 2. 创建翻译任务
        TranslationResponse response = createTranslationJob(client);
        System.out.println(response.getJobsDetail().getJobId());
    }

    public static TranslationResponse createTranslationJob(COSClient client) {
        // 1. 构建请求对象
        TranslationRequest request = new TranslationRequest();

        // 2. 设置基础参数
        request.setBucketName("demobucket-1251704708");
        request.setTag("Translation");

        // 3. 配置输入
        TranslationInput input = new TranslationInput();
        input.setObject("demo.txt");
        input.setLang("zh");
        input.setType("txt");
        request.setInput(input);

        // 4. 配置输出
        TranslationOutput output = new TranslationOutput();
        output.setRegion("ap-guangzhou");
        output.setBucket("demobucket-1251704708");
        output.setObject("translated1.txt");
        request.getOperation().setOutput(output);

        // 5. 配置翻译参数
        request.getOperation().getTranslation().setLang("en");
        request.getOperation().getTranslation().setType("txt");

        // 6. 配置其他参数
        request.getOperation().setJobLevel("0");
        request.getOperation().setUserData("userData");

        request.setCallBack("http://xxxx.xxxx.xxxx:xxxx");
        request.setCallBackFormat("JSON");

        return client.createTranslationJob(request);
    }
}
