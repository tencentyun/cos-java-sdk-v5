package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.image.ImageSlimRequest;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片极智压缩功能Demo
 * https://cloud.tencent.com/document/product/460/94856
 */
public class ImageSlimDemo {

    public static void main(String[] args) {
        // 初始化客户端
        COSClient client = ClientUtils.getTestClient();

        imageSlimProcessDemo(client);

    }

    public static void imageSlimDownloadDemo(COSClient client) {
        try {
            String bucketName = "demo-1251704708";
            String key = "test1.jpeg";

            ImageSlimRequest request = new ImageSlimRequest(bucketName, key);

            // 执行下载时处理
            InputStream inputStream = client.imageSlimDownload(request);

            // 保存压缩后的图片
            String outputFileName = "slim_download_" + key.substring(key.lastIndexOf("/") + 1);
            try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println("压缩后图片已保存到: " + outputFileName);
            }

            inputStream.close();

        } catch (Exception e) {
            System.err.println("下载时处理失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void imageSlimProcessDemo(COSClient client) {

        String bucketName = "chongqingtest-1251704708";
        String key = "SDK/Images/SDK/Images/13123213_cloud_slim.png";

        ImageProcessRequest imageProcessRequest = new ImageProcessRequest(bucketName, key);

        PicOperations picOperations = new PicOperations();

        picOperations.setIsPicInfo(1);

        List<PicOperations.Rule> rules = new ArrayList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId("slim_processed_" + key);
        rule.setRule("imageSlim");
        rules.add(rule);

        picOperations.setRules(rules);
        imageProcessRequest.setPicOperations(picOperations);

        CIUploadResult result = client.imageSlimProcess(imageProcessRequest);

    }
}