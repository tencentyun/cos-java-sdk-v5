package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FileHashCodeResult {
    @XStreamAlias("MD5")
    private String md5;

    @XStreamAlias("SHA1")
    private String sha1;

    @XStreamAlias("SHA256")
    private String sha256;

    @XStreamAlias("FileSize")
    private String fileSize;

    @XStreamAlias("LastModified")
    private String lastModified;

    /**
     * HTTP1.1规定 ETag需要放在双引号内 该值内容为"xxxxxx"
     */
    @XStreamAlias("ETag")
    private String etag;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileHashCodeResult{");
        sb.append("md5='").append(md5).append('\'');
        sb.append(", sha1='").append(sha1).append('\'');
        sb.append(", sha256='").append(sha256).append('\'');
        sb.append(", fileSize='").append(fileSize).append('\'');
        sb.append(", lastModified='").append(lastModified).append('\'');
        sb.append(", etag='").append(etag).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
