package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.job.FileProcessJobResponse;
import com.qcloud.cos.model.ciModel.job.FileProcessRequest;

/**
 * 文件处理任务demo https://cloud.tencent.com/document/product/460/83091
 */
public class FileProcessJobDemo {
    public static void main(String[] args)  {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        createFileCompressJob(client);
    }

    /**
     * createFileCompressJob 接口提交文件压缩任务。
     * @param client
     */
    public static void createFileCompressJob(COSClient client)  {
        //1.创建任务请求对象
        FileProcessRequest request = new FileProcessRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("examplebucket-1250000000");
        //3.调用接口,获取任务响应对象
        FileProcessJobResponse response = client.createFileProcessJob(request);
        System.out.println(response);
    }
}
