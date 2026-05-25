package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.job.FileHashCodeInput;
import com.qcloud.cos.model.ciModel.job.FileHashCodeResult;
import com.qcloud.cos.model.ciModel.job.FileHashCodeSyncResponse;
import org.xml.sax.Attributes;

/**
 * 文件哈希值同步计算响应处理器
 */
public class FileHashCodeSyncResponseHandler extends CIAbstractHandler {
    
    public FileHashCodeSyncResponse response = new FileHashCodeSyncResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {
        // 开始元素处理
    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("Response", "FileHashCodeResult")) {
            FileHashCodeResult result = response.getFileHashCodeResult();
            if (result == null) {
                result = new FileHashCodeResult();
                response.setFileHashCodeResult(result);
            }
            switch (name) {
                case "MD5":
                    result.setMd5(getText());
                    break;
                case "SHA1":
                    result.setSha1(getText());
                    break;
                case "SHA256":
                    result.setSha256(getText());
                    break;
                case "FileSize":
                    result.setFileSize(getText());
                    break;
                case "LastModified":
                    result.setLastModified(getText());
                    break;
                case "Etag":
                    result.setEtag(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "Input")) {
            FileHashCodeInput input = response.getInput();
            if (input == null) {
                input = new FileHashCodeInput();
                response.setInput(input);
            }
            switch (name) {
                case "Bucket":
                    input.setBucket(getText());
                    break;
                case "Object":
                    input.setObject(getText());
                    break;
                case "Region":
                    input.setRegion(getText());
                    break;
                default:
                    break;
            }
        }
    }

    public FileHashCodeSyncResponse getResponse() {
        return response;
    }
}