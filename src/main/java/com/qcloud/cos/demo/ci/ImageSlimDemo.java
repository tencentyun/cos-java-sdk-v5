package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;

import java.io.File;
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
        downloadTimeImageSlim(client);
    }

    /**
     * 极智压缩 - 上传时处理示例
     */
    public static void uploadTimeImageSlim(COSClient client) {
        // 1. 设置基础参数
        String bucketName = "demo-1251704708";
        String sourceKey = "/upload_source.jpg";
        String targetKey = "/upload_slim.jpg";
        File localFile = new File("test.png");
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
    }

    /**
     * 极智压缩 - 下载时处理示例
     */
    public static void downloadTimeImageSlim(COSClient client) {
            String bucketName = "chongqingtest-1251704708";
            String key = "/SDK/Images/13123213_download_test.png";

            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            String compress = "imageSlim";
            getObjectRequest.putCustomQueryParameter(compress, null);
            client.getObject(getObjectRequest, new File("imageSilm.png"));
    }

        /**
         * 极智压缩 - 云上数据处理示例
         */
    public static void cloudDataImageSlim(COSClient client) {
        // 1. 设置基础参数
        String bucketName = "demo-1251704708";
        String sourceKey = "/cloud_source.jpg";
        String targetKey = "/cloud_slim.jpg";
        // 2. 创建云上处理请求
        ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, sourceKey);
        // 3. 配置图片处理参数
        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        // 4. 设置处理规则
        List<PicOperations.Rule> ruleList = new ArrayList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId(targetKey);
        rule.setRule("imageSlim");
        ruleList.add(rule);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);
        // 5. 执行云上数据处理
        CIUploadResult ciUploadResult = client.processImage(imageReq);
    }
}