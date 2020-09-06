package com.qcloud.cos.demo.ci;

import com.qcloud.cos.CIClientConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;

import java.io.UnsupportedEncodingException;

/**
 * GenerateSnapshot 接口用于获取媒体文件某个时间的截图，输出的截图统一为 jpeg 格式。
 * 请求详情参见：https://cloud.tencent.com/document/product/460/38934
 */
public class SnapshotDemo {
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
        generateSnapshot(client);
    }

    public static void generateSnapshot(COSClient client) throws UnsupportedEncodingException {
        SnapshotRequest request = new SnapshotRequest();
        request.setBucketName("markjrzhang-1251704708");
        request.getInput().setObject("1.mp4");
        request.getOutput().setBucket("markjrzhang-1251704708");
        request.getOutput().setRegion("ap-chongqing");
        request.getOutput().setObject("test/1.jpg");
        request.setTime("15");
        SnapshotResponse response = client.generateSnapshot(request);
        System.out.println(response);
    }
}
