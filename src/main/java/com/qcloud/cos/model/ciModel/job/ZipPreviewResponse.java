package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.model.CosServiceResult;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

/**
 * 文件处理请求类
 */
@XStreamAlias("Response")
public class ZipPreviewResponse extends CosServiceResult {
    @XStreamAlias("FileNumber")
    private int fileNumber;

    @XStreamAlias("IsTruncated")
    private boolean isTruncated;

    @XStreamImplicit(keyFieldName = "Contents")
    private List<Content> contents;

    @XStreamAlias("Contents")
    public static class Content {

        @XStreamAlias("Key")
        private String key;

        @XStreamAlias("LastModified")
        private String lastModified;

        @XStreamAlias("UncompressedSize")
        private long uncompressedSize;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getLastModified() {
            return lastModified;
        }

        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
        }

        public long getUncompressedSize() {
            return uncompressedSize;
        }

        public void setUncompressedSize(long uncompressedSize) {
            this.uncompressedSize = uncompressedSize;
        }
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(int fileNumber) {
        this.fileNumber = fileNumber;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public void setTruncated(boolean truncated) {
        isTruncated = truncated;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
