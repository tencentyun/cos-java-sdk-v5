package com.qcloud.cos.model.ciModel.job;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 文件哈希值同步计算响应
 * 详情见 https://cloud.tencent.com/document/product/460/79735
 */
@XStreamAlias("Response")
public class FileHashCodeSyncResponse {
    
    @XStreamAlias("FileHashCodeResult")
    private FileHashCodeResult fileHashCodeResult;
    
    @XStreamAlias("Input")
    private FileHashCodeInput input;

    public FileHashCodeResult getFileHashCodeResult() {
        return fileHashCodeResult;
    }

    public void setFileHashCodeResult(FileHashCodeResult fileHashCodeResult) {
        this.fileHashCodeResult = fileHashCodeResult;
    }

    public FileHashCodeInput getInput() {
        return input;
    }

    public void setInput(FileHashCodeInput input) {
        this.input = input;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileHashCodeSyncResponse{");
        sb.append("fileHashCodeResult=").append(fileHashCodeResult);
        sb.append(", input=").append(input);
        sb.append('}');
        return sb.toString();
    }
}