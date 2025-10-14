package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.image.ImageSlimRequest;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ImageSlimTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void testImageSlimDownloadMethod() throws IOException {
        String key = "SDK/Images/imageSlim/download_method_test.jpg";
        
        File localFile = new File("test.png");
        
        try {
            cosclient.putObject(bucket, key, localFile);
            System.out.println("原始文件大小: " + localFile.length() + " bytes");
            
            ImageSlimRequest request = new ImageSlimRequest(bucket, key);
            InputStream inputStream = cosclient.imageSlimDownload(request);

            // 保存压缩后的文件
            File compressedFile = new File("method_slim_result.jpg");
            try (FileOutputStream fos = new FileOutputStream(compressedFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }
            inputStream.close();
            
            System.out.println("压缩后文件大小: " + compressedFile.length() + " bytes");
            
            assertTrue("压缩后文件应该存在", compressedFile.exists());
            assertTrue("压缩后文件大小应该大于0", compressedFile.length() > 0);
            
            if (compressedFile.length() < localFile.length()) {
                double compressionRatio = (1.0 - (double)compressedFile.length() / localFile.length()) * 100;
                System.out.println("压缩率: " + String.format("%.2f", compressionRatio) + "%");
            }
            
            // 清理测试文件
            cosclient.deleteObject(bucket, key);
            compressedFile.delete();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImageSlimProcessMethod() {
        String sourceKey = "SDK/Images/13123213_cloud_test.png";
        String targetKey = "SDK/Images/imageSlim/13123213_cloud_test1.jpg";
        
        File localFile = new File("test.png");
        
        try {
            // 先上传原图
            cosclient.putObject(bucket, sourceKey, localFile);
            System.out.println("原始文件大小: " + localFile.length() + " bytes");
            
            ImageProcessRequest request = new ImageProcessRequest(bucket, sourceKey);
            
            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            
            List<PicOperations.Rule> rules = new ArrayList<>();
            PicOperations.Rule rule = new PicOperations.Rule();
            rule.setBucket(bucket);
            rule.setFileId(targetKey);

            rule.setRule("imageSlim");
            rules.add(rule);
            
            picOperations.setRules(rules);
            request.setPicOperations(picOperations);
            
            CIUploadResult result = cosclient.imageSlimProcess(request);
            System.out.println("压缩后文件大小: " + result.getProcessResults().getObjectList().get(0).getSize() + " bytes");
            if (result.getProcessResults().getObjectList().get(0).getSize() < localFile.length()) {
                double compressionRatio = (1.0 - (double)result.getProcessResults().getObjectList().get(0).getSize() / localFile.length()) * 100;
                System.out.println("压缩率: " + String.format("%.2f", compressionRatio) + "%");
            }
            // 清理测试文件
            cosclient.deleteObject(bucket, sourceKey);
            cosclient.deleteObject(bucket, targetKey);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testImageSlimUploadTimeProcessing() {
        String sourceKey = "SDK/Images/imageSlim/upload_source_test.jpg";
        String targetKey = "SDK/Images/imageSlim/upload_slim_test.jpg";
        
        File localFile = new File("test.png");
        
        try {
            System.out.println("原始文件大小: " + localFile.length() + " bytes");
            
            // 1. 创建上传请求
            PutObjectRequest putRequest = new PutObjectRequest(bucket, sourceKey, localFile);
            
            // 2. 配置图片处理参数
            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            
            // 3. 设置处理规则 - 极智压缩
            List<PicOperations.Rule> ruleList = new ArrayList<>();
            PicOperations.Rule rule = new PicOperations.Rule();
            rule.setBucket(bucket);
            rule.setFileId(targetKey);
            rule.setRule("imageSlim");
            ruleList.add(rule);
            
            picOperations.setRules(ruleList);
            putRequest.setPicOperations(picOperations);
            
            // 4. 执行上传时处理
            PutObjectResult putResult = cosclient.putObject(putRequest);
            CIUploadResult ciUploadResult = putResult.getCiUploadResult();
            
            // 5. 验证处理结果
            assertNotNull("CIUploadResult不应为空", ciUploadResult);
            assertNotNull("原图信息不应为空", ciUploadResult.getOriginalInfo());
            assertNotNull("处理结果不应为空", ciUploadResult.getProcessResults());
            assertNotNull("处理对象列表不应为空", ciUploadResult.getProcessResults().getObjectList());
            assertFalse("处理对象列表不应为空", ciUploadResult.getProcessResults().getObjectList().isEmpty());
            
            // 6. 输出处理结果信息
            System.out.println("原图信息:");
            System.out.println("  ETag: " + ciUploadResult.getOriginalInfo().getEtag());
            
            System.out.println("处理结果:");
            for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("  处理后文件: " + ciObject.getLocation());
                System.out.println("  文件ETag: " + ciObject.getEtag());
                System.out.println("  文件大小: " + ciObject.getSize() + " bytes");
                
                // 验证压缩效果
                assertTrue("处理后文件大小应该大于0", ciObject.getSize() > 0);
                
                // 计算并验证压缩率
                if (ciObject.getSize() < localFile.length()) {
                    double compressionRatio = (1.0 - (double) ciObject.getSize() / localFile.length()) * 100;
                    System.out.println("  压缩率: " + String.format("%.2f", compressionRatio) + "%");
                    assertTrue("压缩率应该大于0%", compressionRatio > 0);
                    System.out.println("✓ 极智压缩成功，压缩率: " + String.format("%.2f", compressionRatio) + "%");
                } else {
                    System.out.println("  未进行压缩或压缩效果不明显");
                }
            }
            
            // 7. 清理测试文件
            cosclient.deleteObject(bucket, sourceKey);
            cosclient.deleteObject(bucket, targetKey);
            
        } catch (Exception e) {
            System.err.println("上传时处理测试失败: " + e.getMessage());
            e.printStackTrace();
            fail("上传时处理测试失败: " + e.getMessage());
        }
    }

    @Test
    public void testImageSlimUploadTimeWithMultipleRules() {
        String sourceKey = "SDK/Images/imageSlim/upload_multi_source.jpg";
        String slimTargetKey = "SDK/Images/imageSlim/upload_multi_slim.jpg";
        String resizeTargetKey = "SDK/Images/imageSlim/upload_multi_resize.jpg";
        
        File localFile = new File("test.png");
        
        try {
            System.out.println("测试多规则处理 - 原始文件大小: " + localFile.length() + " bytes");
            
            // 1. 创建上传请求
            PutObjectRequest putRequest = new PutObjectRequest(bucket, sourceKey, localFile);
            
            // 2. 配置图片处理参数 - 多个处理规则
            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            
            List<PicOperations.Rule> ruleList = new ArrayList<>();
            
            // 规则1: 极智压缩
            PicOperations.Rule slimRule = new PicOperations.Rule();
            slimRule.setBucket(bucket);
            slimRule.setFileId(slimTargetKey);
            slimRule.setRule("imageSlim");
            ruleList.add(slimRule);
            
            picOperations.setRules(ruleList);
            putRequest.setPicOperations(picOperations);
            
            // 3. 执行上传时处理
            PutObjectResult putResult = cosclient.putObject(putRequest);
            CIUploadResult ciUploadResult = putResult.getCiUploadResult();
            
            // 4. 验证处理结果
            assertNotNull("CIUploadResult不应为空", ciUploadResult);
            assertNotNull("处理结果不应为空", ciUploadResult.getProcessResults());

            for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("    文件: " + ciObject.getLocation());
                System.out.println("    大小: " + ciObject.getSize() + " bytes");
                
                if (ciObject.getSize() < localFile.length()) {
                    double compressionRatio = (1.0 - (double) ciObject.getSize() / localFile.length()) * 100;
                    System.out.println("    压缩率: " + String.format("%.2f", compressionRatio) + "%");
                }
            }
            
            // 6. 清理测试文件
            cosclient.deleteObject(bucket, sourceKey);
            cosclient.deleteObject(bucket, slimTargetKey);
            cosclient.deleteObject(bucket, resizeTargetKey);
            
        } catch (Exception e) {
            System.err.println("多规则上传时处理测试失败: " + e.getMessage());
            e.printStackTrace();
            fail("多规则上传时处理测试失败: " + e.getMessage());
        }
    }
}