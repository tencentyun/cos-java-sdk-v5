package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.hls.CreateHLSPlayKeyRequest;
import com.qcloud.cos.model.ciModel.hls.CreateHLSPlayKeyResponse;
import com.qcloud.cos.model.ciModel.hls.GetHLSPlayKeyRequest;
import com.qcloud.cos.model.ciModel.hls.GetHLSPlayKeyResponse;
import com.qcloud.cos.model.ciModel.hls.UpdataHLSPlayKeyRequest;
import com.qcloud.cos.model.ciModel.hls.UpdataHLSPlayKeyResponse;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HlsTest extends AbstractCOSClientCITest {
    private String jobId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-shanghai");
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Before
    public void getHLSPlayKeyTest() {
        try {
            GetHLSPlayKeyRequest request = new GetHLSPlayKeyRequest();
            request.setBucketName(bucket);

            GetHLSPlayKeyResponse response = cosclient.getHLSPlayKey(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {

        }
    }

    @Test
    public void createHLSPlayKeyTest() {
        try {
            CreateHLSPlayKeyRequest request = new CreateHLSPlayKeyRequest();
            request.setBucketName(bucket);

            CreateHLSPlayKeyResponse response = cosclient.createHLSPlayKey(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {

        }
    }

    @Test
    public void updateHLSPlayKeyTest() {
        try {
            UpdataHLSPlayKeyRequest request = new UpdataHLSPlayKeyRequest();
            request.setBucketName(bucket);
            // 设置备播放密钥;是否必传：是
            request.setBackupPlayKey("testkey");

            UpdataHLSPlayKeyResponse response = cosclient.updataHLSPlayKey(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {

        }
    }


}
