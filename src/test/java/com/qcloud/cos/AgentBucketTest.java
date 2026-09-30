package com.qcloud.cos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.exception.CosServiceException;
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
 * AgentBucket 增强 API 真机测试。
 * <p>
 * 凭证来源（任一）：环境变量 agentSecretId / agentSecretKey / agentBucket / agentRegion，
 * 或工作目录下 ut_account.prop 中的同名配置。缺失时整类跳过。
 * 每次运行使用独立的 SpaceId，结束后递归删除清理。
 * </p>
 */
public class AgentBucketTest {
    private static final List<String> CONFLICT_CODES =
            Arrays.asList("FileAlreadyExists", "SameNameDirectoryOrFileExists");

    private static String secretId;
    private static String secretKey;
    private static String bucket;
    private static String region;
    private static COSClient client;

    private String space;

    @BeforeClass
    public static void setUpClass() throws IOException {
        secretId = System.getenv("agentSecretId");
        secretKey = System.getenv("agentSecretKey");
        bucket = System.getenv("agentBucket");
        region = System.getenv("agentRegion");
        File propFile = new File("ut_account.prop");
        if (propFile.exists() && propFile.canRead()) {
            Properties prop = new Properties();
            InputStream in = new FileInputStream(propFile);
            try {
                prop.load(in);
            } finally {
                in.close();
            }
            secretId = propOrDefault(prop, "agentSecretId", secretId);
            secretKey = propOrDefault(prop, "agentSecretKey", secretKey);
            bucket = propOrDefault(prop, "agentBucket", bucket);
            region = propOrDefault(prop, "agentRegion", region);
        }
        Assume.assumeTrue("AgentBucket 测试凭证缺失，跳过真机测试",
                notBlank(secretId) && notBlank(secretKey) && notBlank(bucket) && notBlank(region));
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 递归操作为同步接口，目录较大时需调大读超时
        clientConfig.setSocketTimeout(120 * 1000);
        client = new COSClient(new BasicCOSCredentials(secretId, secretKey), clientConfig);
    }

    private static String propOrDefault(Properties prop, String name, String defaultValue) {
        String value = prop.getProperty(name);
        return notBlank(value) ? value.trim() : defaultValue;
    }

    private static boolean notBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    @AfterClass
    public static void tearDownClass() {
        if (client != null) {
            client.shutdown();
        }
    }

    @Before
    public void setUp() {
        space = "sdk-ut-" + System.currentTimeMillis();
        // SpaceId 需预先上传空的一级目录对象创建
        putEmpty(space + "/");
    }

    @After
    public void tearDown() {
        try {
            client.deleteObjectRecursive(new DeleteObjectRecursiveRequest(bucket, space + "/"));
        } catch (Exception e) {
            System.out.println("cleanup failed, space: " + space + ", error: " + e.getMessage());
        }
    }

