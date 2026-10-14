package com.qcloud.cos.model.agentbucket;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.internal.XmlWriter;

/**
 * AgentBucket 增强 API 请求体 XML 生成器。值经 {@link XmlWriter} 转义，防止 XML 注入。
 *
 * @since 5.6.283
 */
public class AgentBucketXmlFactory {

    /**
     * 生成 Put Space Quota 请求体：
     * <pre>
     * &lt;SpaceQuota&gt;&lt;Capacity&gt;1099511627776&lt;/Capacity&gt;&lt;/SpaceQuota&gt;
     * </pre>
     */
    public byte[] convertToXmlByteArray(PutSpaceQuotaRequest request) throws CosClientException {
        XmlWriter xml = new XmlWriter();
        xml.start("SpaceQuota");
        xml.start("Capacity").value(String.valueOf(request.getCapacity())).end();
        xml.end();
        return xml.getBytes();
    }

    /**
     * 生成 Put Space Extension 请求体：
     * <pre>
     * &lt;SpaceExtension&gt;&lt;SpaceTag&gt;personal&lt;/SpaceTag&gt;&lt;/SpaceExtension&gt;
     * </pre>
     */
    public byte[] convertToXmlByteArray(PutSpaceExtensionRequest request) throws CosClientException {
        XmlWriter xml = new XmlWriter();
        xml.start("SpaceExtension");
        xml.start("SpaceTag").value(request.getSpaceTag()).end();
        xml.end();
        return xml.getBytes();
    }
}
