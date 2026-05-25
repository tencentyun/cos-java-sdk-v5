package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.job.FileHashCodeSyncRequest;
import com.qcloud.cos.model.ciModel.job.FileHashCodeSyncResponse;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileHashCodeSyncTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        closeCosClient();
    }

    /**
     * 测试使用MD5算法计算文件哈希值
     */
    @Test
    public void testFileHashCodeSyncWithMD5() throws Exception {
        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("chongqingtest-1251704708");
        request.setObjectKey("SDK/Images/test0.jpeg");
        request.setType("md5");
        request.setAddToHeader("false");

        FileHashCodeSyncResponse response = cosclient.fileHashCodeSync(request);

        // 验证响应
        assertNotNull("响应不能为空", response);
        assertNotNull("FileHashCodeResult不能为空", response.getFileHashCodeResult());
        assertNotNull("Input不能为空", response.getInput());
        
        // 验证输入信息
        assertEquals("Bucket名称不匹配", "chongqingtest-1251704708", response.getInput().getBucket());
        assertEquals("ObjectKey不匹配", "SDK/Images/test0.jpeg", response.getInput().getObject());
        
        // 验证哈希值结果
        assertNotNull("MD5哈希值不能为空", response.getFileHashCodeResult().getMd5());
        assertTrue("MD5哈希值长度应为32位", response.getFileHashCodeResult().getMd5().length() == 32);
        assertNotNull("文件大小不能为空", response.getFileHashCodeResult().getFileSize());
        assertNotNull("最后修改时间不能为空", response.getFileHashCodeResult().getLastModified());
        // ETag可能为空，取决于服务端实现
        // assertNotNull("ETag不能为空", response.getFileHashCodeResult().getEtag());
        
        System.out.println("File Hash Code Sync with MD5 - Response: " + Jackson.toJsonString(response));
    }

    /**
     * 测试使用SHA1算法计算文件哈希值
     */
    @Test
    public void testFileHashCodeSyncWithSHA1() throws Exception {
        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("chongqingtest-1251704708");
        request.setObjectKey("SDK/Images/test0.jpeg");
        request.setType("sha1");
        request.setAddToHeader("false");

        FileHashCodeSyncResponse response = cosclient.fileHashCodeSync(request);

        // 验证响应
        assertNotNull("响应不能为空", response);
        assertNotNull("FileHashCodeResult不能为空", response.getFileHashCodeResult());
        
        // 验证哈希值结果
        assertNotNull("SHA1哈希值不能为空", response.getFileHashCodeResult().getSha1());
        assertTrue("SHA1哈希值长度应为40位", response.getFileHashCodeResult().getSha1().length() == 40);
        assertNotNull("文件大小不能为空", response.getFileHashCodeResult().getFileSize());
        
        System.out.println("File Hash Code Sync with SHA1 - Response: " + Jackson.toJsonString(response));
    }

    /**
     * 测试使用SHA256算法计算文件哈希值
     */
    @Test
    public void testFileHashCodeSyncWithSHA256() throws Exception {
        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("chongqingtest-1251704708");
        request.setObjectKey("SDK/Images/test0.jpeg");
        request.setType("sha256");
        request.setAddToHeader("false");

        FileHashCodeSyncResponse response = cosclient.fileHashCodeSync(request);

        // 验证响应
        assertNotNull("响应不能为空", response);
        assertNotNull("FileHashCodeResult不能为空", response.getFileHashCodeResult());
        
        // 验证哈希值结果
        assertNotNull("SHA256哈希值不能为空", response.getFileHashCodeResult().getSha256());
        assertTrue("SHA256哈希值长度应为64位", response.getFileHashCodeResult().getSha256().length() == 64);
        
        System.out.println("File Hash Code Sync with SHA256 - Response: " + Jackson.toJsonString(response));
    }

    /**
     * 测试使用最少参数计算文件哈希值（使用默认的md5算法）
     */
    @Test
    public void testFileHashCodeSyncWithMinimalParams() throws Exception {
        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("chongqingtest-1251704708");
        request.setObjectKey("SDK/Images/test0.jpeg");
        // type参数是必需的，使用默认的md5算法
        request.setType("md5");

        FileHashCodeSyncResponse response = cosclient.fileHashCodeSync(request);

        // 验证响应
        assertNotNull("响应不能为空", response);
        assertNotNull("FileHashCodeResult不能为空", response.getFileHashCodeResult());
        
        // 验证至少有一个哈希值不为空
        boolean hasHashValue = response.getFileHashCodeResult().getMd5() != null ||
                              response.getFileHashCodeResult().getSha1() != null ||
                              response.getFileHashCodeResult().getSha256() != null;
        assertTrue("至少应有一个哈希值不为空", hasHashValue);
        
        System.out.println("File Hash Code Sync with Minimal Params - Response: " + Jackson.toJsonString(response));
    }

    /**
     * 测试将哈希值添加到文件header
     */
    @Test
    public void testFileHashCodeSyncWithAddToHeader() throws Exception {
        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("chongqingtest-1251704708");
        request.setObjectKey("SDK/Images/test0.jpeg");
        request.setType("md5");
        request.setAddToHeader("true");

        FileHashCodeSyncResponse response = cosclient.fileHashCodeSync(request);

        // 验证响应
        assertNotNull("响应不能为空", response);
        assertNotNull("FileHashCodeResult不能为空", response.getFileHashCodeResult());
        assertNotNull("MD5哈希值不能为空", response.getFileHashCodeResult().getMd5());
        
        System.out.println("File Hash Code Sync with AddToHeader - Response: " + Jackson.toJsonString(response));
    }
}