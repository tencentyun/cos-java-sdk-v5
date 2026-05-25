
package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.ciModel.persistence.DetectPetRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectPetResponse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


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

        request.setObjectKey("/SDK/AI/pet/dog1.png");

        DetectPetResponse detectPetResponse = cosclient.detectPet(request);
        System.out.println(detectPetResponse.toString());
    }

    @Test
    public void detectPetWithDetectUrl() {
        DetectPetRequest request = new DetectPetRequest();

        request.setBucketName("chongqingtest-1251704708");

        // 使用外部图片URL（SDK会自动进行URL编码，不需要手动编码）
        request.setDetectUrl("https://chongqingtest-1251704708.cos.ap-chongqing.myqcloud.com/SDK/AI/pet/dog1.png");

        DetectPetResponse detectPetResponse = cosclient.detectPet(request);
        System.out.println(detectPetResponse.toString());
    }
}