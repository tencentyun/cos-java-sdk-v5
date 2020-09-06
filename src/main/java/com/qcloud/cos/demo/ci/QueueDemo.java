package com.qcloud.cos.demo.ci;

import com.qcloud.cos.CIClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaQueueResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;

import java.io.UnsupportedEncodingException;

public class QueueDemo {

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "secretId";
        String secretKey = "secretKey";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, CI 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 https), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("chongqing");
        CIClientConfig clientConfig = new CIClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient client = new COSClient(cred, clientConfig);


        // 4 调用要使用的方法。
        describeMediaQueues(client);
//        updateMediaQueue(client);
    }

    /**
     * DescribeMediaQueues 接口用于搜索队列。
     * @param client
     */
    public static void describeMediaQueues(COSClient client){
        MediaQueueRequest request = new MediaQueueRequest();
        request.setBucketName("markjrzhang-1251704708");
        MediaListQueueResponse response = client.describeMediaQueues(request);
        System.out.println(response);
    }

    /**
     * UpdateMediaQueue 接口用于更新队列
     * Request中 Name,QueueID,State,NotifyConfig 为必填字段
     * @param client
     */
    public static void updateMediaQueue(COSClient client) throws UnsupportedEncodingException {
        MediaQueueRequest request = new MediaQueueRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.setQueueId("p9900025e4ec44b5e8225e70a52170834");
        request.getNotifyConfig().setUrl("cloud.tencent.com");
        request.setState("Active");
        request.setName("testQueue");
        MediaQueueResponse response = client.updateMediaQueue(request);
        System.out.println(response);
    }
}
