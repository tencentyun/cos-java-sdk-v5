package com.qcloud.cos;

import com.qcloud.cos.model.CopyObjectRequest;
import com.qcloud.cos.model.CopyPartRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.transfer.PauseStatus;
import com.qcloud.cos.transfer.Transfer;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.TransferManagerUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class TransfermanagerUtilsTest {
    private String appid_ = System.getenv("appid");
    private String secretId_ = System.getenv("secretId");
    private String secretKey_ = System.getenv("secretKey");
    private String region_ = System.getenv("region");
    private String bucket_ = System.getenv("bucket") + (int) (Math.random() * 1000) + "-" + appid_;

    @Test
    public void testTransferManagerUtils() {
        int inputStreamLength = 1024 * 1024;
        byte[] data = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);
        String key = "testTransferManagerUtils";
        ObjectMetadata objectMetadata = new ObjectMetadata();

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket_, key, inputStream, objectMetadata);
        long result = TransferManagerUtils.getContentLength(putObjectRequest);
        assertEquals(-1, result);

        objectMetadata.setContentLength(inputStreamLength);
        putObjectRequest.setMetadata(objectMetadata);
        result = TransferManagerUtils.getContentLength(putObjectRequest);
        assertEquals(inputStreamLength, result);

        Boolean isParallelizable = TransferManagerUtils.isUploadParallelizable(putObjectRequest, false);
        assertEquals(false, isParallelizable);

        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(bucket_, "testSrc", bucket_, "testDst");
        TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
        long part_size = TransferManagerUtils.calculateOptimalPartSizeForCopy(copyObjectRequest, transferManagerConfiguration, 1024 * 1024L);
        assertEquals(104857600, part_size);

        PauseStatus pauseStatus = TransferManagerUtils.determinePauseStatus(Transfer.TransferState.Waiting, true);
        assertEquals(PauseStatus.CANCELLED_BEFORE_START, pauseStatus);

        pauseStatus = TransferManagerUtils.determinePauseStatus(Transfer.TransferState.InProgress, true);
        assertEquals(PauseStatus.CANCELLED, pauseStatus);

        pauseStatus = TransferManagerUtils.determinePauseStatus(Transfer.TransferState.Waiting, false);
        assertEquals(PauseStatus.NOT_STARTED, pauseStatus);

        pauseStatus = TransferManagerUtils.determinePauseStatus(Transfer.TransferState.Completed, false);
        assertEquals(PauseStatus.NO_EFFECT, pauseStatus);

        putObjectRequest.setFixedEndpointAddr("testFixedEndpointAddr");
        PutObjectRequest putObjectRequest2 = new PutObjectRequest(bucket_, key, inputStream, new ObjectMetadata());
        TransferManagerUtils.populateEndpointAddr(putObjectRequest, putObjectRequest2);
        assertEquals(putObjectRequest.getFixedEndpointAddr(), putObjectRequest2.getFixedEndpointAddr());
    }
}
