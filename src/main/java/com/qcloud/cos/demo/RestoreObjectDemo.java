package com.qcloud.cos.demo;
import java.io.File;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.CASJobParameters;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.Tier;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.RestoreObjectRequest;
import com.qcloud.cos.region.Region;

public class RestoreObjectDemo {
    private static String secretId = System.getenv("SECRETID");
    private static String secretKey = System.getenv("SECRETKEY");
    private static String bucketName = System.getenv("BUCKET_NAME");
    private static String region = System.getenv("REGION");

    public static void main(String[] args) {
        restoreObjectDemo();
    }

    private static void restoreObjectDemo() {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        String key = "test/my_data.txt";

        // 上传一个类型为归档的文件
        File localFile = new File("test/my_data.txt");
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setHeader("x-cos-storage-class", "Archive");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        putObjectRequest.setMetadata(metadata);
        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
        System.out.println("finish upload object, request id: " + putObjectResult.getRequestId());

        // 设置restore得到的临时副本过期天数为1天
        RestoreObjectRequest restoreObjectRequest = new RestoreObjectRequest(bucketName, key, 1);
        // 设置恢复模式为Standard，其他的可选模式包括Expedited和Bulk，三种恢复模式在费用和速度上不一样
        CASJobParameters casJobParameters = new CASJobParameters();
        casJobParameters.setTier(Tier.Standard);
        restoreObjectRequest.setCASJobParameters(casJobParameters);
        cosclient.restoreObject(restoreObjectRequest);
    }
}
