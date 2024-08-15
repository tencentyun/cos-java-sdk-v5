package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.CompleteMultipartUploadRequest;
import com.qcloud.cos.model.CompleteMultipartUploadResult;
import com.qcloud.cos.model.InitiateMultipartUploadRequest;
import com.qcloud.cos.model.InitiateMultipartUploadResult;
import com.qcloud.cos.model.PartETag;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.UploadPartRequest;
import com.qcloud.cos.model.UploadPartResult;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * 盲水印相关demo API 接口说明：https://cloud.tencent.com/document/product/436/46782
 */
public class BlindWatermarkDemo {
    /**
     * 上传时添加盲水印
     */
    public static void addBlindWatermark(COSClient cosClient) {
        String bucketName = "markjrzhang-1251704708";
        String key = "qrcode.jpg";
        File localFile = new File("demo.jpg");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId("BlindWatermark.jpg");
        // 使用盲水印功能，水印图的宽高不得超过原图的1/8
        rule.setRule("watermark/3/type/1/image/aHR0cHM6Ly9tYXJranJ6aGFuZy0xMjUxNzA0NzA4LmNvcy5hcC1jaG9uZ3FpbmcubXlxY2xvdWQuY29tL3NodWl5aW4uanBn");

        ruleList.add(rule);
        picOperations.setRules(ruleList);
        putObjectRequest.setPicOperations(picOperations);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            CIUploadResult ciUploadResult = putObjectResult.getCiUploadResult();
            System.out.println(putObjectResult.getRequestId());
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for(CIObject ciObject:ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
            }
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传时获取盲水印
     */
    public static void getBlindWatermark(COSClient cosClient) {
        String bucketName = "markjrzhang-1251704708";
        String key = "qrcode.jpg";
        File localFile = new File("BlindWatermark.jpg");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId("BlindWatermark-test.jpg");
        // 使用盲水印功能，水印图的宽高不得超过原图的1/8
        rule.setRule("watermark/4/type/2/image/aHR0cHM6Ly9tYXJranJ6aGFuZy0xMjUxNzA0NzA4LmNvcy5hcC1jaG9uZ3FpbmcubXlxY2xvdWQuY29tL3NodWl5aW4uanBn");

        ruleList.add(rule);
        picOperations.setRules(ruleList);
        putObjectRequest.setPicOperations(picOperations);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            CIUploadResult ciUploadResult = putObjectResult.getCiUploadResult();
            System.out.println(putObjectResult.getRequestId());
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for(CIObject ciObject:ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
            }
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    public static void addBlindWatermarkToExistImage(COSClient cosClient) {
        // bucket名需包含appid
        // api 请参考：https://cloud.tencent.com/document/product/436/46782
        String bucketName = "examplebucket-1250000000";

        String key = "image/dog.jpg";
        ImageProcessRequest imageProcessRequest = new ImageProcessRequest(bucketName, key);
        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId("/image/result/dog.jpg");
        // 使用盲水印功能，水印图的宽高不得超过原图的1/8
        rule.setRule("watermark/3/type/2/image/aHR0cHM6Ly9tYXJranJ6aGFuZy0xMjUxNzA0NzA4LmNvcy5hcC1jaG9uZ3FpbmcubXlxY2xvdWQuY29tL3NodWl5aW4uanBn");

        ruleList.add(rule);
        picOperations.setRules(ruleList);
        imageProcessRequest.setPicOperations(picOperations);
        try {
            CIUploadResult ciUploadResult = cosClient.processImage(imageProcessRequest);
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for(CIObject ciObject:ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
            }
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 云上数据处理
     */
    public static void extractBlindWatermarkFromExistImage(COSClient cosClient) {
        // bucket名需包含appid
        // api 请参考：https://cloud.tencent.com/document/product/436/46782
        String bucketName = "examplebucket-1250000000";

        String key = "image/result/dog.jpg";
        ImageProcessRequest imageProcessRequest = new ImageProcessRequest(bucketName, key);
        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId("/image/result/extract-shuiyin.jpg");
        // 抽取盲水印
        rule.setRule("watermark/4/type/2/image/aHR0cDovL2V4YW1wbGVidWNrZXQtMTI1MDAwMDAwMC5jb3MuYXAtZ3Vhbmd6aG91Lm15cWNsb3VkLmNvbS9zaHVpeWluLnBuZw==");

        ruleList.add(rule);
        picOperations.setRules(ruleList);
        imageProcessRequest.setPicOperations(picOperations);
        try {
            CIUploadResult ciUploadResult = cosClient.processImage(imageProcessRequest);
            System.out.println(ciUploadResult.getOriginalInfo().getEtag());
            for(CIObject ciObject:ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println(ciObject.getLocation());
            }
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        COSClient cosClient = ClientUtils.getTestClient();
        getBlindWatermark(cosClient);
        cosClient.shutdown();
    }
}
