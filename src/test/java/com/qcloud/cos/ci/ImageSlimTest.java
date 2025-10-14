package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.persistence.CIObject;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
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

    /**
     * 测试极智压缩 - 下载时处理示例
     */
    @Test
    public void testDownloadTimeImageSlim() {
        String key = "SDK/Images/imageSlim/download_test.png";
        File localFile = new File("test.png");
        
        try {
            // 先上传测试文件
            cosclient.putObject(bucket, key, localFile);
            System.out.println("原始文件大小: " + localFile.length() + " bytes");
            
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, key);
            String compress = "imageSlim";
            getObjectRequest.putCustomQueryParameter(compress, null);
            File compressedFile = new File("imageSlim_download.png");
            cosclient.getObject(getObjectRequest, compressedFile);
            
            // 验证处理结果
            assertTrue("压缩后文件应该存在", compressedFile.exists());
            assertTrue("压缩后文件大小应该大于0", compressedFile.length() > 0);
            System.out.println("压缩后文件大小: " + compressedFile.length() + " bytes");
            
            // 计算压缩率
            if (compressedFile.length() < localFile.length()) {
                double compressionRatio = (1.0 - (double)compressedFile.length() / localFile.length()) * 100;
                System.out.println("压缩率: " + String.format("%.2f", compressionRatio) + "%");
            }
            
            // 清理测试文件
            cosclient.deleteObject(bucket, key);
            compressedFile.delete();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("下载时处理测试失败: " + e.getMessage());
        }
    }

    /**
     * 测试极智压缩 - 上传时处理示例
     */
    @Test
    public void testUploadTimeImageSlim() {
        // 1. 设置基础参数
        String sourceKey = "upload_source.jpg";
        String targetKey = "upload_slim.jpg";
        File localFile = new File("test.png");
        
        try {
            System.out.println("原始文件大小: " + localFile.length() + " bytes");
            
            // 2. 创建上传请求
            PutObjectRequest putRequest = new PutObjectRequest(bucket, sourceKey, localFile);
            // 3. 配置图片处理参数
            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            // 4. 设置处理规则
            List<PicOperations.Rule> ruleList = new ArrayList<>();
            PicOperations.Rule rule = new PicOperations.Rule();
            rule.setBucket(bucket);
            rule.setFileId(targetKey);
            // 极智压缩规则
            rule.setRule("imageSlim");
            ruleList.add(rule);
            picOperations.setRules(ruleList);
            putRequest.setPicOperations(picOperations);
            // 5. 执行上传时处理
            PutObjectResult putResult = cosclient.putObject(putRequest);
            CIUploadResult ciUploadResult = putResult.getCiUploadResult();
            
            // 验证处理结果
            assertNotNull("CIUploadResult不应为空", ciUploadResult);
            assertNotNull("处理结果不应为空", ciUploadResult.getProcessResults());
            assertFalse("处理对象列表不应为空", ciUploadResult.getProcessResults().getObjectList().isEmpty());
            
            // 输出处理结果
            for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("处理后文件: " + ciObject.getLocation());
                System.out.println("文件大小: " + ciObject.getSize() + " bytes");
                
                if (ciObject.getSize() < localFile.length()) {
                    double compressionRatio = (1.0 - (double) ciObject.getSize() / localFile.length()) * 100;
                    System.out.println("压缩率: " + String.format("%.2f", compressionRatio) + "%");
                }
            }
            
            // 清理测试文件
            cosclient.deleteObject(bucket, sourceKey);
            cosclient.deleteObject(bucket, targetKey);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("上传时处理测试失败: " + e.getMessage());
        }
    }

    /**
     * 测试极智压缩 - 云上数据处理示例
     */
    @Test
    public void testCloudDataImageSlim() {
        // 1. 设置基础参数
        String sourceKey = "cloud_source.jpg";
        String targetKey = "cloud_slim.jpg";
        File localFile = new File("test.png");
        
        try {
            // 先上传原图
            cosclient.putObject(bucket, sourceKey, localFile);
            System.out.println("原始文件大小: " + localFile.length() + " bytes");
            
            // 2. 创建云上处理请求
            ImageProcessRequest imageReq = new ImageProcessRequest(bucket, sourceKey);
            // 3. 配置图片处理参数
            PicOperations picOperations = new PicOperations();
            picOperations.setIsPicInfo(1);
            // 4. 设置处理规则
            List<PicOperations.Rule> ruleList = new ArrayList<>();
            PicOperations.Rule rule = new PicOperations.Rule();
            rule.setBucket(bucket);
            rule.setFileId(targetKey);
            rule.setRule("imageSlim");
            ruleList.add(rule);
            picOperations.setRules(ruleList);
            imageReq.setPicOperations(picOperations);
            // 5. 执行云上数据处理
            CIUploadResult ciUploadResult = cosclient.processImage(imageReq);
            
            // 验证处理结果
            assertNotNull("处理结果不应为空", ciUploadResult);
            assertNotNull("处理对象列表不应为空", ciUploadResult.getProcessResults());
            assertFalse("处理对象列表不应为空", ciUploadResult.getProcessResults().getObjectList().isEmpty());
            
            // 输出处理结果
            for (CIObject ciObject : ciUploadResult.getProcessResults().getObjectList()) {
                System.out.println("处理后文件: " + ciObject.getLocation());
                System.out.println("文件大小: " + ciObject.getSize() + " bytes");
                
                if (ciObject.getSize() < localFile.length()) {
                    double compressionRatio = (1.0 - (double) ciObject.getSize() / localFile.length()) * 100;
                    System.out.println("压缩率: " + String.format("%.2f", compressionRatio) + "%");
                }
            }
            
            // 清理测试文件
            cosclient.deleteObject(bucket, sourceKey);
            cosclient.deleteObject(bucket, targetKey);
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("云上数据处理测试失败: " + e.getMessage());
        }
    }

}