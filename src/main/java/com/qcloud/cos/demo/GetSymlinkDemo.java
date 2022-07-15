package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.GetSymlinkRequest;
import com.qcloud.cos.model.GetSymlinkResult;
import com.qcloud.cos.region.Region;

public class GetSymlinkDemo {
    private static final String secretId = "AKID8OZWYeE1GUiwHSnibJrFVegYFk5Fu6mH";
    private static final String secretKey = "Vp12togWGwaWSNEB98Ns5fK22HoPoKtT";
    private static final String bucket = "goosefs-test-1252681929";
    private static final String region = "ap-guangzhou";

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
