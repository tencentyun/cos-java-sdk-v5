package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.auditing.AuditingKeyword;
import com.qcloud.cos.model.ciModel.auditing.AuditingKeywordRequest;
import com.qcloud.cos.model.ciModel.auditing.AuditingKeywordResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyListResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyRequest;
import com.qcloud.cos.model.ciModel.auditing.AuditingStrategyResponse;
import com.qcloud.cos.model.ciModel.auditing.StrategyImageLabel;
import com.qcloud.cos.model.ciModel.auditing.StrategyLabels;
import com.qcloud.cos.utils.Jackson;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class AuditingKeywordTest extends AbstractCOSClientCITest {
    String libID = "e469c64b-0b74-4c56-a35e-c5e8c08";

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }


    @Before
    public void addAuditingLibKeyWord() {
        try {
            AuditingKeywordRequest request = new AuditingKeywordRequest();
            request.setBucketName(bucket);
            request.setLibId(libID);
            List<AuditingKeyword> keywords = request.getKeywords();
            AuditingKeyword keyword = new AuditingKeyword();
            keyword.setContent("demoContent1");
            keyword.setRemark("remark1");
            keyword.setLabel("Ads");
            keywords.add(keyword);

            keyword = new AuditingKeyword();
            keyword.setContent("demoContent2");
            keyword.setRemark("remark2");
            keyword.setLabel("Ads");
            keywords.add(keyword);
            AuditingKeywordResponse response = cosclient.addAuditingLibKeyWord(request);
            libID = response.getLibID();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void describeAuditingKeyWordList() {
        try {
            AuditingKeywordRequest request = new AuditingKeywordRequest();
            request.setBucketName(bucket);
            request.setLibId(libID);
            AuditingKeywordResponse response = cosclient.describeAuditingKeyWordList(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteAuditingKeyWordList() {
        try {
            AuditingKeywordRequest request = new AuditingKeywordRequest();
            request.setBucketName(bucket);
            request.setLibId(libID);

            List<String> keywordIDs = request.getKeywordIDs();
            keywordIDs.add("10884372");
            keywordIDs.add("10884373");
            AuditingKeywordResponse response = cosclient.deleteAuditingKeyWord(request);
            System.out.println(Jackson.toJsonString(response));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }
}
