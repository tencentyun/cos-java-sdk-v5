package com.qcloud.cos.demo.ci;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.ciModel.common.MediaVod;
import com.qcloud.cos.model.ciModel.job.*;
import com.qcloud.cos.model.ciModel.job.v2.GetPlayListRequest;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobResponseV2;
import com.qcloud.cos.model.ciModel.job.v2.MediaJobsRequestV2;
import com.qcloud.cos.utils.Jackson;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 媒体处理 边转边播接口相关demo
 */
public class GeneratePlayListDemo {
    private static String appId = "123456789";
    private static String bucket = "demo-123456789";
    private static String objectKey = "test.m3u8";
    private static String expires = "3600";
    private static byte[] secret = "YourSecret".getBytes();

    public static void main(String[] args) throws Exception {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSClient client = ClientUtils.getTestClient();
        // 2 调用要使用的方法。
        getPlayListSimple(client);
    }

    /**
     * generatePlayList 提交生成播放列表任务
     */
    public static void generatePlayList(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        request.setBucketName(bucket);
        //2.添加请求参数 参数详情请见api接口文档
        request.setTag("GeneratePlayList");
        request.getInput().setObject(objectKey);
        MediaContainerObject container = request.getOperation().getTranscode().getContainer();
        container.setFormat("hls");
        container.getClipConfig().setDuration("5");
        MediaTransConfigObject transConfig = request.getOperation().getTranscode().getTransConfig();
        transConfig.setCosTag("DemoTag=demo1&DemoTag2=demo2");
        transConfig.getHlsEncrypt().setIsHlsEncrypt("true");
        MediaTranscodeVideoObject video = request.getOperation().getTranscode().getVideo();
        video.setCodec("H.264");
        video.setWidth("1280");
        video.setHeight("960");

        request.getOperation().getOutput().setBucket("demo-1234567890");
        request.getOperation().getOutput().setRegion("ap-beijing");
        request.getOperation().getOutput().setObject("output/media/test.m3u8");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.createMediaJobsV2(request);
        System.out.println(Jackson.toJsonString(response));
    }

    /**
     * describeMediaJob 根据jobId查询任务信息
     */
    public static void describeMediaJob(COSClient client) {
        //1.创建任务请求对象
        MediaJobsRequestV2 request = new MediaJobsRequestV2();
        //2.添加请求参数 参数详情请见api接口文档
        request.setBucketName(bucket);
        request.setJobId("j8b360cd0142511efac6425779c0*****");
        //3.调用接口,获取任务响应对象
        MediaJobResponseV2 response = client.describeMediaJobV2(request);
        System.out.println(Jackson.toJsonString(response));
    }



    public static void getPlayList(COSClient client)  {
        String bucketName = bucket;
        String key = objectKey;
        // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
        Instant now = Instant.now();
        Instant expire = now.plus(Long.parseLong(expires), ChronoUnit.SECONDS);
        Date expirationDate = Date.from(expire);
        Map<String, String> params = new HashMap<String, String>();
        params.put("ci-process", "getplaylist");
        params.put("expires", "43200");
        Map<String, String> headers = new HashMap<String, String>();

        HttpMethodName method = HttpMethodName.GET;
        URL url = client.generatePresignedUrl(bucketName, key, expirationDate, method, headers, params);
        System.out.println(url.toString());
    }

    public static void getPlayListSimple(COSClient client) throws UnsupportedEncodingException {
        String bucketName = bucket;
        String key = objectKey;
        // 设置签名过期时间(可选), 若未进行设置则默认使用 ClientConfig 中的签名过期时间(1小时)
        Instant now = Instant.now();
        Instant expire = now.plus(Long.parseLong(expires), ChronoUnit.SECONDS);
        Date expirationDate = Date.from(expire);
        String token = generateToken(appId, bucket, objectKey, secret, expirationDate);
        Map<String, String> params = new HashMap<String, String>();
        params.put("ci-process", "getplaylist");
        params.put("expires", "43200");
        params.put("token-type", "JwtToken");
        params.put("token", token);
        Map<String, String> headers = new HashMap<String, String>();

        HttpMethodName method = HttpMethodName.GET;
        URL url = client.generatePresignedUrl(bucketName, key, expirationDate, method, headers, params);
        System.out.println(url.toString());
    }

    public static String generateToken(String appId, String bucketId, String objectKey, byte[] secret, Date expires) throws UnsupportedEncodingException {
        Instant now = Instant.now();
        String encodedObjectKey;
        encodedObjectKey = URLEncoder.encode(objectKey, "UTF-8");

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create().withIssuer("client")
                .withIssuedAt(Date.from(now))
                .withExpiresAt(expires)
                .withClaim("Type", "CosCiToken")
                .withClaim("AppId", appId)
                .withClaim("BucketId", bucketId)
                .withClaim("Object", encodedObjectKey)
                .withClaim("Issuer", "client")
                .withClaim("IssuedTimeStamp", now.getEpochSecond())
                .withClaim("ExpireTimeStamp", expires.getTime() / 1000)
                .withClaim("UsageLimit", 20)
                .withClaim("ProtectSchema", "rsa1024")
//          .withClaim("PublicKey", "xxx")
                .withClaim("ProtectContentKey", 0);
        return builder.sign(algorithm);
    }

}
