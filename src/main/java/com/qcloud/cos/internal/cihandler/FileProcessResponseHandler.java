package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.model.ciModel.common.FileProcessInputObject;
import com.qcloud.cos.model.ciModel.common.MediaOutputObject;
import com.qcloud.cos.model.ciModel.job.FileCompressConfig;
import com.qcloud.cos.model.ciModel.job.FileHashCodeConfig;
import com.qcloud.cos.model.ciModel.job.FileHashCodeResult;
import com.qcloud.cos.model.ciModel.job.FileProcessJobDetail;
import com.qcloud.cos.model.ciModel.job.FileProcessJobResponse;
import com.qcloud.cos.model.ciModel.job.FileUnCompressConfig;
import org.xml.sax.Attributes;


public class FileProcessResponseHandler extends CIAbstractHandler {
    public FileProcessJobResponse response = new FileProcessJobResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {

    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        if (in("Response", "JobsDetail")) {
            FileProcessJobDetail jobDetail = response.getJobDetail();
            switch (name) {
                case "Code":
                    jobDetail.setCode(getText());
                    break;
                case "Message":
                    jobDetail.setMessage(getText());
                    break;
                case "JobId":
                    jobDetail.setJobId(getText());
                    break;
                case "Tag":
                    jobDetail.setTag(getText());
                    break;
                case "State":
                    jobDetail.setState(getText());
                    break;
                case "CreationTime":
                    jobDetail.setCreationTime(getText());
                    break;
                case "StartTime":
                    jobDetail.setStartTime(getText());
                    break;
                case "EndTime":
                    jobDetail.setEndTime(getText());
                    break;
                case "QueueId":
                    jobDetail.setQueueId(getText());
                    break;
                case "Progress":
                    jobDetail.setProgress(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "Input")) {
            FileProcessInputObject input = response.getJobDetail().getInput();
            switch (name) {
                case "BucketId":
                    input.setBucketId(getText());
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

        } else if (in("Response", "JobsDetail", "Operation")) {
            if ("UserData".equalsIgnoreCase(name)) {
                response.getJobDetail().getOperation().setUserData(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "Output")) {
            MediaOutputObject output = response.getJobDetail().getOperation().getOutput();
            switch (name) {
                case "Bucket":
                    output.setBucket(getText());
                    break;
                case "Region":
                    output.setRegion(getText());
                    break;
                case "Object":
                    output.setObject(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "Operation", "FileCompressConfig")) {
            FileCompressConfig fileCompressConfig = response.getJobDetail().getOperation().getFileCompressConfig();
            switch (name) {
                case "Prefix":
                    fileCompressConfig.setPrefix(getText());
                    break;
                case "Format":
                    fileCompressConfig.setFormat(getText());
                    break;
                case "Flatten":
                    fileCompressConfig.setFlatten(getText());
                    break;
                case "Key":
                    fileCompressConfig.setKey(null);
                    break;
                case "UrlList":
                    fileCompressConfig.setUrlList(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "Operation", "FileUncompressConfig")) {
            FileUnCompressConfig fileUnCompressConfig = response.getJobDetail().getOperation().getFileUnCompressConfig();
            switch (name) {
                case "Prefix":
                    fileUnCompressConfig.setPrefix(getText());
                    break;
                case "PrefixReplaced":
                    fileUnCompressConfig.setPrefixReplaced(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "Operation", "FileHashCodeConfig")) {
            FileHashCodeConfig fileHashCodeConfig = response.getJobDetail().getOperation().getFileHashCodeConfig();
            switch (name) {
                case "Type":
                    fileHashCodeConfig.setType(getText());
                    break;
                case "AddToHeader":
                    fileHashCodeConfig.setAddToHeader(getText());
                    break;
                default:
                    break;
            }
        }
    }

    public FileProcessJobResponse getResponse() {
        return response;
    }

}
