package com.qcloud.cos.demo.ci;

import java.util.List;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingInfo;
import com.qcloud.cos.model.ciModel.auditing.CallbackVersion;
import com.qcloud.cos.model.ciModel.auditing.ReportBadCaseRequest;
import com.qcloud.cos.model.ciModel.auditing.SuggestedLabel;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingResponse;

/**
 * 内容审核 文本审核接口相关demo 详情见https://cloud.tencent.com/document/product/436/56289
 */
public class TextAuditingJobDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createAuditingTextJobs(client);
    }

    /**
     * createAuditingTextJobs 接口用于创建文本审核任务。
     */
    public static void createAuditingTextJobs(COSClient client) {
        //1.创建任务请求对象
        TextAuditingRequest request = new TextAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        //2.1.1设置对象地址
//        request.getInput().setObject("1.txt");
        //2.1.2或直接设置请求内容,文本内容的Base64编码
        request.getInput().setContent("base64Str");
        //2.2设置审核模板（可选）
        request.getConf().setBizType("7e114b6ebaaf11eea5da5254000*****");
        //设置回调信息内容类型 simple精简 Detail详细
        request.getConf().setCallbackVersion(CallbackVersion.Simple);
        //3.调用接口,获取任务响应对象
        TextAuditingResponse response = client.createAuditingTextJobs(request);
    }

    /**
     * DescribeAuditingJob 接口用于查询文本审核任务。
     *
     * @param client
     */
    public static void describeAuditingTextJob(COSClient client) {
        //1.创建任务请求对象
        TextAuditingRequest request = new TextAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-123456789");
        request.setJobId("st68d08596f35011eb9324525400*****");
        //3.调用接口,获取任务响应对象
        TextAuditingResponse response = client.describeAuditingTextJob(request);
    }

    /**
     * reportBadCase 接口用于文本审核结果反馈。
     *
     * @param client
     */
    public static void reportBadCase(COSClient client) {
        //1.创建任务请求对象
        ReportBadCaseRequest request = new ReportBadCaseRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-123456789");
        request.setContentType("1");
        request.setLabel("porn");
        request.setSuggestedLabel(SuggestedLabel.Normal);
        request.setText("6L+Z5piv5Li65LuA5LmI");
        request.setJobId("st68d08596f35011eb9324525400*****");
        //3.调用接口,获取任务响应对象 上报成功则返回
        String requestId = client.reportBadCase(request);
    }
}
