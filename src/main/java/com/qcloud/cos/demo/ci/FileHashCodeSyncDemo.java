package com.qcloud.cos.demo.ci;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.job.FileHashCodeSyncRequest;
import com.qcloud.cos.model.ciModel.job.FileHashCodeSyncResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Jackson;

/**
 * 文件哈希值同步计算演示类
 * 
 * 功能描述：
 * 以同步请求的方式进行文件哈希值计算，实时返回计算得到的哈希值
 * 
 * 使用限制：
 * - 文件大小：支持计算小于128MB大小的文件
 * - 请求超时时间：10秒
 * - 接口默认并发数量：50次/秒
 * 
 * 支持算法：
 * - MD5：32位哈希值
 * - SHA1：40位哈希值  
 * - SHA256：64位哈希值
 * 
 * 示例代码展示了：
 * 1. 使用MD5算法计算文件哈希值
 * 2. 使用SHA1算法计算文件哈希值
 * 3. 使用SHA256算法计算文件哈希值
 * 4. 将哈希值添加到文件header
 */
public class FileHashCodeSyncDemo {

    public static void main(String[] args) {
        // 1. 初始化用户身份信息（secretId, secretKey）
        COSCredentials cred = new BasicCOSCredentials("SECRET_ID", "SECRET_KEY");
        
        // 2. 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-chongqing"));
        
        // 3. 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        
        try {
            // 示例1：使用MD5算法计算文件哈希值
            demoMD5HashCalculation(cosClient);
            
            // 示例2：使用SHA1算法计算文件哈希值
            demoSHA1HashCalculation(cosClient);
            
            // 示例3：使用SHA256算法计算文件哈希值
            demoSHA256HashCalculation(cosClient);
            
            // 示例4：将哈希值添加到文件header
            demoAddHashToHeader(cosClient);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端
            cosClient.shutdown();
        }
    }
    
    /**
     * 示例1：使用MD5算法计算文件哈希值
     */
    private static void demoMD5HashCalculation(COSClient cosClient) {
        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("test-1234567890");
        request.setObjectKey("test.txt");
        request.setType("md5");
        request.setAddToHeader("false");
        
        try {
            FileHashCodeSyncResponse response = cosClient.fileHashCodeSync(request);
            
            System.out.println("MD5哈希值计算成功！");
            System.out.println("文件大小：" + response.getFileHashCodeResult().getFileSize() + " bytes");
            System.out.println("MD5哈希值：" + response.getFileHashCodeResult().getMd5());
            System.out.println("最后修改时间：" + response.getFileHashCodeResult().getLastModified());
            System.out.println("ETag：" + response.getFileHashCodeResult().getEtag());
            System.out.println("完整响应：" + Jackson.toJsonString(response));
            
        } catch (Exception e) {
            System.out.println("MD5哈希值计算失败：" + e.getMessage());
        }
    }
    
    /**
     * 示例2：使用SHA1算法计算文件哈希值
     */
    private static void demoSHA1HashCalculation(COSClient cosClient) {

        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("test-1234567890");
        request.setObjectKey("test.txt");
        request.setType("sha1");
        request.setAddToHeader("false");
        
        try {
            FileHashCodeSyncResponse response = cosClient.fileHashCodeSync(request);
            
            System.out.println("SHA1哈希值计算成功！");
            System.out.println("文件大小：" + response.getFileHashCodeResult().getFileSize() + " bytes");
            System.out.println("SHA1哈希值：" + response.getFileHashCodeResult().getSha1());
            System.out.println("最后修改时间：" + response.getFileHashCodeResult().getLastModified());
            System.out.println("ETag：" + response.getFileHashCodeResult().getEtag());
            System.out.println("完整响应：" + Jackson.toJsonString(response));
            
        } catch (Exception e) {
            System.out.println("SHA1哈希值计算失败：" + e.getMessage());
        }
    }
    
    /**
     * 示例3：使用SHA256算法计算文件哈希值
     */
    private static void demoSHA256HashCalculation(COSClient cosClient) {

        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("test-1234567890");
        request.setObjectKey("test.txt");
        request.setType("sha256");
        request.setAddToHeader("false");
        
        try {
            FileHashCodeSyncResponse response = cosClient.fileHashCodeSync(request);
            
            System.out.println("SHA256哈希值计算成功！");
            System.out.println("文件大小：" + response.getFileHashCodeResult().getFileSize() + " bytes");
            System.out.println("SHA256哈希值：" + response.getFileHashCodeResult().getSha256());
            System.out.println("最后修改时间：" + response.getFileHashCodeResult().getLastModified());
            System.out.println("ETag：" + response.getFileHashCodeResult().getEtag());
            System.out.println("完整响应：" + Jackson.toJsonString(response));
            
        } catch (Exception e) {
            System.out.println("SHA256哈希值计算失败：" + e.getMessage());
        }
        
    }
    
    /**
     * 示例4：将哈希值添加到文件header
     */
    private static void demoAddHashToHeader(COSClient cosClient) {
        FileHashCodeSyncRequest request = new FileHashCodeSyncRequest();
        request.setBucketName("test-1234567890");
        request.setObjectKey("test.txt");
        request.setType("md5");
        request.setAddToHeader("true");
        
        try {
            FileHashCodeSyncResponse response = cosClient.fileHashCodeSync(request);
            
            System.out.println("哈希值计算并添加到header成功！");
            System.out.println("MD5哈希值：" + response.getFileHashCodeResult().getMd5());
            System.out.println("文件大小：" + response.getFileHashCodeResult().getFileSize() + " bytes");
            System.out.println("注意：哈希值已自动添加到文件的自定义header中");
            System.out.println("完整响应：" + Jackson.toJsonString(response));
            
        } catch (Exception e) {
            System.out.println("哈希值计算并添加到header失败：" + e.getMessage());
        }
    }
}