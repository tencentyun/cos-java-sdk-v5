package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.internal.ParserMediaInfoUtils;
import com.qcloud.cos.model.ciModel.common.BatchInputObject;
import com.qcloud.cos.model.ciModel.common.MediaInputObject;
import com.qcloud.cos.model.ciModel.job.AigcMetadata;
import com.qcloud.cos.model.ciModel.job.BatchJobDetail;
import com.qcloud.cos.model.ciModel.job.BatchJobOperation;
import com.qcloud.cos.model.ciModel.job.BatchJobResponse;
import com.qcloud.cos.model.ciModel.job.MediaJobObject;
import com.qcloud.cos.model.ciModel.job.MediaJobOperation;
import com.qcloud.cos.model.ciModel.job.MediaPicProcessTemplateObject;
import com.qcloud.cos.model.ciModel.job.MediaTimeIntervalObject;
import com.qcloud.cos.model.ciModel.job.ProcessResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import com.qcloud.cos.model.ciModel.template.MediaSegmentObject;
import org.xml.sax.Attributes;

public class BatchJobResponseHandler extends CIAbstractHandler {
    BatchJobResponse response = new BatchJobResponse();

    @Override
    protected void doStartElement(String uri, String name, String qName, Attributes attrs) {

    }

    @Override
    protected void doEndElement(String uri, String name, String qName) {
        BatchJobDetail jobDetail = response.getJobDetail();
        if (in("Response")) {
            if ("RequestId".equalsIgnoreCase(name)) {
                response.setRequestId(getText());
            }
        } else if (in("Response", "JobsDetail")) {
            switch (name) {
                case "Code":
                    jobDetail.setCode(getText());
                    break;
                case "CreationTime":
                    jobDetail.setCreationTime(getText());
                    break;
                case "EndTime":
                    jobDetail.setEndTime(getText());
                    break;
                case "JobId":
                    jobDetail.setJobId(getText());
                    break;
                case "Message":
                    jobDetail.setMessage(getText());
                    break;
                case "State":
                    jobDetail.setState(getText());
                    break;
                case "Name":
                    jobDetail.setName(getText());
                    break;
                case "Type":
                    jobDetail.setType(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "Input")) {
            BatchInputObject input = jobDetail.getInput();
            ParserMediaInfoUtils.ParsingInput(input, name, getText());
        } else if (in("Response", "JobsDetail", "Operation")) {
            BatchJobOperation operation = response.getJobDetail().getOperation();
            switch (name) {
                case "QueueId":
                    operation.setQueueId(getText());
                    break;
                case "UserData":
                    operation.setUserData(getText());
                    break;
                case "CallBack":
                    operation.setCallBack(getText());
                    break;
                case "Tag":
                    operation.setTag(getText());
                    break;
                case "JobLevel":
                    operation.setJobLevel(getText());
                    break;
                case "CallBackFormat":
                    operation.setCallBackFormat(getText());
                    break;
                case "CallBackType":
                    operation.setCallBackType(getText());
                    break;
                case "WorkflowIds":
                    operation.setWorkflowIds(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "Operation", "TimeInterval")) {
            MediaTimeIntervalObject timeInterval = response.getJobDetail().getOperation().getTimeInterval();
            switch (name) {
                case "Duration":
                    timeInterval.setDuration(getText());
                    break;
                case "Start":
                    timeInterval.setStart(getText());
                    break;
                case "End":
                    timeInterval.setEnd(getText());
                    break;
                default:
                    break;
            }
        } else if (in("Response", "JobsDetail", "Operation", "JobParam")) {
            if ("TemplateId".equalsIgnoreCase(name)) {
                jobDetail.getOperation().getJobParam().setTemplateId(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "Output")) {
            ParserMediaInfoUtils.ParsingOutput(jobDetail.getOperation().getOutput(), name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "JobParam", "PicProcess")) {
            MediaPicProcessTemplateObject picProcess = jobDetail.getOperation().getJobParam().getPicProcess();
            if ("IsPicInfo".equalsIgnoreCase(name)) {
                picProcess.setIsPicInfo(getText());
            } else if ("ProcessRule".equalsIgnoreCase(name)) {
                picProcess.setProcessRule(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "JobParam", "PicProcess")) {
            MediaPicProcessTemplateObject picProcess = jobDetail.getOperation().getJobParam().getPicProcess();
            if ("IsPicInfo".equalsIgnoreCase(name)) {
                picProcess.setIsPicInfo(getText());
            } else if ("ProcessRule".equalsIgnoreCase(name)) {
                picProcess.setProcessRule(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "JobParam", "PicProcessResult")) {
            if ("ObjectName".equalsIgnoreCase(name)) {
                jobDetail.getOperation().getJobParam().getPicProcessResult().setObjectName(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "JobParam", "PicProcessResult", "OriginalInfo")) {
            if ("Etag".equalsIgnoreCase(name)) {
                jobDetail.getOperation().getJobParam().getPicProcessResult().getOriginalInfo().setEtag(getText());
            }
        } else if (in("Response", "JobsDetail", "Operation", "JobParam", "PicProcessResult", "OriginalInfo", "ImageInfo")) {
            ImageInfo imageInfo = jobDetail.getOperation().getJobParam().getPicProcessResult().getOriginalInfo().getImageInfo();
            ParserMediaInfoUtils.ParsingImageInfo(imageInfo, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "JobParam", "PicProcessResult", "ProcessResult")) {
            ProcessResult processResult = jobDetail.getOperation().getJobParam().getPicProcessResult().getProcessResult();
            ParserMediaInfoUtils.ParsingProcessResult(processResult, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "JobParam", "Segment")) {
            MediaSegmentObject segment = jobDetail.getOperation().getJobParam().getSegment();
            ParserMediaInfoUtils.ParsingSegment(segment, name, getText());
        } else if (in("Response", "JobsDetail", "Operation", "JobParam", "Segment", "AIGCMetadata")) {
            AigcMetadata aigcMetadata = jobDetail.getOperation().getJobParam().getSegment().getAigcMetadata();
            ParserMediaInfoUtils.ParsingAigcMetadata(aigcMetadata, name, getText());
        }
    }

    public BatchJobResponse getResponse() {
        return response;
    }
}
