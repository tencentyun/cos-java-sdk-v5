package com.qcloud.cos;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.internal.crypto.CryptoConfiguration;
import com.qcloud.cos.internal.crypto.CryptoMode;
import com.qcloud.cos.internal.crypto.CryptoStorageMode;
import com.qcloud.cos.internal.crypto.EncryptionMaterials;

import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@Ignore
public class SymmetricCOSEncryptionClientWithObjectMetaCryptoModeTest
        extends AbstractCOSClientTest {

    private static void initEncryptionInfo() throws NoSuchAlgorithmException {
        KeyGenerator symKeyGenerator = KeyGenerator.getInstance("AES");
        symKeyGenerator.init(256);
        SecretKey symKey = symKeyGenerator.generateKey();

        encryptionMaterials = new EncryptionMaterials(symKey);
        cryptoConfiguration = new CryptoConfiguration(CryptoMode.AesCtrEncryption)
                .withStorageMode(CryptoStorageMode.ObjectMetadata);
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        initEncryptionInfo();
        AbstractCOSEncryptionClientTest.setUpBeforeClass();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        AbstractCOSEncryptionClientTest.tearDownAfterClass();
    }

    @Test
    public void testUploadWithClientEncryption() throws Exception{
        File tempFile = buildTestFile(1 * 1024 * 1024L);

        try {
            PutObjectRequest request = new PutObjectRequest(bucket, "testUploadWithClientEncryption.txt", tempFile);
            cosclient.putObject(request);
        } catch (CosClientException cce) {
                fail(cce.getMessage());
        } finally {
            tempFile.delete();
        }
    }

    @Test
    public void testCopyPartWithClientEncryption() throws Exception{
        String bucketName = bucket;

        String srckey = "copysrc.txt";
        String dstkey = "copydst.txt";

        File localFile = buildTestFile(10 * 1024 * 1024L);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, srckey, localFile);
        PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);

        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, dstkey);
        // 设置存储类型, 默认是标准(Standard), 低频(Standard_IA), 归档(Archive)
        request.setStorageClass(StorageClass.Standard);
        String uploadId = "";
        try {
            InitiateMultipartUploadResult initResult = cosclient.initiateMultipartUpload(request);
            // 获取uploadid
            uploadId = initResult.getUploadId();
        } catch (CosClientException e) {
            if (!Objects.equals(e.getMessage(), "initiate multipart upload with encryption client must set dataSize and partSize")){
                throw e;
            }
        }

        request.setDataSizePartSize(10 * 1024 * 1024L, 5 * 1024 * 1024L);

        try {
            InitiateMultipartUploadResult initResult = cosclient.initiateMultipartUpload(request);
            // 获取uploadid
            uploadId = initResult.getUploadId();
        } catch (CosClientException e) {
            if (!Objects.equals(e.getMessage(), "initiate multipart upload with encryption client must set dataSize and partSize")){
                throw e;
            }
        }

        CopyPartRequest copyPartRequest = new CopyPartRequest();

        // 要拷贝的源文件所在的region
        copyPartRequest.setSourceBucketRegion(new Region(region));
        // 要拷贝的源文件的bucket名称
        copyPartRequest.setSourceBucketName(bucketName);
        // 要拷贝的源文件的路径
        copyPartRequest.setSourceKey(srckey);
        copyPartRequest.setSourceAppid(appid);
        // 指定要拷贝的源文件的数据范围(类似content-range)
        copyPartRequest.setFirstByte(0L);
        copyPartRequest.setLastByte(5 * 1024 * 1024L);
        // 目的bucket名称
        copyPartRequest.setDestinationBucketName(bucketName);
        // 目的路径名称
        copyPartRequest.setDestinationKey(dstkey);
        copyPartRequest.setPartNumber(1);
        // uploadId
        copyPartRequest.setUploadId(uploadId);
        try {
            CopyPartResult copyPartResult = cosclient.copyPart(copyPartRequest);
            PartETag partETag = copyPartResult.getPartETag();
            AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucket, dstkey, uploadId);
            cosclient.abortMultipartUpload(abortMultipartUploadRequest);
        } catch (CosServiceException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (CosClientException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } finally {
            if (localFile.exists()) {
                assertTrue(localFile.delete());
            }
        }
    }
}
