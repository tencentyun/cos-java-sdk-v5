package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.snapshot.CosSnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * GenerateSnapshot 接口用于获取媒体文件某个时间的截图，输出的截图统一为 jpeg 格式。
 * 请求详情参见：https://cloud.tencent.com/document/product/460/38934
 */
public class SnapshotDemo {
    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        getSnapshot(client);
    }

    /**
     * GetSnapshot 接口用于获取媒体文件某个时间的截图。 (推荐)
     */
    public static void getSnapshot(COSClient client) throws IOException {
        //1.创建截图请求对象
        CosSnapshotRequest request = new CosSnapshotRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-123456789");
        request.setObjectKey("1.mp4");
        request.setTime("15");
        request.setFormat("jpg");
        //3.调用接口,获取截图响应对象
        InputStream is = null;
        try {
            is = client.getSnapshot(request);
            inputStream2File(is, new File("1.jpg"));
        } finally {
            close(is);
        }
    }

    /**
     * generateSnapshot 接口用于获取媒体文件某个时间的截图并生成至bucket中。
     */
    public static void generateSnapshot(COSClient client) {
        //1.创建截图请求对象
        SnapshotRequest request = new SnapshotRequest();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName("DemoBucket-123456789");
        request.getInput().setObject("1.mp4");
        request.getOutput().setBucket("DemoBucket-123456789");
        request.getOutput().setRegion("ap-chongqing");
        request.getOutput().setObject("test/1.jpg");
        request.setTime("15");
        //3.调用接口,获取截图响应对象
        SnapshotResponse response = client.generateSnapshot(request);
        System.out.println(response);
    }

    private static void inputStream2File(InputStream is, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] read = new byte[4096];
            int count;
            while ((count = is.read(read)) != -1) {
                outputStream.write(read, 0, count);
            }
        }
    }

    private static void close(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
