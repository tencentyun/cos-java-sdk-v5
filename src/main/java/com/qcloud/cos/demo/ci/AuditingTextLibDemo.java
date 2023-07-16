package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingTextLibRequest;
import com.qcloud.cos.model.ciModel.auditing.AuditingTextLibResponse;
import com.qcloud.cos.utils.Jackson;

/**
 * 内容审核 自定义文本库相关demo 详情见https://cloud.tencent.com/document/product/460/76261
 */
public class AuditingTextLibDemo {

    public static void main(String[] args)  {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeAuditingTextLib(client);
    }

    /**
     * addAuditingTextLib 创建审核策略用于创建不同文件类型审核使用的策略，可以指定需要审核的标签。
     * 该接口属于 POST 请求。
     */
    public static void addAuditingTextLib(COSClient client) {
        AuditingTextLibRequest request = new AuditingTextLibRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setLibName("TextLib1");
        request.setMatchType("Exact");
        request.setSuggestion("Block");
        AuditingTextLibResponse response = client.addAuditingTextLib(request);
        System.out.println(Jackson.toJsonString(response));
    }



    /**
     * describeAuditingTextLib 查询审核策略详情用于查询指定策略的详细信息，包括开启的标签等。
     * 该接口属于 GET 请求。
     */
    public static void describeAuditingTextLib(COSClient client)  {
        //1.创建任务请求对象
        AuditingTextLibRequest request = new AuditingTextLibRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        request.setLimit(10);
        //3.调用接口,获取任务响应对象
        AuditingTextLibResponse response = client.describeAuditingTextLib(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * updateAuditingTextLib 修改审核策略用于修改已创建的策略的标签内容。
     * 该接口属于 PUT 请求。
     */
    public static void updateAuditingTextLib(COSClient client){
        //1.创建任务请求对象
        AuditingTextLibRequest request = new AuditingTextLibRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        request.setLibid("");
        //3.调用接口,获取任务响应对象
        AuditingTextLibResponse response = client.updateAuditingTextLib(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * deleteAuditingTextLib 修改审核策略用于修改已创建的策略的标签内容。
     * 该接口属于 PUT 请求。
     */
    public static void deleteAuditingTextLib(COSClient client){
        //1.创建任务请求对象
        AuditingTextLibRequest request = new AuditingTextLibRequest();
        request.setBucketName("markjrzhang-1251704708");
        //2.添加请求参数 参数详情请见api接口文档
        request.setLibid("c3caf149-12fc-4a84-8389-e47c34b27e66");
        //3.调用接口,获取任务响应对象
        AuditingTextLibResponse response = client.deleteAuditingTextLib(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
