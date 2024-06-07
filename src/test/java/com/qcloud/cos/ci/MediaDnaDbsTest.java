package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ciModel.job.v2.DNADbConfigsRequest;
import com.qcloud.cos.model.ciModel.job.v2.DNADbConfigsResponse;
import com.qcloud.cos.model.ciModel.job.v2.DNADbFilesRequest;
import com.qcloud.cos.model.ciModel.job.v2.DNADbFilesResponse;
import com.qcloud.cos.model.ciModel.job.v2.DnaConfig;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MediaDnaDbsTest extends AbstractCOSClientCITest {
    private String jobId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        if (!initConfig()) {
            return;
        }
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-chongqing");
        ClientConfig clientConfig = new ClientConfig(region);
        cosclient = new COSClient(cred, clientConfig);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }


    @Test
    public void createMediaJobsTest() {
        try {
            MediaJobsRequestV2 request = new MediaJobsRequestV2();
            request.setBucketName(bucket);
            request.setTag("DNA");
            request.getInput().setObject("media/test.mp4");
            DnaConfig dnaConfig = request.getOperation().getDnaConfig();
            dnaConfig.setRuleType("GetFingerPrint");
            MediaJobResponseV2 response = cosclient.createMediaJobsV2(request);
            System.out.println(response.getJobsDetail().getJobId());
        } catch (Exception e) {

        }
    }

    @Test
    public void describeMediaJobTest() {
        try {
            MediaJobsRequestV2 request = new MediaJobsRequestV2();
            request.setBucketName(bucket);
            request.setJobId("j9cdbb46e845411ee9d800f9b074616ce");
            MediaJobResponseV2 response = cosclient.describeMediaJobV2(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void describeMediaDnaDbFilesTest() {
        try {
            DNADbFilesRequest request = new DNADbFilesRequest();
            request.setBucketName(bucket);
            request.setDnaDbId("Dnadeed68f8d15a45e6b867491195904695");
            DNADbFilesResponse response = cosclient.describeMediaDnaDbFiles(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {

        }
    }

    @Test
    public void describeMediaDnaDbsTest() {
        try {
            DNADbConfigsRequest request = new DNADbConfigsRequest();
            request.setBucketName(bucket);
            DNADbConfigsResponse response = cosclient.describeMediaDnaDbs(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {

        }

    }
}
