package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutSymlinkRequest;
import com.qcloud.cos.model.PutSymlinkResult;
import com.qcloud.cos.region.Region;

public class CreateSymlinkDemo {

    private static final String secretId = "AKIDXXXXXXXX";
    private static final String secretKey = "1A2Z3YYYYYYYYYY";
    private static final String bucketName = "examplebucket-12500000000";
    private static final String region = "ap-guangzhou";

    public static void main(String[] args) {
        createSymlink("test-symlink", "word_count.txt");
    }

    private static void createSymlink(String symlink, String target)  {
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));

        COSClient cosClient = new COSClient(cosCredentials, clientConfig);

        PutSymlinkRequest putSymlinkRequest = new PutSymlinkRequest(bucketName, symlink, target);

        try {
            PutSymlinkResult putSymlinkResult = cosClient.putSymlink(putSymlinkRequest);
            System.out.println("ETag: " + putSymlinkResult.getETag());
        } finally {
            cosClient.shutdown();
        }

    }
}
