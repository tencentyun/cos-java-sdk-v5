package com.qcloud.cos.demo;

import java.io.ByteArrayInputStream;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.CopyObjectResult;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.agentbucket.ConflictResolutionStrategy;
import com.qcloud.cos.model.agentbucket.CopyObjectRecursiveRequest;
import com.qcloud.cos.model.agentbucket.DeleteObjectRecursiveRequest;
import com.qcloud.cos.model.agentbucket.DeleteObjectRecursiveResult;
import com.qcloud.cos.model.agentbucket.GetSpaceExtensionRequest;
import com.qcloud.cos.model.agentbucket.GetSpaceQuotaRequest;
import com.qcloud.cos.model.agentbucket.MoveObjectRequest;
import com.qcloud.cos.model.agentbucket.MoveObjectResult;
import com.qcloud.cos.model.agentbucket.PutSpaceExtensionRequest;
import com.qcloud.cos.model.agentbucket.PutSpaceQuotaRequest;
import com.qcloud.cos.model.agentbucket.SpaceExtensionResult;
import com.qcloud.cos.model.agentbucket.SpaceQuotaResult;
import com.qcloud.cos.region.Region;

/**
 * 智能体存储桶（AgentBucket）增强 API 示例。
 * <p>
 * API 文档：https://cloud.tencent.com/document/product/436/133858
 * </p>
 * 注意事项：
 * <ul>
 * <li>以下接口仅适用于智能体存储桶</li>
 * <li>SpaceId 对应 ObjectKey 的一级目录，需先上传一个空的一级目录对象（如 "my-space/"）创建</li>
 * <li>Copy/Move/Delete 递归接口为同步接口，目录较大时请调大 socketTimeout</li>
 * <li>执行期间出错时服务端仍返回 HTTP 200，错误在响应体中；SDK 会将其转换为 CosServiceException（statusCode=200）</li>
 * <li>SDK 默认会对 5xx 与网络超时自动重试。递归/移动操作非幂等，重试后可能出现冲突或 404，
 * 捕获异常后请以实际对象状态为准</li>
 * </ul>
 */
public class AgentBucketDemo {
    // 密钥从环境变量读取，切勿硬编码
    private static final String SECRET_ID = System.getenv("COS_SECRET_ID");
    private static final String SECRET_KEY = System.getenv("COS_SECRET_KEY");
    // 存储桶名称，格式 BucketName-APPID
    private static final String BUCKET = "examplebucket-1250000000";
    private static final String REGION = "ap-chengdu";
    private static final String SPACE_ID = "my-space";

    public static void main(String[] args) {
        COSClient cosClient = createCOSClient();
        try {
            createSpace(cosClient);
            putGetSpaceQuota(cosClient);
            putGetSpaceExtension(cosClient);
            copyObjectRecursive(cosClient);
            moveObject(cosClient);
            moveObjectRecursive(cosClient);
            deleteObjectRecursive(cosClient);
        } catch (CosServiceException e) {
            // 服务端错误：包括 HTTP 200 + Error 响应体的情况
            System.out.println("errorCode: " + e.getErrorCode() + ", statusCode: " + e.getStatusCode()
                    + ", requestId: " + e.getRequestId() + ", message: " + e.getErrorMessage());
        } catch (CosClientException e) {
            System.out.println("client error: " + e.getMessage());
        } finally {
            cosClient.shutdown();
        }
    }

    private static COSClient createCOSClient() {
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        ClientConfig clientConfig = new ClientConfig(new Region(REGION));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 递归操作为同步接口，目录较大时调大读超时（默认 30 秒）
        clientConfig.setSocketTimeout(300 * 1000);
        return new COSClient(cred, clientConfig);
    }