    private void putEmpty(String key) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        client.putObject(new PutObjectRequest(bucket, key, new ByteArrayInputStream(new byte[0]), metadata));
    }

    private void putText(String key, String content) {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        client.putObject(new PutObjectRequest(bucket, key, new ByteArrayInputStream(bytes), metadata));
    }

    private void buildTree(String dir) {
        putEmpty(dir + "/");
        putText(dir + "/a.txt", "hello agent bucket");
        putEmpty(dir + "/sub/");
        putText(dir + "/sub/b.txt", "hello sub");
    }

    @Test
    public void testSpaceQuota() {
        long capacity = 1024L * 1024 * 1024;
        SpaceQuotaResult putResult = client.putSpaceQuota(new PutSpaceQuotaRequest(bucket, space, capacity));
        assertEquals(Long.valueOf(capacity), putResult.getCapacity());
        assertNotNull(putResult.getQuotaId());
        assertNotNull(putResult.getRequestId());

        SpaceQuotaResult getResult = client.getSpaceQuota(new GetSpaceQuotaRequest(bucket, space));
        assertEquals(Long.valueOf(capacity), getResult.getCapacity());
        assertEquals(putResult.getQuotaId(), getResult.getQuotaId());
    }

    @Test
    public void testSpaceExtension() {
        client.putSpaceExtension(new PutSpaceExtensionRequest(bucket, space, "personal"));
        SpaceExtensionResult result = client.getSpaceExtension(new GetSpaceExtensionRequest(bucket, space));
        assertEquals("personal", result.getSpaceTag());
        assertNotNull(result.getRequestId());
    }

    @Test
    public void testCopyObjectRecursive() {
        String src = space + "/src";
        String dst = space + "/copy";
        buildTree(src);

        CopyObjectResult result = client.copyObjectRecursive(new CopyObjectRecursiveRequest(bucket, src, bucket, dst));
        assertNotNull(result.getRequestId());
        assertTrue(client.doesObjectExist(bucket, dst + "/a.txt"));
        assertTrue(client.doesObjectExist(bucket, dst + "/sub/b.txt"));
        assertTrue("复制不应删除源", client.doesObjectExist(bucket, src + "/a.txt"));

        // 默认 ask：目标已存在时报冲突（409 或 HTTP 200 + Error）
        try {
            client.copyObjectRecursive(new CopyObjectRecursiveRequest(bucket, src, bucket, dst)
                    .withConflictResolutionStrategy(ConflictResolutionStrategy.ASK));
            fail("目标已存在时应抛出冲突异常");
        } catch (CosServiceException e) {
            assertTrue("unexpected error code: " + e.getErrorCode(), CONFLICT_CODES.contains(e.getErrorCode()));
            assertNotNull(e.getRequestId());
        }

        // rename：自动重命名，不报错
        client.copyObjectRecursive(new CopyObjectRecursiveRequest(bucket, src, bucket, dst)
                .withConflictResolutionStrategy(ConflictResolutionStrategy.RENAME));
    }

    @Test
    public void testMoveObject() {
        String src = space + "/file.txt";
        String dst = space + "/moved.txt";
        putText(src, "move me");
        putText(dst, "old");

        MoveObjectResult result = client.moveObject(new MoveObjectRequest(bucket, src, dst)
                .withConflictResolutionStrategy(ConflictResolutionStrategy.OVERWRITE));
        assertNotNull(result.getRequestId());
        assertFalse(client.doesObjectExist(bucket, src));
        assertTrue(client.doesObjectExist(bucket, dst));
    }

    @Test
    public void testMoveObjectRecursive() {
        String src = space + "/src";
        String dst = space + "/moved-dir";
        buildTree(src);

        MoveObjectResult result = client.moveObjectRecursive(new MoveObjectRequest(bucket, src, dst));
        assertNotNull(result.getRequestId());
        assertTrue(client.doesObjectExist(bucket, dst + "/sub/b.txt"));
        assertFalse(client.doesObjectExist(bucket, src + "/a.txt"));

        // 目标已存在：再建一个源目录移动到同一目标，期望冲突
        String src2 = space + "/src2";
        buildTree(src2);
        try {
            client.moveObjectRecursive(new MoveObjectRequest(bucket, src2, dst));
            fail("目标已存在时应抛出冲突异常");
        } catch (CosServiceException e) {
            assertTrue("unexpected error code: " + e.getErrorCode(), CONFLICT_CODES.contains(e.getErrorCode()));
        }
    }

    @Test
    public void testDeleteObjectRecursive() {
        String dir = space + "/to-delete";
        buildTree(dir);

        // 删除非一级目录时 key 必须以 "/" 结尾；不带 "/" 时服务端按同名文件处理，返回成功但目录不会被删除
        DeleteObjectRecursiveResult result =
                client.deleteObjectRecursive(new DeleteObjectRecursiveRequest(bucket, dir + "/"));
        assertNotNull(result.getRequestId());
        assertFalse(result.hasErrors());
        assertFalse(client.doesObjectExist(bucket, dir + "/"));
        assertFalse(client.doesObjectExist(bucket, dir + "/a.txt"));
        assertFalse(client.doesObjectExist(bucket, dir + "/sub/b.txt"));
    }

    @Test
    public void testDeleteSingleFileRecursive() {
        String file = space + "/single.txt";
        putText(file, "single");
        client.deleteObjectRecursive(new DeleteObjectRecursiveRequest(bucket, file));
        assertFalse(client.doesObjectExist(bucket, file));
    }
}
