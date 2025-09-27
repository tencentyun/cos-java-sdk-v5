package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.image.ImageSlimRequest;
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
        
        File localFile = new File("/Users/junliu/Desktop/13123213.png");
        
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
        
        File localFile = new File("/Users/junliu/Desktop/13123213.png");
        
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
}