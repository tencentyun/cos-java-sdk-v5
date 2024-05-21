package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingTextLibRequest;
import com.qcloud.cos.model.ciModel.auditing.AuditingTextLibResponse;
import com.qcloud.cos.model.ciModel.bucket.DocBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketResponse;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.DocHtmlRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListResponse;
import com.qcloud.cos.model.ciModel.job.DocJobObject;
import com.qcloud.cos.model.ciModel.job.DocJobRequest;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.DocProcessObject;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.DocQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaQueueObject;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class AuditingTextLibTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void addAuditingTextLibTest() {
        try {
            AuditingTextLibRequest request = new AuditingTextLibRequest();
            request.setBucketName(bucket);
            request.setLibName("TextLib1");
            request.setMatchType("Exact");
            request.setSuggestion("Block");
            AuditingTextLibResponse response = cosclient.addAuditingTextLib(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void describeAuditingTextLibTest() {
        try {
            AuditingTextLibRequest request = new AuditingTextLibRequest();
            request.setBucketName(bucket);
            request.setLimit(1);
            AuditingTextLibResponse response = cosclient.describeAuditingTextLib(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
        }
    }

    @Test
    public void updateAuditingTextLibTest() {
        try {
            AuditingTextLibRequest request = new AuditingTextLibRequest();
            request.setBucketName(bucket);
            request.setLibid("067a9359-9d10-4521-a19c-43b9a1c7");
            request.setLibName("TextLib2");
            request.setMatchType("Exact");
            request.setSuggestion("Block");
            AuditingTextLibResponse response = cosclient.updateAuditingTextLib(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {

        }

    }

    @Test
    public void deleteAuditingTextLibTest() {
        try {
            AuditingTextLibRequest request = new AuditingTextLibRequest();
            request.setBucketName(bucket);
            request.setLibid("20a9d0e8-416e-4df8-9091-43b4a35");
            AuditingTextLibResponse response = cosclient.deleteAuditingTextLib(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
