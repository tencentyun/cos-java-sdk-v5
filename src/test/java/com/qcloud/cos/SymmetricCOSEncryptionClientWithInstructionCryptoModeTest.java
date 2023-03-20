package com.qcloud.cos;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


import com.qcloud.cos.model.COSObjectId;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutInstructionFileRequest;
import com.qcloud.cos.model.PutObjectRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;

import com.qcloud.cos.internal.crypto.CryptoConfiguration;
import com.qcloud.cos.internal.crypto.CryptoMode;
import com.qcloud.cos.internal.crypto.CryptoStorageMode;
import com.qcloud.cos.internal.crypto.EncryptionMaterials;
import org.junit.Test;

import static com.qcloud.cos.model.InstructionFileId.DEFAULT_INSTRUCTION_FILE_SUFFIX;
import static org.junit.Assert.fail;

public class SymmetricCOSEncryptionClientWithInstructionCryptoModeTest
        extends AbstractCOSEncryptionClientTest {

    private static void initEncryptionInfo() throws NoSuchAlgorithmException {
        KeyGenerator symKeyGenerator = KeyGenerator.getInstance("AES");
        symKeyGenerator.init(256);
        SecretKey symKey = symKeyGenerator.generateKey();

        encryptionMaterials = new EncryptionMaterials(symKey);
        cryptoConfiguration = new CryptoConfiguration(CryptoMode.AesCtrEncryption)
                .withStorageMode(CryptoStorageMode.InstructionFile);
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
    public void testUploadWithClientEncryption() throws Exception {
        String key = "testEncryptionWithInsMode.txt";
        int inputStreamLength = 1 * 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket , key , inputStream, new ObjectMetadata());
        cosclient.putObject(putObjectRequest);


        COSObjectId cosObjectId = new COSObjectId(bucket, key);

        KeyGenerator symKeyGenerator = KeyGenerator.getInstance("AES");
        symKeyGenerator.init(256);
        SecretKey symKey = symKeyGenerator.generateKey();

        encryptionMaterials = new EncryptionMaterials(symKey);

        PutInstructionFileRequest putInstructionFileRequest = new PutInstructionFileRequest(cosObjectId, encryptionMaterials, DEFAULT_INSTRUCTION_FILE_SUFFIX);

        try {
            ((COSEncryptionClient) cosclient).putInstructionFile(putInstructionFileRequest);
        } catch (Exception e) {
            fail(e.getMessage());
        }finally {
            inputStream.close();
        }
    }

    @Test
    public void testWithRecreateError() throws Exception{
        String key = "testWithRecreateError.txt";
        int inputStreamLength = 1 * 1024 * 1024;
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket , key , inputStream, new ObjectMetadata());
        cosclient.putObject(putObjectRequest);

        COSObjectId cosObjectId = new COSObjectId(bucket, key);

        KeyGenerator symKeyGenerator = KeyGenerator.getInstance("AES");
        symKeyGenerator.init(256);
        SecretKey symKey = symKeyGenerator.generateKey();

        Map<String, String> map = new HashMap<>();
        map.put("testkey", "testvalue");
        PutInstructionFileRequest putInstructionFileRequest = new PutInstructionFileRequest(cosObjectId, map, DEFAULT_INSTRUCTION_FILE_SUFFIX);

        try {
            ((COSEncryptionClient) cosclient).putInstructionFile(putInstructionFileRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            inputStream.close();
        }
    }
}
