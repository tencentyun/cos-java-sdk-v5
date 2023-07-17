package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyListResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyRequest;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyResponse;
import com.qcloud.cos.model.ciModel.auditing.StrategyImageLabel;
import com.qcloud.cos.model.ciModel.auditing.StrategyLabels;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 内容审核 审核策略相关demo 详情见https://cloud.tencent.com/document/product/460/76261
 */
public class AuditingStrategyDemo {

    public static void main(String[] args) throws InterruptedException {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        addAuditingStrategy(client);
    }

    /**
     * addAuditingStrategy 创建审核策略用于创建不同文件类型审核使用的策略，可以指定需要审核的标签。
     * 该接口属于 POST 请求。
     */
    public static void addAuditingStrategy(COSClient client) {
        AuditingStrategyRequest request = new AuditingStrategyRequest();
        request.setBucketName("demo-1234567890");
        request.setName("Strategy1");
        request.setService("Image");

        StrategyLabels labels = request.getLabels();
        StrategyImageLabel image = labels.getImage();
        List<String> politics = image.getPolitics();
        politics.add("NegativeFigure");
        politics.add("ForeignLeaders");

        AuditingStrategyResponse response = client.addAuditingStrategy(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * describeAuditingStrategy 查询审核策略详情用于查询指定策略的详细信息，包括开启的标签等。
     * 该接口属于 GET 请求。
     */
    public static void describeAuditingStrategy(COSClient client)  {
        //1.创建任务请求对象
        AuditingStrategyRequest request = new AuditingStrategyRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setService("Image");
        request.setBizType("1418ef31246a11eebdef52540032dad3");
        //3.调用接口,获取任务响应对象
        AuditingStrategyResponse response = client.describeAuditingStrategy(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * updateAuditingStrategy 修改审核策略用于修改已创建的策略的标签内容。
     * 该接口属于 PUT 请求。
     */
    public static void updateAuditingStrategy(COSClient client){
        //1.创建任务请求对象
        AuditingStrategyRequest request = new AuditingStrategyRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setService("Image");
        request.setBizType("1418ef31246a11eebdef52540032dad3");
        StrategyLabels labels = request.getLabels();
        StrategyImageLabel image = labels.getImage();
        List<String> politics = image.getPolitics();
        politics.add("NegativeFigure");
        politics.add("ForeignLeaders");

        List<String> pron = image.getPorn();
        pron.add("Sexy");
        pron.add("OcrText");
        //3.调用接口,获取任务响应对象
        AuditingStrategyResponse response = client.updateAuditingStrategy(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * describeAuditingStrategyList 查询审核策略列表
     * 该接口属于 GET 请求。
     */
    public static void describeAuditingStrategyList(COSClient client)  {
        //1.创建任务请求对象
        AuditingStrategyRequest request = new AuditingStrategyRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setService("Image");
        //3.调用接口,获取任务响应对象
        AuditingStrategyListResponse response = client.describeAuditingStrategyList(request);
        System.out.println(Jackson.toJsonString(response));
    }

}
