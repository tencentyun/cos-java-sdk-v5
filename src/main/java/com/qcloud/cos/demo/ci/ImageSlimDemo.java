package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.image.ImageSlimRequest;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片极智压缩功能Demo
 * 支持三种处理方式：下载时处理、上传时处理、云上数据处理
 * https://cloud.tencent.com/document/product/460/94856
 */
public class ImageSlimDemo {

    public static void main(String[] args) {
        // 1.初始化客户端
        COSClient client = ClientUtils.getTestClient();
        // 2.执行对应方法
        uploadTimeImageSlim(client);
    }

    /**
     * 极智压缩 - 上传时处理示例
     */
    public static void uploadTimeImageSlim(COSClient client) {
        try {
            // 1. 设置基础参数
            String bucketName = "demo-1251704708";
            String sourceKey = "/upload_source.jpg";
            String targetKey = "/upload_slim.jpg";
            File localFile = new File("test.png");

            System.out.println("原始文件大小: " + localFile.length() + " bytes");

            // 2. 创建上传请求
            PutObjectRequest putRequest = new PutObjectRequest(bucketName, sourceKey, localFile);

            // 3. 配置图片处理参数
            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);

            // 4. 设置处理规则
            List<PicOperations.Rule> ruleList = new ArrayList<>();
            PicOperations.Rule rule = new PicOperations.Rule();
            rule.setBucket(bucketName);
            rule.setFileId(targetKey);
            // 极智压缩规则
            rule.setRule("imageSlim");
            ruleList.add(rule);

            picOperations.setRules(ruleList);
            putRequest.setPicOperations(picOperations);

            // 5. 执行上传时处理
            PutObjectResult putResult = client.putObject(putRequest);
            CIUploadResult ciUploadResult = putResult.getCiUploadResult();

            // 6. 输出处理结果
            if (ciUploadResult.getOriginalInfo() != null) {
                System.out.println("原图信息:");
                System.out.println("  ETag: " + ciUploadResult.getOriginalInfo().getEtag());
                System.out.println("  ImageInfo: " + ciUploadResult.getOriginalInfo().getImageInfo());
            }

            if (ciUploadResult.getProcessResults() != null &&
                    ciUploadResult.getProcessResults().getObjectList() != null) {

                System.out.println("处理结果:");
                for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                    System.out.println("  处理后文件: " + ciObject.getLocation());
                    System.out.println("  文件ETag: " + ciObject.getEtag());
                    System.out.println("  文件大小: " + ciObject.getSize() + " bytes");

                    // 计算压缩率
                    if (ciObject.getSize() > 0) {
                        double compressionRatio = (1.0 - (double) ciObject.getSize() / localFile.length()) * 100;
                        System.out.println("  压缩率: " + String.format("%.2f", compressionRatio) + "%");
                    }
                }
            }

            // 7. 清理测试文件
            client.deleteObject(bucketName, sourceKey);
            client.deleteObject(bucketName, targetKey);

        } catch (Exception e) {
            System.err.println("上传时处理失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 极智压缩 - 下载时处理示例
     */
    public static void downloadTimeImageSlim(COSClient client) {
        try {
            // 1. 设置基础参数
            String bucketName = "demo-1251704708";
            String key = "download_test.jpg";
            File localFile = new File("test.png");

            System.out.println("原始文件大小: " + localFile.length() + " bytes");

            // 2. 先上传原图
            client.putObject(bucketName, key, localFile);

            // 3. 下载压缩图片
            ImageSlimRequest request = new ImageSlimRequest(bucketName, key);
            InputStream inputStream = client.imageSlimDownload(request);

            // 4. 保存压缩后的图片
            String outputFileName = "download_slim_result.jpg";
            try (FileOutputStream fos = new FileOutputStream(outputFileName)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                long totalBytes = 0;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }

                System.out.println("压缩后文件大小: " + totalBytes + " bytes");
                System.out.println("压缩后图片已保存到: " + outputFileName);

                // 5. 计算压缩率
                if (totalBytes < localFile.length()) {
                    double compressionRatio = (1.0 - (double) totalBytes / localFile.length()) * 100;
                    System.out.println("压缩率: " + String.format("%.2f", compressionRatio) + "%");
                }
            }

            inputStream.close();

            // 6. 清理测试文件
            client.deleteObject(bucketName, key);
            new File(outputFileName).delete();

        } catch (Exception e) {
            System.err.println("下载时处理失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 极智压缩 - 云上数据处理示例
     */
    public static void cloudDataImageSlim(COSClient client) {
        try {
            // 1. 设置基础参数
            String bucketName = "demo-1251704708";
            String sourceKey = "/cloud_source.jpg";
            String targetKey = "/cloud_slim.jpg";
            File localFile = new File("/test.png");

            System.out.println("原始文件大小: " + localFile.length() + " bytes");

            // 2. 上传原图
            client.putObject(bucketName, sourceKey, localFile);

            // 3. 创建云上处理请求
            ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, sourceKey);

            // 4. 配置图片处理参数
            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);

            // 5. 设置处理规则
            List<PicOperations.Rule> ruleList = new ArrayList<>();
            PicOperations.Rule rule = new PicOperations.Rule();
            rule.setBucket(bucketName);
            rule.setFileId(targetKey);
            rule.setRule("imageSlim");
            ruleList.add(rule);

            picOperations.setRules(ruleList);
            imageReq.setPicOperations(picOperations);

            // 6. 执行云上数据处理
            CIUploadResult ciUploadResult = client.processImage(imageReq);

            // 7. 输出处理结果
            if (ciUploadResult.getOriginalInfo() != null) {
                System.out.println("原图信息:");
                System.out.println("  ETag: " + ciUploadResult.getOriginalInfo().getEtag());
            }

            if (ciUploadResult.getProcessResults() != null &&
                    ciUploadResult.getProcessResults().getObjectList() != null) {

                System.out.println("处理结果:");
                for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                    System.out.println("  处理后文件: " + ciObject.getLocation());
                    System.out.println("  文件ETag: " + ciObject.getEtag());
                    System.out.println("  文件大小: " + ciObject.getSize() + " bytes");

                    // 计算压缩率
                    if (ciObject.getSize() > 0) {
                        double compressionRatio = (1.0 - (double) ciObject.getSize() / localFile.length()) * 100;
                        System.out.println("  压缩率: " + String.format("%.2f", compressionRatio) + "%");
                    }
                }
            }

            // 8. 清理测试文件
            client.deleteObject(bucketName, sourceKey);
            client.deleteObject(bucketName, targetKey);

        } catch (Exception e) {
            System.err.println("云上数据处理失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}