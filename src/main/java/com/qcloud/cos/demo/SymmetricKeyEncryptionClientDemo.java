package com.qcloud.cos.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.qcloud.cos.COSEncryptionClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSStaticCredentialsProvider;
import com.qcloud.cos.internal.crypto.CryptoConfiguration;
import com.qcloud.cos.internal.crypto.CryptoMode;
import com.qcloud.cos.internal.crypto.CryptoStorageMode;
import com.qcloud.cos.internal.crypto.EncryptionMaterials;
import com.qcloud.cos.internal.crypto.StaticEncryptionMaterialsProvider;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

//使用客户端加密前的注意事项请参考接口文档
//这里给出使用对称秘钥AES256加密每次生成的随机对称秘钥
public class SymmetricKeyEncryptionClientDemo {
    private static final String keyFilePath = "secretFolder/secret.key";

    // 这里给出了一个产生和保存秘钥信息的示例, 推荐使用256位秘钥.
    public static void buildAndSaveSymmetricKey() throws IOException, NoSuchAlgorithmException {
        // Generate symmetric 256 bit AES key.
        KeyGenerator symKeyGenerator = KeyGenerator.getInstance("AES");
        // JDK默认不支持256位长度的AES秘钥, SDK内部默认使用AES256加密数据
        // 运行时会打印如下异常信息java.security.InvalidKeyException: Illegal key size or default parameters
        // 解决办法参考接口文档的FAQ
        symKeyGenerator.init(256);
        SecretKey symKey = symKeyGenerator.generateKey();

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(symKey.getEncoded());
        FileOutputStream keyfos = new FileOutputStream(keyFilePath);
        keyfos.write(x509EncodedKeySpec.getEncoded());
        keyfos.close();
    }

    // 这里给出了一个加载秘钥的示例
    public static SecretKey loadSymmetricAESKey() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidKeyException {
        // Read private key from file.
        File keyFile = new File(keyFilePath);
        FileInputStream keyfis = new FileInputStream(keyFile);
        byte[] encodedPrivateKey = new byte[(int) keyFile.length()];
        keyfis.read(encodedPrivateKey);
        keyfis.close();

        // Generate secret key.
        return new SecretKeySpec(encodedPrivateKey, "AES");
    }

    public static void main(String[] args) throws Exception {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXXXXXXXXXX",
                "YYZZZZZZZZZZZZZZZZZ");
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));


        // 加载保存在文件中的秘钥, 如果不存在，请先使用buildAndSaveSymmetricKey生成秘钥
        // buildAndSaveSymmetricKey();
        SecretKey symKey = loadSymmetricAESKey();

        EncryptionMaterials encryptionMaterials = new EncryptionMaterials(symKey);
        // 使用AES/GCM模式，并将加密信息存储在文件元信息中.
        CryptoConfiguration cryptoConf = new CryptoConfiguration(CryptoMode.AuthenticatedEncryption)
                .withStorageMode(CryptoStorageMode.ObjectMetadata);

        // 生成加密客户端EncryptionClient, COSEncryptionClient是COSClient的子类, 所有COSClient支持的接口他都支持。
        // EncryptionClient覆盖了COSClient上传下载逻辑，操作内部会执行加密操作，其他操作执行逻辑和COSClient一致
        COSEncryptionClient cosEncryptionClient =
                new COSEncryptionClient(new COSStaticCredentialsProvider(cred),
                        new StaticEncryptionMaterialsProvider(encryptionMaterials), clientConfig,
                        cryptoConf);

        // 上传文件
        // 这里给出putObject的示例, 对于高级API上传，只用在生成TransferManager时传入COSEncryptionClient对象即可
        String bucketName = "mybucket-1251668577";
        String key = "xxx/yyy/zzz.txt";
        File localFile = new File("src/test/resources/plain.txt");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        cosEncryptionClient.putObject(putObjectRequest);

        // 下载文件
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        File downloadFile = new File("src/test/resources/downPlain.txt");
        cosEncryptionClient.getObject(getObjectRequest, downloadFile);

        // 删除文件
        cosEncryptionClient.deleteObject(bucketName, key);

        // 关闭
        cosEncryptionClient.shutdown();
    }

}
