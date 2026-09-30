package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;

import com.qcloud.cos.model.CosServiceResult;

/**
 * Put/Get Space Quota 的返回结果。
 *
 * @since 5.6.283
 */
public class SpaceQuotaResult extends CosServiceResult implements Serializable {
    /** 配额的唯一标识符 */
    private String quotaId;
    /** 配额容量，单位字节 */
    private Long capacity;

    public String getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(String quotaId) {
        this.quotaId = quotaId;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "SpaceQuotaResult{quotaId='" + quotaId + "', capacity=" + capacity
                + ", requestId='" + getRequestId() + "'}";
    }
}
