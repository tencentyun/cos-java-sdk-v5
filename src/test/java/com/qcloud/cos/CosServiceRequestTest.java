package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.event.ProgressListener;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class CosServiceRequestTest {
    @Test
    public void testCosServiceRequestTest() {
        int inputStreamLength = 1 * 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);

        PutObjectRequest putObjectRequest = new PutObjectRequest("test_bucket", "test", inputStream, new ObjectMetadata());
        putObjectRequest.putCustomQueryParameter("response-content-disposition", "attachment");
        PutObjectRequest putObjectRequest_clone = putObjectRequest.clone();

        String secretId_ = System.getenv("secretId");
        String secretKey_ = System.getenv("secretKey");
        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        CosServiceRequest serviceRequest = new CosServiceRequest();
        serviceRequest.setCosCredentials(cred);
        serviceRequest.setGeneralProgressListener(null);
        serviceRequest.setGeneralProgressListener(ProgressListener.NOOP);
        serviceRequest.setCiSpecialEndParameter("test_end_parameter");

        CosServiceRequest serviceRequest_clone = serviceRequest.clone();
        CosServiceRequest serviceRequest_clone_clone = serviceRequest_clone.clone();
        CosServiceRequest serviceRequest_clone_root = serviceRequest_clone_clone.getCloneRoot();
        CosServiceRequest serviceRequest_clone_source = serviceRequest_clone.getCloneSource();
        assertEquals(secretId_, serviceRequest_clone_root.getCosCredentials().getCOSAccessKeyId());
        assertEquals(secretId_, serviceRequest_clone_source.getCosCredentials().getCOSAccessKeyId());
        assertEquals(secretKey_, serviceRequest_clone_root.getCosCredentials().getCOSSecretKey());
        assertEquals(secretKey_, serviceRequest_clone_source.getCosCredentials().getCOSSecretKey());
    }
}
