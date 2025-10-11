
package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectPetRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectPetResponse;
import com.qcloud.cos.utils.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class DetectPetTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void detectPetWithGetObject() {
        DetectPetRequest request = new DetectPetRequest();

        request.setBucketName("chongqingtest-1251704708");

        request.setObjectKey("/SDK/AI/pet/dog.png");

        DetectPetResponse detectPetResponse = cosclient.detectPet(request);
        System.out.println(detectPetResponse.toString());
    }
}