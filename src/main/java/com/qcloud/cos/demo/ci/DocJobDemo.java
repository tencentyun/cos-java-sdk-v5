package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 文档预览任务相关demo
 */
public class DocJobDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeMediaJob(client);
    }

    /**
     * createMediaJobs 接口用于创建任务。
     * @param client
     */
    public static void createDocJobs(COSClient client) throws UnsupportedEncodingException {
        //1.创建任务请求对象
        DocJobRequest request = new DocJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        DocJobObject docJobObject = request.getDocJobObject();
        docJobObject.setTag("DocProcess");
        docJobObject.getInput().setObject("1.xlsx");
        docJobObject.setQueueId("pc02270c617ae4b6d9b0a52cb1cfce6b3");
        DocProcessObject docProcessObject = docJobObject.getOperation().getDocProcessObject();
        docProcessObject.setQuality("100");
        docProcessObject.setZoom("100");
        docProcessObject.setStartPage("1");
        docProcessObject.setEndPage("-1");
        MediaOutputObject output = docJobObject.getOperation().getOutput();
        output.setRegion("ap-chongqing");
        output.setBucket("markjrzhang-1251704708");
        output.setObject("mark/${SheetID}/pic-${Page}.jpg");
        //3.调用接口,获取任务响应对象
        DocJobResponse docProcessJobs = client.createDocProcessJobs(request);
        System.out.println(docProcessJobs);
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     * @param client
     */
    public static void describeMediaJob(COSClient client)  {
        //1.创建任务请求对象
        DocJobRequest request = new DocJobRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("markjrzhang-1251704708");
        request.setJobId("dd1578e1837d411eba4fc5989c567bd5d");
        //3.调用接口,获取任务响应对象
        DocJobResponse docJobResponse = client.describeDocProcessJob(request);
        System.out.println(docJobResponse);
    }

    /**
     * describeMediaJobs 查询任务列表
     * @param client
     */
    public static void describeMediaJobs(COSClient client)  {
//        //1.创建任务请求对象
//        DocJobRequest request = new D();
//        //2.添加请求参数 参数详情请见api接口文档
//        request.setBucketName("markjrzhang-1251704708");
//        request.setQueueId("dd1578e1837d411eba4fc5989c567bd5d");
//        request.setTag("DocProcess");
//        //3.调用接口,获取任务响应对象
//        DocJobResponse docJobResponse = client.describeDocProcessJobs(request);
//        List<MediaJobObject> jobsDetail = docJobResponse.get();
//        for (MediaJobObject mediaJobObject : jobsDetail) {
//            System.out.println(mediaJobObject);
//        }
    }
}
