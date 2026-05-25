package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;import com.qcloud.cos.model.ciModel.job.v2.VirusDetectConf;import com.qcloud.cos.model.ciModel.job.v2.VirusDetectInput;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectJobRequest;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectJobResponse;
import com.qcloud.cos.model.ciModel.job.v2.VirusDetectRequest;import com.qcloud.cos.model.ciModel.job.v2.VirusDetectResponse;import com.qcloud.cos.utils.Jackson;public class VirusDetectDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）
        COSClient client = ClientUtils.getTestClient();

        // 2 调用要使用的方法
        virusDetectWithCOSObject(client);
    }

    /**
     * 使用COS中存储的文件进行病毒检测
     */
    public static void virusDetectWithCOSObject(COSClient client) {
        VirusDetectRequest request = new VirusDetectRequest();
        request.setBucketName("demo-1234567890");

        // 设置输入文件（COS中的文件）
        VirusDetectInput input = request.getInput();
        input.setObject("test/virus-test.doc");

        // 设置检测配置
        VirusDetectConf conf = request.getConf();
        conf.setDetectType("Virus");
        conf.setCallback("http://www.callback.com");

        // 执行病毒检测
        VirusDetectResponse response = client.createVirusDetectJob(request);
        System.out.println("病毒检测任务（COS文件）响应: " + Jackson.toJsonString(response));
    }


    /**
     * 查询病毒检测任务结果
     */
public static void describeVirusDetectJob(COSClient client, String jobId) {
    VirusDetectJobRequest request = new VirusDetectJobRequest();
    request.setBucketName("demo-1234567890");
    request.setJobId(jobId);

    // 查询病毒检测任务结果
    VirusDetectJobResponse response = client.describeVirusDetectJob(request);
    System.out.println("病毒检测任务查询响应: " + Jackson.toJsonString(response));

    if (response.getJobsDetail() != null) {
        System.out.println("任务状态: " + response.getJobsDetail().getState());
        System.out.println("检测建议: " + response.getJobsDetail().getSuggestion());

        // 如果有检测详情，打印病毒信息
        if (response.getJobsDetail().getDetectDetail() != null &&
                response.getJobsDetail().getDetectDetail().getResult() != null) {
            System.out.println("检测到的病毒数量: " + response.getJobsDetail().getDetectDetail().getResult().size());
            response.getJobsDetail().getDetectDetail().getResult().forEach(result -> {
                System.out.println("文件名: " + result.getFileName() + ", 病毒名: " + result.getVirusName());
            });
        }
    }
}
}