    /** 创建 SpaceId：上传一个空的一级目录对象 */
    private static void createSpace(COSClient cosClient) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        cosClient.putObject(new PutObjectRequest(BUCKET, SPACE_ID + "/",
                new ByteArrayInputStream(new byte[0]), metadata));
    }

    /** 设置/查询空间配额，单位字节 */
    private static void putGetSpaceQuota(COSClient cosClient) {
        long capacity = 1024L * 1024 * 1024 * 1024; // 1TB
        SpaceQuotaResult putResult = cosClient.putSpaceQuota(new PutSpaceQuotaRequest(BUCKET, SPACE_ID, capacity));
        System.out.println("put space quota: " + putResult);

        SpaceQuotaResult getResult = cosClient.getSpaceQuota(new GetSpaceQuotaRequest(BUCKET, SPACE_ID));
        System.out.println("quotaId: " + getResult.getQuotaId() + ", capacity: " + getResult.getCapacity());
    }

    /** 设置/查询空间扩展属性 */
    private static void putGetSpaceExtension(COSClient cosClient) {
        cosClient.putSpaceExtension(new PutSpaceExtensionRequest(BUCKET, SPACE_ID, "personal"));

        SpaceExtensionResult result = cosClient.getSpaceExtension(new GetSpaceExtensionRequest(BUCKET, SPACE_ID));
        System.out.println("isPublicRead: " + result.getIsPublicRead()
                + ", recognizeSensitiveContent: " + result.getRecognizeSensitiveContent()
                + ", spaceTag: " + result.getSpaceTag());
    }

    /** 递归复制目录：my-space/aaa -> my-space/bbb，目标已存在时自动重命名 */
    private static void copyObjectRecursive(COSClient cosClient) {
        CopyObjectRecursiveRequest request = new CopyObjectRecursiveRequest(
                BUCKET, SPACE_ID + "/aaa", BUCKET, SPACE_ID + "/bbb")
                .withConflictResolutionStrategy(ConflictResolutionStrategy.RENAME);
        CopyObjectResult result = cosClient.copyObjectRecursive(request);
        System.out.println("copy recursive etag: " + result.getETag() + ", requestId: " + result.getRequestId());
    }

    /** 移动单个对象：目标文件已存在时覆盖（overwrite 仅非递归 Move 支持） */
    private static void moveObject(COSClient cosClient) {
        MoveObjectRequest request = new MoveObjectRequest(BUCKET, SPACE_ID + "/a.txt", SPACE_ID + "/b.txt")
                .withConflictResolutionStrategy(ConflictResolutionStrategy.OVERWRITE);
        MoveObjectResult result = cosClient.moveObject(request);
        System.out.println("move object: " + result);
    }

    /** 递归移动目录：默认 ask 策略，目标已存在时抛出冲突异常 */
    private static void moveObjectRecursive(COSClient cosClient) {
        MoveObjectRequest request = new MoveObjectRequest(BUCKET, SPACE_ID + "/bbb", SPACE_ID + "/ccc");
        try {
            MoveObjectResult result = cosClient.moveObjectRecursive(request);
            System.out.println("move recursive: " + result);
        } catch (CosServiceException e) {
            if ("FileAlreadyExists".equals(e.getErrorCode())
                    || "SameNameDirectoryOrFileExists".equals(e.getErrorCode())) {
                System.out.println("destination already exists, requestId: " + e.getRequestId());
            } else {
                throw e;
            }
        }
    }

    /**
     * 递归删除目录，删除后无法恢复。
     * 目录 key 必须以 "/" 结尾；不带 "/" 时服务端按同名文件处理，返回成功但目录不会被删除。
     */
    private static void deleteObjectRecursive(COSClient cosClient) {
        DeleteObjectRecursiveResult result =
                cosClient.deleteObjectRecursive(new DeleteObjectRecursiveRequest(BUCKET, SPACE_ID + "/ccc/"));
        System.out.println("deleted keys: " + result.getDeletedKeys());
        if (result.hasErrors()) {
            System.out.println("partially failed: " + result.getErrors());
        }
    }
}
