package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.qcloud.cos.utils.Jackson;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品抠图 详情见https://cloud.tencent.com/document/product/460/79735
 */
public class GoodsMattingDemo {

    public static void main(String[] args) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        putGoodsMatting(client);
    }

    /**
     * 接口检测图片中的商品信息，生成只包含商品信息的图片
     * 下载时处理 demo
     */
    public static void getGoodsMatting(COSClient client) {
        //图片所在bucket名称
        String bucketName = "demo-1234567890";
        //图片在bucket中的相对位置，比如根目录下file文件夹中的demo.png路径为file/demo.png
        String key = "car.jpg";
        GetObjectRequest getObj = new GetObjectRequest(bucketName, key);
        //具体参数请参考API
        getObj.putCustomQueryParameter("ci-process", "GoodsMatting");
        getObj.putCustomQueryParameter("center-layout", "1");
        ObjectMetadata object = client.getObject(getObj, new File("demo.png"));
    }

    /**
     * 云上数据处理 demo
     */
    public static void postGoodsMatting(COSClient client) {
        String bucketName = "demo-1234567890";
        String key = "car.jpg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucketName);
        rule1.setFileId("cat2.jpg");
        rule1.setRule("ci-process=GoodsMatting&center-layout=1&padding-layout=20x10");
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);
        CIUploadResult ciUploadResult = client.processImage(imageReq);
        System.out.println(Jackson.toJsonString(ciUploadResult));
    }

    /**
     * 上传时处理demo
     */
    public static void putGoodsMatting(COSClient cosClient) {
        String bucketName = "demo-1234567890";
        String key = "car3.jpg";
        File localFile = new File("car.jpg");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule1 = new PicOperations.Rule();
        rule1.setBucket(bucketName);
        rule1.setFileId(key);
        rule1.setRule("ci-process=GoodsMatting&center-layout=1&padding-layout=20x10");
        ruleList.add(rule1);
        picOperations.setRules(ruleList);
        putObjectRequest.setPicOperations(picOperations);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        CIUploadResult ciUploadResult = putObjectResult.getCiUploadResult();
        System.out.println(putObjectResult.getRequestId());
        System.out.println(Jackson.toJsonString(putObjectResult));

    }
}
