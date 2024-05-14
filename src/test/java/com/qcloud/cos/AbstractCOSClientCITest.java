package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AbstractCOSClientCITest {
    protected static String appid = null;
    protected static String secretId = null;
    protected static String secretKey = null;
    protected static String region = null;
    protected static String bucket = null;
    protected static String mediaQueueId = null;
    protected static String docQueueId = null;
    protected static String auditingQueueId = null;
    protected static String workflowId = "we32f75950afe4a4682463d8158ded475";
    protected static String runId = "ibee271f4c88511ed88985254008e464b";
    protected static ClientConfig clientConfig = null;
    protected static COSClient cosclient = null;

    protected static boolean initConfig() throws IOException {
        appid = System.getenv("ciAppid");
        secretId = System.getenv("ciSecretId");
        secretKey = System.getenv("ciSecretKey");
        region = System.getenv("ciRegion");
        bucket = System.getenv("ciBucket");

        File propFile = new File("ut_account.prop");
        if (propFile.exists() && propFile.canRead()) {
            Properties prop = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(propFile);
                prop.load(fis);
                appid = prop.getProperty("ciAppid");
                secretId = prop.getProperty("ciSecretId");
                secretKey = prop.getProperty("ciSecretKey");
                region = prop.getProperty("ciRegion");
                bucket = prop.getProperty("ciBucket");
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e) {
                    }
                }
            }
        }

        if (secretId == null || secretKey == null || bucket == null || region == null) {
            System.out.println("cos ut user info missing. skip all test");
            return false;
        }
        return true;
    }


    public static void initCosClient() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(AbstractCOSClientCITest.region);
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    protected static boolean judgeUserInfoValid() {
        return cosclient != null;
    }

    protected static void closeCosClient() {
        if (cosclient != null) {
            cosclient.shutdown();
        }
    }
}
