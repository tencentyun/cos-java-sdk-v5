package com.qcloud.cos.model.agentbucket;

import java.io.Serializable;
import java.util.Date;

import com.qcloud.cos.model.CosServiceResult;

/**
 * Move Object / Move Object Recursive 的返回结果（目标对象信息）。
 *
 * @since 5.6.283
 */
public class MoveObjectResult extends CosServiceResult implements Serializable {
    /** 目标对象 ETag（已去除首尾双引号） */
    private String eTag;
    /** 目标对象最后修改时间；服务端返回格式无法解析时为 null，原始值见 {@link #getLastModifiedStr()} */
    private Date lastModified;
    /** 目标对象最后修改时间原始字符串（RFC 3339） */
    private String lastModifiedStr;
    /** 目标对象 CRC64 */
    private String crc64Ecma;

    public String getETag() {
        return eTag;
    }

    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedStr() {
        return lastModifiedStr;
    }

    public void setLastModifiedStr(String lastModifiedStr) {
        this.lastModifiedStr = lastModifiedStr;
    }

    public String getCrc64Ecma() {
        return crc64Ecma;
    }

    public void setCrc64Ecma(String crc64Ecma) {
        this.crc64Ecma = crc64Ecma;
    }

    @Override
    public String toString() {
        return "MoveObjectResult{eTag='" + eTag + "', lastModified='" + lastModifiedStr
                + "', crc64Ecma='" + crc64Ecma + "', requestId='" + getRequestId() + "'}";
    }
}
