package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetSymlinkRequest;
import com.qcloud.cos.model.GetSymlinkResult;
import com.qcloud.cos.region.Region;

public class GetSymlinkDemo {
    private static final String secretId = "";
    private static final String secretKey = "";
    private static final String bucket = "";
    private static final String region = "";

    public static String getSymlink(String symlink) {
        COSCredentials cosCredentials = new BasicCOSCredentials(secretId, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        COSClient cosClient = new COSClient(cosCredentials, clientConfig);

        GetSymlinkResult getSymlinkResult = cosClient.getSymlink(
                new GetSymlinkRequest(bucket, symlink, null));

        return getSymlinkResult.getTarget();
    }

    public static void main(String[] args) {
        System.out.println(getSymlink("test-symlink"));
    }
}
