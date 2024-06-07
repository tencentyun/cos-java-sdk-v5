package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyListResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyRequest;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyResponse;
import com.qcloud.cos.model.ciModel.auditing.StrategyImageLabel;
import com.qcloud.cos.model.ciModel.auditing.StrategyLabels;
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

public class AuditingStrategyTest extends AbstractCOSClientCITest {
    String bizType;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Before
    public void addAuditingStrategy() {
        try {
            AuditingStrategyRequest request = new AuditingStrategyRequest();
            request.setBucketName(bucket);
            request.setName("Strategy1");
            request.setService("Image");

            StrategyLabels labels = request.getLabels();
            StrategyImageLabel image = labels.getImage();
            List<String> politics = image.getPolitics();
            politics.add("NegativeFigure");
            politics.add("ForeignLeaders");

            AuditingStrategyResponse response = cosclient.addAuditingStrategy(request);
            bizType = response.getStrategy().getBizType();
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void describeAuditingStrategy() {
        try {
            AuditingStrategyRequest request = new AuditingStrategyRequest();
            request.setBucketName(bucket);
            request.setService("Image");
            request.setBizType(bizType);
            AuditingStrategyResponse response = cosclient.describeAuditingStrategy(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateAuditingStrategy() {
        try {
            AuditingStrategyRequest request = new AuditingStrategyRequest();
            request.setBucketName(bucket);
            request.setService("Image");
            request.setBizType(bizType);
            StrategyLabels labels = request.getLabels();
            StrategyImageLabel image = labels.getImage();
            List<String> politics = image.getPolitics();
            politics.add("NegativeFigure");
            politics.add("ForeignLeaders");

            List<String> pron = image.getPorn();
            pron.add("Sexy");
            pron.add("OcrText");
            AuditingStrategyResponse response = cosclient.updateAuditingStrategy(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (Exception e) {

        }

    }

    @Test
    public void describeAuditingStrategyList() {
        try {
            AuditingStrategyRequest request = new AuditingStrategyRequest();
            request.setBucketName(bucket);
            request.setService("Image");
            AuditingStrategyListResponse response = cosclient.describeAuditingStrategyList(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
