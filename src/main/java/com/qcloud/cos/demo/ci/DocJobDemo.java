package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.*;

/**
 * 文档预览任务相关demo
 */
public class DocJobDemo {
    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createDocJobs(client);
    }

    /**
     * createMediaJobs 接口用于创建任务。
     *
     * @param client
     */
    public static void createDocJobs(COSClient client) {
        //1.创建任务请求对象
        DocJobRequest request = new DocJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("examplebucket-1250000000");
        DocJobObject docJobObject = request.getDocJobObject();
        docJobObject.setTag("DocProcess");
        docJobObject.getInput().setObject("demo.docx");
        docJobObject.setQueueId("pc02270c617ae4b6d9b0a52cb1c*****");
        DocProcessObject docProcessObject = docJobObject.getOperation().getDocProcessObject();
        docProcessObject.setQuality("100");
        docProcessObject.setZoom("100");
        docProcessObject.setStartPage("1");
        docProcessObject.setEndPage("3");
        docProcessObject.setTgtType("png");
        docProcessObject.setDocPassword("123");
        MediaOutputObject output = docJobObject.getOperation().getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("examplebucket-1250000000");
        output.setObject("mark/pic-${Page}.jpg");
        //3.调用接口,获取任务响应对象
        DocJobResponse docProcessJobs = client.createDocProcessJobs(request);
        System.out.println(docProcessJobs);
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        DocJobRequest request = new DocJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("examplebucket-1250000000");
        request.setJobId("d75b6ea083df711eb8d09476dfb8*****");
        //3.调用接口,获取任务响应对象
        DocJobResponse docJobResponse = client.describeDocProcessJob(request);
        System.out.println(docJobResponse);
    }

    /**
     * describeMediaJobs 查询任务列表
     *
     * @param client
     */
    public static void describeMediaJobs(COSClient client) {
        //1.创建任务请求对象
        DocJobListRequest request = new DocJobListRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("examplebucket-1250000000");
        request.setQueueId("pc02270c617ae4b6d9b0a52cb1c*****");
        request.setTag("DocProcess");
        request.setStartCreationTime("2020-12-10T16:20:07+0800");
        //3.调用接口,获取任务响应对象
        DocJobListResponse docJobResponse = client.describeDocProcessJobs(request);
        for (DocJobDetail jobDetail : docJobResponse.getDocJobDetailList()) {
            System.out.println(jobDetail);
        }
    }
}
