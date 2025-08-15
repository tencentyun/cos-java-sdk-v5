package com.qcloud.cos.demo.ci;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ciModel.snapshot.PrivateM3U8Request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class generatePrivateM3U8UrlDemo {
    private static String appId = "123456789";
    private static String bucket = "demo-123456789";
    private static String objectKey = "test.m3u8";
    private static String expires = "3600";
    // 播放密钥：到 对象存储控制台，桶详情页面的数据处理>媒体处理，在上方选中媒体处理页签。编辑开启媒体处理，并生成和复制播放密钥。用于后续 token 服务搭建。
    private static byte[] secret = "YourSecret".getBytes();

    public static String  generateCosDomainPrivateM3U8Url(COSClient client) throws UnsupportedEncodingException {
        PrivateM3U8Request request = new PrivateM3U8Request();
        request.setBucketName(bucket);
        request.setObject(objectKey);
        request.setExpires(expires);
        request.setTokenType("JwtToken");
        String token = generateToken(appId, bucket, objectKey, secret,expires);
        request.setToken(token);
        return client.generateCosDomainPrivateM3U8Url(request);
    }

    public static String generateToken(String appId, String bucketId, String objectKey, byte[] secret, String expires) throws UnsupportedEncodingException {
        Instant now = Instant.now();
        Instant expire = now.plus(Long.parseLong(expires), ChronoUnit.SECONDS);
        String encodedObjectKey;
        encodedObjectKey = URLEncoder.encode(objectKey, "UTF-8");

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create().withIssuer("client")
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(expire))
            .withClaim("Type", "CosCiToken")
            .withClaim("AppId", appId)
            .withClaim("BucketId", bucketId)
            .withClaim("Object", encodedObjectKey)
            .withClaim("Issuer", "client")
            .withClaim("IssuedTimeStamp", now.getEpochSecond())
            .withClaim("ExpireTimeStamp", expire.getEpochSecond())
            .withClaim("UsageLimit", 20)
            .withClaim("ProtectSchema", "rsa1024")
//          .withClaim("PublicKey", "xxx")
            .withClaim("ProtectContentKey", 0);
        return builder.sign(algorithm);
    }


}
