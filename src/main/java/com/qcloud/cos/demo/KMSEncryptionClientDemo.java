package com.qcloud.cos.demo;

import java.io.File;

import com.qcloud.cos.COSEncryptionClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.auth.COSStaticCredentialsProvider;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.internal.crypto.CryptoConfiguration;
import com.qcloud.cos.internal.crypto.CryptoMode;
import com.qcloud.cos.internal.crypto.CryptoStorageMode;
import com.qcloud.cos.internal.crypto.KMSEncryptionMaterials;
import com.qcloud.cos.internal.crypto.KMSEncryptionMaterialsProvider;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;

public class KMSEncryptionClientDemo {
    public static void main(String[] args) throws Exception {
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials("AKIDXXXXXXXXXXXXXXXX",
                "YYZZZZZZZZZZZZZZZZZ");
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing-1"));
        // 为防止请求头部被篡改导致的数据无法解密，强烈建议只使用 https 协议发起请求
        clientConfig.setHttpProtocol(HttpProtocol.https);

        // 用户 KMS 服务的主密钥
        String cmk = "XXXXXXX";
        //// 如果 KMS 服务的 region 与 cos 不一致则需要单独设置。
        //String kmsRegion = "XXXXX";

        // 初始化 KMS 加密材料
        KMSEncryptionMaterials encryptionMaterials = new KMSEncryptionMaterials(cmk);
        // 使用AES/GCM模式，并将加密信息存储在文件元信息中.
        CryptoConfiguration cryptoConf = new CryptoConfiguration(CryptoMode.AuthenticatedEncryption)
                .withStorageMode(CryptoStorageMode.ObjectMetadata);

        //// 如果 kms 服务的 region 与 cos 的 region 不一致，则在加密信息里指定 kms 服务的 region
        //cryptoConf.setKmsRegion(kmsRegion);

        //// 如果需要可以为 KMS 服务的 cmk 设置对应的描述信息。
        // encryptionMaterials.addDescription("yourDescKey", "yourDescValue");

        // 生成加密客户端EncryptionClient, COSEncryptionClient是COSClient的子类, 所有COSClient支持的接口他都支持。
        // EncryptionClient覆盖了COSClient上传下载逻辑，操作内部会执行加密操作，其他操作执行逻辑和COSClient一致
        COSEncryptionClient cosEncryptionClient =
                new COSEncryptionClient(new COSStaticCredentialsProvider(cred),
                        new KMSEncryptionMaterialsProvider(encryptionMaterials), clientConfig,
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
