package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;

public class ModifyObjectMetadataDemo {
    private static String secretId = System.getenv("SECRETID");
    private static String secretKey = System.getenv("SECRETKEY");
    private static String bucketName = System.getenv("BUCKET_NAME");
    private static String region = System.getenv("REGION");
    public static void main(String[] args) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        Region cosRegion = new Region(region);
        ClientConfig clientConfig = new ClientConfig(cosRegion);
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        String key = "exampleobject";

        ObjectMetadata objectMetadata = cosclient.getObjectMetadata(bucketName, key);
        objectMetadata.setHeader("x-cos-metadata-directive", "Replaced");

        // 设置新的对象元数据
        // 注意：Content-Disposition 、自定义元数据或者其他有中文的头域值，在设置前请先调用 UrlEncoderUtils.encode(String) 编码，避免签名问题
        objectMetadata.setHeader("x-cos-storage-class", "STANDARD_IA");
        objectMetadata.setContentType("text/plain");

        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(cosRegion, bucketName, key, bucketName, key);
        copyObjectRequest.setNewObjectMetadata(objectMetadata);

        try {
            CopyObjectResult copyObjectResult = cosclient.copyObject(copyObjectRequest);
            System.out.print(copyObjectResult.getRequestId());
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        } finally {
            // 确认本进程不再使用 cosClient 实例之后，关闭即可
            cosclient.shutdown();
        }
    }
}
