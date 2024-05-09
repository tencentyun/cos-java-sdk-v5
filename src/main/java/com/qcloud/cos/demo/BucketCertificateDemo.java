package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.bucketcertificate.BucketDomainCertificateInfo;
import com.qcloud.cos.model.bucketcertificate.BucketDomainCertificateParameters;
import com.qcloud.cos.model.bucketcertificate.BucketGetDomainCertificate;
import com.qcloud.cos.model.bucketcertificate.BucketPutDomainCertificate;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class BucketCertificateDemo {
    public static void main(String[] args) {
        putGetDeleteBucketCertificate();
    }

    private static void putGetDeleteBucketCertificate() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("xxxxxxxxxxxxxxxxxxxxxxxxxxxx", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
        // bucket名需包含appid
        String bucketName = "mybucket-12500000000";

        BucketPutDomainCertificate bucketPutDomainCertificate = new BucketPutDomainCertificate();
        BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();

        bucketDomainCertificateInfo.setCertType(BucketDomainCertificateParameters.Custom_CertType);
        List<String> domainlist = new LinkedList<>();
        domainlist.add("test.com");

        bucketPutDomainCertificate.setDomainList(domainlist);
        try {
            String key = getStreamContent("src/test/resources/test.com.key");
            bucketDomainCertificateInfo.setPrivateKey(key);
            String pem = getStreamContent("src/test/resources/test.com.pem");
            bucketDomainCertificateInfo.setCert(pem);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);

        bucketPutDomainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        cosclient.setBucketDomainCertificate(bucketName,bucketPutDomainCertificate);

        BucketGetDomainCertificate domainCertificate = cosclient.getBucketDomainCertificate(bucketName,"test.com");
        System.out.println(domainCertificate.getStatus());

        cosclient.deleteBucketDomainCertificate(bucketName,"test.com");

        cosclient.shutdown();
    }

    private static String getStreamContent(String filePath) throws IOException{
        InputStream is = new FileInputStream(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br =
                new BufferedReader(new InputStreamReader(is, StringUtils.UTF8));

        try {
            char[] buf = new char[8192];
            int read = -1;
            while ((read = br.read(buf)) != -1) {
                stringBuilder.append(buf, 0, read);
            }
        }finally {
            is.close();
            br.close();
        }

        return stringBuilder.toString();
    }
}
