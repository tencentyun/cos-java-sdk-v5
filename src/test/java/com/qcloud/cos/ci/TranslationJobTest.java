package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class TranslationJobTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    // 测试正常创建翻译任务
    @Test
    public void testCreateTranslationJob() {
        try {
            TranslationRequest request = buildBaseRequest();
            TranslationResponse response = cosclient.createTranslationJob(request);

            assertNotNull(response);
            assertNotNull(response.getJobsDetail().getJobId());
            assertEquals("Success", response.getJobsDetail().getCode());
            System.out.println("Job created: " + Jackson.toJsonString(response));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    // 测试回调功能
    @Test
    public void testWithCallback() {
        try {
            TranslationRequest request = buildBaseRequest();
            request.setCallBack("http://xxx.xx.xxx.xx:xxxx");
            request.setCallBackFormat("XML");

            TranslationResponse response = cosclient.createTranslationJob(request);
            assertNotNull(response.getJobsDetail().getJobId());
        } catch (Exception e) {
        }
    }

    // 构建基础请求
    private TranslationRequest buildBaseRequest() {
        TranslationRequest request = new TranslationRequest();
        request.setBucketName(bucket);
        request.setTag("Translation");

        TranslationInput input = new TranslationInput();
        input.setObject("test.txt");
        input.setLang("zh");
        input.setType("txt");
        request.setInput(input);

        TranslationOutput output = new TranslationOutput();
        output.setRegion(region);
        output.setBucket(bucket);
        output.setObject("translated.txt");
        request.getOperation().setOutput(output);

        request.getOperation().getTranslation().setLang("en");
        request.getOperation().getTranslation().setType("txt");

        return request;
    }
}
