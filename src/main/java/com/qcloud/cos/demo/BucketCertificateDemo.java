package com.qcloud.cos.demo;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.BucketDomainCertificateInfo;
import com.qcloud.cos.model.BucketGetDomainCertificate;
import com.qcloud.cos.model.BucketPutDomainCertificate;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.StringUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class BucketCertificateDemo {

    public static void putGetDeleteBucketCertificate() {
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("xxxxxxxxxxxxxxxxxxxxxxxxxxxx", "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
        // 2 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = "mybucket-1251668577";

        BucketPutDomainCertificate bucketPutDomainCertificate = new BucketPutDomainCertificate();
        BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();

        bucketDomainCertificateInfo.setCertType(StringUtils.Custom_CertType);
        List<String> domainlist = new LinkedList<>();
        domainlist.add("test.com");

        bucketPutDomainCertificate.setDomainList(domainlist);

        File keyfile = new File("src/test/resources/test.com.key");
        File pemfile = new File("src/test/resources/test.com.pem");
        try {

            InputStream is = new FileInputStream(keyfile);
            InputStreamReader isr = new InputStreamReader(is);
            char charArray[] = new char[(int)keyfile.length()];
            isr.read(charArray);
            String key = new String(charArray);
            bucketDomainCertificateInfo.setPrivateKey(key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream is = new FileInputStream(pemfile);
            InputStreamReader isr = new InputStreamReader(is);
            char charArray[] = new char[(int)pemfile.length()];
            isr.read(charArray);
            String pem = new String(charArray);
            bucketDomainCertificateInfo.setCert(pem);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        bucketPutDomainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        cosclient.setBucketDomainCertificate(bucketName,bucketPutDomainCertificate);

        BucketGetDomainCertificate domainCertificate = cosclient.getBucketDomainCertificate(bucketName,"test.com");
        System.out.println(domainCertificate.getStatus());

        cosclient.deleteBucketDomainCertificate(bucketName,"test.com");

        cosclient.shutdown();
    }

    public static void main(String[] args) {
        putGetDeleteBucketCertificate();
    }
}
