package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;

import com.qcloud.cos.model.ciModel.job.v2.CreatePosterProductionRequest;
import com.qcloud.cos.model.ciModel.job.v2.CreatePosterProductionResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * 提交海报合成任务 详情见https://cloud.tencent.com/document/product/460/82394
 */
public class CreatePosterProductionDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createPosterProduction(client);
    }

    /**
     * createPosterProduction 提交一个海报合成任务
     * 该接口属于 POST 请求。
     */
    public static void createPosterProduction(COSClient client) {
        CreatePosterProductionRequest request = new CreatePosterProductionRequest();

        // 设置存储桶名称;
        request.setBucketName("demo-1234567890");
        // 设置处理对象的路径;
        request.getInput().setObject("/Images/test.jpg");
        // 设置模板 ID;
        request.getOperation().getPosterProduction().setTemplateId("6324349b569067d4d11a2c2c");

        // 设置Info信息
        request.getOperation().getPosterProduction().getInfo().setHeight("https://test-123456789.cos.ap-chongqing.myqcloud.com/demo.jpg");
        request.getOperation().getPosterProduction().getInfo().setName("demo");

        // 设置存储桶的地域;
        request.getOperation().getOutput().setRegion("ap-chongqing");
        // 设置存储结果的存储桶;
        request.getOperation().getOutput().setBucket("demo-1234567890");
        // 设置输出结果的文件名;
        request.getOperation().getOutput().setObject("/output/PosterProductionOut.jpg");
        // 设置透传用户信息, 可打印的 ASCII 码, 长度不超过1024;
        request.getOperation().setUserData("This is my data.");
        // 设置任务优先级，级别限制：0 、1 、2 。级别越大任务优先级越高，默认为0;
        request.getOperation().setJobLevel("0");
        // 设置任务回调格式，JSON 或 XML，默认 XML，优先级高于队列的回调格式;
        request.setCallBackFormat("JSON");
        // 设置任务回调地址，优先级高于队列的回调地址。设置为 no 时，表示队列的回调地址不产生回调;
        request.setCallBack("http://callback.demo.com");

        CreatePosterProductionResponse response = client.createPosterProduction(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
