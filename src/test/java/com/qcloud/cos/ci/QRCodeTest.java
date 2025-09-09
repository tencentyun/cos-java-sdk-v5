package com.qcloud.cos.ci;

import com.qcloud.cos.AbstractCOSClientCITest;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ciModel.image.GenerateQrcodeRequest;
import com.qcloud.cos.utils.IOUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class QRCodeTest extends AbstractCOSClientCITest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        AbstractCOSClientCITest.initCosClient();
    }

    @AfterClass
    public static void tearDownAfterClass() {
        AbstractCOSClientCITest.closeCosClient();
    }

    @Test
    public void identifyQrCodeWithGetObject() throws IOException {
        String key = "/SDK/Images/QRCode/test.png";
        GetObjectRequest request = new GetObjectRequest(bucket, key);
        request.putCustomQueryParameter("ci-process", "QRcode");
        request.putCustomQueryParameter("cover", "0");
        COSObject object = cosclient.getObject(request);
        COSObjectInputStream content = object.getObjectContent();
        String response = IOUtils.toString(content);
        System.out.println(response);
    }
}
