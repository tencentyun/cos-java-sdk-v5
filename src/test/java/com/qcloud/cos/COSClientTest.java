package com.qcloud.cos;

import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSCredentialsProvider;
import com.qcloud.cos.auth.COSSigner;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.exception.MultiObjectDeleteException;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.model.*;
import com.qcloud.cos.model.Tag.Tag;
import com.qcloud.cos.model.bucketcertificate.BucketDomainCertificateInfo;
import com.qcloud.cos.model.bucketcertificate.BucketDomainCertificateRequest;
import com.qcloud.cos.model.bucketcertificate.BucketGetDomainCertificate;
import com.qcloud.cos.model.bucketcertificate.BucketPutDomainCertificate;
import com.qcloud.cos.model.bucketcertificate.SetBucketDomainCertificateRequest;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.AudioAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.AuditingInputObject;
import com.qcloud.cos.model.ciModel.auditing.AuditingSnapshotObject;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingInputObject;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.BatchImageAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.CallbackVersion;
import com.qcloud.cos.model.ciModel.auditing.Conf;
import com.qcloud.cos.model.ciModel.auditing.DescribeImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.DocumentAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.DocumentAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.Encryption;
import com.qcloud.cos.model.ciModel.auditing.Freeze;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.ImageAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.ReportBadCaseRequest;
import com.qcloud.cos.model.ciModel.auditing.SuggestedLabel;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.TextAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.UserInfo;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.VideoAuditingResponse;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingRequest;
import com.qcloud.cos.model.ciModel.auditing.WebpageAuditingResponse;
import com.qcloud.cos.model.ciModel.bucket.DocBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.DocBucketResponse;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketRequest;
import com.qcloud.cos.model.ciModel.bucket.MediaBucketResponse;
import com.qcloud.cos.model.ciModel.common.BatchInputObject;
import com.qcloud.cos.model.ciModel.common.CImageProcessRequest;
import com.qcloud.cos.model.ciModel.common.ImageProcessRequest;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.image.GenerateQrcodeRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelRequest;
import com.qcloud.cos.model.ciModel.image.ImageLabelResponse;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Request;
import com.qcloud.cos.model.ciModel.image.ImageLabelV2Response;
import com.qcloud.cos.model.ciModel.image.ImageSearchRequest;
import com.qcloud.cos.model.ciModel.image.ImageSearchResponse;
import com.qcloud.cos.model.ciModel.image.ImageStyleRequest;
import com.qcloud.cos.model.ciModel.image.ImageStyleResponse;
import com.qcloud.cos.model.ciModel.image.OpenImageSearchRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobRequest;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import com.qcloud.cos.model.ciModel.job.CallBackMqConfig;
import com.qcloud.cos.model.ciModel.job.DocHtmlRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListRequest;
import com.qcloud.cos.model.ciModel.job.DocJobListResponse;
import com.qcloud.cos.model.ciModel.job.DocJobObject;
import com.qcloud.cos.model.ciModel.job.DocJobRequest;
import com.qcloud.cos.model.ciModel.job.DocJobResponse;
import com.qcloud.cos.model.ciModel.job.DocOperationObject;
import com.qcloud.cos.model.ciModel.job.DocProcessObject;
import com.qcloud.cos.model.ciModel.job.FileCompressConfig;
import com.qcloud.cos.model.ciModel.job.FileHashCodeConfig;
import com.qcloud.cos.model.ciModel.job.FileProcessJobResponse;
import com.qcloud.cos.model.ciModel.job.FileProcessJobType;
import com.qcloud.cos.model.ciModel.job.FileProcessOperation;
import com.qcloud.cos.model.ciModel.job.FileProcessRequest;
import com.qcloud.cos.model.ciModel.job.FileUnCompressConfig;
import com.qcloud.cos.model.ciModel.job.JobParam;
import com.qcloud.cos.model.ciModel.job.MediaJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobsRequest;
import com.qcloud.cos.model.ciModel.job.MediaListJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoRequest;
import com.qcloud.cos.model.ciModel.mediaInfo.MediaInfoResponse;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.DetectCarRequest;
import com.qcloud.cos.model.ciModel.persistence.DetectCarResponse;
import com.qcloud.cos.model.ciModel.queue.DocListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.DocQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaListQueueResponse;
import com.qcloud.cos.model.ciModel.queue.MediaNotifyConfig;
import com.qcloud.cos.model.ciModel.queue.MediaQueueRequest;
import com.qcloud.cos.model.ciModel.queue.MediaQueueResponse;
import com.qcloud.cos.model.ciModel.snapshot.CosSnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Request;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Response;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotRequest;
import com.qcloud.cos.model.ciModel.snapshot.SnapshotResponse;
import com.qcloud.cos.model.ciModel.template.MediaListTemplateResponse;
import com.qcloud.cos.model.ciModel.template.MediaTemplateRequest;
import com.qcloud.cos.model.ciModel.template.MediaTemplateResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowExecutionsResponse;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListRequest;
import com.qcloud.cos.model.ciModel.workflow.MediaWorkflowListResponse;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskRequest;
import com.qcloud.cos.model.fetch.GetAsyncFetchTaskResult;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskRequest;
import com.qcloud.cos.model.fetch.PutAsyncFetchTaskResult;
import com.qcloud.cos.model.inventory.InventoryConfiguration;
import com.qcloud.cos.model.inventory.InventoryCosBucketDestination;
import com.qcloud.cos.model.inventory.InventoryDestination;
import com.qcloud.cos.model.inventory.InventoryFilter;
import com.qcloud.cos.model.inventory.InventorySchedule;
import com.qcloud.cos.model.lifecycle.LifecycleFilter;
import com.qcloud.cos.region.Region;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class COSClientTest {

    @Mock
    private COSCredentials mockCred;
    @Mock
    private ClientConfig mockClientConfig;

    private COSClient cosClientUnderTest;

    @Before
    public void setUp() throws Exception {
        cosClientUnderTest = new COSClient(mockCred, mockClientConfig);
    }

    @Test
    public void testShutdown() throws Exception {
        // Setup
        // Run the test
        cosClientUnderTest.shutdown();

        // Verify the results
    }

    @Test
    public void testSetCOSCredentials() throws Exception {
        // Setup
        final COSCredentials cred = null;

        // Run the test
        cosClientUnderTest.setCOSCredentials(cred);

        // Verify the results
    }

    @Test
    public void testSetCOSCredentialsProvider() throws Exception {
        // Setup
        final COSCredentialsProvider credProvider = null;

        // Run the test
        cosClientUnderTest.setCOSCredentialsProvider(credProvider);

        // Verify the results
    }

    @Test
    public void testCreateRequest() throws Exception {
        // Setup
        final CosServiceRequest originalRequest = new CosServiceRequest();
        originalRequest.setFixedEndpointAddr("endpoint");
        originalRequest.setCosCredentials(null);
        originalRequest.setCiSpecialEndParameter("ciSpecialEndParameter");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final CosHttpRequest<CosServiceRequest> result = cosClientUnderTest.createRequest("bucketName",
                "destinationKey", originalRequest, HttpMethodName.GET);

        // Verify the results
    }

    @Test
    public void testPutObject1() throws Exception {
        // Setup
        final PutObjectRequest putObjectRequest = new PutObjectRequest("bucketName", "destinationKey",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PutObjectResult result = cosClientUnderTest.putObject(putObjectRequest);

        // Verify the results
    }

    @Test
    public void testPutObject1_ThrowsCosClientException() throws Exception {
        // Setup
        final PutObjectRequest putObjectRequest = new PutObjectRequest("bucketName", "destinationKey",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.putObject(putObjectRequest));
    }

    @Test
    public void testPutObject1_ThrowsCosServiceException() throws Exception {
        // Setup
        final PutObjectRequest putObjectRequest = new PutObjectRequest("bucketName", "destinationKey",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.putObject(putObjectRequest));
    }

    @Test
    public void testAppendObject() throws Exception {
        // Setup
        final AppendObjectRequest appendObjectRequest = new AppendObjectRequest("bucketName", "key",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final AppendObjectResult result = cosClientUnderTest.appendObject(appendObjectRequest);

        // Verify the results
    }

    @Test
    public void testAppendObject_ThrowsCosServiceException() throws Exception {
        // Setup
        final AppendObjectRequest appendObjectRequest = new AppendObjectRequest("bucketName", "key",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.appendObject(appendObjectRequest));
    }

    @Test
    public void testAppendObject_ThrowsCosClientException() throws Exception {
        // Setup
        final AppendObjectRequest appendObjectRequest = new AppendObjectRequest("bucketName", "key",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.appendObject(appendObjectRequest));
    }

    @Test
    public void testRename() throws Exception {
        // Setup
        final RenameRequest renameRequest = new RenameRequest("bucketName", "srcObject", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.rename(renameRequest);

        // Verify the results
    }

    @Test
    public void testRename_ThrowsCosServiceException() throws Exception {
        // Setup
        final RenameRequest renameRequest = new RenameRequest("bucketName", "srcObject", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.rename(renameRequest));
    }

    @Test
    public void testRename_ThrowsCosClientException() throws Exception {
        // Setup
        final RenameRequest renameRequest = new RenameRequest("bucketName", "srcObject", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.rename(renameRequest));
    }

    @Test
    public void testUploadObjectInternal() throws Exception {
        // Setup
        final PutObjectRequest uploadObjectRequest = new PutObjectRequest("bucketName", "destinationKey",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectMetadata result = cosClientUnderTest.uploadObjectInternal(UploadMode.APPEND_OBJECT,
                uploadObjectRequest);

        // Verify the results
    }

    @Test
    public void testUploadObjectInternal_ThrowsCosClientException() throws Exception {
        // Setup
        final PutObjectRequest uploadObjectRequest = new PutObjectRequest("bucketName", "destinationKey",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.uploadObjectInternal(UploadMode.APPEND_OBJECT, uploadObjectRequest));
    }

    @Test
    public void testUploadObjectInternal_ThrowsCosServiceException() throws Exception {
        // Setup
        final PutObjectRequest uploadObjectRequest = new PutObjectRequest("bucketName", "destinationKey",
                new ByteArrayInputStream("content".getBytes()), new ObjectMetadata());
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.uploadObjectInternal(UploadMode.APPEND_OBJECT, uploadObjectRequest));
    }

    @Test
    public void testPutObject2() throws Exception {
        // Setup
        final File file = new File("filename.txt");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PutObjectResult result = cosClientUnderTest.putObject("bucketName", "destinationKey", file);

        // Verify the results
    }

    @Test
    public void testPutObject2_ThrowsCosClientException() throws Exception {
        // Setup
        final File file = new File("filename.txt");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.putObject("bucketName", "destinationKey", file));
    }

    @Test
    public void testPutObject2_ThrowsCosServiceException() throws Exception {
        // Setup
        final File file = new File("filename.txt");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.putObject("bucketName", "destinationKey", file));
    }

    @Test
    public void testPutObject3() throws Exception {
        // Setup
        final InputStream input = new ByteArrayInputStream("content".getBytes());
        final ObjectMetadata metadata = new ObjectMetadata();
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PutObjectResult result = cosClientUnderTest.putObject("bucketName", "destinationKey", input, metadata);

        // Verify the results
    }

    @Test
    public void testPutObject3_EmptyInput() throws Exception {
        // Setup
        final InputStream input = new ByteArrayInputStream(new byte[]{});
        final ObjectMetadata metadata = new ObjectMetadata();
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PutObjectResult result = cosClientUnderTest.putObject("bucketName", "destinationKey", input, metadata);

        // Verify the results
    }

    @Test
    public void testPutObject3_BrokenInput() throws Exception {
        // Setup
        final InputStream input = new InputStream() {
            private final IOException exception = new IOException("Error");

            @Override
            public int read() throws IOException {
                throw exception;
            }

            @Override
            public int available() throws IOException {
                throw exception;
            }

            @Override
            public long skip(final long n) throws IOException {
                throw exception;
            }

            @Override
            public synchronized void reset() throws IOException {
                throw exception;
            }

            @Override
            public void close() throws IOException {
                throw exception;
            }
        };
        final ObjectMetadata metadata = new ObjectMetadata();
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.putObject("bucketName", "destinationKey", input, metadata));
    }

    @Test
    public void testPutObject4() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PutObjectResult result = cosClientUnderTest.putObject("bucketName", "destinationKey", "content");

        // Verify the results
    }

    @Test
    public void testPutObject4_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.putObject("bucketName", "destinationKey", "content"));
    }

    @Test
    public void testPutObject4_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.putObject("bucketName", "destinationKey", "content"));
    }

    @Test
    public void testGetObject1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final COSObject result = cosClientUnderTest.getObject("bucketName", "destinationKey");

        // Verify the results
    }

    @Test
    public void testGetObject1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getObject("bucketName", "destinationKey"));
    }

    @Test
    public void testGetObject1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getObject("bucketName", "destinationKey"));
    }

    @Test
    public void testGetObject2() throws Exception {
        // Setup
        final GetObjectRequest getObjectRequest = new GetObjectRequest("bucketName", "destinationKey", "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final COSObject result = cosClientUnderTest.getObject(getObjectRequest);

        // Verify the results
    }

    @Test
    public void testGetObject2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetObjectRequest getObjectRequest = new GetObjectRequest("bucketName", "destinationKey", "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getObject(getObjectRequest));
    }

    @Test
    public void testGetObject2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetObjectRequest getObjectRequest = new GetObjectRequest("bucketName", "destinationKey", "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getObject(getObjectRequest));
    }

    @Test
    public void testGetObject3() throws Exception {
        // Setup
        final GetObjectRequest getObjectRequest = new GetObjectRequest("bucketName", "destinationKey", "versionId");
        final File destinationFile = new File("filename.txt");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectMetadata result = cosClientUnderTest.getObject(getObjectRequest, destinationFile);

        // Verify the results
    }

    @Test
    public void testGetObject3_ThrowsCosClientException() throws Exception {
        // Setup
        final GetObjectRequest getObjectRequest = new GetObjectRequest("bucketName", "destinationKey", "versionId");
        final File destinationFile = new File("filename.txt");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getObject(getObjectRequest, destinationFile));
    }

    @Test
    public void testGetObject3_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetObjectRequest getObjectRequest = new GetObjectRequest("bucketName", "destinationKey", "versionId");
        final File destinationFile = new File("filename.txt");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getObject(getObjectRequest, destinationFile));
    }

    @Test
    public void testPutSymlink() throws Exception {
        // Setup
        final PutSymlinkRequest putSymlinkRequest = new PutSymlinkRequest("bucketName", "destinationKey", "target");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PutSymlinkResult result = cosClientUnderTest.putSymlink(putSymlinkRequest);

        // Verify the results
    }

    @Test
    public void testGetSymlink() throws Exception {
        // Setup
        final GetSymlinkRequest getSymlinkRequest = new GetSymlinkRequest("bucketName", "destinationKey", "versionId");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final GetSymlinkResult result = cosClientUnderTest.getSymlink(getSymlinkRequest);

        // Verify the results
    }

    @Test
    public void testDoesObjectExist() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final boolean result = cosClientUnderTest.doesObjectExist("bucketName", "destinationKey");

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDoesObjectExist_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.doesObjectExist("bucketName", "destinationKey"));
    }

    @Test
    public void testDoesObjectExist_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.doesObjectExist("bucketName", "destinationKey"));
    }

    @Test
    public void testGetObjectMetadata1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectMetadata result = cosClientUnderTest.getObjectMetadata("bucketName", "destinationKey");

        // Verify the results
    }

    @Test
    public void testGetObjectMetadata1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getObjectMetadata("bucketName", "destinationKey"));
    }

    @Test
    public void testGetObjectMetadata1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getObjectMetadata("bucketName", "destinationKey"));
    }

    @Test
    public void testGetObjectMetadata2() throws Exception {
        // Setup
        final GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest("bucketName",
                "destinationKey", "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectMetadata result = cosClientUnderTest.getObjectMetadata(getObjectMetadataRequest);

        // Verify the results
    }

    @Test
    public void testGetObjectMetadata2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest("bucketName",
                "destinationKey", "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getObjectMetadata(getObjectMetadataRequest));
    }

    @Test
    public void testGetObjectMetadata2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetObjectMetadataRequest getObjectMetadataRequest = new GetObjectMetadataRequest("bucketName",
                "destinationKey", "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getObjectMetadata(getObjectMetadataRequest));
    }

    @Test
    public void testDeleteObject1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteObject("bucketName", "destinationKey");

        // Verify the results
    }

    @Test
    public void testDeleteObject1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteObject("bucketName", "destinationKey"));
    }

    @Test
    public void testDeleteObject1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteObject("bucketName", "destinationKey"));
    }

    @Test
    public void testDeleteObject2() throws Exception {
        // Setup
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest("bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteObject(deleteObjectRequest);

        // Verify the results
    }

    @Test
    public void testDeleteObject2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest("bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteObject(deleteObjectRequest));
    }

    @Test
    public void testDeleteObject2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest("bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteObject(deleteObjectRequest));
    }

    @Test
    public void testDeleteObjects() throws Exception {
        // Setup
        final DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DeleteObjectsResult result = cosClientUnderTest.deleteObjects(deleteObjectsRequest);

        // Verify the results
    }

    @Test
    public void testDeleteObjects_ThrowsMultiObjectDeleteException() throws Exception {
        // Setup
        final DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(MultiObjectDeleteException.class, () -> cosClientUnderTest.deleteObjects(deleteObjectsRequest));
    }

    @Test
    public void testDeleteObjects_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteObjects(deleteObjectsRequest));
    }

    @Test
    public void testDeleteObjects_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteObjects(deleteObjectsRequest));
    }

    @Test
    public void testDeleteVersion1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteVersion("bucketName", "destinationKey", "versionId");

        // Verify the results
    }

    @Test
    public void testDeleteVersion1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteVersion("bucketName", "destinationKey", "versionId"));
    }

    @Test
    public void testDeleteVersion1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteVersion("bucketName", "destinationKey", "versionId"));
    }

    @Test
    public void testDeleteVersion2() throws Exception {
        // Setup
        final DeleteVersionRequest deleteVersionRequest = new DeleteVersionRequest("bucketName", "destinationKey",
                "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteVersion(deleteVersionRequest);

        // Verify the results
    }

    @Test
    public void testDeleteVersion2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteVersionRequest deleteVersionRequest = new DeleteVersionRequest("bucketName", "destinationKey",
                "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteVersion(deleteVersionRequest));
    }

    @Test
    public void testDeleteVersion2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteVersionRequest deleteVersionRequest = new DeleteVersionRequest("bucketName", "destinationKey",
                "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteVersion(deleteVersionRequest));
    }

    @Test
    public void testCreateBucket1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Bucket result = cosClientUnderTest.createBucket("bucketName");

        // Verify the results
    }

    @Test
    public void testCreateBucket1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.createBucket("bucketName"));
    }

    @Test
    public void testCreateBucket1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.createBucket("bucketName"));
    }

    @Test
    public void testCreateBucket2() throws Exception {
        // Setup
        final CreateBucketRequest createBucketRequest = new CreateBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Bucket result = cosClientUnderTest.createBucket(createBucketRequest);

        // Verify the results
    }

    @Test
    public void testCreateBucket2_ThrowsCosClientException() throws Exception {
        // Setup
        final CreateBucketRequest createBucketRequest = new CreateBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.createBucket(createBucketRequest));
    }

    @Test
    public void testCreateBucket2_ThrowsCosServiceException() throws Exception {
        // Setup
        final CreateBucketRequest createBucketRequest = new CreateBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.createBucket(createBucketRequest));
    }

    @Test
    public void testDeleteBucket1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucket("bucketName");

        // Verify the results
    }

    @Test
    public void testDeleteBucket1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteBucket("bucketName"));
    }

    @Test
    public void testDeleteBucket1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteBucket("bucketName"));
    }

    @Test
    public void testDeleteBucket2() throws Exception {
        // Setup
        final DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucket(deleteBucketRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucket2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteBucket(deleteBucketRequest));
    }

    @Test
    public void testDeleteBucket2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteBucket(deleteBucketRequest));
    }

    @Test
    public void testDoesBucketExist() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final boolean result = cosClientUnderTest.doesBucketExist("bucketName");

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDoesBucketExist_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.doesBucketExist("bucketName"));
    }

    @Test
    public void testDoesBucketExist_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.doesBucketExist("bucketName"));
    }

    @Test
    public void testHeadBucket() throws Exception {
        // Setup
        final HeadBucketRequest headBucketRequest = new HeadBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final HeadBucketResult result = cosClientUnderTest.headBucket(headBucketRequest);

        // Verify the results
    }

    @Test
    public void testHeadBucket_ThrowsCosClientException() throws Exception {
        // Setup
        final HeadBucketRequest headBucketRequest = new HeadBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.headBucket(headBucketRequest));
    }

    @Test
    public void testHeadBucket_ThrowsCosServiceException() throws Exception {
        // Setup
        final HeadBucketRequest headBucketRequest = new HeadBucketRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.headBucket(headBucketRequest));
    }

    @Test
    public void testListBuckets1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final List<Bucket> result = cosClientUnderTest.listBuckets();

        // Verify the results
    }

    @Test
    public void testListBuckets1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listBuckets());
    }

    @Test
    public void testListBuckets1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listBuckets());
    }

    @Test
    public void testListBuckets2() throws Exception {
        // Setup
        final ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final List<Bucket> result = cosClientUnderTest.listBuckets(listBucketsRequest);

        // Verify the results
    }

    @Test
    public void testListBuckets2_ThrowsCosClientException() throws Exception {
        // Setup
        final ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listBuckets(listBucketsRequest));
    }

    @Test
    public void testListBuckets2_ThrowsCosServiceException() throws Exception {
        // Setup
        final ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listBuckets(listBucketsRequest));
    }

    @Test
    public void testGetBucketLocation1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final String result = cosClientUnderTest.getBucketLocation("bucketName");

        // Verify the results
        assertEquals("policyText", result);
    }

    @Test
    public void testGetBucketLocation1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketLocation("bucketName"));
    }

    @Test
    public void testGetBucketLocation1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketLocation("bucketName"));
    }

    @Test
    public void testGetBucketLocation2() throws Exception {
        // Setup
        final GetBucketLocationRequest getBucketLocationRequest = new GetBucketLocationRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final String result = cosClientUnderTest.getBucketLocation(getBucketLocationRequest);

        // Verify the results
        assertEquals("policyText", result);
    }

    @Test
    public void testGetBucketLocation2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketLocationRequest getBucketLocationRequest = new GetBucketLocationRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketLocation(getBucketLocationRequest));
    }

    @Test
    public void testGetBucketLocation2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketLocationRequest getBucketLocationRequest = new GetBucketLocationRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketLocation(getBucketLocationRequest));
    }

    @Test
    public void testInitiateMultipartUpload() throws Exception {
        // Setup
        final InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(
                "bucketName", "destinationKey", 0L, 0L);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final InitiateMultipartUploadResult result = cosClientUnderTest.initiateMultipartUpload(
                initiateMultipartUploadRequest);

        // Verify the results
    }

    @Test
    public void testInitiateMultipartUpload_ThrowsCosClientException() throws Exception {
        // Setup
        final InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(
                "bucketName", "destinationKey", 0L, 0L);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.initiateMultipartUpload(initiateMultipartUploadRequest));
    }

    @Test
    public void testInitiateMultipartUpload_ThrowsCosServiceException() throws Exception {
        // Setup
        final InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(
                "bucketName", "destinationKey", 0L, 0L);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.initiateMultipartUpload(initiateMultipartUploadRequest));
    }

    @Test
    public void testUploadPart() throws Exception {
        // Setup
        final UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setFixedEndpointAddr("endpoint");
        uploadPartRequest.setCosCredentials(null);
        uploadPartRequest.setInputStream(new ByteArrayInputStream("content".getBytes()));
        uploadPartRequest.setBucketName("bucketName");
        uploadPartRequest.setKey("destinationKey");
        uploadPartRequest.setUploadId("uploadId");
        uploadPartRequest.setPartNumber(0);
        uploadPartRequest.setPartSize(0L);
        uploadPartRequest.setMd5Digest("md5Digest");
        uploadPartRequest.setFile(new File("filename.txt"));
        uploadPartRequest.setFileOffset(0L);
        uploadPartRequest.setLastPart(false);
        uploadPartRequest.setSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        uploadPartRequest.setObjectMetadata(new ObjectMetadata());
        uploadPartRequest.setTrafficLimit(0);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final UploadPartResult result = cosClientUnderTest.uploadPart(uploadPartRequest);

        // Verify the results
    }

    @Test
    public void testUploadPart_ThrowsCosClientException() throws Exception {
        // Setup
        final UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setFixedEndpointAddr("endpoint");
        uploadPartRequest.setCosCredentials(null);
        uploadPartRequest.setInputStream(new ByteArrayInputStream("content".getBytes()));
        uploadPartRequest.setBucketName("bucketName");
        uploadPartRequest.setKey("destinationKey");
        uploadPartRequest.setUploadId("uploadId");
        uploadPartRequest.setPartNumber(0);
        uploadPartRequest.setPartSize(0L);
        uploadPartRequest.setMd5Digest("md5Digest");
        uploadPartRequest.setFile(new File("filename.txt"));
        uploadPartRequest.setFileOffset(0L);
        uploadPartRequest.setLastPart(false);
        uploadPartRequest.setSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        uploadPartRequest.setObjectMetadata(new ObjectMetadata());
        uploadPartRequest.setTrafficLimit(0);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.uploadPart(uploadPartRequest));
    }

    @Test
    public void testUploadPart_ThrowsCosServiceException() throws Exception {
        // Setup
        final UploadPartRequest uploadPartRequest = new UploadPartRequest();
        uploadPartRequest.setFixedEndpointAddr("endpoint");
        uploadPartRequest.setCosCredentials(null);
        uploadPartRequest.setInputStream(new ByteArrayInputStream("content".getBytes()));
        uploadPartRequest.setBucketName("bucketName");
        uploadPartRequest.setKey("destinationKey");
        uploadPartRequest.setUploadId("uploadId");
        uploadPartRequest.setPartNumber(0);
        uploadPartRequest.setPartSize(0L);
        uploadPartRequest.setMd5Digest("md5Digest");
        uploadPartRequest.setFile(new File("filename.txt"));
        uploadPartRequest.setFileOffset(0L);
        uploadPartRequest.setLastPart(false);
        uploadPartRequest.setSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        uploadPartRequest.setObjectMetadata(new ObjectMetadata());
        uploadPartRequest.setTrafficLimit(0);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.uploadPart(uploadPartRequest));
    }

    @Test
    public void testListParts() throws Exception {
        // Setup
        final ListPartsRequest listPartsRequest = new ListPartsRequest("bucketName", "destinationKey", "uploadId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PartListing result = cosClientUnderTest.listParts(listPartsRequest);

        // Verify the results
    }

    @Test
    public void testListParts_ThrowsCosClientException() throws Exception {
        // Setup
        final ListPartsRequest listPartsRequest = new ListPartsRequest("bucketName", "destinationKey", "uploadId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listParts(listPartsRequest));
    }

    @Test
    public void testListParts_ThrowsCosServiceException() throws Exception {
        // Setup
        final ListPartsRequest listPartsRequest = new ListPartsRequest("bucketName", "destinationKey", "uploadId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listParts(listPartsRequest));
    }

    @Test
    public void testAbortMultipartUpload() throws Exception {
        // Setup
        final AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest("bucketName",
                "destinationKey", "uploadId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.abortMultipartUpload(abortMultipartUploadRequest);

        // Verify the results
    }

    @Test
    public void testAbortMultipartUpload_ThrowsCosClientException() throws Exception {
        // Setup
        final AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest("bucketName",
                "destinationKey", "uploadId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.abortMultipartUpload(abortMultipartUploadRequest));
    }

    @Test
    public void testAbortMultipartUpload_ThrowsCosServiceException() throws Exception {
        // Setup
        final AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest("bucketName",
                "destinationKey", "uploadId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.abortMultipartUpload(abortMultipartUploadRequest));
    }

    @Test
    public void testCompleteMultipartUpload() throws Exception {
        // Setup
        final CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                "bucketName", "destinationKey", "uploadId", Arrays.asList(new PartETag(0, "eTag")));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final CompleteMultipartUploadResult result = cosClientUnderTest.completeMultipartUpload(
                completeMultipartUploadRequest);

        // Verify the results
    }

    @Test
    public void testCompleteMultipartUpload_ThrowsCosClientException() throws Exception {
        // Setup
        final CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                "bucketName", "destinationKey", "uploadId", Arrays.asList(new PartETag(0, "eTag")));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.completeMultipartUpload(completeMultipartUploadRequest));
    }

    @Test
    public void testCompleteMultipartUpload_ThrowsCosServiceException() throws Exception {
        // Setup
        final CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                "bucketName", "destinationKey", "uploadId", Arrays.asList(new PartETag(0, "eTag")));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.completeMultipartUpload(completeMultipartUploadRequest));
    }

    @Test
    public void testListMultipartUploads() throws Exception {
        // Setup
        final ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MultipartUploadListing result = cosClientUnderTest.listMultipartUploads(listMultipartUploadsRequest);

        // Verify the results
    }

    @Test
    public void testListMultipartUploads_ThrowsCosClientException() throws Exception {
        // Setup
        final ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.listMultipartUploads(listMultipartUploadsRequest));
    }

    @Test
    public void testListMultipartUploads_ThrowsCosServiceException() throws Exception {
        // Setup
        final ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.listMultipartUploads(listMultipartUploadsRequest));
    }

    @Test
    public void testListObjects1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectListing result = cosClientUnderTest.listObjects("bucketName");

        // Verify the results
    }

    @Test
    public void testListObjects1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listObjects("bucketName"));
    }

    @Test
    public void testListObjects1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listObjects("bucketName"));
    }

    @Test
    public void testListObjects2() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectListing result = cosClientUnderTest.listObjects("bucketName", "prefix");

        // Verify the results
    }

    @Test
    public void testListObjects2_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listObjects("bucketName", "prefix"));
    }

    @Test
    public void testListObjects2_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listObjects("bucketName", "prefix"));
    }

    @Test
    public void testListObjects3() throws Exception {
        // Setup
        final ListObjectsRequest listObjectsRequest = new ListObjectsRequest("bucketName", "prefix", "marker",
                "delimiter", 0);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectListing result = cosClientUnderTest.listObjects(listObjectsRequest);

        // Verify the results
    }

    @Test
    public void testListObjects3_ThrowsCosClientException() throws Exception {
        // Setup
        final ListObjectsRequest listObjectsRequest = new ListObjectsRequest("bucketName", "prefix", "marker",
                "delimiter", 0);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listObjects(listObjectsRequest));
    }

    @Test
    public void testListObjects3_ThrowsCosServiceException() throws Exception {
        // Setup
        final ListObjectsRequest listObjectsRequest = new ListObjectsRequest("bucketName", "prefix", "marker",
                "delimiter", 0);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listObjects(listObjectsRequest));
    }

    @Test
    public void testListNextBatchOfObjects1() throws Exception {
        // Setup
        final ObjectListing previousObjectListing = new ObjectListing();
        final COSObjectSummary cosObjectSummary = new COSObjectSummary();
        cosObjectSummary.setBucketName("bucketName");
        cosObjectSummary.setKey("key");
        cosObjectSummary.setETag("eTag");
        cosObjectSummary.setSize(0L);
        cosObjectSummary.setLastModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        cosObjectSummary.setOwner(new Owner("id", "displayName"));
        previousObjectListing.getObjectSummaries().addAll(Arrays.asList(cosObjectSummary));
        previousObjectListing.setNextMarker("marker");
        previousObjectListing.setBucketName("bucketName");
        previousObjectListing.setPrefix("prefix");
        previousObjectListing.setMarker("marker");
        previousObjectListing.setMaxKeys(0);
        previousObjectListing.setDelimiter("delimiter");
        previousObjectListing.setTruncated(false);
        previousObjectListing.setEncodingType("encodingType");

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectListing result = cosClientUnderTest.listNextBatchOfObjects(previousObjectListing);

        // Verify the results
    }

    @Test
    public void testListNextBatchOfObjects1_ThrowsCosClientException() throws Exception {
        // Setup
        final ObjectListing previousObjectListing = new ObjectListing();
        final COSObjectSummary cosObjectSummary = new COSObjectSummary();
        cosObjectSummary.setBucketName("bucketName");
        cosObjectSummary.setKey("key");
        cosObjectSummary.setETag("eTag");
        cosObjectSummary.setSize(0L);
        cosObjectSummary.setLastModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        cosObjectSummary.setOwner(new Owner("id", "displayName"));
        previousObjectListing.getObjectSummaries().addAll(Arrays.asList(cosObjectSummary));
        previousObjectListing.setNextMarker("marker");
        previousObjectListing.setBucketName("bucketName");
        previousObjectListing.setPrefix("prefix");
        previousObjectListing.setMarker("marker");
        previousObjectListing.setMaxKeys(0);
        previousObjectListing.setDelimiter("delimiter");
        previousObjectListing.setTruncated(false);
        previousObjectListing.setEncodingType("encodingType");

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listNextBatchOfObjects(previousObjectListing));
    }

    @Test
    public void testListNextBatchOfObjects1_ThrowsCosServiceException() throws Exception {
        // Setup
        final ObjectListing previousObjectListing = new ObjectListing();
        final COSObjectSummary cosObjectSummary = new COSObjectSummary();
        cosObjectSummary.setBucketName("bucketName");
        cosObjectSummary.setKey("key");
        cosObjectSummary.setETag("eTag");
        cosObjectSummary.setSize(0L);
        cosObjectSummary.setLastModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        cosObjectSummary.setOwner(new Owner("id", "displayName"));
        previousObjectListing.getObjectSummaries().addAll(Arrays.asList(cosObjectSummary));
        previousObjectListing.setNextMarker("marker");
        previousObjectListing.setBucketName("bucketName");
        previousObjectListing.setPrefix("prefix");
        previousObjectListing.setMarker("marker");
        previousObjectListing.setMaxKeys(0);
        previousObjectListing.setDelimiter("delimiter");
        previousObjectListing.setTruncated(false);
        previousObjectListing.setEncodingType("encodingType");

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listNextBatchOfObjects(previousObjectListing));
    }

    @Test
    public void testListNextBatchOfObjects2() throws Exception {
        // Setup
        final ObjectListing objectListing = new ObjectListing();
        final COSObjectSummary cosObjectSummary = new COSObjectSummary();
        cosObjectSummary.setBucketName("bucketName");
        cosObjectSummary.setKey("key");
        cosObjectSummary.setETag("eTag");
        cosObjectSummary.setSize(0L);
        cosObjectSummary.setLastModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        cosObjectSummary.setOwner(new Owner("id", "displayName"));
        objectListing.getObjectSummaries().addAll(Arrays.asList(cosObjectSummary));
        objectListing.setNextMarker("marker");
        objectListing.setBucketName("bucketName");
        objectListing.setPrefix("prefix");
        objectListing.setMarker("marker");
        objectListing.setMaxKeys(0);
        objectListing.setDelimiter("delimiter");
        objectListing.setTruncated(false);
        objectListing.setEncodingType("encodingType");
        final ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest = new ListNextBatchOfObjectsRequest(
                objectListing);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ObjectListing result = cosClientUnderTest.listNextBatchOfObjects(listNextBatchOfObjectsRequest);

        // Verify the results
    }

    @Test
    public void testListNextBatchOfObjects2_ThrowsCosClientException() throws Exception {
        // Setup
        final ObjectListing objectListing = new ObjectListing();
        final COSObjectSummary cosObjectSummary = new COSObjectSummary();
        cosObjectSummary.setBucketName("bucketName");
        cosObjectSummary.setKey("key");
        cosObjectSummary.setETag("eTag");
        cosObjectSummary.setSize(0L);
        cosObjectSummary.setLastModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        cosObjectSummary.setOwner(new Owner("id", "displayName"));
        objectListing.getObjectSummaries().addAll(Arrays.asList(cosObjectSummary));
        objectListing.setNextMarker("marker");
        objectListing.setBucketName("bucketName");
        objectListing.setPrefix("prefix");
        objectListing.setMarker("marker");
        objectListing.setMaxKeys(0);
        objectListing.setDelimiter("delimiter");
        objectListing.setTruncated(false);
        objectListing.setEncodingType("encodingType");
        final ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest = new ListNextBatchOfObjectsRequest(
                objectListing);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.listNextBatchOfObjects(listNextBatchOfObjectsRequest));
    }

    @Test
    public void testListNextBatchOfObjects2_ThrowsCosServiceException() throws Exception {
        // Setup
        final ObjectListing objectListing = new ObjectListing();
        final COSObjectSummary cosObjectSummary = new COSObjectSummary();
        cosObjectSummary.setBucketName("bucketName");
        cosObjectSummary.setKey("key");
        cosObjectSummary.setETag("eTag");
        cosObjectSummary.setSize(0L);
        cosObjectSummary.setLastModified(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        cosObjectSummary.setOwner(new Owner("id", "displayName"));
        objectListing.getObjectSummaries().addAll(Arrays.asList(cosObjectSummary));
        objectListing.setNextMarker("marker");
        objectListing.setBucketName("bucketName");
        objectListing.setPrefix("prefix");
        objectListing.setMarker("marker");
        objectListing.setMaxKeys(0);
        objectListing.setDelimiter("delimiter");
        objectListing.setTruncated(false);
        objectListing.setEncodingType("encodingType");
        final ListNextBatchOfObjectsRequest listNextBatchOfObjectsRequest = new ListNextBatchOfObjectsRequest(
                objectListing);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.listNextBatchOfObjects(listNextBatchOfObjectsRequest));
    }

    @Test
    public void testListVersions1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final VersionListing result = cosClientUnderTest.listVersions("bucketName", "prefix");

        // Verify the results
    }

    @Test
    public void testListVersions1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listVersions("bucketName", "prefix"));
    }

    @Test
    public void testListVersions1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listVersions("bucketName", "prefix"));
    }

    @Test
    public void testListVersions2() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final VersionListing result = cosClientUnderTest.listVersions("bucketName", "prefix", "keyMarker",
                "versionIdMarker", "delimiter", 0);

        // Verify the results
    }

    @Test
    public void testListVersions2_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.listVersions("bucketName", "prefix", "keyMarker", "versionIdMarker",
                        "delimiter", 0));
    }

    @Test
    public void testListVersions2_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.listVersions("bucketName", "prefix", "keyMarker", "versionIdMarker",
                        "delimiter", 0));
    }

    @Test
    public void testListVersions3() throws Exception {
        // Setup
        final ListVersionsRequest listVersionsRequest = new ListVersionsRequest("bucketName", "prefix", "keyMarker",
                "versionIdMarker", "delimiter", 0);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final VersionListing result = cosClientUnderTest.listVersions(listVersionsRequest);

        // Verify the results
    }

    @Test
    public void testListVersions3_ThrowsCosClientException() throws Exception {
        // Setup
        final ListVersionsRequest listVersionsRequest = new ListVersionsRequest("bucketName", "prefix", "keyMarker",
                "versionIdMarker", "delimiter", 0);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.listVersions(listVersionsRequest));
    }

    @Test
    public void testListVersions3_ThrowsCosServiceException() throws Exception {
        // Setup
        final ListVersionsRequest listVersionsRequest = new ListVersionsRequest("bucketName", "prefix", "keyMarker",
                "versionIdMarker", "delimiter", 0);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.listVersions(listVersionsRequest));
    }

    @Test
    public void testListNextBatchOfVersions1() throws Exception {
        // Setup
        final VersionListing previousVersionListing = new VersionListing();
        final COSVersionSummary cosVersionSummary = new COSVersionSummary();
        cosVersionSummary.setBucketName("bucketName");
        cosVersionSummary.setKey("key");
        cosVersionSummary.setVersionId("id");
        cosVersionSummary.setIsLatest(false);
        previousVersionListing.setVersionSummaries(Arrays.asList(cosVersionSummary));
        previousVersionListing.setBucketName("bucketName");
        previousVersionListing.setPrefix("prefix");
        previousVersionListing.setKeyMarker("keyMarker");
        previousVersionListing.setVersionIdMarker("marker");
        previousVersionListing.setMaxKeys(0);
        previousVersionListing.setDelimiter("delimiter");
        previousVersionListing.setNextKeyMarker("keyMarker");
        previousVersionListing.setNextVersionIdMarker("marker");
        previousVersionListing.setTruncated(false);
        previousVersionListing.setEncodingType("encodingType");

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final VersionListing result = cosClientUnderTest.listNextBatchOfVersions(previousVersionListing);

        // Verify the results
    }

    @Test
    public void testListNextBatchOfVersions1_ThrowsCosClientException() throws Exception {
        // Setup
        final VersionListing previousVersionListing = new VersionListing();
        final COSVersionSummary cosVersionSummary = new COSVersionSummary();
        cosVersionSummary.setBucketName("bucketName");
        cosVersionSummary.setKey("key");
        cosVersionSummary.setVersionId("id");
        cosVersionSummary.setIsLatest(false);
        previousVersionListing.setVersionSummaries(Arrays.asList(cosVersionSummary));
        previousVersionListing.setBucketName("bucketName");
        previousVersionListing.setPrefix("prefix");
        previousVersionListing.setKeyMarker("keyMarker");
        previousVersionListing.setVersionIdMarker("marker");
        previousVersionListing.setMaxKeys(0);
        previousVersionListing.setDelimiter("delimiter");
        previousVersionListing.setNextKeyMarker("keyMarker");
        previousVersionListing.setNextVersionIdMarker("marker");
        previousVersionListing.setTruncated(false);
        previousVersionListing.setEncodingType("encodingType");

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.listNextBatchOfVersions(previousVersionListing));
    }

    @Test
    public void testListNextBatchOfVersions1_ThrowsCosServiceException() throws Exception {
        // Setup
        final VersionListing previousVersionListing = new VersionListing();
        final COSVersionSummary cosVersionSummary = new COSVersionSummary();
        cosVersionSummary.setBucketName("bucketName");
        cosVersionSummary.setKey("key");
        cosVersionSummary.setVersionId("id");
        cosVersionSummary.setIsLatest(false);
        previousVersionListing.setVersionSummaries(Arrays.asList(cosVersionSummary));
        previousVersionListing.setBucketName("bucketName");
        previousVersionListing.setPrefix("prefix");
        previousVersionListing.setKeyMarker("keyMarker");
        previousVersionListing.setVersionIdMarker("marker");
        previousVersionListing.setMaxKeys(0);
        previousVersionListing.setDelimiter("delimiter");
        previousVersionListing.setNextKeyMarker("keyMarker");
        previousVersionListing.setNextVersionIdMarker("marker");
        previousVersionListing.setTruncated(false);
        previousVersionListing.setEncodingType("encodingType");

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.listNextBatchOfVersions(previousVersionListing));
    }

    @Test
    public void testListNextBatchOfVersions2() throws Exception {
        // Setup
        final VersionListing versionListing = new VersionListing();
        final COSVersionSummary cosVersionSummary = new COSVersionSummary();
        cosVersionSummary.setBucketName("bucketName");
        cosVersionSummary.setKey("key");
        cosVersionSummary.setVersionId("id");
        cosVersionSummary.setIsLatest(false);
        versionListing.setVersionSummaries(Arrays.asList(cosVersionSummary));
        versionListing.setBucketName("bucketName");
        versionListing.setPrefix("prefix");
        versionListing.setKeyMarker("keyMarker");
        versionListing.setVersionIdMarker("marker");
        versionListing.setMaxKeys(0);
        versionListing.setDelimiter("delimiter");
        versionListing.setNextKeyMarker("keyMarker");
        versionListing.setNextVersionIdMarker("marker");
        versionListing.setTruncated(false);
        versionListing.setEncodingType("encodingType");
        final ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest = new ListNextBatchOfVersionsRequest(
                versionListing);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final VersionListing result = cosClientUnderTest.listNextBatchOfVersions(listNextBatchOfVersionsRequest);

        // Verify the results
    }

    @Test
    public void testListNextBatchOfVersions2_ThrowsCosClientException() throws Exception {
        // Setup
        final VersionListing versionListing = new VersionListing();
        final COSVersionSummary cosVersionSummary = new COSVersionSummary();
        cosVersionSummary.setBucketName("bucketName");
        cosVersionSummary.setKey("key");
        cosVersionSummary.setVersionId("id");
        cosVersionSummary.setIsLatest(false);
        versionListing.setVersionSummaries(Arrays.asList(cosVersionSummary));
        versionListing.setBucketName("bucketName");
        versionListing.setPrefix("prefix");
        versionListing.setKeyMarker("keyMarker");
        versionListing.setVersionIdMarker("marker");
        versionListing.setMaxKeys(0);
        versionListing.setDelimiter("delimiter");
        versionListing.setNextKeyMarker("keyMarker");
        versionListing.setNextVersionIdMarker("marker");
        versionListing.setTruncated(false);
        versionListing.setEncodingType("encodingType");
        final ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest = new ListNextBatchOfVersionsRequest(
                versionListing);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.listNextBatchOfVersions(listNextBatchOfVersionsRequest));
    }

    @Test
    public void testListNextBatchOfVersions2_ThrowsCosServiceException() throws Exception {
        // Setup
        final VersionListing versionListing = new VersionListing();
        final COSVersionSummary cosVersionSummary = new COSVersionSummary();
        cosVersionSummary.setBucketName("bucketName");
        cosVersionSummary.setKey("key");
        cosVersionSummary.setVersionId("id");
        cosVersionSummary.setIsLatest(false);
        versionListing.setVersionSummaries(Arrays.asList(cosVersionSummary));
        versionListing.setBucketName("bucketName");
        versionListing.setPrefix("prefix");
        versionListing.setKeyMarker("keyMarker");
        versionListing.setVersionIdMarker("marker");
        versionListing.setMaxKeys(0);
        versionListing.setDelimiter("delimiter");
        versionListing.setNextKeyMarker("keyMarker");
        versionListing.setNextVersionIdMarker("marker");
        versionListing.setTruncated(false);
        versionListing.setEncodingType("encodingType");
        final ListNextBatchOfVersionsRequest listNextBatchOfVersionsRequest = new ListNextBatchOfVersionsRequest(
                versionListing);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.listNextBatchOfVersions(listNextBatchOfVersionsRequest));
    }

    @Test
    public void testCopyObject1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final CopyObjectResult result = cosClientUnderTest.copyObject("bucketName", "destinationKey", "bucketName",
                "destinationKey");

        // Verify the results
    }

    @Test
    public void testCopyObject1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.copyObject("bucketName", "destinationKey", "bucketName", "destinationKey"));
    }

    @Test
    public void testCopyObject1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.copyObject("bucketName", "destinationKey", "bucketName", "destinationKey"));
    }

    @Test
    public void testCopyObject2() throws Exception {
        // Setup
        final CopyObjectRequest copyObjectRequest = new CopyObjectRequest("sourceAppid",
                new Region("regionName", "displayName"), "bucketName", "destinationKey", "sourceVersionId",
                "bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final CopyObjectResult result = cosClientUnderTest.copyObject(copyObjectRequest);

        // Verify the results
    }

    @Test
    public void testCopyObject2_ThrowsCosClientException() throws Exception {
        // Setup
        final CopyObjectRequest copyObjectRequest = new CopyObjectRequest("sourceAppid",
                new Region("regionName", "displayName"), "bucketName", "destinationKey", "sourceVersionId",
                "bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.copyObject(copyObjectRequest));
    }

    @Test
    public void testCopyObject2_ThrowsCosServiceException() throws Exception {
        // Setup
        final CopyObjectRequest copyObjectRequest = new CopyObjectRequest("sourceAppid",
                new Region("regionName", "displayName"), "bucketName", "destinationKey", "sourceVersionId",
                "bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.copyObject(copyObjectRequest));
    }

    @Test
    public void testCopyPart() throws Exception {
        // Setup
        final CopyPartRequest copyPartRequest = new CopyPartRequest();
        copyPartRequest.setFixedEndpointAddr("endpoint");
        copyPartRequest.setCosCredentials(null);
        copyPartRequest.setUploadId("uploadId");
        copyPartRequest.setPartNumber(0);
        copyPartRequest.setSourceAppid("sourceAppid");
        copyPartRequest.setSourceBucketRegion(new Region("regionName", "displayName"));
        copyPartRequest.setSourceBucketName("sourceBucketName");
        copyPartRequest.setSourceKey("sourceKey");
        copyPartRequest.setSourceVersionId("sourceVersionId");
        copyPartRequest.setDestinationBucketName("destinationBucketName");
        copyPartRequest.setDestinationKey("destinationKey");
        copyPartRequest.setSourceSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        copyPartRequest.setDestinationSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        copyPartRequest.setFirstByte(0L);
        copyPartRequest.setLastByte(0L);
        copyPartRequest.setMatchingETagConstraints(Arrays.asList("value"));
        copyPartRequest.setNonmatchingETagConstraints(Arrays.asList("value"));
        copyPartRequest.setUnmodifiedSinceConstraint(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        copyPartRequest.setModifiedSinceConstraint(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        copyPartRequest.setSourceEndpointBuilder(null);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final CopyPartResult result = cosClientUnderTest.copyPart(copyPartRequest);

        // Verify the results
    }

    @Test
    public void testCopyPart_ThrowsCosClientException() throws Exception {
        // Setup
        final CopyPartRequest copyPartRequest = new CopyPartRequest();
        copyPartRequest.setFixedEndpointAddr("endpoint");
        copyPartRequest.setCosCredentials(null);
        copyPartRequest.setUploadId("uploadId");
        copyPartRequest.setPartNumber(0);
        copyPartRequest.setSourceAppid("sourceAppid");
        copyPartRequest.setSourceBucketRegion(new Region("regionName", "displayName"));
        copyPartRequest.setSourceBucketName("sourceBucketName");
        copyPartRequest.setSourceKey("sourceKey");
        copyPartRequest.setSourceVersionId("sourceVersionId");
        copyPartRequest.setDestinationBucketName("destinationBucketName");
        copyPartRequest.setDestinationKey("destinationKey");
        copyPartRequest.setSourceSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        copyPartRequest.setDestinationSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        copyPartRequest.setFirstByte(0L);
        copyPartRequest.setLastByte(0L);
        copyPartRequest.setMatchingETagConstraints(Arrays.asList("value"));
        copyPartRequest.setNonmatchingETagConstraints(Arrays.asList("value"));
        copyPartRequest.setUnmodifiedSinceConstraint(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        copyPartRequest.setModifiedSinceConstraint(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        copyPartRequest.setSourceEndpointBuilder(null);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.copyPart(copyPartRequest));
    }

    @Test
    public void testCopyPart_ThrowsCosServiceException() throws Exception {
        // Setup
        final CopyPartRequest copyPartRequest = new CopyPartRequest();
        copyPartRequest.setFixedEndpointAddr("endpoint");
        copyPartRequest.setCosCredentials(null);
        copyPartRequest.setUploadId("uploadId");
        copyPartRequest.setPartNumber(0);
        copyPartRequest.setSourceAppid("sourceAppid");
        copyPartRequest.setSourceBucketRegion(new Region("regionName", "displayName"));
        copyPartRequest.setSourceBucketName("sourceBucketName");
        copyPartRequest.setSourceKey("sourceKey");
        copyPartRequest.setSourceVersionId("sourceVersionId");
        copyPartRequest.setDestinationBucketName("destinationBucketName");
        copyPartRequest.setDestinationKey("destinationKey");
        copyPartRequest.setSourceSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        copyPartRequest.setDestinationSSECustomerKey(new SSECustomerKey("base64EncodedKey"));
        copyPartRequest.setFirstByte(0L);
        copyPartRequest.setLastByte(0L);
        copyPartRequest.setMatchingETagConstraints(Arrays.asList("value"));
        copyPartRequest.setNonmatchingETagConstraints(Arrays.asList("value"));
        copyPartRequest.setUnmodifiedSinceConstraint(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        copyPartRequest.setModifiedSinceConstraint(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        copyPartRequest.setSourceEndpointBuilder(null);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.copyPart(copyPartRequest));
    }

    @Test
    public void testSetBucketLifecycleConfiguration1() throws Exception {
        // Setup
        final BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule();
        rule.setId("id");
        rule.setExpirationInDays(0);
        rule.setExpiredObjectDeleteMarker(false);
        rule.setNoncurrentVersionExpirationInDays(0);
        rule.setStatus("status");
        rule.setExpirationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final BucketLifecycleConfiguration.Transition transition = new BucketLifecycleConfiguration.Transition();
        transition.setDays(0);
        transition.setStorageClass("storageClass");
        transition.setDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        rule.setTransitions(Arrays.asList(transition));
        rule.setNoncurrentVersionTransitions(
                Arrays.asList(new BucketLifecycleConfiguration.NoncurrentVersionTransition()));
        final AbortIncompleteMultipartUpload abortIncompleteMultipartUpload = new AbortIncompleteMultipartUpload();
        abortIncompleteMultipartUpload.setDaysAfterInitiation(0);
        rule.setAbortIncompleteMultipartUpload(abortIncompleteMultipartUpload);
        rule.setFilter(new LifecycleFilter(null));
        final BucketLifecycleConfiguration bucketLifecycleConfiguration = new BucketLifecycleConfiguration(
                Arrays.asList(rule));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketLifecycleConfiguration("bucketName", bucketLifecycleConfiguration);

        // Verify the results
    }

    @Test
    public void testSetBucketLifecycleConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule();
        rule.setId("id");
        rule.setExpirationInDays(0);
        rule.setExpiredObjectDeleteMarker(false);
        rule.setNoncurrentVersionExpirationInDays(0);
        rule.setStatus("status");
        rule.setExpirationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final BucketLifecycleConfiguration.Transition transition = new BucketLifecycleConfiguration.Transition();
        transition.setDays(0);
        transition.setStorageClass("storageClass");
        transition.setDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        rule.setTransitions(Arrays.asList(transition));
        rule.setNoncurrentVersionTransitions(
                Arrays.asList(new BucketLifecycleConfiguration.NoncurrentVersionTransition()));
        final AbortIncompleteMultipartUpload abortIncompleteMultipartUpload = new AbortIncompleteMultipartUpload();
        abortIncompleteMultipartUpload.setDaysAfterInitiation(0);
        rule.setAbortIncompleteMultipartUpload(abortIncompleteMultipartUpload);
        rule.setFilter(new LifecycleFilter(null));
        final BucketLifecycleConfiguration bucketLifecycleConfiguration = new BucketLifecycleConfiguration(
                Arrays.asList(rule));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketLifecycleConfiguration("bucketName", bucketLifecycleConfiguration));
    }

    @Test
    public void testSetBucketLifecycleConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule();
        rule.setId("id");
        rule.setExpirationInDays(0);
        rule.setExpiredObjectDeleteMarker(false);
        rule.setNoncurrentVersionExpirationInDays(0);
        rule.setStatus("status");
        rule.setExpirationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final BucketLifecycleConfiguration.Transition transition = new BucketLifecycleConfiguration.Transition();
        transition.setDays(0);
        transition.setStorageClass("storageClass");
        transition.setDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        rule.setTransitions(Arrays.asList(transition));
        rule.setNoncurrentVersionTransitions(
                Arrays.asList(new BucketLifecycleConfiguration.NoncurrentVersionTransition()));
        final AbortIncompleteMultipartUpload abortIncompleteMultipartUpload = new AbortIncompleteMultipartUpload();
        abortIncompleteMultipartUpload.setDaysAfterInitiation(0);
        rule.setAbortIncompleteMultipartUpload(abortIncompleteMultipartUpload);
        rule.setFilter(new LifecycleFilter(null));
        final BucketLifecycleConfiguration bucketLifecycleConfiguration = new BucketLifecycleConfiguration(
                Arrays.asList(rule));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketLifecycleConfiguration("bucketName", bucketLifecycleConfiguration));
    }

    @Test
    public void testSetBucketLifecycleConfiguration2() throws Exception {
        // Setup
        final BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule();
        rule.setId("id");
        rule.setExpirationInDays(0);
        rule.setExpiredObjectDeleteMarker(false);
        rule.setNoncurrentVersionExpirationInDays(0);
        rule.setStatus("status");
        rule.setExpirationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final BucketLifecycleConfiguration.Transition transition = new BucketLifecycleConfiguration.Transition();
        transition.setDays(0);
        transition.setStorageClass("storageClass");
        transition.setDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        rule.setTransitions(Arrays.asList(transition));
        rule.setNoncurrentVersionTransitions(
                Arrays.asList(new BucketLifecycleConfiguration.NoncurrentVersionTransition()));
        final AbortIncompleteMultipartUpload abortIncompleteMultipartUpload = new AbortIncompleteMultipartUpload();
        abortIncompleteMultipartUpload.setDaysAfterInitiation(0);
        rule.setAbortIncompleteMultipartUpload(abortIncompleteMultipartUpload);
        rule.setFilter(new LifecycleFilter(null));
        final SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest = new SetBucketLifecycleConfigurationRequest(
                "bucketName", new BucketLifecycleConfiguration(Arrays.asList(rule)));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketLifecycleConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule();
        rule.setId("id");
        rule.setExpirationInDays(0);
        rule.setExpiredObjectDeleteMarker(false);
        rule.setNoncurrentVersionExpirationInDays(0);
        rule.setStatus("status");
        rule.setExpirationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final BucketLifecycleConfiguration.Transition transition = new BucketLifecycleConfiguration.Transition();
        transition.setDays(0);
        transition.setStorageClass("storageClass");
        transition.setDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        rule.setTransitions(Arrays.asList(transition));
        rule.setNoncurrentVersionTransitions(
                Arrays.asList(new BucketLifecycleConfiguration.NoncurrentVersionTransition()));
        final AbortIncompleteMultipartUpload abortIncompleteMultipartUpload = new AbortIncompleteMultipartUpload();
        abortIncompleteMultipartUpload.setDaysAfterInitiation(0);
        rule.setAbortIncompleteMultipartUpload(abortIncompleteMultipartUpload);
        rule.setFilter(new LifecycleFilter(null));
        final SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest = new SetBucketLifecycleConfigurationRequest(
                "bucketName", new BucketLifecycleConfiguration(Arrays.asList(rule)));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest));
    }

    @Test
    public void testSetBucketLifecycleConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule();
        rule.setId("id");
        rule.setExpirationInDays(0);
        rule.setExpiredObjectDeleteMarker(false);
        rule.setNoncurrentVersionExpirationInDays(0);
        rule.setStatus("status");
        rule.setExpirationDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        final BucketLifecycleConfiguration.Transition transition = new BucketLifecycleConfiguration.Transition();
        transition.setDays(0);
        transition.setStorageClass("storageClass");
        transition.setDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        rule.setTransitions(Arrays.asList(transition));
        rule.setNoncurrentVersionTransitions(
                Arrays.asList(new BucketLifecycleConfiguration.NoncurrentVersionTransition()));
        final AbortIncompleteMultipartUpload abortIncompleteMultipartUpload = new AbortIncompleteMultipartUpload();
        abortIncompleteMultipartUpload.setDaysAfterInitiation(0);
        rule.setAbortIncompleteMultipartUpload(abortIncompleteMultipartUpload);
        rule.setFilter(new LifecycleFilter(null));
        final SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest = new SetBucketLifecycleConfigurationRequest(
                "bucketName", new BucketLifecycleConfiguration(Arrays.asList(rule)));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest));
    }

    @Test
    public void testGetBucketLifecycleConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketLifecycleConfiguration result = cosClientUnderTest.getBucketLifecycleConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketLifecycleConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketLifecycleConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketLifecycleConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketLifecycleConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketLifecycleConfiguration2() throws Exception {
        // Setup
        final GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest = new GetBucketLifecycleConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketLifecycleConfiguration result = cosClientUnderTest.getBucketLifecycleConfiguration(
                getBucketLifecycleConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketLifecycleConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest = new GetBucketLifecycleConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketLifecycleConfiguration(getBucketLifecycleConfigurationRequest));
    }

    @Test
    public void testGetBucketLifecycleConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketLifecycleConfigurationRequest getBucketLifecycleConfigurationRequest = new GetBucketLifecycleConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketLifecycleConfiguration(getBucketLifecycleConfigurationRequest));
    }

    @Test
    public void testDeleteBucketLifecycleConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketLifecycleConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testDeleteBucketLifecycleConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketLifecycleConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketLifecycleConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketLifecycleConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketLifecycleConfiguration2() throws Exception {
        // Setup
        final DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest = new DeleteBucketLifecycleConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketLifecycleConfiguration(deleteBucketLifecycleConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketLifecycleConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest = new DeleteBucketLifecycleConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketLifecycleConfiguration(deleteBucketLifecycleConfigurationRequest));
    }

    @Test
    public void testDeleteBucketLifecycleConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteBucketLifecycleConfigurationRequest deleteBucketLifecycleConfigurationRequest = new DeleteBucketLifecycleConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketLifecycleConfiguration(deleteBucketLifecycleConfigurationRequest));
    }

    @Test
    public void testSetBucketVersioningConfiguration() throws Exception {
        // Setup
        final SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(
                "bucketName", new BucketVersioningConfiguration("status"));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketVersioningConfiguration_ThrowsCosClientException() throws Exception {
        // Setup
        final SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(
                "bucketName", new BucketVersioningConfiguration("status"));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest));
    }

    @Test
    public void testSetBucketVersioningConfiguration_ThrowsCosServiceException() throws Exception {
        // Setup
        final SetBucketVersioningConfigurationRequest setBucketVersioningConfigurationRequest = new SetBucketVersioningConfigurationRequest(
                "bucketName", new BucketVersioningConfiguration("status"));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketVersioningConfiguration(setBucketVersioningConfigurationRequest));
    }

    @Test
    public void testGetBucketVersioningConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketVersioningConfiguration result = cosClientUnderTest.getBucketVersioningConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketVersioningConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketVersioningConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketVersioningConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketVersioningConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketVersioningConfiguration2() throws Exception {
        // Setup
        final GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest = new GetBucketVersioningConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketVersioningConfiguration result = cosClientUnderTest.getBucketVersioningConfiguration(
                getBucketVersioningConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketVersioningConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest = new GetBucketVersioningConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketVersioningConfiguration(getBucketVersioningConfigurationRequest));
    }

    @Test
    public void testGetBucketVersioningConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketVersioningConfigurationRequest getBucketVersioningConfigurationRequest = new GetBucketVersioningConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketVersioningConfiguration(getBucketVersioningConfigurationRequest));
    }

    @Test
    public void testSetObjectAcl1() throws Exception {
        // Setup
        final AccessControlList acl = new AccessControlList();
        acl.setOwner(new Owner("id", "displayName"));
        acl.setExistDefaultAcl(false);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setObjectAcl("bucketName", "destinationKey", acl);

        // Verify the results
    }

    @Test
    public void testSetObjectAcl1_ThrowsCosClientException() throws Exception {
        // Setup
        final AccessControlList acl = new AccessControlList();
        acl.setOwner(new Owner("id", "displayName"));
        acl.setExistDefaultAcl(false);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setObjectAcl("bucketName", "destinationKey", acl));
    }

    @Test
    public void testSetObjectAcl1_ThrowsCosServiceException() throws Exception {
        // Setup
        final AccessControlList acl = new AccessControlList();
        acl.setOwner(new Owner("id", "displayName"));
        acl.setExistDefaultAcl(false);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setObjectAcl("bucketName", "destinationKey", acl));
    }

    @Test
    public void testSetObjectAcl2() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setObjectAcl("bucketName", "destinationKey", CannedAccessControlList.Private);

        // Verify the results
    }

    @Test
    public void testSetObjectAcl2_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setObjectAcl("bucketName", "destinationKey", CannedAccessControlList.Private));
    }

    @Test
    public void testSetObjectAcl2_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setObjectAcl("bucketName", "destinationKey", CannedAccessControlList.Private));
    }

    @Test
    public void testSetObjectAcl3() throws Exception {
        // Setup
        final AccessControlList accessControlList = new AccessControlList();
        accessControlList.setOwner(new Owner("id", "displayName"));
        accessControlList.setExistDefaultAcl(false);
        final SetObjectAclRequest setObjectAclRequest = new SetObjectAclRequest("bucketName", "destinationKey",
                accessControlList);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setObjectAcl(setObjectAclRequest);

        // Verify the results
    }

    @Test
    public void testSetObjectAcl3_ThrowsCosClientException() throws Exception {
        // Setup
        final AccessControlList accessControlList = new AccessControlList();
        accessControlList.setOwner(new Owner("id", "displayName"));
        accessControlList.setExistDefaultAcl(false);
        final SetObjectAclRequest setObjectAclRequest = new SetObjectAclRequest("bucketName", "destinationKey",
                accessControlList);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.setObjectAcl(setObjectAclRequest));
    }

    @Test
    public void testSetObjectAcl3_ThrowsCosServiceException() throws Exception {
        // Setup
        final AccessControlList accessControlList = new AccessControlList();
        accessControlList.setOwner(new Owner("id", "displayName"));
        accessControlList.setExistDefaultAcl(false);
        final SetObjectAclRequest setObjectAclRequest = new SetObjectAclRequest("bucketName", "destinationKey",
                accessControlList);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.setObjectAcl(setObjectAclRequest));
    }

    @Test
    public void testGetObjectAcl1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final AccessControlList result = cosClientUnderTest.getObjectAcl("bucketName", "destinationKey");

        // Verify the results
    }

    @Test
    public void testGetObjectAcl1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getObjectAcl("bucketName", "destinationKey"));
    }

    @Test
    public void testGetObjectAcl1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getObjectAcl("bucketName", "destinationKey"));
    }

    @Test
    public void testGetObjectAcl2() throws Exception {
        // Setup
        final GetObjectAclRequest getObjectAclRequest = new GetObjectAclRequest("bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final AccessControlList result = cosClientUnderTest.getObjectAcl(getObjectAclRequest);

        // Verify the results
    }

    @Test
    public void testGetObjectAcl2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetObjectAclRequest getObjectAclRequest = new GetObjectAclRequest("bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getObjectAcl(getObjectAclRequest));
    }

    @Test
    public void testGetObjectAcl2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetObjectAclRequest getObjectAclRequest = new GetObjectAclRequest("bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getObjectAcl(getObjectAclRequest));
    }

    @Test
    public void testSetBucketAcl1() throws Exception {
        // Setup
        final AccessControlList acl = new AccessControlList();
        acl.setOwner(new Owner("id", "displayName"));
        acl.setExistDefaultAcl(false);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketAcl("bucketName", acl);

        // Verify the results
    }

    @Test
    public void testSetBucketAcl1_ThrowsCosClientException() throws Exception {
        // Setup
        final AccessControlList acl = new AccessControlList();
        acl.setOwner(new Owner("id", "displayName"));
        acl.setExistDefaultAcl(false);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.setBucketAcl("bucketName", acl));
    }

    @Test
    public void testSetBucketAcl1_ThrowsCosServiceException() throws Exception {
        // Setup
        final AccessControlList acl = new AccessControlList();
        acl.setOwner(new Owner("id", "displayName"));
        acl.setExistDefaultAcl(false);

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.setBucketAcl("bucketName", acl));
    }

    @Test
    public void testSetBucketAcl2() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketAcl("bucketName", CannedAccessControlList.Private);

        // Verify the results
    }

    @Test
    public void testSetBucketAcl2_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketAcl("bucketName", CannedAccessControlList.Private));
    }

    @Test
    public void testSetBucketAcl2_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketAcl("bucketName", CannedAccessControlList.Private));
    }

    @Test
    public void testSetBucketAcl3() throws Exception {
        // Setup
        final AccessControlList accessControlList = new AccessControlList();
        accessControlList.setOwner(new Owner("id", "displayName"));
        accessControlList.setExistDefaultAcl(false);
        final SetBucketAclRequest setBucketAclRequest = new SetBucketAclRequest("bucketName", accessControlList);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketAcl(setBucketAclRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketAcl3_ThrowsCosClientException() throws Exception {
        // Setup
        final AccessControlList accessControlList = new AccessControlList();
        accessControlList.setOwner(new Owner("id", "displayName"));
        accessControlList.setExistDefaultAcl(false);
        final SetBucketAclRequest setBucketAclRequest = new SetBucketAclRequest("bucketName", accessControlList);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.setBucketAcl(setBucketAclRequest));
    }

    @Test
    public void testSetBucketAcl3_ThrowsCosServiceException() throws Exception {
        // Setup
        final AccessControlList accessControlList = new AccessControlList();
        accessControlList.setOwner(new Owner("id", "displayName"));
        accessControlList.setExistDefaultAcl(false);
        final SetBucketAclRequest setBucketAclRequest = new SetBucketAclRequest("bucketName", accessControlList);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.setBucketAcl(setBucketAclRequest));
    }

    @Test
    public void testGetBucketAcl1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final AccessControlList result = cosClientUnderTest.getBucketAcl("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketAcl1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketAcl("bucketName"));
    }

    @Test
    public void testGetBucketAcl1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketAcl("bucketName"));
    }

    @Test
    public void testGetBucketAcl2() throws Exception {
        // Setup
        final GetBucketAclRequest getBucketAclRequest = new GetBucketAclRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final AccessControlList result = cosClientUnderTest.getBucketAcl(getBucketAclRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketAcl2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketAclRequest getBucketAclRequest = new GetBucketAclRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketAcl(getBucketAclRequest));
    }

    @Test
    public void testGetBucketAcl2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketAclRequest getBucketAclRequest = new GetBucketAclRequest("bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketAcl(getBucketAclRequest));
    }

    @Test
    public void testGetBucketCrossOriginConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketCrossOriginConfiguration result = cosClientUnderTest.getBucketCrossOriginConfiguration(
                "bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketCrossOriginConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketCrossOriginConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketCrossOriginConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketCrossOriginConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketCrossOriginConfiguration2() throws Exception {
        // Setup
        final GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest = new GetBucketCrossOriginConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketCrossOriginConfiguration result = cosClientUnderTest.getBucketCrossOriginConfiguration(
                getBucketCrossOriginConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketCrossOriginConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest = new GetBucketCrossOriginConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketCrossOriginConfiguration(getBucketCrossOriginConfigurationRequest));
    }

    @Test
    public void testGetBucketCrossOriginConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketCrossOriginConfigurationRequest getBucketCrossOriginConfigurationRequest = new GetBucketCrossOriginConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketCrossOriginConfiguration(getBucketCrossOriginConfigurationRequest));
    }

    @Test
    public void testSetBucketCrossOriginConfiguration1() throws Exception {
        // Setup
        final CORSRule corsRule = new CORSRule();
        corsRule.setId("id");
        corsRule.setAllowedMethods(Arrays.asList(CORSRule.AllowedMethods.GET));
        corsRule.setAllowedOrigins("allowedOrigins");
        corsRule.setMaxAgeSeconds(0);
        corsRule.setExposedHeaders("exposedHeaders");
        corsRule.setAllowedHeaders("allowedHeaders");
        final BucketCrossOriginConfiguration bucketCrossOriginConfiguration = new BucketCrossOriginConfiguration(
                Arrays.asList(corsRule));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketCrossOriginConfiguration("bucketName", bucketCrossOriginConfiguration);

        // Verify the results
    }

    @Test
    public void testSetBucketCrossOriginConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        final CORSRule corsRule = new CORSRule();
        corsRule.setId("id");
        corsRule.setAllowedMethods(Arrays.asList(CORSRule.AllowedMethods.GET));
        corsRule.setAllowedOrigins("allowedOrigins");
        corsRule.setMaxAgeSeconds(0);
        corsRule.setExposedHeaders("exposedHeaders");
        corsRule.setAllowedHeaders("allowedHeaders");
        final BucketCrossOriginConfiguration bucketCrossOriginConfiguration = new BucketCrossOriginConfiguration(
                Arrays.asList(corsRule));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.setBucketCrossOriginConfiguration("bucketName",
                bucketCrossOriginConfiguration));
    }

    @Test
    public void testSetBucketCrossOriginConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        final CORSRule corsRule = new CORSRule();
        corsRule.setId("id");
        corsRule.setAllowedMethods(Arrays.asList(CORSRule.AllowedMethods.GET));
        corsRule.setAllowedOrigins("allowedOrigins");
        corsRule.setMaxAgeSeconds(0);
        corsRule.setExposedHeaders("exposedHeaders");
        corsRule.setAllowedHeaders("allowedHeaders");
        final BucketCrossOriginConfiguration bucketCrossOriginConfiguration = new BucketCrossOriginConfiguration(
                Arrays.asList(corsRule));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.setBucketCrossOriginConfiguration("bucketName",
                bucketCrossOriginConfiguration));
    }

    @Test
    public void testSetBucketCrossOriginConfiguration2() throws Exception {
        // Setup
        final CORSRule corsRule = new CORSRule();
        corsRule.setId("id");
        corsRule.setAllowedMethods(Arrays.asList(CORSRule.AllowedMethods.GET));
        corsRule.setAllowedOrigins("allowedOrigins");
        corsRule.setMaxAgeSeconds(0);
        corsRule.setExposedHeaders("exposedHeaders");
        corsRule.setAllowedHeaders("allowedHeaders");
        final SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest = new SetBucketCrossOriginConfigurationRequest(
                "bucketName", new BucketCrossOriginConfiguration(Arrays.asList(corsRule)));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketCrossOriginConfiguration(setBucketCrossOriginConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketCrossOriginConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final CORSRule corsRule = new CORSRule();
        corsRule.setId("id");
        corsRule.setAllowedMethods(Arrays.asList(CORSRule.AllowedMethods.GET));
        corsRule.setAllowedOrigins("allowedOrigins");
        corsRule.setMaxAgeSeconds(0);
        corsRule.setExposedHeaders("exposedHeaders");
        corsRule.setAllowedHeaders("allowedHeaders");
        final SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest = new SetBucketCrossOriginConfigurationRequest(
                "bucketName", new BucketCrossOriginConfiguration(Arrays.asList(corsRule)));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketCrossOriginConfiguration(setBucketCrossOriginConfigurationRequest));
    }

    @Test
    public void testSetBucketCrossOriginConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final CORSRule corsRule = new CORSRule();
        corsRule.setId("id");
        corsRule.setAllowedMethods(Arrays.asList(CORSRule.AllowedMethods.GET));
        corsRule.setAllowedOrigins("allowedOrigins");
        corsRule.setMaxAgeSeconds(0);
        corsRule.setExposedHeaders("exposedHeaders");
        corsRule.setAllowedHeaders("allowedHeaders");
        final SetBucketCrossOriginConfigurationRequest setBucketCrossOriginConfigurationRequest = new SetBucketCrossOriginConfigurationRequest(
                "bucketName", new BucketCrossOriginConfiguration(Arrays.asList(corsRule)));
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketCrossOriginConfiguration(setBucketCrossOriginConfigurationRequest));
    }

    @Test
    public void testDeleteBucketCrossOriginConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketCrossOriginConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testDeleteBucketCrossOriginConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketCrossOriginConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketCrossOriginConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketCrossOriginConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketCrossOriginConfiguration2() throws Exception {
        // Setup
        final DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest = new DeleteBucketCrossOriginConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketCrossOriginConfiguration(deleteBucketCrossOriginConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketCrossOriginConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest = new DeleteBucketCrossOriginConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteBucketCrossOriginConfiguration(
                deleteBucketCrossOriginConfigurationRequest));
    }

    @Test
    public void testDeleteBucketCrossOriginConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteBucketCrossOriginConfigurationRequest deleteBucketCrossOriginConfigurationRequest = new DeleteBucketCrossOriginConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteBucketCrossOriginConfiguration(
                deleteBucketCrossOriginConfigurationRequest));
    }

    @Test
    public void testSetBucketReplicationConfiguration1() throws Exception {
        // Setup
        final BucketReplicationConfiguration configuration = new BucketReplicationConfiguration();
        configuration.setRoleName("roleName");
        final ReplicationRule replicationRule = new ReplicationRule();
        replicationRule.setID("id");
        replicationRule.setPrefix("prefix");
        replicationRule.setStatus("status");
        final ReplicationDestinationConfig destinationConfig = new ReplicationDestinationConfig();
        destinationConfig.setBucketQCS("bucketQCS");
        destinationConfig.setStorageClass("storageClass");
        replicationRule.setDestinationConfig(destinationConfig);
        configuration.setRules(Arrays.asList(replicationRule));

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketReplicationConfiguration("bucketName", configuration);

        // Verify the results
    }

    @Test
    public void testSetBucketReplicationConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketReplicationConfiguration configuration = new BucketReplicationConfiguration();
        configuration.setRoleName("roleName");
        final ReplicationRule replicationRule = new ReplicationRule();
        replicationRule.setID("id");
        replicationRule.setPrefix("prefix");
        replicationRule.setStatus("status");
        final ReplicationDestinationConfig destinationConfig = new ReplicationDestinationConfig();
        destinationConfig.setBucketQCS("bucketQCS");
        destinationConfig.setStorageClass("storageClass");
        replicationRule.setDestinationConfig(destinationConfig);
        configuration.setRules(Arrays.asList(replicationRule));

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketReplicationConfiguration("bucketName", configuration));
    }

    @Test
    public void testSetBucketReplicationConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketReplicationConfiguration configuration = new BucketReplicationConfiguration();
        configuration.setRoleName("roleName");
        final ReplicationRule replicationRule = new ReplicationRule();
        replicationRule.setID("id");
        replicationRule.setPrefix("prefix");
        replicationRule.setStatus("status");
        final ReplicationDestinationConfig destinationConfig = new ReplicationDestinationConfig();
        destinationConfig.setBucketQCS("bucketQCS");
        destinationConfig.setStorageClass("storageClass");
        replicationRule.setDestinationConfig(destinationConfig);
        configuration.setRules(Arrays.asList(replicationRule));

        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketReplicationConfiguration("bucketName", configuration));
    }

    @Test
    public void testSetBucketReplicationConfiguration2() throws Exception {
        // Setup
        final BucketReplicationConfiguration bucketReplicationConfiguration = new BucketReplicationConfiguration();
        bucketReplicationConfiguration.setRoleName("roleName");
        final ReplicationRule replicationRule = new ReplicationRule();
        replicationRule.setID("id");
        replicationRule.setPrefix("prefix");
        replicationRule.setStatus("status");
        final ReplicationDestinationConfig destinationConfig = new ReplicationDestinationConfig();
        destinationConfig.setBucketQCS("bucketQCS");
        destinationConfig.setStorageClass("storageClass");
        replicationRule.setDestinationConfig(destinationConfig);
        bucketReplicationConfiguration.setRules(Arrays.asList(replicationRule));
        final SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest = new SetBucketReplicationConfigurationRequest(
                "bucketName", bucketReplicationConfiguration);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketReplicationConfiguration(setBucketReplicationConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketReplicationConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketReplicationConfiguration bucketReplicationConfiguration = new BucketReplicationConfiguration();
        bucketReplicationConfiguration.setRoleName("roleName");
        final ReplicationRule replicationRule = new ReplicationRule();
        replicationRule.setID("id");
        replicationRule.setPrefix("prefix");
        replicationRule.setStatus("status");
        final ReplicationDestinationConfig destinationConfig = new ReplicationDestinationConfig();
        destinationConfig.setBucketQCS("bucketQCS");
        destinationConfig.setStorageClass("storageClass");
        replicationRule.setDestinationConfig(destinationConfig);
        bucketReplicationConfiguration.setRules(Arrays.asList(replicationRule));
        final SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest = new SetBucketReplicationConfigurationRequest(
                "bucketName", bucketReplicationConfiguration);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketReplicationConfiguration(setBucketReplicationConfigurationRequest));
    }

    @Test
    public void testSetBucketReplicationConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketReplicationConfiguration bucketReplicationConfiguration = new BucketReplicationConfiguration();
        bucketReplicationConfiguration.setRoleName("roleName");
        final ReplicationRule replicationRule = new ReplicationRule();
        replicationRule.setID("id");
        replicationRule.setPrefix("prefix");
        replicationRule.setStatus("status");
        final ReplicationDestinationConfig destinationConfig = new ReplicationDestinationConfig();
        destinationConfig.setBucketQCS("bucketQCS");
        destinationConfig.setStorageClass("storageClass");
        replicationRule.setDestinationConfig(destinationConfig);
        bucketReplicationConfiguration.setRules(Arrays.asList(replicationRule));
        final SetBucketReplicationConfigurationRequest setBucketReplicationConfigurationRequest = new SetBucketReplicationConfigurationRequest(
                "bucketName", bucketReplicationConfiguration);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketReplicationConfiguration(setBucketReplicationConfigurationRequest));
    }

    @Test
    public void testGetBucketReplicationConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketReplicationConfiguration result = cosClientUnderTest.getBucketReplicationConfiguration(
                "bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketReplicationConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketReplicationConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketReplicationConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketReplicationConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketReplicationConfiguration2() throws Exception {
        // Setup
        final GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest = new GetBucketReplicationConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketReplicationConfiguration result = cosClientUnderTest.getBucketReplicationConfiguration(
                getBucketReplicationConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketReplicationConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest = new GetBucketReplicationConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketReplicationConfiguration(getBucketReplicationConfigurationRequest));
    }

    @Test
    public void testGetBucketReplicationConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketReplicationConfigurationRequest getBucketReplicationConfigurationRequest = new GetBucketReplicationConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketReplicationConfiguration(getBucketReplicationConfigurationRequest));
    }

    @Test
    public void testDeleteBucketReplicationConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketReplicationConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testDeleteBucketReplicationConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketReplicationConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketReplicationConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketReplicationConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketReplicationConfiguration2() throws Exception {
        // Setup
        final DeleteBucketReplicationConfigurationRequest deleteBucketReplicationConfigurationRequest = new DeleteBucketReplicationConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketReplicationConfiguration(deleteBucketReplicationConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketReplicationConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteBucketReplicationConfigurationRequest deleteBucketReplicationConfigurationRequest = new DeleteBucketReplicationConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteBucketReplicationConfiguration(
                deleteBucketReplicationConfigurationRequest));
    }

    @Test
    public void testDeleteBucketReplicationConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteBucketReplicationConfigurationRequest deleteBucketReplicationConfigurationRequest = new DeleteBucketReplicationConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteBucketReplicationConfiguration(
                deleteBucketReplicationConfigurationRequest));
    }

    @Test
    public void testGeneratePresignedUrl1() throws Exception {
        // Setup
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.generatePresignedUrl("bucketName", "destinationKey",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testGeneratePresignedUrl1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.generatePresignedUrl("bucketName", "destinationKey",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testGeneratePresignedUrl2() throws Exception {
        // Setup
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.generatePresignedUrl("bucketName", "destinationKey",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), HttpMethodName.GET);

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testGeneratePresignedUrl2_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.generatePresignedUrl("bucketName", "destinationKey",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), HttpMethodName.GET));
    }

    @Test
    public void testGeneratePresignedUrl3() throws Exception {
        // Setup
        final Map<String, String> headers = new HashMap<>();
        final Map<String, String> params = new HashMap<>();
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.generatePresignedUrl("bucketName", "destinationKey",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), HttpMethodName.GET, headers, params);

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testGeneratePresignedUrl3_ThrowsCosClientException() throws Exception {
        // Setup
        final Map<String, String> headers = new HashMap<>();
        final Map<String, String> params = new HashMap<>();
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.generatePresignedUrl("bucketName", "destinationKey",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), HttpMethodName.GET, headers,
                        params));
    }

    @Test
    public void testGeneratePresignedUrl4() throws Exception {
        // Setup
        final Map<String, String> headers = new HashMap<>();
        final Map<String, String> params = new HashMap<>();
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.generatePresignedUrl("bucketName", "destinationKey",
                new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), HttpMethodName.GET, headers, params, false,
                false);

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testGeneratePresignedUrl4_ThrowsCosClientException() throws Exception {
        // Setup
        final Map<String, String> headers = new HashMap<>();
        final Map<String, String> params = new HashMap<>();
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.generatePresignedUrl("bucketName", "destinationKey",
                        new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime(), HttpMethodName.GET, headers, params,
                        false, false));
    }

    @Test
    public void testGeneratePresignedUrl5() throws Exception {
        // Setup
        final GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("bucketName", "destinationKey",
                HttpMethodName.GET);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.generatePresignedUrl(req);

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testGeneratePresignedUrl5_ThrowsCosClientException() throws Exception {
        // Setup
        final GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("bucketName", "destinationKey",
                HttpMethodName.GET);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.generatePresignedUrl(req));
    }

    @Test
    public void testGeneratePresignedUrl6() throws Exception {
        // Setup
        final GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("bucketName", "destinationKey",
                HttpMethodName.GET);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.generatePresignedUrl(req, false);

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testGeneratePresignedUrl6_ThrowsCosClientException() throws Exception {
        // Setup
        final GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("bucketName", "destinationKey",
                HttpMethodName.GET);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getSignExpired()).thenReturn(0L);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.generatePresignedUrl(req, false));
    }

    @Test
    public void testRestoreObject1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.restoreObject("bucketName", "destinationKey", 0);

        // Verify the results
    }

    @Test
    public void testRestoreObject1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.restoreObject("bucketName", "destinationKey", 0));
    }

    @Test
    public void testRestoreObject1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.restoreObject("bucketName", "destinationKey", 0));
    }

    @Test
    public void testRestoreObject2() throws Exception {
        // Setup
        final RestoreObjectRequest restoreObjectRequest = new RestoreObjectRequest("bucketName", "destinationKey", 0);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.restoreObject(restoreObjectRequest);

        // Verify the results
    }

    @Test
    public void testRestoreObject2_ThrowsCosClientException() throws Exception {
        // Setup
        final RestoreObjectRequest restoreObjectRequest = new RestoreObjectRequest("bucketName", "destinationKey", 0);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.restoreObject(restoreObjectRequest));
    }

    @Test
    public void testRestoreObject2_ThrowsCosServiceException() throws Exception {
        // Setup
        final RestoreObjectRequest restoreObjectRequest = new RestoreObjectRequest("bucketName", "destinationKey", 0);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.restoreObject(restoreObjectRequest));
    }

    @Test
    public void testUpdateObjectMetaData() throws Exception {
        // Setup
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.updateObjectMetaData("bucketName", "destinationKey", objectMetadata);

        // Verify the results
    }

    @Test
    public void testUpdateObjectMetaData_ThrowsCosClientException() throws Exception {
        // Setup
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.updateObjectMetaData("bucketName", "destinationKey", objectMetadata));
    }

    @Test
    public void testUpdateObjectMetaData_ThrowsCosServiceException() throws Exception {
        // Setup
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.updateObjectMetaData("bucketName", "destinationKey", objectMetadata));
    }

    @Test
    public void testSetBucketPolicy1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketPolicy("bucketName", "policyText");

        // Verify the results
    }

    @Test
    public void testSetBucketPolicy1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.setBucketPolicy("bucketName", "policyText"));
    }

    @Test
    public void testSetBucketPolicy1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.setBucketPolicy("bucketName", "policyText"));
    }

    @Test
    public void testGetBucketLoggingConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketLoggingConfiguration result = cosClientUnderTest.getBucketLoggingConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketLoggingConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketLoggingConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketLoggingConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketLoggingConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketLoggingConfiguration2() throws Exception {
        // Setup
        final GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest = new GetBucketLoggingConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketLoggingConfiguration result = cosClientUnderTest.getBucketLoggingConfiguration(
                getBucketLoggingConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketLoggingConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest = new GetBucketLoggingConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketLoggingConfiguration(getBucketLoggingConfigurationRequest));
    }

    @Test
    public void testGetBucketLoggingConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketLoggingConfigurationRequest getBucketLoggingConfigurationRequest = new GetBucketLoggingConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketLoggingConfiguration(getBucketLoggingConfigurationRequest));
    }

    @Test
    public void testSetBucketLoggingConfiguration() throws Exception {
        // Setup
        final SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest = new SetBucketLoggingConfigurationRequest(
                "bucketName", new BucketLoggingConfiguration("destinationBucketName", "logFilePrefix"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketLoggingConfiguration(setBucketLoggingConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketLoggingConfiguration_ThrowsCosClientException() throws Exception {
        // Setup
        final SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest = new SetBucketLoggingConfigurationRequest(
                "bucketName", new BucketLoggingConfiguration("destinationBucketName", "logFilePrefix"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketLoggingConfiguration(setBucketLoggingConfigurationRequest));
    }

    @Test
    public void testSetBucketLoggingConfiguration_ThrowsCosServiceException() throws Exception {
        // Setup
        final SetBucketLoggingConfigurationRequest setBucketLoggingConfigurationRequest = new SetBucketLoggingConfigurationRequest(
                "bucketName", new BucketLoggingConfiguration("destinationBucketName", "logFilePrefix"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketLoggingConfiguration(setBucketLoggingConfigurationRequest));
    }

    @Test
    public void testSetBucketPolicy2() throws Exception {
        // Setup
        final SetBucketPolicyRequest setBucketPolicyRequest = new SetBucketPolicyRequest("bucketName", "policyText");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketPolicy(setBucketPolicyRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketPolicy2_ThrowsCosClientException() throws Exception {
        // Setup
        final SetBucketPolicyRequest setBucketPolicyRequest = new SetBucketPolicyRequest("bucketName", "policyText");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.setBucketPolicy(setBucketPolicyRequest));
    }

    @Test
    public void testSetBucketPolicy2_ThrowsCosServiceException() throws Exception {
        // Setup
        final SetBucketPolicyRequest setBucketPolicyRequest = new SetBucketPolicyRequest("bucketName", "policyText");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.setBucketPolicy(setBucketPolicyRequest));
    }

    @Test
    public void testGetBucketPolicy1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketPolicy result = cosClientUnderTest.getBucketPolicy("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketPolicy1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketPolicy("bucketName"));
    }

    @Test
    public void testGetBucketPolicy1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketPolicy("bucketName"));
    }

    @Test
    public void testGetBucketPolicy2() throws Exception {
        // Setup
        final GetBucketPolicyRequest getBucketPolicyRequest = new GetBucketPolicyRequest("bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketPolicy result = cosClientUnderTest.getBucketPolicy(getBucketPolicyRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketPolicy2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketPolicyRequest getBucketPolicyRequest = new GetBucketPolicyRequest("bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketPolicy(getBucketPolicyRequest));
    }

    @Test
    public void testGetBucketPolicy2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketPolicyRequest getBucketPolicyRequest = new GetBucketPolicyRequest("bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketPolicy(getBucketPolicyRequest));
    }

    @Test
    public void testDeleteBucketPolicy1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketPolicy("bucketName");

        // Verify the results
    }

    @Test
    public void testDeleteBucketPolicy1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteBucketPolicy("bucketName"));
    }

    @Test
    public void testDeleteBucketPolicy1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteBucketPolicy("bucketName"));
    }

    @Test
    public void testDeleteBucketPolicy2() throws Exception {
        // Setup
        final DeleteBucketPolicyRequest deleteBucketPolicyRequest = new DeleteBucketPolicyRequest("bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketPolicy(deleteBucketPolicyRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketPolicy2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteBucketPolicyRequest deleteBucketPolicyRequest = new DeleteBucketPolicyRequest("bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteBucketPolicy(deleteBucketPolicyRequest));
    }

    @Test
    public void testDeleteBucketPolicy2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteBucketPolicyRequest deleteBucketPolicyRequest = new DeleteBucketPolicyRequest("bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteBucketPolicy(deleteBucketPolicyRequest));
    }

    @Test
    public void testGetBucketWebsiteConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketWebsiteConfiguration result = cosClientUnderTest.getBucketWebsiteConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketWebsiteConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketWebsiteConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketWebsiteConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketWebsiteConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketWebsiteConfiguration2() throws Exception {
        // Setup
        final GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest = new GetBucketWebsiteConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketWebsiteConfiguration result = cosClientUnderTest.getBucketWebsiteConfiguration(
                getBucketWebsiteConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketWebsiteConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest = new GetBucketWebsiteConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketWebsiteConfiguration(getBucketWebsiteConfigurationRequest));
    }

    @Test
    public void testGetBucketWebsiteConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketWebsiteConfigurationRequest getBucketWebsiteConfigurationRequest = new GetBucketWebsiteConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketWebsiteConfiguration(getBucketWebsiteConfigurationRequest));
    }

    @Test
    public void testSetBucketWebsiteConfiguration1() throws Exception {
        // Setup
        final BucketWebsiteConfiguration configuration = new BucketWebsiteConfiguration("indexDocumentSuffix",
                "errorDocument");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketWebsiteConfiguration("bucketName", configuration);

        // Verify the results
    }

    @Test
    public void testSetBucketWebsiteConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketWebsiteConfiguration configuration = new BucketWebsiteConfiguration("indexDocumentSuffix",
                "errorDocument");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketWebsiteConfiguration("bucketName", configuration));
    }

    @Test
    public void testSetBucketWebsiteConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketWebsiteConfiguration configuration = new BucketWebsiteConfiguration("indexDocumentSuffix",
                "errorDocument");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketWebsiteConfiguration("bucketName", configuration));
    }

    @Test
    public void testSetBucketWebsiteConfiguration2() throws Exception {
        // Setup
        final SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest = new SetBucketWebsiteConfigurationRequest(
                "bucketName", new BucketWebsiteConfiguration("indexDocumentSuffix", "errorDocument"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketWebsiteConfiguration(setBucketWebsiteConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketWebsiteConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest = new SetBucketWebsiteConfigurationRequest(
                "bucketName", new BucketWebsiteConfiguration("indexDocumentSuffix", "errorDocument"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketWebsiteConfiguration(setBucketWebsiteConfigurationRequest));
    }

    @Test
    public void testSetBucketWebsiteConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final SetBucketWebsiteConfigurationRequest setBucketWebsiteConfigurationRequest = new SetBucketWebsiteConfigurationRequest(
                "bucketName", new BucketWebsiteConfiguration("indexDocumentSuffix", "errorDocument"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketWebsiteConfiguration(setBucketWebsiteConfigurationRequest));
    }

    @Test
    public void testDeleteBucketWebsiteConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketWebsiteConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testDeleteBucketWebsiteConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteBucketWebsiteConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketWebsiteConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketWebsiteConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketWebsiteConfiguration2() throws Exception {
        // Setup
        final DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest = new DeleteBucketWebsiteConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketWebsiteConfiguration(deleteBucketWebsiteConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketWebsiteConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest = new DeleteBucketWebsiteConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketWebsiteConfiguration(deleteBucketWebsiteConfigurationRequest));
    }

    @Test
    public void testDeleteBucketWebsiteConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteBucketWebsiteConfigurationRequest deleteBucketWebsiteConfigurationRequest = new DeleteBucketWebsiteConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketWebsiteConfiguration(deleteBucketWebsiteConfigurationRequest));
    }

    @Test
    public void testDeleteBucketDomainConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketDomainConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testDeleteBucketDomainConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.deleteBucketDomainConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketDomainConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.deleteBucketDomainConfiguration("bucketName"));
    }

    @Test
    public void testDeleteBucketDomainConfiguration2() throws Exception {
        // Setup
        final DeleteBucketDomainConfigurationRequest deleteBucketDomainConfigurationRequest = new DeleteBucketDomainConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketDomainConfiguration(deleteBucketDomainConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketDomainConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteBucketDomainConfigurationRequest deleteBucketDomainConfigurationRequest = new DeleteBucketDomainConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketDomainConfiguration(deleteBucketDomainConfigurationRequest));
    }

    @Test
    public void testDeleteBucketDomainConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteBucketDomainConfigurationRequest deleteBucketDomainConfigurationRequest = new DeleteBucketDomainConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketDomainConfiguration(deleteBucketDomainConfigurationRequest));
    }

    @Test
    public void testSetBucketDomainConfiguration1() throws Exception {
        // Setup
        final BucketDomainConfiguration configuration = new BucketDomainConfiguration();
        final DomainRule domainRule = new DomainRule();
        domainRule.setStatus("status");
        domainRule.setType("type");
        domainRule.setName("name");
        domainRule.setForcedReplacement("forcedReplacement");
        configuration.setDomainRules(Arrays.asList(domainRule));
        configuration.setDomainTxtVerification("domainTxtVerification");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketDomainConfiguration("bucketName", configuration);

        // Verify the results
    }

    @Test
    public void testSetBucketDomainConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketDomainConfiguration configuration = new BucketDomainConfiguration();
        final DomainRule domainRule = new DomainRule();
        domainRule.setStatus("status");
        domainRule.setType("type");
        domainRule.setName("name");
        domainRule.setForcedReplacement("forcedReplacement");
        configuration.setDomainRules(Arrays.asList(domainRule));
        configuration.setDomainTxtVerification("domainTxtVerification");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketDomainConfiguration("bucketName", configuration));
    }

    @Test
    public void testSetBucketDomainConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketDomainConfiguration configuration = new BucketDomainConfiguration();
        final DomainRule domainRule = new DomainRule();
        domainRule.setStatus("status");
        domainRule.setType("type");
        domainRule.setName("name");
        domainRule.setForcedReplacement("forcedReplacement");
        configuration.setDomainRules(Arrays.asList(domainRule));
        configuration.setDomainTxtVerification("domainTxtVerification");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketDomainConfiguration("bucketName", configuration));
    }

    @Test
    public void testSetBucketDomainConfiguration2() throws Exception {
        // Setup
        final BucketDomainConfiguration bucketDomainConfiguration = new BucketDomainConfiguration();
        final DomainRule domainRule = new DomainRule();
        domainRule.setStatus("status");
        domainRule.setType("type");
        domainRule.setName("name");
        domainRule.setForcedReplacement("forcedReplacement");
        bucketDomainConfiguration.setDomainRules(Arrays.asList(domainRule));
        bucketDomainConfiguration.setDomainTxtVerification("domainTxtVerification");
        final SetBucketDomainConfigurationRequest setBucketDomainConfigurationRequest = new SetBucketDomainConfigurationRequest(
                "bucketName", bucketDomainConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketDomainConfiguration(setBucketDomainConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketDomainConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketDomainConfiguration bucketDomainConfiguration = new BucketDomainConfiguration();
        final DomainRule domainRule = new DomainRule();
        domainRule.setStatus("status");
        domainRule.setType("type");
        domainRule.setName("name");
        domainRule.setForcedReplacement("forcedReplacement");
        bucketDomainConfiguration.setDomainRules(Arrays.asList(domainRule));
        bucketDomainConfiguration.setDomainTxtVerification("domainTxtVerification");
        final SetBucketDomainConfigurationRequest setBucketDomainConfigurationRequest = new SetBucketDomainConfigurationRequest(
                "bucketName", bucketDomainConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketDomainConfiguration(setBucketDomainConfigurationRequest));
    }

    @Test
    public void testSetBucketDomainConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketDomainConfiguration bucketDomainConfiguration = new BucketDomainConfiguration();
        final DomainRule domainRule = new DomainRule();
        domainRule.setStatus("status");
        domainRule.setType("type");
        domainRule.setName("name");
        domainRule.setForcedReplacement("forcedReplacement");
        bucketDomainConfiguration.setDomainRules(Arrays.asList(domainRule));
        bucketDomainConfiguration.setDomainTxtVerification("domainTxtVerification");
        final SetBucketDomainConfigurationRequest setBucketDomainConfigurationRequest = new SetBucketDomainConfigurationRequest(
                "bucketName", bucketDomainConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketDomainConfiguration(setBucketDomainConfigurationRequest));
    }

    @Test
    public void testGetBucketDomainConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketDomainConfiguration result = cosClientUnderTest.getBucketDomainConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketDomainConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketDomainConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketDomainConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketDomainConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketDomainConfiguration2() throws Exception {
        // Setup
        final GetBucketDomainConfigurationRequest getBucketDomainConfigurationRequest = new GetBucketDomainConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketDomainConfiguration result = cosClientUnderTest.getBucketDomainConfiguration(
                getBucketDomainConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketDomainConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketDomainConfigurationRequest getBucketDomainConfigurationRequest = new GetBucketDomainConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketDomainConfiguration(getBucketDomainConfigurationRequest));
    }

    @Test
    public void testGetBucketDomainConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketDomainConfigurationRequest getBucketDomainConfigurationRequest = new GetBucketDomainConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketDomainConfiguration(getBucketDomainConfigurationRequest));
    }

    @Test
    public void testSetBucketDomainCertificate1() throws Exception {
        // Setup
        final BucketPutDomainCertificate domainCertificate = new BucketPutDomainCertificate();
        final BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();
        bucketDomainCertificateInfo.setCert("cert");
        bucketDomainCertificateInfo.setPrivateKey("privateKey");
        bucketDomainCertificateInfo.setCertType("certType");
        domainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        domainCertificate.setDomainList(Arrays.asList("value"));

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketDomainCertificate("bucketName", domainCertificate);

        // Verify the results
    }

    @Test
    public void testSetBucketDomainCertificate1_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketPutDomainCertificate domainCertificate = new BucketPutDomainCertificate();
        final BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();
        bucketDomainCertificateInfo.setCert("cert");
        bucketDomainCertificateInfo.setPrivateKey("privateKey");
        bucketDomainCertificateInfo.setCertType("certType");
        domainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        domainCertificate.setDomainList(Arrays.asList("value"));

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketDomainCertificate("bucketName", domainCertificate));
    }

    @Test
    public void testSetBucketDomainCertificate1_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketPutDomainCertificate domainCertificate = new BucketPutDomainCertificate();
        final BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();
        bucketDomainCertificateInfo.setCert("cert");
        bucketDomainCertificateInfo.setPrivateKey("privateKey");
        bucketDomainCertificateInfo.setCertType("certType");
        domainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        domainCertificate.setDomainList(Arrays.asList("value"));

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketDomainCertificate("bucketName", domainCertificate));
    }

    @Test
    public void testSetBucketDomainCertificate2() throws Exception {
        // Setup
        final BucketPutDomainCertificate bucketPutDomainCertificate = new BucketPutDomainCertificate();
        final BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();
        bucketDomainCertificateInfo.setCert("cert");
        bucketDomainCertificateInfo.setPrivateKey("privateKey");
        bucketDomainCertificateInfo.setCertType("certType");
        bucketPutDomainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        bucketPutDomainCertificate.setDomainList(Arrays.asList("value"));
        final SetBucketDomainCertificateRequest setBucketDomainCertificateRequest = new SetBucketDomainCertificateRequest(
                "bucketName", bucketPutDomainCertificate);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketDomainCertificate(setBucketDomainCertificateRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketDomainCertificate2_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketPutDomainCertificate bucketPutDomainCertificate = new BucketPutDomainCertificate();
        final BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();
        bucketDomainCertificateInfo.setCert("cert");
        bucketDomainCertificateInfo.setPrivateKey("privateKey");
        bucketDomainCertificateInfo.setCertType("certType");
        bucketPutDomainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        bucketPutDomainCertificate.setDomainList(Arrays.asList("value"));
        final SetBucketDomainCertificateRequest setBucketDomainCertificateRequest = new SetBucketDomainCertificateRequest(
                "bucketName", bucketPutDomainCertificate);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketDomainCertificate(setBucketDomainCertificateRequest));
    }

    @Test
    public void testSetBucketDomainCertificate2_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketPutDomainCertificate bucketPutDomainCertificate = new BucketPutDomainCertificate();
        final BucketDomainCertificateInfo bucketDomainCertificateInfo = new BucketDomainCertificateInfo();
        bucketDomainCertificateInfo.setCert("cert");
        bucketDomainCertificateInfo.setPrivateKey("privateKey");
        bucketDomainCertificateInfo.setCertType("certType");
        bucketPutDomainCertificate.setBucketDomainCertificateInfo(bucketDomainCertificateInfo);
        bucketPutDomainCertificate.setDomainList(Arrays.asList("value"));
        final SetBucketDomainCertificateRequest setBucketDomainCertificateRequest = new SetBucketDomainCertificateRequest(
                "bucketName", bucketPutDomainCertificate);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketDomainCertificate(setBucketDomainCertificateRequest));
    }

    @Test
    public void testGetBucketDomainCertificate1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketGetDomainCertificate result = cosClientUnderTest.getBucketDomainCertificate("bucketName",
                "domainName");

        // Verify the results
    }

    @Test
    public void testGetBucketDomainCertificate1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketDomainCertificate("bucketName", "domainName"));
    }

    @Test
    public void testGetBucketDomainCertificate1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketDomainCertificate("bucketName", "domainName"));
    }

    @Test
    public void testGetBucketDomainCertificate2() throws Exception {
        // Setup
        final BucketDomainCertificateRequest getBucketDomainCertificateRequest = new BucketDomainCertificateRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketGetDomainCertificate result = cosClientUnderTest.getBucketDomainCertificate(
                getBucketDomainCertificateRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketDomainCertificate2_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketDomainCertificateRequest getBucketDomainCertificateRequest = new BucketDomainCertificateRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketDomainCertificate(getBucketDomainCertificateRequest));
    }

    @Test
    public void testGetBucketDomainCertificate2_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketDomainCertificateRequest getBucketDomainCertificateRequest = new BucketDomainCertificateRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketDomainCertificate(getBucketDomainCertificateRequest));
    }

    @Test
    public void testDeleteBucketDomainCertificate1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketDomainCertificate("bucketName", "domainName");

        // Verify the results
    }

    @Test
    public void testDeleteBucketDomainCertificate1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketDomainCertificate("bucketName", "domainName"));
    }

    @Test
    public void testDeleteBucketDomainCertificate1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketDomainCertificate("bucketName", "domainName"));
    }

    @Test
    public void testDeleteBucketDomainCertificate2() throws Exception {
        // Setup
        final BucketDomainCertificateRequest deleteBucketDomainCertificateRequest = new BucketDomainCertificateRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketDomainCertificate(deleteBucketDomainCertificateRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketDomainCertificate2_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketDomainCertificateRequest deleteBucketDomainCertificateRequest = new BucketDomainCertificateRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketDomainCertificate(deleteBucketDomainCertificateRequest));
    }

    @Test
    public void testDeleteBucketDomainCertificate2_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketDomainCertificateRequest deleteBucketDomainCertificateRequest = new BucketDomainCertificateRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketDomainCertificate(deleteBucketDomainCertificateRequest));
    }

    @Test
    public void testSetBucketRefererConfiguration1() throws Exception {
        // Setup
        final BucketRefererConfiguration configuration = new BucketRefererConfiguration();
        configuration.setStatus("status");
        configuration.setRefererType("refererType");
        configuration.setDomainList(Arrays.asList("value"));
        configuration.setEmptyReferConfiguration("emptyReferConfiguration");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketRefererConfiguration("bucketName", configuration);

        // Verify the results
    }

    @Test
    public void testSetBucketRefererConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketRefererConfiguration configuration = new BucketRefererConfiguration();
        configuration.setStatus("status");
        configuration.setRefererType("refererType");
        configuration.setDomainList(Arrays.asList("value"));
        configuration.setEmptyReferConfiguration("emptyReferConfiguration");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketRefererConfiguration("bucketName", configuration));
    }

    @Test
    public void testSetBucketRefererConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketRefererConfiguration configuration = new BucketRefererConfiguration();
        configuration.setStatus("status");
        configuration.setRefererType("refererType");
        configuration.setDomainList(Arrays.asList("value"));
        configuration.setEmptyReferConfiguration("emptyReferConfiguration");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketRefererConfiguration("bucketName", configuration));
    }

    @Test
    public void testSetBucketRefererConfiguration2() throws Exception {
        // Setup
        final BucketRefererConfiguration bucketRefererConfiguration = new BucketRefererConfiguration();
        bucketRefererConfiguration.setStatus("status");
        bucketRefererConfiguration.setRefererType("refererType");
        bucketRefererConfiguration.setDomainList(Arrays.asList("value"));
        bucketRefererConfiguration.setEmptyReferConfiguration("emptyReferConfiguration");
        final SetBucketRefererConfigurationRequest setBucketRefererConfigurationRequest = new SetBucketRefererConfigurationRequest(
                "bucketName", bucketRefererConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketRefererConfiguration(setBucketRefererConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketRefererConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final BucketRefererConfiguration bucketRefererConfiguration = new BucketRefererConfiguration();
        bucketRefererConfiguration.setStatus("status");
        bucketRefererConfiguration.setRefererType("refererType");
        bucketRefererConfiguration.setDomainList(Arrays.asList("value"));
        bucketRefererConfiguration.setEmptyReferConfiguration("emptyReferConfiguration");
        final SetBucketRefererConfigurationRequest setBucketRefererConfigurationRequest = new SetBucketRefererConfigurationRequest(
                "bucketName", bucketRefererConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketRefererConfiguration(setBucketRefererConfigurationRequest));
    }

    @Test
    public void testSetBucketRefererConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final BucketRefererConfiguration bucketRefererConfiguration = new BucketRefererConfiguration();
        bucketRefererConfiguration.setStatus("status");
        bucketRefererConfiguration.setRefererType("refererType");
        bucketRefererConfiguration.setDomainList(Arrays.asList("value"));
        bucketRefererConfiguration.setEmptyReferConfiguration("emptyReferConfiguration");
        final SetBucketRefererConfigurationRequest setBucketRefererConfigurationRequest = new SetBucketRefererConfigurationRequest(
                "bucketName", bucketRefererConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketRefererConfiguration(setBucketRefererConfigurationRequest));
    }

    @Test
    public void testGetBucketRefererConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketRefererConfiguration result = cosClientUnderTest.getBucketRefererConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketRefererConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.getBucketRefererConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketRefererConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.getBucketRefererConfiguration("bucketName"));
    }

    @Test
    public void testGetBucketRefererConfiguration2() throws Exception {
        // Setup
        final GetBucketRefererConfigurationRequest getBucketRefererConfigurationRequest = new GetBucketRefererConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketRefererConfiguration result = cosClientUnderTest.getBucketRefererConfiguration(
                getBucketRefererConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketRefererConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketRefererConfigurationRequest getBucketRefererConfigurationRequest = new GetBucketRefererConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketRefererConfiguration(getBucketRefererConfigurationRequest));
    }

    @Test
    public void testGetBucketRefererConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketRefererConfigurationRequest getBucketRefererConfigurationRequest = new GetBucketRefererConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketRefererConfiguration(getBucketRefererConfigurationRequest));
    }

    @Test
    public void testDeleteBucketInventoryConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DeleteBucketInventoryConfigurationResult result = cosClientUnderTest.deleteBucketInventoryConfiguration(
                "bucketName", "id");

        // Verify the results
    }

    @Test
    public void testDeleteBucketInventoryConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketInventoryConfiguration("bucketName", "id"));
    }

    @Test
    public void testDeleteBucketInventoryConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketInventoryConfiguration("bucketName", "id"));
    }

    @Test
    public void testDeleteBucketInventoryConfiguration2() throws Exception {
        // Setup
        final DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest = new DeleteBucketInventoryConfigurationRequest(
                "bucketName", "id");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DeleteBucketInventoryConfigurationResult result = cosClientUnderTest.deleteBucketInventoryConfiguration(
                deleteBucketInventoryConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketInventoryConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest = new DeleteBucketInventoryConfigurationRequest(
                "bucketName", "id");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.deleteBucketInventoryConfiguration(deleteBucketInventoryConfigurationRequest));
    }

    @Test
    public void testDeleteBucketInventoryConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final DeleteBucketInventoryConfigurationRequest deleteBucketInventoryConfigurationRequest = new DeleteBucketInventoryConfigurationRequest(
                "bucketName", "id");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.deleteBucketInventoryConfiguration(deleteBucketInventoryConfigurationRequest));
    }

    @Test
    public void testGetBucketInventoryConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final GetBucketInventoryConfigurationResult result = cosClientUnderTest.getBucketInventoryConfiguration(
                "bucketName", "id");

        // Verify the results
    }

    @Test
    public void testGetBucketInventoryConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketInventoryConfiguration("bucketName", "id"));
    }

    @Test
    public void testGetBucketInventoryConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketInventoryConfiguration("bucketName", "id"));
    }

    @Test
    public void testGetBucketInventoryConfiguration2() throws Exception {
        // Setup
        final GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest = new GetBucketInventoryConfigurationRequest(
                "bucketName", "id");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final GetBucketInventoryConfigurationResult result = cosClientUnderTest.getBucketInventoryConfiguration(
                getBucketInventoryConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketInventoryConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest = new GetBucketInventoryConfigurationRequest(
                "bucketName", "id");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.getBucketInventoryConfiguration(getBucketInventoryConfigurationRequest));
    }

    @Test
    public void testGetBucketInventoryConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final GetBucketInventoryConfigurationRequest getBucketInventoryConfigurationRequest = new GetBucketInventoryConfigurationRequest(
                "bucketName", "id");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.getBucketInventoryConfiguration(getBucketInventoryConfigurationRequest));
    }

    @Test
    public void testSetBucketInventoryConfiguration1() throws Exception {
        // Setup
        final InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        inventoryConfiguration.setId("id");
        final InventoryDestination destination = new InventoryDestination();
        final InventoryCosBucketDestination cosBucketDestination = new InventoryCosBucketDestination();
        cosBucketDestination.setAccountId("accountId");
        cosBucketDestination.setBucketArn("bucketArn");
        cosBucketDestination.setFormat("format");
        cosBucketDestination.setPrefix("prefix");
        cosBucketDestination.setEncryption(null);
        destination.setCosBucketDestination(cosBucketDestination);
        inventoryConfiguration.setDestination(destination);
        inventoryConfiguration.setEnabled(false);
        inventoryConfiguration.setInventoryFilter(new InventoryFilter(null));
        inventoryConfiguration.setIncludedObjectVersions("includedObjectVersions");
        inventoryConfiguration.setOptionalFields(Arrays.asList("value"));
        final InventorySchedule schedule = new InventorySchedule();
        schedule.setFrequency("frequency");
        inventoryConfiguration.setSchedule(schedule);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final SetBucketInventoryConfigurationResult result = cosClientUnderTest.setBucketInventoryConfiguration(
                "bucketName", inventoryConfiguration);

        // Verify the results
    }

    @Test
    public void testSetBucketInventoryConfiguration1_ThrowsCosClientException() throws Exception {
        // Setup
        final InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        inventoryConfiguration.setId("id");
        final InventoryDestination destination = new InventoryDestination();
        final InventoryCosBucketDestination cosBucketDestination = new InventoryCosBucketDestination();
        cosBucketDestination.setAccountId("accountId");
        cosBucketDestination.setBucketArn("bucketArn");
        cosBucketDestination.setFormat("format");
        cosBucketDestination.setPrefix("prefix");
        cosBucketDestination.setEncryption(null);
        destination.setCosBucketDestination(cosBucketDestination);
        inventoryConfiguration.setDestination(destination);
        inventoryConfiguration.setEnabled(false);
        inventoryConfiguration.setInventoryFilter(new InventoryFilter(null));
        inventoryConfiguration.setIncludedObjectVersions("includedObjectVersions");
        inventoryConfiguration.setOptionalFields(Arrays.asList("value"));
        final InventorySchedule schedule = new InventorySchedule();
        schedule.setFrequency("frequency");
        inventoryConfiguration.setSchedule(schedule);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketInventoryConfiguration("bucketName", inventoryConfiguration));
    }

    @Test
    public void testSetBucketInventoryConfiguration1_ThrowsCosServiceException() throws Exception {
        // Setup
        final InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        inventoryConfiguration.setId("id");
        final InventoryDestination destination = new InventoryDestination();
        final InventoryCosBucketDestination cosBucketDestination = new InventoryCosBucketDestination();
        cosBucketDestination.setAccountId("accountId");
        cosBucketDestination.setBucketArn("bucketArn");
        cosBucketDestination.setFormat("format");
        cosBucketDestination.setPrefix("prefix");
        cosBucketDestination.setEncryption(null);
        destination.setCosBucketDestination(cosBucketDestination);
        inventoryConfiguration.setDestination(destination);
        inventoryConfiguration.setEnabled(false);
        inventoryConfiguration.setInventoryFilter(new InventoryFilter(null));
        inventoryConfiguration.setIncludedObjectVersions("includedObjectVersions");
        inventoryConfiguration.setOptionalFields(Arrays.asList("value"));
        final InventorySchedule schedule = new InventorySchedule();
        schedule.setFrequency("frequency");
        inventoryConfiguration.setSchedule(schedule);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketInventoryConfiguration("bucketName", inventoryConfiguration));
    }

    @Test
    public void testSetBucketInventoryConfiguration2() throws Exception {
        // Setup
        final InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        inventoryConfiguration.setId("id");
        final InventoryDestination destination = new InventoryDestination();
        final InventoryCosBucketDestination cosBucketDestination = new InventoryCosBucketDestination();
        cosBucketDestination.setAccountId("accountId");
        cosBucketDestination.setBucketArn("bucketArn");
        cosBucketDestination.setFormat("format");
        cosBucketDestination.setPrefix("prefix");
        cosBucketDestination.setEncryption(null);
        destination.setCosBucketDestination(cosBucketDestination);
        inventoryConfiguration.setDestination(destination);
        inventoryConfiguration.setEnabled(false);
        inventoryConfiguration.setInventoryFilter(new InventoryFilter(null));
        inventoryConfiguration.setIncludedObjectVersions("includedObjectVersions");
        inventoryConfiguration.setOptionalFields(Arrays.asList("value"));
        final InventorySchedule schedule = new InventorySchedule();
        schedule.setFrequency("frequency");
        inventoryConfiguration.setSchedule(schedule);
        final SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest = new SetBucketInventoryConfigurationRequest(
                "bucketName", inventoryConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final SetBucketInventoryConfigurationResult result = cosClientUnderTest.setBucketInventoryConfiguration(
                setBucketInventoryConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketInventoryConfiguration2_ThrowsCosClientException() throws Exception {
        // Setup
        final InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        inventoryConfiguration.setId("id");
        final InventoryDestination destination = new InventoryDestination();
        final InventoryCosBucketDestination cosBucketDestination = new InventoryCosBucketDestination();
        cosBucketDestination.setAccountId("accountId");
        cosBucketDestination.setBucketArn("bucketArn");
        cosBucketDestination.setFormat("format");
        cosBucketDestination.setPrefix("prefix");
        cosBucketDestination.setEncryption(null);
        destination.setCosBucketDestination(cosBucketDestination);
        inventoryConfiguration.setDestination(destination);
        inventoryConfiguration.setEnabled(false);
        inventoryConfiguration.setInventoryFilter(new InventoryFilter(null));
        inventoryConfiguration.setIncludedObjectVersions("includedObjectVersions");
        inventoryConfiguration.setOptionalFields(Arrays.asList("value"));
        final InventorySchedule schedule = new InventorySchedule();
        schedule.setFrequency("frequency");
        inventoryConfiguration.setSchedule(schedule);
        final SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest = new SetBucketInventoryConfigurationRequest(
                "bucketName", inventoryConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.setBucketInventoryConfiguration(setBucketInventoryConfigurationRequest));
    }

    @Test
    public void testSetBucketInventoryConfiguration2_ThrowsCosServiceException() throws Exception {
        // Setup
        final InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        inventoryConfiguration.setId("id");
        final InventoryDestination destination = new InventoryDestination();
        final InventoryCosBucketDestination cosBucketDestination = new InventoryCosBucketDestination();
        cosBucketDestination.setAccountId("accountId");
        cosBucketDestination.setBucketArn("bucketArn");
        cosBucketDestination.setFormat("format");
        cosBucketDestination.setPrefix("prefix");
        cosBucketDestination.setEncryption(null);
        destination.setCosBucketDestination(cosBucketDestination);
        inventoryConfiguration.setDestination(destination);
        inventoryConfiguration.setEnabled(false);
        inventoryConfiguration.setInventoryFilter(new InventoryFilter(null));
        inventoryConfiguration.setIncludedObjectVersions("includedObjectVersions");
        inventoryConfiguration.setOptionalFields(Arrays.asList("value"));
        final InventorySchedule schedule = new InventorySchedule();
        schedule.setFrequency("frequency");
        inventoryConfiguration.setSchedule(schedule);
        final SetBucketInventoryConfigurationRequest setBucketInventoryConfigurationRequest = new SetBucketInventoryConfigurationRequest(
                "bucketName", inventoryConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.setBucketInventoryConfiguration(setBucketInventoryConfigurationRequest));
    }

    @Test
    public void testListBucketInventoryConfigurations() throws Exception {
        // Setup
        final ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest = new ListBucketInventoryConfigurationsRequest();
        listBucketInventoryConfigurationsRequest.setFixedEndpointAddr("endpoint");
        listBucketInventoryConfigurationsRequest.setCosCredentials(null);
        listBucketInventoryConfigurationsRequest.setBucketName("bucketName");
        listBucketInventoryConfigurationsRequest.setContinuationToken("continuationToken");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ListBucketInventoryConfigurationsResult result = cosClientUnderTest.listBucketInventoryConfigurations(
                listBucketInventoryConfigurationsRequest);

        // Verify the results
    }

    @Test
    public void testListBucketInventoryConfigurations_ThrowsCosClientException() throws Exception {
        // Setup
        final ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest = new ListBucketInventoryConfigurationsRequest();
        listBucketInventoryConfigurationsRequest.setFixedEndpointAddr("endpoint");
        listBucketInventoryConfigurationsRequest.setCosCredentials(null);
        listBucketInventoryConfigurationsRequest.setBucketName("bucketName");
        listBucketInventoryConfigurationsRequest.setContinuationToken("continuationToken");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class,
                () -> cosClientUnderTest.listBucketInventoryConfigurations(listBucketInventoryConfigurationsRequest));
    }

    @Test
    public void testListBucketInventoryConfigurations_ThrowsCosServiceException() throws Exception {
        // Setup
        final ListBucketInventoryConfigurationsRequest listBucketInventoryConfigurationsRequest = new ListBucketInventoryConfigurationsRequest();
        listBucketInventoryConfigurationsRequest.setFixedEndpointAddr("endpoint");
        listBucketInventoryConfigurationsRequest.setCosCredentials(null);
        listBucketInventoryConfigurationsRequest.setBucketName("bucketName");
        listBucketInventoryConfigurationsRequest.setContinuationToken("continuationToken");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class,
                () -> cosClientUnderTest.listBucketInventoryConfigurations(listBucketInventoryConfigurationsRequest));
    }

    @Test
    public void testGetBucketTaggingConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketTaggingConfiguration result = cosClientUnderTest.getBucketTaggingConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testGetBucketTaggingConfiguration2() throws Exception {
        // Setup
        final GetBucketTaggingConfigurationRequest getBucketTaggingConfigurationRequest = new GetBucketTaggingConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketTaggingConfiguration result = cosClientUnderTest.getBucketTaggingConfiguration(
                getBucketTaggingConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSetBucketTaggingConfiguration1() throws Exception {
        // Setup
        final BucketTaggingConfiguration bucketTaggingConfiguration = new BucketTaggingConfiguration(
                Arrays.asList(new TagSet(new HashMap<>())));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketTaggingConfiguration("bucketName", bucketTaggingConfiguration);

        // Verify the results
    }

    @Test
    public void testSetBucketTaggingConfiguration2() throws Exception {
        // Setup
        final SetBucketTaggingConfigurationRequest setBucketTaggingConfigurationRequest = new SetBucketTaggingConfigurationRequest(
                "bucketName", new BucketTaggingConfiguration(Arrays.asList(new TagSet(new HashMap<>()))));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketTaggingConfiguration(setBucketTaggingConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testDeleteBucketTaggingConfiguration1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketTaggingConfiguration("bucketName");

        // Verify the results
    }

    @Test
    public void testDeleteBucketTaggingConfiguration2() throws Exception {
        // Setup
        final DeleteBucketTaggingConfigurationRequest deleteBucketTaggingConfigurationRequest = new DeleteBucketTaggingConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.deleteBucketTaggingConfiguration(deleteBucketTaggingConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testSelectObjectContent() throws Exception {
        // Setup
        final SelectObjectContentRequest selectRequest = new SelectObjectContentRequest();
        selectRequest.setFixedEndpointAddr("endpoint");
        selectRequest.setCosCredentials(null);
        selectRequest.setBucketName("bucketName");
        selectRequest.setKey("destinationKey");
        selectRequest.setExpression("expression");
        selectRequest.setExpressionType("expressionType");
        final RequestProgress requestProgress = new RequestProgress();
        requestProgress.setEnabled(false);
        selectRequest.setRequestProgress(requestProgress);
        final InputSerialization inputSerialization = new InputSerialization();
        final CSVInput csv = new CSVInput();
        csv.setFileHeaderInfo("fileHeaderInfo");
        csv.setComments('a');
        csv.setQuoteEscapeCharacter('a');
        csv.setRecordDelimiter('a');
        inputSerialization.setCsv(csv);
        selectRequest.setInputSerialization(inputSerialization);
        selectRequest.setSSECustomerKey(new SSECustomerKey("base64EncodedKey"));

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final SelectObjectContentResult result = cosClientUnderTest.selectObjectContent(selectRequest);

        // Verify the results
    }

    @Test
    public void testSelectObjectContent_ThrowsCosClientException() throws Exception {
        // Setup
        final SelectObjectContentRequest selectRequest = new SelectObjectContentRequest();
        selectRequest.setFixedEndpointAddr("endpoint");
        selectRequest.setCosCredentials(null);
        selectRequest.setBucketName("bucketName");
        selectRequest.setKey("destinationKey");
        selectRequest.setExpression("expression");
        selectRequest.setExpressionType("expressionType");
        final RequestProgress requestProgress = new RequestProgress();
        requestProgress.setEnabled(false);
        selectRequest.setRequestProgress(requestProgress);
        final InputSerialization inputSerialization = new InputSerialization();
        final CSVInput csv = new CSVInput();
        csv.setFileHeaderInfo("fileHeaderInfo");
        csv.setComments('a');
        csv.setQuoteEscapeCharacter('a');
        csv.setRecordDelimiter('a');
        inputSerialization.setCsv(csv);
        selectRequest.setInputSerialization(inputSerialization);
        selectRequest.setSSECustomerKey(new SSECustomerKey("base64EncodedKey"));

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosClientException.class, () -> cosClientUnderTest.selectObjectContent(selectRequest));
    }

    @Test
    public void testSelectObjectContent_ThrowsCosServiceException() throws Exception {
        // Setup
        final SelectObjectContentRequest selectRequest = new SelectObjectContentRequest();
        selectRequest.setFixedEndpointAddr("endpoint");
        selectRequest.setCosCredentials(null);
        selectRequest.setBucketName("bucketName");
        selectRequest.setKey("destinationKey");
        selectRequest.setExpression("expression");
        selectRequest.setExpressionType("expressionType");
        final RequestProgress requestProgress = new RequestProgress();
        requestProgress.setEnabled(false);
        selectRequest.setRequestProgress(requestProgress);
        final InputSerialization inputSerialization = new InputSerialization();
        final CSVInput csv = new CSVInput();
        csv.setFileHeaderInfo("fileHeaderInfo");
        csv.setComments('a');
        csv.setQuoteEscapeCharacter('a');
        csv.setRecordDelimiter('a');
        inputSerialization.setCsv(csv);
        selectRequest.setInputSerialization(inputSerialization);
        selectRequest.setSSECustomerKey(new SSECustomerKey("base64EncodedKey"));

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(CosServiceException.class, () -> cosClientUnderTest.selectObjectContent(selectRequest));
    }

    @Test
    public void testGetObjectTagging() throws Exception {
        // Setup
        final GetObjectTaggingRequest getObjectTaggingRequest = new GetObjectTaggingRequest("bucketName",
                "destinationKey", "versionId");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final GetObjectTaggingResult result = cosClientUnderTest.getObjectTagging(getObjectTaggingRequest);

        // Verify the results
    }

    @Test
    public void testSetObjectTagging() throws Exception {
        // Setup
        final SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest("bucketName",
                "destinationKey", "versionId", new ObjectTagging(Arrays.asList(new Tag("key", "value"))));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final SetObjectTaggingResult result = cosClientUnderTest.setObjectTagging(setObjectTaggingRequest);

        // Verify the results
    }

    @Test
    public void testDeleteObjectTagging() throws Exception {
        // Setup
        final DeleteObjectTaggingRequest deleteObjectTaggingRequest = new DeleteObjectTaggingRequest("bucketName",
                "destinationKey");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DeleteObjectTaggingResult result = cosClientUnderTest.deleteObjectTagging(deleteObjectTaggingRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketIntelligentTierConfiguration1() throws Exception {
        // Setup
        final GetBucketIntelligentTierConfigurationRequest getBucketIntelligentTierConfigurationRequest = new GetBucketIntelligentTierConfigurationRequest(
                "bucketName");
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketIntelligentTierConfiguration result = cosClientUnderTest.getBucketIntelligentTierConfiguration(
                getBucketIntelligentTierConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testGetBucketIntelligentTierConfiguration2() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BucketIntelligentTierConfiguration result = cosClientUnderTest.getBucketIntelligentTierConfiguration(
                "bucketName");

        // Verify the results
    }

    @Test
    public void testSetBucketIntelligentTieringConfiguration() throws Exception {
        // Setup
        final BucketIntelligentTierConfiguration bucketIntelligentTierConfiguration = new BucketIntelligentTierConfiguration();
        bucketIntelligentTierConfiguration.setStatus("status");
        bucketIntelligentTierConfiguration.setTransition(new BucketIntelligentTierConfiguration.Transition(0));
        final SetBucketIntelligentTierConfigurationRequest setBucketIntelligentTierConfigurationRequest = new SetBucketIntelligentTierConfigurationRequest(
                "bucketName", bucketIntelligentTierConfiguration);
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        cosClientUnderTest.setBucketIntelligentTieringConfiguration(setBucketIntelligentTierConfigurationRequest);

        // Verify the results
    }

    @Test
    public void testCreateMediaJobs() throws Exception {
        // Setup
        final MediaJobsRequest req = new MediaJobsRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        req.setQueueId("queueId");
        req.setTag("tag");
        req.setOrderByTime("orderByTime");
        req.setNextToken("nextToken");
        req.setSize(0);
        req.setStates("states");
        req.setStartCreationTime("startCreationTime");
        req.setEndCreationTime("endCreationTime");
        req.setJobId("jobId");
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        req.setInput(input);
        req.setCallBack("callBack");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaJobResponse result = cosClientUnderTest.createMediaJobs(req);

        // Verify the results
    }

    @Test
    public void testCancelMediaJob() throws Exception {
        // Setup
        final MediaJobsRequest req = new MediaJobsRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        req.setQueueId("queueId");
        req.setTag("tag");
        req.setOrderByTime("orderByTime");
        req.setNextToken("nextToken");
        req.setSize(0);
        req.setStates("states");
        req.setStartCreationTime("startCreationTime");
        req.setEndCreationTime("endCreationTime");
        req.setJobId("jobId");
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        req.setInput(input);
        req.setCallBack("callBack");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Boolean result = cosClientUnderTest.cancelMediaJob(req);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDescribeMediaJob() throws Exception {
        // Setup
        final MediaJobsRequest req = new MediaJobsRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        req.setQueueId("queueId");
        req.setTag("tag");
        req.setOrderByTime("orderByTime");
        req.setNextToken("nextToken");
        req.setSize(0);
        req.setStates("states");
        req.setStartCreationTime("startCreationTime");
        req.setEndCreationTime("endCreationTime");
        req.setJobId("jobId");
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        req.setInput(input);
        req.setCallBack("callBack");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaJobResponse result = cosClientUnderTest.describeMediaJob(req);

        // Verify the results
    }

    @Test
    public void testDescribeMediaJobs() throws Exception {
        // Setup
        final MediaJobsRequest req = new MediaJobsRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        req.setQueueId("queueId");
        req.setTag("tag");
        req.setOrderByTime("orderByTime");
        req.setNextToken("nextToken");
        req.setSize(0);
        req.setStates("states");
        req.setStartCreationTime("startCreationTime");
        req.setEndCreationTime("endCreationTime");
        req.setJobId("jobId");
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        req.setInput(input);
        req.setCallBack("callBack");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaListJobResponse result = cosClientUnderTest.describeMediaJobs(req);

        // Verify the results
    }

    @Test
    public void testDescribeMediaQueues() throws Exception {
        // Setup
        final MediaQueueRequest req = new MediaQueueRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        req.setQueueId("queueId");
        req.setState("state");
        req.setPageNumber("pageNumber");
        req.setPageSize("pageSize");
        final MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
        notifyConfig.setUrl("url");
        notifyConfig.setType("type");
        notifyConfig.setEvent("event");
        notifyConfig.setState("state");
        req.setNotifyConfig(notifyConfig);
        req.setName("name");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaListQueueResponse result = cosClientUnderTest.describeMediaQueues(req);

        // Verify the results
    }

    @Test
    public void testUpdateMediaQueue() throws Exception {
        // Setup
        final MediaQueueRequest mediaQueueRequest = new MediaQueueRequest();
        mediaQueueRequest.setFixedEndpointAddr("endpoint");
        mediaQueueRequest.setCosCredentials(null);
        mediaQueueRequest.setBucketName("bucketName");
        mediaQueueRequest.setQueueId("queueId");
        mediaQueueRequest.setState("state");
        mediaQueueRequest.setPageNumber("pageNumber");
        mediaQueueRequest.setPageSize("pageSize");
        final MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
        notifyConfig.setUrl("url");
        notifyConfig.setType("type");
        notifyConfig.setEvent("event");
        notifyConfig.setState("state");
        mediaQueueRequest.setNotifyConfig(notifyConfig);
        mediaQueueRequest.setName("name");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaQueueResponse result = cosClientUnderTest.updateMediaQueue(mediaQueueRequest);

        // Verify the results
    }

    @Test
    public void testDescribeMediaBuckets() throws Exception {
        // Setup
        final MediaBucketRequest mediaBucketRequest = new MediaBucketRequest();
        mediaBucketRequest.setFixedEndpointAddr("endpoint");
        mediaBucketRequest.setCosCredentials(null);
        mediaBucketRequest.setRegions("regions");
        mediaBucketRequest.setBucketNames("bucketNames");
        mediaBucketRequest.setBucketName("bucketName");
        mediaBucketRequest.setPageNumber("pageNumber");
        mediaBucketRequest.setPageSize("pageSize");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaBucketResponse result = cosClientUnderTest.describeMediaBuckets(mediaBucketRequest);

        // Verify the results
    }

    @Test
    public void testCreateMediaTemplate() throws Exception {
        // Setup
        final MediaTemplateRequest templateRequest = new MediaTemplateRequest();
        templateRequest.setFixedEndpointAddr("endpoint");
        templateRequest.setCosCredentials(null);
        templateRequest.setBucketName("bucketName");
        templateRequest.setMode("mode");
        templateRequest.setCodec("codec");
        templateRequest.setVoiceType("voiceType");
        templateRequest.setVolume("volume");
        templateRequest.setSpeed("speed");
        templateRequest.setPageNumber("pageNumber");
        templateRequest.setPageSize("pageSize");
        templateRequest.setTag("tag");
        templateRequest.setName("name");
        templateRequest.setTemplateId("templateId");
        templateRequest.setCategory("category");
        templateRequest.setIds("ids");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaTemplateResponse result = cosClientUnderTest.createMediaTemplate(templateRequest);

        // Verify the results
    }

    @Test
    public void testDeleteMediaTemplate() throws Exception {
        // Setup
        final MediaTemplateRequest request = new MediaTemplateRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setMode("mode");
        request.setCodec("codec");
        request.setVoiceType("voiceType");
        request.setVolume("volume");
        request.setSpeed("speed");
        request.setPageNumber("pageNumber");
        request.setPageSize("pageSize");
        request.setTag("tag");
        request.setName("name");
        request.setTemplateId("templateId");
        request.setCategory("category");
        request.setIds("ids");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Boolean result = cosClientUnderTest.deleteMediaTemplate(request);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDescribeMediaTemplates() throws Exception {
        // Setup
        final MediaTemplateRequest request = new MediaTemplateRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setMode("mode");
        request.setCodec("codec");
        request.setVoiceType("voiceType");
        request.setVolume("volume");
        request.setSpeed("speed");
        request.setPageNumber("pageNumber");
        request.setPageSize("pageSize");
        request.setTag("tag");
        request.setName("name");
        request.setTemplateId("templateId");
        request.setCategory("category");
        request.setIds("ids");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaListTemplateResponse result = cosClientUnderTest.describeMediaTemplates(request);

        // Verify the results
    }

    @Test
    public void testUpdateMediaTemplate() throws Exception {
        // Setup
        final MediaTemplateRequest request = new MediaTemplateRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setMode("mode");
        request.setCodec("codec");
        request.setVoiceType("voiceType");
        request.setVolume("volume");
        request.setSpeed("speed");
        request.setPageNumber("pageNumber");
        request.setPageSize("pageSize");
        request.setTag("tag");
        request.setName("name");
        request.setTemplateId("templateId");
        request.setCategory("category");
        request.setIds("ids");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Boolean result = cosClientUnderTest.updateMediaTemplate(request);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testGenerateSnapshot() throws Exception {
        // Setup
        final SnapshotRequest request = new SnapshotRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        request.setInput(input);
        final MediaOutputObject output = new MediaOutputObject();
        output.setRegion("region");
        output.setBucket("bucket");
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        output.setAuObject("auObject");
        request.setOutput(output);
        request.setTime("time");
        request.setWidth("width");
        request.setHeight("height");
        request.setFormat("format");
        request.setMode("mode");
        request.setRotate("rotate");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final SnapshotResponse result = cosClientUnderTest.generateSnapshot(request);

        // Verify the results
    }

    @Test
    public void testGenerateMediainfo() throws Exception {
        // Setup
        final MediaInfoRequest request = new MediaInfoRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        request.setInput(input);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaInfoResponse result = cosClientUnderTest.generateMediainfo(request);

        // Verify the results
    }

    @Test
    public void testDeleteWorkflow() throws Exception {
        // Setup
        final MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setRunId("runId");
        request.setIds("ids");
        request.setName("name");
        request.setPageNumber("pageNumber");
        request.setPageSize("pageSize");
        request.setWorkflowId("workflowId");
        request.setOrderByTime("orderByTime");
        request.setSize("size");
        request.setStates("states");
        request.setStartCreationTime("startCreationTime");
        request.setEndCreationTime("endCreationTime");
        request.setNextToken("nextToken");
        request.setObject("object");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Boolean result = cosClientUnderTest.deleteWorkflow(request);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDescribeWorkflow() throws Exception {
        // Setup
        final MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setRunId("runId");
        request.setIds("ids");
        request.setName("name");
        request.setPageNumber("pageNumber");
        request.setPageSize("pageSize");
        request.setWorkflowId("workflowId");
        request.setOrderByTime("orderByTime");
        request.setSize("size");
        request.setStates("states");
        request.setStartCreationTime("startCreationTime");
        request.setEndCreationTime("endCreationTime");
        request.setNextToken("nextToken");
        request.setObject("object");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaWorkflowListResponse result = cosClientUnderTest.describeWorkflow(request);

        // Verify the results
    }

    @Test
    public void testDescribeWorkflowExecution() throws Exception {
        // Setup
        final MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setRunId("runId");
        request.setIds("ids");
        request.setName("name");
        request.setPageNumber("pageNumber");
        request.setPageSize("pageSize");
        request.setWorkflowId("workflowId");
        request.setOrderByTime("orderByTime");
        request.setSize("size");
        request.setStates("states");
        request.setStartCreationTime("startCreationTime");
        request.setEndCreationTime("endCreationTime");
        request.setNextToken("nextToken");
        request.setObject("object");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaWorkflowExecutionResponse result = cosClientUnderTest.describeWorkflowExecution(request);

        // Verify the results
    }

    @Test
    public void testDescribeWorkflowExecutions() throws Exception {
        // Setup
        final MediaWorkflowListRequest request = new MediaWorkflowListRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setRunId("runId");
        request.setIds("ids");
        request.setName("name");
        request.setPageNumber("pageNumber");
        request.setPageSize("pageSize");
        request.setWorkflowId("workflowId");
        request.setOrderByTime("orderByTime");
        request.setSize("size");
        request.setStates("states");
        request.setStartCreationTime("startCreationTime");
        request.setEndCreationTime("endCreationTime");
        request.setNextToken("nextToken");
        request.setObject("object");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaWorkflowExecutionsResponse result = cosClientUnderTest.describeWorkflowExecutions(request);

        // Verify the results
    }

    @Test
    public void testCreateDocProcessJobs() throws Exception {
        // Setup
        final DocJobRequest request = new DocJobRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        final DocJobObject docJobObject = new DocJobObject();
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        docJobObject.setInput(input);
        docJobObject.setTag("tag");
        docJobObject.setQueueId("queueId");
        final DocOperationObject operation = new DocOperationObject();
        final MediaOutputObject output = new MediaOutputObject();
        output.setRegion("region");
        output.setBucket("bucket");
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        output.setAuObject("auObject");
        operation.setOutput(output);
        final DocProcessObject docProcessObject = new DocProcessObject();
        docProcessObject.setPicPagination("picPagination");
        docProcessObject.setImageDpi("imageDpi");
        docProcessObject.setSrcType("srcType");
        docProcessObject.setTgtType("tgtType");
        docProcessObject.setSheetId("sheetId");
        docProcessObject.setStartPage("startPage");
        docProcessObject.setEndPage("endPage");
        docProcessObject.setImageParams("imageParams");
        docProcessObject.setDocPassword("docPassword");
        docProcessObject.setComments("comments");
        docProcessObject.setPaperDirection("paperDirection");
        docProcessObject.setQuality("quality");
        docProcessObject.setZoom("zoom");
        operation.setDocProcessObject(docProcessObject);
        docJobObject.setOperation(operation);
        request.setDocJobObject(docJobObject);
        request.setJobId("jobId");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DocJobResponse result = cosClientUnderTest.createDocProcessJobs(request);

        // Verify the results
    }

    @Test
    public void testDescribeDocProcessJob() throws Exception {
        // Setup
        final DocJobRequest request = new DocJobRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        final DocJobObject docJobObject = new DocJobObject();
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        docJobObject.setInput(input);
        docJobObject.setTag("tag");
        docJobObject.setQueueId("queueId");
        final DocOperationObject operation = new DocOperationObject();
        final MediaOutputObject output = new MediaOutputObject();
        output.setRegion("region");
        output.setBucket("bucket");
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        output.setAuObject("auObject");
        operation.setOutput(output);
        final DocProcessObject docProcessObject = new DocProcessObject();
        docProcessObject.setPicPagination("picPagination");
        docProcessObject.setImageDpi("imageDpi");
        docProcessObject.setSrcType("srcType");
        docProcessObject.setTgtType("tgtType");
        docProcessObject.setSheetId("sheetId");
        docProcessObject.setStartPage("startPage");
        docProcessObject.setEndPage("endPage");
        docProcessObject.setImageParams("imageParams");
        docProcessObject.setDocPassword("docPassword");
        docProcessObject.setComments("comments");
        docProcessObject.setPaperDirection("paperDirection");
        docProcessObject.setQuality("quality");
        docProcessObject.setZoom("zoom");
        operation.setDocProcessObject(docProcessObject);
        docJobObject.setOperation(operation);
        request.setDocJobObject(docJobObject);
        request.setJobId("jobId");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DocJobResponse result = cosClientUnderTest.describeDocProcessJob(request);

        // Verify the results
    }

    @Test
    public void testDescribeDocProcessJobs() throws Exception {
        // Setup
        final DocJobListRequest request = new DocJobListRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setQueueId("queueId");
        request.setTag("tag");
        request.setOrderByTime("orderByTime");
        request.setNextToken("nextToken");
        request.setSize(0);
        request.setStates("states");
        request.setStartCreationTime("startCreationTime");
        request.setEndCreationTime("endCreationTime");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DocJobListResponse result = cosClientUnderTest.describeDocProcessJobs(request);

        // Verify the results
    }

    @Test
    public void testDescribeDocProcessQueues() throws Exception {
        // Setup
        final DocQueueRequest docRequest = new DocQueueRequest();
        docRequest.setFixedEndpointAddr("endpoint");
        docRequest.setCosCredentials(null);
        docRequest.setBucketName("bucketName");
        docRequest.setQueueId("queueId");
        docRequest.setState("state");
        docRequest.setPageNumber("pageNumber");
        docRequest.setPageSize("pageSize");
        final MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
        notifyConfig.setUrl("url");
        notifyConfig.setType("type");
        notifyConfig.setEvent("event");
        notifyConfig.setState("state");
        docRequest.setNotifyConfig(notifyConfig);
        docRequest.setName("name");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DocListQueueResponse result = cosClientUnderTest.describeDocProcessQueues(docRequest);

        // Verify the results
    }

    @Test
    public void testUpdateDocProcessQueue() throws Exception {
        // Setup
        final DocQueueRequest docRequest = new DocQueueRequest();
        docRequest.setFixedEndpointAddr("endpoint");
        docRequest.setCosCredentials(null);
        docRequest.setBucketName("bucketName");
        docRequest.setQueueId("queueId");
        docRequest.setState("state");
        docRequest.setPageNumber("pageNumber");
        docRequest.setPageSize("pageSize");
        final MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
        notifyConfig.setUrl("url");
        notifyConfig.setType("type");
        notifyConfig.setEvent("event");
        notifyConfig.setState("state");
        docRequest.setNotifyConfig(notifyConfig);
        docRequest.setName("name");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final boolean result = cosClientUnderTest.updateDocProcessQueue(docRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDescribeDocProcessBuckets() throws Exception {
        // Setup
        final DocBucketRequest docRequest = new DocBucketRequest();
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DocBucketResponse result = cosClientUnderTest.describeDocProcessBuckets(docRequest);

        // Verify the results
    }

    @Test
    public void testProcessImage() throws Exception {
        // Setup
        final ImageProcessRequest imageProcessRequest = new ImageProcessRequest("bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final CIUploadResult result = cosClientUnderTest.processImage(imageProcessRequest);

        // Verify the results
    }

    @Test
    public void testImageAuditing() throws Exception {
        // Setup
        final ImageAuditingRequest imageAuditingRequest = new ImageAuditingRequest();
        imageAuditingRequest.setFixedEndpointAddr("endpoint");
        imageAuditingRequest.setCosCredentials(null);
        imageAuditingRequest.setDetectType("detectType");
        imageAuditingRequest.setBucketName("bucketName");
        imageAuditingRequest.setObjectKey("destinationKey");
        imageAuditingRequest.setInterval(0);
        imageAuditingRequest.setMaxFrames(0);
        imageAuditingRequest.setBizType("bizType");
        imageAuditingRequest.setDetectUrl("detectUrl");
        imageAuditingRequest.setLargeImageDetect("largeImageDetect");
        imageAuditingRequest.setJobId("jobId");
        imageAuditingRequest.setDataId("dataId");
        imageAuditingRequest.setAsync("async");
        imageAuditingRequest.setCallback("callback");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ImageAuditingResponse result = cosClientUnderTest.imageAuditing(imageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testCreateVideoAuditingJob() throws Exception {
        // Setup
        final VideoAuditingRequest videoAuditingRequest = new VideoAuditingRequest();
        videoAuditingRequest.setFixedEndpointAddr("endpoint");
        videoAuditingRequest.setCosCredentials(null);
        videoAuditingRequest.setBucketName("bucketName");
        videoAuditingRequest.setJobId("jobId");
        final AuditingInputObject input = new AuditingInputObject();
        input.setObject("object");
        input.setContent("content");
        input.setUrl("url");
        input.setDataId("dataId");
        final UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        final Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        input.setEncryption(encryption);
        videoAuditingRequest.setInput(input);
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        videoAuditingRequest.setConf(conf);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final VideoAuditingResponse result = cosClientUnderTest.createVideoAuditingJob(videoAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAuditingJob() throws Exception {
        // Setup
        final VideoAuditingRequest videoAuditingRequest = new VideoAuditingRequest();
        videoAuditingRequest.setFixedEndpointAddr("endpoint");
        videoAuditingRequest.setCosCredentials(null);
        videoAuditingRequest.setBucketName("bucketName");
        videoAuditingRequest.setJobId("jobId");
        final AuditingInputObject input = new AuditingInputObject();
        input.setObject("object");
        input.setContent("content");
        input.setUrl("url");
        input.setDataId("dataId");
        final UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        final Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        input.setEncryption(encryption);
        videoAuditingRequest.setInput(input);
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        videoAuditingRequest.setConf(conf);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final VideoAuditingResponse result = cosClientUnderTest.describeAuditingJob(videoAuditingRequest);

        // Verify the results
    }

    @Test
    public void testCreateAudioAuditingJobs() throws Exception {
        // Setup
        final AudioAuditingRequest audioAuditingRequest = new AudioAuditingRequest();
        audioAuditingRequest.setFixedEndpointAddr("endpoint");
        audioAuditingRequest.setCosCredentials(null);
        audioAuditingRequest.setBucketName("bucketName");
        audioAuditingRequest.setJobId("jobId");
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        audioAuditingRequest.setConf(conf);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final AudioAuditingResponse result = cosClientUnderTest.createAudioAuditingJobs(audioAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAudioAuditingJob() throws Exception {
        // Setup
        final AudioAuditingRequest audioAuditingRequest = new AudioAuditingRequest();
        audioAuditingRequest.setFixedEndpointAddr("endpoint");
        audioAuditingRequest.setCosCredentials(null);
        audioAuditingRequest.setBucketName("bucketName");
        audioAuditingRequest.setJobId("jobId");
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        audioAuditingRequest.setConf(conf);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final AudioAuditingResponse result = cosClientUnderTest.describeAudioAuditingJob(audioAuditingRequest);

        // Verify the results
    }

    @Test
    public void testGetImageLabel() throws Exception {
        // Setup
        final ImageLabelRequest imageLabelRequest = new ImageLabelRequest();
        imageLabelRequest.setFixedEndpointAddr("endpoint");
        imageLabelRequest.setCosCredentials(null);
        imageLabelRequest.setObjectKey("destinationKey");
        imageLabelRequest.setBucketName("bucketName");
        imageLabelRequest.setDetectUrl("detectUrl");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ImageLabelResponse result = cosClientUnderTest.getImageLabel(imageLabelRequest);

        // Verify the results
    }

    @Test
    public void testGetImageLabelV2() throws Exception {
        // Setup
        final ImageLabelV2Request imageLabelV2Request = new ImageLabelV2Request();
        imageLabelV2Request.setFixedEndpointAddr("endpoint");
        imageLabelV2Request.setCosCredentials(null);
        imageLabelV2Request.setBucketName("bucketName");
        imageLabelV2Request.setObjectKey("destinationKey");
        imageLabelV2Request.setScenes("scenes");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ImageLabelV2Response result = cosClientUnderTest.getImageLabelV2(imageLabelV2Request);

        // Verify the results
    }

    @Test
    public void testCreateAuditingTextJobs() throws Exception {
        // Setup
        final TextAuditingRequest textAuditingRequest = new TextAuditingRequest();
        textAuditingRequest.setFixedEndpointAddr("endpoint");
        textAuditingRequest.setCosCredentials(null);
        textAuditingRequest.setBucketName("bucketName");
        textAuditingRequest.setJobId("jobId");
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        textAuditingRequest.setConf(conf);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final TextAuditingResponse result = cosClientUnderTest.createAuditingTextJobs(textAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAuditingTextJob() throws Exception {
        // Setup
        final TextAuditingRequest textAuditingRequest = new TextAuditingRequest();
        textAuditingRequest.setFixedEndpointAddr("endpoint");
        textAuditingRequest.setCosCredentials(null);
        textAuditingRequest.setBucketName("bucketName");
        textAuditingRequest.setJobId("jobId");
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        textAuditingRequest.setConf(conf);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final TextAuditingResponse result = cosClientUnderTest.describeAuditingTextJob(textAuditingRequest);

        // Verify the results
    }

    @Test
    public void testCreateAuditingDocumentJobs() throws Exception {
        // Setup
        final DocumentAuditingRequest documentAuditingRequest = new DocumentAuditingRequest();
        documentAuditingRequest.setFixedEndpointAddr("endpoint");
        documentAuditingRequest.setCosCredentials(null);
        documentAuditingRequest.setBucketName("bucketName");
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        documentAuditingRequest.setConf(conf);
        documentAuditingRequest.setJobId("jobId");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DocumentAuditingResponse result = cosClientUnderTest.createAuditingDocumentJobs(documentAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAuditingDocumentJob() throws Exception {
        // Setup
        final DocumentAuditingRequest documentAuditingRequest = new DocumentAuditingRequest();
        documentAuditingRequest.setFixedEndpointAddr("endpoint");
        documentAuditingRequest.setCosCredentials(null);
        documentAuditingRequest.setBucketName("bucketName");
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        documentAuditingRequest.setConf(conf);
        documentAuditingRequest.setJobId("jobId");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DocumentAuditingResponse result = cosClientUnderTest.describeAuditingDocumentJob(documentAuditingRequest);

        // Verify the results
    }

    @Test
    public void testBatchImageAuditing() throws Exception {
        // Setup
        final BatchImageAuditingRequest batchImageAuditingRequest = new BatchImageAuditingRequest();
        batchImageAuditingRequest.setFixedEndpointAddr("endpoint");
        batchImageAuditingRequest.setCosCredentials(null);
        batchImageAuditingRequest.setBucketName("bucketName");
        final BatchImageAuditingInputObject batchImageAuditingInputObject = new BatchImageAuditingInputObject();
        batchImageAuditingInputObject.setObject("object");
        batchImageAuditingInputObject.setContent("content");
        batchImageAuditingInputObject.setUrl("url");
        batchImageAuditingInputObject.setDataId("dataId");
        final UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        batchImageAuditingInputObject.setUserInfo(userInfo);
        final Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        batchImageAuditingInputObject.setEncryption(encryption);
        batchImageAuditingInputObject.setInterval("interval");
        batchImageAuditingInputObject.setMaxFrames("maxFrames");
        batchImageAuditingInputObject.setLargeImageDetect("largeImageDetect");
        batchImageAuditingRequest.setInputList(Arrays.asList(batchImageAuditingInputObject));
        final Conf conf = new Conf();
        conf.setBizType("bizType");
        conf.setDetectContent("detectContent");
        conf.setDetectType("detectType");
        final AuditingSnapshotObject snapshot = new AuditingSnapshotObject();
        snapshot.setMode("mode");
        snapshot.setCount("count");
        snapshot.setTimeInterval("timeInterval");
        conf.setSnapshot(snapshot);
        conf.setCallback("callback");
        conf.setCallbackVersion(CallbackVersion.Simple);
        conf.setAsync("async");
        conf.setCallbackType("callbackType");
        conf.setReturnHighlightHtml("returnHighlightHtml");
        final Freeze freeze = new Freeze();
        freeze.setPornScore("pornScore");
        freeze.setPoliticsScore("politicsScore");
        freeze.setTerrorismScore("terrorismScore");
        freeze.setAdsScore("adsScore");
        conf.setFreeze(freeze);
        batchImageAuditingRequest.setConf(conf);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BatchImageAuditingResponse result = cosClientUnderTest.batchImageAuditing(batchImageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testCreateDocProcessBucket() throws Exception {
        // Setup
        final DocBucketRequest docBucketRequest = new DocBucketRequest();
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Boolean result = cosClientUnderTest.createDocProcessBucket(docBucketRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testGenerateDocPreviewUrl() throws Exception {
        // Setup
        final DocHtmlRequest docJobRequest = new DocHtmlRequest();
        docJobRequest.setFixedEndpointAddr("endpoint");
        docJobRequest.setCosCredentials(null);
        docJobRequest.setImageDpi("imageDpi");
        docJobRequest.setObjectKey("destinationKey");
        docJobRequest.setSrcType("srcType");
        docJobRequest.setPage("page");
        docJobRequest.setImageParams("imageParams");
        docJobRequest.setSheet("sheet");
        docJobRequest.setPassword("password");
        docJobRequest.setComment("comment");
        docJobRequest.setExcelPaperDirection("excelPaperDirection");
        docJobRequest.setQuality("quality");
        docJobRequest.setScale("scale");
        docJobRequest.setBucketName("bucketName");
        docJobRequest.setDstType(DocHtmlRequest.DocType.html);
        docJobRequest.setExcelPaperSize("excelPaperSize");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final String result = cosClientUnderTest.GenerateDocPreviewUrl(docJobRequest);

        // Verify the results
        assertEquals("result", result);
    }

    @Test
    public void testGenerateDocPreviewUrl_ThrowsURISyntaxException() throws Exception {
        // Setup
        final DocHtmlRequest docJobRequest = new DocHtmlRequest();
        docJobRequest.setFixedEndpointAddr("endpoint");
        docJobRequest.setCosCredentials(null);
        docJobRequest.setImageDpi("imageDpi");
        docJobRequest.setObjectKey("destinationKey");
        docJobRequest.setSrcType("srcType");
        docJobRequest.setPage("page");
        docJobRequest.setImageParams("imageParams");
        docJobRequest.setSheet("sheet");
        docJobRequest.setPassword("password");
        docJobRequest.setComment("comment");
        docJobRequest.setExcelPaperDirection("excelPaperDirection");
        docJobRequest.setQuality("quality");
        docJobRequest.setScale("scale");
        docJobRequest.setBucketName("bucketName");
        docJobRequest.setDstType(DocHtmlRequest.DocType.html);
        docJobRequest.setExcelPaperSize("excelPaperSize");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        assertThrows(URISyntaxException.class, () -> cosClientUnderTest.GenerateDocPreviewUrl(docJobRequest));
    }

    @Test
    public void testCreateWebpageAuditingJob() throws Exception {
        // Setup
        final WebpageAuditingRequest webpageAuditingRequest = new WebpageAuditingRequest();
        webpageAuditingRequest.setFixedEndpointAddr("endpoint");
        webpageAuditingRequest.setCosCredentials(null);
        webpageAuditingRequest.setBucketName("bucketName");
        webpageAuditingRequest.setJobId("jobId");
        final AuditingInputObject input = new AuditingInputObject();
        input.setObject("object");
        input.setContent("content");
        input.setUrl("url");
        input.setDataId("dataId");
        final UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        final Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        input.setEncryption(encryption);
        webpageAuditingRequest.setInput(input);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final WebpageAuditingResponse result = cosClientUnderTest.createWebpageAuditingJob(webpageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testDescribeWebpageAuditingJob() throws Exception {
        // Setup
        final WebpageAuditingRequest webpageAuditingRequest = new WebpageAuditingRequest();
        webpageAuditingRequest.setFixedEndpointAddr("endpoint");
        webpageAuditingRequest.setCosCredentials(null);
        webpageAuditingRequest.setBucketName("bucketName");
        webpageAuditingRequest.setJobId("jobId");
        final AuditingInputObject input = new AuditingInputObject();
        input.setObject("object");
        input.setContent("content");
        input.setUrl("url");
        input.setDataId("dataId");
        final UserInfo userInfo = new UserInfo();
        userInfo.setTokenId("tokenId");
        userInfo.setNickname("nickname");
        userInfo.setDeviceId("deviceId");
        userInfo.setAppId("appId");
        userInfo.setRoom("room");
        userInfo.setIp("ip");
        userInfo.setType("type");
        userInfo.setReceiveTokenId("receiveTokenId");
        userInfo.setGender("gender");
        userInfo.setLevel("level");
        userInfo.setRole("role");
        input.setUserInfo(userInfo);
        final Encryption encryption = new Encryption();
        encryption.setAlgorithm("algorithm");
        encryption.setKey("key");
        encryption.setIV("iV");
        encryption.setKeyId("keyId");
        encryption.setKeyType("keyType");
        input.setEncryption(encryption);
        webpageAuditingRequest.setInput(input);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final WebpageAuditingResponse result = cosClientUnderTest.describeWebpageAuditingJob(webpageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testReportBadCase() throws Exception {
        // Setup
        final ReportBadCaseRequest reportBadCaseRequest = new ReportBadCaseRequest();
        reportBadCaseRequest.setFixedEndpointAddr("endpoint");
        reportBadCaseRequest.setCosCredentials(null);
        reportBadCaseRequest.setBucketName("bucketName");
        reportBadCaseRequest.setContentType("contentType");
        reportBadCaseRequest.setText("text");
        reportBadCaseRequest.setLabel("label");
        reportBadCaseRequest.setSuggestedLabel(SuggestedLabel.Normal);
        reportBadCaseRequest.setJobId("jobId");
        reportBadCaseRequest.setModerationTime("moderationTime");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final String result = cosClientUnderTest.reportBadCase(reportBadCaseRequest);

        // Verify the results
        assertEquals("policyText", result);
    }

    @Test
    public void testGetObjectUrl1() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.getObjectUrl("bucketName", "destinationKey");

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testGetObjectUrl2() throws Exception {
        // Setup
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.getObjectUrl("bucketName", "destinationKey", "versionId");

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testGetObjectUrl3() throws Exception {
        // Setup
        final GetObjectRequest getObjectRequest = new GetObjectRequest("bucketName", "destinationKey", "versionId");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);

        // Run the test
        final URL result = cosClientUnderTest.getObjectUrl(getObjectRequest);

        // Verify the results
        assertEquals(new URL("https://example.com/"), result);
    }

    @Test
    public void testPutAsyncFetchTask() throws Exception {
        // Setup
        final PutAsyncFetchTaskRequest putAsyncFetchTaskRequest = new PutAsyncFetchTaskRequest();
        putAsyncFetchTaskRequest.setFixedEndpointAddr("endpoint");
        putAsyncFetchTaskRequest.setCosCredentials(null);
        putAsyncFetchTaskRequest.setBucketName("bucketName");
        putAsyncFetchTaskRequest.setUrl("url");
        putAsyncFetchTaskRequest.setMd5("md5");
        putAsyncFetchTaskRequest.setIgnoreSameKey(false);
        putAsyncFetchTaskRequest.setKey("key");
        putAsyncFetchTaskRequest.setSuccessCallbackUrl("successCallbackUrl");
        putAsyncFetchTaskRequest.setFailureCallbackUrl("failureCallbackUrl");
        putAsyncFetchTaskRequest.setOnKeyExist("onKeyExist");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PutAsyncFetchTaskResult result = cosClientUnderTest.putAsyncFetchTask(putAsyncFetchTaskRequest);

        // Verify the results
    }

    @Test
    public void testGetAsyncFetchTask() throws Exception {
        // Setup
        final GetAsyncFetchTaskRequest getAsyncFetchTaskRequest = new GetAsyncFetchTaskRequest();
        getAsyncFetchTaskRequest.setFixedEndpointAddr("endpoint");
        getAsyncFetchTaskRequest.setCosCredentials(null);
        getAsyncFetchTaskRequest.setBucketName("bucketName");
        getAsyncFetchTaskRequest.setTaskId("taskId");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final GetAsyncFetchTaskResult result = cosClientUnderTest.getAsyncFetchTask(getAsyncFetchTaskRequest);

        // Verify the results
    }

    @Test
    public void testDescribeAuditingImageJob() throws Exception {
        // Setup
        final DescribeImageAuditingRequest imageAuditingRequest = new DescribeImageAuditingRequest();
        imageAuditingRequest.setFixedEndpointAddr("endpoint");
        imageAuditingRequest.setCosCredentials(null);
        imageAuditingRequest.setBucketName("bucketName");
        imageAuditingRequest.setJobId("jobId");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ImageAuditingResponse result = cosClientUnderTest.describeAuditingImageJob(imageAuditingRequest);

        // Verify the results
    }

    @Test
    public void testGetPrivateM3U8() throws Exception {
        // Setup
        final PrivateM3U8Request privateM3U8Request = new PrivateM3U8Request();
        privateM3U8Request.setFixedEndpointAddr("endpoint");
        privateM3U8Request.setCosCredentials(null);
        privateM3U8Request.setBucketName("bucketName");
        privateM3U8Request.setExpires("expires");
        privateM3U8Request.setObject("destinationKey");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final PrivateM3U8Response result = cosClientUnderTest.getPrivateM3U8(privateM3U8Request);

        // Verify the results
    }

    @Test
    public void testDetectCar() throws Exception {
        // Setup
        final DetectCarRequest detectCarRequest = new DetectCarRequest();
        detectCarRequest.setFixedEndpointAddr("endpoint");
        detectCarRequest.setCosCredentials(null);
        detectCarRequest.setBucketName("bucketName");
        detectCarRequest.setObjectKey("destinationKey");
        detectCarRequest.setDetectUrl("detectUrl");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DetectCarResponse result = cosClientUnderTest.detectCar(detectCarRequest);

        // Verify the results
    }

    @Test
    public void testOpenImageSearch() throws Exception {
        // Setup
        final OpenImageSearchRequest imageSearchRequest = new OpenImageSearchRequest();
        imageSearchRequest.setFixedEndpointAddr("endpoint");
        imageSearchRequest.setCosCredentials(null);
        imageSearchRequest.setBucketName("bucketName");
        imageSearchRequest.setMaxCapacity("maxCapacity");
        imageSearchRequest.setMaxQps("maxQps");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final boolean result = cosClientUnderTest.openImageSearch(imageSearchRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testAddGalleryImages() throws Exception {
        // Setup
        final ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
        imageSearchRequest.setFixedEndpointAddr("endpoint");
        imageSearchRequest.setCosCredentials(null);
        imageSearchRequest.setObjectKey("destinationKey");
        imageSearchRequest.setBucketName("bucketName");
        imageSearchRequest.setEntityId("entityId");
        imageSearchRequest.setCustomContent("customContent");
        imageSearchRequest.setTags("tags");
        imageSearchRequest.setMatchThreshold("matchThreshold");
        imageSearchRequest.setOffset("offset");
        imageSearchRequest.setLimit("limit");
        imageSearchRequest.setFilter("filter");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final boolean result = cosClientUnderTest.addGalleryImages(imageSearchRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testDeleteGalleryImages() throws Exception {
        // Setup
        final ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
        imageSearchRequest.setFixedEndpointAddr("endpoint");
        imageSearchRequest.setCosCredentials(null);
        imageSearchRequest.setObjectKey("destinationKey");
        imageSearchRequest.setBucketName("bucketName");
        imageSearchRequest.setEntityId("entityId");
        imageSearchRequest.setCustomContent("customContent");
        imageSearchRequest.setTags("tags");
        imageSearchRequest.setMatchThreshold("matchThreshold");
        imageSearchRequest.setOffset("offset");
        imageSearchRequest.setLimit("limit");
        imageSearchRequest.setFilter("filter");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final boolean result = cosClientUnderTest.deleteGalleryImages(imageSearchRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testSearchGalleryImages() throws Exception {
        // Setup
        final ImageSearchRequest imageSearchRequest = new ImageSearchRequest();
        imageSearchRequest.setFixedEndpointAddr("endpoint");
        imageSearchRequest.setCosCredentials(null);
        imageSearchRequest.setObjectKey("destinationKey");
        imageSearchRequest.setBucketName("bucketName");
        imageSearchRequest.setEntityId("entityId");
        imageSearchRequest.setCustomContent("customContent");
        imageSearchRequest.setTags("tags");
        imageSearchRequest.setMatchThreshold("matchThreshold");
        imageSearchRequest.setOffset("offset");
        imageSearchRequest.setLimit("limit");
        imageSearchRequest.setFilter("filter");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ImageSearchResponse result = cosClientUnderTest.searchGalleryImages(imageSearchRequest);

        // Verify the results
    }

    @Test
    public void testTriggerWorkflowList() throws Exception {
        // Setup
        final MediaWorkflowListRequest mediaWorkflowListRequest = new MediaWorkflowListRequest();
        mediaWorkflowListRequest.setFixedEndpointAddr("endpoint");
        mediaWorkflowListRequest.setCosCredentials(null);
        mediaWorkflowListRequest.setBucketName("bucketName");
        mediaWorkflowListRequest.setRunId("runId");
        mediaWorkflowListRequest.setIds("ids");
        mediaWorkflowListRequest.setName("name");
        mediaWorkflowListRequest.setPageNumber("pageNumber");
        mediaWorkflowListRequest.setPageSize("pageSize");
        mediaWorkflowListRequest.setWorkflowId("workflowId");
        mediaWorkflowListRequest.setOrderByTime("orderByTime");
        mediaWorkflowListRequest.setSize("size");
        mediaWorkflowListRequest.setStates("states");
        mediaWorkflowListRequest.setStartCreationTime("startCreationTime");
        mediaWorkflowListRequest.setEndCreationTime("endCreationTime");
        mediaWorkflowListRequest.setNextToken("nextToken");
        mediaWorkflowListRequest.setObject("object");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaWorkflowListResponse result = cosClientUnderTest.triggerWorkflowList(mediaWorkflowListRequest);

        // Verify the results
    }

    @Test
    public void testGetSnapshot() throws Exception {
        // Setup
        final CosSnapshotRequest snapshotRequest = new CosSnapshotRequest();
        snapshotRequest.setFixedEndpointAddr("endpoint");
        snapshotRequest.setCosCredentials(null);
        snapshotRequest.setTime("time");
        snapshotRequest.setWidth("width");
        snapshotRequest.setHeight("height");
        snapshotRequest.setFormat("format");
        snapshotRequest.setMode("mode");
        snapshotRequest.setRotate("rotate");
        snapshotRequest.setBucketName("bucketName");
        snapshotRequest.setObjectKey("destinationKey");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final InputStream result = cosClientUnderTest.getSnapshot(snapshotRequest);

        // Verify the results
    }

    @Test
    public void testGenerateQrcode() throws Exception {
        // Setup
        final GenerateQrcodeRequest generateQrcodeRequest = new GenerateQrcodeRequest();
        generateQrcodeRequest.setFixedEndpointAddr("endpoint");
        generateQrcodeRequest.setCosCredentials(null);
        generateQrcodeRequest.setQrcodeContent("qrcodeContent");
        generateQrcodeRequest.setMode("mode");
        generateQrcodeRequest.setWidth("width");
        generateQrcodeRequest.setBucketName("bucketName");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final String result = cosClientUnderTest.generateQrcode(generateQrcodeRequest);

        // Verify the results
        assertEquals("policyText", result);
    }

    @Test
    public void testAddImageStyle() throws Exception {
        // Setup
        final ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
        imageStyleRequest.setFixedEndpointAddr("endpoint");
        imageStyleRequest.setCosCredentials(null);
        imageStyleRequest.setBucketName("bucketName");
        imageStyleRequest.setStyleName("styleName");
        imageStyleRequest.setStyleBody("styleBody");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Boolean result = cosClientUnderTest.addImageStyle(imageStyleRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testGetImageStyle() throws Exception {
        // Setup
        final ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
        imageStyleRequest.setFixedEndpointAddr("endpoint");
        imageStyleRequest.setCosCredentials(null);
        imageStyleRequest.setBucketName("bucketName");
        imageStyleRequest.setStyleName("styleName");
        imageStyleRequest.setStyleBody("styleBody");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ImageStyleResponse result = cosClientUnderTest.getImageStyle(imageStyleRequest);

        // Verify the results
    }

    @Test
    public void testDeleteImageStyle() throws Exception {
        // Setup
        final ImageStyleRequest imageStyleRequest = new ImageStyleRequest();
        imageStyleRequest.setFixedEndpointAddr("endpoint");
        imageStyleRequest.setCosCredentials(null);
        imageStyleRequest.setBucketName("bucketName");
        imageStyleRequest.setStyleName("styleName");
        imageStyleRequest.setStyleBody("styleBody");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final Boolean result = cosClientUnderTest.deleteImageStyle(imageStyleRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testGetObjectDecompressionStatus1() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final String result = cosClientUnderTest.getObjectDecompressionStatus("bucketName", "destinationKey");

        // Verify the results
        assertEquals("policyText", result);
    }

    @Test
    public void testPostObjectDecompression() throws Exception {
        // Setup
        final DecompressionRequest decompressionRequest = new DecompressionRequest();
        decompressionRequest.setFixedEndpointAddr("endpoint");
        decompressionRequest.setCosCredentials(null);
        decompressionRequest.setTargetBucketName("targetBucketName");
        decompressionRequest.setPrefixReplaced(false);
        decompressionRequest.setResourcesPrefix("resourcesPrefix");
        decompressionRequest.setTargetKeyPrefix("targetKeyPrefix");
        decompressionRequest.setObjectKey("destinationKey");
        decompressionRequest.setSourceBucketName("sourceBucketName");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DecompressionResult result = cosClientUnderTest.postObjectDecompression(decompressionRequest);

        // Verify the results
    }

    @Test
    public void testGetObjectDecompressionStatus2() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final DecompressionResult result = cosClientUnderTest.getObjectDecompressionStatus("bucketName",
                "destinationKey", "jobId");

        // Verify the results
    }

    @Test
    public void testListObjectDecompressionJobs() throws Exception {
        // Setup
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final ListJobsResult result = cosClientUnderTest.listObjectDecompressionJobs("bucketName", "jobStatus",
                "sortBy", "maxResults", "nextToken");

        // Verify the results
    }

    @Test
    public void testCreatePicProcessJob() throws Exception {
        // Setup
        final MediaJobsRequest req = new MediaJobsRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        req.setQueueId("queueId");
        req.setTag("tag");
        req.setOrderByTime("orderByTime");
        req.setNextToken("nextToken");
        req.setSize(0);
        req.setStates("states");
        req.setStartCreationTime("startCreationTime");
        req.setEndCreationTime("endCreationTime");
        req.setJobId("jobId");
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        req.setInput(input);
        req.setCallBack("callBack");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaJobResponse result = cosClientUnderTest.createPicProcessJob(req);

        // Verify the results
    }

    @Test
    public void testDescribePicProcessQueues() throws Exception {
        // Setup
        final MediaQueueRequest req = new MediaQueueRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        req.setQueueId("queueId");
        req.setState("state");
        req.setPageNumber("pageNumber");
        req.setPageSize("pageSize");
        final MediaNotifyConfig notifyConfig = new MediaNotifyConfig();
        notifyConfig.setUrl("url");
        notifyConfig.setType("type");
        notifyConfig.setEvent("event");
        notifyConfig.setState("state");
        req.setNotifyConfig(notifyConfig);
        req.setName("name");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final MediaListQueueResponse result = cosClientUnderTest.describePicProcessQueues(req);

        // Verify the results
    }

    @Test
    public void testProcessImage2() throws Exception {
        // Setup
        final CImageProcessRequest imageProcessRequest = new CImageProcessRequest("bucketName", "destinationKey");
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final boolean result = cosClientUnderTest.processImage2(imageProcessRequest);

        // Verify the results
        assertFalse(result);
    }

    @Test
    public void testCreateFileProcessJob() throws Exception {
        // Setup
        final FileProcessRequest req = new FileProcessRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        req.setTag(FileProcessJobType.FileCompress);
        req.setQueueId("queueId");
        req.setCallBackFormat("callBackFormat");
        req.setCallBackType("callBackType");
        req.setCallBack("callBack");
        req.setCallBackMqConfig("callBackMqConfig");
        final FileProcessOperation operation = new FileProcessOperation();
        final FileCompressConfig fileCompressConfig = new FileCompressConfig();
        fileCompressConfig.setFlatten("flatten");
        fileCompressConfig.setFormat("format");
        fileCompressConfig.setUrlList("urlList");
        fileCompressConfig.setPrefix("prefix");
        fileCompressConfig.setKey(Arrays.asList("value"));
        operation.setFileCompressConfig(fileCompressConfig);
        operation.setUserData("userData");
        final MediaOutputObject output = new MediaOutputObject();
        output.setRegion("region");
        output.setBucket("bucket");
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        output.setAuObject("auObject");
        operation.setOutput(output);
        final FileUnCompressConfig fileUnCompressConfig = new FileUnCompressConfig();
        fileUnCompressConfig.setPrefix("prefix");
        fileUnCompressConfig.setPrefixReplaced("prefixReplaced");
        operation.setFileUnCompressConfig(fileUnCompressConfig);
        final FileHashCodeConfig fileHashCodeConfig = new FileHashCodeConfig();
        fileHashCodeConfig.setType("type");
        fileHashCodeConfig.setAddToHeader("addToHeader");
        operation.setFileHashCodeConfig(fileHashCodeConfig);
        req.setOperation(operation);
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        req.setInput(input);
        req.setJobId("jobId");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final FileProcessJobResponse result = cosClientUnderTest.createFileProcessJob(req);

        // Verify the results
    }

    @Test
    public void testDescribeFileProcessJob() throws Exception {
        // Setup
        final FileProcessRequest request = new FileProcessRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        request.setTag(FileProcessJobType.FileCompress);
        request.setQueueId("queueId");
        request.setCallBackFormat("callBackFormat");
        request.setCallBackType("callBackType");
        request.setCallBack("callBack");
        request.setCallBackMqConfig("callBackMqConfig");
        final FileProcessOperation operation = new FileProcessOperation();
        final FileCompressConfig fileCompressConfig = new FileCompressConfig();
        fileCompressConfig.setFlatten("flatten");
        fileCompressConfig.setFormat("format");
        fileCompressConfig.setUrlList("urlList");
        fileCompressConfig.setPrefix("prefix");
        fileCompressConfig.setKey(Arrays.asList("value"));
        operation.setFileCompressConfig(fileCompressConfig);
        operation.setUserData("userData");
        final MediaOutputObject output = new MediaOutputObject();
        output.setRegion("region");
        output.setBucket("bucket");
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        output.setAuObject("auObject");
        operation.setOutput(output);
        final FileUnCompressConfig fileUnCompressConfig = new FileUnCompressConfig();
        fileUnCompressConfig.setPrefix("prefix");
        fileUnCompressConfig.setPrefixReplaced("prefixReplaced");
        operation.setFileUnCompressConfig(fileUnCompressConfig);
        final FileHashCodeConfig fileHashCodeConfig = new FileHashCodeConfig();
        fileHashCodeConfig.setType("type");
        fileHashCodeConfig.setAddToHeader("addToHeader");
        operation.setFileHashCodeConfig(fileHashCodeConfig);
        request.setOperation(operation);
        final MediaInputObject input = new MediaInputObject();
        input.setObject("object");
        input.setUrl("url");
        request.setInput(input);
        request.setJobId("jobId");

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final FileProcessJobResponse result = cosClientUnderTest.describeFileProcessJob(request);

        // Verify the results
    }

    @Test
    public void testCreateInventoryTriggerJob() throws Exception {
        // Setup
        final BatchJobRequest req = new BatchJobRequest();
        req.setFixedEndpointAddr("endpoint");
        req.setCosCredentials(null);
        req.setBucketName("bucketName");
        final BatchJobOperation operation = new BatchJobOperation();
        final MediaTimeIntervalObject timeInterval = new MediaTimeIntervalObject();
        operation.setTimeInterval(timeInterval);
        operation.setTag("tag");
        final MediaOutputObject output = new MediaOutputObject();
        output.setRegion("region");
        output.setBucket("bucket");
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        output.setAuObject("auObject");
        operation.setOutput(output);
        final JobParam jobParam = new JobParam();
        operation.setJobParam(jobParam);
        operation.setQueueId("queueId");
        operation.setUserData("userData");
        operation.setJobLevel("jobLevel");
        operation.setCallBack("callBack");
        final CallBackMqConfig callBackMqConfig = new CallBackMqConfig();
        operation.setCallBackMqConfig(callBackMqConfig);
        operation.setWorkflowIds("workflowIds");
        req.setOperation(operation);
        req.setName("name");
        req.setType("type");
        req.setJobId("jobId");
        final BatchInputObject input = new BatchInputObject();
        input.setObject("object");
        input.setUrl("url");
        input.setManifest("manifest");
        input.setUrlFile("urlFile");
        input.setPrefix("prefix");
        req.setInput(input);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BatchJobResponse result = cosClientUnderTest.createInventoryTriggerJob(req);

        // Verify the results
    }

    @Test
    public void testDescribeInventoryTriggerJob() throws Exception {
        // Setup
        final BatchJobRequest request = new BatchJobRequest();
        request.setFixedEndpointAddr("endpoint");
        request.setCosCredentials(null);
        request.setBucketName("bucketName");
        final BatchJobOperation operation = new BatchJobOperation();
        final MediaTimeIntervalObject timeInterval = new MediaTimeIntervalObject();
        operation.setTimeInterval(timeInterval);
        operation.setTag("tag");
        final MediaOutputObject output = new MediaOutputObject();
        output.setRegion("region");
        output.setBucket("bucket");
        output.setObject("object");
        output.setSpriteObject("spriteObject");
        output.setAuObject("auObject");
        operation.setOutput(output);
        final JobParam jobParam = new JobParam();
        operation.setJobParam(jobParam);
        operation.setQueueId("queueId");
        operation.setUserData("userData");
        operation.setJobLevel("jobLevel");
        operation.setCallBack("callBack");
        final CallBackMqConfig callBackMqConfig = new CallBackMqConfig();
        operation.setCallBackMqConfig(callBackMqConfig);
        operation.setWorkflowIds("workflowIds");
        request.setOperation(operation);
        request.setName("name");
        request.setType("type");
        request.setJobId("jobId");
        final BatchInputObject input = new BatchInputObject();
        input.setObject("object");
        input.setUrl("url");
        input.setManifest("manifest");
        input.setUrlFile("urlFile");
        input.setPrefix("prefix");
        request.setInput(input);

        when(mockClientConfig.getUserAgent()).thenReturn("result");
        when(mockClientConfig.getEndpointBuilder()).thenReturn(null);
        when(mockClientConfig.getEndpointResolver()).thenReturn(null);
        when(mockClientConfig.getRegion()).thenReturn(new Region("regionName", "displayName"));
        when(mockClientConfig.getIsDistinguishHost()).thenReturn(false);
        when(mockClientConfig.getCiSpecialRequest()).thenReturn(false);
        when(mockClientConfig.getHttpProtocol()).thenReturn(HttpProtocol.http);
        when(mockClientConfig.getCosSigner()).thenReturn(new COSSigner());
        when(mockClientConfig.getSignExpired()).thenReturn(0L);

        // Run the test
        final BatchJobResponse result = cosClientUnderTest.describeInventoryTriggerJob(request);

        // Verify the results
    }

    @Test
    public void testPopulateRequestMetadata() throws Exception {
        // Setup
        final CosHttpRequest<?> request = new CosHttpRequest<>(null);
        final ObjectMetadata metadata = new ObjectMetadata();

        // Run the test
        COSClient.populateRequestMetadata(request, metadata);

        // Verify the results
    }
}
