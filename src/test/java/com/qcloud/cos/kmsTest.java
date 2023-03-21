package com.qcloud.cos;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSStaticCredentialsProvider;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.internal.crypto.*;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.Base64;
import com.qcloud.cos.utils.Jackson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({COSEncryptionClient.class, COSCryptoModuleAE.class, CryptoModuleDispatcher.class, COSCryptoModuleBase.class})
@PowerMockIgnore({"javax.net.ssl.*"})
public class kmsTest {
    private String appid_ = System.getenv("appid");
    private String cmk = System.getenv("KMS_ID");
    private String secretId_ = System.getenv("secretId");
    private String secretKey_ = System.getenv("secretKey");
    private String region_ = System.getenv("region");
    private String bucket_ = System.getenv("bucket") + (int) (Math.random() * 1000) + "-" + appid_;

    @Test
    public void testKMSEncryptionClientWithInvalidCiphertext() throws Exception {
        COSEncryptionClient.COSDirectImpl cosDirect = PowerMockito.mock(COSEncryptionClient.COSDirectImpl.class);
        PowerMockito.whenNew(COSEncryptionClient.COSDirectImpl.class).withNoArguments().thenReturn(cosDirect);

        PowerMockito.when(cosDirect.putObject(any())).thenAnswer((m)->{
            PutObjectResult putObjectResult = new PutObjectResult();
            putObjectResult.setRequestId("NQISXXXXXXXXX");
            return putObjectResult;
        });

        ObjectMetadata metadata = new ObjectMetadata();
        Map<String, String> userMeta = new HashMap<>();
        userMeta.put(Headers.CRYPTO_KEY_V2, Base64.encodeAsString("test_crypto_key_v2".getBytes()));
        userMeta.put(Headers.CRYPTO_IV, Base64.encodeAsString("test_crypto_iv".getBytes()));
        Map<String, String> map = new HashMap<>();
        map.put(KMSEncryptionMaterials.CUSTOMER_MASTER_KEY_ID, cmk);
        userMeta.put(Headers.MATERIALS_DESCRIPTION, Jackson.toJsonString(map));
        userMeta.put(Headers.CRYPTO_KEYWRAP_ALGORITHM, "kms");
        userMeta.put(Headers.CRYPTO_CEK_ALGORITHM, "AES/GCM/NoPadding");
        userMeta.put(Headers.CRYPTO_TAG_LENGTH, "128");
        metadata.setUserMetadata(userMeta);

        COSObject cosObject = new COSObject();
        cosObject.setObjectMetadata(metadata);

        PowerMockito.when(cosDirect.getObject(any())).thenReturn(cosObject);

        COSCredentials cred = new BasicCOSCredentials(secretId_, secretKey_);
        ClientConfig clientConfig = new ClientConfig(new Region(region_));
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 初始化 KMS 加密材料
        KMSEncryptionMaterials encryptionMaterials = new KMSEncryptionMaterials(cmk);
        // 使用AES/CTR模式，并将加密信息存储在文件元信息中.
        // 如果想要此次加密的对象被 COS 其他的 SDK 解密下载，则必须选择 AesCtrEncryption 模式
        CryptoConfiguration cryptoConf = new CryptoConfiguration(CryptoMode.AesCtrEncryption)
                .withStorageMode(CryptoStorageMode.ObjectMetadata);

        COSEncryptionClient cosEncryptionClient =
                new COSEncryptionClient(new COSStaticCredentialsProvider(cred),
                        new KMSEncryptionMaterialsProvider(encryptionMaterials), clientConfig,
                        cryptoConf);

        Boolean switch_stop_create = true;
        String bucketName = bucket_;
        while (switch_stop_create) {
            try {
                cosEncryptionClient.createBucket(bucketName);
                switch_stop_create = false;
            } catch (CosServiceException cse) {
                if (cse.getStatusCode() == 409) {
                    bucketName = System.getenv("bucket") + (int) (Math.random() * 1000) + "-" + appid_;
                    continue;
                }
                fail(cse.getErrorMessage());
            }
        }

        // 这里创建一个 ByteArrayInputStream 来作为示例，实际中这里应该是您要上传的 InputStream 类型的流
        int inputStreamLength = 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);
        String key_ = "testKMS";
        // 上传文件
        // 这里给出putObject的示例, 对于高级API上传，只用在生成TransferManager时传入COSEncryptionClient对象即可
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key_, inputStream, new ObjectMetadata());
        PutObjectResult putObjectResult = cosEncryptionClient.putObject(putObjectRequest);
        System.out.println(putObjectResult.getRequestId());


        try {
            COSObject cosObject2 = cosEncryptionClient.getObject(new GetObjectRequest(bucketName, key_));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            cosEncryptionClient.deleteObject(bucketName, key_);
            cosEncryptionClient.deleteBucket(bucketName);
            inputStream.close();
            cosEncryptionClient.shutdown();
        }
    }
}
