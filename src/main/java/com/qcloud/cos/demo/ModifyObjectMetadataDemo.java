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
    public static void main(String[] args) {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXX", "1A2Z3YYYYYYYYYY");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        Region region = new Region("ap-beijing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = "examplebucket-1250000000";
        String key = "exampleobject";

        ObjectMetadata objectMetadata = cosclient.getObjectMetadata(bucketName, key);
        objectMetadata.setHeader("x-cos-metadata-directive", "Replaced");

        // 设置新的对象元数据
        // 注意：Content-Disposition 、自定义元数据或者其他有中文的头域值，在设置前请先调用 UrlEncoderUtils.encode(String) 编码，避免签名问题
        objectMetadata.setHeader("x-cos-storage-class", "STANDARD_IA");
        objectMetadata.setContentType("text/plain");

        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(region, bucketName, key, bucketName, key);
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
