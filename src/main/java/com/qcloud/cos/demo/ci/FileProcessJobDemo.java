package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.DocJobRequest;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.FileCompressConfig;
import com.qcloud.cos.model.ciModel.job.FileProcessJobResponse;
import com.qcloud.cos.model.ciModel.job.FileProcessJobType;
import com.qcloud.cos.model.ciModel.job.FileProcessRequest;
import com.qcloud.cos.utils.Jackson;

import java.util.List;

/**
 * 文件处理任务demo https://cloud.tencent.com/document/product/460/83091
 */
public class FileProcessJobDemo {
    public static void main(String[] args)  {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        describeFileProcessJob(client);
    }

    /**
     * createFileCompressJob 提交文件压缩任务。
     * @param client
     */
    public static void createFileCompressJob(COSClient client)  {
        //1.创建任务请求对象
        FileProcessRequest request = new FileProcessRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setTag(FileProcessJobType.FileCompress);
        FileCompressConfig fileCompressConfig = request.getOperation().getFileCompressConfig();
        fileCompressConfig.setFormat("zip");
        fileCompressConfig.setFlatten("0");
        List<String> keyList = fileCompressConfig.getKey();
        keyList.add("mark/pic-1.jpg");
        keyList.add("mark/pic-1.pdf");

        request.setQueueId("p1ff062b35a494cf0ac4b572df22a5650");

        MediaOutputObject output = request.getOperation().getOutput();
        output.setBucket("demo-1234567890");
        output.setRegion("ap-shanghai");
        output.setObject("output/demo.zip");

        //3.调用接口,获取任务响应对象
        FileProcessJobResponse response = client.createFileProcessJob(request);
        System.out.println(response.getJobDetail().getJobId());
    }

    /**
     * describeFileProcessJob 根据jobId查询任务信息
     *
     * @param client
     */
    public static void describeFileProcessJob(COSClient client) {
        //1.创建任务请求对象
        FileProcessRequest request = new FileProcessRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("demo-1234567890");
        request.setJobId("fda7eb1607b8411ed8c182156726*****");
        //3.调用接口,获取任务响应对象
        FileProcessJobResponse response = client.describeFileProcessJob(request);
        System.out.println(Jackson.toJsonString(response));
    }
}
