package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class FileProcessOperation {
    @XStreamAlias("FileCompressConfig")
    private FileCompressConfig fileCompressConfig;

    @XStreamAlias("FileUncompressConfig")
    private FileUnCompressConfig fileUnCompressConfig;

    @XStreamAlias("FileHashCodeConfig")
    private FileHashCodeConfig fileHashCodeConfig;

    @XStreamAlias("FileHashCodeResult")
    private FileHashCodeResult fileHashCodeResult;

    @XStreamAlias("FileCompressResult")
    private FileCompressResult fileCompressResult;

    @XStreamAlias("UserData")
    private String userData;

    @XStreamAlias("Output")
    private MediaOutputObject output;

    public FileCompressConfig getFileCompressConfig() {
        if (fileCompressConfig == null) {
            fileCompressConfig = new FileCompressConfig();
        }
        return fileCompressConfig;
    }

    public void setFileCompressConfig(FileCompressConfig fileCompressConfig) {
        this.fileCompressConfig = fileCompressConfig;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public MediaOutputObject getOutput() {
        if (output == null) {
            output = new MediaOutputObject();
        }
        return output;
    }

    public void setOutput(MediaOutputObject output) {
        this.output = output;
    }

    public FileUnCompressConfig getFileUnCompressConfig() {
        if (fileUnCompressConfig == null) {
            fileUnCompressConfig = new FileUnCompressConfig();
        }
        return fileUnCompressConfig;
    }

    public void setFileUnCompressConfig(FileUnCompressConfig fileUnCompressConfig) {
        this.fileUnCompressConfig = fileUnCompressConfig;
    }

    public FileHashCodeConfig getFileHashCodeConfig() {
        if (fileHashCodeConfig == null) {
            fileHashCodeConfig = new FileHashCodeConfig();
        }
        return fileHashCodeConfig;
    }

    public void setFileHashCodeConfig(FileHashCodeConfig fileHashCodeConfig) {
        this.fileHashCodeConfig = fileHashCodeConfig;
    }

    public FileHashCodeResult getFileHashCodeResult() {
        if (fileHashCodeResult == null) {
            fileHashCodeResult = new FileHashCodeResult();
        }
        return fileHashCodeResult;
    }

    public void setFileHashCodeResult(FileHashCodeResult fileHashCodeResult) {
        this.fileHashCodeResult = fileHashCodeResult;
    }

    public FileCompressResult getFileCompressResult() {
        if (fileCompressResult == null) {
            fileCompressResult = new FileCompressResult();
        }
        return fileCompressResult;
    }

    public void setFileCompressResult(FileCompressResult fileCompressResult) {
        this.fileCompressResult = fileCompressResult;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileProcessOperation{");
        sb.append("fileCompressConfig=").append(fileCompressConfig);
        sb.append(", userData='").append(userData).append('\'');
        sb.append(", output=").append(output);
        sb.append('}');
        return sb.toString();
    }
}
