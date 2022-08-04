package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.DecompressionRequest;
import com.qcloud.cos.model.DecompressionResult;
import com.qcloud.cos.model.ListJobsResult;
import com.qcloud.cos.region.Region;

public class DecompressDemo {

    private static final String secretId = "";
    private static final String secretKey = "";
    private static final String bucket = "";
    private static final String region = "";

    public static DecompressionResult postObjectDecompression() {
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cosCredentials, clientConfig);

        try {
            DecompressionRequest decompressionRequest =
                    new DecompressionRequest()
                            .setObjectKey("a.tar.gz")
                            .setSourceBucketName(bucket)
                            .setTargetBucketName(bucket)
                            .setPrefixReplaced(true)
                            .setResourcesPrefix("")
                            .setTargetKeyPrefix("test_out/");
            return cosClient.postObjectDecompression(decompressionRequest);
        } finally {
            cosClient.shutdown();
        }

    }

    public static DecompressionResult getObjectDecompressionStatus(String objectKey) {
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.http);
        COSClient cosClient = new COSClient(cosCredentials, clientConfig);
        try {
            return cosClient.getObjectDecompressionStatus(bucket, objectKey, null);
        } finally {
            cosClient.shutdown();
        }
    }


    public static void listObjectDecompressionJobs() {
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.http);
        COSClient cosClient = new COSClient(cosCredentials, clientConfig);
        try {
            ListJobsResult result =
                    cosClient.listObjectDecompressionJobs(bucket, "Success", "asc", 1000, null);
            System.out.println(result);
        } finally {
            cosClient.shutdown();
        }
    }

    public static String getPrefix(String objectKey) {
        int index = objectKey.lastIndexOf('/');
        String parent = "";
        if (index != -1) {
            parent = objectKey.substring(0, index + 1);
        }
        return parent;
    }


    public static void main(String[] args) {
        System.out.println(postObjectDecompression());
        System.out.println(getObjectDecompressionStatus("a.tar.gz"));
        listObjectDecompressionJobs();
    }
}
