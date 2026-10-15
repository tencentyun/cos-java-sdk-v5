package com.qcloud.cos.internal;

/**
 * AgentBucket 递归/移动类接口的响应解析契约。
 * <p>
 * 这类接口在执行期间出错时仍返回 HTTP 200，错误信息以 &lt;Error&gt; 作为响应体返回，
 * 解析器需识别该情况，由 COSClient 转换为 CosServiceException。
 * </p>
 *
 * @since 5.6.283
 */
public interface AgentBucketErrorAware {

    /** 响应体顶层是否为 Error 节点 */
    boolean isErrorResponse();

    String getErrorCode();

    String getErrorMessage();

    /** Error/Resource，出错的资源路径 */
    String getErrorResource();

    /** Error/RequestId，为空时使用响应头中的 requestId */
    String getErrorRequestId();

    /** 响应头 x-cos-request-id，由 {@link RequestIdHeaderHandler} 写入 */
    String getHeaderRequestId();

    void setHeaderRequestId(String requestId);
}
