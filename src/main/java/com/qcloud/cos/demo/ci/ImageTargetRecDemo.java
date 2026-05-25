package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.CiServiceResult;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import com.qcloud.cos.utils.Jackson;

import java.util.LinkedList;
import java.util.List;

/**
 * 图片智能打码 Demo
 * 识别图片中的车辆、人体、人脸、车牌等目标，返回目标位置信息并进行打码。
 * 文档链接：https://cloud.tencent.com/document/product/460/118934
 *
 * ci-process=ImageTargetRec
 * detect-type: 识别类型，可选值：face(人脸)，plate(车牌)，body(人体)，car(车辆)，多个类型通过","分割。默认值为：face,plate
 * cover: 是否打码，0（不打码），1（打码），默认值为：0
 */
public class ImageTargetRecDemo {

    public static void main(String[] args) throws Exception {
        COSClient cosClient = ClientUtils.getTestClient();
        // 1. 识别人脸和车牌并打码（默认类型）
        imageTargetRecDefaultCover(cosClient);
        // 2. 识别所有类型（人脸+车牌+人体+车辆）并打码
        imageTargetRecAllTypes(cosClient);
        // 3. 仅识别不打码（获取坐标信息）
        imageTargetRecDetectOnly(cosClient);
        cosClient.shutdown();
    }

    /**
     * 识别人脸和车牌并打码（默认detect-type）
     * 使用云上数据处理方式（对已存储在COS中的图片进行处理）
     */
    public static void imageTargetRecDefaultCover(COSClient cosClient) {
        String bucketName = "demo-1234567890";
        String key = "test.jpg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId("output/test_mosaic.jpg");
        // ci-process=ImageTargetRec: 图片智能打码
        // detect-type=face,plate: 识别人脸和车牌（默认值）
        // cover=1: 对识别到的目标进行打码
        rule.setRule("ci-process=ImageTargetRec&detect-type=face,plate&cover=1");
        ruleList.add(rule);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult result = cosClient.processImage(imageReq);
            System.out.println("=== 人脸+车牌打码结果 ===");
            System.out.println(Jackson.toJsonString(result));
            if (result.getProcessResults() != null && result.getProcessResults().getObjectList() != null) {
                for (CIObject ciObject : result.getProcessResults().getObjectList()) {
                    System.out.println("输出文件: " + ciObject.getLocation());
                    System.out.println("ETag: " + ciObject.getEtag());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 识别所有类型（人脸+车牌+人体+车辆）并打码
     */
    public static void imageTargetRecAllTypes(COSClient cosClient) {
        String bucketName = "demo-1234567890";
        String key = "street.jpg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId("output/street_mosaic_all.jpg");
        // detect-type=face,plate,body,car: 识别所有类型
        // cover=1: 对识别到的目标进行打码
        rule.setRule("ci-process=ImageTargetRec&detect-type=face,plate,body,car&cover=1");
        ruleList.add(rule);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult result = cosClient.processImage(imageReq);
            System.out.println("=== 全类型打码结果 ===");
            System.out.println(Jackson.toJsonString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 仅识别不打码（获取目标坐标信息）
     * cover=0 时不打码，仅返回识别到的目标位置信息
     */
    public static void imageTargetRecDetectOnly(COSClient cosClient) {
        String bucketName = "demo-1234567890";
        String key = "photo.jpg";
        ImageProcessRequest imageReq = new ImageProcessRequest(bucketName, key);

        PicOperations picOperations = new PicOperations();
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> ruleList = new LinkedList<>();
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setBucket(bucketName);
        rule.setFileId("output/photo_detect.jpg");
        // cover=0: 不打码，仅返回识别结果（坐标信息）
        rule.setRule("ci-process=ImageTargetRec&detect-type=face,plate,body,car&cover=0");
        ruleList.add(rule);
        picOperations.setRules(ruleList);
        imageReq.setPicOperations(picOperations);

        try {
            CIUploadResult result = cosClient.processImage(imageReq);
            System.out.println("=== 仅识别（不打码）结果 ===");
            System.out.println(Jackson.toJsonString(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
