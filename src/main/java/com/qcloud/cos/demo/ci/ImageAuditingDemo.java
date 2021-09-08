package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingInfo;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelRequest;
import com.qcloud.cos.transfer.CIPostJob;
import com.qcloud.cos.transfer.TransferManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 内容审核 图片审核接口相关demo 详情见https://cloud.tencent.com/document/product/460/37318
 */
public class ImageAuditingDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        imageAuditing(client);
    }

    /**
     * createImageAuditingJob 接口用于创建图片审核任务。
     *
     * @param client
     */
    public static void imageAuditing(COSClient client) {
        //1.创建任务请求对象
        ImageAuditingRequest request = new ImageAuditingRequest();
        //2.添加请求参数 参数详情请见api接口文档
        //2.1设置请求bucket
        request.setBucketName("demo-123456789");
        //2.2设置审核类型
        request.setDetectType("porn");
        //2.3设置bucket中的图片位置
        request.setObjectKey("1.png");
        //3.调用接口,获取任务响应对象
        ImageAuditingResponse response = client.imageAuditing(request);
        //4调用工具类，获取各审核类型详情集合 (也可自行根据业务解析)
        List<AuditingInfo> imageInfoList = AuditingResultUtil.getImageInfoList(response);
    }

    /**
     * 批量获取图片标签
     */
    protected static void batchPostImageAuditing(COSClient client) {
        List<ImageAuditingRequest> requestList = new ArrayList<>();
        ImageAuditingRequest request = new ImageAuditingRequest();
        request.setBucketName("demo-123456789");
        request.setObjectKey("1.png");
        requestList.add(request);

        request = new ImageAuditingRequest();
        request.setBucketName("demo-123456789");
        request.setObjectKey("2.png");
        requestList.add(request);

        ExecutorService threadPool = Executors.newFixedThreadPool(16);
        // 传入一个threadpool, 若不传入线程池, 默认TransferManager中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(client, threadPool);
        CIPostJob ciPostJob = transferManager.batchPostImageAuditing(requestList);
        // 或者阻塞等待完成
        ciPostJob.waitForCompletion();
    }
}
