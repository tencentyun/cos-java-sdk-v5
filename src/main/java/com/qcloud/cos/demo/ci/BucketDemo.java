package com.qcloud.cos.demo.ci;

import com.qcloud.cos.CIClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;

public class BucketDemo {

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
        describeMediaBuckets(client);
    }

    /**
     * DescribeMediaBuckets 接口用于查询存储桶是否已开通媒体处理功能。
     * @param client
     */
    public static void describeMediaBuckets(COSClient client){
        MediaBucketRequest request = new MediaBucketRequest();
        request.setBucketName("markjrzhang-1251704708");
        MediaBucketResponse response = client.describeMediaBuckets(request);
        System.out.println(response);
    }
}
