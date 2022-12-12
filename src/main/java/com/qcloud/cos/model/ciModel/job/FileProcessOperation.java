package com.qcloud.cos.model.ciModel.job;

import com.qcloud.cos.model.ciModel.common.MediaOutputObject;

public class FileProcessOperation {
    private FileCompressConfig fileCompressConfig;
    private FileUnCompressConfig fileUnCompressConfig;
    private FileHashCodeConfig fileHashCodeConfig;
    private String userData;
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
