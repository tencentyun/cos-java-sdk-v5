package com.qcloud.cos;

import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.BucketDomainConfiguration;
import com.qcloud.cos.model.DomainRule;
import com.qcloud.cos.model.bucketcertificate.BucketDomainCertificateInfo;
import com.qcloud.cos.model.bucketcertificate.BucketDomainCertificateParameters;
import com.qcloud.cos.model.bucketcertificate.BucketGetDomainCertificate;
import com.qcloud.cos.model.bucketcertificate.BucketPutDomainCertificate;
import com.qcloud.cos.utils.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.fail;

public class BucketDomainCertificateTest extends AbstractCOSClientTest{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientTest.initCosClient();
        userDefDomain = System.getenv("user_def_domain");
        domainPemPath = System.getenv("domain_pem_path");
        domainKeyPath = System.getenv("domain_key_path");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSClientTest.destoryCosClient();
    }

    @Test
    public void SetGetDelDomainCertificateTest() throws Exception {
        BucketDomainConfiguration bucketDomainConfiguration = new BucketDomainConfiguration();
        DomainRule domainRule = new DomainRule();
        domainRule.setStatus(DomainRule.ENABLED);
        domainRule.setType(DomainRule.REST);
        domainRule.setName(userDefDomain);
        bucketDomainConfiguration.getDomainRules().add(domainRule);
        try {
            cosclient.setBucketDomainConfiguration(bucket, bucketDomainConfiguration);
        } catch (CosServiceException cse) {
            fail(cse.toString());
        }


        BucketPutDomainCertificate bucketPutDomainCertificate = new BucketPutDomainCertificate();
        BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();

        bucketDomainCertificateInfo.setCertType(BucketDomainCertificateParameters.Custom_CertType);
        List<String> domainlist = new LinkedList<>();
        domainlist.add(userDefDomain);

        bucketPutDomainCertificate.setDomainList(domainlist);
        try {
            String key = getStreamContent(domainKeyPath);
            bucketDomainCertificateInfo.setPrivateKey(key);
            String pem = getStreamContent(domainPemPath);
            bucketDomainCertificateInfo.setCert(pem);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        bucketPutDomainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        cosclient.setBucketDomainCertificate(bucket,bucketPutDomainCertificate);

        BucketGetDomainCertificate domainCertificate = cosclient.getBucketDomainCertificate(bucket,userDefDomain);
        System.out.println(domainCertificate.getStatus());

        cosclient.deleteBucketDomainCertificate(bucket, userDefDomain);

        cosclient.deleteBucketDomainConfiguration(bucket);
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
        } finally {
            is.close();
            br.close();
        }

        return stringBuilder.toString();
    }
}
