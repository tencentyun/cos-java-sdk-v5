package com.qcloud.cos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.CosHttpClient;
import com.qcloud.cos.http.CosHttpRequest;
import com.qcloud.cos.http.CosHttpResponse;
import com.qcloud.cos.http.HttpResponseHandler;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.internal.CosServiceResponse;

/**
 * AgentBucket 离线测试用的 CosHttpClient 桩：替换 COSClient 的真实 HTTP 层。
 * <ul>
 * <li>记录每次请求，读取请求体便于断言</li>
 * <li>响应以 HTTP 200 + 给定响应体走真实的 handler 链，覆盖解析与 200+Error 转异常</li>
 * <li>可按调用顺序排队多个响应；队列项为 RuntimeException 时直接抛出，用于模拟服务端/客户端异常</li>
 * </ul>
 */
public class AgentBucketStubHttpClient implements CosHttpClient {
    public final List<CosHttpRequest<?>> requests = new ArrayList<CosHttpRequest<?>>();
    public CosHttpRequest<?> lastRequest;
    public String lastBody;
    /** 队列为空时使用的默认响应体 */
    public String responseBody = "";
    public Map<String, String> responseHeaders = new HashMap<String, String>();
    /** 为 true 时 exeute 直接返回 null，用于覆盖调用方的空结果兜底 */
    public boolean returnNull = false;

    private final Deque<Object> responses = new ArrayDeque<Object>();

    /** 将桩注入到 COSClient，替换并关闭原有的 DefaultCosHttpClient */
    public static AgentBucketStubHttpClient install(COSClient client) throws Exception {
        AgentBucketStubHttpClient stub = new AgentBucketStubHttpClient();
        Field field = COSClient.class.getDeclaredField("cosHttpClient");
        field.setAccessible(true);
        ((CosHttpClient) field.get(client)).shutdown();
        field.set(client, stub);
        return stub;
    }

    public AgentBucketStubHttpClient enqueue(String body) {
        responses.addLast(body);
        return this;
    }

    public AgentBucketStubHttpClient enqueueException(RuntimeException exception) {
        responses.addLast(exception);
        return this;
    }

    @Override
    public <X, Y extends CosServiceRequest> X exeute(CosHttpRequest<Y> request,
            HttpResponseHandler<CosServiceResponse<X>> responseHandler) {
        requests.add(request);
        lastRequest = request;
        lastBody = readBody(request.getContent());
        Object next = responses.isEmpty() ? responseBody : responses.pollFirst();
        if (next instanceof RuntimeException) {
            throw (RuntimeException) next;
        }
        if (returnNull) {
            return null;
        }
        CosHttpResponse response = new CosHttpResponse(request, null);
        response.setStatusCode(200);
        response.setContent(new ByteArrayInputStream(((String) next).getBytes(StandardCharsets.UTF_8)));
        response.setHeaders(new HashMap<String, String>(responseHeaders));
        try {
            return responseHandler.handle(response).getResult();
        } catch (CosClientException e) {
            throw e;
        } catch (Exception e) {
            throw new CosClientException("stub handle failed", e);
        }
    }

    private static String readBody(InputStream in) {
        if (in == null) {
            return null;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void shutdown() {
    }
}
